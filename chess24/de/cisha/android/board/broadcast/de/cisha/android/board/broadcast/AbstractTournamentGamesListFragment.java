/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.View$OnLongClickListener
 *  android.view.ViewGroup
 *  android.widget.AbsListView
 *  android.widget.AbsListView$OnScrollListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.LinearLayout
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.TextView
 */
package de.cisha.android.board.broadcast;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import de.cisha.android.board.TwoSidedHashMap;
import de.cisha.android.board.broadcast.TournamentSubFragmentWithOpenedGames;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentGameID;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentGamesObserver;
import de.cisha.android.board.broadcast.model.TournamentHolder;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import de.cisha.android.board.broadcast.view.TournamentGameInfoView;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public abstract class AbstractTournamentGamesListFragment
extends TournamentSubFragmentWithOpenedGames
implements TournamentGamesObserver {
    public static final String TAGNAME = "BROADCAST_TOURNAMENT_GAMES_LIST_FRAGMENT";
    private ListView _list;
    private TwoSidedHashMap<String, TournamentGameInfoView> _mapIdView = new TwoSidedHashMap();
    private View _navigationViewGroup;
    private View _nextButton;
    private View _nextFastButton;
    private LinearLayout _offsetLayout;
    private View _previousButton;
    private View _previousFastButton;
    private ViewGroup _roundChoser;
    private TextView _roundDescription;
    private boolean _scrolling;
    private Timer _timer;

    private void updateSelectedRoundView(final TournamentRoundInfo tournamentRoundInfo) {
        if (tournamentRoundInfo != null) {
            this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                @Override
                public void run() {
                    AbstractTournamentGamesListFragment.this._roundDescription.setText((CharSequence)tournamentRoundInfo.getRoundString());
                    TournamentRoundInfo tournamentRoundInfo3 = tournamentRoundInfo.getMainRound();
                    TournamentRoundInfo tournamentRoundInfo2 = tournamentRoundInfo;
                    int n = 0;
                    int n2 = tournamentRoundInfo3 != tournamentRoundInfo2 ? 1 : 0;
                    tournamentRoundInfo3 = AbstractTournamentGamesListFragment.this._previousFastButton;
                    int n3 = 8;
                    int n4 = n2 != 0 ? 0 : 8;
                    tournamentRoundInfo3.setVisibility(n4);
                    tournamentRoundInfo3 = AbstractTournamentGamesListFragment.this._nextFastButton;
                    n4 = n3;
                    if (n2 != 0) {
                        n4 = 0;
                    }
                    tournamentRoundInfo3.setVisibility(n4);
                    tournamentRoundInfo3 = AbstractTournamentGamesListFragment.this._previousButton;
                    n2 = AbstractTournamentGamesListFragment.this.getTournamentHolder().hasPreviousRound() ? 0 : 4;
                    tournamentRoundInfo3.setVisibility(n2);
                    tournamentRoundInfo3 = AbstractTournamentGamesListFragment.this._nextButton;
                    n2 = AbstractTournamentGamesListFragment.this.getTournamentHolder().hasNextRound() ? n : 4;
                    tournamentRoundInfo3.setVisibility(n2);
                }
            });
        }
    }

    @Override
    public void gameInfoChanged(final TournamentGameInfo tournamentGameInfo) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                TwoSidedHashMap twoSidedHashMap = AbstractTournamentGamesListFragment.this._mapIdView;
                synchronized (twoSidedHashMap) {
                    if (AbstractTournamentGamesListFragment.this._mapIdView.containsKey(tournamentGameInfo.getGameID().getUuid())) {
                        ((TournamentGameInfoView)((Object)AbstractTournamentGamesListFragment.this._mapIdView.get(tournamentGameInfo.getGameID().getUuid()))).setGameInfo(tournamentGameInfo);
                    }
                    return;
                }
            }
        });
    }

    protected abstract ListAdapter getListAdapter();

    protected abstract void listItemClicked(int var1);

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2131427388, viewGroup, false);
        this._list = (ListView)layoutInflater.findViewById(2131296356);
        this._offsetLayout = new LinearLayout((Context)this.getActivity());
        this._list.addFooterView((View)this._offsetLayout);
        this._list.setOverScrollMode(2);
        this._list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                AbstractTournamentGamesListFragment.this.listItemClicked(n);
            }
        });
        this._list.setAdapter(this.getListAdapter());
        this._list.setOnScrollListener(new AbsListView.OnScrollListener(){

            public void onScroll(AbsListView absListView, int n, int n2, int n3) {
            }

            public void onScrollStateChanged(AbsListView object, int n) {
                object = AbstractTournamentGamesListFragment.this;
                boolean bl = true;
                if (n != 1) {
                    bl = false;
                }
                ((AbstractTournamentGamesListFragment)object)._scrolling = bl;
            }
        });
        this._navigationViewGroup = layoutInflater.findViewById(2131296355);
        this._navigationViewGroup.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
            }
        });
        this._navigationViewGroup.measure(View.MeasureSpec.makeMeasureSpec((int)0, (int)0), View.MeasureSpec.makeMeasureSpec((int)0, (int)0));
        this._offsetLayout.addView((View)new LinearLayout((Context)this.getActivity()), -2, this._navigationViewGroup.getMeasuredHeight());
        this._previousButton = layoutInflater.findViewById(2131296352);
        this._previousButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                AbstractTournamentGamesListFragment.this.getTournamentHolder().selectPreviousRound();
            }
        });
        this._previousButton.setOnLongClickListener(new View.OnLongClickListener(){

            public boolean onLongClick(View view) {
                AbstractTournamentGamesListFragment.this.getTournamentHolder().selectFirstRound();
                return true;
            }
        });
        this._previousFastButton = layoutInflater.findViewById(2131296353);
        this._previousFastButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                AbstractTournamentGamesListFragment.this.getTournamentHolder().selectPreviousMainRound();
            }
        });
        this._nextButton = layoutInflater.findViewById(2131296350);
        this._nextButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                AbstractTournamentGamesListFragment.this.getTournamentHolder().selectNextRound();
            }
        });
        this._nextButton.setOnLongClickListener(new View.OnLongClickListener(){

            public boolean onLongClick(View view) {
                AbstractTournamentGamesListFragment.this.getTournamentHolder().selectLastRound();
                return true;
            }
        });
        this._nextFastButton = layoutInflater.findViewById(2131296351);
        this._nextFastButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                AbstractTournamentGamesListFragment.this.getTournamentHolder().selectNextMainRound();
            }
        });
        this._roundChoser = (ViewGroup)layoutInflater.findViewById(2131296355);
        this._roundDescription = (TextView)layoutInflater.findViewById(2131296354);
        return layoutInflater;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        this._list = null;
        this._nextButton = null;
        this._previousButton = null;
        this._roundDescription = null;
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();
        this._timer.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        this._timer = new Timer();
        this._timer.schedule(new TimerTask(){

            @Override
            public void run() {
                if (!AbstractTournamentGamesListFragment.this._scrolling) {
                    AbstractTournamentGamesListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                        @Override
                        public void run() {
                            Iterator iterator = AbstractTournamentGamesListFragment.this._mapIdView.values().iterator();
                            while (iterator.hasNext()) {
                                ((TournamentGameInfoView)((Object)iterator.next())).updateRemainingTimes();
                            }
                        }
                    });
                }
            }

        }, 1000L, 1000L);
    }

    @Override
    public void onSelectRound(TournamentRoundInfo tournamentRoundInfo) {
        this.updateSelectedRoundView(tournamentRoundInfo);
        this.allGameInfosChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        this.getTournamentHolder().addTournamentGamesObserver(this);
        TournamentRoundInfo tournamentRoundInfo = this.getTournamentHolder().getSelectedRound();
        if (tournamentRoundInfo != null) {
            this.updateSelectedRoundView(tournamentRoundInfo);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        this.getTournamentHolder().removeTournamentGamesObserver(this);
    }

    protected void openGame(TournamentGameID tournamentGameID) {
        this.gameSelected(tournamentGameID);
    }

    protected void registerGameView(TournamentGameID tournamentGameID, TournamentGameInfoView tournamentGameInfoView) {
        this._mapIdView.put(tournamentGameID.getUuid(), tournamentGameInfoView);
    }

    @Override
    public void registeredForChangesOn(final Tournament tournament, TournamentRoundInfo tournamentRoundInfo, final boolean bl) {
        this.onSelectRound(tournamentRoundInfo);
        this._navigationViewGroup.post(new Runnable(){

            @Override
            public void run() {
                if (bl) {
                    View view = AbstractTournamentGamesListFragment.this._navigationViewGroup;
                    int n = tournament.getRounds().size() < 2 ? 8 : 0;
                    view.setVisibility(n);
                }
            }
        });
    }

    protected void showRoundsChoser(boolean bl) {
        ViewGroup viewGroup = this._roundChoser;
        int n = bl ? 0 : 8;
        viewGroup.setVisibility(n);
    }

}
