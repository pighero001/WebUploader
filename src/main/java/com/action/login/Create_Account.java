package com.action.login;

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

/**
 * @program: WebUploader
 * @description: 注册账号
 * @author: Dai Yuanchuan
 * @create: 2018-07-09 11:22
 **/
@Controller
@RequestMapping(value = "/register")
public class Create_Account {


    /**
     * 注册一个账号
     * @param user_name
     * @param user_password
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/Creating_User",method=RequestMethod.POST)
    public @ResponseBody
    net.sf.json.JSONArray Create_Account_Action(String user_name,String user_password,HttpServletRequest request, HttpServletResponse response){

        // 验证 接口
        if(new Verify_HTTP().Islegal(request)){
            if((user_name!=""&&user_password!="")&&(user_name!=null&&user_password!=null)){
                // 匹配用户名
                if(new T_userDML().UserName_IsEmpty(user_name)==null){
                    // 创建用户
                    if(new T_userDML().Create_Account(new T_user(user_name,user_password))){
                        return new Transformations_JSON()
                                .String_Transformations_JSON("[{\"errMsg\":\"OK\"}]");
                    }else{
                        // 创建用户失败
                        return new Transformations_JSON()
                                .String_Transformations_JSON("[{\"errMsg\":\"Create user failure\"}]");
                    }
                }else{
                    // 用户名存在
                    return new Transformations_JSON()
                            .String_Transformations_JSON("[{\"errMsg\":\"The username has already existed\"}]");
                }
            }else{
                // Username is illegal
                return new Transformations_JSON()
                        .String_Transformations_JSON("[{\"errMsg\":\"Username is illegal\"}]");
            }
        }else{
            // 链接非法
            return new Transformations_JSON()
                    .String_Transformations_JSON("[{\"errMsg\":\"Current connection is unlawful\"}]");
        }
    }
}
