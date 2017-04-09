package com.blackstone.goldenquran.utilities.threads;

import java.util.UUID;

public class ThreadManagerConfiguration {

    //region Variables

    private String mId;
    private int mNumberOfCores, mNumberOfThreads;

    //endregion

    //region Properties

    public String getId() {
        return mId;
    }

    public int getNumberOfCores() {
        return mNumberOfCores;
    }

    public int getNumberOfThreads() {
        return mNumberOfThreads;
    }

    //endregion

    //region Constructors

    public ThreadManagerConfiguration(String id, int numberOfThreads, int numberOfCores) {

        mId = id;
        mNumberOfCores = numberOfCores;
        mNumberOfThreads = numberOfThreads;
    }
    public ThreadManagerConfiguration(int numberOfThreads, int numberOfCores) {

        mId = UUID.randomUUID().toString();
        mNumberOfCores = numberOfCores;
        mNumberOfThreads = numberOfThreads;
    }

    //endregion
}
