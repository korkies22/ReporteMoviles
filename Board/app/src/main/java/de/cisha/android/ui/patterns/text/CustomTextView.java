// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.text;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.view.animation.Transformation;
import android.view.animation.Animation;
import android.util.Log;
import android.graphics.Typeface;
import android.graphics.Canvas;
import de.cisha.android.ui.patterns.R;
import android.util.AttributeSet;
import android.content.Context;
import android.content.res.ColorStateList;
import android.widget.TextView;

public class CustomTextView extends TextView
{
    private CustomTextViewTextStyle _currentStyle;
    private CustomTextViewTextStyle _selectedStyle;
    private CustomTextViewTextStyle _style;
    private ColorStateList _textColor;
    private ColorStateList _textColorBeforeAnimation;
    
    public CustomTextView(Context obtainStyledAttributes, final AttributeSet set) {
        super(obtainStyledAttributes, set);
        obtainStyledAttributes = (Context)obtainStyledAttributes.obtainStyledAttributes(set, R.styleable.CustomTextView);
        try {
            this._textColor = ((TypedArray)obtainStyledAttributes).getColorStateList(R.styleable.CustomTextView_android_textColor);
            this.setTextStyle(this._style = CustomTextViewTextStyle.valueOf(((TypedArray)obtainStyledAttributes).getInt(R.styleable.CustomTextView_textstyle, 0)), this._textColor);
            final int int1 = ((TypedArray)obtainStyledAttributes).getInt(R.styleable.CustomTextView_textstyle_selected, -1);
            if (int1 != -1) {
                this._selectedStyle = CustomTextViewTextStyle.valueOf(int1);
            }
        }
        finally {
            ((TypedArray)obtainStyledAttributes).recycle();
        }
    }
    
    public CustomTextView(final Context context, final AttributeSet set, final int n) {
        this(context, set);
    }
    
    public CustomTextView(final Context context, final CustomTextViewTextStyle style, final CustomTextViewTextStyle selectedStyle) {
        super(context);
        this._style = style;
        this._selectedStyle = selectedStyle;
        this.setTextStyle(style, this._textColor);
    }
    
    private void adjustStyle() {
        if (this._selectedStyle != null) {
            if (!this.isSelected() && !this.isPressed()) {
                this.setTextStyle(this._style, this._textColor);
                return;
            }
            this.setTextStyle(this._selectedStyle, null);
        }
    }
    
    private CustomTextViewTextStyle getCurrentStyle() {
        return this._currentStyle;
    }
    
    private void setTextStyle(final CustomTextViewTextStyle currentStyle, final ColorStateList textColor) {
        if (currentStyle != null) {
            this._currentStyle = currentStyle;
            if (textColor != null) {
                this.setTextColor(textColor);
            }
            else {
                this.setTextColor(currentStyle.getColor());
            }
            this.setTextSize(2, currentStyle.getFontSize());
            this.setFont(currentStyle.getFont());
            if (currentStyle.hasShadow()) {
                this.setShadowLayer(currentStyle.getShadowWidth(), currentStyle.getShadowX(), currentStyle.getShadowY(), currentStyle.getShadowColor());
            }
        }
    }
    
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        if (this._currentStyle != null && this._currentStyle.getShadowWidth() > 0.0f && this._currentStyle.getShadowX() == 0.0f && this._currentStyle.getShadowY() == 0.0f) {
            for (int i = 0; i < 8; ++i) {
                super.onDraw(canvas);
            }
        }
    }
    
    public void setFont(final FontName fontName) {
        while (true) {
            try {
                this.setTypeface(Typeface.createFromAsset(this.getContext().getAssets(), fontName.getAssetsFileName()));
                return;
                final StringBuilder sb = new StringBuilder();
                sb.append("Font not found: ");
                sb.append(fontName.getAssetsFileName());
                Log.e("CustomTextView", sb.toString());
            }
            catch (Exception ex) {
                continue;
            }
            break;
        }
    }
    
    public void setGlowing(final boolean b) {
        if (b && this.getAnimation() == null) {
            this._textColorBeforeAnimation = this.getTextColors();
            this.startAnimation((Animation)new GlowAnimation());
            return;
        }
        this.clearAnimation();
        this.setTextColor(this._textColorBeforeAnimation);
    }
    
    public void setPressed(final boolean pressed) {
        super.setPressed(pressed);
        this.adjustStyle();
    }
    
    public void setSelected(final boolean selected) {
        super.setSelected(selected);
        this.adjustStyle();
    }
    
    public void setStyle(final CustomTextViewTextStyle style) {
        this.setTextStyle(this._style = style, null);
    }
    
    public class GlowAnimation extends Animation
    {
        private int _colorEnd;
        private int _colorStart;
        
        public GlowAnimation() {
            final int color = CustomTextView.this._style.getColor();
            int color2;
            if (CustomTextView.this._selectedStyle != null) {
                color2 = CustomTextView.this._selectedStyle.getColor();
            }
            else {
                color2 = -16777216;
            }
            this._colorStart = Math.min(color, color2);
            this._colorEnd = Math.max(color, color2);
            this.setRepeatMode(2);
            this.setRepeatCount(-1);
            this.setDuration(500L);
        }
        
        private int getColorValue(final RGBFunction rgbFunction, final float n) {
            return rgbFunction.returnIntFor(this._colorStart) + (int)((rgbFunction.returnIntFor(this._colorEnd) - rgbFunction.returnIntFor(this._colorStart)) * n);
        }
        
        protected void applyTransformation(final float n, final Transformation transformation) {
            CustomTextView.this.setTextColor(Color.rgb(this.getColorValue(RGBFunction.RED, n), this.getColorValue(RGBFunction.GREEN, n), this.getColorValue(RGBFunction.BLUE, n)));
        }
    }
    
    private enum RGBFunction
    {
        BLUE {
            @Override
            public int returnIntFor(final int n) {
                return Color.blue(n);
            }
        }, 
        GREEN {
            @Override
            public int returnIntFor(final int n) {
                return Color.green(n);
            }
        }, 
        RED {
            @Override
            public int returnIntFor(final int n) {
                return Color.red(n);
            }
        };
        
        public abstract int returnIntFor(final int p0);
    }
}
