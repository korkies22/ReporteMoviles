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
import de.cisha.android.board.broadcast.model.TeamStanding;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import de.cisha.android.board.broadcast.standings.TournamentTeamStandingsFragment;
import de.cisha.android.board.broadcast.standings.view.StandingsRankingItem;
import de.cisha.android.ui.patterns.text.CustomTextViewTextStyle;
import de.cisha.chess.model.Country;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

class TournamentTeamStandingsFragment.StandingsListAdapter
extends BaseAdapter {
    TournamentTeamStandingsFragment.StandingsListAdapter() {
    }

    public int getCount() {
        return TournamentTeamStandingsFragment.this._standings.size();
    }

    public Object getItem(int n) {
        return TournamentTeamStandingsFragment.this._standings.get(n);
    }

    public long getItemId(int n) {
        return 0L;
    }

    public View getView(int n, View object, ViewGroup object2) {
        Serializable serializable;
        if (object == null) {
            object = new StandingsRankingItem((Context)TournamentTeamStandingsFragment.this.getActivity());
            object.setNameTextStyle(CustomTextViewTextStyle.TABLE);
        } else {
            object = (StandingsRankingItem)((Object)object);
        }
        TournamentTeam tournamentTeam = (TournamentTeam)TournamentTeamStandingsFragment.this._standings.get(n);
        object2 = tournamentTeam.getCurrentStandings();
        String string = "";
        if (object2 != null) {
            serializable = DecimalFormat.getInstance();
            serializable.setMinimumFractionDigits(0);
            serializable.setMaximumFractionDigits(1);
            string = serializable.format(object2.getTeamPoints());
            object2 = serializable.format(object2.getBoardPoints());
        } else {
            object2 = "";
        }
        serializable = new StringBuilder();
        serializable.append(n + 1);
        serializable.append(".");
        object.setItemText(serializable.toString(), tournamentTeam.getName(), string, (String)object2);
        object2 = tournamentTeam.getCountry();
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
