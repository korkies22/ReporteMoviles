// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board;

import de.cisha.android.board.service.jsonparser.APIStatusCode;

public class LoadingHelperWithAPIStatusCode extends LoadingHelper
{
    private APIStatusCode _statusCode;
    
    public LoadingHelperWithAPIStatusCode(final LoadingHelperListener loadingHelperListener) {
        super(loadingHelperListener);
        this._statusCode = APIStatusCode.API_ERROR_UNKNOWN;
    }
    
    public APIStatusCode getAPIStatusCodeFromLoadingFailed() {
        return this._statusCode;
    }
    
    public void loadingFailed(final Object o, final APIStatusCode statusCode) {
        synchronized (this) {
            this._statusCode = statusCode;
            super.loadingFailed(o);
        }
    }
}
