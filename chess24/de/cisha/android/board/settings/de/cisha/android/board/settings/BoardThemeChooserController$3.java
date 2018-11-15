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
    final /* synthetic */ View val$pieceView;

    BoardThemeChooserController(View view) {
        this.val$pieceView = view;
    }

    public void onClick(View view) {
        BoardThemeChooserController.this.selectPieceView(this.val$pieceView);
        if (BoardThemeChooserController.this.isLockOn()) {
            BoardThemeChooserController.this.selectBoardView((View)BoardThemeChooserController.this._mapBoardToPiece.getKeyForValue(this.val$pieceView));
        }
    }
}
