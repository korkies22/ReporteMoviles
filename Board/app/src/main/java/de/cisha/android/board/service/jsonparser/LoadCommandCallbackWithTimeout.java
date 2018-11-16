// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import org.json.JSONObject;
import java.util.List;
import android.os.Handler;
import android.os.Looper;
import java.util.TimerTask;
import java.util.Timer;

public abstract class LoadCommandCallbackWithTimeout<E> implements LoadCommandCallback<E>
{
    private static Timer _timer;
    private boolean _canceled;
    private boolean _finished;
    private TimerTask _timeoutTask;
    
    static {
        LoadCommandCallbackWithTimeout._timer = new Timer();
    }
    
    public LoadCommandCallbackWithTimeout() {
        this(20000);
    }
    
    public LoadCommandCallbackWithTimeout(final int n) {
        this._canceled = false;
        if (n > 0) {
            this._timeoutTask = new TimerTask() {
                @Override
                public void run() {
                    new Handler(Looper.getMainLooper()).post((Runnable)new Runnable() {
                        @Override
                        public void run() {
                            if (!LoadCommandCallbackWithTimeout.this._finished) {
                                LoadCommandCallbackWithTimeout.this.loadingFailed(APIStatusCode.INTERNAL_TIMEOUT, "Task timed out", null, null);
                            }
                        }
                    });
                }
            };
            LoadCommandCallbackWithTimeout._timer.schedule(this._timeoutTask, n);
        }
    }
    
    private void killTimeoutTimer() {
        if (this._timeoutTask != null) {
            this._timeoutTask.cancel();
        }
        LoadCommandCallbackWithTimeout._timer.purge();
    }
    
    public void cancel() {
        if (!this._finished && !this._canceled) {
            this.loadingCancelled();
        }
        this._canceled = true;
    }
    
    protected abstract void failed(final APIStatusCode p0, final String p1, final List<LoadFieldError> p2, final JSONObject p3);
    
    public boolean isCanceled() {
        return this._canceled;
    }
    
    public boolean isLoadingFinished() {
        return this._finished;
    }
    
    @Override
    public void loadingCancelled() {
    }
    
    @Override
    public void loadingFailed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
        synchronized (this) {
            if (!this._finished && this.shouldFinishLoading()) {
                this._finished = true;
                this.failed(apiStatusCode, s, list, jsonObject);
                this.killTimeoutTimer();
            }
        }
    }
    
    @Override
    public void loadingSucceded(final E e) {
        synchronized (this) {
            if (!this._finished && this.shouldFinishLoading()) {
                this._finished = true;
                this.succeded(e);
                this.killTimeoutTimer();
            }
        }
    }
    
    public boolean shouldFinishLoading() {
        return this.isCanceled() ^ true;
    }
    
    protected abstract void succeded(final E p0);
}
