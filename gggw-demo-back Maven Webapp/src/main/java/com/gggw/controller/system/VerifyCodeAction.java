package com.gggw.controller.system;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gggw.controller.base.BaseController;
import com.gggw.core.annotation.NoLogin;
import com.gggw.core.utils.CookieUtil;
import com.gggw.system.service.IImageCodeService;

/**
 * ClassName:VerificationCodeAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2016-10-12 上午9:51:12 <br/>
 * @author   cgw
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@Controller
public class VerifyCodeAction extends BaseController{
	@Autowired
	private IImageCodeService verifyCodeService;
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(VerifyCodeAction.class);
	
	@NoLogin
	@RequestMapping("imageCode.img")
	public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
		String verifyCode="";
		OutputStream os = null;
		try {
			// 生成随机验证码内容   
			verifyCode= verifyCodeService.getRandString();
			String sessionId= CookieUtil.getCookie(request, CookieUtil.COOKIE_GGGW_SESSION_ID);
			
			/**
			 * 这里测试用，如果没有cookie的话则设置cookie
			 */
			if (null == sessionId) {
				sessionId = get32UUID();
				CookieUtil.setCookie(response, CookieUtil.COOKIE_GGGW_SESSION_ID, sessionId, true);
			}		
			
			// 存入redis，以便集群服务器均可获取，3分钟过期
			verifyCodeService.saveValidateCode(verifyCode, sessionId);
			BufferedImage bufferedImage = verifyCodeService.createVerifyCodeImage(verifyCode);
			response.setContentType("image/png");  
		    os = response.getOutputStream();  
		    ImageIO.write(bufferedImage, "png", os);
		    os.close();
		} catch (Exception e) {
			logger.error("imageCode.img is error!", e);
		} finally {
			if (null != os) {
				try {
					os.close();
				} catch (IOException e) {
					logger.error("关闭文件流错误", e);					
				}
			}
		}
	}
}

