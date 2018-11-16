// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.tactics.view;

import android.util.AttributeSet;
import android.content.Context;
import android.widget.ScrollView;

public class ScrollViewWithSizeListener extends ScrollView
{
    private SizeListener _listener;
    private boolean _sizeInit;
    
    public ScrollViewWithSizeListener(final Context context) {
        super(context);
        this._sizeInit = false;
    }
    
    public ScrollViewWithSizeListener(final Context context, final AttributeSet set) {
        super(context, set);
        this._sizeInit = false;
    }
    
    public ScrollViewWithSizeListener(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this._sizeInit = false;
    }
    
    protected void onSizeChanged(final int n, final int n2, final int n3, final int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        if (this._listener != null && !this._sizeInit) {
            this._listener.onSizeInit(n2);
            this._sizeInit = true;
        }
    }
    
    public void setSizeListener(final SizeListener listener) {
        this._listener = listener;
    }
    
    public interface SizeListener
    {
        void onSizeInit(final int p0);
    }
}
