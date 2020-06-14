package com.holmes.springboot.atomikos.datasource;

/**
 * @author Administrator
 */
public class DataSourceManager {
    /**
     * 保存当前线程所使用的数据源
     */
    private static ThreadLocal<String> dataSourceThreadLocal = new ThreadLocal<>();

    public static String get() {
        return dataSourceThreadLocal.get();
    }

    public static void set(String dataSourceType) {
        dataSourceThreadLocal.set(dataSourceType);
    }

    public static void clear() {
        if (null != dataSourceThreadLocal.get()) {
            dataSourceThreadLocal.remove();
        }
    }
}
