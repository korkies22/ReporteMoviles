// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import android.support.v4.app.Fragment;

public abstract class FragmentLoadCommandCallback<E> extends LoadCommandCallbackWithTimeout<E>
{
    private Fragment _fragment;
    
    public FragmentLoadCommandCallback(final Fragment fragment) {
        this._fragment = fragment;
    }
    
    @Override
    public boolean shouldFinishLoading() {
        return this._fragment.isResumed() && this._fragment.isAdded() && !this._fragment.isRemoving();
    }
}
