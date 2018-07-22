package com.action.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @program: WebUploader
 * @description: 定义错误类
 * @author: Dai Yuanchuan
 * @create: 2018-07-05 21:33
 **/
@Controller
public class Error {

    /**
     * 出错后返回的页面
     * @return
     */
    @RequestMapping("/Error-404")
    public ModelAndView Error(){
        return new ModelAndView("/error/404");
    }

}
