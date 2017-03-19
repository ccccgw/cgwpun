package com.gggw.controller.picture;

import com.gggw.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * ClassName:ShortcutsAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2016-12-1 上午9:10:36 <br/>
 * @author   cgw 
 */
@Controller
public class PictureController extends BaseController{
	
	/**
	 * 进入十月图片
	 */
	@RequestMapping(value="toOctober")
	public ModelAndView toSQLStatistics()throws Exception{
		ModelAndView modelAndView = new ModelAndView();			
		modelAndView.setViewName("ui/backend/picture/October");
		
		return modelAndView;
	}
	
	//==================================       ajaxFunction start          =====================================//

	//==================================       ajaxFunction end            =====================================//
	
	
}

