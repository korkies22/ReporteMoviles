/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.DUUIDService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class DUUIDService
implements Runnable {
    DUUIDService() {
    }

    @Override
    public void run() {
        1.this.val$callback.loadingFailed(APIStatusCode.INTERNAL_TIMEOUT, "Not possible to get DUUID", null, null);
    }
}
