package com.gggw.core.cache;

import com.gggw.entity.system.BaseResource;
import com.gggw.entity.system.BaseRole;
import com.gggw.service.system.SysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * Created by cgw on 2016-11-28.
 */
@Component
public class SysRoleCache implements ICache<BaseRole> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String, BaseRole> configData = new LinkedHashMap<String, BaseRole>();

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private RoleResourceCache roleResourceCache;

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public String getId() {
        return "sysRoleCache";
    }

    @Override
    public Map<String, BaseRole> getConfigData() {
        return configData;
    }

    @Override
    public void refresh() throws Exception {
        List<BaseRole> list = sysRoleService.getAll();
        if (list != null && list.size() > 0) {
            configData.clear();
            for (BaseRole baseRole : list) {
                String roleId = baseRole.getRoleId().toString();
                baseRole.setBaseResourceList(roleResourceCache.getByRoleId(roleId));
                configData.put(roleId, baseRole);
            }
        }
    }

    /**
     * 获取不重复的资源集合
     * @param roleIds 以逗号隔开的roleId串
     * @return
     */
    public List<BaseResource> getByRoleIds(String roleIds){
        String[] roleIdArray = roleIds.split(",");
        Set<BaseResource> resources = new HashSet<BaseResource>();
        for (String roleId : roleIdArray) {
            List<BaseResource> subResources = configData.get(roleId).getBaseResourceList();
            resources.addAll(subResources);
        }
        //Set --> List
        List<BaseResource> resourceList = new ArrayList<BaseResource>(resources);
        if(resourceList!=null && resourceList.size()>0){
            Collections.sort(resourceList, new Comparator<BaseResource>() {
                @Override
                public int compare(BaseResource o1, BaseResource o2) {
                    //调整排序
                    return o1.getOrderNum().compareTo(o2.getOrderNum());
                }

            });
        }
        return resourceList;
    }

    /**
     * 获取资源集合中的子集合
     * @param resources
     * @param resourceId
     * @return
     */
    public List<BaseResource> getKidResources(List<BaseResource> resources, Integer resourceId) {
        List<BaseResource> kidResources = new ArrayList<BaseResource>();
        for (BaseResource resource : resources) {
            //两个Integer对象 比较用equals
            if (resource.getParentId().equals(resourceId)) {
                kidResources.add(resource);
            }
        }
        if(kidResources!=null && kidResources.size()>0){
            Collections.sort(kidResources, new Comparator<BaseResource>() {
                @Override
                public int compare(BaseResource o1, BaseResource o2) {
                    //调整排序
                    return o1.getOrderNum().compareTo(o2.getOrderNum());
                }

            });
        }
        return  kidResources;
    }

    /**
     * 根据类型获取资源集合
     */
    public List<BaseResource> getResourcesByType(List<BaseResource> resources, String type) {
        List<BaseResource> typeResources = new ArrayList<BaseResource>();
        for (BaseResource baseResource : resources) {
            if (type.equals(baseResource.getResourceType())) {
                typeResources.add(baseResource);
            }
        }
        return typeResources;
    }

    /**
     * 获取菜单
     * @param roleIds
     * @return
     * @throws Exception
     */
    public String getMenu(String roleIds) throws Exception{
        StringBuilder menuHtml = new StringBuilder();
        List<BaseResource> allResource = getResourcesByType(getByRoleIds(roleIds),"M");
        List<BaseResource> mainMenus = getKidResources(allResource, 0);
        menuHtml.append(getChildMenuHtml(mainMenus, allResource));
        return menuHtml.toString();
    }

    public String getChildMenuHtml(List<BaseResource> menus, List<BaseResource> allResource) throws Exception{
        StringBuilder menuHtml = new StringBuilder();
        for (BaseResource menu : menus) {
            List<BaseResource> childMenus = getKidResources(allResource, menu.getResourceId());
            if(childMenus.size() > 0 ){
                menuHtml.append("<li><a href=\"#\"><i class=\"fa " + menu.getIcon() + " fa-fw\"></i> " + menu.getResourceName() + "<span class=\"fa arrow\"></span></a>");
                menuHtml.append(build(menu, childMenus, allResource));
                menuHtml.append("</li>");
            } else {
                String url = "";
                if (StringUtils.isNotBlank(menu.getUrlInner())) {
                    url = menu.getUrlInner();
                }
                menuHtml.append("<li><a onclick=\"toPage(this, '"+ url +"')\"><i class=\"fa " + menu.getIcon() + " fa-fw\"></i> " + menu.getResourceName() + "</a></li>");
            }
        }
        return menuHtml.toString();
    }

    public String build (BaseResource menu, List<BaseResource> childMenus, List<BaseResource> allResource) throws Exception{
        StringBuilder childHtml = new StringBuilder();
        if (childMenus.size() > 0) {
            childHtml.append("<ul class=\"nav " + menu.getClassStyle() + "\">");
            childHtml.append(getChildMenuHtml(childMenus, allResource));
            childHtml.append("</ul>");
        }
        return childHtml.toString();
    }
}
