/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.RectF
 */
package android.support.v7.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.RoundRectDrawableWithShadow;

class CardViewBaseImpl
implements RoundRectDrawableWithShadow.RoundRectHelper {
    CardViewBaseImpl() {
    }

    @Override
    public void drawRoundRect(Canvas canvas, RectF rectF, float f, Paint paint) {
        float f2 = 2.0f * f;
        float f3 = rectF.width() - f2 - 1.0f;
        float f4 = rectF.height();
        if (f >= 1.0f) {
            float f5 = f + 0.5f;
            RectF rectF2 = CardViewBaseImpl.this.mCornerRect;
            float f6 = - f5;
            rectF2.set(f6, f6, f5, f5);
            int n = canvas.save();
            canvas.translate(rectF.left + f5, rectF.top + f5);
            canvas.drawArc(CardViewBaseImpl.this.mCornerRect, 180.0f, 90.0f, true, paint);
            canvas.translate(f3, 0.0f);
            canvas.rotate(90.0f);
            canvas.drawArc(CardViewBaseImpl.this.mCornerRect, 180.0f, 90.0f, true, paint);
            canvas.translate(f4 - f2 - 1.0f, 0.0f);
            canvas.rotate(90.0f);
            canvas.drawArc(CardViewBaseImpl.this.mCornerRect, 180.0f, 90.0f, true, paint);
            canvas.translate(f3, 0.0f);
            canvas.rotate(90.0f);
            canvas.drawArc(CardViewBaseImpl.this.mCornerRect, 180.0f, 90.0f, true, paint);
            canvas.restoreToCount(n);
            canvas.drawRect(rectF.left + f5 - 1.0f, rectF.top, rectF.right - f5 + 1.0f, rectF.top + f5, paint);
            canvas.drawRect(rectF.left + f5 - 1.0f, rectF.bottom - f5, rectF.right - f5 + 1.0f, rectF.bottom, paint);
        }
        canvas.drawRect(rectF.left, rectF.top + f, rectF.right, rectF.bottom - f, paint);
    }
}
