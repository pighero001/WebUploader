package com.entity;

/**
 * @program: WebUploader
 * @description: 用户
 * @author: Dai Yuanchuan
 * @create: 2018-07-05 17:58
 **/
public class T_user {

    private int User_id;// 用户ID
    private String User_name;// 用户名
    private String User_password;//用户密码
    private String Time;//时间戳

    public T_user() {
    }

    public T_user(String user_name, String user_password) {
        User_name = user_name;
        User_password = user_password;
    }

    public T_user(int user_id, String user_name, String user_password) {
        User_id = user_id;
        User_name = user_name;
        User_password = user_password;
    }

    public int getUser_id() {
        return User_id;
    }

    public void setUser_id(int user_id) {
        User_id = user_id;
    }

    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public String getUser_password() {
        return User_password;
    }

    public void setUser_password(String user_password) {
        User_password = user_password;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
