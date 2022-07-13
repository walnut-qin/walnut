package com.kaos.skynet.core.tool.lock;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import com.google.common.collect.Lists;

public final class LockExecutor {
    /**
     * 线程独立的锁执行器
     */
    static final ThreadLocal<Executor> executor = new ThreadLocal<>();

    /**
     * 创建
     */
    public static void create() {
        executor.set(new Executor());
    }

    /**
     * 销毁
     */
    public static void destroy() {
        executor.remove();
    }

    /**
     * 获取当前的执行器
     * 
     * @return
     */
    public static void link(Lock lock, Object key) {
        executor.get().link(lock, key);
    }

    /**
     * 获取当前的执行器
     * 
     * @return
     */
    public static void clear() {
        executor.get().clear();
    }

    /**
     * 带锁执行
     * 
     * @param <T>
     * @param callable
     * @return
     * @throws Exception
     */
    public static <T> T execute(Callable<T> callable) throws Exception {
        return executor.get().execute(callable);
    }

    /**
     * 带锁执行
     * 
     * @param runnable
     * @throws Exception
     */
    public static void execute(Runnable runnable) throws Exception {
        executor.get().execute(runnable);
    }

    /**
     * 执行器
     */
    static class Executor {
        /**
         * 关联的锁实体
         */
        List<Object> objects = Lists.newArrayList();

        /**
         * 关联锁芯
         * 
         * @param object
         */
        public void link(Lock lock, Object key) {
            // 通过hash方法变换到有效索引
            Integer idx = key.hashCode() & Integer.MAX_VALUE % lock.objects.size();

            // 定位对应的锁
            objects.add(lock.objects.get(idx));
        }

        /**
         * 清除当前所有关联的锁芯
         */
        public void clear() {
            objects.clear();
        }

        /**
         * 核心执行逻辑
         * 
         * @param <T>
         * @param it
         * @param Callable
         * @return
         * @throws Exception
         */
        private <T> T execute(Iterator<Object> it, Callable<T> callable) throws Exception {
            if (it.hasNext()) {
                // 获取锁
                var lock = it.next();

                // 执行
                synchronized (lock) {
                    return execute(it, callable);
                }
            } else {
                return callable.call();
            }
        }

        /**
         * 带锁执行callable
         * 
         * @param <T>
         * @param locks
         * @param Callable
         * @return
         * @throws Exception
         */
        public <T> T execute(Callable<T> callable) throws Exception {
            return execute(objects.iterator(), callable);
        }

        /**
         * 核心执行逻辑
         * 
         * @param <T>
         * @param it
         * @param Callable
         * @return
         * @throws Exception
         */
        private void execute(Iterator<Object> it, Runnable runnable) throws Exception {
            if (it.hasNext()) {
                // 获取锁
                var lock = it.next();

                // 执行
                synchronized (lock) {
                    execute(it, runnable);
                }
            } else {
                runnable.run();
            }
        }

        /**
         * 带锁执行runable
         * 
         * @param <T>
         * @param locks
         * @param Callable
         * @return
         * @throws Exception
         */
        public void execute(Runnable runnable) throws Exception {
            execute(objects.iterator(), runnable);
        }
    }
}
