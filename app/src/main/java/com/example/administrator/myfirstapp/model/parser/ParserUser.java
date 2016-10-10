package com.example.administrator.myfirstapp.model.parser;

import com.example.administrator.myfirstapp.model.BaseEntry;
import com.example.administrator.myfirstapp.model.Comit;
import com.example.administrator.myfirstapp.model.FindPSW;
import com.example.administrator.myfirstapp.model.Register;
import com.example.administrator.myfirstapp.model.Update;
import com.example.administrator.myfirstapp.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class ParserUser {

    /**
     * 解析用户注册信息
     *
     * @param json
     * @return
     */
    public static BaseEntry<Register> getRegisterInfo(String json) {
        Gson gson = new Gson();
        BaseEntry<Register> entry = gson.fromJson(json, new TypeToken<BaseEntry<Register>>() {
        }.getType());
        return entry;
    }

    /**
     * 解析登录成功的用户信息
     *
     * @param json
     * @return
     */
    public static BaseEntry<User> getLoginInfo(String json) {
        Gson gson = new Gson();
        BaseEntry<User> userBaseEntry = gson.fromJson(json, new TypeToken<BaseEntry<User>>() {
        }.getType());
        return userBaseEntry;
    }

    public static BaseEntry<Comit> getComit(String json) {
        Gson gson = new Gson();
        BaseEntry<Comit> userComit = gson.fromJson(json, new TypeToken<BaseEntry<Comit>>() {
        }.getType());
        return userComit;
    }

    /**
     * 解析版本信息
     *
     * @param json
     * @return
     */
    public static BaseEntry<Update> getUpdate(String json) {
        Gson gson = new Gson();
        BaseEntry<Update> userUpdate = gson.fromJson(json, new TypeToken<BaseEntry<Update>>() {
        }.getType());
        return userUpdate;
    }

    public static BaseEntry<FindPSW> getFind(String json) {
        Gson gson = new Gson();
        BaseEntry<FindPSW> findPSW = gson.fromJson(json, new TypeToken<BaseEntry<FindPSW>>() {
        }.getType());
        return findPSW;
    }

}
