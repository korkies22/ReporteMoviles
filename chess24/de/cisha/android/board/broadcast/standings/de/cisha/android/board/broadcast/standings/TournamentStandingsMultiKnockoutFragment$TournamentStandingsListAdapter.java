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
package de.cisha.android.board.broadcast.standings;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import de.cisha.android.board.broadcast.GameSelectedListener;
import de.cisha.android.board.broadcast.standings.TournamentStandingsMultiKnockoutFragment;
import de.cisha.android.board.broadcast.standings.model.KnockoutMatch;
import de.cisha.android.board.broadcast.standings.view.TournamentMatchInfoView;
import java.util.List;

private class TournamentStandingsMultiKnockoutFragment.TournamentStandingsListAdapter
extends BaseAdapter {
    private TournamentStandingsMultiKnockoutFragment.TournamentStandingsListAdapter() {
    }

    public int getCount() {
        return TournamentStandingsMultiKnockoutFragment.this._currentMatchList.size();
    }

    public Object getItem(int n) {
        return TournamentStandingsMultiKnockoutFragment.this._currentMatchList.get(n);
    }

    public long getItemId(int n) {
        return 0L;
    }

    public View getView(final int n, View object, ViewGroup viewGroup) {
        if (object != null) {
            object = (TournamentMatchInfoView)((Object)object);
        } else {
            object = new TournamentMatchInfoView((Context)TournamentStandingsMultiKnockoutFragment.this.getActivity());
            object.setGameSelectionListener(TournamentStandingsMultiKnockoutFragment.this);
        }
        object.updateWithMatch((KnockoutMatch)TournamentStandingsMultiKnockoutFragment.this._currentMatchList.get(n));
        object.setOnHeaderClickListener(new View.OnClickListener((TournamentMatchInfoView)((Object)object)){
            final /* synthetic */ TournamentMatchInfoView val$thisView;
            {
                this.val$thisView = tournamentMatchInfoView;
            }

            public void onClick(View view) {
                TournamentStandingsMultiKnockoutFragment.access$600((TournamentStandingsMultiKnockoutFragment)TournamentStandingsMultiKnockoutFragment.this)[n] = TournamentStandingsMultiKnockoutFragment.this._gamesListViewShownForMatch[n] ^ true;
                this.val$thisView.setGamesViewEnabled(TournamentStandingsMultiKnockoutFragment.this._gamesListViewShownForMatch[n]);
            }
        });
        object.setGamesViewEnabled(TournamentStandingsMultiKnockoutFragment.this._gamesListViewShownForMatch[n]);
        return object;
    }

}
