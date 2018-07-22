package com.entity;

/**
 * @program: WebUploader
 * @description: 图片集实体类
 * @author: Dai Yuanchuan
 * @create: 2018-07-05 17:50
 **/
public class T_imglist {

    private int List_id; // 集合ID
    private String List_title;// 集合标题
    private String List_label;// 集合标签
    private String List_user; // 创建集合的用户

    public T_imglist(int list_id, String list_title, String list_label, String list_user) {
        super();
        List_id = list_id;
        List_title = list_title;
        List_label = list_label;
        List_user = list_user;
    }

    public T_imglist() {
        super();
    }

    public int getList_id() {
        return List_id;
    }

    public void setList_id(int list_id) {
        List_id = list_id;
    }

    public String getList_title() {
        return List_title;
    }

    public void setList_title(String list_title) {
        List_title = list_title;
    }

    public String getList_label() {
        return List_label;
    }

    public void setList_label(String list_label) {
        List_label = list_label;
    }

    public String getList_user() {
        return List_user;
    }

    public void setList_user(String list_user) {
        List_user = list_user;
    }
}
