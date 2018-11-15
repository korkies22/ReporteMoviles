/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import android.os.Handler;
import android.os.Looper;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONObject;

public abstract class LoadCommandCallbackWithTimeout<E>
implements LoadCommandCallback<E> {
    private static Timer _timer = new Timer();
    private boolean _canceled = false;
    private boolean _finished;
    private TimerTask _timeoutTask;

    public LoadCommandCallbackWithTimeout() {
        this(20000);
    }

    public LoadCommandCallbackWithTimeout(int n) {
        if (n > 0) {
            this._timeoutTask = new TimerTask(){

                @Override
                public void run() {
                    new Handler(Looper.getMainLooper()).post(new Runnable(){

                        @Override
                        public void run() {
                            if (!LoadCommandCallbackWithTimeout.this._finished) {
                                LoadCommandCallbackWithTimeout.this.loadingFailed(APIStatusCode.INTERNAL_TIMEOUT, "Task timed out", null, null);
                            }
                        }
                    });
                }

            };
            _timer.schedule(this._timeoutTask, n);
        }
    }

    private void killTimeoutTimer() {
        if (this._timeoutTask != null) {
            this._timeoutTask.cancel();
        }
        _timer.purge();
    }

    public void cancel() {
        if (!this._finished && !this._canceled) {
            this.loadingCancelled();
        }
        this._canceled = true;
    }

    protected abstract void failed(APIStatusCode var1, String var2, List<LoadFieldError> var3, JSONObject var4);

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
    public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        synchronized (this) {
            if (!this._finished && this.shouldFinishLoading()) {
                this._finished = true;
                this.failed(aPIStatusCode, string, list, jSONObject);
                this.killTimeoutTimer();
            }
            return;
        }
    }

    @Override
    public void loadingSucceded(E e) {
        synchronized (this) {
            if (!this._finished && this.shouldFinishLoading()) {
                this._finished = true;
                this.succeded(e);
                this.killTimeoutTimer();
            }
            return;
        }
    }

    public boolean shouldFinishLoading() {
        return this.isCanceled() ^ true;
    }

    protected abstract void succeded(E var1);

}
