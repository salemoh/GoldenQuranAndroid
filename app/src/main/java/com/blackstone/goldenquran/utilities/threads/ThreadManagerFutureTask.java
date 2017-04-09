package com.blackstone.goldenquran.utilities.threads;

import android.support.annotation.NonNull;

import java.util.concurrent.FutureTask;

class ThreadManagerFutureTask<T> extends FutureTask<T> implements Comparable<ThreadManagerFutureTask<T>> {

    //region Variables

    private Task mRunnable;
    private int mPriority;

    //endregion

    //region Properties

    public Task getRunnable() {
        return mRunnable;
    }

    public String getId() {
        return mRunnable.getId();
    }

    public String getPoolId() {
        return mRunnable.getPoolId();
    }

    public int getPriority() {
        return mPriority;
    }

    //endregion

    //region Constructors

    ThreadManagerFutureTask(Runnable runnable, T result, int priority) {

        super(runnable, result);

        mRunnable = (Task) runnable;
        mPriority = priority;
    }

    //endregion

    //region Overridden Methods

    @Override
    public int compareTo(@NonNull ThreadManagerFutureTask<T> futureTaskToCompare) {

        return mPriority - futureTaskToCompare.mPriority;
    }

    //endregion
}
