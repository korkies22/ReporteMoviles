// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast;

import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentGameID;
import java.util.Iterator;
import java.util.TimerTask;
import android.view.View.OnLongClickListener;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.content.Context;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.widget.ListAdapter;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import java.util.Timer;
import android.widget.TextView;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.view.View;
import de.cisha.android.board.broadcast.view.TournamentGameInfoView;
import de.cisha.android.board.TwoSidedHashMap;
import android.widget.ListView;
import de.cisha.android.board.broadcast.model.TournamentGamesObserver;

public abstract class AbstractTournamentGamesListFragment extends TournamentSubFragmentWithOpenedGames implements TournamentGamesObserver
{
    public static final String TAGNAME = "BROADCAST_TOURNAMENT_GAMES_LIST_FRAGMENT";
    private ListView _list;
    private TwoSidedHashMap<String, TournamentGameInfoView> _mapIdView;
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
    
    public AbstractTournamentGamesListFragment() {
        this._mapIdView = new TwoSidedHashMap<String, TournamentGameInfoView>();
    }
    
    private void updateSelectedRoundView(final TournamentRoundInfo tournamentRoundInfo) {
        if (tournamentRoundInfo != null) {
            this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                @Override
                public void run() {
                    AbstractTournamentGamesListFragment.this._roundDescription.setText((CharSequence)tournamentRoundInfo.getRoundString());
                    final TournamentRoundInfo mainRound = tournamentRoundInfo.getMainRound();
                    final TournamentRoundInfo val.tournamentRoundInfo = tournamentRoundInfo;
                    final boolean b = false;
                    final boolean b2 = mainRound != val.tournamentRoundInfo;
                    final View access.400 = AbstractTournamentGamesListFragment.this._previousFastButton;
                    final int n = 8;
                    int visibility;
                    if (b2) {
                        visibility = 0;
                    }
                    else {
                        visibility = 8;
                    }
                    access.400.setVisibility(visibility);
                    final View access.401 = AbstractTournamentGamesListFragment.this._nextFastButton;
                    int visibility2 = n;
                    if (b2) {
                        visibility2 = 0;
                    }
                    access.401.setVisibility(visibility2);
                    final View access.402 = AbstractTournamentGamesListFragment.this._previousButton;
                    int visibility3;
                    if (AbstractTournamentGamesListFragment.this.getTournamentHolder().hasPreviousRound()) {
                        visibility3 = 0;
                    }
                    else {
                        visibility3 = 4;
                    }
                    access.402.setVisibility(visibility3);
                    final View access.403 = AbstractTournamentGamesListFragment.this._nextButton;
                    int visibility4;
                    if (AbstractTournamentGamesListFragment.this.getTournamentHolder().hasNextRound()) {
                        visibility4 = (b ? 1 : 0);
                    }
                    else {
                        visibility4 = 4;
                    }
                    access.403.setVisibility(visibility4);
                }
            });
        }
    }
    
    @Override
    public void gameInfoChanged(final TournamentGameInfo tournamentGameInfo) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                synchronized (AbstractTournamentGamesListFragment.this._mapIdView) {
                    if (AbstractTournamentGamesListFragment.this._mapIdView.containsKey(tournamentGameInfo.getGameID().getUuid())) {
                        AbstractTournamentGamesListFragment.this._mapIdView.get(tournamentGameInfo.getGameID().getUuid()).setGameInfo(tournamentGameInfo);
                    }
                }
            }
        });
    }
    
    protected abstract ListAdapter getListAdapter();
    
    protected abstract void listItemClicked(final int p0);
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View inflate = layoutInflater.inflate(2131427388, viewGroup, false);
        this._list = (ListView)inflate.findViewById(2131296356);
        this._offsetLayout = new LinearLayout((Context)this.getActivity());
        this._list.addFooterView((View)this._offsetLayout);
        this._list.setOverScrollMode(2);
        this._list.setOnItemClickListener((AdapterView.OnItemClickListener)new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int n, final long n2) {
                AbstractTournamentGamesListFragment.this.listItemClicked(n);
            }
        });
        this._list.setAdapter(this.getListAdapter());
        this._list.setOnScrollListener((AbsListView.OnScrollListener)new AbsListView.OnScrollListener() {
            public void onScroll(final AbsListView absListView, final int n, final int n2, final int n3) {
            }
            
            public void onScrollStateChanged(final AbsListView absListView, final int n) {
                final AbstractTournamentGamesListFragment this.0 = AbstractTournamentGamesListFragment.this;
                boolean b = true;
                if (n != 1) {
                    b = false;
                }
                this.0._scrolling = b;
            }
        });
        (this._navigationViewGroup = inflate.findViewById(2131296355)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
            }
        });
        this._navigationViewGroup.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        this._offsetLayout.addView((View)new LinearLayout((Context)this.getActivity()), -2, this._navigationViewGroup.getMeasuredHeight());
        (this._previousButton = inflate.findViewById(2131296352)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                AbstractTournamentGamesListFragment.this.getTournamentHolder().selectPreviousRound();
            }
        });
        this._previousButton.setOnLongClickListener((View.OnLongClickListener)new View.OnLongClickListener() {
            public boolean onLongClick(final View view) {
                AbstractTournamentGamesListFragment.this.getTournamentHolder().selectFirstRound();
                return true;
            }
        });
        (this._previousFastButton = inflate.findViewById(2131296353)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                AbstractTournamentGamesListFragment.this.getTournamentHolder().selectPreviousMainRound();
            }
        });
        (this._nextButton = inflate.findViewById(2131296350)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                AbstractTournamentGamesListFragment.this.getTournamentHolder().selectNextRound();
            }
        });
        this._nextButton.setOnLongClickListener((View.OnLongClickListener)new View.OnLongClickListener() {
            public boolean onLongClick(final View view) {
                AbstractTournamentGamesListFragment.this.getTournamentHolder().selectLastRound();
                return true;
            }
        });
        (this._nextFastButton = inflate.findViewById(2131296351)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                AbstractTournamentGamesListFragment.this.getTournamentHolder().selectNextMainRound();
            }
        });
        this._roundChoser = (ViewGroup)inflate.findViewById(2131296355);
        this._roundDescription = (TextView)inflate.findViewById(2131296354);
        return inflate;
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
        (this._timer = new Timer()).schedule(new TimerTask() {
            @Override
            public void run() {
                if (!AbstractTournamentGamesListFragment.this._scrolling) {
                    AbstractTournamentGamesListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                        @Override
                        public void run() {
                            final Iterator<TournamentGameInfoView> iterator = AbstractTournamentGamesListFragment.this._mapIdView.values().iterator();
                            while (iterator.hasNext()) {
                                iterator.next().updateRemainingTimes();
                            }
                        }
                    });
                }
            }
        }, 1000L, 1000L);
    }
    
    @Override
    public void onSelectRound(final TournamentRoundInfo tournamentRoundInfo) {
        this.updateSelectedRoundView(tournamentRoundInfo);
        this.allGameInfosChanged();
    }
    
    @Override
    public void onStart() {
        super.onStart();
        this.getTournamentHolder().addTournamentGamesObserver(this);
        final TournamentRoundInfo selectedRound = this.getTournamentHolder().getSelectedRound();
        if (selectedRound != null) {
            this.updateSelectedRoundView(selectedRound);
        }
    }
    
    @Override
    public void onStop() {
        super.onStop();
        this.getTournamentHolder().removeTournamentGamesObserver(this);
    }
    
    protected void openGame(final TournamentGameID tournamentGameID) {
        this.gameSelected(tournamentGameID);
    }
    
    protected void registerGameView(final TournamentGameID tournamentGameID, final TournamentGameInfoView tournamentGameInfoView) {
        this._mapIdView.put(tournamentGameID.getUuid(), tournamentGameInfoView);
    }
    
    @Override
    public void registeredForChangesOn(final Tournament tournament, final TournamentRoundInfo tournamentRoundInfo, final boolean b) {
        this.onSelectRound(tournamentRoundInfo);
        this._navigationViewGroup.post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (b) {
                    final View access.800 = AbstractTournamentGamesListFragment.this._navigationViewGroup;
                    int visibility;
                    if (tournament.getRounds().size() < 2) {
                        visibility = 8;
                    }
                    else {
                        visibility = 0;
                    }
                    access.800.setVisibility(visibility);
                }
            }
        });
    }
    
    protected void showRoundsChoser(final boolean b) {
        final ViewGroup roundChoser = this._roundChoser;
        int visibility;
        if (b) {
            visibility = 0;
        }
        else {
            visibility = 8;
        }
        roundChoser.setVisibility(visibility);
    }
}
