package com.action.ImgList;

import ToolClass.JSON.JSON_Transformations_String;
import ToolClass.JSON.Transformations_JSON;
import ToolClass.Paging.PageBean;
import ToolClass.Token.TokenDecryption;
import ToolClass.Verify_HTTP;
import com.action.Uploader.Uploader;
import com.entity.T_imginfo;
import com.entity.T_imglist;
import com.sqlConnect.dml.T_imginfoDml;
import com.sqlConnect.dml.T_imglistDml;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: WebUploader
 * @description: 取出图片集
 * @author: Dai Yuanchuan
 * @create: 2018-07-09 17:40
 **/
@Controller
@RequestMapping(value = "/Img")
public class ImgList {

    /**
     * 图片集总数
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/TotalNum",method=RequestMethod.POST)
    public @ResponseBody net.sf.json.JSONArray TotalNum(String name,String time,String token,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        // 验证连接
        if(new Verify_HTTP().Islegal(request)){
            // 验证token
            if (token != "") {
                String JSON = new TokenDecryption().Decryption(token);
                // token解析成功
                if (new JSON_Transformations_String().Transformations_String(JSON.substring(1, JSON.length()).substring(0, JSON.substring(1, JSON.length()).length() - 1), "user_name").equals(name)&&new JSON_Transformations_String().Transformations_String(JSON.substring(1, JSON.length()).substring(0, JSON.substring(1, JSON.length()).length() - 1), "time").equals(time)) {
                    return new Transformations_JSON()
                            .String_Transformations_JSON("[{\"errMsg\":\"OK\",\"totalNum\":"+new T_imglistDml().TotalNum("")+"}]");
                } else {
                    // 匹配失败
                    return new Transformations_JSON()
                            .String_Transformations_JSON("[{\"errMsg\":\"Token failure\"}]");
                }
            }else{
                // token 已失效
                return new Transformations_JSON()
                        .String_Transformations_JSON("[{\"errMsg\":\"Invalid token\"}]");
            }
        }else{
            // 链接非法
            return new Transformations_JSON()
                    .String_Transformations_JSON("[{\"errMsg\":\"Current connection is unlawful\"}]");
        }
    }

    /**
     * 分页查询所有图片集
     * @param name
     * @param time
     * @param token
     * @param Page
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/ImgList",method=RequestMethod.POST)
    public @ResponseBody net.sf.json.JSONArray ImgList(String name,String time,String token,String Page,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        // 验证连接
        if(new Verify_HTTP().Islegal(request)){

            if (token != "") {
                String JSON = new TokenDecryption().Decryption(token);
                // token解析成功
                if (new JSON_Transformations_String().Transformations_String(JSON.substring(1, JSON.length()).substring(0, JSON.substring(1, JSON.length()).length() - 1), "user_name").equals(name)&&new JSON_Transformations_String().Transformations_String(JSON.substring(1, JSON.length()).substring(0, JSON.substring(1, JSON.length()).length() - 1), "time").equals(time)) {

                    try {
                        Integer.valueOf(Page);
                        if("".equals(Page)|| Page==null){
                            Page = "1";
                        }
                        PageBean PageBean = new PageBean(Integer.parseInt(Page),30);
                        List<T_imglist> t_imglists = new T_imglistDml().ImgList(new ToolClass.Paging.PageData().PangingSQL(PageBean));
                        return new Transformations_JSON()
                                .String_Transformations_JSON("[{\"errMsg\":\"OK\",\"ImgList\":"+new Transformations_JSON().List_Transformations_JSON(t_imglists)+",\"ListSize\":"+t_imglists.size()+",\"cover\":"+new Transformations_JSON().List_Transformations_JSON(new T_imginfoDml().Picture_Cover(new ToolClass.Paging.PageData().PangingSQL(PageBean)))+"}]");
                    } catch (Exception e) {
                        e.printStackTrace();
                        return new Transformations_JSON().String_Transformations_JSON("[{\"errMsg\":\"The number of pages is positive integer\"}]");
                    }

                } else {
                    // 匹配失败
                    return new Transformations_JSON()
                            .String_Transformations_JSON("[{\"errMsg\":\"Token failure\"}]");
                }
            }else{
                // token 已失效
                return new Transformations_JSON()
                        .String_Transformations_JSON("[{\"errMsg\":\"Invalid token\"}]");
            }

        }else{
            // 链接非法
            return new Transformations_JSON()
                    .String_Transformations_JSON("[{\"errMsg\":\"Current connection is unlawful\"}]");
        }
    }

    /**
     * 删除图片集
     * @param JSON
     * @param name
     * @param time
     * @param token
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/delImgList",method=RequestMethod.POST)
    public @ResponseBody net.sf.json.JSONArray DelImgList(String JSON,String name,String time,String token,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        // 验证连接
        if(new Verify_HTTP().Islegal(request)){
            String str = "";
            // 删除集合
            for (Object jsonArray : new Transformations_JSON().String_Transformations_JSON(new JSON_Transformations_String().JSONArray_Transformations_String(JSON,"ImgListID",0,"ImgListID"))){
                // 删除文件
                for(T_imginfo t_imginfo : new T_imginfoDml().According_toImgListID_Inquire(String.valueOf(jsonArray))){
                    if(!new ToolClass.FileDel.DeleteFiles().delFile(new Uploader().getTempFilePath(request)+"\\"+t_imginfo.getImg_address().split("/")[2])){
                        str = str + jsonArray + ",";
                    }
                }
                // 删除记录
                if(!new T_imglistDml().Delete_ImgList(String.valueOf(jsonArray))){
                    str = str + jsonArray + ",";
                }
            }
            if(str==""||str.length()==0){
                return new Transformations_JSON()
                        .String_Transformations_JSON("[{\"errMsg\":\"OK\"}]");
            }else {
                return new Transformations_JSON()
                        .String_Transformations_JSON("[{\"errMsg\":\"image listID "+str+" delete failure\"}]");
            }
        }else{
            // 链接非法
            return new Transformations_JSON()
                    .String_Transformations_JSON("[{\"errMsg\":\"Current connection is unlawful\"}]");
        }
    }


    /**
     * 搜索的数量
     * @param Keyword
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/Number_of_results",method=RequestMethod.POST)
    public @ResponseBody net.sf.json.JSONArray Number_of_results(String Keyword,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        // 验证连接
        if(new Verify_HTTP().Islegal(request)){
            return new Transformations_JSON()
                    .String_Transformations_JSON("[{\"errMsg\":\"OK\",\"Size\":"+new T_imglistDml().Number_of_results(Keyword)+"}]");
        }else{
            // 链接非法
            return new Transformations_JSON()
                    .String_Transformations_JSON("[{\"errMsg\":\"Current connection is unlawful\"}]");
        }
    }

    /**
     * 取出搜索结果
     * @param Keyword
     * @param Page
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/SearchResult",method=RequestMethod.POST)
    public @ResponseBody net.sf.json.JSONArray SearchResult(String Keyword,String Page,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        if(new Verify_HTTP().Islegal(request)){

            try {
                Integer.valueOf(Page);
                if("".equals(Page)|| Page==null){
                    Page = "1";
                }
                PageBean PageBean = new PageBean(Integer.parseInt(Page),30);
                List<T_imglist> t_imglists = new T_imglistDml().SearchResult(Keyword,new ToolClass.Paging.PageData().PangingSQL(PageBean));
                List<T_imginfo> t_imginfos = new ArrayList<>();
                for(T_imglist t_imglist : t_imglists){
                    for (T_imginfo t_imginfo:new T_imginfoDml().According_toImgListID_InquirePicture_Cover(String.valueOf(t_imglist.getList_id()))){
                        t_imginfos.add(t_imginfo);
                    }
                }
                return new Transformations_JSON()
                        .String_Transformations_JSON("[{\"errMsg\":\"OK\",\"Search\":"+new Transformations_JSON().List_Transformations_JSON(t_imglists)+",\"Size\":"+t_imglists.size()+",\"cover\":"+new Transformations_JSON().List_Transformations_JSON(t_imginfos)+"}]");
            } catch (Exception e) {
                e.printStackTrace();
                return new Transformations_JSON().String_Transformations_JSON("[{\"errMsg\":\"The number of pages is positive integer\"}]");
            }
        }else {
            return new Transformations_JSON()
                    .String_Transformations_JSON("[{\"errMsg\":\"Current connection is unlawful\"}]");
        }
    }
}
