/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.widget.TextView
 */
package android.support.v4.widget;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.TextViewCompat;
import android.widget.TextView;

@RequiresApi(value=17)
static class TextViewCompat.TextViewCompatApi17Impl
extends TextViewCompat.TextViewCompatApi16Impl {
    TextViewCompat.TextViewCompatApi17Impl() {
    }

    @Override
    public Drawable[] getCompoundDrawablesRelative(@NonNull TextView arrdrawable) {
        int n = arrdrawable.getLayoutDirection();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        arrdrawable = arrdrawable.getCompoundDrawables();
        if (bl) {
            Drawable drawable = arrdrawable[2];
            Drawable drawable2 = arrdrawable[0];
            arrdrawable[0] = drawable;
            arrdrawable[2] = drawable2;
        }
        return arrdrawable;
    }

    @Override
    public void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        int n = textView.getLayoutDirection();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        Drawable drawable5 = bl ? drawable3 : drawable;
        if (!bl) {
            drawable = drawable3;
        }
        textView.setCompoundDrawables(drawable5, drawable2, drawable, drawable4);
    }

    @Override
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int n, @DrawableRes int n2, @DrawableRes int n3, @DrawableRes int n4) {
        int n5 = textView.getLayoutDirection();
        boolean bl = true;
        if (n5 != 1) {
            bl = false;
        }
        n5 = bl ? n3 : n;
        if (!bl) {
            n = n3;
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(n5, n2, n, n4);
    }

    @Override
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        int n = textView.getLayoutDirection();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        Drawable drawable5 = bl ? drawable3 : drawable;
        if (!bl) {
            drawable = drawable3;
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(drawable5, drawable2, drawable, drawable4);
    }
}
