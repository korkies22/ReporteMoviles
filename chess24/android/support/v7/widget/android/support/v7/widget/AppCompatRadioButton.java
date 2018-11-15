/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.widget.CompoundButton
 *  android.widget.RadioButton
 *  android.widget.TextView
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.widget.TintableCompoundButton;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatCompoundButtonHelper;
import android.support.v7.widget.AppCompatTextHelper;
import android.support.v7.widget.TintContextWrapper;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

public class AppCompatRadioButton
extends RadioButton
implements TintableCompoundButton {
    private final AppCompatCompoundButtonHelper mCompoundButtonHelper = new AppCompatCompoundButtonHelper((CompoundButton)this);
    private final AppCompatTextHelper mTextHelper;

    public AppCompatRadioButton(Context context) {
        this(context, null);
    }

    public AppCompatRadioButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.radioButtonStyle);
    }

    public AppCompatRadioButton(Context context, AttributeSet attributeSet, int n) {
        super(TintContextWrapper.wrap(context), attributeSet, n);
        this.mCompoundButtonHelper.loadFromAttributes(attributeSet, n);
        this.mTextHelper = new AppCompatTextHelper((TextView)this);
        this.mTextHelper.loadFromAttributes(attributeSet, n);
    }

    public int getCompoundPaddingLeft() {
        int n;
        int n2 = n = super.getCompoundPaddingLeft();
        if (this.mCompoundButtonHelper != null) {
            n2 = this.mCompoundButtonHelper.getCompoundPaddingLeft(n);
        }
        return n2;
    }

    @Nullable
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public ColorStateList getSupportButtonTintList() {
        if (this.mCompoundButtonHelper != null) {
            return this.mCompoundButtonHelper.getSupportButtonTintList();
        }
        return null;
    }

    @Nullable
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public PorterDuff.Mode getSupportButtonTintMode() {
        if (this.mCompoundButtonHelper != null) {
            return this.mCompoundButtonHelper.getSupportButtonTintMode();
        }
        return null;
    }

    public void setButtonDrawable(@DrawableRes int n) {
        this.setButtonDrawable(AppCompatResources.getDrawable(this.getContext(), n));
    }

    public void setButtonDrawable(Drawable drawable) {
        super.setButtonDrawable(drawable);
        if (this.mCompoundButtonHelper != null) {
            this.mCompoundButtonHelper.onSetButtonDrawable();
        }
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public void setSupportButtonTintList(@Nullable ColorStateList colorStateList) {
        if (this.mCompoundButtonHelper != null) {
            this.mCompoundButtonHelper.setSupportButtonTintList(colorStateList);
        }
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public void setSupportButtonTintMode(@Nullable PorterDuff.Mode mode) {
        if (this.mCompoundButtonHelper != null) {
            this.mCompoundButtonHelper.setSupportButtonTintMode(mode);
        }
    }
}
