package com.action.login;

import ToolClass.EncryptionAlgorithm.AESOperator;
import ToolClass.EncryptionAlgorithm.Base64;
import ToolClass.JSON.Transformations_JSON;
import ToolClass.Verify_HTTP;
import com.entity.T_user;
import com.sqlConnect.dml.T_userDML;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @program: WebUploader
 * @description: 登陆执行类
 * @author: Dai Yuanchuan
 * @create: 2018-07-06 00:14
 **/
@Controller
@RequestMapping(value = "/login")
public class Login_Action {


    /**
     * 执行用户登陆
     *
     * @return
     */
    @RequestMapping(value = "/user_login", method = RequestMethod.POST)
    public @ResponseBody
    net.sf.json.JSONArray Login_Action(String UserName, String Password, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        // 验证链接
        if(new Verify_HTTP().Islegal(request)){
            // 匹配用户名
            String UserName_IsEmpty = new T_userDML().UserName_IsEmpty(UserName);
            if(UserName_IsEmpty!=null){
                // 匹配用户密码
                List<T_user> t_users = new T_userDML().Userlogin(UserName,Password);
                if(t_users.isEmpty()){
                    // 用户名与密码不匹配
                    return new Transformations_JSON()
                            .String_Transformations_JSON("[{\"errMsg\":\"Mismatch between username and password\"}]");
                }else{
                    // 获取当前时间的毫秒数
                    String time = String.valueOf(new ToolClass.Time.GetTodayTime().DateConversion_to_Milliseconds(new ToolClass.Time.GetTodayTime().GetNetworkTodayTime("yyyy-MM-dd hh:mm:ss"),"yyyy-MM-dd hh:mm:ss"));
                    for(T_user t_user : t_users){
                        t_user.setTime(time);
                    }
                    // 加密算法: 用AES加密原始数据后用Base64二次加密
                    return new Transformations_JSON()
                            .String_Transformations_JSON("[{\"errMsg\":\"OK\",\"token\":\"" + new Base64().encode(new AESOperator().getInstance().encrypt(String.valueOf(new Transformations_JSON().List_Transformations_JSON(t_users)))) + "\",\"time\":\""+time+"\"}]");
                }
            }else{
                // 用户名不存在
                return new Transformations_JSON()
                        .String_Transformations_JSON("[{\"errMsg\":\"User name does not exist\"}]");
            }
        }else{
            // 链接非法
            return new Transformations_JSON()
                    .String_Transformations_JSON("[{\"errMsg\":\"Current connection is unlawful\"}]");
        }
    }
}