/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Color
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.animation.AlphaAnimation
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.view.animation.AnimationSet
 *  android.view.animation.Transformation
 *  android.view.animation.TranslateAnimation
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 */
package de.cisha.android.ui.patterns.input;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.android.ui.patterns.R;

public abstract class CustomInput
extends LinearLayout {
    protected static final int ACTIVE_HINT_COLOR;
    protected static final long ANIMATION_TIME = 500L;
    protected static final int INACTIVE_HINT_COLOR = -16777216;
    protected static final int INACTIVE_HINT_COLOR_WITH_ALPHA;
    private String _hintString;
    private TextView _hintTextView;

    static {
        INACTIVE_HINT_COLOR_WITH_ALPHA = Color.argb((int)128, (int)0, (int)0, (int)0);
        ACTIVE_HINT_COLOR = Color.rgb((int)0, (int)74, (int)174);
    }

    public CustomInput(Context context) {
        super(context);
        this.init();
    }

    public CustomInput(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.readAttributes(attributeSet);
        this.init();
    }

    private void init() {
        this.setOrientation(1);
        this.setBackgroundResource(17301529);
        float f = this.getResources().getDisplayMetrics().density;
        this._hintTextView = new TextView(this.getContext());
        this._hintTextView.setText((CharSequence)this.getHintString());
        this._hintTextView.setPadding((int)(13.0f * f), 0, 0, 0);
        this._hintTextView.setTextSize(5.0f * f);
        this._hintTextView.setVisibility(0);
        this._hintTextView.setTextColor(INACTIVE_HINT_COLOR_WITH_ALPHA);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.topMargin = (int)(1.5 * (double)f);
        this.addView((View)this._hintTextView, (ViewGroup.LayoutParams)layoutParams);
    }

    private void readAttributes(AttributeSet attributeSet) {
        attributeSet = this.getContext().obtainStyledAttributes(attributeSet, R.styleable.CustomInput);
        try {
            this._hintString = attributeSet.getString(R.styleable.CustomInput_hintText);
            return;
        }
        finally {
            attributeSet.recycle();
        }
    }

    protected String getHintString() {
        if (this._hintString == null) {
            return "";
        }
        return this._hintString;
    }

    public void setHint(String string) {
        if (this._hintTextView != null) {
            this._hintTextView.setText((CharSequence)string);
        }
        this._hintString = string;
    }

    protected void setHintActive(boolean bl) {
        if (this._hintTextView.getVisibility() == 0) {
            AlphaAndColorAnimation alphaAndColorAnimation = bl ? new AlphaAndColorAnimation(0.5f, 1.0f, this._hintTextView, -16777216, ACTIVE_HINT_COLOR) : new AlphaAndColorAnimation(1.0f, 0.5f, this._hintTextView, ACTIVE_HINT_COLOR, -16777216);
            alphaAndColorAnimation.setDuration(500L);
            alphaAndColorAnimation.setFillAfter(true);
            this._hintTextView.startAnimation((Animation)alphaAndColorAnimation);
            return;
        }
        TextView textView = this._hintTextView;
        int n = bl ? ACTIVE_HINT_COLOR : INACTIVE_HINT_COLOR_WITH_ALPHA;
        textView.setTextColor(n);
    }

    protected void setInputView(View view) {
        int n;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.topMargin = n = (int)(-10.0f * this.getResources().getDisplayMetrics().density);
        layoutParams.bottomMargin = n;
        view.setBackgroundColor(0);
        this.addView(view, (ViewGroup.LayoutParams)layoutParams);
    }

    protected void showHint(boolean bl) {
        TextView textView = this._hintTextView;
        int n = bl ? 0 : 4;
        textView.setVisibility(n);
    }

    protected void updateHint(final boolean bl) {
        AnimationSet animationSet;
        if (this._hintTextView.getVisibility() == 0 && !bl) {
            animationSet = new AnimationSet(true);
            TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, (float)this._hintTextView.getHeight());
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            animationSet.addAnimation((Animation)translateAnimation);
            animationSet.addAnimation((Animation)alphaAnimation);
        } else if (this._hintTextView.getVisibility() != 0 && bl) {
            this._hintTextView.setTextColor(ACTIVE_HINT_COLOR);
            animationSet = new AnimationSet(true);
            TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, (float)this._hintTextView.getHeight(), 0.0f);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            animationSet.addAnimation((Animation)translateAnimation);
            animationSet.addAnimation((Animation)alphaAnimation);
        } else {
            animationSet = null;
        }
        if (animationSet != null) {
            animationSet.setDuration(500L);
            animationSet.setFillAfter(true);
            animationSet.setFillEnabled(true);
            animationSet.setAnimationListener(new Animation.AnimationListener(){

                public void onAnimationEnd(Animation animation) {
                    CustomInput.this.showHint(bl);
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                    CustomInput.this.showHint(true);
                }
            });
            this._hintTextView.startAnimation((Animation)animationSet);
        }
    }

    private static class AlphaAndColorAnimation
    extends AlphaAnimation {
        private int _endColor;
        private int _startColor;
        private TextView _text;

        public AlphaAndColorAnimation(float f, float f2, TextView textView, int n, int n2) {
            super(f, f2);
            this._text = textView;
            this._startColor = n;
            this._endColor = n2;
        }

        protected void applyTransformation(float f, Transformation transformation) {
            super.applyTransformation(f, transformation);
            float f2 = 1.0f - f;
            int n = Color.argb((int)((int)((float)Color.alpha((int)this._startColor) * f2 + (float)Color.alpha((int)this._endColor) * f)), (int)((int)((float)Color.red((int)this._startColor) * f2 + (float)Color.red((int)this._endColor) * f)), (int)((int)((float)Color.green((int)this._startColor) * f2 + (float)Color.green((int)this._endColor) * f)), (int)((int)(f2 * (float)Color.blue((int)this._startColor) + f * (float)Color.blue((int)this._endColor))));
            this._text.setTextColor(n);
        }
    }

}
