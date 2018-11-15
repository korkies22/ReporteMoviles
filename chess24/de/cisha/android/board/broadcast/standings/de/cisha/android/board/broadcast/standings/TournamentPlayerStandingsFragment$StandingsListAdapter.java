/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 */
package de.cisha.android.board.broadcast.standings;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.neovisionaries.i18n.CountryCode;
import de.cisha.android.board.broadcast.model.PlayerStanding;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.standings.TournamentPlayerStandingsFragment;
import de.cisha.android.board.broadcast.standings.view.StandingsRankingItem;
import java.text.DecimalFormat;
import java.util.List;

class TournamentPlayerStandingsFragment.StandingsListAdapter
extends BaseAdapter {
    TournamentPlayerStandingsFragment.StandingsListAdapter() {
    }

    public int getCount() {
        return TournamentPlayerStandingsFragment.this._standings.size();
    }

    public Object getItem(int n) {
        return TournamentPlayerStandingsFragment.this._standings.get(n);
    }

    public long getItemId(int n) {
        return 0L;
    }

    public View getView(int n, View object, ViewGroup object2) {
        if (object == null) {
            object = new StandingsRankingItem((Context)TournamentPlayerStandingsFragment.this.getActivity());
            object.hideLastRow();
        } else {
            object = (StandingsRankingItem)((Object)object);
        }
        TournamentPlayer tournamentPlayer = (TournamentPlayer)TournamentPlayerStandingsFragment.this._standings.get(n);
        Object object3 = tournamentPlayer.getStanding();
        object2 = "";
        if (object3 != null) {
            object2 = DecimalFormat.getInstance();
            object2.setMinimumFractionDigits(0);
            object2.setMaximumFractionDigits(1);
            object2 = object2.format(object3.getPoints());
        }
        object3 = new StringBuilder();
        object3.append(n + 1);
        object3.append(".");
        object.setItemText(object3.toString(), tournamentPlayer.getNameWithTitleAndRating(), (String)object2);
        object2 = tournamentPlayer.getCountry();
        if (object2 != null) {
            object.setFlagImage(object2.getImageId());
            object.setShowsFlag(true);
            return object;
        }
        object.setShowsFlag(false);
        return object;
    }

    public boolean isEnabled(int n) {
        return true;
    }
}
