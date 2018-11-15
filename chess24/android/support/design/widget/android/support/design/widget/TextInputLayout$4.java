/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package android.support.design.widget;

import android.view.View;

class TextInputLayout
implements View.OnClickListener {
    TextInputLayout() {
    }

    public void onClick(View view) {
        TextInputLayout.this.passwordVisibilityToggleRequested(false);
    }
}
