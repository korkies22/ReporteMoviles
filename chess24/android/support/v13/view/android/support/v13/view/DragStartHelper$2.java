/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 */
package android.support.v13.view;

import android.view.MotionEvent;
import android.view.View;

class DragStartHelper
implements View.OnTouchListener {
    DragStartHelper() {
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        return DragStartHelper.this.onTouch(view, motionEvent);
    }
}
