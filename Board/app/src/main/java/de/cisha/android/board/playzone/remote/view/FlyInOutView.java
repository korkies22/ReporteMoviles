// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.remote.view;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.LinearLayout;

public class FlyInOutView extends LinearLayout
{
    private static final int ANIMATION_DURATION = 1000;
    
    public FlyInOutView(final Context context) {
        super(context);
        this.setVisibility(8);
    }
    
    public FlyInOutView(final Context context, final AttributeSet set) {
        super(context, set);
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
    
    protected void hide(final float n, final float n2) {
        this.getBackground().setLevel(1);
        final TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, n, 1, 0.0f, 1, n2);
        translateAnimation.setDuration(1000L);
        translateAnimation.setFillEnabled(true);
        translateAnimation.setFillAfter(true);
        translateAnimation.setAnimationListener((Animation.AnimationListener)new Animation.AnimationListener() {
            public void onAnimationEnd(final Animation animation) {
                FlyInOutView.this.setVisibility(8);
            }
            
            public void onAnimationRepeat(final Animation animation) {
            }
            
            public void onAnimationStart(final Animation animation) {
            }
        });
        this.startAnimation((Animation)translateAnimation);
        this.setVisibility(0);
    }
    
    protected void show(final float n, final float n2) {
        this.getBackground().setLevel(0);
        final TranslateAnimation translateAnimation = new TranslateAnimation(1, n, 1, 0.0f, 1, n2, 1, 0.0f);
        translateAnimation.setDuration(1000L);
        this.startAnimation((Animation)translateAnimation);
        this.setVisibility(0);
    }
}
