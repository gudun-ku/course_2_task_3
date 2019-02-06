package com.beloushkin.android.learn.mockatl;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class MockATL extends AsyncTaskLoader<Boolean> {
    public final String TAG = getClass().getSimpleName();

    public static final String MOCK_TIME = "MOCK_TIME";

    private boolean mIsDone = false;
    private boolean mIsRunning = false;
    private Bundle args;

    public MockATL(Context context, Bundle args) {
        super(context);
        this.args = args;
    }

    public boolean isRunning() {
        return mIsRunning;
    }

    public boolean isDone() {
        return mIsDone;
    }

    @Override
    protected void onStartLoading() {
      if (args == null) {
          return;
      }

      if (mIsDone) {
          deliverResult(mIsDone);
      } else {
          forceLoad();
      }
    }

    @Override
    public Boolean loadInBackground() {
        Log.d(TAG, "loadInBackground");
        int mockTime = args.getInt(MOCK_TIME);
        return processTask(mockTime);
    }

    @Override
    protected void onReset() {
        super.onReset();
        Log.d(TAG, "onReset: ");
    }

    @Override
    public void deliverResult(Boolean result) {
        Log.d(TAG, "deliverResult");
        super.deliverResult(result);
    }

    private boolean processTask(int mockTime) {
        // sleep for n seconds
        mIsDone = false;
        mIsRunning = true;

        try {
            TimeUnit.MILLISECONDS.sleep(mockTime);
        } catch (InterruptedException e) {
            mIsRunning = false;
            return false;
        }

        mIsDone = true;
        mIsRunning = false;

        return true;
    }
}
