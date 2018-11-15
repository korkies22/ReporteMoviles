/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.playzone.view;

import android.view.View;

class AbstractAfterGameView
implements View.OnClickListener {
    AbstractAfterGameView() {
    }

    public void onClick(View view) {
        AbstractAfterGameView.this.rematchButtonClicked();
    }
}
