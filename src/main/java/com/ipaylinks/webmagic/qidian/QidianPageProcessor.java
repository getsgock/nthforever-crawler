package com.ipaylinks.webmagic.qidian;

import com.ipaylinks.webmagic.qidian.dao.BookInfoDAO;
import com.ipaylinks.webmagic.qidian.model.BookInfo;
import com.ipaylinks.webmagic.util.MyBatisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

@TargetUrl("https://book.qidian.com/info/\\d*")
@HelpUrl("https://www.qidian.com/\\w+")
public class QidianPageProcessor implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @ExtractBy(value = "//h1/em/text()")
    private String name;

    @ExtractBy(value = "//a[@class='writer']/text()")
    private String author;


    public void process(Page page) {
        //是否为内容页
        System.out.println("page : "+page.getUrl());
        boolean matches = page.getUrl().toString().matches("https://book.qidian.com/info/\\d*");
        if (matches){
            String name = page.getHtml().xpath("//h1/em/text()").toString();
            String author = page.getHtml().xpath("//a[@class='writer']/text()").toString();
            BookInfo info = new BookInfo();
            info.setName(name);
            info.setAuthor(author);
            page.putField("info",info);
        }else {
            page.setSkip(true);
        }
        List<String> all = page.getHtml().links().regex("https://www.qidian.com/\\w+").all();
        List<String> all2 = page.getHtml().links().regex("//book.qidian.com/info/\\d*").all();
        page.addTargetRequests(all);
        page.addTargetRequests(all2);
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        Spider.create(new QidianPageProcessor())
                .addUrl("https://www.qidian.com")
                .addPipeline(new ConsolePipeline())
                .addPipeline(new MysqlPipeline())
                .thread(3)
                .run();
//        OOSpider.create(Site.me(),new ConsolePageModelPipeline(),QidianPageProcessor.class)
//                .addUrl("https://www.qidian.com")
//                .thread(3)
//                .run();
    }
}
