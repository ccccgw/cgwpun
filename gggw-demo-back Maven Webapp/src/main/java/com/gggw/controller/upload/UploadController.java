package com.gggw.controller.upload;

import com.gggw.controller.base.BaseController;
import com.gggw.util.QiniuUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * TODO
 *
 * @author Cui.GaoWei
 * @date 2017/3/19
 */
@Controller
@RequestMapping("upload")
public class UploadController extends BaseController {

    @RequestMapping(value="toImgUpload")
    public ModelAndView toImgUpload()throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ui/backend/upload/imgUploadManager");
        return modelAndView;
    }

    @RequestMapping(value="toImgEdit")
    public ModelAndView toImgEdit()throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ui/backend/upload/imgUploadEdit");
        return modelAndView;
    }

    @RequestMapping("getToken")
    @ResponseBody
    public Object getToken(String bucketname) throws Exception{
        return QiniuUtil.getTokenMap(bucketname);
    }
}
