/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.facebook;

import android.content.Context;
import android.view.View;

class FacebookButtonBase
implements View.OnClickListener {
    FacebookButtonBase() {
    }

    public void onClick(View view) {
        FacebookButtonBase.this.logButtonTapped(FacebookButtonBase.this.getContext());
        if (FacebookButtonBase.this.internalOnClickListener != null) {
            FacebookButtonBase.this.internalOnClickListener.onClick(view);
            return;
        }
        if (FacebookButtonBase.this.externalOnClickListener != null) {
            FacebookButtonBase.this.externalOnClickListener.onClick(view);
        }
    }
}
