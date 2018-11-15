/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.RadioGroup
 *  android.widget.RadioGroup$OnCheckedChangeListener
 */
package de.cisha.android.board.setup;

import android.widget.RadioGroup;
import de.cisha.android.board.setup.model.PositionHolder;
import de.cisha.chess.model.ModifyablePosition;
import de.cisha.chess.model.position.Position;

class SetupBoardNavigationBarController
implements RadioGroup.OnCheckedChangeListener {
    SetupBoardNavigationBarController() {
    }

    public void onCheckedChanged(RadioGroup object, int n) {
        boolean bl = n == 2131296983;
        object = new ModifyablePosition(SetupBoardNavigationBarController.this._positionHolder.getPosition());
        object.setActiveColor(bl);
        object.setEnPassantColumn(-1);
        SetupBoardNavigationBarController.this._positionHolder.setPosition(object.getSafePosition());
    }
}
