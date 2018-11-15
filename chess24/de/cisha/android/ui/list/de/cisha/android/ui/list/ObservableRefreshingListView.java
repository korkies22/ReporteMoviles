/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 */
package de.cisha.android.ui.list;

import android.content.Context;
import android.util.AttributeSet;
import de.cisha.android.ui.list.RefreshingListView;
import de.cisha.android.ui.list.UpdatingList;

public class ObservableRefreshingListView
extends RefreshingListView {
    private UpdatingList.UpdatingListListener _listener;

    public ObservableRefreshingListView(Context context) {
        super(context);
    }

    public ObservableRefreshingListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ObservableRefreshingListView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    @Override
    protected void footerReached() {
        if (this._listener != null) {
            this._listener.footerReached();
        }
    }

    @Override
    protected void headerReached() {
        if (this._listener != null) {
            this._listener.headerReached();
        }
    }

    @Override
    public void setListScrollListener(UpdatingList.UpdatingListListener updatingListListener) {
        this._listener = updatingListListener;
    }
}
