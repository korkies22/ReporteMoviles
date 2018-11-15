/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.LinearLayout
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.TextView
 */
package de.cisha.android.board.broadcast.standings;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import de.cisha.android.board.broadcast.GameSelectedListener;
import de.cisha.android.board.broadcast.TournamentSubFragment;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentGameID;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentGamesObserver;
import de.cisha.android.board.broadcast.model.TournamentHolder;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import de.cisha.android.board.broadcast.standings.model.IStandingsCalculator;
import de.cisha.android.board.broadcast.standings.model.KnockoutMatch;
import de.cisha.android.board.broadcast.standings.model.MultiGameKnockOutStandingsCalculator;
import de.cisha.android.board.broadcast.standings.view.TournamentMatchInfoView;
import de.cisha.chess.util.Logger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TournamentStandingsMultiKnockoutFragment
extends TournamentSubFragment
implements TournamentGamesObserver,
GameSelectedListener {
    public static final String TAGNAME = "BROADCAST_TOURNAMENT_STANDINGS_FRAGMENT_TAG";
    private List<KnockoutMatch> _currentMatchList = new LinkedList<KnockoutMatch>();
    private GameSelectedListener _gameSelectionListener;
    private boolean[] _gamesListViewShownForMatch;
    private TournamentStandingsListAdapter _listAdapter = new TournamentStandingsListAdapter();
    private ListView _listView;
    private Map<TournamentRoundInfo, List<KnockoutMatch>> _matches;
    private View _nextButton;
    private LinearLayout _offsetListItem;
    private View _previousButton;
    private TextView _roundDescription;
    private TournamentRoundInfo _selectedRound;
    private Tournament _tournament;
    private View _view;

    private void calculateStandings() {
        if (this._tournament != null) {
            new StandingsCalculationTask(this._tournament, new MultiGameKnockOutStandingsCalculator()){

                protected void onPostExecute(Map<TournamentRoundInfo, List<KnockoutMatch>> map) {
                    TournamentStandingsMultiKnockoutFragment.this._matches = map;
                    TournamentStandingsMultiKnockoutFragment.this.updateViewWithStandingsInfo();
                }
            }.execute((Object[])new Void[0]);
        }
    }

    private void setCurrentMatchList(List<KnockoutMatch> list) {
        this._currentMatchList = list;
        this._gamesListViewShownForMatch = new boolean[list.size()];
    }

    private void updateSelectedRoundView(TournamentRoundInfo tournamentRoundInfo) {
        if (this._roundDescription != null && tournamentRoundInfo != null) {
            int n = this._tournament.getMainRounds().size();
            TextView textView = this._roundDescription;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(tournamentRoundInfo.getRoundString());
            stringBuilder.append(" / ");
            stringBuilder.append(n);
            textView.setText((CharSequence)stringBuilder.toString());
        }
    }

    private void updateViewWithStandingsInfo() {
        Map<TournamentRoundInfo, List<KnockoutMatch>> map = this._matches;
        Object object = this._selectedRound != null ? this._selectedRound : (this._tournament != null && this._tournament.getCurrentRound() != null ? this._tournament.getCurrentRound().getMainRound() : null);
        if (this._view != null && map != null && object != null && (object = this._matches.get(this._selectedRound)) != null) {
            this.setCurrentMatchList((List<KnockoutMatch>)object);
            this._listAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void allGameInfosChanged() {
        this.calculateStandings();
    }

    @Override
    public void gameInfoChanged(TournamentGameInfo tournamentGameInfo) {
    }

    @Override
    public void gameSelected(TournamentGameID tournamentGameID) {
        if (this._gameSelectionListener != null) {
            this._gameSelectionListener.gameSelected(tournamentGameID);
        }
    }

    @Override
    public void onAttach(Activity object) {
        super.onAttach((Activity)object);
        this.getTournamentHolder().addTournamentGamesObserver(this);
        object = this.getParentFragment();
        if (object instanceof GameSelectedListener) {
            this.setOnGameSelectedListener((GameSelectedListener)object);
            return;
        }
        Logger.getInstance().error(this.getClass().getName(), "Wrong parent fragment class - can't register gameSelectionListener");
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this._view = layoutInflater.inflate(2131427394, viewGroup, false);
        this._view.setBackgroundResource(2131230870);
        return this._view;
    }

    @Override
    public void onSelectRound(TournamentRoundInfo tournamentRoundInfo) {
        if (tournamentRoundInfo != null) {
            if (!(tournamentRoundInfo = tournamentRoundInfo.getMainRound()).equals(this._selectedRound)) {
                this._selectedRound = tournamentRoundInfo;
                this.updateViewWithStandingsInfo();
                this.updateSelectedRoundView(tournamentRoundInfo);
                return;
            }
        } else {
            Logger.getInstance().debug(this.getClass().getName(), "given Round was null");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this._listView = (ListView)this._view.findViewById(2131296400);
        this._offsetListItem = new LinearLayout((Context)this.getActivity());
        this._listView.addFooterView((View)this._offsetListItem);
        this._listView.setAdapter((ListAdapter)this._listAdapter);
        this._roundDescription = (TextView)this._view.findViewById(2131296403);
        this._previousButton = this._view.findViewById(2131296402);
        this._previousButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                TournamentStandingsMultiKnockoutFragment.this.getTournamentHolder().selectPreviousMainRound();
            }
        });
        this._nextButton = this._view.findViewById(2131296401);
        this._nextButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                TournamentStandingsMultiKnockoutFragment.this.getTournamentHolder().selectNextMainRound();
            }
        });
        view = this._view.findViewById(2131296404);
        view.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
            }
        });
        view.measure(View.MeasureSpec.makeMeasureSpec((int)0, (int)0), View.MeasureSpec.makeMeasureSpec((int)0, (int)0));
        this._offsetListItem.addView((View)new LinearLayout((Context)this.getActivity()), -2, view.getMeasuredHeight());
        this.updateViewWithStandingsInfo();
        this.updateSelectedRoundView(this._selectedRound);
    }

    @Override
    public void registeredForChangesOn(Tournament tournament, TournamentRoundInfo tournamentRoundInfo, boolean bl) {
        this._tournament = tournament;
        this.onSelectRound(tournamentRoundInfo);
        this.allGameInfosChanged();
    }

    public void setOnGameSelectedListener(GameSelectedListener gameSelectedListener) {
        this._gameSelectionListener = gameSelectedListener;
    }

    private class StandingsCalculationTask
    extends AsyncTask<Void, Void, Map<TournamentRoundInfo, List<KnockoutMatch>>> {
        private IStandingsCalculator _standingsCalculator;
        private Tournament _taskTournament;

        public StandingsCalculationTask(Tournament tournament, IStandingsCalculator iStandingsCalculator) {
            this._taskTournament = tournament;
            this._standingsCalculator = iStandingsCalculator;
        }

        protected /* varargs */ Map<TournamentRoundInfo, List<KnockoutMatch>> doInBackground(Void ... object) {
            object = new HashMap();
            for (TournamentRoundInfo tournamentRoundInfo : this._taskTournament.getMainRounds()) {
                object.put(tournamentRoundInfo, this._standingsCalculator.getMatchesForMainRound(tournamentRoundInfo, this._taskTournament));
            }
            return object;
        }
    }

    private class TournamentStandingsListAdapter
    extends BaseAdapter {
        private TournamentStandingsListAdapter() {
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

}
