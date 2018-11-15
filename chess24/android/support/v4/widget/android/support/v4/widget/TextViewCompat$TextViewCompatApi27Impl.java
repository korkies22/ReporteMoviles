/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.TextView
 */
package android.support.v4.widget;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.TextViewCompat;
import android.widget.TextView;

@RequiresApi(value=27)
static class TextViewCompat.TextViewCompatApi27Impl
extends TextViewCompat.TextViewCompatApi26Impl {
    TextViewCompat.TextViewCompatApi27Impl() {
    }

    @Override
    public int getAutoSizeMaxTextSize(TextView textView) {
        return textView.getAutoSizeMaxTextSize();
    }

    @Override
    public int getAutoSizeMinTextSize(TextView textView) {
        return textView.getAutoSizeMinTextSize();
    }

    @Override
    public int getAutoSizeStepGranularity(TextView textView) {
        return textView.getAutoSizeStepGranularity();
    }

    @Override
    public int[] getAutoSizeTextAvailableSizes(TextView textView) {
        return textView.getAutoSizeTextAvailableSizes();
    }

    @Override
    public int getAutoSizeTextType(TextView textView) {
        return textView.getAutoSizeTextType();
    }

    @Override
    public void setAutoSizeTextTypeUniformWithConfiguration(TextView textView, int n, int n2, int n3, int n4) throws IllegalArgumentException {
        textView.setAutoSizeTextTypeUniformWithConfiguration(n, n2, n3, n4);
    }

    @Override
    public void setAutoSizeTextTypeUniformWithPresetSizes(TextView textView, @NonNull int[] arrn, int n) throws IllegalArgumentException {
        textView.setAutoSizeTextTypeUniformWithPresetSizes(arrn, n);
    }

    @Override
    public void setAutoSizeTextTypeWithDefaults(TextView textView, int n) {
        textView.setAutoSizeTextTypeWithDefaults(n);
    }
}
