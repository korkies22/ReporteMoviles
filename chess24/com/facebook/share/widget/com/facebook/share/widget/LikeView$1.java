/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.facebook.share.widget;

import android.view.View;

class LikeView
implements View.OnClickListener {
    LikeView() {
    }

    public void onClick(View view) {
        LikeView.this.toggleLike();
    }
}
