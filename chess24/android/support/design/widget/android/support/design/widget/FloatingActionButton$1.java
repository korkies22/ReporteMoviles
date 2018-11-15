/*
 * Decompiled with CFR 0_134.
 */
package android.support.design.widget;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.FloatingActionButtonImpl;

class FloatingActionButton
implements FloatingActionButtonImpl.InternalVisibilityChangedListener {
    final /* synthetic */ FloatingActionButton.OnVisibilityChangedListener val$listener;

    FloatingActionButton(FloatingActionButton.OnVisibilityChangedListener onVisibilityChangedListener) {
        this.val$listener = onVisibilityChangedListener;
    }

    @Override
    public void onHidden() {
        this.val$listener.onHidden(FloatingActionButton.this);
    }

    @Override
    public void onShown() {
        this.val$listener.onShown(FloatingActionButton.this);
    }
}
