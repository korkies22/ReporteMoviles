/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service.jsonparser;

import android.support.v4.app.Fragment;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;

public abstract class FragmentLoadCommandCallback<E>
extends LoadCommandCallbackWithTimeout<E> {
    private Fragment _fragment;

    public FragmentLoadCommandCallback(Fragment fragment) {
        this._fragment = fragment;
    }

    @Override
    public boolean shouldFinishLoading() {
        if (this._fragment.isResumed() && this._fragment.isAdded() && !this._fragment.isRemoving()) {
            return true;
        }
        return false;
    }
}
