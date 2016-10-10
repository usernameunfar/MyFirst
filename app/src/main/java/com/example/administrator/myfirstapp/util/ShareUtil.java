package com.example.administrator.myfirstapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.administrator.myfirstapp.model.BaseEntry;
import com.example.administrator.myfirstapp.model.Register;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class ShareUtil {
    private static final String SHARE_PATH = "app_share";
    private static final String SHARE_PATH_REGISTER = "register";


    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARE_PATH, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getDefaultSharedPreferences_register(Context context) {
        return context.getSharedPreferences(SHARE_PATH_REGISTER, Context.MODE_PRIVATE);
    }


    /**
     * 保存用户的注册信息
     *
     * @param baseRegister
     * @param context
     */
    public static void saveRegisterInfo(BaseEntry<Register> baseRegister, Context context) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences_register(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLogin", true);
        Register register = baseRegister.getData();
        editor.putInt("result", register.getResult());
        editor.putString("explain", register.getExplain());
        editor.putString("token", register.getToken());
        editor.commit();
    }

    /**
     * 判断用户是否登录
     *
     * @param context
     * @param key
     * @param defValue
     */
    public static boolean getIsLogined(Context context, String key, boolean defValue) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences_register(context);
        return sharedPreferences.getBoolean(key, defValue);
    }

    /**
     * 是否为第三方登录
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getIsOther(Context context, String key, boolean defValue) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences_register(context);
        return sharedPreferences.getBoolean(key, defValue);
    }

    /**
     * 保存用户登录后的信息
     *
     * @param context
     * @param key
     * @return
     */
    public static String getTokey(Context context, String key) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences_register(context);
        return sharedPreferences.getString(key, null);
    }


    public static void putInt(Context context, String key, int value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 设置第三方登录状态
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putBooleanOther(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences_register(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static int getInt(Context context, String key) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(key, 0);
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, null);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(key, defValue);

    }

    public static void clearData(Context context) {
        SharedPreferences dataBase = context.getSharedPreferences(SHARE_PATH_REGISTER, Activity.MODE_PRIVATE);
        putBooleanOther(context, "isOther", false);
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.removeAccount(true);
        dataBase.edit().clear().commit();
    }

}
