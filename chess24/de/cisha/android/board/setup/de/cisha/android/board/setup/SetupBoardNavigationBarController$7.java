/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.RadioGroup
 */
package de.cisha.android.board.setup;

import android.view.View;
import android.widget.RadioGroup;
import de.cisha.android.board.setup.model.PositionHolder;
import de.cisha.chess.model.position.Position;

class SetupBoardNavigationBarController
implements Runnable {
    SetupBoardNavigationBarController() {
    }

    @Override
    public void run() {
        SetupBoardNavigationBarController.this.showSubmenu((View)SetupBoardNavigationBarController.this._radioGroupActiveColor);
        RadioGroup radioGroup = SetupBoardNavigationBarController.this._radioGroupActiveColor;
        int n = SetupBoardNavigationBarController.this._positionHolder.getPosition().getActiveColor() ? 2131296983 : 2131296982;
        radioGroup.check(n);
    }
}
