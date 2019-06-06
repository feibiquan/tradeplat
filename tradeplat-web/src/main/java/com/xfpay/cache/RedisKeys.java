package com.xfpay.cache;

public class RedisKeys {
    /******************************
     * 系统配置参数所需要的KEY值 * *
     ******************************
     */
    public final static String INIT_LOAD_VERSION = "xfpay.cache.init.version"; // 系统初始化版本号
    public final static String INIT_SYSCONFIG_VERSION = "xfpay.cache.sysconfig.version"; // 系统初始化版本号


    /**
     * 参考实例 --key的命名规则
     *
     * @author android
     */
    public static class SimpleInfo {
        public final static String PREFIX_KEY = "SIMPLE.KEY.INFO";//key前缀

        public static String getSingleKey(Integer userId) {
            return PREFIX_KEY + "." + userId;
        }

        //多个参数作为key使用实例
        public static String getSingleKey2(Integer info1, String info2) {
            return PREFIX_KEY + "." + info1 + "_" + info2;
        }
    }


    /**
     * 系统版本更新信息（历史版本）
     */
    public static class VersionUpdateSpace {
        public final static String VERSION_UPDATE_KEY = "xfpay.cache.version.update";

        public static String get_new_version_key(String systemType, String clientYype, String isNew) {
            //KEY + 所属业务系统 + 客户端类型 + 是否为最新版本
            return VERSION_UPDATE_KEY + "." + systemType + "." + clientYype + "." + isNew;
        }

        public static String all_version_key() {
            //KEY +
            return VERSION_UPDATE_KEY + ".*";
        }
    }

}
