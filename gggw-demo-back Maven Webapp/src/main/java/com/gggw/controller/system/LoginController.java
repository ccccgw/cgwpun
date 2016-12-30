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
import com.gggw.result.SisapResult;
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
import com.gggw.core.utils.RequestUtil;
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
	 * 主页A
	 */
	@RequestMapping(value="toHomeA")
	public ModelAndView toHomeA(BaseSysUser user, HttpServletRequest request, HttpServletResponse response)throws Exception{
		user = getUser();
		ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("menuHtml", getMenuList(user));
        modelAndView.setViewName("ui/backend/index");
		return modelAndView;
	}
	
	/**
	 * 登录页A
	 */
	@NoLogin
	@RequestMapping(value="toLoginA")
	public ModelAndView toLoginA(HttpServletRequest request, HttpServletResponse response)throws Exception{
		ModelAndView modelAndView = new ModelAndView();	
		BaseSysUser user = getUser();
		//设置sessionId
		String sessionId= CookieUtil.getCookie(request, CookieUtil.COOKIE_GGGW_SESSION_ID);
		if (null == sessionId) {
			sessionId = get32UUID();
			CookieUtil.setCookie(response, CookieUtil.COOKIE_GGGW_SESSION_ID, sessionId, true);
		}	
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

	//=========================================  ajaxFunction  start  ===========================================//
	
	/**
	 * 请求登录，验证用户
	 */
	@NoLogin
	@RequestMapping(value="ajaxSubmitLogin")
	@ResponseBody
	public Object ajaxSubmitLogin(BaseSysUser loginUser, HttpServletRequest request, HttpServletResponse response) throws Exception{
		SisapResult sisapResult = new SisapResult("0", "登录成功!");
		String ipAddress = RequestUtil.getIpAddress(request);
		PageData pd = this.getPageData();
		Boolean verifyCodeFlag = verifyCodeService.checkVerifycode(pd.getString("verifyCode"), CookieUtil.getCookie(request, CookieUtil.COOKIE_GGGW_SESSION_ID));
		if (verifyCodeFlag) {
			BaseSysUser baseSysUser = sysUserService.checkLogin(loginUser);
			if (null != baseSysUser) {
				//problem_ 状态判断最好用switch的形式  这里需要用到字典
				if ( sysUserService.checkStatus(baseSysUser) ) {
					baseSysUser.setLoginIps(ipAddress);
					sysUserService.updateUser(baseSysUser);
					request.setAttribute(CookieUtil.GGGW_USER_SESSION_ID, baseSysUser);
					//problem_ why 这里需要再写一遍不然会出错。 
					CookieUtil.writeObject(request, response, CookieUtil.GGGW_USER_SESSION_ID, baseSysUser);
				} else {
					sisapResult.setError_no("3");
					sisapResult.setError_info("用户非正常状态");
				}				
			} else {
				sisapResult.setError_no("2");
				sisapResult.setError_info("用户名密码错误!请重新输入。");
			}
		} else {
			sisapResult.setError_no("1");
			sisapResult.setError_info("验证码错误!");
		}
		
		return sisapResult;
	}
    
	//=========================================  ajaxFunction  end  =============================================//
	
	
	/**
	 * 菜单资源获取
	 */
	public Object getMenuList(BaseSysUser user){
        StringBuilder menuHtml = new StringBuilder();
		try {
			menuHtml.append(sysRoleCache.getMenu(user.getRoleId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menuHtml.toString();
	}
}

