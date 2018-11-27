package com.hrz.poplayer.impl;


import com.hrz.poplayer.interfaces.PushManager;
import com.hrz.poplayer.log.Environment;
import com.hrz.poplayer.log.Logs;
import com.hrz.poplayer.log.Order;

/**
 * 在单独的功能模块 执行命令时装配环境
 * 推送功能模块
 * @author  ch
 * Created by mac on 2018/7/10.
 */

public class PushManagerImpl implements PushManager,Order {

    private Logs logs;

     public PushManagerImpl(Logs logs) {
        this.logs = logs;
    }

    @Override
    public void execute(Environment environment) {
        logs.logE("PushManagerImpl");
        environment.setOrder(this);
    }

    @Override
    public String toString() {
        return "Push State";
    }

}
