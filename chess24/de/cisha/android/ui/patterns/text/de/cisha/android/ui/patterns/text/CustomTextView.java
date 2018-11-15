/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Typeface
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.animation.Animation
 *  android.view.animation.Transformation
 *  android.widget.TextView
 */
package de.cisha.android.ui.patterns.text;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextView;
import de.cisha.android.ui.patterns.R;
import de.cisha.android.ui.patterns.text.CustomTextViewTextStyle;
import de.cisha.android.ui.patterns.text.FontName;

public class CustomTextView
extends TextView {
    private CustomTextViewTextStyle _currentStyle;
    private CustomTextViewTextStyle _selectedStyle;
    private CustomTextViewTextStyle _style;
    private ColorStateList _textColor;
    private ColorStateList _textColorBeforeAnimation;

    public CustomTextView(Context context, AttributeSet object) {
        block4 : {
            super(context, object);
            context = context.obtainStyledAttributes(object, R.styleable.CustomTextView);
            this._textColor = context.getColorStateList(R.styleable.CustomTextView_android_textColor);
            object = CustomTextViewTextStyle.valueOf(context.getInt(R.styleable.CustomTextView_textstyle, 0));
            this._style = object;
            this.setTextStyle((CustomTextViewTextStyle)((Object)object), this._textColor);
            int n = context.getInt(R.styleable.CustomTextView_textstyle_selected, -1);
            if (n == -1) break block4;
            this._selectedStyle = CustomTextViewTextStyle.valueOf(n);
        }
        return;
        finally {
            context.recycle();
        }
    }

    public CustomTextView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet);
    }

    public CustomTextView(Context context, CustomTextViewTextStyle customTextViewTextStyle, CustomTextViewTextStyle customTextViewTextStyle2) {
        super(context);
        this._style = customTextViewTextStyle;
        this._selectedStyle = customTextViewTextStyle2;
        this.setTextStyle(customTextViewTextStyle, this._textColor);
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

    private void setTextStyle(CustomTextViewTextStyle customTextViewTextStyle, ColorStateList colorStateList) {
        if (customTextViewTextStyle != null) {
            this._currentStyle = customTextViewTextStyle;
            if (colorStateList != null) {
                this.setTextColor(colorStateList);
            } else {
                this.setTextColor(customTextViewTextStyle.getColor());
            }
            this.setTextSize(2, customTextViewTextStyle.getFontSize());
            this.setFont(customTextViewTextStyle.getFont());
            if (customTextViewTextStyle.hasShadow()) {
                this.setShadowLayer(customTextViewTextStyle.getShadowWidth(), customTextViewTextStyle.getShadowX(), customTextViewTextStyle.getShadowY(), customTextViewTextStyle.getShadowColor());
            }
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this._currentStyle != null && this._currentStyle.getShadowWidth() > 0.0f && this._currentStyle.getShadowX() == 0.0f && this._currentStyle.getShadowY() == 0.0f) {
            for (int i = 0; i < 8; ++i) {
                super.onDraw(canvas);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setFont(FontName fontName) {
        try {
            this.setTypeface(Typeface.createFromAsset((AssetManager)this.getContext().getAssets(), (String)fontName.getAssetsFileName()));
            return;
        }
        catch (Exception exception) {}
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Font not found: ");
        stringBuilder.append(fontName.getAssetsFileName());
        Log.e((String)"CustomTextView", (String)stringBuilder.toString());
    }

    public void setGlowing(boolean bl) {
        if (bl && this.getAnimation() == null) {
            this._textColorBeforeAnimation = this.getTextColors();
            this.startAnimation((Animation)new GlowAnimation());
            return;
        }
        this.clearAnimation();
        this.setTextColor(this._textColorBeforeAnimation);
    }

    public void setPressed(boolean bl) {
        super.setPressed(bl);
        this.adjustStyle();
    }

    public void setSelected(boolean bl) {
        super.setSelected(bl);
        this.adjustStyle();
    }

    public void setStyle(CustomTextViewTextStyle customTextViewTextStyle) {
        this._style = customTextViewTextStyle;
        this.setTextStyle(this._style, null);
    }

    public class GlowAnimation
    extends Animation {
        private int _colorEnd;
        private int _colorStart;

        public GlowAnimation() {
            int n = CustomTextView.this._style.getColor();
            int n2 = CustomTextView.this._selectedStyle != null ? CustomTextView.this._selectedStyle.getColor() : -16777216;
            this._colorStart = Math.min(n, n2);
            this._colorEnd = Math.max(n, n2);
            this.setRepeatMode(2);
            this.setRepeatCount(-1);
            this.setDuration(500L);
        }

        private int getColorValue(RGBFunction rGBFunction, float f) {
            return rGBFunction.returnIntFor(this._colorStart) + (int)((float)(rGBFunction.returnIntFor(this._colorEnd) - rGBFunction.returnIntFor(this._colorStart)) * f);
        }

        protected void applyTransformation(float f, Transformation transformation) {
            int n = this.getColorValue(RGBFunction.RED, f);
            int n2 = this.getColorValue(RGBFunction.BLUE, f);
            int n3 = this.getColorValue(RGBFunction.GREEN, f);
            CustomTextView.this.setTextColor(Color.rgb((int)n, (int)n3, (int)n2));
        }
    }

    private static enum RGBFunction {
        RED{

            @Override
            public int returnIntFor(int n) {
                return Color.red((int)n);
            }
        }
        ,
        GREEN{

            @Override
            public int returnIntFor(int n) {
                return Color.green((int)n);
            }
        }
        ,
        BLUE{

            @Override
            public int returnIntFor(int n) {
                return Color.blue((int)n);
            }
        };
        

        private RGBFunction() {
        }

        public abstract int returnIntFor(int var1);

    }

}
