package com.blackstone.goldenquran.utilities.threads;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ThreadManager {

    //region Constants

    private static final int KEEP_ALIVE_TIME = 1;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    //endregion

    //region Static Variables

    private static boolean sIsDebugMode = false;

    //endregion

    //region Static Variables

    private static HashMap<String, ThreadManagerPoolExecutor> sThreadPoolExecutors = new HashMap<>();

    //endregion

    //region Properties

    public static void setDebugMode(boolean isDebugMode) {

        ThreadManager.sIsDebugMode = isDebugMode;
    }

    //endregion

    //region Methods

    public static String initializeThreadManagerPool(ThreadManagerConfiguration threadManagerConfiguration) {

        if (isThereThreadPool(threadManagerConfiguration.getId())) {

            throw new ThreadsManagerPoolAlreadyExistException();
        }

        ThreadManagerPoolExecutor threadPoolExecutor = new ThreadManagerPoolExecutor(
                threadManagerConfiguration.getNumberOfCores(),
                threadManagerConfiguration.getNumberOfThreads(),
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                new PriorityBlockingQueue<Runnable>()
        );

        sThreadPoolExecutors.put(
                threadManagerConfiguration.getId(),
                threadPoolExecutor
        );

        return threadManagerConfiguration.getId();
    }

    public static String initializeThreadManagerQueuedPool(String threadContextWorkSpaceId) {

        ThreadManagerConfiguration threadManagerConfiguration = new ThreadManagerConfiguration(
                threadContextWorkSpaceId,
                1,
                1
        );

        return initializeThreadManagerPool(threadManagerConfiguration);
    }

    public static String initializeThreadManagerFullPowerPool(String threadManagerPoolSpaceId) {

        ThreadManagerConfiguration threadManagerConfiguration = new ThreadManagerConfiguration(
                threadManagerPoolSpaceId,
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors()
        );

        return initializeThreadManagerPool(threadManagerConfiguration);
    }

    public static String addTaskToThreadManagerPool(String threadManagerPoolId, int priority, Task threadContextRunnable) {

        if (!isThereThreadPool(threadManagerPoolId)) {

            throw new ThreadsManagerPoolNotFoundException();
        }

        ThreadManagerPoolExecutor threadManagerPoolExecutor = sThreadPoolExecutors.get(threadManagerPoolId);
        threadContextRunnable.mPoolId = threadManagerPoolId;
        threadManagerPoolExecutor.mIsDebugMode = sIsDebugMode;
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        threadContextRunnable.stackTrace = "Beginning =>\n";
        int stackTraceCounter = 1;
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            threadContextRunnable.stackTrace += stackTraceCounter + ") " + stackTraceElement.toString() + "\n";
            stackTraceCounter++;
        }
        threadManagerPoolExecutor.submit(threadContextRunnable, priority);
        return threadContextRunnable.getId();
    }

    public static boolean isThereThreadPool(String threadManagerPoolId) {
        return sThreadPoolExecutors.containsKey(threadManagerPoolId);
    }

    public static boolean isThreadManagerPoolBusy(String threadManaherPoolId) {

        if (!isThereThreadPool(threadManaherPoolId)) {

            throw new ThreadsManagerPoolNotFoundException();
        }

        ThreadManagerPoolExecutor threadManagerPoolExecutor = sThreadPoolExecutors.get(threadManaherPoolId);

        return threadManagerPoolExecutor.getTaskCount() != threadManagerPoolExecutor.getCompletedTaskCount();
    }

    public static void awaitAndTerminateThreadManagerPool(String threadManagerPoolId) {

        if (!isThereThreadPool(threadManagerPoolId)) {

            throw new ThreadsManagerPoolNotFoundException();
        }

        ThreadManagerPoolExecutor threadManagerPoolExecutor = sThreadPoolExecutors.get(threadManagerPoolId);
        sThreadPoolExecutors.remove(threadManagerPoolId);
        threadManagerPoolExecutor.shutdown();
    }

    public static List<Runnable> terminateThreadManagerPool(String threadManagerPoolId) {

        if (!isThereThreadPool(threadManagerPoolId)) {

            throw new ThreadsManagerPoolNotFoundException();
        }

        ThreadManagerPoolExecutor threadManagerPoolExecutor = sThreadPoolExecutors.get(threadManagerPoolId);
        sThreadPoolExecutors.remove(threadManagerPoolId);

        return threadManagerPoolExecutor.shutdownNow();
    }

    //endregion

    //region Exceptions

    public static class ThreadsManagerPoolAlreadyExistException extends RuntimeException {

        public ThreadsManagerPoolAlreadyExistException() {

            super(ThreadManager.class.getName() + " : Thread manager pool already existed.");
        }
    }

    public static class ThreadsManagerPoolNotFoundException extends RuntimeException {

        public ThreadsManagerPoolNotFoundException() {

            super(ThreadManager.class.getName() + " : Thread manager pool not found.");
        }
    }

    //endregion
}
