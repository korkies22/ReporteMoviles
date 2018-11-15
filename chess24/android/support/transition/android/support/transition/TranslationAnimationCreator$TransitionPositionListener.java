/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorListenerAdapter
 *  android.view.View
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.transition.R;
import android.support.transition.TranslationAnimationCreator;
import android.view.View;

private static class TranslationAnimationCreator.TransitionPositionListener
extends AnimatorListenerAdapter {
    private final View mMovingView;
    private float mPausedX;
    private float mPausedY;
    private final int mStartX;
    private final int mStartY;
    private final float mTerminalX;
    private final float mTerminalY;
    private int[] mTransitionPosition;
    private final View mViewInHierarchy;

    private TranslationAnimationCreator.TransitionPositionListener(View view, View view2, int n, int n2, float f, float f2) {
        this.mMovingView = view;
        this.mViewInHierarchy = view2;
        this.mStartX = n - Math.round(this.mMovingView.getTranslationX());
        this.mStartY = n2 - Math.round(this.mMovingView.getTranslationY());
        this.mTerminalX = f;
        this.mTerminalY = f2;
        this.mTransitionPosition = (int[])this.mViewInHierarchy.getTag(R.id.transition_position);
        if (this.mTransitionPosition != null) {
            this.mViewInHierarchy.setTag(R.id.transition_position, null);
        }
    }

    public void onAnimationCancel(Animator animator) {
        if (this.mTransitionPosition == null) {
            this.mTransitionPosition = new int[2];
        }
        this.mTransitionPosition[0] = Math.round((float)this.mStartX + this.mMovingView.getTranslationX());
        this.mTransitionPosition[1] = Math.round((float)this.mStartY + this.mMovingView.getTranslationY());
        this.mViewInHierarchy.setTag(R.id.transition_position, (Object)this.mTransitionPosition);
    }

    public void onAnimationEnd(Animator animator) {
        this.mMovingView.setTranslationX(this.mTerminalX);
        this.mMovingView.setTranslationY(this.mTerminalY);
    }

    public void onAnimationPause(Animator animator) {
        this.mPausedX = this.mMovingView.getTranslationX();
        this.mPausedY = this.mMovingView.getTranslationY();
        this.mMovingView.setTranslationX(this.mTerminalX);
        this.mMovingView.setTranslationY(this.mTerminalY);
    }

    public void onAnimationResume(Animator animator) {
        this.mMovingView.setTranslationX(this.mPausedX);
        this.mMovingView.setTranslationY(this.mPausedY);
    }
}
