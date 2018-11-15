/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.view;

import android.view.View;
import de.cisha.android.board.action.BoardAction;

class PremiumButtonOverlayLayout
implements View.OnClickListener {
    PremiumButtonOverlayLayout() {
    }

    public void onClick(View view) {
        if (PremiumButtonOverlayLayout.this._action != null) {
            PremiumButtonOverlayLayout.this._action.perform();
        }
    }
}
