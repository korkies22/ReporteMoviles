/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import android.os.Handler;
import android.os.Looper;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.model.CishaUUID;
import java.util.List;
import org.json.JSONObject;

class DUUIDService
implements Runnable {
    final /* synthetic */ LoadCommandCallback val$callback;

    DUUIDService(LoadCommandCallback loadCommandCallback) {
        this.val$callback = loadCommandCallback;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        do {
            try {
                Thread.sleep(50L);
            }
            catch (InterruptedException interruptedException) {}
        } while (DUUIDService.this._isInitializing);
        Handler handler = new Handler(Looper.getMainLooper());
        if (DUUIDService.this._initialized && DUUIDService.this._duuid != null) {
            handler.post(new Runnable(){

                @Override
                public void run() {
                    1.this.val$callback.loadingSucceded(DUUIDService.this._duuid);
                }
            });
            return;
        }
        handler.post(new Runnable(){

            @Override
            public void run() {
                1.this.val$callback.loadingFailed(APIStatusCode.INTERNAL_TIMEOUT, "Not possible to get DUUID", null, null);
            }
        });
    }

}
