/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.FrameLayout
 */
package com.google.android.gms.common;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import com.google.android.gms.R;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzaj;
import com.google.android.gms.common.internal.zzak;
import com.google.android.gms.dynamic.zzg;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class SignInButton
extends FrameLayout
implements View.OnClickListener {
    public static final int COLOR_AUTO = 2;
    public static final int COLOR_DARK = 0;
    public static final int COLOR_LIGHT = 1;
    public static final int SIZE_ICON_ONLY = 2;
    public static final int SIZE_STANDARD = 0;
    public static final int SIZE_WIDE = 1;
    private int mColor;
    private int mSize;
    private View zzaxs;
    private View.OnClickListener zzaxt = null;

    public SignInButton(Context context) {
        this(context, null);
    }

    public SignInButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SignInButton(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.zzb(context, attributeSet);
        this.setStyle(this.mSize, this.mColor);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzaw(Context context) {
        block3 : {
            if (this.zzaxs != null) {
                this.removeView(this.zzaxs);
            }
            try {
                this.zzaxs = zzaj.zzd(context, this.mSize, this.mColor);
                break block3;
            }
            catch (zzg.zza zza2) {}
            Log.w((String)"SignInButton", (String)"Sign in button not found, using placeholder instead");
            this.zzaxs = SignInButton.zzc(context, this.mSize, this.mColor);
        }
        this.addView(this.zzaxs);
        this.zzaxs.setEnabled(this.isEnabled());
        this.zzaxs.setOnClickListener((View.OnClickListener)this);
    }

    private void zzb(Context context, AttributeSet attributeSet) {
        context = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.SignInButton, 0, 0);
        try {
            this.mSize = context.getInt(R.styleable.SignInButton_buttonSize, 0);
            this.mColor = context.getInt(R.styleable.SignInButton_colorScheme, 2);
            return;
        }
        finally {
            context.recycle();
        }
    }

    private static Button zzc(Context context, int n, int n2) {
        zzak zzak2 = new zzak(context);
        zzak2.zza(context.getResources(), n, n2);
        return zzak2;
    }

    public void onClick(View view) {
        if (this.zzaxt != null && view == this.zzaxs) {
            this.zzaxt.onClick((View)this);
        }
    }

    public void setColorScheme(int n) {
        this.setStyle(this.mSize, n);
    }

    public void setEnabled(boolean bl) {
        super.setEnabled(bl);
        this.zzaxs.setEnabled(bl);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.zzaxt = onClickListener;
        if (this.zzaxs != null) {
            this.zzaxs.setOnClickListener((View.OnClickListener)this);
        }
    }

    @Deprecated
    public void setScopes(Scope[] arrscope) {
        this.setStyle(this.mSize, this.mColor);
    }

    public void setSize(int n) {
        this.setStyle(n, this.mColor);
    }

    public void setStyle(int n, int n2) {
        this.mSize = n;
        this.mColor = n2;
        this.zzaw(this.getContext());
    }

    @Deprecated
    public void setStyle(int n, int n2, Scope[] arrscope) {
        this.setStyle(n, n2);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ButtonSize {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ColorScheme {
    }

}
