/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Typeface
 *  android.os.Bundle
 *  android.text.TextPaint
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 */
package com.facebook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.common.R;
import com.facebook.internal.FragmentWrapper;

public abstract class FacebookButtonBase
extends Button {
    private String analyticsButtonCreatedEventName;
    private String analyticsButtonTappedEventName;
    private View.OnClickListener externalOnClickListener;
    private View.OnClickListener internalOnClickListener;
    private boolean overrideCompoundPadding;
    private int overrideCompoundPaddingLeft;
    private int overrideCompoundPaddingRight;
    private FragmentWrapper parentFragment;

    protected FacebookButtonBase(Context context, AttributeSet attributeSet, int n, int n2, String string, String string2) {
        super(context, attributeSet, 0);
        int n3 = n2;
        if (n2 == 0) {
            n3 = this.getDefaultStyleResource();
        }
        n2 = n3;
        if (n3 == 0) {
            n2 = R.style.com_facebook_button;
        }
        this.configureButton(context, attributeSet, n, n2);
        this.analyticsButtonCreatedEventName = string;
        this.analyticsButtonTappedEventName = string2;
        this.setClickable(true);
        this.setFocusable(true);
    }

    private void logButtonCreated(Context context) {
        AppEventsLogger.newLogger(context).logSdkEvent(this.analyticsButtonCreatedEventName, null, null);
    }

    private void logButtonTapped(Context context) {
        AppEventsLogger.newLogger(context).logSdkEvent(this.analyticsButtonTappedEventName, null, null);
    }

    private void parseBackgroundAttributes(Context context, AttributeSet attributeSet, int n, int n2) {
        if (this.isInEditMode()) {
            return;
        }
        attributeSet = context.getTheme().obtainStyledAttributes(attributeSet, new int[]{16842964}, n, n2);
        try {
            if (attributeSet.hasValue(0)) {
                n = attributeSet.getResourceId(0, 0);
                if (n != 0) {
                    this.setBackgroundResource(n);
                } else {
                    this.setBackgroundColor(attributeSet.getColor(0, 0));
                }
            } else {
                this.setBackgroundColor(ContextCompat.getColor(context, R.color.com_facebook_blue));
            }
            return;
        }
        finally {
            attributeSet.recycle();
        }
    }

    @SuppressLint(value={"ResourceType"})
    private void parseCompoundDrawableAttributes(Context context, AttributeSet attributeSet, int n, int n2) {
        context = context.getTheme().obtainStyledAttributes(attributeSet, new int[]{16843119, 16843117, 16843120, 16843118, 16843121}, n, n2);
        try {
            this.setCompoundDrawablesWithIntrinsicBounds(context.getResourceId(0, 0), context.getResourceId(1, 0), context.getResourceId(2, 0), context.getResourceId(3, 0));
            this.setCompoundDrawablePadding(context.getDimensionPixelSize(4, 0));
            return;
        }
        finally {
            context.recycle();
        }
    }

    private void parseContentAttributes(Context context, AttributeSet attributeSet, int n, int n2) {
        context = context.getTheme().obtainStyledAttributes(attributeSet, new int[]{16842966, 16842967, 16842968, 16842969}, n, n2);
        try {
            this.setPadding(context.getDimensionPixelSize(0, 0), context.getDimensionPixelSize(1, 0), context.getDimensionPixelSize(2, 0), context.getDimensionPixelSize(3, 0));
            return;
        }
        finally {
            context.recycle();
        }
    }

    private void parseTextAttributes(Context context, AttributeSet attributeSet, int n, int n2) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, new int[]{16842904}, n, n2);
        this.setTextColor(typedArray.getColorStateList(0));
        typedArray = context.getTheme().obtainStyledAttributes(attributeSet, new int[]{16842927}, n, n2);
        this.setGravity(typedArray.getInt(0, 17));
        context = context.getTheme().obtainStyledAttributes(attributeSet, new int[]{16842901, 16842903, 16843087}, n, n2);
        try {
            this.setTextSize(0, (float)context.getDimensionPixelSize(0, 0));
            this.setTypeface(Typeface.defaultFromStyle((int)context.getInt(1, 1)));
            this.setText((CharSequence)context.getString(2));
            return;
        }
        finally {
            typedArray.recycle();
        }
    }

    private void setupOnClickListener() {
        super.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                FacebookButtonBase.this.logButtonTapped(FacebookButtonBase.this.getContext());
                if (FacebookButtonBase.this.internalOnClickListener != null) {
                    FacebookButtonBase.this.internalOnClickListener.onClick(view);
                    return;
                }
                if (FacebookButtonBase.this.externalOnClickListener != null) {
                    FacebookButtonBase.this.externalOnClickListener.onClick(view);
                }
            }
        });
    }

    protected void callExternalOnClickListener(View view) {
        if (this.externalOnClickListener != null) {
            this.externalOnClickListener.onClick(view);
        }
    }

    protected void configureButton(Context context, AttributeSet attributeSet, int n, int n2) {
        this.parseBackgroundAttributes(context, attributeSet, n, n2);
        this.parseCompoundDrawableAttributes(context, attributeSet, n, n2);
        this.parseContentAttributes(context, attributeSet, n, n2);
        this.parseTextAttributes(context, attributeSet, n, n2);
        this.setupOnClickListener();
    }

    protected Activity getActivity() {
        boolean bl;
        Context context = this.getContext();
        while (!(bl = context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper)context).getBaseContext();
        }
        if (bl) {
            return (Activity)context;
        }
        throw new FacebookException("Unable to get Activity.");
    }

    public int getCompoundPaddingLeft() {
        if (this.overrideCompoundPadding) {
            return this.overrideCompoundPaddingLeft;
        }
        return super.getCompoundPaddingLeft();
    }

    public int getCompoundPaddingRight() {
        if (this.overrideCompoundPadding) {
            return this.overrideCompoundPaddingRight;
        }
        return super.getCompoundPaddingRight();
    }

    protected abstract int getDefaultRequestCode();

    protected int getDefaultStyleResource() {
        return 0;
    }

    public android.support.v4.app.Fragment getFragment() {
        if (this.parentFragment != null) {
            return this.parentFragment.getSupportFragment();
        }
        return null;
    }

    public Fragment getNativeFragment() {
        if (this.parentFragment != null) {
            return this.parentFragment.getNativeFragment();
        }
        return null;
    }

    public int getRequestCode() {
        return this.getDefaultRequestCode();
    }

    protected int measureTextWidth(String string) {
        return (int)Math.ceil(this.getPaint().measureText(string));
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.isInEditMode()) {
            this.logButtonCreated(this.getContext());
        }
    }

    protected void onDraw(Canvas canvas) {
        int n = (this.getGravity() & 1) != 0 ? 1 : 0;
        if (n != 0) {
            n = this.getCompoundPaddingLeft();
            int n2 = this.getCompoundPaddingRight();
            int n3 = this.getCompoundDrawablePadding();
            n3 = Math.min((this.getWidth() - (n3 + n) - n2 - this.measureTextWidth(this.getText().toString())) / 2, (n - this.getPaddingLeft()) / 2);
            this.overrideCompoundPaddingLeft = n - n3;
            this.overrideCompoundPaddingRight = n2 + n3;
            this.overrideCompoundPadding = true;
        }
        super.onDraw(canvas);
        this.overrideCompoundPadding = false;
    }

    public void setFragment(Fragment fragment) {
        this.parentFragment = new FragmentWrapper(fragment);
    }

    public void setFragment(android.support.v4.app.Fragment fragment) {
        this.parentFragment = new FragmentWrapper(fragment);
    }

    protected void setInternalOnClickListener(View.OnClickListener onClickListener) {
        this.internalOnClickListener = onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.externalOnClickListener = onClickListener;
    }

}
