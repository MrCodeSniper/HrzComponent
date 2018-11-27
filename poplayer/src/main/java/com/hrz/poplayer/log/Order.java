package com.hrz.poplayer.log;

/**
 * Created by mac on 2018/7/10.
 */

public interface Order {
    void execute(Environment environment); //命令 这里主要是输出日志
}
