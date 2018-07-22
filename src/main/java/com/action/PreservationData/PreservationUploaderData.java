package com.action.PreservationData;

import ToolClass.JSON.JSON_Transformations_String;
import ToolClass.JSON.Transformations_JSON;
import ToolClass.Token.TokenDecryption;
import ToolClass.Verify_HTTP;
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
 * @description: 保存上传数据
 * @author: Dai Yuanchuan
 * @create: 2018-07-06 22:42
 **/
@Controller
@RequestMapping(value = "/Preservation")
public class PreservationUploaderData {

    /**
     * @RequestBody
     * @param JSONData
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/UploaderData", method = RequestMethod.POST)
    public @ResponseBody net.sf.json.JSONArray UploaderData(String JSONData, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        // 验证连接
        if(new Verify_HTTP().Islegal(request)){
            String name = new JSON_Transformations_String().JSONArray_Transformations_String(JSONData,"fileInfo",0,"name");
            String JSON = new TokenDecryption().Decryption(new JSON_Transformations_String().JSONArray_Transformations_String(JSONData,"fileInfo",0,"token"));
            // 再次验证Token
            if (new JSON_Transformations_String().Transformations_String(JSON.substring(1, JSON.length()).substring(0, JSON.substring(1, JSON.length()).length() - 1), "user_name").equals(name) && new JSON_Transformations_String().Transformations_String(JSON.substring(1, JSON.length()).substring(0, JSON.substring(1, JSON.length()).length() - 1), "time").equals(new JSON_Transformations_String().JSONArray_Transformations_String(JSONData, "fileInfo", 0, "time"))) {
                // 解析JSON字符串
                System.out.println(new JSON_Transformations_String().JSONArray_Transformations_String(JSONData,"fileInfo",0,"title"));
                System.out.println(new JSON_Transformations_String().JSONArray_Transformations_String(JSONData,"fileInfo",0,"label"));
                System.out.println(new JSON_Transformations_String().JSONArray_Transformations_String(JSONData,"fileInfo",0,"address"));

                // 保存图片集
                T_imglist t_imglist = new T_imglist();
                t_imglist.setList_title(new JSON_Transformations_String().JSONArray_Transformations_String(JSONData,"fileInfo",0,"title"));
                t_imglist.setList_label(new JSON_Transformations_String().JSONArray_Transformations_String(JSONData,"fileInfo",0,"label"));
                t_imglist.setList_user(name);
                if(new T_imglistDml().Save_Picture_set(t_imglist)){
                    List<T_imginfo> t_imginfos = new ArrayList<>();
                    // 遍历图片地址信息
                    for (Object jsonArray : new Transformations_JSON().String_Transformations_JSON(new JSON_Transformations_String().JSONArray_Transformations_String(JSONData,"fileInfo",0,"address"))){
                        T_imginfo t_imginfo = new T_imginfo();
                        t_imginfo.setImg_address(String.valueOf(jsonArray));
                        t_imginfo.setImg_list(String.valueOf(t_imglist.getList_id()));
                        t_imginfos.add(t_imginfo);
                    }
                    // 保存图片info
                    if(new T_imginfoDml().Save_Pictureinfo(t_imginfos)){
                        // 保存成功
                        return new Transformations_JSON()
                                .String_Transformations_JSON("[{\"errMsg\":\"OK\"}]");
                    }else{
                        // 保存失败
                        return new Transformations_JSON()
                                .String_Transformations_JSON("[{\"errMsg\":\"Failure of picture information preservation\"}]");
                    }
                }else{
                    // 图片集保存失败
                    return new Transformations_JSON()
                            .String_Transformations_JSON("[{\"errMsg\":\"Picture set save failure\"}]");
                }
            }else{
                // Token 校验失败
                return new Transformations_JSON()
                        .String_Transformations_JSON("[{\"errMsg\":\"Token verification failure\"}]");
            }
        }else{
            // 链接非法
            return new Transformations_JSON()
                    .String_Transformations_JSON("[{\"errMsg\":\"Current connection is unlawful\"}]");
        }
    }
}
