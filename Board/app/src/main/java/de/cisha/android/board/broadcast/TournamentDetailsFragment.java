// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast;

import android.support.v4.app.FragmentTransaction;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import de.cisha.android.board.broadcast.connection.ConnectionLifecycleManagerFactory;
import de.cisha.android.board.broadcast.connection.ActivityLifecycle;
import android.os.Bundle;
import de.cisha.android.board.broadcast.statistics.TeamStatisticsFragment;
import de.cisha.android.board.broadcast.statistics.SinglePlayerStatisticFragment;
import de.cisha.android.board.broadcast.video.TournamentVideoFragment;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import java.util.Set;
import de.cisha.android.board.mainmenu.view.MenuItem;
import android.content.res.Resources;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.broadcast.model.TournamentGameID;
import android.content.Context;
import android.widget.Toast;
import de.cisha.android.board.broadcast.standings.TournamentStandingsMultiKnockoutFragment;
import de.cisha.android.board.broadcast.standings.TournamentPlayerStandingsFragment;
import de.cisha.android.board.broadcast.standings.TournamentTeamStandingsFragment;
import android.view.View;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import de.cisha.android.board.broadcast.connection.IConnection;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.broadcast.model.TournamentID;
import com.crashlytics.android.Crashlytics;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.broadcast.model.Tournament;
import java.util.LinkedList;
import android.os.Handler;
import android.support.v4.app.Fragment;
import de.cisha.android.board.broadcast.connection.ConnectionLifecycleManager;
import java.util.Deque;
import de.cisha.android.board.broadcast.model.TournamentHolder;
import de.cisha.android.board.broadcast.model.ITournamentConnection;
import de.cisha.android.ui.patterns.navigationbar.MenuBar;
import de.cisha.android.board.AbstractNetworkContentFragment;

public class TournamentDetailsFragment extends AbstractNetworkContentFragment implements MenuBarObserver, GameSelectedListener, TournamentConnectionListener, TournamentDetailsHolder, ContentFragmentSetter, TournamentPlayerSelectionListener, TournamentTeamSelectionListener
{
    private static final String SAVE_INSTANCE_SELECTED_MENU_BAR_ITEM = "selectedMenuBarItem";
    public static final String TOURNAMENT_KEY = "TOURNAMENTDETAIL_TOURNAMENTKEY";
    Deque<BackstackEntry> _backStack;
    private ITournamentConnection _connection;
    private ConnectionLifecycleManager _connectionManager;
    private Fragment _currentShownFragment;
    private boolean _flagReloadTournament;
    private MenuBar _menBar;
    private int _selectedMenuBarItem;
    private TournamentHolder _tournamentHolder;
    private Handler _uiThreadHandler;
    
    public TournamentDetailsFragment() {
        this._tournamentHolder = new TournamentHolder();
        this._backStack = new LinkedList<BackstackEntry>();
    }
    
    private Fragment createGameListFragment() {
        Crashlytics.setInt("holderObjectIdAtCreateFragment", System.identityHashCode(this._tournamentHolder));
        Crashlytics.setString("CreateFragmentThread", Thread.currentThread().getName());
        if (!this._tournamentHolder.hasTournament()) {
            return null;
        }
        if (TournamentDetailsFragment.5..SwitchMap.de.cisha.android.board.broadcast.model.jsonparsers.BroadcastTournamentType[this._tournamentHolder.getTournament().getType().ordinal()] != 4) {
            return new TournamentGamesListFragment();
        }
        return new TournamentTeamRoundFragment();
    }
    
    private void createTournamentConnection() {
        TournamentID tournamentID;
        if (this.getArguments() != null) {
            tournamentID = new TournamentID(this.getArguments().getString("TOURNAMENTDETAIL_TOURNAMENTKEY"));
        }
        else {
            tournamentID = null;
        }
        if (this._connection == null && tournamentID != null) {
            this._connection = ServiceProvider.getInstance().getTournamentService().getTournamentConnection(tournamentID, this, this.getTournamentHolder());
            ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.BROADCAST, "Tournament selected", tournamentID.getUuid());
        }
        if (this._connection != null) {
            if (!this._connection.isConnected()) {
                this.showDialogWaiting(true, true, "loading tournament...", null);
            }
            this._connectionManager.addConnection(this._connection);
        }
    }
    
    private void loadTournament() {
        this._connection.loadTournament();
    }
    
    private void showGamesList() {
        Fragment fragment = this.getChildFragmentManager().findFragmentByTag("BROADCAST_TOURNAMENT_GAMES_LIST_FRAGMENT");
        boolean b = false;
        final boolean b2 = fragment == null;
        Crashlytics.setBool("createFragment", b2);
        Crashlytics.setString("threadCreatingFragment", Thread.currentThread().getName());
        Crashlytics.setBool("hasTournamentAtCreationTime", this._tournamentHolder.hasTournament());
        if (b2) {
            fragment = this.createGameListFragment();
        }
        if (fragment == null) {
            b = true;
        }
        Crashlytics.setBool("gamesListFragmentIsNull", b);
        String s;
        if (this._currentShownFragment != fragment) {
            s = "TournamentGamesList";
        }
        else {
            s = null;
        }
        this.setContentFragment(fragment, b2, "BROADCAST_TOURNAMENT_GAMES_LIST_FRAGMENT", s);
        this._menBar.selectItem((MenuBarItem)this._menBar.findViewById(2131296389));
    }
    
    private void showMenuItemsForTournament(final Tournament tournament) {
        if (!tournament.isStandingsEnabled()) {
            final View viewById = this._menBar.findViewById(2131296391);
            if (viewById != null) {
                this._menBar.removeView(viewById);
            }
        }
        if (!tournament.hasVideos()) {
            final View viewById2 = this._menBar.findViewById(2131296392);
            if (viewById2 != null) {
                this._menBar.removeView(viewById2);
            }
        }
    }
    
    private void showStandings() {
        if (this._tournamentHolder.hasTournament()) {
            final Tournament tournament = this._tournamentHolder.getTournament();
            this._menBar.selectItem((MenuBarItem)this._menBar.findViewById(2131296391));
            final int n = TournamentDetailsFragment.5..SwitchMap.de.cisha.android.board.broadcast.model.jsonparsers.BroadcastTournamentType[tournament.getType().ordinal()];
            boolean b = true;
            final String s = null;
            Fragment fragment = null;
            Object o = null;
            Label_0232: {
                switch (n) {
                    default: {
                        b = false;
                        o = (fragment = null);
                        break Label_0232;
                    }
                    case 4: {
                        final String s2 = "TournamentTeamStandingsRoundRobin";
                        final Fragment fragmentByTag = this.getChildFragmentManager().findFragmentByTag("TournamentTeamStandingsRoundRobin");
                        o = s2;
                        fragment = fragmentByTag;
                        if (fragmentByTag == null) {
                            fragment = new TournamentTeamStandingsFragment();
                            o = s2;
                            break Label_0232;
                        }
                        break;
                    }
                    case 2:
                    case 3: {
                        final String s3 = "TournamentPlayerStandings";
                        final Fragment fragmentByTag2 = this.getChildFragmentManager().findFragmentByTag("TournamentPlayerStandings");
                        o = s3;
                        fragment = fragmentByTag2;
                        if (fragmentByTag2 == null) {
                            fragment = new TournamentPlayerStandingsFragment();
                            o = s3;
                            break Label_0232;
                        }
                        break;
                    }
                    case 1: {
                        final String s4 = "BROADCAST_TOURNAMENT_STANDINGS_FRAGMENT_TAG";
                        final Fragment fragmentByTag3 = this.getChildFragmentManager().findFragmentByTag("BROADCAST_TOURNAMENT_STANDINGS_FRAGMENT_TAG");
                        o = s4;
                        fragment = fragmentByTag3;
                        if (fragmentByTag3 == null) {
                            fragment = new TournamentStandingsMultiKnockoutFragment();
                            o = s4;
                            break Label_0232;
                        }
                        break;
                    }
                }
                b = false;
            }
            String s5 = s;
            if (this._currentShownFragment != fragment) {
                s5 = "TournamentStandings";
            }
            this.setContentFragment(fragment, b, (String)o, s5);
        }
    }
    
    @Override
    public void connectionClosed(final IConnection connection) {
    }
    
    @Override
    public void connectionEstablished(final IConnection connection) {
        if (this._flagReloadTournament) {
            this.loadTournament();
        }
        this.hideDialogWaiting();
        this._connection.subscribeToTournament();
    }
    
    @Override
    public void connectionFailed(final IConnection connection) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                Toast.makeText((Context)TournamentDetailsFragment.this.getActivity(), 2131689598, 0).show();
            }
        });
    }
    
    @Override
    public void gameSelected(final TournamentGameID tournamentGameID) {
        if (tournamentGameID != null) {
            String linkToShareForGame = null;
            if (this._tournamentHolder.hasTournament()) {
                linkToShareForGame = this._tournamentHolder.getTournament().createLinkToShareForGame(tournamentGameID.getUuid());
            }
            this.getContentPresenter().showFragment(TournamentGameViewFragment.createFragmentWith(tournamentGameID.getUuid(), linkToShareForGame), true, true);
        }
    }
    
    @Override
    public ContentFragmentSetter getContentFragmentSetter() {
        return this;
    }
    
    @Override
    public String getHeaderText(final Resources resources) {
        return resources.getString(2131690386);
    }
    
    @Override
    public MenuItem getHighlightedMenuItem() {
        return MenuItem.CHESSTV;
    }
    
    @Override
    public Set<SettingsMenuCategory> getSettingsMenuCategories() {
        return null;
    }
    
    @Override
    public TournamentHolder getTournamentHolder() {
        return this._tournamentHolder;
    }
    
    @Override
    public String getTrackingName() {
        return "TournamentDetails";
    }
    
    @Override
    public boolean isGrabMenuEnabled() {
        return true;
    }
    
    @Override
    public void menuItemClicked(final MenuBarItem menuBarItem) {
        this._selectedMenuBarItem = menuBarItem.getId();
        final int id = menuBarItem.getId();
        final boolean b = false;
        boolean b2 = false;
        final String s = null;
        String s2 = null;
        switch (id) {
            case 2131296392: {
                Fragment fragmentByTag = this.getChildFragmentManager().findFragmentByTag("TournamentVideo");
                if (fragmentByTag != this._currentShownFragment) {
                    s2 = "TournamentVideo";
                }
                if (fragmentByTag == null) {
                    b2 = true;
                }
                if (b2) {
                    fragmentByTag = new TournamentVideoFragment();
                }
                this.setContentFragment(fragmentByTag, b2, "TournamentVideo", s2);
                break;
            }
            case 2131296391: {
                this.showStandings();
                break;
            }
            case 2131296390: {
                Fragment fragmentByTag2 = this.getChildFragmentManager().findFragmentByTag("BROADCAST_TOURNAMENT_INFO_FRAGMENT");
                String s3 = s;
                if (fragmentByTag2 != this._currentShownFragment) {
                    s3 = "TournamentInfo";
                }
                boolean b3 = b;
                if (fragmentByTag2 == null) {
                    b3 = true;
                }
                if (b3) {
                    fragmentByTag2 = new TournamentInfoFragment();
                }
                this.setContentFragment(fragmentByTag2, b3, "BROADCAST_TOURNAMENT_INFO_FRAGMENT", s3);
                break;
            }
            case 2131296389: {
                this.showGamesList();
                break;
            }
        }
        this._backStack.clear();
    }
    
    @Override
    public void menuItemLongClicked(final MenuBarItem menuBarItem) {
    }
    
    @Override
    protected boolean needAuthToken() {
        return false;
    }
    
    @Override
    protected boolean needNetwork() {
        return true;
    }
    
    @Override
    protected boolean needRegisteredUser() {
        return false;
    }
    
    @Override
    public boolean onBackPressed() {
        if (this._currentShownFragment != null) {
            if (!(this._currentShownFragment instanceof SinglePlayerStatisticFragment) && !(this._currentShownFragment instanceof TeamStatisticsFragment)) {
                if (!(this._currentShownFragment instanceof AbstractTournamentGamesListFragment)) {
                    this.showGamesList();
                    return true;
                }
            }
            else {
                if (this._backStack.size() >= 1) {
                    this._backStack.removeLast();
                }
                if (!this._backStack.isEmpty()) {
                    final BackstackEntry backstackEntry = this._backStack.getLast();
                    if (this._currentShownFragment instanceof SinglePlayerStatisticFragment) {
                        if (backstackEntry.hasPlayer()) {
                            this._tournamentHolder.setSelectedPlayer(backstackEntry.getPlayer());
                        }
                        else {
                            this._tournamentHolder.setSelectedTeam(backstackEntry.getTeam());
                            this.setContentFragment(new TeamStatisticsFragment(), true, TeamStatisticsFragment.TAG, "TournamentTeam");
                        }
                    }
                    else if (backstackEntry.hasTeam()) {
                        this._tournamentHolder.setSelectedTeam(backstackEntry.getTeam());
                    }
                    else {
                        this._tournamentHolder.setSelectedPlayer(backstackEntry.getPlayer());
                        this.setContentFragment(new SinglePlayerStatisticFragment(), true, "SinglePlayerStatisticFragment", "TournamentPlayer");
                    }
                    this._backStack.removeLast();
                    return true;
                }
                this.showStandings();
                return true;
            }
        }
        return super.onBackPressed();
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this._connectionManager = ConnectionLifecycleManagerFactory.createManagerForFragment(this, ActivityLifecycle.CREATE_DESTROY, 5000);
        this._uiThreadHandler = new Handler();
        this._flagReloadTournament = true;
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View inflate = layoutInflater.inflate(2131427569, viewGroup, false);
        this._menBar = (MenuBar)inflate.findViewById(2131296388);
        if (this._tournamentHolder.hasTournament()) {
            this.showMenuItemsForTournament(this._tournamentHolder.getTournament());
        }
        this._menBar.setObserver((MenuBar.MenuBarObserver)this);
        if (bundle != null) {
            final int int1 = bundle.getInt("selectedMenuBarItem");
            if (int1 > 0) {
                this.menuItemClicked((MenuBarItem)inflate.findViewById(int1));
            }
        }
        return inflate;
    }
    
    @Override
    public void onSaveInstanceState(final Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("selectedMenuBarItem", this._selectedMenuBarItem);
    }
    
    @Override
    public void onStart() {
        super.onStart();
        this._uiThreadHandler.removeCallbacksAndMessages((Object)null);
        this.createTournamentConnection();
        this.getTournamentHolder().addPlayerSelectionListener((TournamentHolder.TournamentPlayerSelectionListener)this);
        this.getTournamentHolder().addTeamSelectionListener((TournamentHolder.TournamentTeamSelectionListener)this);
    }
    
    @Override
    public void onStop() {
        super.onStop();
        this._uiThreadHandler.postDelayed((Runnable)new Runnable() {
            @Override
            public void run() {
                TournamentDetailsFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        TournamentDetailsFragment.this._connectionManager.removeConnection(TournamentDetailsFragment.this._connection, true);
                        TournamentDetailsFragment.this._flagReloadTournament = true;
                    }
                });
            }
        }, 300000L);
        this.getTournamentHolder().removePlayerSelectionListener((TournamentHolder.TournamentPlayerSelectionListener)this);
        this.getTournamentHolder().removeTeamSelectionListener((TournamentHolder.TournamentTeamSelectionListener)this);
    }
    
    @Override
    public void selectedPlayerChanged(final TournamentPlayer tournamentPlayer) {
        this._backStack.addLast(new BackstackEntry(tournamentPlayer));
    }
    
    @Override
    public void selectedTeamChanged(final TournamentTeam tournamentTeam) {
        this._backStack.addLast(new BackstackEntry(tournamentTeam));
    }
    
    @Override
    public void setContentFragment(final Fragment currentShownFragment, final boolean b, final String s, final String s2) {
        if (this._currentShownFragment == currentShownFragment) {
            return;
        }
        final FragmentTransaction beginTransaction = this.getChildFragmentManager().beginTransaction();
        if (beginTransaction != null) {
            if (this._currentShownFragment != null) {
                beginTransaction.hide(this._currentShownFragment);
                Crashlytics.setString("shownFragment", this._currentShownFragment.getTag());
            }
            if (b) {
                try {
                    beginTransaction.add(2131296387, currentShownFragment, s);
                }
                catch (Exception ex) {
                    final Boolean value = this._tournamentHolder.hasTournament();
                    Crashlytics.setBool("hasTournament", value);
                    Crashlytics.setInt("menuBarItem", this._selectedMenuBarItem);
                    Crashlytics.setString("ThreadSettingFragment", Thread.currentThread().getName());
                    if (value) {
                        final Tournament tournament = this._tournamentHolder.getTournament();
                        Crashlytics.setString("name", tournament.getName());
                        Crashlytics.setString("description", tournament.getDescription());
                        Crashlytics.setString("type", tournament.getType().toString());
                    }
                    Crashlytics.logException(ex);
                }
            }
            else {
                beginTransaction.show(currentShownFragment);
            }
            beginTransaction.commitAllowingStateLoss();
            this._currentShownFragment = currentShownFragment;
            if (s2 != null && !s2.isEmpty()) {
                ServiceProvider.getInstance().getTrackingService().trackScreenOpen(s2);
            }
        }
    }
    
    @Override
    public boolean showMenu() {
        return false;
    }
    
    @Override
    public void tournamentLoaded(final Tournament tournament) {
        this._flagReloadTournament = false;
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                final boolean hasTournament = TournamentDetailsFragment.this._tournamentHolder.hasTournament();
                TournamentDetailsFragment.this._tournamentHolder.setTournament(tournament);
                TournamentDetailsFragment.this.showMenuItemsForTournament(tournament);
                if (hasTournament ^ true) {
                    final View viewById = TournamentDetailsFragment.this._menBar.findViewById(2131296389);
                    viewById.setSelected(true);
                    TournamentDetailsFragment.this.menuItemClicked((MenuBarItem)viewById);
                }
            }
        });
        this.hideDialogWaiting();
    }
    
    @Override
    public void tournamentLoadingFailed(final APIStatusCode apiStatusCode) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                TournamentDetailsFragment.this.showViewForErrorCode(apiStatusCode, new IErrorPresenter.IReloadAction() {
                    @Override
                    public void performReload() {
                        TournamentDetailsFragment.this.loadTournament();
                    }
                }, true);
            }
        });
    }
    
    private class BackstackEntry
    {
        private TournamentPlayer _player;
        private TournamentTeam _team;
        
        public BackstackEntry(final TournamentPlayer player) {
            this._player = player;
        }
        
        public BackstackEntry(final TournamentTeam team) {
            this._team = team;
        }
        
        private boolean hasPlayer() {
            return this._player != null;
        }
        
        private boolean hasTeam() {
            return this._team != null;
        }
        
        public TournamentPlayer getPlayer() {
            return this._player;
        }
        
        public TournamentTeam getTeam() {
            return this._team;
        }
    }
}
