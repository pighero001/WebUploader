package com.action.token;

import ToolClass.JSON.JSON_Transformations_String;
import ToolClass.JSON.Transformations_JSON;
import ToolClass.Token.TokenDecryption;
import ToolClass.Verify_HTTP;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @program: WebUploader
 * @description: 验证Token
 * @author: Dai Yuanchuan
 * @create: 2018-07-06 11:21
 **/
@Controller
@RequestMapping(value = "/token")
public class VerificationToken {

    /**
     * 验证Token 合法性
     *
     * @param token
     * @param name
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/Verification",method=RequestMethod.POST)
    public @ResponseBody
    net.sf.json.JSONArray Verification(String token, String name, String time, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        if (new Verify_HTTP().Islegal(request)) {
            if (token != null || token != "") {
                // Token解密后的JSON字符串
                String JSON = new TokenDecryption().Decryption(token);
                // 解析JSON字符串
                if (new JSON_Transformations_String().Transformations_String(JSON.substring(1, JSON.length()).substring(0, JSON.substring(1, JSON.length()).length() - 1), "errMsg").equals("Token decryption failure")) {
                    // token解析失败
                    return new Transformations_JSON()
                            .String_Transformations_JSON("[" + JSON + "]");
                } else {
                    // token解析成功
                    if (new JSON_Transformations_String().Transformations_String(JSON.substring(1, JSON.length()).substring(0, JSON.substring(1, JSON.length()).length() - 1), "user_name").equals(name)&&new JSON_Transformations_String().Transformations_String(JSON.substring(1, JSON.length()).substring(0, JSON.substring(1, JSON.length()).length() - 1), "time").equals(time)) {
                        // 匹配成功
                        return new Transformations_JSON()
                                .String_Transformations_JSON("[{\"errMsg\":\"OK\"}]");
                    } else {
                        // 匹配失败
                        return new Transformations_JSON()
                                .String_Transformations_JSON("[{\"errMsg\":\"Token failure\"}]");
                    }
                }
            } else {
                // token 已失效
                return new Transformations_JSON()
                        .String_Transformations_JSON("[{\"errMsg\":\"Invalid token\"}]");
            }
        } else {
            // 链接非法
            return new Transformations_JSON()
                    .String_Transformations_JSON("[{\"errMsg\":\"Current connection is unlawful\"}]");
        }
    }
}
