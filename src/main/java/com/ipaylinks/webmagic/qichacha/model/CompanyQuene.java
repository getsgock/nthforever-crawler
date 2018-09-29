package com.ipaylinks.webmagic.qichacha.model;

import java.util.Date;

public class CompanyQuene {

    /**
     * id 主键
     */
    private int id;
    /**
     * 表company_quene的主键id
     */
    private int queneId;
    /**
     * 抓取的url
     */
    private String url;
    /**
     * 处理状态，0未处理，1处理成功
     */
    private  short status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQueneId() {
        return queneId;
    }

    public void setQueneId(int queneId) {
        this.queneId = queneId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "CompanyQuene{" +
                "id=" + id +
                ", queneId=" + queneId +
                ", url=" + url +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
