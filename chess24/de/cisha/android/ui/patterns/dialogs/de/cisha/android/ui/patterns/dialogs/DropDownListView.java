/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.view.animation.AnticipateInterpolator
 *  android.view.animation.BounceInterpolator
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.view.animation.OvershootInterpolator
 *  android.view.animation.Transformation
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 */
package de.cisha.android.ui.patterns.dialogs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import de.cisha.android.ui.patterns.R;

public class DropDownListView
extends LinearLayout {
    private boolean _animationIsRunning;
    private View _contentView;
    private int _duration;
    private ViewGroup _headViewGroup;
    private int _maxYsize;
    private boolean _opened = false;
    private ViewGroup _resizeableView;

    public DropDownListView(Context context) {
        super(context);
        this.init(context);
    }

    public DropDownListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(context);
    }

    public DropDownListView(Context context, ViewGroup viewGroup, ViewGroup viewGroup2) {
        this(context);
        this.setHeadView((View)viewGroup, -1, -1);
        this.setContentView((View)viewGroup2);
    }

    private void init(Context context) {
        this._duration = 1000;
        this.setOrientation(1);
        DropDownListView.inflate((Context)context, (int)R.layout.dropdown_list_view, (ViewGroup)this);
        this._headViewGroup = (ViewGroup)this.findViewById(R.id.dropdown_list_view_headgroupview);
        this._resizeableView = (ViewGroup)this.findViewById(R.id.dropdown_list_view_resizeableviewgroup);
    }

    private void openCloseContentView(final boolean bl, int n, boolean bl2) {
        if (!this._animationIsRunning) {
            this._animationIsRunning = true;
            Object object = new int[2];
            this._resizeableView.getLocationInWindow(object);
            this.getLocationInWindow(new int[2]);
            object = new ResizeHeightAnimation((View)this._resizeableView, n, this._maxYsize, bl ^ true, bl2);
            object.setDuration((long)this._duration);
            object.setAnimationListener(new Animation.AnimationListener(){

                public void onAnimationEnd(Animation animation) {
                    DropDownListView.this.clearAnimation();
                    DropDownListView.this._opened = bl;
                    DropDownListView.this._animationIsRunning = false;
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                }
            });
            this.startAnimation((Animation)object);
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

    protected void onMeasure(int n, int n2) {
        int n3 = this._maxYsize;
        int n4 = View.MeasureSpec.getSize((int)n2);
        int n5 = this._headViewGroup != null ? this._headViewGroup.getMeasuredHeight() : 0;
        this._maxYsize = Math.max(n3, n4 - n5);
        super.onMeasure(n, n2);
    }

    public void openCloseContentView(boolean bl, boolean bl2) {
        if (this._contentView != null) {
            this._contentView.measure(View.MeasureSpec.makeMeasureSpec((int)1073741824, (int)this.getWidth()), View.MeasureSpec.makeMeasureSpec((int)Integer.MIN_VALUE, (int)100000));
            this.openCloseContentView(bl, this._contentView.getMeasuredHeight(), bl2);
        }
    }

    public void setContentView(View view) {
        if (this._contentView != null) {
            this._resizeableView.removeViewInLayout(this._contentView);
        }
        this._contentView = view;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        this._resizeableView.addView(view, (ViewGroup.LayoutParams)layoutParams);
        this.requestLayout();
    }

    public void setDuration(int n) {
        this._duration = n;
    }

    public void setHeadView(View view, int n, int n2) {
        this._headViewGroup.removeAllViews();
        this._headViewGroup.addView(view, n, n2);
    }

    public void setHeadView(View view, LinearLayout.LayoutParams layoutParams) {
        this._headViewGroup.removeAllViews();
        this._headViewGroup.addView(view, (ViewGroup.LayoutParams)layoutParams);
        this.requestLayout();
    }

    public void setMaxHeight(int n) {
        int n2 = n;
        if (this._headViewGroup != null) {
            n2 = n - this._headViewGroup.getMeasuredHeight();
        }
        this._maxYsize = n2;
    }

    public class ResizeHeightAnimation
    extends Animation {
        private int _offset;
        private int _oldViewHeight;
        private boolean _open;
        private View _view;

        public ResizeHeightAnimation(View object, int n, int n2, boolean bl, boolean bl2) {
            this._view = object;
            this._oldViewHeight = this._view.getHeight();
            this._offset = Math.min(n, n2) - this._oldViewHeight;
            this._open = bl ^ true;
            if (bl2) {
                DropDownListView.this = n < n2 ? new OvershootInterpolator() : new BounceInterpolator();
                object = n < n2 ? new AnticipateInterpolator() : new DecelerateInterpolator();
                if (!this._open) {
                    DropDownListView.this = object;
                }
                this.setInterpolator((Interpolator)DropDownListView.this);
            }
        }

        protected void applyTransformation(float f, Transformation transformation) {
            transformation = this._view.getLayoutParams();
            int n = this._open ? this._oldViewHeight + (int)(f * (float)this._offset) : (int)((float)this._oldViewHeight * (1.0f - f));
            transformation.height = n;
            this._view.requestLayout();
        }
    }

}
