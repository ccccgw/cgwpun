package com.gggw.core.cache;

import com.gggw.entity.system.BaseResource;
import com.gggw.service.system.SysResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cgw on 2016-11-28.
 */
@Component
public class SysResourceCache implements ICache<BaseResource> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String, BaseResource> configData = new LinkedHashMap<String, BaseResource>();

    @Autowired
    private SysResourceService sysResourceService;

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public String getId() {
        return "sysResourceCache";
    }

    @Override
    public Map<String, BaseResource> getConfigData() {
        return configData;
    }

    @Override
    public void refresh() throws Exception {
        List<BaseResource> list = sysResourceService.getAllResource();
        if (list != null && list.size() > 0) {
            configData.clear();
            for (BaseResource baseResource : list) {
                configData.put(baseResource.getResourceId().toString(), baseResource);
            }
        }
    }

    public BaseResource getById(String resourceId) throws Exception{
        return configData.get(resourceId);
    }
}
