// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.dialogs;

import android.view.animation.Transformation;
import android.view.animation.Interpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout.LayoutParams;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout.LayoutParams;
import android.view.View.MeasureSpec;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import de.cisha.android.ui.patterns.R;
import android.util.AttributeSet;
import android.content.Context;
import android.view.ViewGroup;
import android.view.View;
import android.widget.LinearLayout;

public class DropDownListView extends LinearLayout
{
    private boolean _animationIsRunning;
    private View _contentView;
    private int _duration;
    private ViewGroup _headViewGroup;
    private int _maxYsize;
    private boolean _opened;
    private ViewGroup _resizeableView;
    
    public DropDownListView(final Context context) {
        super(context);
        this._opened = false;
        this.init(context);
    }
    
    public DropDownListView(final Context context, final AttributeSet set) {
        super(context, set);
        this._opened = false;
        this.init(context);
    }
    
    public DropDownListView(final Context context, final ViewGroup viewGroup, final ViewGroup contentView) {
        this(context);
        this.setHeadView((View)viewGroup, -1, -1);
        this.setContentView((View)contentView);
    }
    
    private void init(final Context context) {
        this._duration = 1000;
        this.setOrientation(1);
        inflate(context, R.layout.dropdown_list_view, (ViewGroup)this);
        this._headViewGroup = (ViewGroup)this.findViewById(R.id.dropdown_list_view_headgroupview);
        this._resizeableView = (ViewGroup)this.findViewById(R.id.dropdown_list_view_resizeableviewgroup);
    }
    
    private void openCloseContentView(final boolean b, final int n, final boolean b2) {
        if (!this._animationIsRunning) {
            this._animationIsRunning = true;
            this._resizeableView.getLocationInWindow(new int[2]);
            this.getLocationInWindow(new int[2]);
            final ResizeHeightAnimation resizeHeightAnimation = new ResizeHeightAnimation((View)this._resizeableView, n, this._maxYsize, b ^ true, b2);
            resizeHeightAnimation.setDuration((long)this._duration);
            resizeHeightAnimation.setAnimationListener((Animation.AnimationListener)new Animation.AnimationListener() {
                public void onAnimationEnd(final Animation animation) {
                    DropDownListView.this.clearAnimation();
                    DropDownListView.this._opened = b;
                    DropDownListView.this._animationIsRunning = false;
                }
                
                public void onAnimationRepeat(final Animation animation) {
                }
                
                public void onAnimationStart(final Animation animation) {
                }
            });
            this.startAnimation((Animation)resizeHeightAnimation);
        }
    }
    
    public boolean contentViewIsOpened() {
        return this._opened;
    }
    
    public int getDuration() {
        return this._duration;
    }
    
    public ViewGroup getHeadViewGroup() {
        return this._headViewGroup;
    }
    
    public boolean isClosed() {
        return this._opened ^ true;
    }
    
    protected void onMeasure(final int n, final int n2) {
        final int maxYsize = this._maxYsize;
        final int size = View.MeasureSpec.getSize(n2);
        int measuredHeight;
        if (this._headViewGroup != null) {
            measuredHeight = this._headViewGroup.getMeasuredHeight();
        }
        else {
            measuredHeight = 0;
        }
        this._maxYsize = Math.max(maxYsize, size - measuredHeight);
        super.onMeasure(n, n2);
    }
    
    public void openCloseContentView(final boolean b, final boolean b2) {
        if (this._contentView != null) {
            this._contentView.measure(View.MeasureSpec.makeMeasureSpec(1073741824, this.getWidth()), View.MeasureSpec.makeMeasureSpec(Integer.MIN_VALUE, 100000));
            this.openCloseContentView(b, this._contentView.getMeasuredHeight(), b2);
        }
    }
    
    public void setContentView(final View contentView) {
        if (this._contentView != null) {
            this._resizeableView.removeViewInLayout(this._contentView);
        }
        this._contentView = contentView;
        this._resizeableView.addView(contentView, (ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, -1));
        this.requestLayout();
    }
    
    public void setDuration(final int duration) {
        this._duration = duration;
    }
    
    public void setHeadView(final View view, final int n, final int n2) {
        this._headViewGroup.removeAllViews();
        this._headViewGroup.addView(view, n, n2);
    }
    
    public void setHeadView(final View view, final LinearLayout.LayoutParams linearLayout.LayoutParams) {
        this._headViewGroup.removeAllViews();
        this._headViewGroup.addView(view, (ViewGroup.LayoutParams)linearLayout.LayoutParams);
        this.requestLayout();
    }
    
    public void setMaxHeight(final int n) {
        int maxYsize = n;
        if (this._headViewGroup != null) {
            maxYsize = n - this._headViewGroup.getMeasuredHeight();
        }
        this._maxYsize = maxYsize;
    }
    
    public class ResizeHeightAnimation extends Animation
    {
        private int _offset;
        private int _oldViewHeight;
        private boolean _open;
        private View _view;
        
        public ResizeHeightAnimation(final View view, final int n, final int n2, final boolean b, final boolean b2) {
            this._view = view;
            this._oldViewHeight = this._view.getHeight();
            this._offset = Math.min(n, n2) - this._oldViewHeight;
            this._open = (b ^ true);
            if (b2) {
                Object interpolator;
                if (n < n2) {
                    interpolator = new OvershootInterpolator();
                }
                else {
                    interpolator = new BounceInterpolator();
                }
                Object o;
                if (n < n2) {
                    o = new AnticipateInterpolator();
                }
                else {
                    o = new DecelerateInterpolator();
                }
                if (!this._open) {
                    interpolator = o;
                }
                this.setInterpolator((Interpolator)interpolator);
            }
        }
        
        protected void applyTransformation(final float n, final Transformation transformation) {
            final ViewGroup.LayoutParams layoutParams = this._view.getLayoutParams();
            int height;
            if (this._open) {
                height = this._oldViewHeight + (int)(n * this._offset);
            }
            else {
                height = (int)(this._oldViewHeight * (1.0f - n));
            }
            layoutParams.height = height;
            this._view.requestLayout();
        }
    }
}
