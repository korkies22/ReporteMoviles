/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.TextView
 */
package android.support.v4.widget;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.support.v4.widget.TextViewCompat;
import android.widget.TextView;

@RequiresApi(value=23)
static class TextViewCompat.TextViewCompatApi23Impl
extends TextViewCompat.TextViewCompatApi18Impl {
    TextViewCompat.TextViewCompatApi23Impl() {
    }

    @Override
    public void setTextAppearance(@NonNull TextView textView, @StyleRes int n) {
        textView.setTextAppearance(n);
    }
}
