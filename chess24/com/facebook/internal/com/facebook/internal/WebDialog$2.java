/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.facebook.internal;

import android.view.View;

class WebDialog
implements View.OnClickListener {
    WebDialog() {
    }

    public void onClick(View view) {
        WebDialog.this.cancel();
    }
}
