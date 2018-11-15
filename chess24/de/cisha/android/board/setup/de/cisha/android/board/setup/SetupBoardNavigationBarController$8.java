/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package de.cisha.android.board.setup;

import android.view.View;

class SetupBoardNavigationBarController
implements Runnable {
    SetupBoardNavigationBarController() {
    }

    @Override
    public void run() {
        SetupBoardNavigationBarController.this.updateCastlingView();
        SetupBoardNavigationBarController.this.showSubmenu(SetupBoardNavigationBarController.this._castlingView);
    }
}
