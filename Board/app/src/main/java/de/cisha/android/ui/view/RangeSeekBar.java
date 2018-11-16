// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.view;

import java.math.BigDecimal;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View.MeasureSpec;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.BitmapFactory;
import de.cisha.android.ui.patterns.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.widget.ImageView;

public class RangeSeekBar<T extends Number> extends ImageView
{
    public static final int ACTION_POINTER_INDEX_MASK = 65280;
    public static final int ACTION_POINTER_INDEX_SHIFT = 8;
    public static final int ACTION_POINTER_UP = 6;
    public static final int INVALID_POINTER_ID = 255;
    private int DEFAULT_COLOR;
    private final T absoluteMaxValue;
    private final double absoluteMaxValuePrim;
    private final T absoluteMinValue;
    private final double absoluteMinValuePrim;
    private final float lineHeight;
    private OnRangeSeekBarChangeListener<T> listener;
    private int mActivePointerId;
    private float mDownMotionX;
    private boolean mIsDragging;
    private int mScaledTouchSlop;
    float mTouchProgressOffset;
    private double normalizedMaxValue;
    private double normalizedMinValue;
    private boolean notifyWhileDragging;
    private final NumberType numberType;
    private final float padding;
    private final Paint paint;
    private Thumb pressedThumb;
    private final float thumbHalfHeight;
    private final float thumbHalfWidth;
    private final Bitmap thumbImage;
    private final Bitmap thumbPressedImage;
    private final float thumbWidth;
    
    public RangeSeekBar(final T absoluteMinValue, final T absoluteMaxValue, final Context context) throws IllegalArgumentException {
        super(context);
        this.paint = new Paint(1);
        this.thumbImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.seek_thumb_normal);
        this.thumbPressedImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.seek_thumb_pressed);
        this.thumbWidth = this.thumbImage.getWidth();
        this.thumbHalfWidth = this.thumbWidth * 0.5f;
        this.thumbHalfHeight = 0.5f * this.thumbImage.getHeight();
        this.lineHeight = 0.3f * this.thumbHalfHeight;
        this.padding = this.thumbHalfWidth;
        this.normalizedMinValue = 0.0;
        this.normalizedMaxValue = 1.0;
        this.pressedThumb = null;
        this.notifyWhileDragging = false;
        this.DEFAULT_COLOR = Color.argb(255, 51, 181, 229);
        this.mActivePointerId = 255;
        this.absoluteMinValue = absoluteMinValue;
        this.absoluteMaxValue = absoluteMaxValue;
        this.absoluteMinValuePrim = absoluteMinValue.doubleValue();
        this.absoluteMaxValuePrim = absoluteMaxValue.doubleValue();
        this.numberType = NumberType.fromNumber(absoluteMinValue);
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
    
    private void drawThumb(final float n, final boolean b, final Canvas canvas) {
        Bitmap bitmap;
        if (b) {
            bitmap = this.thumbPressedImage;
        }
        else {
            bitmap = this.thumbImage;
        }
        canvas.drawBitmap(bitmap, n - this.thumbHalfWidth, 0.5f * this.getHeight() - this.thumbHalfHeight, this.paint);
    }
    
    private Thumb evalPressedThumb(final float n) {
        final boolean inThumbRange = this.isInThumbRange(n, this.normalizedMinValue);
        final boolean inThumbRange2 = this.isInThumbRange(n, this.normalizedMaxValue);
        if (inThumbRange && inThumbRange2) {
            if (n / this.getWidth() > 0.5f) {
                return Thumb.MIN;
            }
            return Thumb.MAX;
        }
        else {
            if (inThumbRange) {
                return Thumb.MIN;
            }
            if (inThumbRange2) {
                return Thumb.MAX;
            }
            return null;
        }
    }
    
    private final void init() {
        this.mScaledTouchSlop = ViewConfiguration.get(this.getContext()).getScaledTouchSlop();
    }
    
    private boolean isInThumbRange(final float n, final double n2) {
        return Math.abs(n - this.normalizedToScreen(n2)) <= this.thumbHalfWidth;
    }
    
    private float normalizedToScreen(final double n) {
        return (float)(this.padding + n * (this.getWidth() - 2.0f * this.padding));
    }
    
    private T normalizedToValue(final double n) {
        return (T)this.numberType.toNumber(this.absoluteMinValuePrim + n * (this.absoluteMaxValuePrim - this.absoluteMinValuePrim));
    }
    
    private final void onSecondaryPointerUp(final MotionEvent motionEvent) {
        final int n = (motionEvent.getAction() & 0xFF00) >> 8;
        if (motionEvent.getPointerId(n) == this.mActivePointerId) {
            int n2;
            if (n == 0) {
                n2 = 1;
            }
            else {
                n2 = 0;
            }
            this.mDownMotionX = motionEvent.getX(n2);
            this.mActivePointerId = motionEvent.getPointerId(n2);
        }
    }
    
    private double screenToNormalized(final float n) {
        final float n2 = this.getWidth();
        if (n2 <= this.padding * 2.0f) {
            return 0.0;
        }
        return Math.min(1.0, Math.max(0.0, (n - this.padding) / (n2 - 2.0f * this.padding)));
    }
    
    private final void trackTouchEvent(final MotionEvent motionEvent) {
        final float x = motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId));
        if (Thumb.MIN.equals(this.pressedThumb)) {
            this.setNormalizedMinValue(this.screenToNormalized(x));
            return;
        }
        if (Thumb.MAX.equals(this.pressedThumb)) {
            this.setNormalizedMaxValue(this.screenToNormalized(x));
        }
    }
    
    private double valueToNormalized(final T t) {
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
    
    protected void onDraw(final Canvas canvas) {
        synchronized (this) {
            super.onDraw(canvas);
            final RectF rectF = new RectF(this.padding, (this.getHeight() - this.lineHeight) * 0.5f, this.getWidth() - this.padding, 0.5f * (this.getHeight() + this.lineHeight));
            this.paint.setStyle(Paint.Style.FILL);
            this.paint.setColor(-7829368);
            this.paint.setAntiAlias(true);
            canvas.drawRect(rectF, this.paint);
            rectF.left = this.normalizedToScreen(this.normalizedMinValue);
            rectF.right = this.normalizedToScreen(this.normalizedMaxValue);
            this.paint.setColor(this.DEFAULT_COLOR);
            canvas.drawRect(rectF, this.paint);
            this.drawThumb(this.normalizedToScreen(this.normalizedMinValue), Thumb.MIN.equals(this.pressedThumb), canvas);
            this.drawThumb(this.normalizedToScreen(this.normalizedMaxValue), Thumb.MAX.equals(this.pressedThumb), canvas);
        }
    }
    
    protected void onMeasure(int n, final int n2) {
        // monitorenter(this)
        int size = 200;
        try {
            if (View.MeasureSpec.getMode(n) != 0) {
                size = View.MeasureSpec.getSize(n);
            }
            final int n3 = n = this.thumbImage.getHeight();
            if (View.MeasureSpec.getMode(n2) != 0) {
                n = Math.min(n3, View.MeasureSpec.getSize(n2));
            }
            this.setMeasuredDimension(size, n);
        }
        finally {
        }
        // monitorexit(this)
    }
    
    protected void onRestoreInstanceState(final Parcelable parcelable) {
        final Bundle bundle = (Bundle)parcelable;
        super.onRestoreInstanceState(bundle.getParcelable("SUPER"));
        this.normalizedMinValue = bundle.getDouble("MIN");
        this.normalizedMaxValue = bundle.getDouble("MAX");
    }
    
    protected Parcelable onSaveInstanceState() {
        final Bundle bundle = new Bundle();
        bundle.putParcelable("SUPER", super.onSaveInstanceState());
        bundle.putDouble("MIN", this.normalizedMinValue);
        bundle.putDouble("MAX", this.normalizedMaxValue);
        return (Parcelable)bundle;
    }
    
    void onStartTrackingTouch() {
        this.mIsDragging = true;
    }
    
    void onStopTrackingTouch() {
        this.mIsDragging = false;
    }
    
    public boolean onTouchEvent(final MotionEvent motionEvent) {
        if (!this.isEnabled()) {
            return false;
        }
        switch (motionEvent.getAction() & 0xFF) {
            default: {
                return true;
            }
            case 6: {
                this.onSecondaryPointerUp(motionEvent);
                this.invalidate();
                return true;
            }
            case 5: {
                final int n = motionEvent.getPointerCount() - 1;
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
                if (this.pressedThumb == null) {
                    break;
                }
                if (this.mIsDragging) {
                    this.trackTouchEvent(motionEvent);
                }
                else if (Math.abs(motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId)) - this.mDownMotionX) > this.mScaledTouchSlop) {
                    this.setPressed(true);
                    this.invalidate();
                    this.onStartTrackingTouch();
                    this.trackTouchEvent(motionEvent);
                    this.attemptClaimDrag();
                }
                if (this.notifyWhileDragging && this.listener != null) {
                    this.listener.onRangeSeekBarValuesChanged(this, this.getSelectedMinValue(), this.getSelectedMaxValue());
                    return true;
                }
                break;
            }
            case 1: {
                if (this.mIsDragging) {
                    this.trackTouchEvent(motionEvent);
                    this.onStopTrackingTouch();
                    this.setPressed(false);
                }
                else {
                    this.onStartTrackingTouch();
                    this.trackTouchEvent(motionEvent);
                    this.onStopTrackingTouch();
                }
                this.pressedThumb = null;
                this.invalidate();
                if (this.listener != null) {
                    this.listener.onRangeSeekBarValuesChanged(this, this.getSelectedMinValue(), this.getSelectedMaxValue());
                    return true;
                }
                break;
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
                break;
            }
        }
        return true;
    }
    
    public void setNormalizedMaxValue(final double n) {
        this.normalizedMaxValue = Math.max(0.0, Math.min(1.0, Math.max(n, this.normalizedMinValue)));
        this.invalidate();
    }
    
    public void setNormalizedMinValue(final double n) {
        this.normalizedMinValue = Math.max(0.0, Math.min(1.0, Math.min(n, this.normalizedMaxValue)));
        this.invalidate();
    }
    
    public void setNotifyWhileDragging(final boolean notifyWhileDragging) {
        this.notifyWhileDragging = notifyWhileDragging;
    }
    
    public void setOnRangeSeekBarChangeListener(final OnRangeSeekBarChangeListener<T> listener) {
        this.listener = listener;
    }
    
    public void setSelectedMaxValue(final T t) {
        if (0.0 == this.absoluteMaxValuePrim - this.absoluteMinValuePrim) {
            this.setNormalizedMaxValue(1.0);
            return;
        }
        this.setNormalizedMaxValue(this.valueToNormalized(t));
    }
    
    public void setSelectedMinValue(final T t) {
        if (0.0 == this.absoluteMaxValuePrim - this.absoluteMinValuePrim) {
            this.setNormalizedMinValue(0.0);
            return;
        }
        this.setNormalizedMinValue(this.valueToNormalized(t));
    }
    
    private enum NumberType
    {
        BIG_DECIMAL, 
        BYTE, 
        DOUBLE, 
        FLOAT, 
        INTEGER, 
        LONG, 
        SHORT;
        
        public static <E extends Number> NumberType fromNumber(final E e) throws IllegalArgumentException {
            if (e instanceof Long) {
                return NumberType.LONG;
            }
            if (e instanceof Double) {
                return NumberType.DOUBLE;
            }
            if (e instanceof Integer) {
                return NumberType.INTEGER;
            }
            if (e instanceof Float) {
                return NumberType.FLOAT;
            }
            if (e instanceof Short) {
                return NumberType.SHORT;
            }
            if (e instanceof Byte) {
                return NumberType.BYTE;
            }
            if (e instanceof BigDecimal) {
                return NumberType.BIG_DECIMAL;
            }
            final StringBuilder sb = new StringBuilder();
            sb.append("Number class '");
            sb.append(e.getClass().getName());
            sb.append("' is not supported");
            throw new IllegalArgumentException(sb.toString());
        }
        
        public Number toNumber(final double n) {
            switch (RangeSeekBar.1..SwitchMap.de.cisha.android.ui.view.RangeSeekBar.NumberType[this.ordinal()]) {
                default: {
                    final StringBuilder sb = new StringBuilder();
                    sb.append("can't convert ");
                    sb.append(this);
                    sb.append(" to a Number object");
                    throw new InstantiationError(sb.toString());
                }
                case 7: {
                    return new BigDecimal(n);
                }
                case 6: {
                    return new Byte((byte)n);
                }
                case 5: {
                    return new Short((short)n);
                }
                case 4: {
                    return new Float(n);
                }
                case 3: {
                    return new Integer((int)n);
                }
                case 2: {
                    return n;
                }
                case 1: {
                    return new Long((long)n);
                }
            }
        }
    }
    
    public interface OnRangeSeekBarChangeListener<T>
    {
        void onRangeSeekBarValuesChanged(final RangeSeekBar<?> p0, final T p1, final T p2);
    }
    
    private enum Thumb
    {
        MAX, 
        MIN;
    }
}
