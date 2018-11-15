/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 */
package android.support.v4.view;

import android.content.Context;
import android.support.v4.view.AsyncLayoutInflater;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

private static class AsyncLayoutInflater.BasicInflater
extends LayoutInflater {
    private static final String[] sClassPrefixList = new String[]{"android.widget.", "android.webkit.", "android.app."};

    AsyncLayoutInflater.BasicInflater(Context context) {
        super(context);
    }

    public LayoutInflater cloneInContext(Context context) {
        return new AsyncLayoutInflater.BasicInflater(context);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected View onCreateView(String string, AttributeSet attributeSet) throws ClassNotFoundException {
        String[] arrstring = sClassPrefixList;
        int n = 0;
        int n2 = arrstring.length;
        do {
            if (n >= n2) {
                return super.onCreateView(string, attributeSet);
            }
            String string2 = arrstring[n];
            try {
                string2 = this.createView(string, string2, attributeSet);
                if (string2 != null) {
                    return string2;
                }
            }
            catch (ClassNotFoundException classNotFoundException) {}
            ++n;
        } while (true);
    }
}
