/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 */
package de.cisha.android.board.view;

import android.view.MotionEvent;
import android.view.View;

class DragView
implements View.OnTouchListener {
    DragView() {
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            DragView.this.startDrag(view);
        }
        return true;
    }
}
