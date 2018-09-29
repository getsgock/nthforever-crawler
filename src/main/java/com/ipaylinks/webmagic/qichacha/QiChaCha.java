package com.ipaylinks.webmagic.qichacha;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.pipeline.FilePageModelPipeline;

@Deprecated
@TargetUrl("https://m.qichacha.com/firm_[a-zA-Z0-9]*.shtml")
@HelpUrl("https://m.qichacha.com/search?key=.*")
public class QiChaCha {

    @ExtractBy(value = "//div[@class='company-name']/text()")
    private String companyName;

    @ExtractBy(value = "//a[starts-with(@href,'tel://')]")
    private String name;

    @ExtractBy(value = "//div[@class='oper-warp']/a[@class='oper']/text()")
    private String oper;

    @ExtractBy(value = "//a[contains(@class,'email')]/text()")
    private String email;

    @ExtractBy(value = "//div[contains(@class,'basic-wrap')]//div[contains(@class,'basic-item-left')][text()='注册号']/following-sibling::div[1]/text()")
    private String registrationId;


    public static void main(String[] args) {
        Spider spider = OOSpider.create(Site.me().setRetryTimes(2).setSleepTime(2000),QiChaCha.class)
                .addUrl("https://m.qichacha.com/")
//                .addPipeline()
                .thread(3);
    }
}
