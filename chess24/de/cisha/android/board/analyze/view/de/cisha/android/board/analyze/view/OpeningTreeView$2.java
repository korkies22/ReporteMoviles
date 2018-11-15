/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.analyze.view;

import android.view.View;
import de.cisha.android.board.analyze.model.OpeningMoveInformation;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveExecutor;
import de.cisha.chess.model.SEP;

class OpeningTreeView
implements View.OnClickListener {
    final /* synthetic */ OpeningMoveInformation val$moveInformation;

    OpeningTreeView(OpeningMoveInformation openingMoveInformation) {
        this.val$moveInformation = openingMoveInformation;
    }

    public void onClick(View view) {
        if (OpeningTreeView.this._moveExecutor != null) {
            OpeningTreeView.this._moveExecutor.doMoveInCurrentPosition(this.val$moveInformation.getMove().getSEP());
        }
    }
}
