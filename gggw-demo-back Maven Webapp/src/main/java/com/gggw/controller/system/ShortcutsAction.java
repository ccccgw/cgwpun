package com.gggw.controller.system;

import java.io.*;
import java.net.URLEncoder;
import java.util.List;

import com.gggw.core.annotation.NoLogin;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gggw.controller.base.BaseController;
import com.gggw.entity.system.BaseDictionary;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName:ShortcutsAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2016-12-1 上午9:10:36 <br/>
 * @author   cgw 
 */
@Controller
public class ShortcutsAction extends BaseController{
	
	/**
	 * 进入用户管理页
	 */
	@RequestMapping(value="toShortcuts")
	public ModelAndView toShortcuts()throws Exception{
		ModelAndView modelAndView = new ModelAndView();			
		modelAndView.setViewName("ui/backend/system/shortcutsManager");
		return modelAndView;
	}

	@NoLogin
	@RequestMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		String fileName = "webapp.zip";

		//1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
		response.setContentType("multipart/form-data");
		//2.设置文件头
		String userAgent = request.getHeader("User-Agent");
		if (StringUtils.isBlank(userAgent)) {
			fileName = URLEncoder.encode(fileName, "UTF-8");
		} else {
			if (userAgent.indexOf("MSIE") != -1) {
				// IE使用URLEncoder
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} else {
				// FireFox使用ISO-8859-1
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}
		}
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));
		File file = null;
		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("win") >= 0) {
			file = new File("e://hunanFile//ceshi//webapp.zip");
		}
		else {
			file = new File("/usr/cgw/myfile/webapp.zip");
		}

		OutputStream out = null;
		InputStream inputStream = null;
		response.setContentLength((int)file.length());
		try {
			inputStream = new FileInputStream(file);
			out = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = inputStream.read(buffer)) != -1) {
				out.write(buffer, 0, count);
			}
			out.close();
			out.flush();
		} catch (IOException e) {
			logger.error("文件下载异常:{}", e);
		} finally {
			response.reset();
			IOUtils.closeQuietly(inputStream);
		}
	}

	@NoLogin
	@RequestMapping("/downloadFZ")
	public void downloadFZ(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		String fileName = "webapp.zip";

		//1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
		response.setContentType("multipart/form-data");
		//2.设置文件头
		String userAgent = request.getHeader("User-Agent");
		if (StringUtils.isBlank(userAgent)) {
			fileName = URLEncoder.encode(fileName, "UTF-8");
		} else {
			if (userAgent.indexOf("MSIE") != -1) {
				// IE使用URLEncoder
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} else {
				// FireFox使用ISO-8859-1
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}
		}
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));
		File file = null;
		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("win") >= 0) {
			file = new File("e://hunanFile//fangzhen//webapp.zip");
		}
		else {
			file = new File("/usr/cgw/myfile/fangzhen/webapp.zip");
		}

		OutputStream out = null;
		InputStream inputStream = null;
		response.setContentLength((int)file.length());
		try {
			inputStream = new FileInputStream(file);
			out = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = inputStream.read(buffer)) != -1) {
				out.write(buffer, 0, count);
			}
			out.close();
			out.flush();
		} catch (IOException e) {
			logger.error("文件下载异常:{}", e);
		} finally {
			response.reset();
			IOUtils.closeQuietly(inputStream);
		}
	}


	//==================================       ajaxFunction start          =====================================//
	/**
	 * ajax关闭计算机
	 */
	@RequestMapping(value="ajaxShutdown")
	public void ajaxShutdown()throws Exception{
		shutdown();
	}
	//==================================       ajaxFunction end            =====================================//
	
	
	/** 
     * 函数名:exec 
     * 简单描述:执行command命令 
     */  
    public void exec(String command) throws Exception{  
    	Runtime.getRuntime().exec(command);  
    }  
      
    /** 
     *关机  
     */  
    public void shutdown()  throws Exception{  
       exec("shutdown -S ");  
    }  
      
    /** 
 	 *重启
     */  
    public void restart() throws Exception {  
       exec("shutdown -R ");  
    }  
      
    /** 
     *注销   
     */  
    public void logout() throws Exception {  
       exec("shutdown -L ");  
    }


    //===========================================================    ToolFunction       =============================================//


}

