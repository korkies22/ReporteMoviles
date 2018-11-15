/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.setup;

import android.view.View;

class SetupBoardActivity
implements View.OnClickListener {
    SetupBoardActivity() {
    }

    public void onClick(View view) {
        SetupBoardActivity.this.enableDeleteMode(SetupBoardActivity.this._deleteMode ^ true);
    }
}
