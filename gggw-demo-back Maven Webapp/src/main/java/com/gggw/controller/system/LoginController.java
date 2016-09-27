/**
 * Project Name:gggw-demo-backend Maven Webapp
 * File Name:LoginController.java
 * Package Name:com.gggw.controller.system
 * Date:2016-6-24下午2:35:29
 * Copyright (c) 2016, 502269006@qq.com All Rights Reserved.
 *
*/

package com.gggw.controller.system;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.gggw.util.FastJsonUtil;
import com.gggw.util.PageData;
import com.gggw.controller.base.BaseController;
import com.gggw.core.factory.impl.CounterServiceFactory;
import com.gggw.service.counter.service.CounterService0002;
import com.gggw.service.system.SysUserService;

/**
 * ClassName:LoginController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-6-24 下午2:35:29 <br/>
 * @author   cgw
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */

@Controller
public class LoginController extends BaseController{
	
	@Resource(name="sysUserService")
	private SysUserService sysUserService;
	
	@Autowired
	private CounterServiceFactory counterFactory;
	/**
	 * 请求登录，验证用户
	 */
	@RequestMapping(value="/login_login")
	@ResponseBody
	public Object login(HttpServletRequest request)throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = sysUserService.findByUserNo(pd);
		//System.out.println(getRequestPostStr(request));
		map.put("error_no", "0");
		counterFactory.excute("cccgw", null, CounterService0002.class);
		
		return FastJsonUtil.toJSONString(map);
	}
	
	  /**       
     * 描述:获取 post 请求内容 
     * <pre> 
     * 举例： 
     * </pre> 
     * @param request 
     * @return 
     * @throws IOException       
     */  
    public static String getRequestPostStr(HttpServletRequest request)  
            throws IOException {  
        byte buffer[] = getRequestPostBytes(request);  
        String charEncoding = request.getCharacterEncoding();  
        if (charEncoding == null) {  
            charEncoding = "UTF-8";  
        }  
        return new String(buffer, charEncoding);  
    }  

    /**       
     * 描述:获取 post 请求的 byte[] 数组 
     * <pre> 
     * 举例： 
     * </pre> 
     * @param request 
     * @return 
     * @throws IOException       
     */  
    public static byte[] getRequestPostBytes(HttpServletRequest request)  
            throws IOException {  
        int contentLength = request.getContentLength();  
        if(contentLength<0){  
            return null;  
        }  
        byte buffer[] = new byte[contentLength];  
        for (int i = 0; i < contentLength;) {  
  
            int readlen = request.getInputStream().read(buffer, i,  
                    contentLength - i);  
            if (readlen == -1) {  
                break;  
            }  
            i += readlen;  
        }  
        return buffer;  
    }  
}

