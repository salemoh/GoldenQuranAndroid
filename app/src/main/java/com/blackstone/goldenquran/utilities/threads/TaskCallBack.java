package com.blackstone.goldenquran.utilities.threads;

interface TaskCallBack {

    //region Methods

    void onPreRun();

    void onPreRunFailure(Exception ex);

    void onRunSuccess();

    void onRunFailure(Exception ex);

    //endregion
}
