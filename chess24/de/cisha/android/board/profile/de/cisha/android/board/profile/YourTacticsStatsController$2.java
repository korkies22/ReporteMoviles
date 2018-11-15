/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile;

import de.cisha.android.board.profile.model.TacticStatisticData;
import de.cisha.android.board.view.BoardView;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.position.Position;

class YourTacticsStatsController
implements Runnable {
    final /* synthetic */ TacticStatisticData val$tacticsData;

    YourTacticsStatsController(TacticStatisticData tacticStatisticData) {
        this.val$tacticsData = tacticStatisticData;
    }

    @Override
    public void run() {
        YourTacticsStatsController.this._boardView.setPosition(new Position(this.val$tacticsData.getFenClassic()));
    }
}
