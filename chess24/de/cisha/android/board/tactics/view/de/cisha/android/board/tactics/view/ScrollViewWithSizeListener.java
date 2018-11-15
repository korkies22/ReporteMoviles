/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.widget.ScrollView
 */
package de.cisha.android.board.tactics.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ScrollViewWithSizeListener
extends ScrollView {
    private SizeListener _listener;
    private boolean _sizeInit = false;

    public ScrollViewWithSizeListener(Context context) {
        super(context);
    }

    public ScrollViewWithSizeListener(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ScrollViewWithSizeListener(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        if (this._listener != null && !this._sizeInit) {
            this._listener.onSizeInit(n2);
            this._sizeInit = true;
        }
    }

    public void setSizeListener(SizeListener sizeListener) {
        this._listener = sizeListener;
    }

    public static interface SizeListener {
        public void onSizeInit(int var1);
    }

}
