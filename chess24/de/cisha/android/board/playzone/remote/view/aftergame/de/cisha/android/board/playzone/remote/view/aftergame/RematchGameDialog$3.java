/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.playzone.remote.view.aftergame;

import android.view.View;
import de.cisha.android.ui.patterns.dialogs.ConfirmCallback;

class RematchGameDialog
implements View.OnClickListener {
    RematchGameDialog() {
    }

    public void onClick(View view) {
        RematchGameDialog.this._callback.canceled();
    }
}
