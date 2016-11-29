package com.gggw.service.system;

import com.gggw.dao.DaoSupport;
import com.gggw.entity.system.BaseRoleResource;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cgw on 2016-11-6.
 */
@Service("roleResourceService")
public class RoleResourceService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**
	 * 新增
	 */
	public int addRoleResource(BaseRoleResource baseRoleResource) throws Exception{
		return (Integer)dao.save("BaseRoleResourceMapper.insert", baseRoleResource);
	}
	
	/**
	 * 通过角色Id获取数据
	 */
	public List<String> getByRoleId(String roleId) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		return (List<String>)dao.findForList("BaseRoleResourceMapper.getByRoleId", params);
	}
	
	/**
	 * 通过角色集获取数据
	 */
	public List<String> getByRoles(String roleIds) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleIds", roleIds);
		return (List<String>)dao.findForList("BaseRoleResourceMapper.getByRoles", params);
	}

	/**
	 * 通过roleId删除
	 */
	public void deleteByRoleId(int roleId) throws Exception{
		dao.delete("BaseRoleResourceMapper.deleteByRoleId", roleId);
	}
	

	/**
	 * 获取所有
	 */
	public List<BaseRoleResource> getAll() throws Exception{
		return (List<BaseRoleResource>)dao.findForList("BaseRoleResourceMapper.selectAll", null);
	}
}
