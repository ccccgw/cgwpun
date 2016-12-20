package com.gggw.controller.system;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gggw.controller.base.BaseController;

/**
 * ClassName:ShortcutsAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2016-12-1 上午9:10:36 <br/>
 * @author   cgw 
 */
@Controller
public class StatisticalController extends BaseController{
	
	/**
	 * 进入SQL统计页面
	 */
	@RequestMapping(value="toSQLStatistics")
	public ModelAndView toSQLStatistics()throws Exception{
		ModelAndView modelAndView = new ModelAndView();			
		modelAndView.setViewName("ui/backend/statistics/sqlStatisticsManager");
		
		return modelAndView;
	}
	
	//==================================       ajaxFunction start          =====================================//

	//==================================       ajaxFunction end            =====================================//
	
	
}

