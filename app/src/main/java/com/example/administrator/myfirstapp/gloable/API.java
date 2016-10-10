package com.example.administrator.myfirstapp.gloable;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class API {

    public static final String ServerIP = "http://118.244.212.82:9092/newsClient/";
    /**
     * 首页列表
     */
//    public static final String NEWS_LIST = ServerIP + "news_sort?ver=1&imei=864394101849794";
    public static final String NEWS_LIST = "http://118.244.212.82:9092/newsClient/news_list?ver=1dsf&subid=1&dir=1&nid=0&stamp=20160828&cnt=20";
    /**
     * 标题列表
     */
    public static final String NEWS_SORT = ServerIP + "news_sort?";
    /**
     * 用户登录界面
     */
    public static final String USER_LOGIN = ServerIP + "user_login?";
    /**
     * 用户注册
     */
    public static final String USER_Register = ServerIP + "user_register?";
    /**
     * 用户中心数据
     */
    public static final String USER_CENTER_DATA = ServerIP + "user_home?";
    /**
     * 用户评论数目
     */
    public static final String USER_COMMENT = ServerIP + "cmt_num?";
    /**
     * 评论列表
     */
    public static final String USER_COMMENT_LIST=ServerIP+"cmt_list?";
    /**
     * 评论内容
     */
    public static final String USER_COMMENT_CONTENT=ServerIP+"cmt_commit?";
}
