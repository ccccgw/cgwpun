package com.gggw.core.utils;

import com.gggw.entity.system.BaseSysUser;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.list.AbstractLinkedList;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by cgw on 2017/2/4.
 */
public class SortListUtil {

    private static Logger logger = LoggerFactory.getLogger(SortListUtil.class);

    /** 升序 */
    public static final String ASC = "asc";
    /** 降序 */
    public static final String DESC = "desc";


    /**
     * 对list中的元素按升序排列.
     *
     * <pre>
     * eg:SortListUtil.sort(list, "age")	把list中的对象按照age值的升序排列
     * </pre>
     * @param list 排序集合
     * @param field 排序字段
     */
    public static void sort(List<?> list, final String field) {
        sort(list, field, null);
    }

    /**
     * 对list中的元素进行排序.
     *
     * <pre>
     * eg:SortListUtil.sort(list, "age", SortListUtil.DESC)	把list中的对象按照age值的降序排列
     * </pre>
     * @param list 排序集合
     * @param field 排序字段
     * @param sort 排序方式: desc:降序, asc:升序。默认升序
     * @see SortListUtil#ASC
     * @see SortListUtil#DESC
     */
    public static void sort(List<?> list, final String field, final String sort) {
        sort(list, new String[] {field}, new String[] {sort});
    }

    /**
     * 对list中的元素按fields和sorts进行排序
     *
     * <pre>
     * eg:
     * SortListUtil.sort(list, new String[] {"name", "id"}, new String[] {SortListUtil.ASC, SortListUtil.DESC});
     * 先按name正序,name相同时再按id倒序
     * </pre>
     * @param list 排序集合
     * @param fields 指定多个排序字段
     * @param sorts 指定多个排序方式，如果sorts[i]为空则默认按升序排列
     */
    public static void sort(List<?> list, String[] fields, String[] sorts) {
        if (null != fields && fields.length > 0) {
            //后置位先排序
            for (int i = fields.length - 1; i >= 0; i-- ) {
                final String field = fields[i];
                String tmpSort = null != sorts && sorts.length > i && sorts[i] != null ? ASC : sorts[i];
                final String sort = tmpSort;
                Collections.sort(list, new Comparator<Object>() {
                    public int compare(Object a, Object b) {
                        int compareValue = 0;
                        try {
                            Class<?> type = PropertyUtils.getPropertyType(a, field);
                            Object va = PropertyUtils.getProperty(a, field);
                            Object vb = PropertyUtils.getProperty(b, field);
                            if (va == null && vb != null) {
                                compareValue = -1;
                            } else if (va != null && vb == null) {
                                compareValue = 1;
                            } else if (va == null && vb == null) {
                                compareValue = 0;
                            } else {
                                if (Comparable.class.isAssignableFrom(type)) {
                                    compareValue = ((Comparable<Comparable<?>>)va).compareTo((Comparable<?>)vb);
                                } else {
                                    compareValue = String.valueOf(va).compareTo(String.valueOf(vb));
                                }
                            }
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                            return 0;
                        }

                        if (StringUtils.equalsIgnoreCase(sort, DESC)) {
                            return -compareValue;
                        } else {
                            return compareValue;
                        }
                    }
                });
            }
        }
    }

    public static void main(String[] args) {
        BaseSysUser b1 = new BaseSysUser();
        b1.setMemo("cgw1");
        b1.setUserId(10);
        BaseSysUser b2 = new BaseSysUser();
        b2.setMemo("cgw1");
        b2.setUserId(9);
        BaseSysUser b3 = new BaseSysUser();
        b3.setMemo("cgw1");
        b3.setUserId(8);
        List<BaseSysUser> list = new ArrayList<BaseSysUser>();
        list.add(b1);
        list.add(b2);
        list.add(b3);

        for (BaseSysUser u : list) {
            System.out.println(u.getMemo()+ "           " + u.getUserId());
        }
        System.out.println("==================================");
        String[] fields = {"memo","userId"};
        String[] sorts = {"asc", "asc"};
        sort(list, fields, sorts);
        for (BaseSysUser u : list) {
            System.out.println(u.getMemo()+ "           " + u.getUserId());
        }
    }
}
