package com.gggw.core.cache;

import com.gggw.entity.system.BaseResource;
import com.gggw.entity.system.BaseRoleResource;
import com.gggw.service.system.RoleResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cgw on 2016-11-28.
 */
@Component
public class RoleResourceCache implements ICache<List<BaseResource>> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String, List<BaseResource>> configData = new LinkedHashMap<String, List<BaseResource>>();

    @Autowired
    private RoleResourceService roleResourceService;
    @Autowired
    private SysResourceCache sysResourceCache;

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public String getId() {
        return "roleResourceCache";
    }

    @Override
    public Map<String, List<BaseResource>> getConfigData() {
        return configData;
    }

    @Override
    public void refresh() throws Exception {
        List<BaseRoleResource> list = roleResourceService.getAll();
        if (list != null && list.size() > 0) {
            configData.clear();
            for (BaseRoleResource baseRoleResource : list) {
                int roleId = baseRoleResource.getRoleId();
                BaseResource baseResource = sysResourceCache.getById(String.valueOf(baseRoleResource.getResourceId()));
                List<BaseResource> resources = configData.get(String.valueOf(roleId));
                if (resources == null) {
                    resources = new ArrayList<BaseResource>();
                    resources.add(baseResource);
                    configData.put(String.valueOf(roleId), resources);
                }
                resources.add(baseResource);
            }
        }

    }

    public List<BaseResource> getByRoleId(String roleId){
        return  configData.get(roleId);
    }
}
