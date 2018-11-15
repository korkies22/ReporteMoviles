/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.webkit.WebView
 */
package com.facebook.internal;

import android.content.Context;
import android.webkit.WebView;

class WebDialog
extends WebView {
    WebDialog(Context context) {
        super(context);
    }

    public void onWindowFocusChanged(boolean bl) {
        try {
            super.onWindowFocusChanged(bl);
            return;
        }
        catch (NullPointerException nullPointerException) {
            return;
        }
    }
}
