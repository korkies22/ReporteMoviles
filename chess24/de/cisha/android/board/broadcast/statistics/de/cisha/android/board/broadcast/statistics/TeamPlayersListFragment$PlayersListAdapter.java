/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 */
package de.cisha.android.board.broadcast.statistics;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.neovisionaries.i18n.CountryCode;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.standings.view.StandingsRankingItem;
import de.cisha.android.board.broadcast.statistics.TeamPlayersListFragment;
import java.util.List;

private class TeamPlayersListFragment.PlayersListAdapter
extends BaseAdapter {
    private TeamPlayersListFragment.PlayersListAdapter() {
    }

    public int getCount() {
        return TeamPlayersListFragment.this._players.size();
    }

    public Object getItem(int n) {
        return TeamPlayersListFragment.this._players.get(n);
    }

    public long getItemId(int n) {
        return ((TournamentPlayer)TeamPlayersListFragment.this._players.get(n)).hashCode();
    }

    public View getView(int n, View object, ViewGroup object2) {
        object2 = (StandingsRankingItem)((Object)object);
        TournamentPlayer tournamentPlayer = (TournamentPlayer)TeamPlayersListFragment.this._players.get(n);
        if (object == null) {
            object2 = new StandingsRankingItem((Context)TeamPlayersListFragment.this.getActivity());
            object2.hideLastTwoRows();
        }
        if (tournamentPlayer.getCountry() != null) {
            object2.setShowsFlag(true);
            object2.setFlagImage(tournamentPlayer.getCountry().getImageId());
        } else {
            object2.setShowsFlag(false);
        }
        object = new StringBuilder();
        object.append("");
        object.append(n + 1);
        object2.setItemText(object.toString(), tournamentPlayer.getNameWithTitleAndRating(), "");
        return object2;
    }
}
