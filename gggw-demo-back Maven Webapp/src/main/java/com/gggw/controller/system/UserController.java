package com.gggw.controller.system;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.gggw.entity.PageForm;
import com.gggw.entity.Paginator;
import com.gggw.entity.system.BaseDictionary;
import com.gggw.entity.system.BaseRole;
import com.gggw.entity.system.BaseSysUser;
import com.gggw.result.SisapResult;
import com.gggw.service.system.SysRoleService;
import com.gggw.service.system.SysUserService;

import com.gggw.util.DateUtil;
import com.gggw.util.PageData;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gggw.controller.base.BaseController;
import com.gggw.core.cache.BaseDictionaryCache;
import com.gggw.core.utils.AESUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:UserController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2016-11-9 下午4:56:06 <br/>
 * @author   cgw 
 */
@Controller
public class UserController extends BaseController{

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);

	@Resource(name="sysUserService")
	private SysUserService sysUserService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private BaseDictionaryCache baseDictionaryCache;

	/**
	 * 进入用户管理页
	 */
	@RequestMapping(value="toUser")
	public ModelAndView toUser()throws Exception{
		ModelAndView modelAndView = new ModelAndView();		
		List<BaseDictionary> statusList = baseDictionaryCache.getDictionaryList("1000");
		List<BaseDictionary> genderList = baseDictionaryCache.getDictionaryList("1100");		
		modelAndView.addObject("statusList", statusList);
		modelAndView.addObject("genderList", genderList);		
		modelAndView.setViewName("ui/backend/system/userManager");
		return modelAndView;
	}
	
	/**
	 * 进入用户编辑页面（新增/修改）
	 */
	@RequestMapping(value="toUserEdit")
	public ModelAndView toUserEdit(BaseSysUser baseSysUser, @RequestParam("operatType") String operatType)throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("operatType", operatType);
		List<BaseDictionary> statusList = baseDictionaryCache.getDictionaryList("1000");
		List<BaseDictionary> genderList = baseDictionaryCache.getDictionaryList("1100");
		List<BaseRole> roleList = (List<BaseRole>) sysRoleService.getAll();				// temporary_最好使用cache的形式，baseRole总新增listResource属性
		modelAndView.addObject("statusList", statusList);
		modelAndView.addObject("genderList", genderList);
		modelAndView.addObject("roleList", roleList);
		if ("1".equals(operatType)) {
			baseSysUser = sysUserService.findByUserId(baseSysUser.getUserId());
			modelAndView.addObject("baseSysUser", baseSysUser);
		}
		modelAndView.setViewName("ui/backend/system/userEdit");
		return modelAndView;
	}

	
	//==================================       ajaxFunction start          =====================================//
	
    /**
	 * ajax 获取分页数据
	 */
	@RequestMapping(value="ajaxUserList")
	@ResponseBody
	public Object ajaxUserList(@Valid PageForm pageForm) {
		List<BaseSysUser> userList = new ArrayList<BaseSysUser>();
		PageData requestParam = this.getPageData();
		int userListCount = 0;
		try {
			userList = sysUserService.selectByUserPage(requestParam);
			userListCount = sysUserService.selectByUserPageCount(requestParam);
		} catch (Exception e) {
			logger.error("UserController --> ajaxUserList error!", e);
		}
		return new Paginator(userListCount, userListCount, userList, pageForm.getDraw());
	}
	
	/**
	 * ajax 新增/修改 用户
	 */
	@RequestMapping(value="ajaxUserEdit")
	@ResponseBody
	public Object ajaxUserEdit(BaseSysUser baseSysUser, @RequestParam("operatType") String operatType) {
		SisapResult sisapResult = new SisapResult("0", "");
		BaseSysUser operator = getUser();
		try {
			
			if ("0".equals(operatType)) {
				BaseSysUser userIsExist = sysUserService.findByUserNo(baseSysUser);
				if (null != userIsExist) {
					sisapResult.setError_no("1");
					sisapResult.setError_info("已存在");
					return sisapResult;
				}
				baseSysUser.setUserPwd(AESUtil.encrypt(baseSysUser.getUserPwd(), baseSysUser.getUserNo()+"nmb"));
				baseSysUser.setCreateDate(DateUtil.getTime());
				baseSysUser.setCreateBy(operator.getUserId());
				sysUserService.addUser(baseSysUser);
				sisapResult.setError_info("添加成功!");
			} else {
				BaseSysUser userIsExist = sysUserService.findByUserId(baseSysUser.getUserId());
				if (null == userIsExist) {
					sisapResult.setError_no("1");
					sisapResult.setError_info("该用户不存在");
					return sisapResult;
				}
				baseSysUser.setUpdateDate(DateUtil.getTime());
				baseSysUser.setUpdateBy(operator.getUserId());
				sysUserService.updateUser(baseSysUser);
				sisapResult.setError_info("修改成功!");
			}
			
		} catch (Exception e) {
			logger.error("UserController --> ajaxUserEdit error!");
			sisapResult.setError_no("1");
			sisapResult.setError_info("异常");
		}
		return sisapResult;
	}

	/**
	 * ajax删除用户
	 */
	@RequestMapping(value="ajaxUserDel")
	@ResponseBody
	public Object ajaxUserDel() {
		SisapResult sisapResult = new SisapResult("0", "删除成功!");
		try {
			PageData requestParam = this.getPageData();
			int userId = Integer.parseInt(requestParam.get("userId").toString());
			BaseSysUser userIsExist = sysUserService.findByUserId(userId);
			if (null == userIsExist) {
				sisapResult.setError_no("2");
				sisapResult.setError_info("记录不存在");
				return sisapResult;
			}
			sysUserService.delUser(userId);
		} catch (Exception e) {
			logger.error("UserController --> ajaxUserEdit error!");
			sisapResult.setError_no("1");
			sisapResult.setError_info("异常");
		}
		return sisapResult;
	}
	//==================================       ajaxFunction end          =====================================//
}

