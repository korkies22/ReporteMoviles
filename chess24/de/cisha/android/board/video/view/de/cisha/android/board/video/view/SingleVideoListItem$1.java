/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.TextView
 */
package de.cisha.android.board.video.view;

import android.view.View;
import android.widget.TextView;

class SingleVideoListItem
implements View.OnClickListener {
    SingleVideoListItem() {
    }

    public void onClick(View view) {
        if (SingleVideoListItem.this._description.getVisibility() == 8) {
            SingleVideoListItem.this._description.setVisibility(0);
            return;
        }
        SingleVideoListItem.this._description.setVisibility(8);
    }
}
