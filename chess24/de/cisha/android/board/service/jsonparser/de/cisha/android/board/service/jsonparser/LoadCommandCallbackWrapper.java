/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

public class LoadCommandCallbackWrapper<E>
extends LoadCommandCallbackWithTimeout<E> {
    private LoadCommandCallback<E> _otherCallback;

    public LoadCommandCallbackWrapper(int n, LoadCommandCallback<E> loadCommandCallback) {
        super(n);
        this._otherCallback = loadCommandCallback;
    }

    public LoadCommandCallbackWrapper(LoadCommandCallback<E> loadCommandCallback) {
        this._otherCallback = loadCommandCallback;
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        this._otherCallback.loadingFailed(aPIStatusCode, string, list, null);
    }

    @Override
    public void loadingCancelled() {
        this._otherCallback.loadingCancelled();
        super.loadingCancelled();
    }

    @Override
    protected void succeded(E e) {
        this._otherCallback.loadingSucceded(e);
    }
}
