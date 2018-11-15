/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.TextView
 */
package android.support.v4.widget;

import android.support.annotation.RequiresApi;
import android.support.v4.widget.TextViewCompat;
import android.widget.TextView;

@RequiresApi(value=16)
static class TextViewCompat.TextViewCompatApi16Impl
extends TextViewCompat.TextViewCompatBaseImpl {
    TextViewCompat.TextViewCompatApi16Impl() {
    }

    @Override
    public int getMaxLines(TextView textView) {
        return textView.getMaxLines();
    }

    @Override
    public int getMinLines(TextView textView) {
        return textView.getMinLines();
    }
}
