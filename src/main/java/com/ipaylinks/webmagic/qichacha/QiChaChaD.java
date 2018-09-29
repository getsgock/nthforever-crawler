package com.ipaylinks.webmagic.qichacha;

import com.ipaylinks.webmagic.qichacha.dao.CompanyQueneDAO;
import com.ipaylinks.webmagic.qichacha.model.CompanyQuene;
import com.ipaylinks.webmagic.qichacha.model.CompanyQueneRes;
import com.ipaylinks.webmagic.qichacha.model.ScannerQuene;
import com.ipaylinks.webmagic.qichacha.pipeline.CompanyInfoPipeline;
import com.ipaylinks.webmagic.qichacha.pipeline.CompanyListPipeline;
import com.ipaylinks.webmagic.qichacha.util.SourceUtil;
import com.ipaylinks.webmagic.util.MyBatisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.ibatis.session.SqlSession;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.ProxyProvider;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

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

public class QiChaChaD implements PageProcessor {

    private static final String DOOR = "https://m.qichacha.com/";
    private static final String DOMAIN ="m.qichacha.com";
    private static final int THREAD_NUM = 5;
    private static final String ROOT = "https://m.qichacha.com/search?key=";
    // 隧道代理订单appKey(请注意替换)
    String appKey  = "RkJrTmhCWjdyVVNOa3F4TDpZVTN1T2RpRFJQN0NhZml3";
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(2).setSleepTime(1000)
            .setDomain(DOMAIN)
            .addHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36")
             .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
             .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
            .addHeader("Connection", "keep-alive")
            .addHeader("X-Requested-With", "XMLHttpRequest")
            .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
	        .addHeader("Proxy-Authorization", "Basic "+ appKey);
    private CookieStore cookieStore;

    public void process(Page page) {
        String origin = page.getUrl().toString();
        System.out.println("解析开始 ==> " +origin);
        //捕获列表页面
        boolean helperMatches = origin.startsWith("https://m.qichacha.com/search?key=");
        if (helperMatches){
            List<String> all = page.getHtml().links().regex("/firm\\w*.shtml").all();
            Map<String, String> request = SourceUtil.URLRequest(origin);
            String id ="";
            if (request.containsKey("id")){
                id = request.get("id");
            }else {
                page.setSkip(true);
            }

            if (all.size() == 0){
                System.out.println("页面数据  ==> "+page.getRawText());
                String s = page.getHtml().xpath("//meta[contains(@name,'keywords')]").toString();
                if (StringUtils.isEmpty(s)) {
                    page.setSkip(true);
                    System.out.println("数据采集被限，放弃 ==> "+origin);
                }else {
                    CompanyQueneRes res = new CompanyQueneRes();
                    res.setId(Integer.parseInt(id));
                    page.putField("res",res);
                    System.out.println("没有搜索到相关公司 ==> "+origin);
                }
            }else {
                System.out.println("数据采集完成，success  ==> "+origin);
                CompanyQueneRes res = new CompanyQueneRes();
                List<CompanyQuene> list = new ArrayList<CompanyQuene>();
                for (String targetUrl:all){
                    CompanyQuene quene = new CompanyQuene();
                    quene.setQueneId(Integer.parseInt(id));
                    quene.setUrl(targetUrl);
                    list.add(quene);
                }
                res.setQuenes(list);
                res.setId(Integer.parseInt(id));
                page.putField("res",res);
            }
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
        //
        final QiChaChaD qiChaChaD = new QiChaChaD();
        //模拟请求获取cookie

        final HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
//        ProxyProvider pp = new SimpleProxyProvider(QiChaChaInit.getProxyList());
        Proxy proxy = new Proxy("transfer.mogumiao.com",9001,"FBkNhBZ7rUSNkqxL","YU3uOdiDRP7Cafiw");
        List<Proxy> proxyList = new ArrayList<Proxy>();
        proxyList.add(proxy);
        ProxyProvider pp = new SimpleProxyProvider(proxyList);
        httpClientDownloader.setProxyProvider(pp);
        //爬取数据

        ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
        service.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
                String time = sdf.format(System.currentTimeMillis());
                System.out.println("任务开始 ==== "+ time);
                Spider spider = Spider.create( new QiChaChaD())
//                .addUrl(DOOR)
                        .addPipeline(new ConsolePipeline())
                        .addPipeline(new CompanyListPipeline())
                        .setDownloader(httpClientDownloader)
                        .thread(THREAD_NUM);

                //查询并添加任务
                SqlSession sqlSession = MyBatisUtil.getSqlSession();
                CompanyQueneDAO companyQueneDAO = sqlSession.getMapper(CompanyQueneDAO.class);
                List<ScannerQuene> scannerQuenes = companyQueneDAO.selectUndoList(new ScannerQuene());
                sqlSession.close();
                if (null == scannerQuenes || scannerQuenes.size() == 0){
                    return;
                }
                for (ScannerQuene quene:scannerQuenes){
                    spider.addUrl(ROOT+quene.getCompanyName()+"&id="+quene.getId());
                }
                spider.run();
            }
        },0,10,TimeUnit.SECONDS);

//        service.scheduleWithFixedDelay(new Runnable() {
//            public void run() {
//                System.out.println("更换代理开始");
//                List<Proxy> proxies = QiChaChaInit.changeProxy();
//                ProxyProvider proxyProvider = new SimpleProxyProvider(QiChaChaInit.getProxyList());
//                httpClientDownloader.setProxyProvider(proxyProvider);
//                System.out.println("更换代理结束");
//            }
//        },300,300,TimeUnit.SECONDS);
    }
}
