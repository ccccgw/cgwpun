package com.gggw.core.user;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.gggw.core.utils.CookieUtil;
import com.gggw.entity.system.BaseSysUser;

/**
 * ClassName:iuser自定义参数自动注入 <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2016-11-29 下午6:09:37 <br/>
 * @author   cgw 
 */
@Component("userArgumentResolver")
public class UserArgumentResolver implements HandlerMethodArgumentResolver{
	
	@Override
	public boolean supportsParameter(MethodParameter paramMethodParameter) {
		return BaseSysUser.class.isAssignableFrom(paramMethodParameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter paramMethodParameter, ModelAndViewContainer paramModelAndViewContainer, NativeWebRequest paramNativeWebRequest,
			WebDataBinderFactory paramWebDataBinderFactory) throws Exception {
		BaseSysUser user = (BaseSysUser)paramNativeWebRequest.getAttribute(CookieUtil.GGGW_USER_SESSION_ID, RequestAttributes.SCOPE_REQUEST);
		return user;
	}
}

