package com.example.administrator.myfirstapp.model;

import java.io.Serializable;

/**
 * 每条新闻包含的具体的属性
 * Created by Administrator on 16-9-7.
 */
public class HomeNews<T> implements Serializable {
    private String summary;
    private String icon;
    private String stamp;
    private String title;
    private String link;
    private int nid;
    private int type;

    public HomeNews(String summary, String icon, String stamp, String title, String link, int nid, int type) {
        this.summary = summary;
        this.icon = icon;
        this.stamp = stamp;
        this.title = title;
        this.link = link;
        this.nid = nid;
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "HomeNews{" +
                "summary='" + summary + '\'' +
                ", icon='" + icon + '\'' +
                ", stamp='" + stamp + '\'' +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", nid=" + nid +
                ", type=" + type +
                '}';
    }
}
