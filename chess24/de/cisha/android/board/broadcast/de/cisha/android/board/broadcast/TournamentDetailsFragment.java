/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.os.Handler
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.Toast
 */
package de.cisha.android.board.broadcast;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.crashlytics.android.Crashlytics;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.AbstractNetworkContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.broadcast.AbstractTournamentGamesListFragment;
import de.cisha.android.board.broadcast.GameSelectedListener;
import de.cisha.android.board.broadcast.TournamentDetailsHolder;
import de.cisha.android.board.broadcast.TournamentGameViewFragment;
import de.cisha.android.board.broadcast.TournamentGamesListFragment;
import de.cisha.android.board.broadcast.TournamentInfoFragment;
import de.cisha.android.board.broadcast.TournamentTeamRoundFragment;
import de.cisha.android.board.broadcast.connection.ActivityLifecycle;
import de.cisha.android.board.broadcast.connection.ConnectionLifecycleManager;
import de.cisha.android.board.broadcast.connection.ConnectionLifecycleManagerFactory;
import de.cisha.android.board.broadcast.connection.IConnection;
import de.cisha.android.board.broadcast.model.ITournamentConnection;
import de.cisha.android.board.broadcast.model.ITournamentService;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentGameID;
import de.cisha.android.board.broadcast.model.TournamentHolder;
import de.cisha.android.board.broadcast.model.TournamentID;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import de.cisha.android.board.broadcast.model.jsonparsers.BroadcastTournamentType;
import de.cisha.android.board.broadcast.standings.TournamentPlayerStandingsFragment;
import de.cisha.android.board.broadcast.standings.TournamentStandingsMultiKnockoutFragment;
import de.cisha.android.board.broadcast.standings.TournamentTeamStandingsFragment;
import de.cisha.android.board.broadcast.statistics.SinglePlayerStatisticFragment;
import de.cisha.android.board.broadcast.statistics.TeamStatisticsFragment;
import de.cisha.android.board.broadcast.video.TournamentVideoFragment;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.ui.patterns.navigationbar.MenuBar;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Set;

public class TournamentDetailsFragment
extends AbstractNetworkContentFragment
implements MenuBar.MenuBarObserver,
GameSelectedListener,
ITournamentConnection.TournamentConnectionListener,
TournamentDetailsHolder,
TournamentDetailsHolder.ContentFragmentSetter,
TournamentHolder.TournamentPlayerSelectionListener,
TournamentHolder.TournamentTeamSelectionListener {
    private static final String SAVE_INSTANCE_SELECTED_MENU_BAR_ITEM = "selectedMenuBarItem";
    public static final String TOURNAMENT_KEY = "TOURNAMENTDETAIL_TOURNAMENTKEY";
    Deque<BackstackEntry> _backStack = new LinkedList<BackstackEntry>();
    private ITournamentConnection _connection;
    private ConnectionLifecycleManager _connectionManager;
    private Fragment _currentShownFragment;
    private boolean _flagReloadTournament;
    private MenuBar _menBar;
    private int _selectedMenuBarItem;
    private TournamentHolder _tournamentHolder = new TournamentHolder();
    private Handler _uiThreadHandler;

    private Fragment createGameListFragment() {
        Crashlytics.setInt("holderObjectIdAtCreateFragment", System.identityHashCode(this._tournamentHolder));
        Crashlytics.setString("CreateFragmentThread", Thread.currentThread().getName());
        if (this._tournamentHolder.hasTournament()) {
            if (.$SwitchMap$de$cisha$android$board$broadcast$model$jsonparsers$BroadcastTournamentType[this._tournamentHolder.getTournament().getType().ordinal()] != 4) {
                return new TournamentGamesListFragment();
            }
            return new TournamentTeamRoundFragment();
        }
        return null;
    }

    private void createTournamentConnection() {
        TournamentID tournamentID = this.getArguments() != null ? new TournamentID(this.getArguments().getString(TOURNAMENT_KEY)) : null;
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
        boolean bl = false;
        boolean bl2 = fragment == null;
        Crashlytics.setBool("createFragment", bl2);
        Crashlytics.setString("threadCreatingFragment", Thread.currentThread().getName());
        Crashlytics.setBool("hasTournamentAtCreationTime", this._tournamentHolder.hasTournament());
        if (bl2) {
            fragment = this.createGameListFragment();
        }
        if (fragment == null) {
            bl = true;
        }
        Crashlytics.setBool("gamesListFragmentIsNull", bl);
        String string = this._currentShownFragment != fragment ? "TournamentGamesList" : null;
        this.setContentFragment(fragment, bl2, "BROADCAST_TOURNAMENT_GAMES_LIST_FRAGMENT", string);
        this._menBar.selectItem((MenuBarItem)this._menBar.findViewById(2131296389));
    }

    private void showMenuItemsForTournament(Tournament tournament) {
        View view;
        if (!tournament.isStandingsEnabled() && (view = this._menBar.findViewById(2131296391)) != null) {
            this._menBar.removeView(view);
        }
        if (!tournament.hasVideos() && (tournament = this._menBar.findViewById(2131296392)) != null) {
            this._menBar.removeView((View)tournament);
        }
    }

    private void showStandings() {
        block6 : {
            Object var6_4;
            Object object;
            Object object2;
            String string;
            boolean bl;
            block7 : {
                if (!this._tournamentHolder.hasTournament()) break block6;
                object = this._tournamentHolder.getTournament();
                this._menBar.selectItem((MenuBarItem)this._menBar.findViewById(2131296391));
                int n = .$SwitchMap$de$cisha$android$board$broadcast$model$jsonparsers$BroadcastTournamentType[object.getType().ordinal()];
                bl = true;
                var6_4 = null;
                switch (n) {
                    default: {
                        bl = false;
                        object2 = object = null;
                        break block7;
                    }
                    case 4: {
                        string = "TournamentTeamStandingsRoundRobin";
                        Fragment fragment = this.getChildFragmentManager().findFragmentByTag("TournamentTeamStandingsRoundRobin");
                        object = string;
                        object2 = fragment;
                        if (fragment != null) break;
                        object2 = new TournamentTeamStandingsFragment();
                        object = string;
                        break block7;
                    }
                    case 2: 
                    case 3: {
                        string = "TournamentPlayerStandings";
                        Fragment fragment = this.getChildFragmentManager().findFragmentByTag("TournamentPlayerStandings");
                        object = string;
                        object2 = fragment;
                        if (fragment != null) break;
                        object2 = new TournamentPlayerStandingsFragment();
                        object = string;
                        break block7;
                    }
                    case 1: {
                        string = "BROADCAST_TOURNAMENT_STANDINGS_FRAGMENT_TAG";
                        Fragment fragment = this.getChildFragmentManager().findFragmentByTag("BROADCAST_TOURNAMENT_STANDINGS_FRAGMENT_TAG");
                        object = string;
                        object2 = fragment;
                        if (fragment != null) break;
                        object2 = new TournamentStandingsMultiKnockoutFragment();
                        object = string;
                        break block7;
                    }
                }
                bl = false;
            }
            string = var6_4;
            if (this._currentShownFragment != object2) {
                string = "TournamentStandings";
            }
            this.setContentFragment((Fragment)object2, bl, (String)object, string);
        }
    }

    @Override
    public void connectionClosed(IConnection iConnection) {
    }

    @Override
    public void connectionEstablished(IConnection iConnection) {
        if (this._flagReloadTournament) {
            this.loadTournament();
        }
        this.hideDialogWaiting();
        this._connection.subscribeToTournament();
    }

    @Override
    public void connectionFailed(IConnection iConnection) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                Toast.makeText((Context)TournamentDetailsFragment.this.getActivity(), (int)2131689598, (int)0).show();
            }
        });
    }

    @Override
    public void gameSelected(TournamentGameID object) {
        if (object != null) {
            String string = null;
            if (this._tournamentHolder.hasTournament()) {
                string = this._tournamentHolder.getTournament().createLinkToShareForGame(object.getUuid());
            }
            object = TournamentGameViewFragment.createFragmentWith(object.getUuid(), string);
            this.getContentPresenter().showFragment((AbstractContentFragment)object, true, true);
        }
    }

    @Override
    public TournamentDetailsHolder.ContentFragmentSetter getContentFragmentSetter() {
        return this;
    }

    @Override
    public String getHeaderText(Resources resources) {
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
    public void menuItemClicked(MenuBarItem object) {
        this._selectedMenuBarItem = object.getId();
        int n = object.getId();
        boolean bl = false;
        boolean bl2 = false;
        Object var6_5 = null;
        object = null;
        switch (n) {
            default: {
                break;
            }
            case 2131296392: {
                Fragment fragment = this.getChildFragmentManager().findFragmentByTag("TournamentVideo");
                if (fragment != this._currentShownFragment) {
                    object = "TournamentVideo";
                }
                if (fragment == null) {
                    bl2 = true;
                }
                if (bl2) {
                    fragment = new TournamentVideoFragment();
                }
                this.setContentFragment(fragment, bl2, "TournamentVideo", (String)object);
                break;
            }
            case 2131296391: {
                this.showStandings();
                break;
            }
            case 2131296390: {
                Fragment fragment = this.getChildFragmentManager().findFragmentByTag("BROADCAST_TOURNAMENT_INFO_FRAGMENT");
                object = var6_5;
                if (fragment != this._currentShownFragment) {
                    object = "TournamentInfo";
                }
                bl2 = bl;
                if (fragment == null) {
                    bl2 = true;
                }
                if (bl2) {
                    fragment = new TournamentInfoFragment();
                }
                this.setContentFragment(fragment, bl2, "BROADCAST_TOURNAMENT_INFO_FRAGMENT", (String)object);
                break;
            }
            case 2131296389: {
                this.showGamesList();
            }
        }
        this._backStack.clear();
    }

    @Override
    public void menuItemLongClicked(MenuBarItem menuBarItem) {
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
            } else {
                if (this._backStack.size() >= 1) {
                    this._backStack.removeLast();
                }
                if (!this._backStack.isEmpty()) {
                    BackstackEntry backstackEntry = this._backStack.getLast();
                    if (this._currentShownFragment instanceof SinglePlayerStatisticFragment) {
                        if (backstackEntry.hasPlayer()) {
                            this._tournamentHolder.setSelectedPlayer(backstackEntry.getPlayer());
                        } else {
                            this._tournamentHolder.setSelectedTeam(backstackEntry.getTeam());
                            this.setContentFragment(new TeamStatisticsFragment(), true, TeamStatisticsFragment.TAG, "TournamentTeam");
                        }
                    } else if (backstackEntry.hasTeam()) {
                        this._tournamentHolder.setSelectedTeam(backstackEntry.getTeam());
                    } else {
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
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this._connectionManager = ConnectionLifecycleManagerFactory.createManagerForFragment(this, ActivityLifecycle.CREATE_DESTROY, 5000);
        this._uiThreadHandler = new Handler();
        this._flagReloadTournament = true;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int n;
        layoutInflater = layoutInflater.inflate(2131427569, viewGroup, false);
        this._menBar = (MenuBar)layoutInflater.findViewById(2131296388);
        if (this._tournamentHolder.hasTournament()) {
            this.showMenuItemsForTournament(this._tournamentHolder.getTournament());
        }
        this._menBar.setObserver(this);
        if (bundle != null && (n = bundle.getInt(SAVE_INSTANCE_SELECTED_MENU_BAR_ITEM)) > 0) {
            this.menuItemClicked((MenuBarItem)layoutInflater.findViewById(n));
        }
        return layoutInflater;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(SAVE_INSTANCE_SELECTED_MENU_BAR_ITEM, this._selectedMenuBarItem);
    }

    @Override
    public void onStart() {
        super.onStart();
        this._uiThreadHandler.removeCallbacksAndMessages(null);
        this.createTournamentConnection();
        this.getTournamentHolder().addPlayerSelectionListener(this);
        this.getTournamentHolder().addTeamSelectionListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        this._uiThreadHandler.postDelayed(new Runnable(){

            @Override
            public void run() {
                TournamentDetailsFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        TournamentDetailsFragment.this._connectionManager.removeConnection(TournamentDetailsFragment.this._connection, true);
                        TournamentDetailsFragment.this._flagReloadTournament = true;
                    }
                });
            }

        }, 300000L);
        this.getTournamentHolder().removePlayerSelectionListener(this);
        this.getTournamentHolder().removeTeamSelectionListener(this);
    }

    @Override
    public void selectedPlayerChanged(TournamentPlayer tournamentPlayer) {
        this._backStack.addLast(new BackstackEntry(tournamentPlayer));
    }

    @Override
    public void selectedTeamChanged(TournamentTeam tournamentTeam) {
        this._backStack.addLast(new BackstackEntry(tournamentTeam));
    }

    @Override
    public void setContentFragment(Fragment fragment, boolean bl, String string, String string2) {
        if (this._currentShownFragment == fragment) {
            return;
        }
        FragmentTransaction fragmentTransaction = this.getChildFragmentManager().beginTransaction();
        if (fragmentTransaction != null) {
            if (this._currentShownFragment != null) {
                fragmentTransaction.hide(this._currentShownFragment);
                Crashlytics.setString("shownFragment", this._currentShownFragment.getTag());
            }
            if (bl) {
                try {
                    fragmentTransaction.add(2131296387, fragment, string);
                }
                catch (Exception exception) {
                    Object object = this._tournamentHolder.hasTournament();
                    Crashlytics.setBool("hasTournament", object.booleanValue());
                    Crashlytics.setInt("menuBarItem", this._selectedMenuBarItem);
                    Crashlytics.setString("ThreadSettingFragment", Thread.currentThread().getName());
                    if (object.booleanValue()) {
                        object = this._tournamentHolder.getTournament();
                        Crashlytics.setString("name", object.getName());
                        Crashlytics.setString("description", object.getDescription());
                        Crashlytics.setString("type", object.getType().toString());
                    }
                    Crashlytics.logException(exception);
                }
            } else {
                fragmentTransaction.show(fragment);
            }
            fragmentTransaction.commitAllowingStateLoss();
            this._currentShownFragment = fragment;
            if (string2 != null && !string2.isEmpty()) {
                ServiceProvider.getInstance().getTrackingService().trackScreenOpen(string2);
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
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                boolean bl = TournamentDetailsFragment.this._tournamentHolder.hasTournament();
                TournamentDetailsFragment.this._tournamentHolder.setTournament(tournament);
                TournamentDetailsFragment.this.showMenuItemsForTournament(tournament);
                if (bl ^ true) {
                    View view = TournamentDetailsFragment.this._menBar.findViewById(2131296389);
                    view.setSelected(true);
                    TournamentDetailsFragment.this.menuItemClicked((MenuBarItem)view);
                }
            }
        });
        this.hideDialogWaiting();
    }

    @Override
    public void tournamentLoadingFailed(final APIStatusCode aPIStatusCode) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                TournamentDetailsFragment.this.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

                    @Override
                    public void performReload() {
                        TournamentDetailsFragment.this.loadTournament();
                    }
                }, true);
            }

        });
    }

    private class BackstackEntry {
        private TournamentPlayer _player;
        private TournamentTeam _team;

        public BackstackEntry(TournamentPlayer tournamentPlayer) {
            this._player = tournamentPlayer;
        }

        public BackstackEntry(TournamentTeam tournamentTeam) {
            this._team = tournamentTeam;
        }

        private boolean hasPlayer() {
            if (this._player != null) {
                return true;
            }
            return false;
        }

        private boolean hasTeam() {
            if (this._team != null) {
                return true;
            }
            return false;
        }

        public TournamentPlayer getPlayer() {
            return this._player;
        }

        public TournamentTeam getTeam() {
            return this._team;
        }
    }

}
