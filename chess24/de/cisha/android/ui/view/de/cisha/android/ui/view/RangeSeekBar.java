/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.RectF
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewParent
 *  android.widget.ImageView
 */
package de.cisha.android.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.ImageView;
import de.cisha.android.ui.patterns.R;
import java.math.BigDecimal;

public class RangeSeekBar<T extends Number>
extends ImageView {
    public static final int ACTION_POINTER_INDEX_MASK = 65280;
    public static final int ACTION_POINTER_INDEX_SHIFT = 8;
    public static final int ACTION_POINTER_UP = 6;
    public static final int INVALID_POINTER_ID = 255;
    private int DEFAULT_COLOR = Color.argb((int)255, (int)51, (int)181, (int)229);
    private final T absoluteMaxValue;
    private final double absoluteMaxValuePrim;
    private final T absoluteMinValue;
    private final double absoluteMinValuePrim;
    private final float lineHeight = 0.3f * this.thumbHalfHeight;
    private OnRangeSeekBarChangeListener<T> listener;
    private int mActivePointerId = 255;
    private float mDownMotionX;
    private boolean mIsDragging;
    private int mScaledTouchSlop;
    float mTouchProgressOffset;
    private double normalizedMaxValue = 1.0;
    private double normalizedMinValue = 0.0;
    private boolean notifyWhileDragging = false;
    private final NumberType numberType;
    private final float padding = this.thumbHalfWidth;
    private final Paint paint = new Paint(1);
    private Thumb pressedThumb = null;
    private final float thumbHalfHeight = 0.5f * (float)this.thumbImage.getHeight();
    private final float thumbHalfWidth = this.thumbWidth * 0.5f;
    private final Bitmap thumbImage = BitmapFactory.decodeResource((Resources)this.getResources(), (int)R.drawable.seek_thumb_normal);
    private final Bitmap thumbPressedImage = BitmapFactory.decodeResource((Resources)this.getResources(), (int)R.drawable.seek_thumb_pressed);
    private final float thumbWidth = this.thumbImage.getWidth();

    public RangeSeekBar(T t, T t2, Context context) throws IllegalArgumentException {
        super(context);
        this.absoluteMinValue = t;
        this.absoluteMaxValue = t2;
        this.absoluteMinValuePrim = t.doubleValue();
        this.absoluteMaxValuePrim = t2.doubleValue();
        this.numberType = NumberType.fromNumber(t);
        this.DEFAULT_COLOR = context.getResources().getColor(R.color.chess24Blue);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        this.init();
    }

    private void attemptClaimDrag() {
        if (this.getParent() != null) {
            this.getParent().requestDisallowInterceptTouchEvent(true);
        }
    }

    private void drawThumb(float f, boolean bl, Canvas canvas) {
        Bitmap bitmap = bl ? this.thumbPressedImage : this.thumbImage;
        canvas.drawBitmap(bitmap, f - this.thumbHalfWidth, 0.5f * (float)this.getHeight() - this.thumbHalfHeight, this.paint);
    }

    private Thumb evalPressedThumb(float f) {
        boolean bl = this.isInThumbRange(f, this.normalizedMinValue);
        boolean bl2 = this.isInThumbRange(f, this.normalizedMaxValue);
        if (bl && bl2) {
            if (f / (float)this.getWidth() > 0.5f) {
                return Thumb.MIN;
            }
            return Thumb.MAX;
        }
        if (bl) {
            return Thumb.MIN;
        }
        if (bl2) {
            return Thumb.MAX;
        }
        return null;
    }

    private final void init() {
        this.mScaledTouchSlop = ViewConfiguration.get((Context)this.getContext()).getScaledTouchSlop();
    }

    private boolean isInThumbRange(float f, double d) {
        if (Math.abs(f - this.normalizedToScreen(d)) <= this.thumbHalfWidth) {
            return true;
        }
        return false;
    }

    private float normalizedToScreen(double d) {
        return (float)((double)this.padding + d * (double)((float)this.getWidth() - 2.0f * this.padding));
    }

    private T normalizedToValue(double d) {
        return (T)this.numberType.toNumber(this.absoluteMinValuePrim + d * (this.absoluteMaxValuePrim - this.absoluteMinValuePrim));
    }

    private final void onSecondaryPointerUp(MotionEvent motionEvent) {
        int n = (motionEvent.getAction() & 65280) >> 8;
        if (motionEvent.getPointerId(n) == this.mActivePointerId) {
            n = n == 0 ? 1 : 0;
            this.mDownMotionX = motionEvent.getX(n);
            this.mActivePointerId = motionEvent.getPointerId(n);
        }
    }

    private double screenToNormalized(float f) {
        float f2 = this.getWidth();
        if (f2 <= this.padding * 2.0f) {
            return 0.0;
        }
        return Math.min(1.0, Math.max(0.0, (double)((f - this.padding) / (f2 - 2.0f * this.padding))));
    }

    private final void trackTouchEvent(MotionEvent motionEvent) {
        float f = motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId));
        if (Thumb.MIN.equals((Object)this.pressedThumb)) {
            this.setNormalizedMinValue(this.screenToNormalized(f));
            return;
        }
        if (Thumb.MAX.equals((Object)this.pressedThumb)) {
            this.setNormalizedMaxValue(this.screenToNormalized(f));
        }
    }

    private double valueToNormalized(T t) {
        if (0.0 == this.absoluteMaxValuePrim - this.absoluteMinValuePrim) {
            return 0.0;
        }
        return (t.doubleValue() - this.absoluteMinValuePrim) / (this.absoluteMaxValuePrim - this.absoluteMinValuePrim);
    }

    public T getAbsoluteMaxValue() {
        return this.absoluteMaxValue;
    }

    public T getAbsoluteMinValue() {
        return this.absoluteMinValue;
    }

    public T getSelectedMaxValue() {
        return this.normalizedToValue(this.normalizedMaxValue);
    }

    public T getSelectedMinValue() {
        return this.normalizedToValue(this.normalizedMinValue);
    }

    public boolean isNotifyWhileDragging() {
        return this.notifyWhileDragging;
    }

    protected void onDraw(Canvas canvas) {
        synchronized (this) {
            super.onDraw(canvas);
            RectF rectF = new RectF(this.padding, ((float)this.getHeight() - this.lineHeight) * 0.5f, (float)this.getWidth() - this.padding, 0.5f * ((float)this.getHeight() + this.lineHeight));
            this.paint.setStyle(Paint.Style.FILL);
            this.paint.setColor(-7829368);
            this.paint.setAntiAlias(true);
            canvas.drawRect(rectF, this.paint);
            rectF.left = this.normalizedToScreen(this.normalizedMinValue);
            rectF.right = this.normalizedToScreen(this.normalizedMaxValue);
            this.paint.setColor(this.DEFAULT_COLOR);
            canvas.drawRect(rectF, this.paint);
            this.drawThumb(this.normalizedToScreen(this.normalizedMinValue), Thumb.MIN.equals((Object)this.pressedThumb), canvas);
            this.drawThumb(this.normalizedToScreen(this.normalizedMaxValue), Thumb.MAX.equals((Object)this.pressedThumb), canvas);
            return;
        }
    }

    protected void onMeasure(int n, int n2) {
        synchronized (this) {
            int n3;
            int n4 = 200;
            if (View.MeasureSpec.getMode((int)n) != 0) {
                n4 = View.MeasureSpec.getSize((int)n);
            }
            n = n3 = this.thumbImage.getHeight();
            if (View.MeasureSpec.getMode((int)n2) != 0) {
                n = Math.min(n3, View.MeasureSpec.getSize((int)n2));
            }
            this.setMeasuredDimension(n4, n);
            return;
        }
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        parcelable = (Bundle)parcelable;
        super.onRestoreInstanceState(parcelable.getParcelable("SUPER"));
        this.normalizedMinValue = parcelable.getDouble("MIN");
        this.normalizedMaxValue = parcelable.getDouble("MAX");
    }

    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("SUPER", super.onSaveInstanceState());
        bundle.putDouble("MIN", this.normalizedMinValue);
        bundle.putDouble("MAX", this.normalizedMaxValue);
        return bundle;
    }

    void onStartTrackingTouch() {
        this.mIsDragging = true;
    }

    void onStopTrackingTouch() {
        this.mIsDragging = false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.isEnabled()) {
            return false;
        }
        switch (motionEvent.getAction() & 255) {
            default: {
                return true;
            }
            case 6: {
                this.onSecondaryPointerUp(motionEvent);
                this.invalidate();
                return true;
            }
            case 5: {
                int n = motionEvent.getPointerCount() - 1;
                this.mDownMotionX = motionEvent.getX(n);
                this.mActivePointerId = motionEvent.getPointerId(n);
                this.invalidate();
                return true;
            }
            case 3: {
                if (this.mIsDragging) {
                    this.onStopTrackingTouch();
                    this.setPressed(false);
                }
                this.invalidate();
                return true;
            }
            case 2: {
                if (this.pressedThumb == null) break;
                if (this.mIsDragging) {
                    this.trackTouchEvent(motionEvent);
                } else if (Math.abs(motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId)) - this.mDownMotionX) > (float)this.mScaledTouchSlop) {
                    this.setPressed(true);
                    this.invalidate();
                    this.onStartTrackingTouch();
                    this.trackTouchEvent(motionEvent);
                    this.attemptClaimDrag();
                }
                if (!this.notifyWhileDragging || this.listener == null) break;
                this.listener.onRangeSeekBarValuesChanged(this, this.getSelectedMinValue(), this.getSelectedMaxValue());
                return true;
            }
            case 1: {
                if (this.mIsDragging) {
                    this.trackTouchEvent(motionEvent);
                    this.onStopTrackingTouch();
                    this.setPressed(false);
                } else {
                    this.onStartTrackingTouch();
                    this.trackTouchEvent(motionEvent);
                    this.onStopTrackingTouch();
                }
                this.pressedThumb = null;
                this.invalidate();
                if (this.listener == null) break;
                this.listener.onRangeSeekBarValuesChanged(this, this.getSelectedMinValue(), this.getSelectedMaxValue());
                return true;
            }
            case 0: {
                this.mActivePointerId = motionEvent.getPointerId(motionEvent.getPointerCount() - 1);
                this.mDownMotionX = motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId));
                this.pressedThumb = this.evalPressedThumb(this.mDownMotionX);
                if (this.pressedThumb == null) {
                    return super.onTouchEvent(motionEvent);
                }
                this.setPressed(true);
                this.invalidate();
                this.onStartTrackingTouch();
                this.trackTouchEvent(motionEvent);
                this.attemptClaimDrag();
            }
        }
        return true;
    }

    public void setNormalizedMaxValue(double d) {
        this.normalizedMaxValue = Math.max(0.0, Math.min(1.0, Math.max(d, this.normalizedMinValue)));
        this.invalidate();
    }

    public void setNormalizedMinValue(double d) {
        this.normalizedMinValue = Math.max(0.0, Math.min(1.0, Math.min(d, this.normalizedMaxValue)));
        this.invalidate();
    }

    public void setNotifyWhileDragging(boolean bl) {
        this.notifyWhileDragging = bl;
    }

    public void setOnRangeSeekBarChangeListener(OnRangeSeekBarChangeListener<T> onRangeSeekBarChangeListener) {
        this.listener = onRangeSeekBarChangeListener;
    }

    public void setSelectedMaxValue(T t) {
        if (0.0 == this.absoluteMaxValuePrim - this.absoluteMinValuePrim) {
            this.setNormalizedMaxValue(1.0);
            return;
        }
        this.setNormalizedMaxValue(this.valueToNormalized(t));
    }

    public void setSelectedMinValue(T t) {
        if (0.0 == this.absoluteMaxValuePrim - this.absoluteMinValuePrim) {
            this.setNormalizedMinValue(0.0);
            return;
        }
        this.setNormalizedMinValue(this.valueToNormalized(t));
    }

    private static enum NumberType {
        LONG,
        DOUBLE,
        INTEGER,
        FLOAT,
        SHORT,
        BYTE,
        BIG_DECIMAL;
        

        private NumberType() {
        }

        public static <E extends Number> NumberType fromNumber(E e) throws IllegalArgumentException {
            if (e instanceof Long) {
                return LONG;
            }
            if (e instanceof Double) {
                return DOUBLE;
            }
            if (e instanceof Integer) {
                return INTEGER;
            }
            if (e instanceof Float) {
                return FLOAT;
            }
            if (e instanceof Short) {
                return SHORT;
            }
            if (e instanceof Byte) {
                return BYTE;
            }
            if (e instanceof BigDecimal) {
                return BIG_DECIMAL;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Number class '");
            stringBuilder.append(e.getClass().getName());
            stringBuilder.append("' is not supported");
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public Number toNumber(double d) {
            switch (.$SwitchMap$de$cisha$android$ui$view$RangeSeekBar$NumberType[this.ordinal()]) {
                default: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("can't convert ");
                    stringBuilder.append((Object)this);
                    stringBuilder.append(" to a Number object");
                    throw new InstantiationError(stringBuilder.toString());
                }
                case 7: {
                    return new BigDecimal(d);
                }
                case 6: {
                    return new Byte((byte)d);
                }
                case 5: {
                    return new Short((short)d);
                }
                case 4: {
                    return new Float(d);
                }
                case 3: {
                    return new Integer((int)d);
                }
                case 2: {
                    return d;
                }
                case 1: 
            }
            return new Long((long)d);
        }
    }

    public static interface OnRangeSeekBarChangeListener<T> {
        public void onRangeSeekBarValuesChanged(RangeSeekBar<?> var1, T var2, T var3);
    }

    private static enum Thumb {
        MIN,
        MAX;
        

        private Thumb() {
        }
    }

}
