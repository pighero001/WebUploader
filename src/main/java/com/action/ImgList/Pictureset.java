package com.action.ImgList;

import ToolClass.JSON.Transformations_JSON;
import ToolClass.Verify_HTTP;
import com.sqlConnect.dml.T_imginfoDml;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @program: WebUploader
 * @description: 取出图片地址
 * @author: Dai Yuanchuan
 * @create: 2018-07-11 11:38
 **/
@Controller
@RequestMapping(value = "/Pictureset")
public class Pictureset {

    /**
     * 获取图片集对应的图片地址
     * @param ImgListID
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/PictureAddress",method=RequestMethod.POST)
    public @ResponseBody
    net.sf.json.JSONArray PictureAddress(String ImgListID, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        // 验证连接
        if(new Verify_HTTP().Islegal(request)){
           return new Transformations_JSON().List_Transformations_JSON(new T_imginfoDml().According_toImgListID_Inquire(ImgListID));

        }else{
            // 链接非法
            return new Transformations_JSON()
                    .String_Transformations_JSON("[{\"errMsg\":\"Current connection is unlawful\"}]");
        }
    }
}
