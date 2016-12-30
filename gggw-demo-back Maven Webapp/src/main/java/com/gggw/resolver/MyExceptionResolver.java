/**
 * Project Name:gggw-demo-backend Maven Webapp
 * File Name:MyExceptionResolver.java
 * Package Name:com.gggw.resolver
 * Date:2016-6-24下午12:26:21
 * Copyright (c) 2016, 502269006@qq.com All Rights Reserved.
 *
*/

package com.gggw.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.gggw.util.Logger;

/**
 * ClassName:MyExceptionResolver <br/>
 *             
 *             1.implements HandlerExceptionResolver
 *             2.extends AbstractHandlerExceptionResolver （implements HandlerExceptionResolver, Ordered） 
 *             
 * Date:     2016-6-24 下午12:26:21 <br/>
 * @author   cgw
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class MyExceptionResolver extends AbstractHandlerExceptionResolver{
	protected Logger logger = Logger.getLogger(this.getClass());

	@Override
	public ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		/**
		 * 	1.页面请求,返回错误页面
		 * 	2.json请求，返回MappingJackson2JsonView
		 */
		HandlerMethod method = (HandlerMethod)handler;
		ResponseBody anno = method.getMethodAnnotation(ResponseBody.class);
		RestController rest = method.getBeanType().getAnnotation(RestController.class);

		if (anno != null || rest != null) {
			MappingJackson2JsonView view = new MappingJackson2JsonView();
			view.addStaticAttribute("error_no", "-1");
			view.addStaticAttribute("error_info", "出错了");
			return new ModelAndView(view);
		}
		
		//记录错误日志。
		logger.error("Catch Exception: ",ex);
		ex.printStackTrace();		
		ModelAndView mv = new ModelAndView("ui/backend/login");
		//mv.addObject("exception", ex.toString().replaceAll("\n", "<br/>"));
		return mv;
	}

	//率先执行
	@Override
	public int getOrder() {
		return 0;
	}

}

