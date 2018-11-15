/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.LayoutInflater$Factory2
 *  android.view.View
 */
package android.support.v4.view;

import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

static class LayoutInflaterCompat.Factory2Wrapper
implements LayoutInflater.Factory2 {
    final LayoutInflaterFactory mDelegateFactory;

    LayoutInflaterCompat.Factory2Wrapper(LayoutInflaterFactory layoutInflaterFactory) {
        this.mDelegateFactory = layoutInflaterFactory;
    }

    public View onCreateView(View view, String string, Context context, AttributeSet attributeSet) {
        return this.mDelegateFactory.onCreateView(view, string, context, attributeSet);
    }

    public View onCreateView(String string, Context context, AttributeSet attributeSet) {
        return this.mDelegateFactory.onCreateView(null, string, context, attributeSet);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append("{");
        stringBuilder.append(this.mDelegateFactory);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
