/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Color
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.ListAdapter
 *  android.widget.ListView
 */
package de.cisha.android.board.broadcast.standings;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import de.cisha.android.board.broadcast.TournamentSubFragment;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentGamesObserver;
import de.cisha.android.board.broadcast.model.TournamentHolder;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;

public abstract class AbstractStandingsFragment
extends TournamentSubFragment
implements TournamentGamesObserver {
    private ListView _listView;

    @Override
    public void allGameInfosChanged() {
    }

    protected boolean canSelectItems() {
        return false;
    }

    protected abstract View createHeaderView();

    protected abstract ListAdapter createStandingsListAdapter();

    @Override
    public void gameInfoChanged(TournamentGameInfo tournamentGameInfo) {
    }

    protected void itemSelected(int n) {
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.getTournamentHolder().addTournamentGamesObserver(this);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = new LinearLayout((Context)this.getActivity());
        layoutInflater.setBackgroundColor(Color.rgb((int)255, (int)255, (int)255));
        layoutInflater.setOrientation(1);
        layoutInflater.addView(this.createHeaderView());
        this._listView = new ListView((Context)this.getActivity());
        this._listView.setDividerHeight(0);
        this._listView.setAdapter(this.createStandingsListAdapter());
        if (this.canSelectItems()) {
            this._listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                    AbstractStandingsFragment.this.itemSelected(n);
                }
            });
        } else {
            this._listView.setItemsCanFocus(false);
        }
        viewGroup = new LinearLayout.LayoutParams(-1, -1);
        viewGroup.weight = 1.0f;
        layoutInflater.addView((View)this._listView, (ViewGroup.LayoutParams)viewGroup);
        return layoutInflater;
    }

    @Override
    public void onSelectRound(TournamentRoundInfo tournamentRoundInfo) {
    }

}
