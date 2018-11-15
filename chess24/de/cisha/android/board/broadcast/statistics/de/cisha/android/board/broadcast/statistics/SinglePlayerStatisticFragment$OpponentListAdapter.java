/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 */
package de.cisha.android.board.broadcast.statistics;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import de.cisha.android.board.broadcast.model.TournamentGameID;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.statistics.SinglePlayerStatisticFragment;
import de.cisha.android.board.broadcast.statistics.view.OpponentRowItem;
import java.util.List;

private class SinglePlayerStatisticFragment.OpponentListAdapter
extends BaseAdapter {
    private SinglePlayerStatisticFragment.OpponentListAdapter() {
    }

    public int getCount() {
        return SinglePlayerStatisticFragment.this._gamesForPlayer.size();
    }

    public Object getItem(int n) {
        return SinglePlayerStatisticFragment.this._gamesForPlayer.get(n);
    }

    public long getItemId(int n) {
        return ((TournamentGameInfo)SinglePlayerStatisticFragment.this._gamesForPlayer.get(n)).hashCode();
    }

    public View getView(final int n, View object, ViewGroup object2) {
        object2 = (OpponentRowItem)((Object)object);
        object = object2;
        if (object2 == null) {
            object = new OpponentRowItem((Context)SinglePlayerStatisticFragment.this.getActivity());
        }
        object.setDataForOpponentOfPlayer(SinglePlayerStatisticFragment.this._player, (TournamentGameInfo)SinglePlayerStatisticFragment.this._gamesForPlayer.get(n));
        object.setGameSelectionListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (n < SinglePlayerStatisticFragment.this._gamesForPlayer.size()) {
                    SinglePlayerStatisticFragment.this.gameSelected(((TournamentGameInfo)SinglePlayerStatisticFragment.this._gamesForPlayer.get(n)).getGameID());
                }
            }
        });
        return object;
    }

}
