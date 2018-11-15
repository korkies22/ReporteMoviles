/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.ObjectAnimator
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.Region
 *  android.graphics.Region$Op
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.IBinder
 *  android.text.Layout
 *  android.text.Layout$Alignment
 *  android.text.StaticLayout
 *  android.text.TextPaint
 *  android.text.TextUtils
 *  android.text.method.TransformationMethod
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Property
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.ViewConfiguration
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.CompoundButton
 */
package android.support.v7.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.text.AllCapsTransformationMethod;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.ViewUtils;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Property;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import java.util.List;

public class SwitchCompat
extends CompoundButton {
    private static final String ACCESSIBILITY_EVENT_CLASS_NAME = "android.widget.Switch";
    private static final int[] CHECKED_STATE_SET;
    private static final int MONOSPACE = 3;
    private static final int SANS = 1;
    private static final int SERIF = 2;
    private static final int THUMB_ANIMATION_DURATION = 250;
    private static final Property<SwitchCompat, Float> THUMB_POS;
    private static final int TOUCH_MODE_DOWN = 1;
    private static final int TOUCH_MODE_DRAGGING = 2;
    private static final int TOUCH_MODE_IDLE = 0;
    private boolean mHasThumbTint = false;
    private boolean mHasThumbTintMode = false;
    private boolean mHasTrackTint = false;
    private boolean mHasTrackTintMode = false;
    private int mMinFlingVelocity;
    private Layout mOffLayout;
    private Layout mOnLayout;
    ObjectAnimator mPositionAnimator;
    private boolean mShowText;
    private boolean mSplitTrack;
    private int mSwitchBottom;
    private int mSwitchHeight;
    private int mSwitchLeft;
    private int mSwitchMinWidth;
    private int mSwitchPadding;
    private int mSwitchRight;
    private int mSwitchTop;
    private TransformationMethod mSwitchTransformationMethod;
    private int mSwitchWidth;
    private final Rect mTempRect = new Rect();
    private ColorStateList mTextColors;
    private CharSequence mTextOff;
    private CharSequence mTextOn;
    private final TextPaint mTextPaint = new TextPaint(1);
    private Drawable mThumbDrawable;
    private float mThumbPosition;
    private int mThumbTextPadding;
    private ColorStateList mThumbTintList = null;
    private PorterDuff.Mode mThumbTintMode = null;
    private int mThumbWidth;
    private int mTouchMode;
    private int mTouchSlop;
    private float mTouchX;
    private float mTouchY;
    private Drawable mTrackDrawable;
    private ColorStateList mTrackTintList = null;
    private PorterDuff.Mode mTrackTintMode = null;
    private VelocityTracker mVelocityTracker = VelocityTracker.obtain();

    static {
        THUMB_POS = new Property<SwitchCompat, Float>(Float.class, "thumbPos"){

            public Float get(SwitchCompat switchCompat) {
                return Float.valueOf(switchCompat.mThumbPosition);
            }

            public void set(SwitchCompat switchCompat, Float f) {
                switchCompat.setThumbPosition(f.floatValue());
            }
        };
        CHECKED_STATE_SET = new int[]{16842912};
    }

    public SwitchCompat(Context context) {
        this(context, null);
    }

    public SwitchCompat(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.switchStyle);
    }

    public SwitchCompat(Context context, AttributeSet object, int n) {
        super(context, (AttributeSet)object, n);
        Resources resources = this.getResources();
        this.mTextPaint.density = resources.getDisplayMetrics().density;
        object = TintTypedArray.obtainStyledAttributes(context, (AttributeSet)object, R.styleable.SwitchCompat, n, 0);
        this.mThumbDrawable = object.getDrawable(R.styleable.SwitchCompat_android_thumb);
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.setCallback((Drawable.Callback)this);
        }
        this.mTrackDrawable = object.getDrawable(R.styleable.SwitchCompat_track);
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setCallback((Drawable.Callback)this);
        }
        this.mTextOn = object.getText(R.styleable.SwitchCompat_android_textOn);
        this.mTextOff = object.getText(R.styleable.SwitchCompat_android_textOff);
        this.mShowText = object.getBoolean(R.styleable.SwitchCompat_showText, true);
        this.mThumbTextPadding = object.getDimensionPixelSize(R.styleable.SwitchCompat_thumbTextPadding, 0);
        this.mSwitchMinWidth = object.getDimensionPixelSize(R.styleable.SwitchCompat_switchMinWidth, 0);
        this.mSwitchPadding = object.getDimensionPixelSize(R.styleable.SwitchCompat_switchPadding, 0);
        this.mSplitTrack = object.getBoolean(R.styleable.SwitchCompat_splitTrack, false);
        resources = object.getColorStateList(R.styleable.SwitchCompat_thumbTint);
        if (resources != null) {
            this.mThumbTintList = resources;
            this.mHasThumbTint = true;
        }
        if (this.mThumbTintMode != (resources = DrawableUtils.parseTintMode(object.getInt(R.styleable.SwitchCompat_thumbTintMode, -1), null))) {
            this.mThumbTintMode = resources;
            this.mHasThumbTintMode = true;
        }
        if (this.mHasThumbTint || this.mHasThumbTintMode) {
            this.applyThumbTint();
        }
        if ((resources = object.getColorStateList(R.styleable.SwitchCompat_trackTint)) != null) {
            this.mTrackTintList = resources;
            this.mHasTrackTint = true;
        }
        if (this.mTrackTintMode != (resources = DrawableUtils.parseTintMode(object.getInt(R.styleable.SwitchCompat_trackTintMode, -1), null))) {
            this.mTrackTintMode = resources;
            this.mHasTrackTintMode = true;
        }
        if (this.mHasTrackTint || this.mHasTrackTintMode) {
            this.applyTrackTint();
        }
        if ((n = object.getResourceId(R.styleable.SwitchCompat_switchTextAppearance, 0)) != 0) {
            this.setSwitchTextAppearance(context, n);
        }
        object.recycle();
        context = ViewConfiguration.get((Context)context);
        this.mTouchSlop = context.getScaledTouchSlop();
        this.mMinFlingVelocity = context.getScaledMinimumFlingVelocity();
        this.refreshDrawableState();
        this.setChecked(this.isChecked());
    }

    private void animateThumbToCheckedState(boolean bl) {
        float f = bl ? 1.0f : 0.0f;
        this.mPositionAnimator = ObjectAnimator.ofFloat((Object)((Object)this), THUMB_POS, (float[])new float[]{f});
        this.mPositionAnimator.setDuration(250L);
        if (Build.VERSION.SDK_INT >= 18) {
            this.mPositionAnimator.setAutoCancel(true);
        }
        this.mPositionAnimator.start();
    }

    private void applyThumbTint() {
        if (this.mThumbDrawable != null && (this.mHasThumbTint || this.mHasThumbTintMode)) {
            this.mThumbDrawable = this.mThumbDrawable.mutate();
            if (this.mHasThumbTint) {
                DrawableCompat.setTintList(this.mThumbDrawable, this.mThumbTintList);
            }
            if (this.mHasThumbTintMode) {
                DrawableCompat.setTintMode(this.mThumbDrawable, this.mThumbTintMode);
            }
            if (this.mThumbDrawable.isStateful()) {
                this.mThumbDrawable.setState(this.getDrawableState());
            }
        }
    }

    private void applyTrackTint() {
        if (this.mTrackDrawable != null && (this.mHasTrackTint || this.mHasTrackTintMode)) {
            this.mTrackDrawable = this.mTrackDrawable.mutate();
            if (this.mHasTrackTint) {
                DrawableCompat.setTintList(this.mTrackDrawable, this.mTrackTintList);
            }
            if (this.mHasTrackTintMode) {
                DrawableCompat.setTintMode(this.mTrackDrawable, this.mTrackTintMode);
            }
            if (this.mTrackDrawable.isStateful()) {
                this.mTrackDrawable.setState(this.getDrawableState());
            }
        }
    }

    private void cancelPositionAnimator() {
        if (this.mPositionAnimator != null) {
            this.mPositionAnimator.cancel();
        }
    }

    private void cancelSuperTouch(MotionEvent motionEvent) {
        motionEvent = MotionEvent.obtain((MotionEvent)motionEvent);
        motionEvent.setAction(3);
        super.onTouchEvent(motionEvent);
        motionEvent.recycle();
    }

    private static float constrain(float f, float f2, float f3) {
        if (f < f2) {
            return f2;
        }
        f2 = f;
        if (f > f3) {
            f2 = f3;
        }
        return f2;
    }

    private boolean getTargetCheckedState() {
        if (this.mThumbPosition > 0.5f) {
            return true;
        }
        return false;
    }

    private int getThumbOffset() {
        float f = ViewUtils.isLayoutRtl((View)this) ? 1.0f - this.mThumbPosition : this.mThumbPosition;
        return (int)(f * (float)this.getThumbScrollRange() + 0.5f);
    }

    private int getThumbScrollRange() {
        if (this.mTrackDrawable != null) {
            Rect rect = this.mTempRect;
            this.mTrackDrawable.getPadding(rect);
            Rect rect2 = this.mThumbDrawable != null ? DrawableUtils.getOpticalBounds(this.mThumbDrawable) : DrawableUtils.INSETS_NONE;
            return this.mSwitchWidth - this.mThumbWidth - rect.left - rect.right - rect2.left - rect2.right;
        }
        return 0;
    }

    private boolean hitThumb(float f, float f2) {
        Drawable drawable = this.mThumbDrawable;
        boolean bl = false;
        if (drawable == null) {
            return false;
        }
        int n = this.getThumbOffset();
        this.mThumbDrawable.getPadding(this.mTempRect);
        int n2 = this.mSwitchTop;
        int n3 = this.mTouchSlop;
        n = this.mSwitchLeft + n - this.mTouchSlop;
        int n4 = this.mThumbWidth;
        int n5 = this.mTempRect.left;
        int n6 = this.mTempRect.right;
        int n7 = this.mTouchSlop;
        int n8 = this.mSwitchBottom;
        int n9 = this.mTouchSlop;
        boolean bl2 = bl;
        if (f > (float)n) {
            bl2 = bl;
            if (f < (float)(n4 + n + n5 + n6 + n7)) {
                bl2 = bl;
                if (f2 > (float)(n2 - n3)) {
                    bl2 = bl;
                    if (f2 < (float)(n8 + n9)) {
                        bl2 = true;
                    }
                }
            }
        }
        return bl2;
    }

    private Layout makeLayout(CharSequence charSequence) {
        CharSequence charSequence2;
        int n;
        charSequence2 = charSequence;
        if (this.mSwitchTransformationMethod != null) {
            charSequence2 = this.mSwitchTransformationMethod.getTransformation(charSequence, (View)this);
        }
        charSequence = this.mTextPaint;
        n = charSequence2 != null ? (int)Math.ceil(Layout.getDesiredWidth((CharSequence)charSequence2, (TextPaint)this.mTextPaint)) : 0;
        return new StaticLayout(charSequence2, (TextPaint)charSequence, n, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
    }

    private void setSwitchTypefaceByIndex(int n, int n2) {
        Typeface typeface;
        switch (n) {
            default: {
                typeface = null;
                break;
            }
            case 3: {
                typeface = Typeface.MONOSPACE;
                break;
            }
            case 2: {
                typeface = Typeface.SERIF;
                break;
            }
            case 1: {
                typeface = Typeface.SANS_SERIF;
            }
        }
        this.setSwitchTypeface(typeface, n2);
    }

    private void stopDrag(MotionEvent motionEvent) {
        this.mTouchMode = 0;
        int n = motionEvent.getAction();
        boolean bl = true;
        n = n == 1 && this.isEnabled() ? 1 : 0;
        boolean bl2 = this.isChecked();
        if (n != 0) {
            this.mVelocityTracker.computeCurrentVelocity(1000);
            float f = this.mVelocityTracker.getXVelocity();
            if (Math.abs(f) > (float)this.mMinFlingVelocity) {
                if (!(ViewUtils.isLayoutRtl((View)this) ? f < 0.0f : f > 0.0f)) {
                    bl = false;
                }
            } else {
                bl = this.getTargetCheckedState();
            }
        } else {
            bl = bl2;
        }
        if (bl != bl2) {
            this.playSoundEffect(0);
        }
        this.setChecked(bl);
        this.cancelSuperTouch(motionEvent);
    }

    public void draw(Canvas canvas) {
        Rect rect;
        int n;
        Rect rect2;
        int n2;
        int n3;
        int n4;
        block4 : {
            int n5;
            int n6;
            int n7;
            block7 : {
                int n8;
                int n9;
                block6 : {
                    block5 : {
                        rect2 = this.mTempRect;
                        n9 = this.mSwitchLeft;
                        n4 = this.mSwitchTop;
                        n8 = this.mSwitchRight;
                        n = this.mSwitchBottom;
                        n3 = this.getThumbOffset() + n9;
                        rect = this.mThumbDrawable != null ? DrawableUtils.getOpticalBounds(this.mThumbDrawable) : DrawableUtils.INSETS_NONE;
                        n2 = n3;
                        if (this.mTrackDrawable == null) break block4;
                        this.mTrackDrawable.getPadding(rect2);
                        n5 = n3 + rect2.left;
                        if (rect == null) break block5;
                        n2 = n9;
                        if (rect.left > rect2.left) {
                            n2 = n9 + (rect.left - rect2.left);
                        }
                        n3 = rect.top > rect2.top ? rect.top - rect2.top + n4 : n4;
                        n6 = n8;
                        if (rect.right > rect2.right) {
                            n6 = n8 - (rect.right - rect2.right);
                        }
                        n9 = n2;
                        n8 = n6;
                        n7 = n3;
                        if (rect.bottom <= rect2.bottom) break block6;
                        n7 = n - (rect.bottom - rect2.bottom);
                        break block7;
                    }
                    n7 = n4;
                }
                n2 = n;
                n3 = n7;
                n7 = n2;
                n6 = n8;
                n2 = n9;
            }
            this.mTrackDrawable.setBounds(n2, n3, n6, n7);
            n2 = n5;
        }
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.getPadding(rect2);
            n3 = n2 - rect2.left;
            n2 = n2 + this.mThumbWidth + rect2.right;
            this.mThumbDrawable.setBounds(n3, n4, n2, n);
            rect = this.getBackground();
            if (rect != null) {
                DrawableCompat.setHotspotBounds((Drawable)rect, n3, n4, n2, n);
            }
        }
        super.draw(canvas);
    }

    public void drawableHotspotChanged(float f, float f2) {
        if (Build.VERSION.SDK_INT >= 21) {
            super.drawableHotspotChanged(f, f2);
        }
        if (this.mThumbDrawable != null) {
            DrawableCompat.setHotspot(this.mThumbDrawable, f, f2);
        }
        if (this.mTrackDrawable != null) {
            DrawableCompat.setHotspot(this.mTrackDrawable, f, f2);
        }
    }

    protected void drawableStateChanged() {
        boolean bl;
        super.drawableStateChanged();
        int[] arrn = this.getDrawableState();
        Drawable drawable = this.mThumbDrawable;
        boolean bl2 = bl = false;
        if (drawable != null) {
            bl2 = bl;
            if (drawable.isStateful()) {
                bl2 = false | drawable.setState(arrn);
            }
        }
        drawable = this.mTrackDrawable;
        bl = bl2;
        if (drawable != null) {
            bl = bl2;
            if (drawable.isStateful()) {
                bl = bl2 | drawable.setState(arrn);
            }
        }
        if (bl) {
            this.invalidate();
        }
    }

    public int getCompoundPaddingLeft() {
        int n;
        if (!ViewUtils.isLayoutRtl((View)this)) {
            return super.getCompoundPaddingLeft();
        }
        int n2 = n = super.getCompoundPaddingLeft() + this.mSwitchWidth;
        if (!TextUtils.isEmpty((CharSequence)this.getText())) {
            n2 = n + this.mSwitchPadding;
        }
        return n2;
    }

    public int getCompoundPaddingRight() {
        int n;
        if (ViewUtils.isLayoutRtl((View)this)) {
            return super.getCompoundPaddingRight();
        }
        int n2 = n = super.getCompoundPaddingRight() + this.mSwitchWidth;
        if (!TextUtils.isEmpty((CharSequence)this.getText())) {
            n2 = n + this.mSwitchPadding;
        }
        return n2;
    }

    public boolean getShowText() {
        return this.mShowText;
    }

    public boolean getSplitTrack() {
        return this.mSplitTrack;
    }

    public int getSwitchMinWidth() {
        return this.mSwitchMinWidth;
    }

    public int getSwitchPadding() {
        return this.mSwitchPadding;
    }

    public CharSequence getTextOff() {
        return this.mTextOff;
    }

    public CharSequence getTextOn() {
        return this.mTextOn;
    }

    public Drawable getThumbDrawable() {
        return this.mThumbDrawable;
    }

    public int getThumbTextPadding() {
        return this.mThumbTextPadding;
    }

    @Nullable
    public ColorStateList getThumbTintList() {
        return this.mThumbTintList;
    }

    @Nullable
    public PorterDuff.Mode getThumbTintMode() {
        return this.mThumbTintMode;
    }

    public Drawable getTrackDrawable() {
        return this.mTrackDrawable;
    }

    @Nullable
    public ColorStateList getTrackTintList() {
        return this.mTrackTintList;
    }

    @Nullable
    public PorterDuff.Mode getTrackTintMode() {
        return this.mTrackTintMode;
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.jumpToCurrentState();
        }
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.jumpToCurrentState();
        }
        if (this.mPositionAnimator != null && this.mPositionAnimator.isStarted()) {
            this.mPositionAnimator.end();
            this.mPositionAnimator = null;
        }
    }

    protected int[] onCreateDrawableState(int n) {
        int[] arrn = super.onCreateDrawableState(n + 1);
        if (this.isChecked()) {
            SwitchCompat.mergeDrawableStates((int[])arrn, (int[])CHECKED_STATE_SET);
        }
        return arrn;
    }

    protected void onDraw(Canvas canvas) {
        int n;
        super.onDraw(canvas);
        Rect rect = this.mTempRect;
        int[] arrn = this.mTrackDrawable;
        if (arrn != null) {
            arrn.getPadding(rect);
        } else {
            rect.setEmpty();
        }
        int n2 = this.mSwitchTop;
        int n3 = this.mSwitchBottom;
        int n4 = rect.top;
        int n5 = rect.bottom;
        Drawable drawable = this.mThumbDrawable;
        if (arrn != null) {
            if (this.mSplitTrack && drawable != null) {
                Rect rect2 = DrawableUtils.getOpticalBounds(drawable);
                drawable.copyBounds(rect);
                rect.left += rect2.left;
                rect.right -= rect2.right;
                n = canvas.save();
                canvas.clipRect(rect, Region.Op.DIFFERENCE);
                arrn.draw(canvas);
                canvas.restoreToCount(n);
            } else {
                arrn.draw(canvas);
            }
        }
        int n6 = canvas.save();
        if (drawable != null) {
            drawable.draw(canvas);
        }
        rect = this.getTargetCheckedState() ? this.mOnLayout : this.mOffLayout;
        if (rect != null) {
            arrn = this.getDrawableState();
            if (this.mTextColors != null) {
                this.mTextPaint.setColor(this.mTextColors.getColorForState(arrn, 0));
            }
            this.mTextPaint.drawableState = arrn;
            if (drawable != null) {
                drawable = drawable.getBounds();
                n = drawable.left + drawable.right;
            } else {
                n = this.getWidth();
            }
            int n7 = rect.getWidth() / 2;
            n2 = (n2 + n4 + (n3 - n5)) / 2;
            n3 = rect.getHeight() / 2;
            canvas.translate((float)((n /= 2) - n7), (float)(n2 - n3));
            rect.draw(canvas);
        }
        canvas.restoreToCount(n6);
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName((CharSequence)ACCESSIBILITY_EVENT_CLASS_NAME);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName((CharSequence)ACCESSIBILITY_EVENT_CLASS_NAME);
        CharSequence charSequence = this.isChecked() ? this.mTextOn : this.mTextOff;
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            CharSequence charSequence2 = accessibilityNodeInfo.getText();
            if (TextUtils.isEmpty((CharSequence)charSequence2)) {
                accessibilityNodeInfo.setText(charSequence);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(charSequence2);
            stringBuilder.append(' ');
            stringBuilder.append(charSequence);
            accessibilityNodeInfo.setText((CharSequence)stringBuilder);
        }
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        Drawable drawable = this.mThumbDrawable;
        n2 = 0;
        if (drawable != null) {
            drawable = this.mTempRect;
            if (this.mTrackDrawable != null) {
                this.mTrackDrawable.getPadding((Rect)drawable);
            } else {
                drawable.setEmpty();
            }
            Rect rect = DrawableUtils.getOpticalBounds(this.mThumbDrawable);
            n2 = Math.max(0, rect.left - drawable.left);
            n = Math.max(0, rect.right - drawable.right);
        } else {
            n = 0;
        }
        if (ViewUtils.isLayoutRtl((View)this)) {
            n3 = this.getPaddingLeft() + n2;
            n4 = this.mSwitchWidth + n3 - n2 - n;
        } else {
            n4 = this.getWidth() - this.getPaddingRight() - n;
            n3 = n4 - this.mSwitchWidth + n2 + n;
        }
        n = this.getGravity() & 112;
        if (n != 16) {
            if (n != 80) {
                n = this.getPaddingTop();
                n2 = this.mSwitchHeight + n;
            } else {
                n2 = this.getHeight() - this.getPaddingBottom();
                n = n2 - this.mSwitchHeight;
            }
        } else {
            n = (this.getPaddingTop() + this.getHeight() - this.getPaddingBottom()) / 2 - this.mSwitchHeight / 2;
            n2 = this.mSwitchHeight + n;
        }
        this.mSwitchLeft = n3;
        this.mSwitchTop = n;
        this.mSwitchBottom = n2;
        this.mSwitchRight = n4;
    }

    public void onMeasure(int n, int n2) {
        int n3;
        int n4;
        int n5;
        if (this.mShowText) {
            if (this.mOnLayout == null) {
                this.mOnLayout = this.makeLayout(this.mTextOn);
            }
            if (this.mOffLayout == null) {
                this.mOffLayout = this.makeLayout(this.mTextOff);
            }
        }
        Rect rect = this.mTempRect;
        Drawable drawable = this.mThumbDrawable;
        int n6 = 0;
        if (drawable != null) {
            this.mThumbDrawable.getPadding(rect);
            n4 = this.mThumbDrawable.getIntrinsicWidth() - rect.left - rect.right;
            n5 = this.mThumbDrawable.getIntrinsicHeight();
        } else {
            n5 = n4 = 0;
        }
        int n7 = this.mShowText ? Math.max(this.mOnLayout.getWidth(), this.mOffLayout.getWidth()) + this.mThumbTextPadding * 2 : 0;
        this.mThumbWidth = Math.max(n7, n4);
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.getPadding(rect);
            n4 = this.mTrackDrawable.getIntrinsicHeight();
        } else {
            rect.setEmpty();
            n4 = n6;
        }
        int n8 = rect.left;
        n6 = n3 = rect.right;
        n7 = n8;
        if (this.mThumbDrawable != null) {
            rect = DrawableUtils.getOpticalBounds(this.mThumbDrawable);
            n7 = Math.max(n8, rect.left);
            n6 = Math.max(n3, rect.right);
        }
        n7 = Math.max(this.mSwitchMinWidth, 2 * this.mThumbWidth + n7 + n6);
        n5 = Math.max(n4, n5);
        this.mSwitchWidth = n7;
        this.mSwitchHeight = n5;
        super.onMeasure(n, n2);
        if (this.getMeasuredHeight() < n5) {
            this.setMeasuredDimension(this.getMeasuredWidthAndState(), n5);
        }
    }

    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        CharSequence charSequence = this.isChecked() ? this.mTextOn : this.mTextOff;
        if (charSequence != null) {
            accessibilityEvent.getText().add(charSequence);
        }
    }

    /*
     * Exception decompiling
     */
    public boolean onTouchEvent(MotionEvent var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:487)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:66)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:374)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:186)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:131)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:378)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:884)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:786)
        // org.benf.cfr.reader.Main.doClass(Main.java:54)
        // org.benf.cfr.reader.Main.main(Main.java:247)
        throw new IllegalStateException("Decompilation failed");
    }

    public void setChecked(boolean bl) {
        super.setChecked(bl);
        bl = this.isChecked();
        if (this.getWindowToken() != null && ViewCompat.isLaidOut((View)this)) {
            this.animateThumbToCheckedState(bl);
            return;
        }
        this.cancelPositionAnimator();
        float f = bl ? 1.0f : 0.0f;
        this.setThumbPosition(f);
    }

    public void setShowText(boolean bl) {
        if (this.mShowText != bl) {
            this.mShowText = bl;
            this.requestLayout();
        }
    }

    public void setSplitTrack(boolean bl) {
        this.mSplitTrack = bl;
        this.invalidate();
    }

    public void setSwitchMinWidth(int n) {
        this.mSwitchMinWidth = n;
        this.requestLayout();
    }

    public void setSwitchPadding(int n) {
        this.mSwitchPadding = n;
        this.requestLayout();
    }

    public void setSwitchTextAppearance(Context object, int n) {
        float f;
        ColorStateList colorStateList = (object = TintTypedArray.obtainStyledAttributes((Context)object, n, R.styleable.TextAppearance)).getColorStateList(R.styleable.TextAppearance_android_textColor);
        this.mTextColors = colorStateList != null ? colorStateList : this.getTextColors();
        n = object.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 0);
        if (n != 0 && (f = (float)n) != this.mTextPaint.getTextSize()) {
            this.mTextPaint.setTextSize(f);
            this.requestLayout();
        }
        this.setSwitchTypefaceByIndex(object.getInt(R.styleable.TextAppearance_android_typeface, -1), object.getInt(R.styleable.TextAppearance_android_textStyle, -1));
        this.mSwitchTransformationMethod = object.getBoolean(R.styleable.TextAppearance_textAllCaps, false) ? new AllCapsTransformationMethod(this.getContext()) : null;
        object.recycle();
    }

    public void setSwitchTypeface(Typeface typeface) {
        if (this.mTextPaint.getTypeface() != null && !this.mTextPaint.getTypeface().equals((Object)typeface) || this.mTextPaint.getTypeface() == null && typeface != null) {
            this.mTextPaint.setTypeface(typeface);
            this.requestLayout();
            this.invalidate();
        }
    }

    public void setSwitchTypeface(Typeface typeface, int n) {
        float f = 0.0f;
        boolean bl = false;
        if (n > 0) {
            typeface = typeface == null ? Typeface.defaultFromStyle((int)n) : Typeface.create((Typeface)typeface, (int)n);
            this.setSwitchTypeface(typeface);
            int n2 = typeface != null ? typeface.getStyle() : 0;
            n = ~ n2 & n;
            typeface = this.mTextPaint;
            if ((n & 1) != 0) {
                bl = true;
            }
            typeface.setFakeBoldText(bl);
            typeface = this.mTextPaint;
            if ((n & 2) != 0) {
                f = -0.25f;
            }
            typeface.setTextSkewX(f);
            return;
        }
        this.mTextPaint.setFakeBoldText(false);
        this.mTextPaint.setTextSkewX(0.0f);
        this.setSwitchTypeface(typeface);
    }

    public void setTextOff(CharSequence charSequence) {
        this.mTextOff = charSequence;
        this.requestLayout();
    }

    public void setTextOn(CharSequence charSequence) {
        this.mTextOn = charSequence;
        this.requestLayout();
    }

    public void setThumbDrawable(Drawable drawable) {
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.setCallback(null);
        }
        this.mThumbDrawable = drawable;
        if (drawable != null) {
            drawable.setCallback((Drawable.Callback)this);
        }
        this.requestLayout();
    }

    void setThumbPosition(float f) {
        this.mThumbPosition = f;
        this.invalidate();
    }

    public void setThumbResource(int n) {
        this.setThumbDrawable(AppCompatResources.getDrawable(this.getContext(), n));
    }

    public void setThumbTextPadding(int n) {
        this.mThumbTextPadding = n;
        this.requestLayout();
    }

    public void setThumbTintList(@Nullable ColorStateList colorStateList) {
        this.mThumbTintList = colorStateList;
        this.mHasThumbTint = true;
        this.applyThumbTint();
    }

    public void setThumbTintMode(@Nullable PorterDuff.Mode mode) {
        this.mThumbTintMode = mode;
        this.mHasThumbTintMode = true;
        this.applyThumbTint();
    }

    public void setTrackDrawable(Drawable drawable) {
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setCallback(null);
        }
        this.mTrackDrawable = drawable;
        if (drawable != null) {
            drawable.setCallback((Drawable.Callback)this);
        }
        this.requestLayout();
    }

    public void setTrackResource(int n) {
        this.setTrackDrawable(AppCompatResources.getDrawable(this.getContext(), n));
    }

    public void setTrackTintList(@Nullable ColorStateList colorStateList) {
        this.mTrackTintList = colorStateList;
        this.mHasTrackTint = true;
        this.applyTrackTint();
    }

    public void setTrackTintMode(@Nullable PorterDuff.Mode mode) {
        this.mTrackTintMode = mode;
        this.mHasTrackTintMode = true;
        this.applyTrackTint();
    }

    public void toggle() {
        this.setChecked(this.isChecked() ^ true);
    }

    protected boolean verifyDrawable(Drawable drawable) {
        if (!super.verifyDrawable(drawable) && drawable != this.mThumbDrawable && drawable != this.mTrackDrawable) {
            return false;
        }
        return true;
    }

}
