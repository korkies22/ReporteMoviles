/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.view.View
 */
package android.support.v7.widget.helper;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

private static class ItemTouchHelper.RecoverAnimation
implements Animator.AnimatorListener {
    final int mActionState;
    final int mAnimationType;
    boolean mEnded = false;
    private float mFraction;
    public boolean mIsPendingCleanup;
    boolean mOverridden = false;
    final float mStartDx;
    final float mStartDy;
    final float mTargetX;
    final float mTargetY;
    private final ValueAnimator mValueAnimator;
    final RecyclerView.ViewHolder mViewHolder;
    float mX;
    float mY;

    ItemTouchHelper.RecoverAnimation(RecyclerView.ViewHolder viewHolder, int n, int n2, float f, float f2, float f3, float f4) {
        this.mActionState = n2;
        this.mAnimationType = n;
        this.mViewHolder = viewHolder;
        this.mStartDx = f;
        this.mStartDy = f2;
        this.mTargetX = f3;
        this.mTargetY = f4;
        this.mValueAnimator = ValueAnimator.ofFloat((float[])new float[]{0.0f, 1.0f});
        this.mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                RecoverAnimation.this.setFraction(valueAnimator.getAnimatedFraction());
            }
        });
        this.mValueAnimator.setTarget((Object)viewHolder.itemView);
        this.mValueAnimator.addListener((Animator.AnimatorListener)this);
        this.setFraction(0.0f);
    }

    public void cancel() {
        this.mValueAnimator.cancel();
    }

    public void onAnimationCancel(Animator animator) {
        this.setFraction(1.0f);
    }

    public void onAnimationEnd(Animator animator) {
        if (!this.mEnded) {
            this.mViewHolder.setIsRecyclable(true);
        }
        this.mEnded = true;
    }

    public void onAnimationRepeat(Animator animator) {
    }

    public void onAnimationStart(Animator animator) {
    }

    public void setDuration(long l) {
        this.mValueAnimator.setDuration(l);
    }

    public void setFraction(float f) {
        this.mFraction = f;
    }

    public void start() {
        this.mViewHolder.setIsRecyclable(false);
        this.mValueAnimator.start();
    }

    public void update() {
        this.mX = this.mStartDx == this.mTargetX ? this.mViewHolder.itemView.getTranslationX() : this.mStartDx + this.mFraction * (this.mTargetX - this.mStartDx);
        if (this.mStartDy == this.mTargetY) {
            this.mY = this.mViewHolder.itemView.getTranslationY();
            return;
        }
        this.mY = this.mStartDy + this.mFraction * (this.mTargetY - this.mStartDy);
    }

}
