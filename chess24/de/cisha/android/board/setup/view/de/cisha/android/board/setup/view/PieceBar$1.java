/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.setup.view;

import android.view.View;

class PieceBar
implements View.OnClickListener {
    PieceBar() {
    }

    public void onClick(View view) {
        PieceBar.this.toggleColor();
    }
}
