/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.mainmenu.view;

import android.view.View;

class MenuSlider
implements View.OnClickListener {
    MenuSlider() {
    }

    public void onClick(View view) {
        if (MenuSlider.this.menuShouldAnimate()) {
            if (MenuSlider.this._menuOpened) {
                MenuSlider.this.notifyObserversNewX(0.999f);
            } else {
                MenuSlider.this.notifyObserversNewX(0.001f);
            }
        }
        MenuSlider.this.toggle(true);
    }
}
