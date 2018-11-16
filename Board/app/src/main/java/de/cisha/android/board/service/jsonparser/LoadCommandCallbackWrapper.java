// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import org.json.JSONObject;
import java.util.List;

public class LoadCommandCallbackWrapper<E> extends LoadCommandCallbackWithTimeout<E>
{
    private LoadCommandCallback<E> _otherCallback;
    
    public LoadCommandCallbackWrapper(final int n, final LoadCommandCallback<E> otherCallback) {
        super(n);
        this._otherCallback = otherCallback;
    }
    
    public LoadCommandCallbackWrapper(final LoadCommandCallback<E> otherCallback) {
        this._otherCallback = otherCallback;
    }
    
    @Override
    protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
        this._otherCallback.loadingFailed(apiStatusCode, s, list, null);
    }
    
    @Override
    public void loadingCancelled() {
        this._otherCallback.loadingCancelled();
        super.loadingCancelled();
    }
    
    @Override
    protected void succeded(final E e) {
        this._otherCallback.loadingSucceded(e);
    }
}
