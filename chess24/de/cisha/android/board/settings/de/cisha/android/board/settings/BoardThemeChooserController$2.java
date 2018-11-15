/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.settings;

import android.view.View;
import de.cisha.android.board.TwoSidedHashMap;

class BoardThemeChooserController
implements View.OnClickListener {
    final /* synthetic */ View val$boardView;

    BoardThemeChooserController(View view) {
        this.val$boardView = view;
    }

    public void onClick(View view) {
        BoardThemeChooserController.this.selectBoardView(this.val$boardView);
        if (BoardThemeChooserController.this.isLockOn()) {
            BoardThemeChooserController.this.selectPieceView((View)BoardThemeChooserController.this._mapBoardToPiece.get((Object)this.val$boardView));
        }
    }
}
