package com.ipaylinks.webmagic.qichacha;

import com.ipaylinks.webmagic.qichacha.dao.CompanyQueneDAO;
import com.ipaylinks.webmagic.qichacha.model.ScannerQuene;
import com.ipaylinks.webmagic.qichacha.util.ProxyAuthenticator;
import com.ipaylinks.webmagic.qichacha.util.SourceUtil;
import com.ipaylinks.webmagic.util.MyBatisUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.webmagic.proxy.Proxy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QiChaChaInit {
    private static final String DOOR = "https://m.qichacha.com/";
    private static int count=0;

    public static void main(String[] args) {
        //获取数据源
        SourceUtil util = new SourceUtil();
        List<String> sourceList = util.getSourceList();
        System.out.println("sourceList size() = "+sourceList.size());
        SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
        CompanyQueneDAO companyQueneDAO = sqlSession.getMapper(CompanyQueneDAO.class);
        for (String s:sourceList){
            ScannerQuene scannerQuene = new ScannerQuene();
            scannerQuene.setCompanyName(s.trim());
            companyQueneDAO.addCompanyQuene(scannerQuene);
        }
        sqlSession.close();
    }


    public static CookieStore getCookieStore(){
        CookieStore store = new BasicCookieStore();
        HttpHost proxy = new HttpHost("210.72.14.142",80);
        CloseableHttpClient build = HttpClients.custom()
                .setProxy(proxy)
                .setDefaultCookieStore(store).build();
//        HttpGet get = new HttpGet(DOOR);
        HttpGet get = new HttpGet("https://m.qichacha.com/firm_71d1e716cd0b159086c0434148a1aaad.shtml");
        get.addHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        get.addHeader("Accept-Language", "zh-CN,zh;q=0.9");
        get.addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        get.addHeader("Connection", "keep-alive");
        get.addHeader("X-Requested-With", "XMLHttpRequest");
        get.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        try {
            CloseableHttpResponse execute = build.execute(get);
            HttpEntity entity = execute.getEntity();
            InputStream content = entity.getContent();
            String s = IOUtils.toString(content);
            System.out.println(s);
            //
        } catch (IOException e) {
            e.printStackTrace();
        }
        return store;
    }

    public static CookieStore getCookieStoreLocal(){
        CookieStore cookieStore = getCookieStore();
//        CookieStore store = new BasicCookieStore();
        BasicClientCookie cookie = (BasicClientCookie) cookieStore.getCookies().get(0);
        try {
            BasicClientCookie clone = (BasicClientCookie) cookie.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        BasicClientCookie basicClientCookie = new BasicClientCookie("","");
        String origin = "acw_tc=7ae3a4a915368048934941486eac244baec97da07ec7920359fd872c92; PHPSESSID=1gkmem5rsch0f7gbqqmmk31165; UM_distinctid=165d0b5bb6f3c5-0d900003243d41-9393265-1fa400-165d0b5bb7376f; zg_did=%7B%22did%22%3A%20%22165d0b5bb89ec9-085b0beb4796d7-9393265-1fa400-165d0b5bb8a4c9%22%7D; Hm_lvt_3456bee468c83cc63fb5147f119f1075=1536804896,1536841804,1536843262,1536843278; acw_sc__v3=5b9f76d39643452b91983b6c7cace32f881c783e; CNZZDATA1254842228=1469617885-1536803992-%7C1537175992; zg_de1d1a35bfa24ce29bbf2c7eb17e6c4f=%7B%22sid%22%3A%201537177302222%2C%22updated%22%3A%201537177713719%2C%22info%22%3A%201536804895631%2C%22superProperty%22%3A%20%22%7B%7D%22%2C%22platform%22%3A%20%22%7B%7D%22%2C%22utm%22%3A%20%22%7B%7D%22%2C%22referrerDomain%22%3A%20%22m.qichacha.com%22%7D; Hm_lpvt_3456bee468c83cc63fb5147f119f1075=1537177714";
        String[] split = origin.split(";");
        for (String less:split){
            String[] finals = less.split("=");

        }
        return null;
    }

    public static List<Proxy> getProxyList(){
        List<Proxy> list = new ArrayList<Proxy>();
        String str = "106.110.145.133:22687\n" +
                "220.165.17.206:40342\n" +
                "39.81.62.21:41737\n" +
                "182.247.54.33:27713\n" +
                "101.27.21.195:28869\n" +
                "116.249.181.129:38007\n" +
                "1.193.220.116:29137\n" +
                "116.53.197.121:28435\n" +
                "113.9.20.184:21309\n" +
                "42.239.240.140:11104\n" +
                "106.57.192.30:20646\n" +
                "60.161.201.139:19454\n" +
                "118.254.84.123:42432\n" +
                "1.196.143.52:22661\n" +
                "58.62.202.250:35300\n" +
                "113.239.0.235:30879\n" +
                "220.186.151.117:26635\n" +
                "182.247.139.127:20548\n" +
                "117.95.9.114:28456\n" +
                "123.133.207.5:42636\n" +
                "125.89.22.92:25326\n" +
                "114.236.67.59:17560\n" +
                "180.121.115.38:26451\n" +
                "60.168.22.234:29037\n" +
                "218.9.224.201:21874\n" +
                "112.240.177.80:18636\n" +
                "211.93.55.72:29663\n" +
                "27.206.72.39:41802\n" +
                "116.115.209.12:34241\n" +
                "125.117.131.68:31356\n" +
                "113.221.44.169:26404\n" +
                "182.246.10.124:25081\n" +
                "220.170.202.2:27131\n" +
                "140.250.162.200:28510\n" +
                "125.105.6.107:10310\n" +
                "117.94.207.226:11281\n" +
                "223.243.132.193:28480\n" +
                "14.115.68.124:26518\n" +
                "118.253.3.190:18145\n" +
                "119.116.47.137:27586\n" +
                "60.185.148.113:20319\n" +
                "113.87.193.152:36858\n" +
                "220.170.202.231:27298\n" +
                "180.118.247.238:12104\n" +
                "122.7.225.86:20358\n" +
                "1.25.104.93:29842\n" +
                "114.236.241.6:29212\n" +
                "117.95.64.53:28203\n" +
                "183.32.221.190:19696\n" +
                "115.216.228.228:32704";
        String[] split = str.split("\n");
        for (String s :split){
            String[] split1 = s.split(":");
            String host = split1[0];
            int port = Integer.parseInt(split1[1]);
            Proxy proxy = new Proxy(host,port);
            list.add(proxy);
        }
        return list;
    }

    public static List<Proxy> changeProxy(){
        List<Proxy> list = new ArrayList<Proxy>();
        CloseableHttpClient aDefault = HttpClients.createDefault();
        HttpGet get = new HttpGet("http://ip.11jsq.com/index.php/api/entry?method=proxyServer.generate_api_url&packid=0&fa=0&fetch_key=&qty=50&time=101&pro=&city=&port=1&format=txt&ss=1&css=&dt=1&specialTxt=3&specialJson=");
        try {
            CloseableHttpResponse execute = aDefault.execute(get);
            HttpEntity entity = execute.getEntity();
            InputStream content = entity.getContent();
            String str = IOUtils.toString(content);
            System.out.println("ip str "+str);
            String[] split = str.split("\n");
            for (String s :split){
                String[] split1 = s.split(":");
                String host = split1[0];
                int port = Integer.parseInt(split1[1]);
                Proxy proxy = new Proxy(host,port);
                list.add(proxy);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Proxy> nextProxy(){
        List<Proxy> proxyList = getProxyList();
        int size = proxyList.size();
        int i = count / size;
        List<Proxy> p = new ArrayList<Proxy>();
        Proxy proxy = proxyList.get(i);
        p.add(proxy);
        System.out.println("当前代理 host ==> "+proxy.getHost()+"  port ==> "+proxy.getPort()+ "  第"+i+"个");
        return p;
    }

    public static void countAdd(){
        count++;
        System.out.println("准备切换代理");
    }

    /**********************************************************************************************************/

    public static List<Proxy> getProxyByAbu(){

        List<Proxy> list = new ArrayList<Proxy>();

        // 要访问的目标页面
        String targetUrl = "http://test.abuyun.com";

        // 代理服务器
        String proxyServer = "http-dyn.abuyun.com";
        int proxyPort      = 9020;

        // 代理隧道验证信息
        String proxyUser  = "HS52D86WA540690D";
        String proxyPass  = "2011D76130E267FE";

        try {
            URL url = new URL(targetUrl);

            Authenticator.setDefault(new ProxyAuthenticator(proxyUser, proxyPass));

            // 创建代理服务器地址对象
            InetSocketAddress addr = new InetSocketAddress(proxyServer, proxyPort);
            // 创建HTTP类型代理对象
            java.net.Proxy proxy = new java.net.Proxy(java.net.Proxy.Type.HTTP, addr);

            // 设置通过代理访问目标页面
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);

            // 解析返回数据
            byte[] response = readStream(connection.getInputStream());

            System.out.println(new String(response));
            String regEx="((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(new String(response));

            while (m.find()) {
                String result=m.group();
                System.out.println(result);
                break;
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        return list;
    }

    /**
     * 将输入流转换成字符串
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;

        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();

        return outSteam.toByteArray();
    }
 }
