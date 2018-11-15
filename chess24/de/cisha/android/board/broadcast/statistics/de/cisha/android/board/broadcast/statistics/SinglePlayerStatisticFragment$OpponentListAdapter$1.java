/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.broadcast.statistics;

import android.view.View;
import de.cisha.android.board.broadcast.model.TournamentGameID;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.statistics.SinglePlayerStatisticFragment;
import java.util.List;

class SinglePlayerStatisticFragment.OpponentListAdapter
implements View.OnClickListener {
    final /* synthetic */ int val$position;

    SinglePlayerStatisticFragment.OpponentListAdapter(int n) {
        this.val$position = n;
    }

    public void onClick(View view) {
        if (this.val$position < OpponentListAdapter.this.this$0._gamesForPlayer.size()) {
            OpponentListAdapter.this.this$0.gameSelected(((TournamentGameInfo)OpponentListAdapter.this.this$0._gamesForPlayer.get(this.val$position)).getGameID());
        }
    }
}
