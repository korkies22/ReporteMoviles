/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.view.View
 */
package android.support.design.widget;

import android.os.Handler;
import android.support.design.widget.BaseTransientBottomBar;
import android.view.View;

class BaseTransientBottomBar
implements BaseTransientBottomBar.OnAttachStateChangeListener {
    BaseTransientBottomBar() {
    }

    @Override
    public void onViewAttachedToWindow(View view) {
    }

    @Override
    public void onViewDetachedFromWindow(View view) {
        if (BaseTransientBottomBar.this.isShownOrQueued()) {
            android.support.design.widget.BaseTransientBottomBar.sHandler.post(new Runnable(){

                @Override
                public void run() {
                    BaseTransientBottomBar.this.onViewHidden(3);
                }
            });
        }
    }

}
