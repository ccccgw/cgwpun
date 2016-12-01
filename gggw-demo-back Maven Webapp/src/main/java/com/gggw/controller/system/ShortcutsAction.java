package com.gggw.controller.system;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gggw.controller.base.BaseController;
import com.gggw.entity.system.BaseDictionary;

/**
 * ClassName:ShortcutsAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2016-12-1 上午9:10:36 <br/>
 * @author   cgw 
 */
@Controller
public class ShortcutsAction extends BaseController{
	
	/**
	 * 进入用户管理页
	 */
	@RequestMapping(value="toShortcuts")
	public ModelAndView toShortcuts()throws Exception{
		ModelAndView modelAndView = new ModelAndView();			
		modelAndView.setViewName("ui/backend/system/shortcutsManager");
		return modelAndView;
	}
	
	//==================================       ajaxFunction start          =====================================//
	/**
	 * ajax关闭计算机
	 */
	@RequestMapping(value="ajaxShutdown")
	public void ajaxShutdown()throws Exception{
		shutdown();
	}
	//==================================       ajaxFunction end            =====================================//
	
	
	/** 
     * 函数名:exec 
     * 简单描述:执行command命令 
     */  
    public void exec(String command) throws Exception{  
    	Runtime.getRuntime().exec(command);  
    }  
      
    /** 
     *关机  
     */  
    public void shutdown()  throws Exception{  
       exec("shutdown -S ");  
    }  
      
    /** 
 	 *重启
     */  
    public void restart() throws Exception {  
       exec("shutdown -R ");  
    }  
      
    /** 
     *注销   
     */  
    public void logout() throws Exception {  
       exec("shutdown -L ");  
    }
}

