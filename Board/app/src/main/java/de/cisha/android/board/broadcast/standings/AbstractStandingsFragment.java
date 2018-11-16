// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.standings;

import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.graphics.Color;
import android.content.Context;
import android.widget.LinearLayout;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import android.widget.ListAdapter;
import android.view.View;
import android.widget.ListView;
import de.cisha.android.board.broadcast.model.TournamentGamesObserver;
import de.cisha.android.board.broadcast.TournamentSubFragment;

public abstract class AbstractStandingsFragment extends TournamentSubFragment implements TournamentGamesObserver
{
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
    public void gameInfoChanged(final TournamentGameInfo tournamentGameInfo) {
    }
    
    protected void itemSelected(final int n) {
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.getTournamentHolder().addTournamentGamesObserver(this);
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final LinearLayout linearLayout = new LinearLayout((Context)this.getActivity());
        linearLayout.setBackgroundColor(Color.rgb(255, 255, 255));
        linearLayout.setOrientation(1);
        linearLayout.addView(this.createHeaderView());
        (this._listView = new ListView((Context)this.getActivity())).setDividerHeight(0);
        this._listView.setAdapter(this.createStandingsListAdapter());
        if (this.canSelectItems()) {
            this._listView.setOnItemClickListener((AdapterView.OnItemClickListener)new AdapterView.OnItemClickListener() {
                public void onItemClick(final AdapterView<?> adapterView, final View view, final int n, final long n2) {
                    AbstractStandingsFragment.this.itemSelected(n);
                }
            });
        }
        else {
            this._listView.setItemsCanFocus(false);
        }
        final LinearLayout.LayoutParams linearLayout.LayoutParams = new LinearLayout.LayoutParams(-1, -1);
        linearLayout.LayoutParams.weight = 1.0f;
        linearLayout.addView((View)this._listView, (ViewGroup.LayoutParams)linearLayout.LayoutParams);
        return (View)linearLayout;
    }
    
    @Override
    public void onSelectRound(final TournamentRoundInfo tournamentRoundInfo) {
    }
}
