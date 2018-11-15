/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Message
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.v4.view;

import android.os.Handler;
import android.os.Message;
import android.support.v4.util.Pools;
import android.support.v4.view.AsyncLayoutInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.concurrent.ArrayBlockingQueue;

private static class AsyncLayoutInflater.InflateThread
extends Thread {
    private static final AsyncLayoutInflater.InflateThread sInstance = new AsyncLayoutInflater.InflateThread();
    private ArrayBlockingQueue<AsyncLayoutInflater.InflateRequest> mQueue = new ArrayBlockingQueue(10);
    private Pools.SynchronizedPool<AsyncLayoutInflater.InflateRequest> mRequestPool = new Pools.SynchronizedPool(10);

    static {
        sInstance.start();
    }

    private AsyncLayoutInflater.InflateThread() {
    }

    public static AsyncLayoutInflater.InflateThread getInstance() {
        return sInstance;
    }

    public void enqueue(AsyncLayoutInflater.InflateRequest inflateRequest) {
        try {
            this.mQueue.put(inflateRequest);
            return;
        }
        catch (InterruptedException interruptedException) {
            throw new RuntimeException("Failed to enqueue async inflate request", interruptedException);
        }
    }

    public AsyncLayoutInflater.InflateRequest obtainRequest() {
        AsyncLayoutInflater.InflateRequest inflateRequest;
        AsyncLayoutInflater.InflateRequest inflateRequest2 = inflateRequest = this.mRequestPool.acquire();
        if (inflateRequest == null) {
            inflateRequest2 = new AsyncLayoutInflater.InflateRequest();
        }
        return inflateRequest2;
    }

    public void releaseRequest(AsyncLayoutInflater.InflateRequest inflateRequest) {
        inflateRequest.callback = null;
        inflateRequest.inflater = null;
        inflateRequest.parent = null;
        inflateRequest.resid = 0;
        inflateRequest.view = null;
        this.mRequestPool.release(inflateRequest);
    }

    @Override
    public void run() {
        do {
            this.runInner();
        } while (true);
    }

    public void runInner() {
        AsyncLayoutInflater.InflateRequest inflateRequest;
        try {
            inflateRequest = this.mQueue.take();
        }
        catch (InterruptedException interruptedException) {
            Log.w((String)AsyncLayoutInflater.TAG, (Throwable)interruptedException);
            return;
        }
        try {
            inflateRequest.view = inflateRequest.inflater.mInflater.inflate(inflateRequest.resid, inflateRequest.parent, false);
        }
        catch (RuntimeException runtimeException) {
            Log.w((String)AsyncLayoutInflater.TAG, (String)"Failed to inflate resource in the background! Retrying on the UI thread", (Throwable)runtimeException);
        }
        Message.obtain((Handler)inflateRequest.inflater.mHandler, (int)0, (Object)inflateRequest).sendToTarget();
    }
}
