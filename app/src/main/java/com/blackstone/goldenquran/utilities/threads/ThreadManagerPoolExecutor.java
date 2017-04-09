package com.blackstone.goldenquran.utilities.threads;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class ThreadManagerPoolExecutor extends ThreadPoolExecutor {

    //region Variables

    boolean mIsDebugMode = false;

    //endregion

    //region Constructors

    ThreadManagerPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {

        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public ThreadManagerPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {

        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public ThreadManagerPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {

        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public ThreadManagerPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
                                     RejectedExecutionHandler handler) {

        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    //endregion

    //region Overridden Methods

    @Override
    protected void beforeExecute(Thread thread, Runnable runnable) {

        super.beforeExecute(thread, runnable);

        Task originalRunnable = ((ThreadManagerFutureTask) runnable).getRunnable();

        try {

            if (mIsDebugMode) {

                Log.d("onPreRun => ", " RunnableStackTrace:" +  originalRunnable.stackTrace);
            }

            originalRunnable.onPreRun();

        } catch (Exception ex) {

            originalRunnable.onPreRunFailure(ex);

        }
    }

    @Override
    protected void afterExecute(Runnable runnable, Throwable throwable) {

        super.afterExecute(runnable, throwable);

        Task originalRunnable = ((ThreadManagerFutureTask) runnable).getRunnable();

        try {

            if (mIsDebugMode) {

                Log.d("onRunSuccess => ", " RunnableStackTrace:" +  originalRunnable.stackTrace);
            }

            originalRunnable.onRunSuccess();

        } catch (Exception ex) {

            originalRunnable.onRunFailure(ex);

        }
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {

        return newTaskFor(runnable, value, 0);
    }

    @NonNull
    @Override
    public Future<?> submit(Runnable task) {

        return submit(task, 0);
    }

    @NonNull
    @Override
    public <T> Future<T> submit(Runnable task, T result) {

        return submit(task, result, 0);
    }
    //endregion

    //region Methods

    private <T> RunnableFuture<T> newTaskFor(final Runnable runnable, T value, int priority) {

        return new ThreadManagerFutureTask(runnable, value, priority);
    }

    Future<?> submit(Runnable task, int priority) {

        return submit(task, null, priority);
    }

    private <T> Future<T> submit(Runnable task, T result, int priority) {

        RunnableFuture<T> futureTask = newTaskFor(task, result, priority);
        execute(futureTask);

        return futureTask;
    }

    //endregion
}
