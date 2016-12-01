package com.gggw.interceptor;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import com.gggw.core.annotation.NoLogin;
import com.gggw.core.exception.BizException;
import com.gggw.core.utils.CookieUtil;
import com.gggw.entity.system.BaseSysUser;

/**
 * 
 * ClassName: LoginHandlerInterceptor <br/>
 * Function: 校验用户是否登录拦截器.使用cookie代替session以便集群. <br/>
 * 			 session:	服务器端保持状态
 * 			 cookie:    客户端保持状态
 * 			       		集群环境下，使用session,当另一台服务器接收请求时,会找不到session.
 * date: 2016-6-24 下午2:33:19 <br/>
 *
 * @author cgw
 * @version 
 * @since JDK 1.6
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter{

	/**
	 * 在业务处理器处理请求之前被调用 
	 * 		1.返回false 退出拦截器链
	 * 		2.返回true  执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
	 * 		3.抛出异常
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		
		/**
		 * 		登录拦截处理流程：
		 * 				1.读取cookie（USER_SESSION）, 把cookie转为User类
		 * 				2.request中设置属性  request.setAttribute(sessionName, user);
		 * 				3.判断是否为静态资源，如果是不做拦截
		 * 				4.如果有注解@NoLogin 不做拦截
		 * 				5.其他登录逻辑
		 */
		
		/** 静态资源请求 不做拦截*/
		if (handler.getClass().equals(ResourceHttpRequestHandler.class)) {
			return true;
		}
		
		/**
		 * 	1.不是  Controller、RestController不做拦截
		 *  2.NoLogin类 不做拦截
		 *  3.NoLogin方法不做拦截
		 */
		HandlerMethod method = (HandlerMethod)handler;
		if (!method.getBeanType().isAnnotationPresent(Controller.class) && !method.getBeanType().isAnnotationPresent(RestController.class)) {
			return true;
		}
		if (method.getBeanType().isAnnotationPresent(NoLogin.class)) {
			return true;
		}
		if (method.getMethodAnnotation(NoLogin.class) != null) {
			return true;
		}
		
		prepareSessionFromCookie(request);
			
		BaseSysUser user = (BaseSysUser)request.getAttribute(CookieUtil.GGGW_USER_SESSION_ID);
		if (user != null) {
			if(checkUser(user)){
				return true;
			}
		}
		
		System.out.println("LoginHandlerInterceptor  preHandle()===========登录拦截");
		
		//return true;
		throw new BizException("1", "拦截了");
	}
	
	
	/** 
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
		/**
		 * 1.读取request   user = request.getAttribute(sessionName);
		 * 2.把user写入cookie
		 */
		updateSessionToCookie(request, response);
		System.out.println("LoginHandlerInterceptor  postHandle()===========");
	}
	
	
	/**
	 * 在DispatcherServlet完全处理完请求后被调用
	 * 
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
		System.out.println("LoginHandlerInterceptor  afterCompletion()===========");
		
	}
	
	/**
	 * 解析request中的cookie，转化为session中的user
	 * @param request
	 * @throws Exception
	 */
	protected void prepareSessionFromCookie(HttpServletRequest request) throws Exception {
		String sessionName = CookieUtil.GGGW_USER_SESSION_ID;
		BaseSysUser user = (BaseSysUser) CookieUtil.readObject(request, sessionName, BaseSysUser.class);
		if (user == null) {
			user = new BaseSysUser();
		}
		request.setAttribute(sessionName, user);
	}
	
	/**
	 * 检查 user 是否有效
	 * @param user
	 * @return
	 */
	private boolean checkUser(BaseSysUser user) {
		if (user == null || StringUtils.isBlank(user.getUserNo())) {
			return false;
		}
		return true;
	}
	
	/**
	 * 更新session中的user到cookie
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	protected void updateSessionToCookie(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String sessionName = CookieUtil.GGGW_USER_SESSION_ID;
		BaseSysUser user = (BaseSysUser)request.getAttribute(sessionName);
		if (user != null && StringUtils.isNotEmpty(user.getUserNo())) {
			CookieUtil.writeObject(request, response, sessionName, user);
		}
	}
}
