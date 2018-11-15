/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.app;

import android.support.v7.widget.ContentFrameLayout;

class AppCompatDelegateImplV9
implements ContentFrameLayout.OnAttachListener {
    AppCompatDelegateImplV9() {
    }

    @Override
    public void onAttachedFromWindow() {
    }

    @Override
    public void onDetachedFromWindow() {
        AppCompatDelegateImplV9.this.dismissPopups();
    }
}
