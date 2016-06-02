package com.eco.pub.filter;

/**
 * 将url权限验证标记放入线程变量内
 *
 * @author sunzhen
 */
public class AuthExcludeFlagHolder {

    private static ThreadLocal<Boolean> excludeFlag = new ThreadLocal<Boolean>();


    public static boolean getExcludeFlag() {

        return excludeFlag.get();
    }


    public static void setExcludeFlag(boolean exclude) {
        excludeFlag.set(exclude);
    }


    public static void clearExcludeFlag() {
        excludeFlag.set(null);
    }
}
