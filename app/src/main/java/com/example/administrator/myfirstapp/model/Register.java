package com.example.administrator.myfirstapp.model;

/**
 * 注册状态
 * Created by Administrator on 2016/9/8 0008.
 */
public class Register {

    private int result;
    private String explain;
    private String token;

    public Register(int result, String explan, String token) {
        this.result = result;
        this.explain = explan;
        this.token = token;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplan(String explan) {
        this.explain = explan;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Register{" +
                "result=" + result +
                ", explan='" + explain + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
