package com.gggw.entity.system;

import java.util.ArrayList;
import java.util.List;

public class BaseRole {
    private Integer roleId;

    private String roleCode;

    private String roleName;

	private String memo;
    
    private List<BaseResource> baseResourceList = new ArrayList<BaseResource>();
    
    public List<BaseResource> getBaseResourceList() {
		return baseResourceList;
	}

	public void setBaseResourceList(List<BaseResource> baseResourceList) {
		this.baseResourceList = baseResourceList;
	}

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }
}