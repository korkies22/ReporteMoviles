/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnClickListener
 *  android.content.DialogInterface$OnDismissListener
 *  android.content.DialogInterface$OnKeyListener
 *  android.content.DialogInterface$OnMultiChoiceClickListener
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.database.Cursor
 *  android.graphics.drawable.Drawable
 *  android.util.TypedValue
 *  android.view.ContextThemeWrapper
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.ListAdapter
 */
package android.support.v7.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertController;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

public static class AlertDialog.Builder {
    private final AlertController.AlertParams P;
    private final int mTheme;

    public AlertDialog.Builder(@NonNull Context context) {
        this(context, AlertDialog.resolveDialogTheme(context, 0));
    }

    public AlertDialog.Builder(@NonNull Context context, @StyleRes int n) {
        this.P = new AlertController.AlertParams((Context)new ContextThemeWrapper(context, AlertDialog.resolveDialogTheme(context, n)));
        this.mTheme = n;
    }

    public AlertDialog create() {
        AlertDialog alertDialog = new AlertDialog(this.P.mContext, this.mTheme);
        this.P.apply(alertDialog.mAlert);
        alertDialog.setCancelable(this.P.mCancelable);
        if (this.P.mCancelable) {
            alertDialog.setCanceledOnTouchOutside(true);
        }
        alertDialog.setOnCancelListener(this.P.mOnCancelListener);
        alertDialog.setOnDismissListener(this.P.mOnDismissListener);
        if (this.P.mOnKeyListener != null) {
            alertDialog.setOnKeyListener(this.P.mOnKeyListener);
        }
        return alertDialog;
    }

    @NonNull
    public Context getContext() {
        return this.P.mContext;
    }

    public AlertDialog.Builder setAdapter(ListAdapter listAdapter, DialogInterface.OnClickListener onClickListener) {
        this.P.mAdapter = listAdapter;
        this.P.mOnClickListener = onClickListener;
        return this;
    }

    public AlertDialog.Builder setCancelable(boolean bl) {
        this.P.mCancelable = bl;
        return this;
    }

    public AlertDialog.Builder setCursor(Cursor cursor, DialogInterface.OnClickListener onClickListener, String string) {
        this.P.mCursor = cursor;
        this.P.mLabelColumn = string;
        this.P.mOnClickListener = onClickListener;
        return this;
    }

    public AlertDialog.Builder setCustomTitle(@Nullable View view) {
        this.P.mCustomTitleView = view;
        return this;
    }

    public AlertDialog.Builder setIcon(@DrawableRes int n) {
        this.P.mIconId = n;
        return this;
    }

    public AlertDialog.Builder setIcon(@Nullable Drawable drawable) {
        this.P.mIcon = drawable;
        return this;
    }

    public AlertDialog.Builder setIconAttribute(@AttrRes int n) {
        TypedValue typedValue = new TypedValue();
        this.P.mContext.getTheme().resolveAttribute(n, typedValue, true);
        this.P.mIconId = typedValue.resourceId;
        return this;
    }

    @Deprecated
    public AlertDialog.Builder setInverseBackgroundForced(boolean bl) {
        this.P.mForceInverseBackground = bl;
        return this;
    }

    public AlertDialog.Builder setItems(@ArrayRes int n, DialogInterface.OnClickListener onClickListener) {
        this.P.mItems = this.P.mContext.getResources().getTextArray(n);
        this.P.mOnClickListener = onClickListener;
        return this;
    }

    public AlertDialog.Builder setItems(CharSequence[] arrcharSequence, DialogInterface.OnClickListener onClickListener) {
        this.P.mItems = arrcharSequence;
        this.P.mOnClickListener = onClickListener;
        return this;
    }

    public AlertDialog.Builder setMessage(@StringRes int n) {
        this.P.mMessage = this.P.mContext.getText(n);
        return this;
    }

    public AlertDialog.Builder setMessage(@Nullable CharSequence charSequence) {
        this.P.mMessage = charSequence;
        return this;
    }

    public AlertDialog.Builder setMultiChoiceItems(@ArrayRes int n, boolean[] arrbl, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
        this.P.mItems = this.P.mContext.getResources().getTextArray(n);
        this.P.mOnCheckboxClickListener = onMultiChoiceClickListener;
        this.P.mCheckedItems = arrbl;
        this.P.mIsMultiChoice = true;
        return this;
    }

    public AlertDialog.Builder setMultiChoiceItems(Cursor cursor, String string, String string2, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
        this.P.mCursor = cursor;
        this.P.mOnCheckboxClickListener = onMultiChoiceClickListener;
        this.P.mIsCheckedColumn = string;
        this.P.mLabelColumn = string2;
        this.P.mIsMultiChoice = true;
        return this;
    }

    public AlertDialog.Builder setMultiChoiceItems(CharSequence[] arrcharSequence, boolean[] arrbl, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
        this.P.mItems = arrcharSequence;
        this.P.mOnCheckboxClickListener = onMultiChoiceClickListener;
        this.P.mCheckedItems = arrbl;
        this.P.mIsMultiChoice = true;
        return this;
    }

    public AlertDialog.Builder setNegativeButton(@StringRes int n, DialogInterface.OnClickListener onClickListener) {
        this.P.mNegativeButtonText = this.P.mContext.getText(n);
        this.P.mNegativeButtonListener = onClickListener;
        return this;
    }

    public AlertDialog.Builder setNegativeButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
        this.P.mNegativeButtonText = charSequence;
        this.P.mNegativeButtonListener = onClickListener;
        return this;
    }

    public AlertDialog.Builder setNegativeButtonIcon(Drawable drawable) {
        this.P.mNegativeButtonIcon = drawable;
        return this;
    }

    public AlertDialog.Builder setNeutralButton(@StringRes int n, DialogInterface.OnClickListener onClickListener) {
        this.P.mNeutralButtonText = this.P.mContext.getText(n);
        this.P.mNeutralButtonListener = onClickListener;
        return this;
    }

    public AlertDialog.Builder setNeutralButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
        this.P.mNeutralButtonText = charSequence;
        this.P.mNeutralButtonListener = onClickListener;
        return this;
    }

    public AlertDialog.Builder setNeutralButtonIcon(Drawable drawable) {
        this.P.mNeutralButtonIcon = drawable;
        return this;
    }

    public AlertDialog.Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        this.P.mOnCancelListener = onCancelListener;
        return this;
    }

    public AlertDialog.Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.P.mOnDismissListener = onDismissListener;
        return this;
    }

    public AlertDialog.Builder setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.P.mOnItemSelectedListener = onItemSelectedListener;
        return this;
    }

    public AlertDialog.Builder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        this.P.mOnKeyListener = onKeyListener;
        return this;
    }

    public AlertDialog.Builder setPositiveButton(@StringRes int n, DialogInterface.OnClickListener onClickListener) {
        this.P.mPositiveButtonText = this.P.mContext.getText(n);
        this.P.mPositiveButtonListener = onClickListener;
        return this;
    }

    public AlertDialog.Builder setPositiveButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
        this.P.mPositiveButtonText = charSequence;
        this.P.mPositiveButtonListener = onClickListener;
        return this;
    }

    public AlertDialog.Builder setPositiveButtonIcon(Drawable drawable) {
        this.P.mPositiveButtonIcon = drawable;
        return this;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public AlertDialog.Builder setRecycleOnMeasureEnabled(boolean bl) {
        this.P.mRecycleOnMeasure = bl;
        return this;
    }

    public AlertDialog.Builder setSingleChoiceItems(@ArrayRes int n, int n2, DialogInterface.OnClickListener onClickListener) {
        this.P.mItems = this.P.mContext.getResources().getTextArray(n);
        this.P.mOnClickListener = onClickListener;
        this.P.mCheckedItem = n2;
        this.P.mIsSingleChoice = true;
        return this;
    }

    public AlertDialog.Builder setSingleChoiceItems(Cursor cursor, int n, String string, DialogInterface.OnClickListener onClickListener) {
        this.P.mCursor = cursor;
        this.P.mOnClickListener = onClickListener;
        this.P.mCheckedItem = n;
        this.P.mLabelColumn = string;
        this.P.mIsSingleChoice = true;
        return this;
    }

    public AlertDialog.Builder setSingleChoiceItems(ListAdapter listAdapter, int n, DialogInterface.OnClickListener onClickListener) {
        this.P.mAdapter = listAdapter;
        this.P.mOnClickListener = onClickListener;
        this.P.mCheckedItem = n;
        this.P.mIsSingleChoice = true;
        return this;
    }

    public AlertDialog.Builder setSingleChoiceItems(CharSequence[] arrcharSequence, int n, DialogInterface.OnClickListener onClickListener) {
        this.P.mItems = arrcharSequence;
        this.P.mOnClickListener = onClickListener;
        this.P.mCheckedItem = n;
        this.P.mIsSingleChoice = true;
        return this;
    }

    public AlertDialog.Builder setTitle(@StringRes int n) {
        this.P.mTitle = this.P.mContext.getText(n);
        return this;
    }

    public AlertDialog.Builder setTitle(@Nullable CharSequence charSequence) {
        this.P.mTitle = charSequence;
        return this;
    }

    public AlertDialog.Builder setView(int n) {
        this.P.mView = null;
        this.P.mViewLayoutResId = n;
        this.P.mViewSpacingSpecified = false;
        return this;
    }

    public AlertDialog.Builder setView(View view) {
        this.P.mView = view;
        this.P.mViewLayoutResId = 0;
        this.P.mViewSpacingSpecified = false;
        return this;
    }

    @Deprecated
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public AlertDialog.Builder setView(View view, int n, int n2, int n3, int n4) {
        this.P.mView = view;
        this.P.mViewLayoutResId = 0;
        this.P.mViewSpacingSpecified = true;
        this.P.mViewSpacingLeft = n;
        this.P.mViewSpacingTop = n2;
        this.P.mViewSpacingRight = n3;
        this.P.mViewSpacingBottom = n4;
        return this;
    }

    public AlertDialog show() {
        AlertDialog alertDialog = this.create();
        alertDialog.show();
        return alertDialog;
    }
}
