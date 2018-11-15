/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.ImageView
 */
package de.cisha.android.board.mainmenu.view;

import android.widget.ImageView;
import de.cisha.android.board.mainmenu.view.MenuObserver;
import de.cisha.android.board.mainmenu.view.MenuSlider;

private class MenuSlider.MyMenuObserver
implements MenuObserver {
    private MenuSlider.MyMenuObserver() {
    }

    @Override
    public void menuClosed() {
        MenuSlider.this.getMenuButton().setAlpha(255);
        MenuSlider.this._active = true;
    }

    @Override
    public void menuOpened() {
        MenuSlider.this.getMenuButton().setAlpha(0);
        MenuSlider.this._active = false;
    }

    @Override
    public void menuOpenedTo(float f) {
        int n = (int)((1.0f - f) * 255.0f);
        MenuSlider.this.getMenuButton().setAlpha(n);
        if (f > 0.0f) {
            MenuSlider.this._active = false;
            return;
        }
        MenuSlider.this._active = true;
    }
}
