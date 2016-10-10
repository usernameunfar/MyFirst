package com.example.administrator.myfirstapp.model;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class CommentNumber {
    private int data;
    private String message;
    private int status;

    public CommentNumber(int data, String message, int status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CommentNumber{" +
                "data=" + data +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
