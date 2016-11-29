package com.gggw.core.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;


/**
 * ClassName:CookieUtil <br/>
 * Function: TODO excute cookie. <br/>
 * Date:     2016-10-11 下午5:32:14 <br/>
 * @author   cgw
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class CookieUtil {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(CookieUtil.class);
	
	public static final String COOKIE_GGGW_SESSION_ID = "GGGW_SESSION_ID";
	public static final String GGGW_USER_SESSION_ID = "USER_SESSION_ID";
	private static Map<String, Integer> cookies = null;
	
	/**
	 * 用来存储cookies存活时间, 单位:天
	 */
	static {
		cookies = new HashMap<String, Integer>();
		cookies.put(GGGW_USER_SESSION_ID, 2);
	}
	
	/**
	 * 功能说明: 设置cookie<br>
	 * 				response.addHeader("Set-Cookie", cookieContent.toString());的方式
	 * 系统版本: @version 1.0<br>
	 * 开发人员: @author cgw<br>
	 * 开发时间: 2016-10-11 下午9:18:17<br>
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, Boolean isRemmerber) {
		try {
			if (null == value) {
				value = "";
			}
			StringBuffer cookieContent = new StringBuffer();
			/**cookie Id */
			cookieContent.append(name);
			cookieContent.append("=");
			/**
			 * 		为了支持中文
			 * 			js中使用2次encodeURIComponent进行编码
			 * 				1.encodeURI() ： 对整个url进行编码
			 * 				2.encodeURIComponent() ： 对url中的而一部分进行编码
			 * 				3.因此，"; / ? : @ & = + $ , #"，这些在encodeURI()中不被编码的符号，
			 * 					  在encodeURIComponent()中统统会被编码。至于具体的编码方法，两者是一样。
			 *					  它对应的解码函数是decodeURIComponent()。
			 *
			 *			因为第一次编码，你的参数内容便不带有多字节字符了，成了纯粹的 Ascii 字符串。 [STR_ENC1] 
			 *			再编一次后，提交，接收时容器自动解一次（容器自动解的这一次，不管是按 GBK 还是 UTF-8 还是 ISO-8859-1 都好，都能够正确的得到 [STR_ENC1]）
			 *			然后，再在程序中实现一次 decodeURIComponent (Java中通常使用 java.net.URLDecoder(***, "UTF-8")) 就可以得到想提交的参数的原值。
			 */
			cookieContent.append(URLEncoder.encode(value, "UTF-8"));		
			cookieContent.append(";");
			/**cookie path */
			cookieContent.append("Path=/;");
			/**cookie domain */
			cookieContent.append("Domain=");
			cookieContent.append(PropertiesUtil.get("gggw.cookie.domain"));
			cookieContent.append(";");
			/**cookie Max-Age */
			if (cookies.containsKey(name)) {
				if (cookies.get(name) > 0 && isRemmerber) {
					cookieContent.append("Max-Age=");
					cookieContent.append(cookies.get(name) * 24 * 3600);
					cookieContent.append(";");
				}
			}
			/**防止客户端js 读取cookie值 */
			cookieContent.append("HTTPOnly");
			response.addHeader("Set-Cookie", cookieContent.toString());
			logger.info("Set-Cookie-->  " + cookieContent.toString());
		} catch (Exception e) {
			logger.error("CookieUtil setCookie is error!", e);
		}
	} 
	
	public static void setCookie2(HttpServletResponse response, String name, String value, Boolean isRemmerber) {
		try {
			if (null == value) {
				value = "";
			}
			/**cookie Id */
			Cookie cookie = new Cookie(name, URLEncoder.encode(value, "UTF-8"));
			/**
			 * cookie path 
			 * 		warning: 默认为根目录
			 * 				 也可设置/sys/  表示在www.baidu.com/sys 下面有效
			 */
			cookie.setPath("/");
			/**  cookie domain 
			 *      warning: 这里domain不能设置为"" 
			 *      		 127.0.0.1也不行
			 *      		  要么默认,要么填写域名  www.baidu.com 
			 *              
			 */
			cookie.setDomain(PropertiesUtil.get("gggw.cookie.domain"));
			/**cookie Max-Age */
			if (cookies.containsKey(name)) {
				if (cookies.get(name) > 0 && isRemmerber) {
					cookie.setMaxAge(cookies.get(name) * 24 * 3600);
				}
			}
			/**防止客户端js 读取cookie值      Only support from Servlet 3+ */
			cookie.setHttpOnly(true);
			response.addCookie(cookie);
		} catch (Exception e) {
			logger.error("CookieUtil setCookie2 is error!", e);
		}	
	}
	
	/**
	 * 功能说明: 获取cookie<br>
	 * 系统版本: @version 1.0<br>
	 * 开发人员: @author cgw<br>
	 * 开发时间: 2016-10-11 下午10:00:42<br>
	 */
	public static String getCookie(HttpServletRequest request, String name) {
		try {
			Cookie[] requestCookies = request.getCookies();
			if (requestCookies != null) {
				for (Cookie cookie : requestCookies) {
					if (name.equals(cookie.getName())) {
						//中文解码
						return URLDecoder.decode(cookie.getValue(), "UTF-8");
					}
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("CookieUtil getCookie is error!", e);
			return null;
		}
		return null;
	}
	
	/**
	 * 功能说明: 清除cookie<br>
	 * 系统版本: @version 1.0<br>
	 * 开发人员: @author cgw<br>
	 * 开发时间: 2016-10-11 下午10:04:25<br>
	 */
	public static void clearCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (StringUtils.equals(cookieName, cookie.getName())) {
					cookie.setMaxAge(0);
					cookie.setPath("/");
					// cookie.setValue(null)会被读取出来为"null"，所以这里要设置为空字符串
					cookie.setValue("");
					response.addCookie(cookie);
				}
			}
		}
	}
	
	/**
	 * 功能说明: 把对象写入到cookie中<br>
	 * 系统版本: @version 1.0<br>
	 * 开发人员: @author cgw<br>
	 * 开发时间: 2016-10-17 上午9:30:58<br>
	 */
	public static void writeObject(HttpServletRequest request, HttpServletResponse response, String key, Object bean) {
		try {
			Map<String, String> tokenMap = split2TokenMap(key, bean);
			
			// 写cookie
			if (tokenMap != null && !tokenMap.isEmpty()) {
				/**
				 * Iterator遍历				 
				Set<Map.Entry<String, String>> entryMap = tokenMap.entrySet();
				Iterator<Map.Entry<String, String>> it = entryMap.iterator();
				while (it.hasNext()) {
					Entry<String, String> entry = it.next();
					Cookie tmpCookie = new Cookie(entry.getKey(), entry.getValue());
					tmpCookie.setPath("/");
					tmpCookie.setHttpOnly(true);
					response.addCookie(tmpCookie);
				}*/
				
				for (Map.Entry<String, String> entry  : tokenMap.entrySet()) {
					Cookie tmpCookie = new Cookie(entry.getKey(), entry.getValue());
					tmpCookie.setPath("/");
					tmpCookie.setHttpOnly(true);
					response.addCookie(tmpCookie);
				}
			}
		} catch (Exception e) {
			logger.error("对象写入cookie失败", e);
		}
		
	}
	
	/**
	 * 功能说明: 将对象序列化、加密、做切片，返回map<br>
	 * 系统版本: @version 1.0<br>
	 * 开发人员: @author cgw<br>
	 * 开发时间: 2016-10-17 上午10:09:28<br>
	 */
	public static Map<String, String> split2TokenMap(String key, Object object) throws Exception {
		// 1.序列化
		String jsonStr = FastJsonUtil.toJSONString(object);
		// 2.加密
		String encStr = encryptionCookie(jsonStr);
		// 3.编码
		String base64String = Base64.encodeBase64String(encStr.getBytes());		
		// 4.切片
		Map<String, String> sliceMap = ParamUtil.splitParam(key, base64String);
		return sliceMap;
	}
	
	/**
	 * 功能说明: cookie加密<br>
	 * 系统版本: @version 1.0<br>
	 * 开发人员: @author cgw<br>
	 * 开发时间: 2016-10-17 上午10:13:01<br>
	 */
	public static String encryptionCookie (String toEncrypt) throws Exception {
		String keyString = MD5.md5Encrypt(PropertiesUtil.get("gggw.cookie.encryptkey", "cuigaowei"));
		String cookieEncryptStr = AESUtil.encrypt(toEncrypt, keyString);
		return cookieEncryptStr;
	}
	
	/**
	 * 功能说明: 从cookie中读取对象<br>
	 * 系统版本: @version 1.0<br>
	 * 开发人员: @author cgw<br>
	 * 开发时间: 2016-10-17 下午1:22:21<br>
	 */
	public static Object readObject(HttpServletRequest request, String key, Class clazz) {
		if (request == null || StringUtils.isEmpty(key)) {
			return null;
		}
		try {
			Cookie[] cookies = request.getCookies();
			if (cookies != null && cookies.length > 0) {
				// 1.读cookie
				Map<String, String> params = new HashMap<String, String>();
				for (Cookie cookie : cookies) {
					String name = cookie.getName();
					String value = cookie.getValue();
					params.put(name, value);
				}
				// 2.组装切片
				String base64Str = ParamUtil.mergeParam(key, params);
				if(StringUtils.isBlank(base64Str)) {
					return null;
				}
				// 3.解码
				byte[] buffer = Base64.decodeBase64(base64Str);
				// 4.解密
				String plainStr = decryptionCookie(new String(buffer));
				// 5.反序列化
				return FastJsonUtil.parseObject(plainStr, clazz);
			} 	
		} catch (Exception e) {
			logger.error("cookie转为对象失败", e);
			return null;
		}
		
		return null;
	}
	
	/**
	 * 功能说明: cookie解密<br>
	 * 系统版本: @version 1.0<br>
	 * 开发人员: @author cgw<br>
	 * 开发时间: 2016-10-17 上午10:13:01<br>
	 */
	public static String decryptionCookie(String toDecrypt) throws Exception {
		String keyString = MD5.md5Encrypt(PropertiesUtil.get("gggw.cookie.encryptkey", "cuigaowei"));
		String cookieDecryptStr = AESUtil.decrypt(toDecrypt, keyString);
		return cookieDecryptStr;
	}
}

