/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ListAdapter
 *  org.json.JSONObject
 */
package de.cisha.android.board.broadcast;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.broadcast.actions.ShowTournamentDetailsAction;
import de.cisha.android.board.broadcast.model.ITournamentListService;
import de.cisha.android.board.broadcast.model.TournamentInfo;
import de.cisha.android.board.broadcast.model.TournamentState;
import de.cisha.android.board.broadcast.view.TournamentInfoView;
import de.cisha.android.board.broadcast.view.TournamentListHeaderView;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.ui.list.ObservableRefreshingListView;
import de.cisha.android.ui.list.SectionedListAdapter;
import de.cisha.android.ui.list.UpdatingList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

public class TournamentListFragment
extends AbstractContentFragment
implements UpdatingList.UpdatingListListener,
AdapterView.OnItemClickListener {
    private final Map<TournamentState, List<TournamentInfo>> _infosByState = new HashMap<TournamentState, List<TournamentInfo>>();
    private boolean _initialized = false;
    private TournamentListAdapter _listAdapter;
    private final List<TournamentState> _orderOfStatesToShow = new LinkedList<TournamentState>();
    private UpdatingList _tournamentList;

    private void addAllTournamentInfos(Collection<TournamentInfo> collection, boolean bl) {
        List<TournamentInfo> list;
        if (bl) {
            list = this._infosByState.values().iterator();
            while (list.hasNext()) {
                list.next().clear();
            }
        }
        for (TournamentInfo tournamentInfo : collection) {
            TournamentState tournamentState = tournamentInfo.getState();
            list = this._infosByState.get((Object)tournamentState);
            collection = list;
            if (list == null) {
                collection = new LinkedList<TournamentInfo>();
                this._infosByState.put(tournamentState, (List<TournamentInfo>)collection);
            }
            collection.add(tournamentInfo);
        }
        collection = this._infosByState.remove((Object)TournamentState.PAUSED);
        if (collection != null) {
            list = this._infosByState.get((Object)TournamentState.ONGOING);
            if (list != null) {
                list.addAll(collection);
            } else {
                this._infosByState.put(TournamentState.ONGOING, (List<TournamentInfo>)collection);
            }
        }
        this._listAdapter.notifyDataSetChanged();
    }

    private void loadTournaments() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                TournamentListFragment.this._initialized = true;
                TournamentListFragment.this.showDialogWaiting(false, true, "", null);
                TournamentListFragment.this._tournamentList.enableFooter();
                TournamentListFragment.this._tournamentList.updateStarted();
                ITournamentListService iTournamentListService = ServiceProvider.getInstance().getTournamentListService();
                int n = TournamentListFragment.this._listAdapter != null && TournamentListFragment.this._listAdapter.getCountOfValues() > 3 ? TournamentListFragment.this._listAdapter.getCountOfValues() : 20;
                iTournamentListService.getTournaments(0, n, (LoadCommandCallback<List<TournamentInfo>>)new LoadCommandCallbackWithTimeout<List<TournamentInfo>>(){

                    @Override
                    public void failed(final APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                        TournamentListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                            @Override
                            public void run() {
                                TournamentListFragment.this.hideDialogWaiting();
                                TournamentListFragment.this._tournamentList.updateFinished();
                                IErrorPresenter iErrorPresenter = TournamentListFragment.this.getErrorHandler();
                                if (iErrorPresenter != null) {
                                    iErrorPresenter.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

                                        @Override
                                        public void performReload() {
                                            TournamentListFragment.this.loadTournaments();
                                        }
                                    }, true);
                                }
                            }

                        });
                    }

                    @Override
                    public void succeded(final List<TournamentInfo> list) {
                        TournamentListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                            @Override
                            public void run() {
                                TournamentListFragment.this.hideDialogWaiting();
                                TournamentListFragment.this.addAllTournamentInfos(list, true);
                                TournamentListFragment.this._tournamentList.updateFinished();
                                TournamentListFragment.this._listAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                });
            }

        });
    }

    @Override
    public void footerReached() {
        if (this._initialized) {
            this._tournamentList.updateStarted();
            ServiceProvider.getInstance().getTournamentListService().getTournaments(this._listAdapter.getCountOfValues(), 10, (LoadCommandCallback<List<TournamentInfo>>)new LoadCommandCallbackWithTimeout<List<TournamentInfo>>(){

                @Override
                public void failed(final APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                    TournamentListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                        @Override
                        public void run() {
                            TournamentListFragment.this._tournamentList.updateFinished();
                            IErrorPresenter iErrorPresenter = TournamentListFragment.this.getErrorHandler();
                            if (iErrorPresenter != null) {
                                iErrorPresenter.showViewForErrorCode(aPIStatusCode, null);
                            }
                        }
                    });
                }

                @Override
                public void succeded(final List<TournamentInfo> list) {
                    TournamentListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                        @Override
                        public void run() {
                            TournamentListFragment.this._tournamentList.updateFinished();
                            if (list == null || list.size() == 0) {
                                TournamentListFragment.this._tournamentList.disableFooter();
                            }
                            TournamentListFragment.this.addAllTournamentInfos(list, false);
                        }
                    });
                }

            });
        }
    }

    @Override
    public String getHeaderText(Resources resources) {
        return resources.getString(2131690387);
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
    public String getTrackingName() {
        return "TournamentList";
    }

    @Override
    public void headerReached() {
        if (this._initialized) {
            this.loadTournaments();
        }
    }

    @Override
    public boolean isGrabMenuEnabled() {
        return true;
    }

    @Override
    public void onCreate(Bundle arrtournamentState) {
        super.onCreate((Bundle)arrtournamentState);
        for (TournamentState tournamentState : TournamentState.values()) {
            this._infosByState.put(tournamentState, new ArrayList());
        }
        this._orderOfStatesToShow.add(TournamentState.ONGOING);
        this._orderOfStatesToShow.add(TournamentState.PAUSED);
        this._orderOfStatesToShow.add(TournamentState.UPCOMING);
        this._orderOfStatesToShow.add(TournamentState.FINISHED);
        this.loadTournaments();
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup object, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2131427370, (ViewGroup)object, false);
        object = (ObservableRefreshingListView)layoutInflater.findViewById(2131297183);
        this._tournamentList = object;
        this._listAdapter = new TournamentListAdapter(this._infosByState, this._orderOfStatesToShow);
        this._listAdapter.setHidesEmptySections(true);
        object.setAdapter((ListAdapter)this._listAdapter);
        this._tournamentList.setListScrollListener(this);
        object.setOnItemClickListener(this);
        this._tournamentList.enableFooter();
        return layoutInflater;
    }

    public void onItemClick(AdapterView<?> object, View view, int n, long l) {
        object = (TournamentInfo)this._listAdapter.getValueForPosition(n);
        if (object != null) {
            new ShowTournamentDetailsAction(this.getContentPresenter(), (TournamentInfo)object).perform();
        }
    }

    @Override
    public boolean showMenu() {
        return true;
    }

    private class TournamentListAdapter
    extends SectionedListAdapter<TournamentState, TournamentInfo> {
        public TournamentListAdapter(Map<TournamentState, List<TournamentInfo>> map, List<TournamentState> list) {
            super(map, list);
        }

        @Override
        protected View getViewForSectionHeader(TournamentState tournamentState, View object, ViewGroup object2) {
            object2 = object;
            if (object == null) {
                object2 = new TournamentListHeaderView((Context)TournamentListFragment.this.getActivity());
            }
            object = (TournamentListHeaderView)((Object)object2);
            object.setTitle(TournamentListFragment.this.getText(tournamentState.getNameResourceId()));
            return object;
        }

        @Override
        protected View getViewForValue(TournamentInfo tournamentInfo, View object, ViewGroup object2) {
            object2 = object2 != null ? object2.getContext() : TournamentListFragment.this.getActivity();
            Object object3 = object;
            if (object == null) {
                object3 = new TournamentInfoView((Context)object2);
            }
            object = (TournamentInfoView)((Object)object3);
            object.setTournamentInfo(tournamentInfo);
            return object;
        }
    }

}
