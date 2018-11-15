/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.method.PasswordTransformationMethod
 *  android.text.method.TransformationMethod
 *  android.util.AttributeSet
 *  android.widget.TextView
 */
package android.support.v7.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.AutoSizeableTextView;
import android.support.v7.appcompat.R;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.AppCompatTextHelperV17;
import android.support.v7.widget.AppCompatTextViewAutoSizeHelper;
import android.support.v7.widget.TintInfo;
import android.support.v7.widget.TintTypedArray;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.widget.TextView;
import java.lang.ref.WeakReference;

@RequiresApi(value=9)
class AppCompatTextHelper {
    private static final int MONOSPACE = 3;
    private static final int SANS = 1;
    private static final int SERIF = 2;
    private boolean mAsyncFontPending;
    @NonNull
    private final AppCompatTextViewAutoSizeHelper mAutoSizeTextHelper;
    private TintInfo mDrawableBottomTint;
    private TintInfo mDrawableLeftTint;
    private TintInfo mDrawableRightTint;
    private TintInfo mDrawableTopTint;
    private Typeface mFontTypeface;
    private int mStyle = 0;
    final TextView mView;

    AppCompatTextHelper(TextView textView) {
        this.mView = textView;
        this.mAutoSizeTextHelper = new AppCompatTextViewAutoSizeHelper(this.mView);
    }

    static AppCompatTextHelper create(TextView textView) {
        if (Build.VERSION.SDK_INT >= 17) {
            return new AppCompatTextHelperV17(textView);
        }
        return new AppCompatTextHelper(textView);
    }

    protected static TintInfo createTintInfo(Context context, AppCompatDrawableManager object, int n) {
        if ((context = object.getTintList(context, n)) != null) {
            object = new TintInfo();
            object.mHasTintList = true;
            object.mTintList = context;
            return object;
        }
        return null;
    }

    private void onAsyncTypefaceReceived(WeakReference<TextView> textView, Typeface typeface) {
        if (this.mAsyncFontPending) {
            this.mFontTypeface = typeface;
            if ((textView = (TextView)textView.get()) != null) {
                textView.setTypeface(typeface, this.mStyle);
            }
        }
    }

    private void setTextSizeInternal(int n, float f) {
        this.mAutoSizeTextHelper.setTextSizeInternal(n, f);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateTypefaceAndStyle(Context object, TintTypedArray tintTypedArray) {
        int n;
        block13 : {
            this.mStyle = tintTypedArray.getInt(R.styleable.TextAppearance_android_textStyle, this.mStyle);
            boolean bl = tintTypedArray.hasValue(R.styleable.TextAppearance_android_fontFamily);
            boolean bl2 = true;
            if (!bl && !tintTypedArray.hasValue(R.styleable.TextAppearance_fontFamily)) {
                if (tintTypedArray.hasValue(R.styleable.TextAppearance_android_typeface)) {
                    this.mAsyncFontPending = false;
                    switch (tintTypedArray.getInt(R.styleable.TextAppearance_android_typeface, 1)) {
                        default: {
                            return;
                        }
                        case 3: {
                            this.mFontTypeface = Typeface.MONOSPACE;
                            return;
                        }
                        case 2: {
                            this.mFontTypeface = Typeface.SERIF;
                            return;
                        }
                        case 1: 
                    }
                    this.mFontTypeface = Typeface.SANS_SERIF;
                }
                return;
            }
            this.mFontTypeface = null;
            n = tintTypedArray.hasValue(R.styleable.TextAppearance_fontFamily) ? R.styleable.TextAppearance_fontFamily : R.styleable.TextAppearance_android_fontFamily;
            if (!object.isRestricted()) {
                block12 : {
                    block11 : {
                        object = new ResourcesCompat.FontCallback(new WeakReference<TextView>(this.mView)){
                            final /* synthetic */ WeakReference val$textViewWeak;
                            {
                                this.val$textViewWeak = weakReference;
                            }

                            @Override
                            public void onFontRetrievalFailed(int n) {
                            }

                            @Override
                            public void onFontRetrieved(@NonNull Typeface typeface) {
                                AppCompatTextHelper.this.onAsyncTypefaceReceived(this.val$textViewWeak, typeface);
                            }
                        };
                        try {
                            this.mFontTypeface = tintTypedArray.getFont(n, this.mStyle, (ResourcesCompat.FontCallback)object);
                            if (this.mFontTypeface != null) break block11;
                            break block12;
                        }
                        catch (Resources.NotFoundException | UnsupportedOperationException object2) {
                            break block13;
                        }
                    }
                    bl2 = false;
                }
                this.mAsyncFontPending = bl2;
            }
        }
        if (this.mFontTypeface == null && (object = tintTypedArray.getString(n)) != null) {
            this.mFontTypeface = Typeface.create((String)object, (int)this.mStyle);
        }
    }

    final void applyCompoundDrawableTint(Drawable drawable, TintInfo tintInfo) {
        if (drawable != null && tintInfo != null) {
            AppCompatDrawableManager.tintDrawable(drawable, tintInfo, this.mView.getDrawableState());
        }
    }

    void applyCompoundDrawablesTints() {
        if (this.mDrawableLeftTint != null || this.mDrawableTopTint != null || this.mDrawableRightTint != null || this.mDrawableBottomTint != null) {
            Drawable[] arrdrawable = this.mView.getCompoundDrawables();
            this.applyCompoundDrawableTint(arrdrawable[0], this.mDrawableLeftTint);
            this.applyCompoundDrawableTint(arrdrawable[1], this.mDrawableTopTint);
            this.applyCompoundDrawableTint(arrdrawable[2], this.mDrawableRightTint);
            this.applyCompoundDrawableTint(arrdrawable[3], this.mDrawableBottomTint);
        }
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    void autoSizeText() {
        this.mAutoSizeTextHelper.autoSizeText();
    }

    int getAutoSizeMaxTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMaxTextSize();
    }

    int getAutoSizeMinTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMinTextSize();
    }

    int getAutoSizeStepGranularity() {
        return this.mAutoSizeTextHelper.getAutoSizeStepGranularity();
    }

    int[] getAutoSizeTextAvailableSizes() {
        return this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes();
    }

    int getAutoSizeTextType() {
        return this.mAutoSizeTextHelper.getAutoSizeTextType();
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    boolean isAutoSizeEnabled() {
        return this.mAutoSizeTextHelper.isAutoSizeEnabled();
    }

    @SuppressLint(value={"NewApi"})
    void loadFromAttributes(AttributeSet arrn, int n) {
        boolean bl;
        boolean bl2;
        Context context = this.mView.getContext();
        AppCompatDrawableManager appCompatDrawableManager = AppCompatDrawableManager.get();
        TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(context, (AttributeSet)arrn, R.styleable.AppCompatTextHelper, n, 0);
        int n2 = tintTypedArray.getResourceId(R.styleable.AppCompatTextHelper_android_textAppearance, -1);
        if (tintTypedArray.hasValue(R.styleable.AppCompatTextHelper_android_drawableLeft)) {
            this.mDrawableLeftTint = AppCompatTextHelper.createTintInfo(context, appCompatDrawableManager, tintTypedArray.getResourceId(R.styleable.AppCompatTextHelper_android_drawableLeft, 0));
        }
        if (tintTypedArray.hasValue(R.styleable.AppCompatTextHelper_android_drawableTop)) {
            this.mDrawableTopTint = AppCompatTextHelper.createTintInfo(context, appCompatDrawableManager, tintTypedArray.getResourceId(R.styleable.AppCompatTextHelper_android_drawableTop, 0));
        }
        if (tintTypedArray.hasValue(R.styleable.AppCompatTextHelper_android_drawableRight)) {
            this.mDrawableRightTint = AppCompatTextHelper.createTintInfo(context, appCompatDrawableManager, tintTypedArray.getResourceId(R.styleable.AppCompatTextHelper_android_drawableRight, 0));
        }
        if (tintTypedArray.hasValue(R.styleable.AppCompatTextHelper_android_drawableBottom)) {
            this.mDrawableBottomTint = AppCompatTextHelper.createTintInfo(context, appCompatDrawableManager, tintTypedArray.getResourceId(R.styleable.AppCompatTextHelper_android_drawableBottom, 0));
        }
        tintTypedArray.recycle();
        boolean bl3 = this.mView.getTransformationMethod() instanceof PasswordTransformationMethod;
        tintTypedArray = null;
        Object object = null;
        ColorStateList colorStateList = null;
        if (n2 != -1) {
            object = TintTypedArray.obtainStyledAttributes(context, n2, R.styleable.TextAppearance);
            if (!bl3 && object.hasValue(R.styleable.TextAppearance_textAllCaps)) {
                bl2 = object.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
                bl = true;
            } else {
                bl2 = bl = false;
            }
            this.updateTypefaceAndStyle(context, (TintTypedArray)object);
            if (Build.VERSION.SDK_INT < 23) {
                tintTypedArray = object.hasValue(R.styleable.TextAppearance_android_textColor) ? object.getColorStateList(R.styleable.TextAppearance_android_textColor) : null;
                appCompatDrawableManager = object.hasValue(R.styleable.TextAppearance_android_textColorHint) ? object.getColorStateList(R.styleable.TextAppearance_android_textColorHint) : null;
                if (object.hasValue(R.styleable.TextAppearance_android_textColorLink)) {
                    colorStateList = object.getColorStateList(R.styleable.TextAppearance_android_textColorLink);
                }
            } else {
                colorStateList = null;
                appCompatDrawableManager = colorStateList;
            }
            object.recycle();
        } else {
            bl2 = bl = false;
            colorStateList = null;
            appCompatDrawableManager = colorStateList;
            tintTypedArray = object;
        }
        TintTypedArray tintTypedArray2 = TintTypedArray.obtainStyledAttributes(context, (AttributeSet)arrn, R.styleable.TextAppearance, n, 0);
        boolean bl4 = bl;
        boolean bl5 = bl2;
        if (!bl3) {
            bl4 = bl;
            bl5 = bl2;
            if (tintTypedArray2.hasValue(R.styleable.TextAppearance_textAllCaps)) {
                bl5 = tintTypedArray2.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
                bl4 = true;
            }
        }
        TintTypedArray tintTypedArray3 = tintTypedArray;
        ColorStateList colorStateList2 = colorStateList;
        object = appCompatDrawableManager;
        if (Build.VERSION.SDK_INT < 23) {
            if (tintTypedArray2.hasValue(R.styleable.TextAppearance_android_textColor)) {
                tintTypedArray = tintTypedArray2.getColorStateList(R.styleable.TextAppearance_android_textColor);
            }
            if (tintTypedArray2.hasValue(R.styleable.TextAppearance_android_textColorHint)) {
                appCompatDrawableManager = tintTypedArray2.getColorStateList(R.styleable.TextAppearance_android_textColorHint);
            }
            tintTypedArray3 = tintTypedArray;
            colorStateList2 = colorStateList;
            object = appCompatDrawableManager;
            if (tintTypedArray2.hasValue(R.styleable.TextAppearance_android_textColorLink)) {
                colorStateList2 = tintTypedArray2.getColorStateList(R.styleable.TextAppearance_android_textColorLink);
                object = appCompatDrawableManager;
                tintTypedArray3 = tintTypedArray;
            }
        }
        this.updateTypefaceAndStyle(context, tintTypedArray2);
        tintTypedArray2.recycle();
        if (tintTypedArray3 != null) {
            this.mView.setTextColor((ColorStateList)tintTypedArray3);
        }
        if (object != null) {
            this.mView.setHintTextColor((ColorStateList)object);
        }
        if (colorStateList2 != null) {
            this.mView.setLinkTextColor(colorStateList2);
        }
        if (!bl3 && bl4) {
            this.setAllCaps(bl5);
        }
        if (this.mFontTypeface != null) {
            this.mView.setTypeface(this.mFontTypeface, this.mStyle);
        }
        this.mAutoSizeTextHelper.loadFromAttributes((AttributeSet)arrn, n);
        if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE && this.mAutoSizeTextHelper.getAutoSizeTextType() != 0 && (arrn = this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes()).length > 0) {
            if ((float)this.mView.getAutoSizeStepGranularity() != -1.0f) {
                this.mView.setAutoSizeTextTypeUniformWithConfiguration(this.mAutoSizeTextHelper.getAutoSizeMinTextSize(), this.mAutoSizeTextHelper.getAutoSizeMaxTextSize(), this.mAutoSizeTextHelper.getAutoSizeStepGranularity(), 0);
                return;
            }
            this.mView.setAutoSizeTextTypeUniformWithPresetSizes(arrn, 0);
        }
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        if (!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE) {
            this.autoSizeText();
        }
    }

    void onSetTextAppearance(Context context, int n) {
        ColorStateList colorStateList;
        TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(context, n, R.styleable.TextAppearance);
        if (tintTypedArray.hasValue(R.styleable.TextAppearance_textAllCaps)) {
            this.setAllCaps(tintTypedArray.getBoolean(R.styleable.TextAppearance_textAllCaps, false));
        }
        if (Build.VERSION.SDK_INT < 23 && tintTypedArray.hasValue(R.styleable.TextAppearance_android_textColor) && (colorStateList = tintTypedArray.getColorStateList(R.styleable.TextAppearance_android_textColor)) != null) {
            this.mView.setTextColor(colorStateList);
        }
        this.updateTypefaceAndStyle(context, tintTypedArray);
        tintTypedArray.recycle();
        if (this.mFontTypeface != null) {
            this.mView.setTypeface(this.mFontTypeface, this.mStyle);
        }
    }

    void setAllCaps(boolean bl) {
        this.mView.setAllCaps(bl);
    }

    void setAutoSizeTextTypeUniformWithConfiguration(int n, int n2, int n3, int n4) throws IllegalArgumentException {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithConfiguration(n, n2, n3, n4);
    }

    void setAutoSizeTextTypeUniformWithPresetSizes(@NonNull int[] arrn, int n) throws IllegalArgumentException {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithPresetSizes(arrn, n);
    }

    void setAutoSizeTextTypeWithDefaults(int n) {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeWithDefaults(n);
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    void setTextSize(int n, float f) {
        if (!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE && !this.isAutoSizeEnabled()) {
            this.setTextSizeInternal(n, f);
        }
    }

}
