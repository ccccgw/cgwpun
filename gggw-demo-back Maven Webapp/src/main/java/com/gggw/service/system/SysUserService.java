/**
 * Project Name:gggw-demo-backend Maven Webapp
 * File Name:SysUserService.java
 * Package Name:com.gggw.service.system
 * Date:2016-6-24下午2:39:04
 * Copyright (c) 2016, 502269006@qq.com All Rights Reserved.
 *
*/

package com.gggw.service.system;

import javax.annotation.Resource;

import com.gggw.entity.system.BaseSysUser;
import com.gggw.result.SisapResult;

import org.springframework.stereotype.Service;

import com.gggw.core.utils.AESUtil;
import com.gggw.dao.DaoSupport;

import java.util.List;
import java.util.Map;

/**
 * ClassName:SysUserService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-6-24 下午2:39:04 <br/>
 * @author   cgw
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@Service("sysUserService")
public class SysUserService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 根据主键查找
	 */
	public BaseSysUser findByUserId(int userId)throws Exception{
		return (BaseSysUser)dao.findForObject("BaseSysUserMapper.selectByPrimaryKey", userId);
	}

	/**
	 * findByUserNo:(通过登录的用户名查询用户). <br/>
	 */
	public BaseSysUser findByUserNo(BaseSysUser user)throws Exception{
		return (BaseSysUser)dao.findForObject("BaseSysUserMapper.findByUserNo", user);
	}
	
	/**
	 * 查询所有用户
	 */
	public List<BaseSysUser> getUserListAll(Map<String, Integer> params) throws Exception{
		return (List<BaseSysUser>) dao.findForList("BaseSysUserMapper.selectAll", params);
	}

	/**
	 * 查询所有用户的总数
	 */
	public Integer getUserListAllCount() throws  Exception{
		return (Integer)dao.findForObject("BaseSysUserMapper.selectAllCount", null);
	}

	/**
	 * 根据User查询
	 */
	public List<BaseSysUser> selectByUserPage(Map<String, String> params) throws Exception{
		return (List<BaseSysUser>) dao.findForList("BaseSysUserMapper.selectByUserPage", params);
	}

	/**
	 * 根据User查询总数
	 */
	public Integer selectByUserPageCount(Map<String, String> params) throws Exception{
		return (Integer)dao.findForObject("BaseSysUserMapper.selectByUserPageCount", params);
	}

	/**
	 * 新增用户
	 */
	public void addUser(BaseSysUser baseSysUser) throws Exception{
		dao.findForObject("BaseSysUserMapper.insert", baseSysUser);
	}

	/**
	 * 删除用户
	 */
	public void delUser(int userId) throws Exception{
		dao.findForObject("BaseSysUserMapper.deleteByPrimaryKey", userId);
	}
	
	/**
	 * 修改用户
	 */
	public void updateUser(BaseSysUser baseSysUser) throws Exception{
		dao.findForObject("BaseSysUserMapper.updateByPrimaryKey", baseSysUser);
	}
	
	//========================================    以下为业务逻辑方法       =========================================================//
	
	/**
	 * 验证用户登录
	 */
	public BaseSysUser checkLogin(BaseSysUser loginUser) throws Exception{
		BaseSysUser baseSysUser = findByUserNo(loginUser);
		String password = baseSysUser.getUserPwd();
		String loginPasswordString = AESUtil.encrypt(loginUser.getUserPwd(), loginUser.getUserNo()+"nmb");
		if (password.equals(loginPasswordString)) {
			return baseSysUser;	
		} 
		return null;		
	}
	
	/**
	 * 检查用户状态
	 */
	public boolean checkStatus(BaseSysUser user) throws Exception{
		if(user.getStatus() == 8) {
			return true;
		}
		return false;
	}
}

