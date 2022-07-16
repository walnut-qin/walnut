package com.kaos.walnut.core.tool;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Maps;
import com.google.common.collect.Queues;

public class ThreadPool {
    /**
     * 线程池名称
     */
    final String name;

    /**
     * 线程池实体
     */
    final ThreadPoolExecutor executor;

    /**
     * 构造线程池
     * 
     * @param name
     * @param threadSize
     */
    public ThreadPool(String name, Integer threadCount) {
        this.name = name;
        this.executor = new ThreadPoolExecutor(threadCount, threadCount,
                0, TimeUnit.SECONDS,
                Queues.newLinkedBlockingDeque());
    }

    /**
     * 执行任务
     */
    public void execute(Runnable runnable) {
        this.executor.execute(runnable);
    }

    /**
     * 展示线程池状态
     */
    public Map<String, Object> show() {
        Map<String, Object> view = Maps.newHashMap();
        view.put("name", name);
        view.put("size", this.executor.getCorePoolSize());
        view.put("activeSize", this.executor.getActiveCount());
        view.put("queueSize", this.executor.getQueue().size());
        view.put("taskCount", this.executor.getTaskCount());
        view.put("completeTaskCount", this.executor.getCompletedTaskCount());
        return view;
    }
}
