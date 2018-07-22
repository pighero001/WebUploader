package com.action.Jump;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @program: WebUploader
 * @description: 跳转页面用
 * @author: Dai Yuanchuan
 * @create: 2018-07-06 16:40
 **/
@Controller
@RequestMapping(value = "/Jump")
public class JumpPage {

    @RequestMapping("/Uploader")
    public ModelAndView Uploader(){
        return new ModelAndView("/Uploader/Uploader");
    }

    @RequestMapping("/List")
    public ModelAndView ImgList(){
        return new ModelAndView("/ImgList/ImgList");
    }




}
