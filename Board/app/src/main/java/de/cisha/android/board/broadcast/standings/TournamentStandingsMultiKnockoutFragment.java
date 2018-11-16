// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.standings;

import de.cisha.android.board.broadcast.standings.view.TournamentMatchInfoView;
import android.widget.BaseAdapter;
import java.util.Iterator;
import java.util.HashMap;
import android.os.AsyncTask;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.ListAdapter;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import de.cisha.chess.util.Logger;
import android.app.Activity;
import de.cisha.android.board.broadcast.model.TournamentGameID;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.standings.model.IStandingsCalculator;
import de.cisha.android.board.broadcast.standings.model.MultiGameKnockOutStandingsCalculator;
import de.cisha.android.board.broadcast.model.TournamentHolder;
import java.util.LinkedList;
import de.cisha.android.board.broadcast.model.Tournament;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.view.View;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import java.util.Map;
import android.widget.ListView;
import de.cisha.android.board.broadcast.standings.model.KnockoutMatch;
import java.util.List;
import de.cisha.android.board.broadcast.GameSelectedListener;
import de.cisha.android.board.broadcast.model.TournamentGamesObserver;
import de.cisha.android.board.broadcast.TournamentSubFragment;

public class TournamentStandingsMultiKnockoutFragment extends TournamentSubFragment implements TournamentGamesObserver, GameSelectedListener
{
    public static final String TAGNAME = "BROADCAST_TOURNAMENT_STANDINGS_FRAGMENT_TAG";
    private List<KnockoutMatch> _currentMatchList;
    private GameSelectedListener _gameSelectionListener;
    private boolean[] _gamesListViewShownForMatch;
    private TournamentStandingsListAdapter _listAdapter;
    private ListView _listView;
    private Map<TournamentRoundInfo, List<KnockoutMatch>> _matches;
    private View _nextButton;
    private LinearLayout _offsetListItem;
    private View _previousButton;
    private TextView _roundDescription;
    private TournamentRoundInfo _selectedRound;
    private Tournament _tournament;
    private View _view;
    
    public TournamentStandingsMultiKnockoutFragment() {
        this._currentMatchList = new LinkedList<KnockoutMatch>();
        this._listAdapter = new TournamentStandingsListAdapter();
    }
    
    private void calculateStandings() {
        if (this._tournament != null) {
            ((StandingsCalculationTask)new StandingsCalculationTask(this._tournament, new MultiGameKnockOutStandingsCalculator()) {
                protected void onPostExecute(final Map<TournamentRoundInfo, List<KnockoutMatch>> map) {
                    TournamentStandingsMultiKnockoutFragment.this._matches = map;
                    TournamentStandingsMultiKnockoutFragment.this.updateViewWithStandingsInfo();
                }
            }).execute((Object[])new Void[0]);
        }
    }
    
    private void setCurrentMatchList(final List<KnockoutMatch> currentMatchList) {
        this._currentMatchList = currentMatchList;
        this._gamesListViewShownForMatch = new boolean[currentMatchList.size()];
    }
    
    private void updateSelectedRoundView(final TournamentRoundInfo tournamentRoundInfo) {
        if (this._roundDescription != null && tournamentRoundInfo != null) {
            final int size = this._tournament.getMainRounds().size();
            final TextView roundDescription = this._roundDescription;
            final StringBuilder sb = new StringBuilder();
            sb.append(tournamentRoundInfo.getRoundString());
            sb.append(" / ");
            sb.append(size);
            roundDescription.setText((CharSequence)sb.toString());
        }
    }
    
    private void updateViewWithStandingsInfo() {
        final Map<TournamentRoundInfo, List<KnockoutMatch>> matches = this._matches;
        TournamentRoundInfo tournamentRoundInfo;
        if (this._selectedRound != null) {
            tournamentRoundInfo = this._selectedRound;
        }
        else if (this._tournament != null && this._tournament.getCurrentRound() != null) {
            tournamentRoundInfo = this._tournament.getCurrentRound().getMainRound();
        }
        else {
            tournamentRoundInfo = null;
        }
        if (this._view != null && matches != null && tournamentRoundInfo != null) {
            final List<KnockoutMatch> currentMatchList = this._matches.get(this._selectedRound);
            if (currentMatchList != null) {
                this.setCurrentMatchList(currentMatchList);
                this._listAdapter.notifyDataSetChanged();
            }
        }
    }
    
    @Override
    public void allGameInfosChanged() {
        this.calculateStandings();
    }
    
    @Override
    public void gameInfoChanged(final TournamentGameInfo tournamentGameInfo) {
    }
    
    @Override
    public void gameSelected(final TournamentGameID tournamentGameID) {
        if (this._gameSelectionListener != null) {
            this._gameSelectionListener.gameSelected(tournamentGameID);
        }
    }
    
    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        this.getTournamentHolder().addTournamentGamesObserver(this);
        final Fragment parentFragment = this.getParentFragment();
        if (parentFragment instanceof GameSelectedListener) {
            this.setOnGameSelectedListener((GameSelectedListener)parentFragment);
            return;
        }
        Logger.getInstance().error(this.getClass().getName(), "Wrong parent fragment class - can't register gameSelectionListener");
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        (this._view = layoutInflater.inflate(2131427394, viewGroup, false)).setBackgroundResource(2131230870);
        return this._view;
    }
    
    @Override
    public void onSelectRound(TournamentRoundInfo mainRound) {
        if (mainRound != null) {
            mainRound = mainRound.getMainRound();
            if (!mainRound.equals(this._selectedRound)) {
                this._selectedRound = mainRound;
                this.updateViewWithStandingsInfo();
                this.updateSelectedRoundView(mainRound);
            }
        }
        else {
            Logger.getInstance().debug(this.getClass().getName(), "given Round was null");
        }
    }
    
    @Override
    public void onViewCreated(View viewById, final Bundle bundle) {
        super.onViewCreated(viewById, bundle);
        this._listView = (ListView)this._view.findViewById(2131296400);
        this._offsetListItem = new LinearLayout((Context)this.getActivity());
        this._listView.addFooterView((View)this._offsetListItem);
        this._listView.setAdapter((ListAdapter)this._listAdapter);
        this._roundDescription = (TextView)this._view.findViewById(2131296403);
        (this._previousButton = this._view.findViewById(2131296402)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                TournamentStandingsMultiKnockoutFragment.this.getTournamentHolder().selectPreviousMainRound();
            }
        });
        (this._nextButton = this._view.findViewById(2131296401)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                TournamentStandingsMultiKnockoutFragment.this.getTournamentHolder().selectNextMainRound();
            }
        });
        viewById = this._view.findViewById(2131296404);
        viewById.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
            }
        });
        viewById.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        this._offsetListItem.addView((View)new LinearLayout((Context)this.getActivity()), -2, viewById.getMeasuredHeight());
        this.updateViewWithStandingsInfo();
        this.updateSelectedRoundView(this._selectedRound);
    }
    
    @Override
    public void registeredForChangesOn(final Tournament tournament, final TournamentRoundInfo tournamentRoundInfo, final boolean b) {
        this._tournament = tournament;
        this.onSelectRound(tournamentRoundInfo);
        this.allGameInfosChanged();
    }
    
    public void setOnGameSelectedListener(final GameSelectedListener gameSelectionListener) {
        this._gameSelectionListener = gameSelectionListener;
    }
    
    private class StandingsCalculationTask extends AsyncTask<Void, Void, Map<TournamentRoundInfo, List<KnockoutMatch>>>
    {
        private IStandingsCalculator _standingsCalculator;
        private Tournament _taskTournament;
        
        public StandingsCalculationTask(final Tournament taskTournament, final IStandingsCalculator standingsCalculator) {
            this._taskTournament = taskTournament;
            this._standingsCalculator = standingsCalculator;
        }
        
        protected Map<TournamentRoundInfo, List<KnockoutMatch>> doInBackground(final Void... array) {
            final HashMap<TournamentRoundInfo, List<KnockoutMatch>> hashMap = new HashMap<TournamentRoundInfo, List<KnockoutMatch>>();
            for (final TournamentRoundInfo tournamentRoundInfo : this._taskTournament.getMainRounds()) {
                hashMap.put(tournamentRoundInfo, this._standingsCalculator.getMatchesForMainRound(tournamentRoundInfo, this._taskTournament));
            }
            return hashMap;
        }
    }
    
    private class TournamentStandingsListAdapter extends BaseAdapter
    {
        public int getCount() {
            return TournamentStandingsMultiKnockoutFragment.this._currentMatchList.size();
        }
        
        public Object getItem(final int n) {
            return TournamentStandingsMultiKnockoutFragment.this._currentMatchList.get(n);
        }
        
        public long getItemId(final int n) {
            return 0L;
        }
        
        public View getView(final int n, final View view, final ViewGroup viewGroup) {
            TournamentMatchInfoView tournamentMatchInfoView;
            if (view != null) {
                tournamentMatchInfoView = (TournamentMatchInfoView)view;
            }
            else {
                tournamentMatchInfoView = new TournamentMatchInfoView((Context)TournamentStandingsMultiKnockoutFragment.this.getActivity());
                tournamentMatchInfoView.setGameSelectionListener(TournamentStandingsMultiKnockoutFragment.this);
            }
            tournamentMatchInfoView.updateWithMatch(TournamentStandingsMultiKnockoutFragment.this._currentMatchList.get(n));
            tournamentMatchInfoView.setOnHeaderClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    TournamentStandingsMultiKnockoutFragment.this._gamesListViewShownForMatch[n] ^= true;
                    tournamentMatchInfoView.setGamesViewEnabled(TournamentStandingsMultiKnockoutFragment.this._gamesListViewShownForMatch[n]);
                }
            });
            tournamentMatchInfoView.setGamesViewEnabled(TournamentStandingsMultiKnockoutFragment.this._gamesListViewShownForMatch[n]);
            return (View)tournamentMatchInfoView;
        }
    }
}
