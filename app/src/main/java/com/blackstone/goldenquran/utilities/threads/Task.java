package com.blackstone.goldenquran.utilities.threads;

import java.util.UUID;

abstract class Task implements TaskCallBack, Runnable {

    //region Variables

    private String mId;
    String mPoolId;
    String stackTrace;

    //endregion

    //region Properties

    public String getId() {
        return mId;
    }

    String getPoolId() {
        return mPoolId;
    }

    //endregion

    //region Constructors

    public Task() {
        mId = UUID.randomUUID().toString();
    }

    public Task(String id) {
        mId = id;
    }

    //endregion
}
