package com.baiyan.auth.service.utils;


import com.baiyan.auth.common.utils.threadpool.ThreadPoolService;
import com.baiyan.auth.common.utils.threadpool.ThreadPoolServiceImpl;

/**
 * 通用线程池
 *
 * @author baiyan
 * @date 2020/11/30
 */
public class CommonThreadPool {

    /**
     * 单例线程池
     */
    private volatile static ThreadPoolService threadPoolService;

    /**
     * 构造方法私有化
     */
    private CommonThreadPool(){

    }

    /**
     * 单例线程池获取
     * @return
     */
    public static ThreadPoolService getBlockThreadPool() {
        if (threadPoolService == null) {
            synchronized (CommonThreadPool.class) {
                if (threadPoolService == null) {
                    threadPoolService = new ThreadPoolServiceImpl();
                }
            }
        }
        return threadPoolService;
    }

}
