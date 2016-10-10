package com.example.administrator.myfirstapp.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.myfirstapp.model.HomeNews;
import com.example.administrator.myfirstapp.model.NewsType;
import com.example.administrator.myfirstapp.model.SubType;
import com.example.administrator.myfirstapp.util.dbutil.DBManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/10 0010.
 */
public class DBTools {
    private Context context;
    private DBManager dbManager;
    private SQLiteDatabase sd;
    private HomeNews homeNews;
    private SubType subType;
    private NewsType newsType;

    public DBTools(Context context) {
        this.context = context;
        dbManager = new DBManager(context);
    }

    /**
     * 查询收藏列表中是否有当前要收藏的新闻
     *
     * @param news
     * @return
     */
    public boolean saveLoadFavorite(HomeNews news) {
        sd = dbManager.getReadableDatabase();
        String sql = "select nid from " + DBManager.NEWSFAVORITE_NAME + " where nid = ?";
        Cursor c = sd.rawQuery(sql, new String[]{news.getNid() + ""});
        if (c.moveToNext()) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", news.getType());
        contentValues.put("nid", news.getNid());
        contentValues.put("link", news.getLink());
        contentValues.put("title", news.getTitle());
        contentValues.put("stamp", news.getStamp());
        contentValues.put("icon", news.getIcon());
        contentValues.put("summary", news.getSummary());
        sd.insert(DBManager.NEWSFAVORITE_NAME, null, contentValues);
        return true;
    }

    /**
     * 查询所有收藏新闻
     *
     * @return
     */
    public List<HomeNews> getLocalFavorite() {
        List<HomeNews> listNews = new ArrayList<HomeNews>();
        sd = dbManager.getReadableDatabase();
        Cursor c = sd.query(DBManager.NEWSFAVORITE_NAME, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                int type = c.getInt(c.getColumnIndex("type"));
                int nid = c.getInt(c.getColumnIndex("nid"));
                String link = c.getString(c.getColumnIndex("link"));
                String title = c.getString(c.getColumnIndex("title"));
                String stamp = c.getString(c.getColumnIndex("stamp"));
                String icon = c.getString(c.getColumnIndex("icon"));
                String summary = c.getString(c.getColumnIndex("summary"));
                homeNews = new HomeNews(summary, icon, stamp, title, link, nid, type);
                listNews.add(homeNews);
            } while (c.moveToNext());
        }
        return listNews;
    }

    /**
     * 查询并判断Subtype中的数据
     *
     * @param subNews
     * @return
     */
    public boolean saveLocalSubType(SubType subNews) {
        sd = dbManager.getReadableDatabase();
        String sql = "select subid from " + DBManager.NEWSTYPE_NAME + " where subid = ?";
        Cursor c = sd.rawQuery(sql, new String[]{subNews.getSubid() + ""});
        if (c.moveToNext()) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("subgroup", subNews.getSubgroup());
        contentValues.put("subid", subNews.getSubid());
        sd.insert(DBManager.NEWSTYPE_NAME, null, contentValues);
        return true;
    }

    /**
     * 获得Subtype中的数据
     *
     * @return
     */
    public List<SubType> getLocalSubType() {
        List<SubType> listNews = new ArrayList<SubType>();
        sd = dbManager.getReadableDatabase();
        Cursor c = sd.query(DBManager.NEWSTYPE_NAME, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                int subid = c.getInt(c.getColumnIndex("subid"));
                String subgroup = c.getString(c.getColumnIndex("subgroup"));
                subType = new SubType(subgroup, subid);
                listNews.add(subType);
            } while (c.moveToNext());
        }
        return listNews;
    }

    /**
     * 查询并判断HomeNews中的数据
     *
     * @param homeNews
     * @return
     */
    public boolean saveLocalHomeNews(HomeNews homeNews) {
        sd = dbManager.getReadableDatabase();
        String sql = "select nid from " + DBManager.HOMENEWS_NAME + " where nid = ?";
        Cursor c = sd.rawQuery(sql, new String[]{homeNews.getNid() + ""});
        if (c.moveToNext()) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", homeNews.getType());
        contentValues.put("nid", homeNews.getNid());
        contentValues.put("link", homeNews.getLink());
        contentValues.put("title", homeNews.getTitle());
        contentValues.put("stamp", homeNews.getStamp());
        contentValues.put("icon", homeNews.getIcon());
        contentValues.put("summary", homeNews.getSummary());
        sd.insert(DBManager.HOMENEWS_NAME, null, contentValues);
        return true;
    }

    /**
     * 查询NewsType中的所有数据
     *
     * @return
     */
    public List<HomeNews> getLocalHomeNews() {
        List<HomeNews> listNews = new ArrayList<HomeNews>();
        sd = dbManager.getReadableDatabase();
        Cursor c = sd.query(DBManager.HOMENEWS_NAME, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                int type = c.getInt(c.getColumnIndex("type"));
                int nid = c.getInt(c.getColumnIndex("nid"));
                String link = c.getString(c.getColumnIndex("link"));
                String title = c.getString(c.getColumnIndex("title"));
                String stamp = c.getString(c.getColumnIndex("stamp"));
                String icon = c.getString(c.getColumnIndex("icon"));
                String summary = c.getString(c.getColumnIndex("summary"));
                homeNews = new HomeNews(summary, icon, stamp, title, link, nid, type);
                listNews.add(homeNews);
            } while (c.moveToNext());
        }
        return listNews;
    }
}
