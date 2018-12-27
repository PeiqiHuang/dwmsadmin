package com.dwms.common.consts;

import com.dwms.common.config.SysConfig;
import com.dwms.common.util.SpringUtils;

/**
 * 环境变量枚举
 * @author windy
 * @date 2018-11-21
 */
public enum EnvEnum {

    //启动环境
    PROD("prod", "生产环境"),
    TEST("test", "测试环境"),
    DEV("dev", "开发环境"),
    NONE("none", "不存在环境"),;

    private String key;
    private String msg;

    EnvEnum(String key, String msg) {
        this.key = key;
        this.msg = msg;
    }

    /**
     * 判断是否当前环境
     * @author windy
     * @date 2018-11-21
     */
    public boolean current(String env) {
        return this.key.equals(env);
    }

    /**
     * 判断是否当前环境
     * 枚举在系统未注入时初始化，所以拦截器前无法获取SysConfig
     * @author windy
     * @date 2018-11-21
     */
    public boolean current() {
        return this.key.equals(SpringUtils.getBean(SysConfig.class).getEnv());
    }
}
