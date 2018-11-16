// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.list;

import android.util.AttributeSet;
import android.content.Context;

public class ObservableRefreshingListView extends RefreshingListView
{
    private UpdatingListListener _listener;
    
    public ObservableRefreshingListView(final Context context) {
        super(context);
    }
    
    public ObservableRefreshingListView(final Context context, final AttributeSet set) {
        super(context, set);
    }
    
    public ObservableRefreshingListView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
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
    
    public void setListScrollListener(final UpdatingListListener listener) {
        this._listener = listener;
    }
}
