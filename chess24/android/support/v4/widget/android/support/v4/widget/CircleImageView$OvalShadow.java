/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.RadialGradient
 *  android.graphics.RectF
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.drawable.shapes.OvalShape
 */
package android.support.v4.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v4.widget.CircleImageView;

private class CircleImageView.OvalShadow
extends OvalShape {
    private RadialGradient mRadialGradient;
    private Paint mShadowPaint = new Paint();

    CircleImageView.OvalShadow(int n) {
        CircleImageView.this.mShadowRadius = n;
        this.updateRadialGradient((int)this.rect().width());
    }

    private void updateRadialGradient(int n) {
        float f = n / 2;
        float f2 = CircleImageView.this.mShadowRadius;
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        this.mRadialGradient = new RadialGradient(f, f, f2, new int[]{1023410176, 0}, null, tileMode);
        this.mShadowPaint.setShader((Shader)this.mRadialGradient);
    }

    public void draw(Canvas canvas, Paint paint) {
        int n = CircleImageView.this.getWidth();
        int n2 = CircleImageView.this.getHeight();
        float f = n /= 2;
        float f2 = n2 / 2;
        canvas.drawCircle(f, f2, f, this.mShadowPaint);
        canvas.drawCircle(f, f2, (float)(n - CircleImageView.this.mShadowRadius), paint);
    }

    protected void onResize(float f, float f2) {
        super.onResize(f, f2);
        this.updateRadialGradient((int)f);
    }
}
