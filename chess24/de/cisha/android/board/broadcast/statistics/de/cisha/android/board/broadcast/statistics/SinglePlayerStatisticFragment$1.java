/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 */
package de.cisha.android.board.broadcast.statistics;

import android.view.View;
import android.widget.AdapterView;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentHolder;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import java.util.List;

class SinglePlayerStatisticFragment
implements AdapterView.OnItemClickListener {
    SinglePlayerStatisticFragment() {
    }

    public void onItemClick(AdapterView<?> object, View view, int n, long l) {
        if (n < SinglePlayerStatisticFragment.this._gamesForPlayer.size() && (object = ((TournamentGameInfo)SinglePlayerStatisticFragment.this._gamesForPlayer.get(n)).getOpponentOfPlayer(SinglePlayerStatisticFragment.this._player)) != null) {
            SinglePlayerStatisticFragment.this.getTournamentHolder().setSelectedPlayer((TournamentPlayer)object);
        }
    }
}
