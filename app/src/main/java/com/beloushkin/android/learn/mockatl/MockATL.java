package com.beloushkin.android.learn.mockatl;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class MockATL extends AsyncTaskLoader<Boolean> {
    public final String TAG = getClass().getSimpleName();

    private boolean isRunning;

    public boolean isRunning() {
        return isRunning;
    }

    public MockATL(Context context, Bundle args) {
        super(context);
    }

    @Override
    public Boolean loadInBackground() {
        Log.d(TAG, "loadInBackground");
        isRunning = true;
        return processTask();
    }

    @Override
    public void forceLoad() {
        Log.d(TAG, "forceLoad");
        super.forceLoad();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.d(TAG, "onStartLoading");
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        Log.d(TAG, "onStopLoading");
    }

    @Override
    protected void onReset() {
        super.onReset();
        Log.d(TAG, "onReset: ");
        forceLoad();
    }

    @Override
    public void deliverResult(Boolean result) {
        Log.d(TAG, "deliverResult");
        super.deliverResult(result);
    }


    private boolean processTask() {
        // sleep for 5 second
        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            isRunning = false;
            return false;
        }
        isRunning = false;
        return true;
    }
}
