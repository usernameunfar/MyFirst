package com.example.administrator.myfirstapp.model.parser;

import android.util.Log;

import com.example.administrator.myfirstapp.model.BaseEntry;
import com.example.administrator.myfirstapp.model.CommentContent;
import com.example.administrator.myfirstapp.model.CommentNumber;
import com.example.administrator.myfirstapp.model.HomeNews;
import com.example.administrator.myfirstapp.model.NewsType;
import com.example.administrator.myfirstapp.model.SubType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
public class ParserNews {
    private static final String TAG = "ParserNews";

    /**
     * 解析新闻的类型
     *
     * @param json
     * @return
     */
    public static List<SubType> getNewsType(String json) {
        List<SubType> list = new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<BaseEntry<List<NewsType>>>() {
        }.getType();
        BaseEntry<List<NewsType>> entry = gson.fromJson(json, type);
        List<NewsType> litNewsType = entry.getData();
        for (int i = 0; i < litNewsType.size(); i++) {
            list.addAll(litNewsType.get(i).getSubList());
        }
        return list;
    }

    /**
     * 解析每条新闻的具体信息
     *
     * @param json
     * @return
     */
    public static List<HomeNews> getHomeNews(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<BaseEntry<List<HomeNews>>>() {
        }.getType();
        BaseEntry<List<HomeNews>> entry = gson.fromJson(json, type);
        List<HomeNews> listHomeNews = entry.getData();

        return listHomeNews;
    }

    /**
     * 解析每条新闻的评论数
     *
     * @param json
     * @return
     */
    public static CommentNumber getCommentNumber(String json) {
        Gson gson = new Gson();
        CommentNumber co = gson.fromJson(json, new TypeToken<CommentNumber>() {
        }.getType());
        Log.i(TAG, "getCommentNumber: ``````````````" + co);
        return co;
    }

    public static List<CommentContent> getCommentContent(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<BaseEntry<List<CommentContent>>>() {
        }.getType();
        BaseEntry<List<CommentContent>> entry = gson.fromJson(json, type);
        List<CommentContent> listComment = entry.getData();
        Log.i(TAG, "getCommentContent: `````````````" + listComment);
        return listComment;
    }


}
