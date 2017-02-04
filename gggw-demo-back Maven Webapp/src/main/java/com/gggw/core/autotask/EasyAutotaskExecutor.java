package com.gggw.core.autotask;

import com.gggw.core.utils.SpringContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * 功能说明: 简单轮询任务抽象类<br>
 * 系统版本: @version 1.0<br>
 * 开发人员: @author cgw<br>
 * 开发时间: 2017-2-3 下午12:19:29<br>
 */
public abstract class EasyAutotaskExecutor {

    @Autowired
    private ApplicationContext context;

    /**
     * 任务计划
     * @return
     */
    public abstract String getCron();

    /**
     * 是否启用
     * @return
     */
    public abstract boolean inuse();

    /**
     * 任务执行
     */
    public abstract void execute();

    // public abstract Future<?> execute();//支持@Async，发现并无太大意义

    /**
     * 是否是单例轮询任务。
     * <p>
     * 如果可以在多个机器上都启动该定时任务，子类需要复写返回false
     */
    public boolean isSingleton() {
        return true;
    }
}

