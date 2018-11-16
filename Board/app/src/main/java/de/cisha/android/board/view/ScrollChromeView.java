// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.view;

import android.view.MotionEvent;
import android.util.AttributeSet;
import android.content.Context;
import android.view.ViewGroup;
import android.view.View;
import android.widget.LinearLayout;

public class ScrollChromeView extends LinearLayout
{
    private static final int THRES_OPEN_CLOSE = 15;
    private boolean _active;
    private boolean _closed;
    private boolean _fixed;
    private float _preferredHeight;
    private View _scrollView;
    private ViewGroup _topBar;
    private float _topBarHeight;
    private float _yLastDiff;
    private float _yPosition;
    private float _yStartPosition;
    
    public ScrollChromeView(final Context context) {
        super(context);
        this._active = true;
        this.init();
        inflate(this.getContext(), 2131427530, (ViewGroup)this);
        this._topBar = (ViewGroup)this.findViewById(2131296938);
        this._scrollView = this.findViewById(2131296937);
    }
    
    public ScrollChromeView(final Context context, final AttributeSet set) {
        super(context, set);
        this._active = true;
        this.init();
    }
    
    private void init() {
        this.setOrientation(1);
    }
    
    private boolean openOrCloseTopBar() {
        final int n = (int)this._topBarHeight;
        boolean closed = true;
        final boolean b = n == 0;
        float topBarHeight = n;
        final boolean b2 = topBarHeight >= this._preferredHeight;
        boolean b3;
        if (this._active && !b && !b2) {
            final float preferredHeight = this._preferredHeight;
            topBarHeight = ((this._closed ? (15 > n) : (this._preferredHeight - 15.0f > topBarHeight)) ? 0.0f : preferredHeight);
            this._topBarHeight = topBarHeight;
            this.requestLayout();
            b3 = true;
        }
        else {
            b3 = false;
        }
        if (topBarHeight != 0.0f) {
            closed = false;
        }
        this._closed = closed;
        return b3;
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
    
    public boolean dispatchTouchEvent(final MotionEvent motionEvent) {
        if (this._active && !this._fixed) {
            final int action = motionEvent.getAction();
            final float y = motionEvent.getY();
            boolean b = true;
            if (action == 2 && (this._yStartPosition > this._preferredHeight || this._closed)) {
                float yLastDiff;
                if (y > this._yStartPosition - this._preferredHeight) {
                    yLastDiff = y - this._yPosition;
                    if (this._yLastDiff * yLastDiff >= 0.0f) {
                        b = false;
                    }
                    if (yLastDiff < 0.0f && b) {
                        this._yStartPosition = y;
                    }
                    this._yLastDiff = yLastDiff;
                }
                else {
                    yLastDiff = 0.0f;
                }
                float preferredHeight = yLastDiff + this._topBarHeight;
                if (preferredHeight < 0.0f) {
                    preferredHeight = 0.0f;
                }
                if (preferredHeight >= this._preferredHeight) {
                    preferredHeight = this._preferredHeight;
                }
                if (preferredHeight != this._topBarHeight) {
                    this._topBarHeight = preferredHeight;
                    this.requestLayout();
                }
            }
            else if (action == 0) {
                this._yStartPosition = y;
            }
            else if ((action == 3 || action == 1) && this.openOrCloseTopBar()) {
                return true;
            }
            this._yPosition = y;
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
        (this._scrollView = this.getChildAt(1)).bringToFront();
    }
    
    protected void onLayout(final boolean b, final int n, final int n2, final int n3, final int n4) {
        final boolean active = this._active;
        int n5 = 0;
        if (active) {
            this._topBar.layout(n, n2 - this.getTopBarView().getMeasuredHeight() + (int)this._topBarHeight, n3, (int)this._topBarHeight + n2);
        }
        else {
            this._topBar.layout(0, 0, 0, 0);
        }
        final View scrollView = this._scrollView;
        if (this._active) {
            n5 = (int)this._topBarHeight;
        }
        scrollView.layout(n, n2 + n5, n3, n4);
    }
    
    protected void onMeasure(final int n, final int n2) {
        super.onMeasure(n, n2);
    }
    
    protected void onSizeChanged(final int n, final int n2, final int n3, final int n4) {
        this._topBarHeight = this._topBar.getMeasuredHeight();
        this._preferredHeight = this._topBarHeight;
        super.onSizeChanged(n, n2, n3, n4);
    }
    
    public void setTopbarFixed(final boolean fixed) {
        this._fixed = fixed;
    }
}
