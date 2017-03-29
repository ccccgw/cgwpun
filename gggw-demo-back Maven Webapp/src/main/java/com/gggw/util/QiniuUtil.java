package com.gggw.util;

import com.qiniu.util.Auth;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO 七牛云工具
 *
 * @author Cui.GaoWei
 * @date 2017/3/28
 */
public class QiniuUtil {

    //设置好账号的ACCESS_KEY和SECRET_KEY
    private static String ACCESS_KEY = "3iEEsGwCAYJdopP_RaZWQ_w95lG-AZQckB7oAPO8";
    private static String SECRET_KEY = "QJWsrPYe5z2H0rNIKU_dz06LbWuMAASdDRoD0BaV";

    private static final String UP_TOKEN = "uptoken";

    //密钥配置
    private static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    /**
     * 获取token
     * @param bucketname 需要上传到的空间名
     * @return
     */
    public static String getToken(String bucketname) {
        return auth.uploadToken(bucketname);
    }

    public static Map getTokenMap(String bucketname) {
        Map tokenMap = new HashMap(1);
        tokenMap.put(UP_TOKEN, getToken(bucketname));
        return tokenMap;
    }

}
