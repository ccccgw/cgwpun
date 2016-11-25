package com.gggw.controller.system;


import com.gggw.entity.PageForm;
import com.gggw.entity.Paginator;
import com.gggw.entity.system.BaseDictionary;
import com.gggw.entity.system.BaseResource;
import com.gggw.entity.system.BaseRole;
import com.gggw.entity.system.BaseRoleResource;
import com.gggw.result.SisapResult;
import com.gggw.service.system.RoleResourceService;
import com.gggw.service.system.SysResourceService;
import com.gggw.service.system.SysRoleService;
import com.gggw.util.PageData;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gggw.controller.base.BaseController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

/**
 * ClassName:角色和资源管理器<br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2016-11-23 下午4:36:19 <br/>
 * @author   cgw 
 */
@Controller
public class RoleResourceController extends BaseController{

	@Autowired
	private SysResourceService sysResourceService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private RoleResourceService roleResourceService;

	/**
	 * 进入角色管理页
	 */
	@RequestMapping(value="toRole")
	public ModelAndView toRole()throws Exception{
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName("ui/backend/system/roleManager");
		return modelAndView;
	}
	
	/**
	 * 进入角色编辑页面（新增/修改）
	 */
	@RequestMapping(value="toRoleEdit")
	public ModelAndView toRoleEdit(BaseRole baseRole, @RequestParam("operatType") String operatType)throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("operatType", operatType);
		if ("1".equals(operatType)) {
			baseRole = sysRoleService.findById(baseRole);
			modelAndView.addObject("baseRole", baseRole);
		}
		modelAndView.setViewName("ui/backend/system/roleEdit");
		return modelAndView;
	}

	
	//==================================       ajaxFunction start          =====================================//
	/**
	 * ajax 获取分页数据
	 */
	@RequestMapping(value="ajaxRoleList")
	@ResponseBody
	public Object ajaxRoleList(@Valid PageForm pageForm) {
		List<BaseRole> roleList = new ArrayList<BaseRole>();
		PageData requestParam = this.getPageData();
		int roleListCount = 0;
		try {
			roleList = sysRoleService.selectByRolePage(requestParam);
			roleListCount = sysRoleService.selectByRolePageCount(requestParam);
		} catch (Exception e) {
			logger.error("RoleController --> ajaxRoleList error!", e);
		}
		return new Paginator(roleListCount, roleListCount, roleList, pageForm.getDraw());
	}
	
	/**
	 * 获取所有资源
	 */
	@ResponseBody
	@RequestMapping(value="ajaxGetAllResource")
	public Object ajaxGetAllResource()throws Exception{
		PageData requestParam = this.getPageData();
		String roleId = requestParam.getString("roleId");
		List<String> checkedResources = new ArrayList<String>();
		if (StringUtils.isNotBlank(roleId)) {
			checkedResources = roleResourceService.getByRoleId(roleId);
			
		}
		List<BaseResource> allResourceList = sysResourceService.getAllResource();
		List<Map<String, Object>>  resources = new ArrayList<Map<String, Object>>();
		for (BaseResource br : allResourceList) {
			Map<String, Object> resource = new HashMap<String, Object>();
			resource.put("id", br.getResourceId());
			resource.put("pId", br.getParentId());
			resource.put("name", br.getResourceName());
			if (checkedResources.contains(br.getResourceId().toString())) {
				resource.put("checked", true);
			}
			resources.add(resource);
		}
		return resources;
	}
	
	/**
	 *  ajax 新增/修改角色
	 */
	@ResponseBody
	@RequestMapping(value="ajaxRoleEdit")
	public Object ajaxRoleEdit(BaseRole baseRole, @RequestParam("operatType") String operatType)throws Exception{
		SisapResult sisapResult = new SisapResult("0", "");		
		PageData requestParam = this.getPageData();
		String[] resources = requestParam.getString("resources").split(",");
		try {
			BaseRole baseRoleExist = sysRoleService.findByCode(baseRole);
			if ("0".equals(operatType)) {
				if (null != baseRoleExist) {
					sisapResult.setError_no("1");
					sisapResult.setError_info("已存在");
					return sisapResult;
				}
				sysRoleService.addRole(baseRole);
				baseRole = sysRoleService.findByCode(baseRole);
				updateRoleResource(resources, baseRole);
				sisapResult.setError_info("添加成功!");
			} else {
				if (null == baseRoleExist) {
					sisapResult.setError_no("1");
					sisapResult.setError_info("该角色不存在");
					return sisapResult;
				}
				sysRoleService.updateRole(baseRole);
				roleResourceService.deleteByRoleId(baseRole.getRoleId());
				updateRoleResource(resources, baseRole);
				sisapResult.setError_info("修改成功!");
			}
		} catch (Exception e) {
			logger.error("RoleController --> ajaxRoleEdit error!", e);
			sisapResult.setError_no("1");
			sisapResult.setError_info("数据异常");
		}
		return sisapResult;
	}

	/**
	 * ajax删除角色
	 */
	@RequestMapping(value="ajaxRoleDel")
	@ResponseBody
	public Object ajaxRoleDel(BaseRole baseRole) {
		SisapResult sisapResult = new SisapResult("0", "删除成功!");
		try {
			roleResourceService.deleteByRoleId(baseRole.getRoleId());
			sysRoleService.delRole(baseRole.getRoleId());
		} catch (Exception e) {
			logger.error("RoleController --> ajaxRoleDel error!", e);
			sisapResult.setError_no("1");
			sisapResult.setError_info("数据异常");
		}
		return sisapResult;
	}
	//==================================       ajaxFunction end          =====================================//

	public void updateRoleResource(String[] resources, BaseRole baseRole ) throws Exception{
		if (!("".equals(resources[0]))) {
			for (String resourceId : resources) {
				BaseRoleResource BaseRoleResource = new BaseRoleResource();
				BaseRoleResource.setRoleId(baseRole.getRoleId());
				BaseRoleResource.setResourceId(Integer.parseInt(resourceId));
				roleResourceService.addRoleResource(BaseRoleResource);
			}
		}
	}
}

