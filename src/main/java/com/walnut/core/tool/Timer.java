package com.walnut.core.tool;

import com.google.common.base.Stopwatch;

public final class Timer {
    /**
     * 定义线程变量
     */
    final static ThreadLocal<Stopwatch> timer = new ThreadLocal<>();

    /**
     * 禁用实例化
     */
    private Timer() {
    }

    /**
     * 创建计时器
     */
    public static void create() {
        timer.set(Stopwatch.createUnstarted());
    }

    /**
     * 销毁计时器
     */
    public static void destroy() {
        timer.remove();
    }

    /**
     * 探测是否在计时
     * 
     * @return
     */
    public static boolean isRunning() {
        return timer.get().isRunning();
    }

    /**
     * 重新开始计时
     */
    public static void reset() {
        // 获取计时器
        var watcher = timer.get();

        // 重置计时器
        watcher.reset();

        // 开始计时
        watcher.start();
    }

    /**
     * 获取读数
     * 
     * @return
     */
    public static String getValue() {
        // 获取计时器
        var watcher = timer.get();

        // 获取读数
        return watcher.toString();
    }

    /**
     * 停止计时并返回计时结果
     */
    public static void stop() {
        // 获取计时器
        var watcher = timer.get();

        // 停止计时
        watcher.stop();
    }
}
