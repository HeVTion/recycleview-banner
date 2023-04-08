package com.example.mydemo.ui.widget;

import android.os.Handler;
import android.os.Looper;

public class SimpleTimer {

    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private Runnable mRunnable;
    private long mDelayMillis;
    private boolean mIsRunning = false;

    public SimpleTimer(long delayMillis, Runnable runnable) {
        this.mDelayMillis = delayMillis;
        this.mRunnable = runnable;
    }

    public void start() {
        if (mIsRunning) {
            return;
        }
        mIsRunning = true;
        mHandler.postDelayed(mRunnable, mDelayMillis);
    }

    public void reStart() {
        mIsRunning = true;
        mHandler.postDelayed(mRunnable, mDelayMillis);
    }

    public void stop() {
        if (!mIsRunning) {
            return;
        }
        mIsRunning = false;
        mHandler.removeCallbacks(mRunnable);
    }

    public boolean isRunning() {
        return mIsRunning;
    }

    public void setDelayMillis(long delayMillis) {
        this.mDelayMillis = delayMillis;
    }

    public long getDelayMillis() {
        return mDelayMillis;
    }

    public void setRunnable(Runnable runnable) {
        this.mRunnable = runnable;
    }

    public Runnable getRunnable() {
        return mRunnable;
    }
}
