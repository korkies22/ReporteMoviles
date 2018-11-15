/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 */
package de.cisha.android.board.mainmenu.view;

import android.view.MotionEvent;
import android.view.View;

class MenuSlider
implements View.OnTouchListener {
    MenuSlider() {
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (!MenuSlider.this._menuOpened) {
            return false;
        }
        if (motionEvent.getAction() == 1) {
            MenuSlider.this.closeWithAnimation();
        }
        return true;
    }
}
