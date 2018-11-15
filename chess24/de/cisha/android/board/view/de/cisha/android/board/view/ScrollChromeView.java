/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 */
package de.cisha.android.board.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ScrollChromeView
extends LinearLayout {
    private static final int THRES_OPEN_CLOSE = 15;
    private boolean _active = true;
    private boolean _closed;
    private boolean _fixed;
    private float _preferredHeight;
    private View _scrollView;
    private ViewGroup _topBar;
    private float _topBarHeight;
    private float _yLastDiff;
    private float _yPosition;
    private float _yStartPosition;

    public ScrollChromeView(Context context) {
        super(context);
        this.init();
        ScrollChromeView.inflate((Context)this.getContext(), (int)2131427530, (ViewGroup)this);
        this._topBar = (ViewGroup)this.findViewById(2131296938);
        this._scrollView = this.findViewById(2131296937);
    }

    public ScrollChromeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.setOrientation(1);
    }

    private boolean openOrCloseTopBar() {
        boolean bl;
        int n = (int)this._topBarHeight;
        boolean bl2 = true;
        boolean bl3 = n == 0;
        float f = n;
        boolean bl4 = f >= this._preferredHeight;
        if (this._active && !bl3 && !bl4) {
            float f2 = this._preferredHeight;
            f = (this._closed ? 15 > n : this._preferredHeight - 15.0f > f) ? 0.0f : f2;
            this._topBarHeight = f;
            this.requestLayout();
            bl = true;
        } else {
            bl = false;
        }
        if (f != 0.0f) {
            bl2 = false;
        }
        this._closed = bl2;
        return bl;
    }

    public void activateTopbar() {
        if (!this._active) {
            this._topBarHeight = 0.0f;
        }
        this._active = true;
        this._topBar.setVisibility(0);
        this.requestLayout();
    }

    public void deactivateTopbar() {
        this._active = false;
        this._topBarHeight = 0.0f;
        this._topBar.setVisibility(8);
        this._scrollView.bringToFront();
        this.requestLayout();
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this._active && !this._fixed) {
            int n = motionEvent.getAction();
            float f = motionEvent.getY();
            boolean bl = true;
            if (n == 2 && (this._yStartPosition > this._preferredHeight || this._closed)) {
                float f2;
                if (f > this._yStartPosition - this._preferredHeight) {
                    f2 = f - this._yPosition;
                    if (this._yLastDiff * f2 >= 0.0f) {
                        bl = false;
                    }
                    if (f2 < 0.0f && bl) {
                        this._yStartPosition = f;
                    }
                    this._yLastDiff = f2;
                } else {
                    f2 = 0.0f;
                }
                if ((f2 += this._topBarHeight) < 0.0f) {
                    f2 = 0.0f;
                }
                if (f2 >= this._preferredHeight) {
                    f2 = this._preferredHeight;
                }
                if (f2 != this._topBarHeight) {
                    this._topBarHeight = f2;
                    this.requestLayout();
                }
            } else if (n == 0) {
                this._yStartPosition = f;
            } else if ((n == 3 || n == 1) && this.openOrCloseTopBar()) {
                return true;
            }
            this._yPosition = f;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public View getScrollView() {
        return this._scrollView;
    }

    public ViewGroup getTopBarView() {
        return this._topBar;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this._topBar = (ViewGroup)this.getChildAt(0);
        this._scrollView = this.getChildAt(1);
        this._scrollView.bringToFront();
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        bl = this._active;
        int n5 = 0;
        if (bl) {
            this._topBar.layout(n, n2 - this.getTopBarView().getMeasuredHeight() + (int)this._topBarHeight, n3, (int)this._topBarHeight + n2);
        } else {
            this._topBar.layout(0, 0, 0, 0);
        }
        View view = this._scrollView;
        if (this._active) {
            n5 = (int)this._topBarHeight;
        }
        view.layout(n, n2 + n5, n3, n4);
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        this._preferredHeight = this._topBarHeight = (float)this._topBar.getMeasuredHeight();
        super.onSizeChanged(n, n2, n3, n4);
    }

    public void setTopbarFixed(boolean bl) {
        this._fixed = bl;
    }
}
