/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.RectF
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.TextView
 */
package com.facebook.share.internal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.facebook.common.R;

@Deprecated
public class LikeBoxCountView
extends FrameLayout {
    private int additionalTextPadding;
    private Paint borderPaint;
    private float borderRadius;
    private float caretHeight;
    private LikeBoxCountViewCaretPosition caretPosition = LikeBoxCountViewCaretPosition.LEFT;
    private float caretWidth;
    private TextView likeCountLabel;
    private int textPadding;

    @Deprecated
    public LikeBoxCountView(Context context) {
        super(context);
        this.initialize(context);
    }

    private void drawBorder(Canvas canvas, float f, float f2, float f3, float f4) {
        float f5;
        Path path = new Path();
        float f6 = this.borderRadius * 2.0f;
        float f7 = f + f6;
        float f8 = f2 + f6;
        path.addArc(new RectF(f, f2, f7, f8), -180.0f, 90.0f);
        if (this.caretPosition == LikeBoxCountViewCaretPosition.TOP) {
            f5 = f3 - f;
            path.lineTo((f5 - this.caretWidth) / 2.0f + f, f2);
            path.lineTo(f5 / 2.0f + f, f2 - this.caretHeight);
            path.lineTo((f5 + this.caretWidth) / 2.0f + f, f2);
        }
        path.lineTo(f3 - this.borderRadius, f2);
        f5 = f3 - f6;
        path.addArc(new RectF(f5, f2, f3, f8), -90.0f, 90.0f);
        if (this.caretPosition == LikeBoxCountViewCaretPosition.RIGHT) {
            f8 = f4 - f2;
            path.lineTo(f3, (f8 - this.caretWidth) / 2.0f + f2);
            path.lineTo(this.caretHeight + f3, f8 / 2.0f + f2);
            path.lineTo(f3, (f8 + this.caretWidth) / 2.0f + f2);
        }
        path.lineTo(f3, f4 - this.borderRadius);
        f6 = f4 - f6;
        path.addArc(new RectF(f5, f6, f3, f4), 0.0f, 90.0f);
        if (this.caretPosition == LikeBoxCountViewCaretPosition.BOTTOM) {
            path.lineTo((this.caretWidth + (f3 -= f)) / 2.0f + f, f4);
            path.lineTo(f3 / 2.0f + f, this.caretHeight + f4);
            path.lineTo((f3 - this.caretWidth) / 2.0f + f, f4);
        }
        path.lineTo(this.borderRadius + f, f4);
        path.addArc(new RectF(f, f6, f7, f4), 90.0f, 90.0f);
        if (this.caretPosition == LikeBoxCountViewCaretPosition.LEFT) {
            f3 = f4 - f2;
            path.lineTo(f, (this.caretWidth + f3) / 2.0f + f2);
            path.lineTo(f - this.caretHeight, f3 / 2.0f + f2);
            path.lineTo(f, (f3 - this.caretWidth) / 2.0f + f2);
        }
        path.lineTo(f, f2 + this.borderRadius);
        canvas.drawPath(path, this.borderPaint);
    }

    private void initialize(Context context) {
        this.setWillNotDraw(false);
        this.caretHeight = this.getResources().getDimension(R.dimen.com_facebook_likeboxcountview_caret_height);
        this.caretWidth = this.getResources().getDimension(R.dimen.com_facebook_likeboxcountview_caret_width);
        this.borderRadius = this.getResources().getDimension(R.dimen.com_facebook_likeboxcountview_border_radius);
        this.borderPaint = new Paint();
        this.borderPaint.setColor(this.getResources().getColor(R.color.com_facebook_likeboxcountview_border_color));
        this.borderPaint.setStrokeWidth(this.getResources().getDimension(R.dimen.com_facebook_likeboxcountview_border_width));
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.initializeLikeCountLabel(context);
        this.addView((View)this.likeCountLabel);
        this.setCaretPosition(this.caretPosition);
    }

    private void initializeLikeCountLabel(Context context) {
        this.likeCountLabel = new TextView(context);
        context = new FrameLayout.LayoutParams(-1, -1);
        this.likeCountLabel.setLayoutParams((ViewGroup.LayoutParams)context);
        this.likeCountLabel.setGravity(17);
        this.likeCountLabel.setTextSize(0, this.getResources().getDimension(R.dimen.com_facebook_likeboxcountview_text_size));
        this.likeCountLabel.setTextColor(this.getResources().getColor(R.color.com_facebook_likeboxcountview_text_color));
        this.textPadding = this.getResources().getDimensionPixelSize(R.dimen.com_facebook_likeboxcountview_text_padding);
        this.additionalTextPadding = this.getResources().getDimensionPixelSize(R.dimen.com_facebook_likeboxcountview_caret_height);
    }

    private void setAdditionalTextPadding(int n, int n2, int n3, int n4) {
        this.likeCountLabel.setPadding(this.textPadding + n, this.textPadding + n2, this.textPadding + n3, this.textPadding + n4);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int n = this.getPaddingTop();
        int n2 = this.getPaddingLeft();
        int n3 = this.getWidth() - this.getPaddingRight();
        int n4 = this.getHeight() - this.getPaddingBottom();
        switch (.$SwitchMap$com$facebook$share$internal$LikeBoxCountView$LikeBoxCountViewCaretPosition[this.caretPosition.ordinal()]) {
            default: {
                break;
            }
            case 4: {
                n4 = (int)((float)n4 - this.caretHeight);
                break;
            }
            case 3: {
                n3 = (int)((float)n3 - this.caretHeight);
                break;
            }
            case 2: {
                n = (int)((float)n + this.caretHeight);
                break;
            }
            case 1: {
                n2 = (int)((float)n2 + this.caretHeight);
            }
        }
        this.drawBorder(canvas, n2, n, n3, n4);
    }

    @Deprecated
    public void setCaretPosition(LikeBoxCountViewCaretPosition likeBoxCountViewCaretPosition) {
        this.caretPosition = likeBoxCountViewCaretPosition;
        switch (.$SwitchMap$com$facebook$share$internal$LikeBoxCountView$LikeBoxCountViewCaretPosition[likeBoxCountViewCaretPosition.ordinal()]) {
            default: {
                return;
            }
            case 4: {
                this.setAdditionalTextPadding(0, 0, 0, this.additionalTextPadding);
                return;
            }
            case 3: {
                this.setAdditionalTextPadding(0, 0, this.additionalTextPadding, 0);
                return;
            }
            case 2: {
                this.setAdditionalTextPadding(0, this.additionalTextPadding, 0, 0);
                return;
            }
            case 1: 
        }
        this.setAdditionalTextPadding(this.additionalTextPadding, 0, 0, 0);
    }

    @Deprecated
    public void setText(String string) {
        this.likeCountLabel.setText((CharSequence)string);
    }

    public static enum LikeBoxCountViewCaretPosition {
        LEFT,
        TOP,
        RIGHT,
        BOTTOM;
        

        private LikeBoxCountViewCaretPosition() {
        }
    }

}
