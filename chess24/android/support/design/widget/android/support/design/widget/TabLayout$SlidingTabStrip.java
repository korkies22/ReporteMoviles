/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.animation.Interpolator
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 */
package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.design.widget.AnimationUtils;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

private class TabLayout.SlidingTabStrip
extends LinearLayout {
    private ValueAnimator mIndicatorAnimator;
    private int mIndicatorLeft;
    private int mIndicatorRight;
    private int mLayoutDirection;
    private int mSelectedIndicatorHeight;
    private final Paint mSelectedIndicatorPaint;
    int mSelectedPosition;
    float mSelectionOffset;

    TabLayout.SlidingTabStrip(Context context) {
        super(context);
        this.mSelectedPosition = -1;
        this.mLayoutDirection = -1;
        this.mIndicatorLeft = -1;
        this.mIndicatorRight = -1;
        this.setWillNotDraw(false);
        this.mSelectedIndicatorPaint = new Paint();
    }

    private void updateIndicatorPosition() {
        int n;
        int n2;
        View view = this.getChildAt(this.mSelectedPosition);
        if (view != null && view.getWidth() > 0) {
            int n3;
            int n4 = view.getLeft();
            n2 = n3 = view.getRight();
            n = n4;
            if (this.mSelectionOffset > 0.0f) {
                n2 = n3;
                n = n4;
                if (this.mSelectedPosition < this.getChildCount() - 1) {
                    view = this.getChildAt(this.mSelectedPosition + 1);
                    n = (int)(this.mSelectionOffset * (float)view.getLeft() + (1.0f - this.mSelectionOffset) * (float)n4);
                    n2 = (int)(this.mSelectionOffset * (float)view.getRight() + (1.0f - this.mSelectionOffset) * (float)n3);
                }
            }
        } else {
            n = -1;
            n2 = -1;
        }
        this.setIndicatorPosition(n, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    void animateIndicatorToPosition(final int n, int n2) {
        int n3;
        if (this.mIndicatorAnimator != null && this.mIndicatorAnimator.isRunning()) {
            this.mIndicatorAnimator.cancel();
        }
        final int n4 = ViewCompat.getLayoutDirection((View)this) == 1 ? 1 : 0;
        View view = this.getChildAt(n);
        if (view == null) {
            this.updateIndicatorPosition();
            return;
        }
        final int n5 = view.getLeft();
        final int n6 = view.getRight();
        if (Math.abs(n - this.mSelectedPosition) <= 1) {
            n4 = this.mIndicatorLeft;
            n3 = this.mIndicatorRight;
        } else {
            n3 = TabLayout.this.dpToPx(24);
            n4 = n < this.mSelectedPosition ? (n4 != 0 ? n5 - n3 : n3 + n6) : (n4 != 0 ? n3 + n6 : n5 - n3);
            n3 = n4;
        }
        if (n4 != n5 || n3 != n6) {
            view = new ValueAnimator();
            this.mIndicatorAnimator = view;
            view.setInterpolator((TimeInterpolator)AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
            view.setDuration((long)n2);
            view.setFloatValues(new float[]{0.0f, 1.0f});
            view.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float f = valueAnimator.getAnimatedFraction();
                    SlidingTabStrip.this.setIndicatorPosition(AnimationUtils.lerp(n4, n5, f), AnimationUtils.lerp(n3, n6, f));
                }
            });
            view.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                public void onAnimationEnd(Animator animator) {
                    SlidingTabStrip.this.mSelectedPosition = n;
                    SlidingTabStrip.this.mSelectionOffset = 0.0f;
                }
            });
            view.start();
        }
    }

    boolean childrenNeedLayout() {
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            if (this.getChildAt(i).getWidth() > 0) continue;
            return true;
        }
        return false;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mIndicatorLeft >= 0 && this.mIndicatorRight > this.mIndicatorLeft) {
            canvas.drawRect((float)this.mIndicatorLeft, (float)(this.getHeight() - this.mSelectedIndicatorHeight), (float)this.mIndicatorRight, (float)this.getHeight(), this.mSelectedIndicatorPaint);
        }
    }

    float getIndicatorPosition() {
        return (float)this.mSelectedPosition + this.mSelectionOffset;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        if (this.mIndicatorAnimator != null && this.mIndicatorAnimator.isRunning()) {
            this.mIndicatorAnimator.cancel();
            long l = this.mIndicatorAnimator.getDuration();
            this.animateIndicatorToPosition(this.mSelectedPosition, Math.round((1.0f - this.mIndicatorAnimator.getAnimatedFraction()) * (float)l));
            return;
        }
        this.updateIndicatorPosition();
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        if (View.MeasureSpec.getMode((int)n) != 1073741824) {
            return;
        }
        int n3 = TabLayout.this.mMode;
        int n4 = 1;
        if (n3 == 1 && TabLayout.this.mTabGravity == 1) {
            int n5;
            View view;
            int n6 = this.getChildCount();
            int n7 = 0;
            int n8 = n3 = 0;
            while (n3 < n6) {
                view = this.getChildAt(n3);
                n5 = n8;
                if (view.getVisibility() == 0) {
                    n5 = Math.max(n8, view.getMeasuredWidth());
                }
                ++n3;
                n8 = n5;
            }
            if (n8 <= 0) {
                return;
            }
            n3 = TabLayout.this.dpToPx(16);
            if (n8 * n6 <= this.getMeasuredWidth() - n3 * 2) {
                n3 = 0;
                for (n5 = n7; n5 < n6; ++n5) {
                    view = (LinearLayout.LayoutParams)this.getChildAt(n5).getLayoutParams();
                    if (view.width == n8 && view.weight == 0.0f) continue;
                    view.width = n8;
                    view.weight = 0.0f;
                    n3 = 1;
                }
            } else {
                TabLayout.this.mTabGravity = 0;
                TabLayout.this.updateTabViews(false);
                n3 = n4;
            }
            if (n3 != 0) {
                super.onMeasure(n, n2);
            }
        }
    }

    public void onRtlPropertiesChanged(int n) {
        super.onRtlPropertiesChanged(n);
        if (Build.VERSION.SDK_INT < 23 && this.mLayoutDirection != n) {
            this.requestLayout();
            this.mLayoutDirection = n;
        }
    }

    void setIndicatorPosition(int n, int n2) {
        if (n != this.mIndicatorLeft || n2 != this.mIndicatorRight) {
            this.mIndicatorLeft = n;
            this.mIndicatorRight = n2;
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    void setIndicatorPositionFromTabPosition(int n, float f) {
        if (this.mIndicatorAnimator != null && this.mIndicatorAnimator.isRunning()) {
            this.mIndicatorAnimator.cancel();
        }
        this.mSelectedPosition = n;
        this.mSelectionOffset = f;
        this.updateIndicatorPosition();
    }

    void setSelectedIndicatorColor(int n) {
        if (this.mSelectedIndicatorPaint.getColor() != n) {
            this.mSelectedIndicatorPaint.setColor(n);
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    void setSelectedIndicatorHeight(int n) {
        if (this.mSelectedIndicatorHeight != n) {
            this.mSelectedIndicatorHeight = n;
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

}
