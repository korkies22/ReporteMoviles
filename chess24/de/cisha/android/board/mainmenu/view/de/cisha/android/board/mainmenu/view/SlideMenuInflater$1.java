/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.mainmenu.view;

import android.app.Activity;
import android.view.View;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.mainmenu.view.MainMenuSlider;
import de.cisha.android.board.mainmenu.view.MenuItem;

class SlideMenuInflater
implements View.OnClickListener {
    final /* synthetic */ MenuItem val$item;

    SlideMenuInflater(MenuItem menuItem) {
        this.val$item = menuItem;
    }

    public void onClick(View view) {
        if (!this.val$item.isHighlighted()) {
            this.val$item.highlight(true);
            this.val$item.performAction(SlideMenuInflater.this._activity, SlideMenuInflater.this._presenter);
            return;
        }
        SlideMenuInflater.this._slider.closeWithAnimation();
    }
}
