package com.ipaylinks.webmagic.qichacha;

import com.ipaylinks.webmagic.qichacha.dao.CompanyListQueneDAO;
import com.ipaylinks.webmagic.qichacha.dao.CompanyQueneDAO;
import com.ipaylinks.webmagic.qichacha.model.CompanyInfo;
import com.ipaylinks.webmagic.qichacha.model.CompanyInfoRes;
import com.ipaylinks.webmagic.qichacha.model.CompanyQuene;
import com.ipaylinks.webmagic.qichacha.model.ScannerQuene;
import com.ipaylinks.webmagic.qichacha.pipeline.CompanyInfoPipeline;
import com.ipaylinks.webmagic.qichacha.pipeline.CompanyListPipeline;
import com.ipaylinks.webmagic.qichacha.util.SourceUtil;
import com.ipaylinks.webmagic.util.MyBatisUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.ibatis.session.SqlSession;
import org.openqa.selenium.chrome.ChromeDriver;
import sun.net.www.http.HttpClient;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.ProxyProvider;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.selector.Selectable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QiChaChaS implements PageProcessor {

    private static final String DOMAIN ="m.qichacha.com";
    private static final int THREAD_NUM = 3;
    private static final String ROOT = "https://m.qichacha.com";
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    // 隧道代理订单appKey(请注意替换)
    static final String APP_KEY  = "bWUyZ2cyd2paMzVTTU1keTpLb3V1OG1GT1J2aTV0SG1m";
    static final String NAME ="me2gg2wjZ35SMMdy";
    static final String PASS_WORD ="Kouu8mFORvi5tHmf";
    private Site site = Site.me().setRetryTimes(2).setSleepTime(500)
            .setDomain(DOMAIN)
            .addHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
			.addHeader("Connection", "keep-alive")
			.addHeader("X-Requested-With", "XMLHttpRequest")
			.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
			.addHeader("Proxy-Authorization", "Basic "+ APP_KEY);

    private CookieStore cookieStore;

    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来
        String origin = page.getUrl().toString();
        //捕获目标页面
        boolean targetMatches = origin.startsWith("https://m.qichacha.com/firm");
        String rawText = page.getRawText();
        System.out.println("页面链接  ==> "+origin);
        //  //<script>window.location.href='http://m.qichacha.com/index_verify?type=companyview&back=/firm_hfbe53ed70b9c6e77fc79860e1bff2cb.shtml?id=29';</script>
//        if (rawText.startsWith("<script>window.location.href")){
//            String regex = "'([^']*)'";
//            Pattern pattern = Pattern.compile(regex);
//            Matcher matcher = pattern.matcher(rawText);
//            while (matcher.find()) {
//                String group = matcher.group().replace("'","");
//                System.out.println("window.location.href ==》 "+group);
//                ChromeDriver driver = new ChromeDriver();
//                Map<String, String> cookies = page.getRequest().getCookies();
//                Iterator<Map.Entry<String, String>> iterator = cookies.entrySet().iterator();
//                while (iterator.hasNext()){
//                    Map.Entry<String, String> b = iterator.next();
//                    org.openqa.selenium.Cookie cookie = new org.openqa.selenium.Cookie( b.getKey(),b.getValue());
//                    driver.manage().addCookie(cookie);
//                }
//                driver.get(group);
//
//                Map<String, String> request = SourceUtil.URLRequest(group);
//                String id ="";
//                if (request.containsKey("id")){
//                    id = request.get("id");
//                }else {
//                    page.setSkip(true);
//                }
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                String company = driver.findElementByXPath("//div[@class='company-name']/text()").getText();
//                String tel = driver.findElementByXPath("//a[starts-with(@href,'tel://')]/text()").getText();
//                String oper = driver.findElementByXPath("//div[@class='oper-warp']/a[@class='oper']/text()").getText();
//                String email = driver.findElementByXPath("//a[contains(@class,'email')]/text()]").getText();
//                String registrationId = driver.findElementByXPath("//div[contains(@class,'basic-wrap')]//div[contains(@class,'basic-item-left')][text()='注册号']/following-sibling::div[1]").getText();
//                if (!StringUtils.isEmpty(registrationId) && !"-".equals(registrationId)){
//                    CompanyInfoRes res = new CompanyInfoRes();
//                    res.setLisId(Integer.parseInt(id));
//                    CompanyInfo info = new CompanyInfo();
//                    info.setCompanyName(company);
//                    info.setPhone(tel);
//                    info.setOper(oper);
//                    info.setEmail(email);
//                    info.setRegistrationId(Long.parseLong(registrationId));
//                    res.setCompanyInfo(info);
//                    page.putField("res",res);
//                    System.out.println("数据采集完成，success  ==> "+origin);
//                }else {
//                    page.setSkip(true);
//                }
//                driver.close();
////                page.addTargetRequest(group);
//                break;
//            }
//        }
        if (targetMatches){
            String company = page.getHtml().xpath("//div[@class='company-name']/text()").toString();
            String tel = page.getHtml().xpath("//a[starts-with(@href,'tel://')]/text()").toString();
            String oper = page.getHtml().xpath("//div[contains(@class,'oper-warp')]/a[@class='oper']/text()").toString();
            String email = page.getHtml().xpath("//a[contains(@class,'email')]/text()").toString();
            String registrationId="";
            String creditNum="";
            String registeredCapital="";
            String createDate="";
            String companyType="";
            String range="";
            String address="";
            String companyStatus="";
            try {
                Selectable xpath = page.getHtml().xpath("//div[contains(@class,'basic-wrap')]/div[contains(@class,'basic-item-left')]");
                Selectable xpath2 = page.getHtml().xpath("//div[contains(@class,'basic-item-right')]/text()");
                if (xpath2.nodes().size() != 0){
                    registrationId = xpath2.nodes().get(1).get().trim();
                    creditNum = xpath2.nodes().get(2).get().trim();
                    registeredCapital = xpath2.nodes().get(3).get().trim();
                    createDate = xpath2.nodes().get(4).get().trim();
                    companyType = xpath2.nodes().get(5).get().trim();
                    range = xpath2.nodes().get(6).get().trim();
                    address = xpath2.nodes().get(7).get().trim();
                    companyStatus = xpath2.nodes().get(8).get().trim();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            Map<String, String> request = SourceUtil.URLRequest(origin);
            String id ="";
            if (request.containsKey("id")){
                id = request.get("id");
            }else {
                page.setSkip(true);
            }

            if (!StringUtils.isEmpty(registrationId)){

                if ("-".equals(registrationId)){
                    CompanyInfoRes res = new CompanyInfoRes();
                    page.putField("res",res);
                    res.setLisId(Integer.parseInt(id));
                    System.out.println("数据采集放弃，failure  ==> "+origin);
                }else {
                    CompanyInfoRes res = new CompanyInfoRes();
                    res.setLisId(Integer.parseInt(id));
                    CompanyInfo info = new CompanyInfo();
                    info.setCompanyName(company);
                    info.setPhone(tel);
                    info.setOper(oper);
                    info.setEmail(email);
                    info.setRegistrationId(registrationId);
                    info.setCreditNum(creditNum);
                    info.setAddress(address);
                    info.setCompanyStatus(companyStatus);
                    info.setRange(range);
                    info.setCompanyType(companyType);
                    info.setRegisteredCapital(registeredCapital);
                    info.setCreateDate(createDate);
                    res.setCompanyInfo(info);
                    page.putField("res",res);
                    System.out.println("数据采集完成，success  ==> "+origin);
                }
            }else {
                CompanyInfoRes res = new CompanyInfoRes();
                page.putField("res",res);
                res.setLisId(Integer.parseInt(id));
                System.out.println("数据采集为空，failure  ==> "+origin);
            }
            System.out.println("targetMatches  is true");
        }else {
            page.setSkip(true);
        }
    }



    public Site getSite() {
        return site;
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }

    public void setCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }

    public static void main(String[] args) {


        //模拟请求获取cookie
        final HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
//        ProxyProvider pp = new SimpleProxyProvider(QiChaChaInit.getProxyList());
        Proxy proxy = new Proxy("transfer.mogumiao.com",9001,NAME,PASS_WORD);
        List<Proxy> proxyList = new ArrayList<Proxy>();
        proxyList.add(proxy);
        ProxyProvider pp = new SimpleProxyProvider(proxyList);
        httpClientDownloader.setProxyProvider(pp);
//        final SeleniumDownloader seleniumDownloader = new SeleniumDownloader("D:\\chromedriver_win32\\chromedriver.exe");
//        System.getProperties().setProperty("webdriver.chrome.driver","D:\\chromedriver_win32\\chromedriver.exe");
//        System.setProperty("selenuim_config", "D:\\spiders\\webmaigc-jiaqi\\config.ini");
        ScheduledExecutorService service = Executors.newScheduledThreadPool(3);

        service.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
                String time = sdf.format(System.currentTimeMillis());
                System.out.println("任务开始 ==== "+ time);
                Spider spider = Spider.create( new QiChaChaS())
//                .addUrl(DOOR)
                        .addPipeline(new ConsolePipeline())
                        .addPipeline(new CompanyInfoPipeline())
                        .setDownloader(httpClientDownloader)
                        .thread(THREAD_NUM);

                //查询并添加任务
                SqlSession sqlSession = MyBatisUtil.getSqlSession();
                CompanyListQueneDAO companyListQueneDAO = sqlSession.getMapper(CompanyListQueneDAO.class);
                List<CompanyQuene> companyQuenes = companyListQueneDAO.selectUndoList(new CompanyQuene());
                sqlSession.close();
                if (null == companyQuenes || companyQuenes.size() == 0){
                    return;
                }
                for (CompanyQuene quene:companyQuenes){
                    spider.addUrl(ROOT+quene.getUrl()+"?id="+quene.getId());
                }
                spider.run();
            }
        },0,3,TimeUnit.SECONDS);

//        service.scheduleWithFixedDelay(new Runnable() {
//            public void run() {
//                System.out.println("更换代理开始");
//                List<Proxy> proxies = QiChaChaInit.changeProxy();
//                ProxyProvider proxyProvider = new SimpleProxyProvider(QiChaChaInit.getProxyList());
//                httpClientDownloader.setProxyProvider(proxyProvider);
//                System.out.println("更换代理结束");
//            }
//        },0,5,TimeUnit.SECONDS);
    }

}
