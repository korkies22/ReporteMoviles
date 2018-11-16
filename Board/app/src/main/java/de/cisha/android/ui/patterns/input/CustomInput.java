// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.input;

import android.content.res.TypedArray;
import android.view.animation.Transformation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.Animation;
import de.cisha.android.ui.patterns.R;
import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.LinearLayout;

public abstract class CustomInput extends LinearLayout
{
    protected static final int ACTIVE_HINT_COLOR;
    protected static final long ANIMATION_TIME = 500L;
    protected static final int INACTIVE_HINT_COLOR = -16777216;
    protected static final int INACTIVE_HINT_COLOR_WITH_ALPHA;
    private String _hintString;
    private TextView _hintTextView;
    
    static {
        INACTIVE_HINT_COLOR_WITH_ALPHA = Color.argb(128, 0, 0, 0);
        ACTIVE_HINT_COLOR = Color.rgb(0, 74, 174);
    }
    
    public CustomInput(final Context context) {
        super(context);
        this.init();
    }
    
    public CustomInput(final Context context, final AttributeSet set) {
        super(context, set);
        this.readAttributes(set);
        this.init();
    }
    
    private void init() {
        this.setOrientation(1);
        this.setBackgroundResource(17301529);
        final float density = this.getResources().getDisplayMetrics().density;
        (this._hintTextView = new TextView(this.getContext())).setText((CharSequence)this.getHintString());
        this._hintTextView.setPadding((int)(13.0f * density), 0, 0, 0);
        this._hintTextView.setTextSize(5.0f * density);
        this._hintTextView.setVisibility(0);
        this._hintTextView.setTextColor(CustomInput.INACTIVE_HINT_COLOR_WITH_ALPHA);
        final LinearLayout.LayoutParams linearLayout.LayoutParams = new LinearLayout.LayoutParams(-1, -2);
        linearLayout.LayoutParams.topMargin = (int)(1.5 * density);
        this.addView((View)this._hintTextView, (ViewGroup.LayoutParams)linearLayout.LayoutParams);
    }
    
    private void readAttributes(AttributeSet obtainStyledAttributes) {
        obtainStyledAttributes = (AttributeSet)this.getContext().obtainStyledAttributes(obtainStyledAttributes, R.styleable.CustomInput);
        try {
            this._hintString = ((TypedArray)obtainStyledAttributes).getString(R.styleable.CustomInput_hintText);
        }
        finally {
            ((TypedArray)obtainStyledAttributes).recycle();
        }
    }
    
    protected String getHintString() {
        if (this._hintString == null) {
            return "";
        }
        return this._hintString;
    }
    
    public void setHint(final String s) {
        if (this._hintTextView != null) {
            this._hintTextView.setText((CharSequence)s);
        }
        this._hintString = s;
    }
    
    protected void setHintActive(final boolean b) {
        if (this._hintTextView.getVisibility() == 0) {
            AlphaAndColorAnimation alphaAndColorAnimation;
            if (b) {
                alphaAndColorAnimation = new AlphaAndColorAnimation(0.5f, 1.0f, this._hintTextView, -16777216, CustomInput.ACTIVE_HINT_COLOR);
            }
            else {
                alphaAndColorAnimation = new AlphaAndColorAnimation(1.0f, 0.5f, this._hintTextView, CustomInput.ACTIVE_HINT_COLOR, -16777216);
            }
            ((Animation)alphaAndColorAnimation).setDuration(500L);
            ((Animation)alphaAndColorAnimation).setFillAfter(true);
            this._hintTextView.startAnimation((Animation)alphaAndColorAnimation);
            return;
        }
        final TextView hintTextView = this._hintTextView;
        int textColor;
        if (b) {
            textColor = CustomInput.ACTIVE_HINT_COLOR;
        }
        else {
            textColor = CustomInput.INACTIVE_HINT_COLOR_WITH_ALPHA;
        }
        hintTextView.setTextColor(textColor);
    }
    
    protected void setInputView(final View view) {
        final LinearLayout.LayoutParams linearLayout.LayoutParams = new LinearLayout.LayoutParams(-1, -2);
        final int n = (int)(-10.0f * this.getResources().getDisplayMetrics().density);
        linearLayout.LayoutParams.topMargin = n;
        linearLayout.LayoutParams.bottomMargin = n;
        view.setBackgroundColor(0);
        this.addView(view, (ViewGroup.LayoutParams)linearLayout.LayoutParams);
    }
    
    protected void showHint(final boolean b) {
        final TextView hintTextView = this._hintTextView;
        int visibility;
        if (b) {
            visibility = 0;
        }
        else {
            visibility = 4;
        }
        hintTextView.setVisibility(visibility);
    }
    
    protected void updateHint(final boolean b) {
        AnimationSet set;
        if (this._hintTextView.getVisibility() == 0 && !b) {
            set = new AnimationSet(true);
            final TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, (float)this._hintTextView.getHeight());
            final AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            set.addAnimation((Animation)translateAnimation);
            set.addAnimation((Animation)alphaAnimation);
        }
        else if (this._hintTextView.getVisibility() != 0 && b) {
            this._hintTextView.setTextColor(CustomInput.ACTIVE_HINT_COLOR);
            set = new AnimationSet(true);
            final TranslateAnimation translateAnimation2 = new TranslateAnimation(0.0f, 0.0f, (float)this._hintTextView.getHeight(), 0.0f);
            final AlphaAnimation alphaAnimation2 = new AlphaAnimation(0.0f, 1.0f);
            set.addAnimation((Animation)translateAnimation2);
            set.addAnimation((Animation)alphaAnimation2);
        }
        else {
            set = null;
        }
        if (set != null) {
            set.setDuration(500L);
            set.setFillAfter(true);
            set.setFillEnabled(true);
            set.setAnimationListener((Animation.AnimationListener)new Animation.AnimationListener() {
                public void onAnimationEnd(final Animation animation) {
                    CustomInput.this.showHint(b);
                }
                
                public void onAnimationRepeat(final Animation animation) {
                }
                
                public void onAnimationStart(final Animation animation) {
                    CustomInput.this.showHint(true);
                }
            });
            this._hintTextView.startAnimation((Animation)set);
        }
    }
    
    private static class AlphaAndColorAnimation extends AlphaAnimation
    {
        private int _endColor;
        private int _startColor;
        private TextView _text;
        
        public AlphaAndColorAnimation(final float n, final float n2, final TextView text, final int startColor, final int endColor) {
            super(n, n2);
            this._text = text;
            this._startColor = startColor;
            this._endColor = endColor;
        }
        
        protected void applyTransformation(final float n, final Transformation transformation) {
            super.applyTransformation(n, transformation);
            final float n2 = 1.0f - n;
            this._text.setTextColor(Color.argb((int)(Color.alpha(this._startColor) * n2 + Color.alpha(this._endColor) * n), (int)(Color.red(this._startColor) * n2 + Color.red(this._endColor) * n), (int)(Color.green(this._startColor) * n2 + Color.green(this._endColor) * n), (int)(n2 * Color.blue(this._startColor) + n * Color.blue(this._endColor))));
        }
    }
}
