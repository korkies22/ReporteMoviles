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
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import java.util.TimerTask;
import org.json.JSONObject;

class LoadCommandCallbackWithTimeout
extends TimerTask {
    LoadCommandCallbackWithTimeout() {
    }

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

}
