/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 */
package de.cisha.android.board.setup;

import android.widget.CompoundButton;
import de.cisha.android.board.setup.model.PositionHolder;
import de.cisha.chess.model.ModifyablePosition;
import de.cisha.chess.model.position.Position;

class SetupBoardNavigationBarController
implements CompoundButton.OnCheckedChangeListener {
    SetupBoardNavigationBarController() {
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
        ModifyablePosition modifyablePosition = new ModifyablePosition(SetupBoardNavigationBarController.this._positionHolder.getPosition());
        switch (compoundButton.getId()) {
            default: {
                break;
            }
            case 2131296981: {
                modifyablePosition.setWhiteQueenSideCastling(bl);
                break;
            }
            case 2131296980: {
                modifyablePosition.setWhiteKingSideCastling(bl);
                break;
            }
            case 2131296979: {
                modifyablePosition.setBlackQueenSideCastling(bl);
                break;
            }
            case 2131296978: {
                modifyablePosition.setBlackKingSideCastling(bl);
            }
        }
        SetupBoardNavigationBarController.this._doNotClosePopup = true;
        SetupBoardNavigationBarController.this._positionHolder.setPosition(modifyablePosition.getSafePosition());
    }
}
