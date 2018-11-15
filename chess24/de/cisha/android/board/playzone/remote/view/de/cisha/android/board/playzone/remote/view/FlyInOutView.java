/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.view.animation.TranslateAnimation
 *  android.widget.LinearLayout
 */
package de.cisha.android.board.playzone.remote.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

public class FlyInOutView
extends LinearLayout {
    private static final int ANIMATION_DURATION = 1000;

    public FlyInOutView(Context context) {
        super(context);
        this.setVisibility(8);
    }

    public FlyInOutView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setVisibility(8);
    }

    public void flyInFromBottom() {
        this.show(0.0f, 1.0f);
    }

    public void flyInFromLeft() {
        this.show(-1.0f, 0.0f);
    }

    public void flyInFromRight() {
        this.show(1.0f, 0.0f);
    }

    public void flyInFromTop() {
        this.show(0.0f, -1.0f);
    }

    public void flyOutToBottom() {
        this.hide(0.0f, 1.0f);
    }

    public void flyOutToLeft() {
        this.hide(-1.0f, 0.0f);
    }

    public void flyOutToRight() {
        this.hide(1.0f, 0.0f);
    }

    public void flyOutToTop() {
        this.hide(0.0f, -1.0f);
    }

    protected void hide(float f, float f2) {
        this.getBackground().setLevel(1);
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, f, 1, 0.0f, 1, f2);
        translateAnimation.setDuration(1000L);
        translateAnimation.setFillEnabled(true);
        translateAnimation.setFillAfter(true);
        translateAnimation.setAnimationListener(new Animation.AnimationListener(){

            public void onAnimationEnd(Animation animation) {
                FlyInOutView.this.setVisibility(8);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });
        this.startAnimation((Animation)translateAnimation);
        this.setVisibility(0);
    }

    protected void show(float f, float f2) {
        this.getBackground().setLevel(0);
        TranslateAnimation translateAnimation = new TranslateAnimation(1, f, 1, 0.0f, 1, f2, 1, 0.0f);
        translateAnimation.setDuration(1000L);
        this.startAnimation((Animation)translateAnimation);
        this.setVisibility(0);
    }

}
