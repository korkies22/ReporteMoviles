/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.text.method.TransformationMethod
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.widget.Button
 */
package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.Button;
import com.google.android.gms.R;
import com.google.android.gms.common.internal.zzac;

public final class zzak
extends Button {
    public zzak(Context context) {
        this(context, null);
    }

    public zzak(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 16842824);
    }

    private void zza(Resources resources) {
        this.setTypeface(Typeface.DEFAULT_BOLD);
        this.setTextSize(14.0f);
        int n = (int)(resources.getDisplayMetrics().density * 48.0f + 0.5f);
        this.setMinHeight(n);
        this.setMinWidth(n);
    }

    private void zzb(Resources resources, int n, int n2) {
        this.setBackgroundDrawable(resources.getDrawable(this.zze(n, this.zzg(n2, R.drawable.common_google_signin_btn_icon_dark, R.drawable.common_google_signin_btn_icon_light, R.drawable.common_google_signin_btn_icon_light), this.zzg(n2, R.drawable.common_google_signin_btn_text_dark, R.drawable.common_google_signin_btn_text_light, R.drawable.common_google_signin_btn_text_light))));
    }

    private void zzc(Resources object, int n, int n2) {
        block5 : {
            this.setTextColor(zzac.zzw(object.getColorStateList(this.zzg(n2, R.color.common_google_signin_btn_text_dark, R.color.common_google_signin_btn_text_light, R.color.common_google_signin_btn_text_light))));
            switch (n) {
                default: {
                    object = new StringBuilder(32);
                    object.append("Unknown button size: ");
                    object.append(n);
                    throw new IllegalStateException(object.toString());
                }
                case 2: {
                    this.setText(null);
                    break block5;
                }
                case 1: {
                    n = R.string.common_signin_button_text_long;
                    break;
                }
                case 0: {
                    n = R.string.common_signin_button_text;
                }
            }
            this.setText((CharSequence)object.getString(n));
        }
        this.setTransformationMethod(null);
    }

    private int zze(int n, int n2, int n3) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder(32);
                stringBuilder.append("Unknown button size: ");
                stringBuilder.append(n);
                throw new IllegalStateException(stringBuilder.toString());
            }
            case 2: {
                return n2;
            }
            case 0: 
            case 1: 
        }
        return n3;
    }

    private int zzg(int n, int n2, int n3, int n4) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder(33);
                stringBuilder.append("Unknown color scheme: ");
                stringBuilder.append(n);
                throw new IllegalStateException(stringBuilder.toString());
            }
            case 2: {
                return n4;
            }
            case 1: {
                return n3;
            }
            case 0: 
        }
        return n2;
    }

    public void zza(Resources resources, int n, int n2) {
        this.zza(resources);
        this.zzb(resources, n, n2);
        this.zzc(resources, n, n2);
    }
}
