/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class LoadCommandCallbackWithTimeout
implements Runnable {
    LoadCommandCallbackWithTimeout() {
    }

    @Override
    public void run() {
        if (!1.this.this$0._finished) {
            1.this.this$0.loadingFailed(APIStatusCode.INTERNAL_TIMEOUT, "Task timed out", null, null);
        }
    }
}
