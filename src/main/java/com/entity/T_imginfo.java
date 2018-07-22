package com.entity;

/**
 * @program: WebUploader
 * @description: 图片信息实体类
 * @author: Dai Yuanchuan
 * @create: 2018-07-05 17:39
 **/
public class T_imginfo {

    private int Img_id;// 图片ID
    private String Img_address;// 图片地址
    private String Img_list;// 关联的图片集

    public T_imginfo() {
        super();
    }

    public T_imginfo(int img_id, String img_address, String img_list) {
        super();
        Img_id = img_id;
        Img_address = img_address;
        Img_list = img_list;
    }

    public int getImg_id() {
        return Img_id;
    }

    public void setImg_id(int img_id) {
        Img_id = img_id;
    }

    public String getImg_address() {
        return Img_address;
    }

    public void setImg_address(String img_address) {
        Img_address = img_address;
    }

    public String getImg_list() {
        return Img_list;
    }

    public void setImg_list(String img_list) {
        Img_list = img_list;
    }
}
