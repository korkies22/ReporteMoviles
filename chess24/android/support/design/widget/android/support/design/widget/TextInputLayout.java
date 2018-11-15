/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.Typeface
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.graphics.drawable.DrawableContainer
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.text.Editable
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.text.method.PasswordTransformationMethod
 *  android.text.method.TransformationMethod
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewPropertyAnimator
 *  android.view.ViewStructure
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.animation.AccelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.widget.EditText
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 */
package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.annotation.VisibleForTesting;
import android.support.design.R;
import android.support.design.widget.AnimationUtils;
import android.support.design.widget.CheckableImageButton;
import android.support.design.widget.CollapsingTextHelper;
import android.support.design.widget.DrawableUtils;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.ThemeUtils;
import android.support.design.widget.ViewUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.Space;
import android.support.v4.widget.TextViewCompat;
import android.support.v4.widget.ViewGroupUtils;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.WithHint;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

public class TextInputLayout
extends LinearLayout
implements WithHint {
    private static final int ANIMATION_DURATION = 200;
    private static final int INVALID_MAX_LENGTH = -1;
    private static final String LOG_TAG = "TextInputLayout";
    private ValueAnimator mAnimator;
    final CollapsingTextHelper mCollapsingTextHelper = new CollapsingTextHelper((View)this);
    boolean mCounterEnabled;
    private int mCounterMaxLength;
    private int mCounterOverflowTextAppearance;
    private boolean mCounterOverflowed;
    private int mCounterTextAppearance;
    private TextView mCounterView;
    private ColorStateList mDefaultTextColor;
    EditText mEditText;
    private CharSequence mError;
    private boolean mErrorEnabled;
    private boolean mErrorShown;
    private int mErrorTextAppearance;
    TextView mErrorView;
    private ColorStateList mFocusedTextColor;
    private boolean mHasPasswordToggleTintList;
    private boolean mHasPasswordToggleTintMode;
    private boolean mHasReconstructedEditTextBackground;
    private CharSequence mHint;
    private boolean mHintAnimationEnabled;
    private boolean mHintEnabled;
    private boolean mHintExpanded;
    private boolean mInDrawableStateChanged;
    private LinearLayout mIndicatorArea;
    private int mIndicatorsAdded;
    private final FrameLayout mInputFrame;
    private Drawable mOriginalEditTextEndDrawable;
    private CharSequence mOriginalHint;
    private CharSequence mPasswordToggleContentDesc;
    private Drawable mPasswordToggleDrawable;
    private Drawable mPasswordToggleDummyDrawable;
    private boolean mPasswordToggleEnabled;
    private ColorStateList mPasswordToggleTintList;
    private PorterDuff.Mode mPasswordToggleTintMode;
    private CheckableImageButton mPasswordToggleView;
    private boolean mPasswordToggledVisible;
    private boolean mRestoringSavedState;
    private Paint mTmpPaint;
    private final Rect mTmpRect = new Rect();
    private Typeface mTypeface;

    public TextInputLayout(Context context) {
        this(context, null);
    }

    public TextInputLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TextInputLayout(Context object, AttributeSet attributeSet, int n) {
        super((Context)object, attributeSet);
        ThemeUtils.checkAppCompatTheme((Context)object);
        this.setOrientation(1);
        this.setWillNotDraw(false);
        this.setAddStatesFromChildren(true);
        this.mInputFrame = new FrameLayout((Context)object);
        this.mInputFrame.setAddStatesFromChildren(true);
        this.addView((View)this.mInputFrame);
        this.mCollapsingTextHelper.setTextSizeInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        this.mCollapsingTextHelper.setPositionInterpolator((Interpolator)new AccelerateInterpolator());
        this.mCollapsingTextHelper.setCollapsedTextGravity(8388659);
        object = TintTypedArray.obtainStyledAttributes((Context)object, attributeSet, R.styleable.TextInputLayout, n, R.style.Widget_Design_TextInputLayout);
        this.mHintEnabled = object.getBoolean(R.styleable.TextInputLayout_hintEnabled, true);
        this.setHint(object.getText(R.styleable.TextInputLayout_android_hint));
        this.mHintAnimationEnabled = object.getBoolean(R.styleable.TextInputLayout_hintAnimationEnabled, true);
        if (object.hasValue(R.styleable.TextInputLayout_android_textColorHint)) {
            attributeSet = object.getColorStateList(R.styleable.TextInputLayout_android_textColorHint);
            this.mFocusedTextColor = attributeSet;
            this.mDefaultTextColor = attributeSet;
        }
        if (object.getResourceId(R.styleable.TextInputLayout_hintTextAppearance, -1) != -1) {
            this.setHintTextAppearance(object.getResourceId(R.styleable.TextInputLayout_hintTextAppearance, 0));
        }
        this.mErrorTextAppearance = object.getResourceId(R.styleable.TextInputLayout_errorTextAppearance, 0);
        boolean bl = object.getBoolean(R.styleable.TextInputLayout_errorEnabled, false);
        boolean bl2 = object.getBoolean(R.styleable.TextInputLayout_counterEnabled, false);
        this.setCounterMaxLength(object.getInt(R.styleable.TextInputLayout_counterMaxLength, -1));
        this.mCounterTextAppearance = object.getResourceId(R.styleable.TextInputLayout_counterTextAppearance, 0);
        this.mCounterOverflowTextAppearance = object.getResourceId(R.styleable.TextInputLayout_counterOverflowTextAppearance, 0);
        this.mPasswordToggleEnabled = object.getBoolean(R.styleable.TextInputLayout_passwordToggleEnabled, false);
        this.mPasswordToggleDrawable = object.getDrawable(R.styleable.TextInputLayout_passwordToggleDrawable);
        this.mPasswordToggleContentDesc = object.getText(R.styleable.TextInputLayout_passwordToggleContentDescription);
        if (object.hasValue(R.styleable.TextInputLayout_passwordToggleTint)) {
            this.mHasPasswordToggleTintList = true;
            this.mPasswordToggleTintList = object.getColorStateList(R.styleable.TextInputLayout_passwordToggleTint);
        }
        if (object.hasValue(R.styleable.TextInputLayout_passwordToggleTintMode)) {
            this.mHasPasswordToggleTintMode = true;
            this.mPasswordToggleTintMode = ViewUtils.parseTintMode(object.getInt(R.styleable.TextInputLayout_passwordToggleTintMode, -1), null);
        }
        object.recycle();
        this.setErrorEnabled(bl);
        this.setCounterEnabled(bl2);
        this.applyPasswordToggleTint();
        if (ViewCompat.getImportantForAccessibility((View)this) == 0) {
            ViewCompat.setImportantForAccessibility((View)this, 1);
        }
        ViewCompat.setAccessibilityDelegate((View)this, new TextInputAccessibilityDelegate());
    }

    private void addIndicator(TextView textView, int n) {
        if (this.mIndicatorArea == null) {
            this.mIndicatorArea = new LinearLayout(this.getContext());
            this.mIndicatorArea.setOrientation(0);
            this.addView((View)this.mIndicatorArea, -1, -2);
            Space space = new Space(this.getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, 0, 1.0f);
            this.mIndicatorArea.addView((View)space, (ViewGroup.LayoutParams)layoutParams);
            if (this.mEditText != null) {
                this.adjustIndicatorPadding();
            }
        }
        this.mIndicatorArea.setVisibility(0);
        this.mIndicatorArea.addView((View)textView, n);
        ++this.mIndicatorsAdded;
    }

    private void adjustIndicatorPadding() {
        ViewCompat.setPaddingRelative((View)this.mIndicatorArea, ViewCompat.getPaddingStart((View)this.mEditText), 0, ViewCompat.getPaddingEnd((View)this.mEditText), this.mEditText.getPaddingBottom());
    }

    private void applyPasswordToggleTint() {
        if (this.mPasswordToggleDrawable != null && (this.mHasPasswordToggleTintList || this.mHasPasswordToggleTintMode)) {
            this.mPasswordToggleDrawable = DrawableCompat.wrap(this.mPasswordToggleDrawable).mutate();
            if (this.mHasPasswordToggleTintList) {
                DrawableCompat.setTintList(this.mPasswordToggleDrawable, this.mPasswordToggleTintList);
            }
            if (this.mHasPasswordToggleTintMode) {
                DrawableCompat.setTintMode(this.mPasswordToggleDrawable, this.mPasswordToggleTintMode);
            }
            if (this.mPasswordToggleView != null && this.mPasswordToggleView.getDrawable() != this.mPasswordToggleDrawable) {
                this.mPasswordToggleView.setImageDrawable(this.mPasswordToggleDrawable);
            }
        }
    }

    private static boolean arrayContains(int[] arrn, int n) {
        int n2 = arrn.length;
        for (int i = 0; i < n2; ++i) {
            if (arrn[i] != n) continue;
            return true;
        }
        return false;
    }

    private void collapseHint(boolean bl) {
        if (this.mAnimator != null && this.mAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        if (bl && this.mHintAnimationEnabled) {
            this.animateToExpansionFraction(1.0f);
        } else {
            this.mCollapsingTextHelper.setExpansionFraction(1.0f);
        }
        this.mHintExpanded = false;
    }

    private void ensureBackgroundDrawableStateWorkaround() {
        int n = Build.VERSION.SDK_INT;
        if (n != 21 && n != 22) {
            return;
        }
        Drawable drawable = this.mEditText.getBackground();
        if (drawable == null) {
            return;
        }
        if (!this.mHasReconstructedEditTextBackground) {
            Drawable drawable2 = drawable.getConstantState().newDrawable();
            if (drawable instanceof DrawableContainer) {
                this.mHasReconstructedEditTextBackground = DrawableUtils.setContainerConstantState((DrawableContainer)drawable, drawable2.getConstantState());
            }
            if (!this.mHasReconstructedEditTextBackground) {
                ViewCompat.setBackground((View)this.mEditText, drawable2);
                this.mHasReconstructedEditTextBackground = true;
            }
        }
    }

    private void expandHint(boolean bl) {
        if (this.mAnimator != null && this.mAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        if (bl && this.mHintAnimationEnabled) {
            this.animateToExpansionFraction(0.0f);
        } else {
            this.mCollapsingTextHelper.setExpansionFraction(0.0f);
        }
        this.mHintExpanded = true;
    }

    private boolean hasPasswordTransformation() {
        if (this.mEditText != null && this.mEditText.getTransformationMethod() instanceof PasswordTransformationMethod) {
            return true;
        }
        return false;
    }

    private void passwordVisibilityToggleRequested(boolean bl) {
        if (this.mPasswordToggleEnabled) {
            int n = this.mEditText.getSelectionEnd();
            if (this.hasPasswordTransformation()) {
                this.mEditText.setTransformationMethod(null);
                this.mPasswordToggledVisible = true;
            } else {
                this.mEditText.setTransformationMethod((TransformationMethod)PasswordTransformationMethod.getInstance());
                this.mPasswordToggledVisible = false;
            }
            this.mPasswordToggleView.setChecked(this.mPasswordToggledVisible);
            if (bl) {
                this.mPasswordToggleView.jumpDrawablesToCurrentState();
            }
            this.mEditText.setSelection(n);
        }
    }

    private static void recursiveSetEnabled(ViewGroup viewGroup, boolean bl) {
        int n = viewGroup.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(bl);
            if (!(view instanceof ViewGroup)) continue;
            TextInputLayout.recursiveSetEnabled((ViewGroup)view, bl);
        }
    }

    private void removeIndicator(TextView textView) {
        if (this.mIndicatorArea != null) {
            int n;
            this.mIndicatorArea.removeView((View)textView);
            this.mIndicatorsAdded = n = this.mIndicatorsAdded - 1;
            if (n == 0) {
                this.mIndicatorArea.setVisibility(8);
            }
        }
    }

    private void setEditText(EditText editText) {
        if (this.mEditText != null) {
            throw new IllegalArgumentException("We already have an EditText, can only have one");
        }
        if (!(editText instanceof TextInputEditText)) {
            Log.i((String)LOG_TAG, (String)"EditText added is not a TextInputEditText. Please switch to using that class instead.");
        }
        this.mEditText = editText;
        if (!this.hasPasswordTransformation()) {
            this.mCollapsingTextHelper.setTypefaces(this.mEditText.getTypeface());
        }
        this.mCollapsingTextHelper.setExpandedTextSize(this.mEditText.getTextSize());
        int n = this.mEditText.getGravity();
        this.mCollapsingTextHelper.setCollapsedTextGravity(48 | n & -113);
        this.mCollapsingTextHelper.setExpandedTextGravity(n);
        this.mEditText.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
                TextInputLayout.this.updateLabelState(TextInputLayout.this.mRestoringSavedState ^ true);
                if (TextInputLayout.this.mCounterEnabled) {
                    TextInputLayout.this.updateCounter(editable.length());
                }
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
        if (this.mDefaultTextColor == null) {
            this.mDefaultTextColor = this.mEditText.getHintTextColors();
        }
        if (this.mHintEnabled && TextUtils.isEmpty((CharSequence)this.mHint)) {
            this.mOriginalHint = this.mEditText.getHint();
            this.setHint(this.mOriginalHint);
            this.mEditText.setHint(null);
        }
        if (this.mCounterView != null) {
            this.updateCounter(this.mEditText.getText().length());
        }
        if (this.mIndicatorArea != null) {
            this.adjustIndicatorPadding();
        }
        this.updatePasswordToggleView();
        this.updateLabelState(false, true);
    }

    private void setError(final @Nullable CharSequence charSequence, boolean bl) {
        this.mError = charSequence;
        if (!this.mErrorEnabled) {
            if (TextUtils.isEmpty((CharSequence)charSequence)) {
                return;
            }
            this.setErrorEnabled(true);
        }
        this.mErrorShown = TextUtils.isEmpty((CharSequence)charSequence) ^ true;
        this.mErrorView.animate().cancel();
        if (this.mErrorShown) {
            this.mErrorView.setText(charSequence);
            this.mErrorView.setVisibility(0);
            if (bl) {
                if (this.mErrorView.getAlpha() == 1.0f) {
                    this.mErrorView.setAlpha(0.0f);
                }
                this.mErrorView.animate().alpha(1.0f).setDuration(200L).setInterpolator((TimeInterpolator)AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                    public void onAnimationStart(Animator animator) {
                        TextInputLayout.this.mErrorView.setVisibility(0);
                    }
                }).start();
            } else {
                this.mErrorView.setAlpha(1.0f);
            }
        } else if (this.mErrorView.getVisibility() == 0) {
            if (bl) {
                this.mErrorView.animate().alpha(0.0f).setDuration(200L).setInterpolator((TimeInterpolator)AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                    public void onAnimationEnd(Animator animator) {
                        TextInputLayout.this.mErrorView.setText(charSequence);
                        TextInputLayout.this.mErrorView.setVisibility(4);
                    }
                }).start();
            } else {
                this.mErrorView.setText(charSequence);
                this.mErrorView.setVisibility(4);
            }
        }
        this.updateEditTextBackground();
        this.updateLabelState(bl);
    }

    private void setHintInternal(CharSequence charSequence) {
        this.mHint = charSequence;
        this.mCollapsingTextHelper.setText(charSequence);
    }

    private boolean shouldShowPasswordIcon() {
        if (this.mPasswordToggleEnabled && (this.hasPasswordTransformation() || this.mPasswordToggledVisible)) {
            return true;
        }
        return false;
    }

    private void updateEditTextBackground() {
        if (this.mEditText == null) {
            return;
        }
        Drawable drawable = this.mEditText.getBackground();
        if (drawable == null) {
            return;
        }
        this.ensureBackgroundDrawableStateWorkaround();
        Drawable drawable2 = drawable;
        if (android.support.v7.widget.DrawableUtils.canSafelyMutateDrawable(drawable)) {
            drawable2 = drawable.mutate();
        }
        if (this.mErrorShown && this.mErrorView != null) {
            drawable2.setColorFilter((ColorFilter)AppCompatDrawableManager.getPorterDuffColorFilter(this.mErrorView.getCurrentTextColor(), PorterDuff.Mode.SRC_IN));
            return;
        }
        if (this.mCounterOverflowed && this.mCounterView != null) {
            drawable2.setColorFilter((ColorFilter)AppCompatDrawableManager.getPorterDuffColorFilter(this.mCounterView.getCurrentTextColor(), PorterDuff.Mode.SRC_IN));
            return;
        }
        DrawableCompat.clearColorFilter(drawable2);
        this.mEditText.refreshDrawableState();
    }

    private void updateInputLayoutMargins() {
        int n;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)this.mInputFrame.getLayoutParams();
        if (this.mHintEnabled) {
            if (this.mTmpPaint == null) {
                this.mTmpPaint = new Paint();
            }
            this.mTmpPaint.setTypeface(this.mCollapsingTextHelper.getCollapsedTypeface());
            this.mTmpPaint.setTextSize(this.mCollapsingTextHelper.getCollapsedTextSize());
            n = (int)(- this.mTmpPaint.ascent());
        } else {
            n = 0;
        }
        if (n != layoutParams.topMargin) {
            layoutParams.topMargin = n;
            this.mInputFrame.requestLayout();
        }
    }

    private void updatePasswordToggleView() {
        Drawable[] arrdrawable;
        if (this.mEditText == null) {
            return;
        }
        if (this.shouldShowPasswordIcon()) {
            if (this.mPasswordToggleView == null) {
                this.mPasswordToggleView = (CheckableImageButton)LayoutInflater.from((Context)this.getContext()).inflate(R.layout.design_text_input_password_icon, (ViewGroup)this.mInputFrame, false);
                this.mPasswordToggleView.setImageDrawable(this.mPasswordToggleDrawable);
                this.mPasswordToggleView.setContentDescription(this.mPasswordToggleContentDesc);
                this.mInputFrame.addView((View)this.mPasswordToggleView);
                this.mPasswordToggleView.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View view) {
                        TextInputLayout.this.passwordVisibilityToggleRequested(false);
                    }
                });
            }
            if (this.mEditText != null && ViewCompat.getMinimumHeight((View)this.mEditText) <= 0) {
                this.mEditText.setMinimumHeight(ViewCompat.getMinimumHeight((View)this.mPasswordToggleView));
            }
            this.mPasswordToggleView.setVisibility(0);
            this.mPasswordToggleView.setChecked(this.mPasswordToggledVisible);
            if (this.mPasswordToggleDummyDrawable == null) {
                this.mPasswordToggleDummyDrawable = new ColorDrawable();
            }
            this.mPasswordToggleDummyDrawable.setBounds(0, 0, this.mPasswordToggleView.getMeasuredWidth(), 1);
            Drawable[] arrdrawable2 = TextViewCompat.getCompoundDrawablesRelative((TextView)this.mEditText);
            if (arrdrawable2[2] != this.mPasswordToggleDummyDrawable) {
                this.mOriginalEditTextEndDrawable = arrdrawable2[2];
            }
            TextViewCompat.setCompoundDrawablesRelative((TextView)this.mEditText, arrdrawable2[0], arrdrawable2[1], this.mPasswordToggleDummyDrawable, arrdrawable2[3]);
            this.mPasswordToggleView.setPadding(this.mEditText.getPaddingLeft(), this.mEditText.getPaddingTop(), this.mEditText.getPaddingRight(), this.mEditText.getPaddingBottom());
            return;
        }
        if (this.mPasswordToggleView != null && this.mPasswordToggleView.getVisibility() == 0) {
            this.mPasswordToggleView.setVisibility(8);
        }
        if (this.mPasswordToggleDummyDrawable != null && (arrdrawable = TextViewCompat.getCompoundDrawablesRelative((TextView)this.mEditText))[2] == this.mPasswordToggleDummyDrawable) {
            TextViewCompat.setCompoundDrawablesRelative((TextView)this.mEditText, arrdrawable[0], arrdrawable[1], this.mOriginalEditTextEndDrawable, arrdrawable[3]);
            this.mPasswordToggleDummyDrawable = null;
        }
    }

    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        if (view instanceof EditText) {
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(layoutParams);
            layoutParams2.gravity = 16 | layoutParams2.gravity & -113;
            this.mInputFrame.addView(view, (ViewGroup.LayoutParams)layoutParams2);
            this.mInputFrame.setLayoutParams(layoutParams);
            this.updateInputLayoutMargins();
            this.setEditText((EditText)view);
            return;
        }
        super.addView(view, n, layoutParams);
    }

    @VisibleForTesting
    void animateToExpansionFraction(float f) {
        if (this.mCollapsingTextHelper.getExpansionFraction() == f) {
            return;
        }
        if (this.mAnimator == null) {
            this.mAnimator = new ValueAnimator();
            this.mAnimator.setInterpolator((TimeInterpolator)AnimationUtils.LINEAR_INTERPOLATOR);
            this.mAnimator.setDuration(200L);
            this.mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    TextInputLayout.this.mCollapsingTextHelper.setExpansionFraction(((Float)valueAnimator.getAnimatedValue()).floatValue());
                }
            });
        }
        this.mAnimator.setFloatValues(new float[]{this.mCollapsingTextHelper.getExpansionFraction(), f});
        this.mAnimator.start();
    }

    public void dispatchProvideAutofillStructure(ViewStructure viewStructure, int n) {
        if (this.mOriginalHint != null && this.mEditText != null) {
            CharSequence charSequence = this.mEditText.getHint();
            this.mEditText.setHint(this.mOriginalHint);
            try {
                super.dispatchProvideAutofillStructure(viewStructure, n);
                return;
            }
            finally {
                this.mEditText.setHint(charSequence);
            }
        }
        super.dispatchProvideAutofillStructure(viewStructure, n);
    }

    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        this.mRestoringSavedState = true;
        super.dispatchRestoreInstanceState(sparseArray);
        this.mRestoringSavedState = false;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mHintEnabled) {
            this.mCollapsingTextHelper.draw(canvas);
        }
    }

    protected void drawableStateChanged() {
        if (this.mInDrawableStateChanged) {
            return;
        }
        boolean bl = true;
        this.mInDrawableStateChanged = true;
        super.drawableStateChanged();
        int[] arrn = this.getDrawableState();
        if (!ViewCompat.isLaidOut((View)this) || !this.isEnabled()) {
            bl = false;
        }
        this.updateLabelState(bl);
        this.updateEditTextBackground();
        boolean bl2 = this.mCollapsingTextHelper != null ? this.mCollapsingTextHelper.setState(arrn) | false : false;
        if (bl2) {
            this.invalidate();
        }
        this.mInDrawableStateChanged = false;
    }

    public int getCounterMaxLength() {
        return this.mCounterMaxLength;
    }

    @Nullable
    public EditText getEditText() {
        return this.mEditText;
    }

    @Nullable
    public CharSequence getError() {
        if (this.mErrorEnabled) {
            return this.mError;
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getHint() {
        if (this.mHintEnabled) {
            return this.mHint;
        }
        return null;
    }

    @Nullable
    public CharSequence getPasswordVisibilityToggleContentDescription() {
        return this.mPasswordToggleContentDesc;
    }

    @Nullable
    public Drawable getPasswordVisibilityToggleDrawable() {
        return this.mPasswordToggleDrawable;
    }

    @NonNull
    public Typeface getTypeface() {
        return this.mTypeface;
    }

    public boolean isCounterEnabled() {
        return this.mCounterEnabled;
    }

    public boolean isErrorEnabled() {
        return this.mErrorEnabled;
    }

    public boolean isHintAnimationEnabled() {
        return this.mHintAnimationEnabled;
    }

    public boolean isHintEnabled() {
        return this.mHintEnabled;
    }

    @VisibleForTesting
    final boolean isHintExpanded() {
        return this.mHintExpanded;
    }

    public boolean isPasswordVisibilityToggleEnabled() {
        return this.mPasswordToggleEnabled;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        if (this.mHintEnabled && this.mEditText != null) {
            Rect rect = this.mTmpRect;
            ViewGroupUtils.getDescendantRect((ViewGroup)this, (View)this.mEditText, rect);
            n = rect.left + this.mEditText.getCompoundPaddingLeft();
            n3 = rect.right - this.mEditText.getCompoundPaddingRight();
            this.mCollapsingTextHelper.setExpandedBounds(n, rect.top + this.mEditText.getCompoundPaddingTop(), n3, rect.bottom - this.mEditText.getCompoundPaddingBottom());
            this.mCollapsingTextHelper.setCollapsedBounds(n, this.getPaddingTop(), n3, n4 - n2 - this.getPaddingBottom());
            this.mCollapsingTextHelper.recalculate();
        }
    }

    protected void onMeasure(int n, int n2) {
        this.updatePasswordToggleView();
        super.onMeasure(n, n2);
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.getSuperState());
        this.setError(parcelable.error);
        if (parcelable.isPasswordToggledVisible) {
            this.passwordVisibilityToggleRequested(true);
        }
        this.requestLayout();
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (this.mErrorShown) {
            savedState.error = this.getError();
        }
        savedState.isPasswordToggledVisible = this.mPasswordToggledVisible;
        return savedState;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void setCounterEnabled(boolean bl) {
        block7 : {
            block6 : {
                if (this.mCounterEnabled == bl) return;
                if (bl) {
                    this.mCounterView = new AppCompatTextView(this.getContext());
                    this.mCounterView.setId(R.id.textinput_counter);
                    if (this.mTypeface != null) {
                        this.mCounterView.setTypeface(this.mTypeface);
                    }
                    this.mCounterView.setMaxLines(1);
                    TextViewCompat.setTextAppearance(this.mCounterView, this.mCounterTextAppearance);
                    break block6;
                }
                this.removeIndicator(this.mCounterView);
                this.mCounterView = null;
                break block7;
                catch (Exception exception) {}
                TextViewCompat.setTextAppearance(this.mCounterView, R.style.TextAppearance_AppCompat_Caption);
                this.mCounterView.setTextColor(ContextCompat.getColor(this.getContext(), R.color.error_color_material));
            }
            this.addIndicator(this.mCounterView, -1);
            if (this.mEditText == null) {
                this.updateCounter(0);
            } else {
                this.updateCounter(this.mEditText.getText().length());
            }
        }
        this.mCounterEnabled = bl;
    }

    public void setCounterMaxLength(int n) {
        if (this.mCounterMaxLength != n) {
            this.mCounterMaxLength = n > 0 ? n : -1;
            if (this.mCounterEnabled) {
                n = this.mEditText == null ? 0 : this.mEditText.getText().length();
                this.updateCounter(n);
            }
        }
    }

    public void setEnabled(boolean bl) {
        TextInputLayout.recursiveSetEnabled((ViewGroup)this, bl);
        super.setEnabled(bl);
    }

    public void setError(@Nullable CharSequence charSequence) {
        boolean bl = ViewCompat.isLaidOut((View)this) && this.isEnabled() && (this.mErrorView == null || !TextUtils.equals((CharSequence)this.mErrorView.getText(), (CharSequence)charSequence));
        this.setError(charSequence, bl);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void setErrorEnabled(boolean bl) {
        block8 : {
            int n;
            block7 : {
                block6 : {
                    if (this.mErrorEnabled == bl) return;
                    if (this.mErrorView != null) {
                        this.mErrorView.animate().cancel();
                    }
                    if (bl) {
                        this.mErrorView = new AppCompatTextView(this.getContext());
                        this.mErrorView.setId(R.id.textinput_error);
                        if (this.mTypeface != null) {
                            this.mErrorView.setTypeface(this.mTypeface);
                        }
                        TextViewCompat.setTextAppearance(this.mErrorView, this.mErrorTextAppearance);
                        if (Build.VERSION.SDK_INT >= 23 && (n = this.mErrorView.getTextColors().getDefaultColor()) == -65281) break block6;
                        n = 0;
                        break block7;
                    }
                    this.mErrorShown = false;
                    this.updateEditTextBackground();
                    this.removeIndicator(this.mErrorView);
                    this.mErrorView = null;
                    break block8;
                    catch (Exception exception) {}
                }
                n = 1;
            }
            if (n != 0) {
                TextViewCompat.setTextAppearance(this.mErrorView, R.style.TextAppearance_AppCompat_Caption);
                this.mErrorView.setTextColor(ContextCompat.getColor(this.getContext(), R.color.error_color_material));
            }
            this.mErrorView.setVisibility(4);
            ViewCompat.setAccessibilityLiveRegion((View)this.mErrorView, 1);
            this.addIndicator(this.mErrorView, 0);
        }
        this.mErrorEnabled = bl;
    }

    public void setErrorTextAppearance(@StyleRes int n) {
        this.mErrorTextAppearance = n;
        if (this.mErrorView != null) {
            TextViewCompat.setTextAppearance(this.mErrorView, n);
        }
    }

    public void setHint(@Nullable CharSequence charSequence) {
        if (this.mHintEnabled) {
            this.setHintInternal(charSequence);
            this.sendAccessibilityEvent(2048);
        }
    }

    public void setHintAnimationEnabled(boolean bl) {
        this.mHintAnimationEnabled = bl;
    }

    public void setHintEnabled(boolean bl) {
        if (bl != this.mHintEnabled) {
            this.mHintEnabled = bl;
            CharSequence charSequence = this.mEditText.getHint();
            if (!this.mHintEnabled) {
                if (!TextUtils.isEmpty((CharSequence)this.mHint) && TextUtils.isEmpty((CharSequence)charSequence)) {
                    this.mEditText.setHint(this.mHint);
                }
                this.setHintInternal(null);
            } else if (!TextUtils.isEmpty((CharSequence)charSequence)) {
                if (TextUtils.isEmpty((CharSequence)this.mHint)) {
                    this.setHint(charSequence);
                }
                this.mEditText.setHint(null);
            }
            if (this.mEditText != null) {
                this.updateInputLayoutMargins();
            }
        }
    }

    public void setHintTextAppearance(@StyleRes int n) {
        this.mCollapsingTextHelper.setCollapsedTextAppearance(n);
        this.mFocusedTextColor = this.mCollapsingTextHelper.getCollapsedTextColor();
        if (this.mEditText != null) {
            this.updateLabelState(false);
            this.updateInputLayoutMargins();
        }
    }

    public void setPasswordVisibilityToggleContentDescription(@StringRes int n) {
        CharSequence charSequence = n != 0 ? this.getResources().getText(n) : null;
        this.setPasswordVisibilityToggleContentDescription(charSequence);
    }

    public void setPasswordVisibilityToggleContentDescription(@Nullable CharSequence charSequence) {
        this.mPasswordToggleContentDesc = charSequence;
        if (this.mPasswordToggleView != null) {
            this.mPasswordToggleView.setContentDescription(charSequence);
        }
    }

    public void setPasswordVisibilityToggleDrawable(@DrawableRes int n) {
        Drawable drawable = n != 0 ? AppCompatResources.getDrawable(this.getContext(), n) : null;
        this.setPasswordVisibilityToggleDrawable(drawable);
    }

    public void setPasswordVisibilityToggleDrawable(@Nullable Drawable drawable) {
        this.mPasswordToggleDrawable = drawable;
        if (this.mPasswordToggleView != null) {
            this.mPasswordToggleView.setImageDrawable(drawable);
        }
    }

    public void setPasswordVisibilityToggleEnabled(boolean bl) {
        if (this.mPasswordToggleEnabled != bl) {
            this.mPasswordToggleEnabled = bl;
            if (!bl && this.mPasswordToggledVisible && this.mEditText != null) {
                this.mEditText.setTransformationMethod((TransformationMethod)PasswordTransformationMethod.getInstance());
            }
            this.mPasswordToggledVisible = false;
            this.updatePasswordToggleView();
        }
    }

    public void setPasswordVisibilityToggleTintList(@Nullable ColorStateList colorStateList) {
        this.mPasswordToggleTintList = colorStateList;
        this.mHasPasswordToggleTintList = true;
        this.applyPasswordToggleTint();
    }

    public void setPasswordVisibilityToggleTintMode(@Nullable PorterDuff.Mode mode) {
        this.mPasswordToggleTintMode = mode;
        this.mHasPasswordToggleTintMode = true;
        this.applyPasswordToggleTint();
    }

    public void setTypeface(@Nullable Typeface typeface) {
        if (this.mTypeface != null && !this.mTypeface.equals((Object)typeface) || this.mTypeface == null && typeface != null) {
            this.mTypeface = typeface;
            this.mCollapsingTextHelper.setTypefaces(typeface);
            if (this.mCounterView != null) {
                this.mCounterView.setTypeface(typeface);
            }
            if (this.mErrorView != null) {
                this.mErrorView.setTypeface(typeface);
            }
        }
    }

    void updateCounter(int n) {
        boolean bl = this.mCounterOverflowed;
        if (this.mCounterMaxLength == -1) {
            this.mCounterView.setText((CharSequence)String.valueOf(n));
            this.mCounterOverflowed = false;
        } else {
            boolean bl2 = n > this.mCounterMaxLength;
            this.mCounterOverflowed = bl2;
            if (bl != this.mCounterOverflowed) {
                TextView textView = this.mCounterView;
                int n2 = this.mCounterOverflowed ? this.mCounterOverflowTextAppearance : this.mCounterTextAppearance;
                TextViewCompat.setTextAppearance(textView, n2);
            }
            this.mCounterView.setText((CharSequence)this.getContext().getString(R.string.character_counter_pattern, new Object[]{n, this.mCounterMaxLength}));
        }
        if (this.mEditText != null && bl != this.mCounterOverflowed) {
            this.updateLabelState(false);
            this.updateEditTextBackground();
        }
    }

    void updateLabelState(boolean bl) {
        this.updateLabelState(bl, false);
    }

    void updateLabelState(boolean bl, boolean bl2) {
        boolean bl3 = this.isEnabled();
        boolean bl4 = this.mEditText != null && !TextUtils.isEmpty((CharSequence)this.mEditText.getText());
        boolean bl5 = TextInputLayout.arrayContains(this.getDrawableState(), 16842908);
        boolean bl6 = TextUtils.isEmpty((CharSequence)this.getError());
        if (this.mDefaultTextColor != null) {
            this.mCollapsingTextHelper.setExpandedTextColor(this.mDefaultTextColor);
        }
        if (bl3 && this.mCounterOverflowed && this.mCounterView != null) {
            this.mCollapsingTextHelper.setCollapsedTextColor(this.mCounterView.getTextColors());
        } else if (bl3 && bl5 && this.mFocusedTextColor != null) {
            this.mCollapsingTextHelper.setCollapsedTextColor(this.mFocusedTextColor);
        } else if (this.mDefaultTextColor != null) {
            this.mCollapsingTextHelper.setCollapsedTextColor(this.mDefaultTextColor);
        }
        if (!(bl4 || this.isEnabled() && (bl5 || true ^ bl6))) {
            if (bl2 || !this.mHintExpanded) {
                this.expandHint(bl);
                return;
            }
        } else if (bl2 || this.mHintExpanded) {
            this.collapseHint(bl);
        }
    }

    static class SavedState
    extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        CharSequence error;
        boolean isPasswordToggledVisible;

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.error = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            int n = parcel.readInt();
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            this.isPasswordToggledVisible = bl;
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("TextInputLayout.SavedState{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(" error=");
            stringBuilder.append((Object)this.error);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            RuntimeException runtimeException;
            super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
            throw runtimeException;
        }

    }

    private class TextInputAccessibilityDelegate
    extends AccessibilityDelegateCompat {
        TextInputAccessibilityDelegate() {
        }

        @Override
        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName((CharSequence)TextInputLayout.class.getSimpleName());
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(View object, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo((View)object, accessibilityNodeInfoCompat);
            accessibilityNodeInfoCompat.setClassName(TextInputLayout.class.getSimpleName());
            object = TextInputLayout.this.mCollapsingTextHelper.getText();
            if (!TextUtils.isEmpty((CharSequence)object)) {
                accessibilityNodeInfoCompat.setText((CharSequence)object);
            }
            if (TextInputLayout.this.mEditText != null) {
                accessibilityNodeInfoCompat.setLabelFor((View)TextInputLayout.this.mEditText);
            }
            object = TextInputLayout.this.mErrorView != null ? TextInputLayout.this.mErrorView.getText() : null;
            if (!TextUtils.isEmpty((CharSequence)object)) {
                accessibilityNodeInfoCompat.setContentInvalid(true);
                accessibilityNodeInfoCompat.setError((CharSequence)object);
            }
        }

        @Override
        public void onPopulateAccessibilityEvent(View object, AccessibilityEvent accessibilityEvent) {
            super.onPopulateAccessibilityEvent((View)object, accessibilityEvent);
            object = TextInputLayout.this.mCollapsingTextHelper.getText();
            if (!TextUtils.isEmpty((CharSequence)object)) {
                accessibilityEvent.getText().add(object);
            }
        }
    }

}
