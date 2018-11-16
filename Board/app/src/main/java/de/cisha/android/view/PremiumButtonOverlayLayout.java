// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.ViewGroup.LayoutParams;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import de.cisha.android.board.action.BoardAction;
import android.widget.FrameLayout;

public class PremiumButtonOverlayLayout extends FrameLayout
{
    private static final int OVERLAY_COLOR;
    private BoardAction _action;
    private View _button;
    private boolean _enableOverlay;
    private boolean _firstClickDone;
    private boolean _hideBeforeClickedEnabled;
    private OverlayLayout _overlay;
    
    static {
        OVERLAY_COLOR = Color.argb(200, 255, 255, 255);
    }
    
    public PremiumButtonOverlayLayout(final Context context, final AttributeSet set) {
        super(context, set);
        this._enableOverlay = true;
        this._hideBeforeClickedEnabled = true;
        inflate(this.getContext(), 2131427509, (ViewGroup)this);
        inflate(this.getContext(), this.getLayoutResIdForButton(), (ViewGroup)this);
        this._overlay = (OverlayLayout)this.findViewById(2131296801);
        (this._button = this.findViewById(2131296802)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                if (PremiumButtonOverlayLayout.this._action != null) {
                    PremiumButtonOverlayLayout.this._action.perform();
                }
            }
        });
    }
    
    private void updateOverlayViews() {
        final OverlayLayout overlay = this._overlay;
        final int n = 0;
        if (overlay != null) {
            this._overlay.setOverlayEnabled(this._enableOverlay && (!this._hideBeforeClickedEnabled || this._firstClickDone));
        }
        if (this._button != null) {
            final View button = this._button;
            int visibility = 0;
            Label_0097: {
                if (this._enableOverlay) {
                    visibility = n;
                    if (!this._hideBeforeClickedEnabled) {
                        break Label_0097;
                    }
                    if (this._firstClickDone) {
                        visibility = n;
                        break Label_0097;
                    }
                }
                visibility = 8;
            }
            button.setVisibility(visibility);
        }
    }
    
    public void addView(final View view) {
        if (this._overlay != null) {
            this._overlay.addView(view);
            return;
        }
        super.addView(view);
    }
    
    public void addView(final View view, final int n) {
        if (this._overlay != null) {
            this._overlay.addView(view, n);
            return;
        }
        super.addView(view, n);
    }
    
    public void addView(final View view, final int n, final int n2) {
        if (this._overlay != null) {
            this._overlay.addView(view, n, n2);
            return;
        }
        super.addView(view, n, n2);
    }
    
    public void addView(final View view, final int n, final ViewGroup.LayoutParams viewGroup.LayoutParams) {
        if (this._overlay != null) {
            this._overlay.addView(view, n, viewGroup.LayoutParams);
            return;
        }
        super.addView(view, n, viewGroup.LayoutParams);
    }
    
    public void addView(final View view, final ViewGroup.LayoutParams viewGroup.LayoutParams) {
        if (this._overlay != null) {
            this._overlay.addView(view, viewGroup.LayoutParams);
            return;
        }
        super.addView(view, viewGroup.LayoutParams);
    }
    
    protected int getLayoutResIdForButton() {
        return 2131427510;
    }
    
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.updateOverlayViews();
    }
    
    public boolean onInterceptTouchEvent(final MotionEvent motionEvent) {
        if (!this._firstClickDone) {
            this._firstClickDone = true;
            this.updateOverlayViews();
            return this._enableOverlay;
        }
        return false;
    }
    
    public boolean onTouchEvent(final MotionEvent motionEvent) {
        return true;
    }
    
    public void setHideBeforeClickedEnabled(final boolean hideBeforeClickedEnabled) {
        this._hideBeforeClickedEnabled = hideBeforeClickedEnabled;
    }
    
    public void setOverlayButtonAction(final BoardAction action) {
        this._action = action;
    }
    
    public void setPremiumOverlayEnabled(final boolean enableOverlay) {
        this._enableOverlay = enableOverlay;
        this.updateOverlayViews();
    }
    
    public static class OverlayLayout extends FrameLayout
    {
        private boolean _overlayEnabled;
        private Paint _paint;
        
        public OverlayLayout(final Context context) {
            super(context);
            this.init();
        }
        
        public OverlayLayout(final Context context, final AttributeSet set) {
            super(context, set);
            this.init();
        }
        
        private void init() {
            this._overlayEnabled = false;
            (this._paint = new Paint()).setColor(PremiumButtonOverlayLayout.OVERLAY_COLOR);
        }
        
        protected void dispatchDraw(final Canvas canvas) {
            super.dispatchDraw(canvas);
            if (this._overlayEnabled) {
                canvas.drawPaint(this._paint);
            }
        }
        
        public boolean onInterceptTouchEvent(final MotionEvent motionEvent) {
            return this._overlayEnabled;
        }
        
        public boolean onTouchEvent(final MotionEvent motionEvent) {
            return true;
        }
        
        public void setOverlayEnabled(final boolean overlayEnabled) {
            this._overlayEnabled = overlayEnabled;
            this.invalidate();
        }
    }
}
