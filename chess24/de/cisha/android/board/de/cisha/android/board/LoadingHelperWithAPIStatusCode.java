/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board;

import de.cisha.android.board.LoadingHelper;
import de.cisha.android.board.service.jsonparser.APIStatusCode;

public class LoadingHelperWithAPIStatusCode
extends LoadingHelper {
    private APIStatusCode _statusCode = APIStatusCode.API_ERROR_UNKNOWN;

    public LoadingHelperWithAPIStatusCode(LoadingHelper.LoadingHelperListener loadingHelperListener) {
        super(loadingHelperListener);
    }

    public APIStatusCode getAPIStatusCodeFromLoadingFailed() {
        return this._statusCode;
    }

    public void loadingFailed(Object object, APIStatusCode aPIStatusCode) {
        synchronized (this) {
            this._statusCode = aPIStatusCode;
            super.loadingFailed(object);
            return;
        }
    }
}
