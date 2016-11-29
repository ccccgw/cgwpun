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
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gggw.entity.system.BaseResource;
import com.gggw.service.system.SysResourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.gggw.util.PageData;
import com.gggw.util.jedis.RedisClientUtil;
import com.gggw.controller.base.BaseController;
import com.gggw.core.annotation.NoLogin;
import com.gggw.core.cache.SysRoleCache;
import com.gggw.core.factory.impl.CounterServiceFactory;
import com.gggw.core.utils.CookieUtil;
import com.gggw.core.utils.FastJsonUtil;
import com.gggw.counter.service.feature.CounterService0002;
import com.gggw.entity.system.BaseSysUser;
import com.gggw.service.system.SysUserService;
import com.gggw.system.service.IImageCodeService;
import org.springframework.web.servlet.ModelAndView;

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
	
	/**
	 * @Resource  
	 * 			默认使用 byName装配，也可使用 byType（@Resource(type="ISserService")） 
	 * 			等效于
	 * 				 @Autowired
	 *				 @Qualifier("sysUserService")
	 *			属于 JSR 250 (@Resource @PostConstruct @PreDestroy)
	 *
	 * @Autowired
	 * 			使用byType装配
	 * 		WARNNING： 当多个实现类实现统一接口时，抛出 "NoSuchBeanDefinitionException"异常
	 * 					
	 * 			
	 */
	@Resource(name="sysUserService")
	private SysUserService sysUserService;
	@Resource(name="sysResourceService")
	private SysResourceService sysResourceService;
	
	@Autowired
	private CounterServiceFactory counterFactory;
	@Autowired
	private IImageCodeService verifyCodeService;
	@Autowired
	private SysRoleCache sysRoleCache;
	
	/**
	 * 主页
	 */
	@NoLogin
	@RequestMapping(value="toHomeA")
	@ResponseBody
	public ModelAndView toHomeA(HttpServletRequest request, HttpServletResponse response)throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		//modelAndView.setViewName("ui/back/login");
        modelAndView.addObject("menuHtml", getMenuList());
        System.out.println(getMenuList());
        modelAndView.setViewName("ui/backend/index");
		return modelAndView;
	}
	
	/**
	 * 登录页
	 */
	@NoLogin
	@RequestMapping(value="toLoginA")
	@ResponseBody
	public ModelAndView toLoginA(HttpServletRequest request, HttpServletResponse response)throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ui/backend/login");
		return modelAndView;
	}

	/**
	 * 登录页
	 */
	@NoLogin
	@RequestMapping(value="toLogin")
	@ResponseBody
	public ModelAndView toLogin(HttpServletRequest request, HttpServletResponse response)throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ui/back/login");
		return modelAndView;
	}

	/**
	 * 请求登录，验证用户
	 */
	@RequestMapping(value="login_login")
	@ResponseBody
	public Object login(HttpServletRequest request, HttpServletResponse response)throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		//pd = sysUserService.findByUserNo(pd);
		//System.out.println(getRequestPostStr(request));
		map.put("error_no", "0");
		counterFactory.excute("cccgw", null, CounterService0002.class);
		
		CookieUtil.setCookie(response, CookieUtil.COOKIE_GGGW_SESSION_ID, UUID.randomUUID().toString(), true);
		
		return FastJsonUtil.toJSONString(pd);
	}
	
	/**
	 * 测试redis
	 */
	@RequestMapping(value="redisTest")
	@ResponseBody
	public Object redisTest(HttpServletRequest request)throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		RedisClientUtil.set(pd.getString("regist_user_email"), pd.getString("regist_user_no"));
		map.put("regist_user_no", RedisClientUtil.get("502269006@qq.com"));
		return FastJsonUtil.toJSONString(map);
	}
	
	/**
	 * 测试cookie
	 */
	@RequestMapping(value="cookieTest")
	@ResponseBody
	public Object cookieTest(HttpServletRequest request)throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String cookieString = CookieUtil.getCookie(request, CookieUtil.COOKIE_GGGW_SESSION_ID);
		map.put("cookie_string", cookieString);
		return FastJsonUtil.toJSONString(map);
	}
	
	/**
	 * 校验验证码
	 */
	@RequestMapping(value="checkCookie")
	@ResponseBody
	public Object checkCookie(HttpServletRequest request)throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		if (verifyCodeService.checkVerifycode(pd.getString("verify_code"), CookieUtil.getCookie(request, CookieUtil.COOKIE_GGGW_SESSION_ID))) {
			map.put("result_info", "正确");
		} else {
			map.put("result_info", "错误");
		}
		
		
		return FastJsonUtil.toJSONString(map);
	}
	
	/**
	 * 测试bean写入cookie
	 */
	@RequestMapping(value="setBeanCookie")
	@ResponseBody
	public Object setBeanCookie(HttpServletRequest request, HttpServletResponse response)throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		BaseSysUser baseSysUser = new BaseSysUser();
		baseSysUser.setUserName("setBeanCookie");
		baseSysUser.setUserNo("123123");
		baseSysUser.setUserPwd("ggggw");
		
		CookieUtil.writeObject(request, response, "Base_sys_user_session", baseSysUser);
		map.put("result_info", "已设置");
		return FastJsonUtil.toJSONString(map);
	}
	
	/**
	 * 测试cookie读取bean
	 */
	@RequestMapping(value="getBeanCookie")
	@ResponseBody
	public Object getBeanCookie(HttpServletRequest request, HttpServletResponse response)throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		BaseSysUser baseSysUser = (BaseSysUser) CookieUtil.readObject(request, "Base_sys_user_session", BaseSysUser.class);
		map.put("result_info", FastJsonUtil.toJSONString(baseSysUser));
		return FastJsonUtil.toJSONString(map);
	}
	
	
	/**
	 * 请求登录，验证用户
	 */
	@NoLogin
	@RequestMapping(value="toHome")
	public ModelAndView toHome(HttpServletRequest request, HttpServletResponse response)throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ui/back/included");
		return modelAndView;
	}

	/**
	 * 菜单资源获取
	 */
	@NoLogin
	@RequestMapping(value="getMenuList")
	public Object getMenuList(){
        StringBuilder menuHtml = new StringBuilder();
		try {
			menuHtml.append(sysRoleCache.getMenu("10000"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menuHtml.toString();
	}
	//=========================================  tool Functions  start  ===========================================//
	
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
    
	//=========================================  tool Functions  end  ===========================================//
}

