package com.example.administrator.myfirstapp.model;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class CommentContent {
    private String uid;
    private String content;
    private String stamp;
    private String portrait;
    private int cid;

    public CommentContent(String uid, String content, String stamp, String portrait, int cid) {
        this.uid = uid;
        this.content = content;
        this.stamp = stamp;
        this.portrait = portrait;
        this.cid = cid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "CommentContent{" +
                "uid='" + uid + '\'' +
                ", content='" + content + '\'' +
                ", stamp=" + stamp +
                ", portrait='" + portrait + '\'' +
                ", cid=" + cid +
                '}';
    }
}
