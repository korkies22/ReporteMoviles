// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast;

import de.cisha.android.board.broadcast.view.TournamentInfoView;
import android.content.Context;
import de.cisha.android.board.broadcast.view.TournamentListHeaderView;
import de.cisha.android.ui.list.SectionedListAdapter;
import de.cisha.android.board.broadcast.actions.ShowTournamentDetailsAction;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import de.cisha.android.ui.list.ObservableRefreshingListView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import java.util.ArrayList;
import android.os.Bundle;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import java.util.Set;
import de.cisha.android.board.mainmenu.view.MenuItem;
import android.content.res.Resources;
import de.cisha.android.board.broadcast.model.ITournamentListService;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.ServiceProvider;
import java.util.Iterator;
import java.util.Collection;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import java.util.LinkedList;
import java.util.HashMap;
import de.cisha.android.board.broadcast.model.TournamentInfo;
import java.util.List;
import de.cisha.android.board.broadcast.model.TournamentState;
import java.util.Map;
import android.widget.AdapterView.OnItemClickListener;
import de.cisha.android.ui.list.UpdatingList;
import de.cisha.android.board.AbstractContentFragment;

public class TournamentListFragment extends AbstractContentFragment implements UpdatingListListener, AdapterView.OnItemClickListener
{
    private final Map<TournamentState, List<TournamentInfo>> _infosByState;
    private boolean _initialized;
    private TournamentListAdapter _listAdapter;
    private final List<TournamentState> _orderOfStatesToShow;
    private UpdatingList _tournamentList;
    
    public TournamentListFragment() {
        this._infosByState = new HashMap<TournamentState, List<TournamentInfo>>();
        this._initialized = false;
        this._orderOfStatesToShow = new LinkedList<TournamentState>();
    }
    
    private void addAllTournamentInfos(final Collection<TournamentInfo> collection, final boolean b) {
        if (b) {
            final Iterator<List<TournamentInfo>> iterator = this._infosByState.values().iterator();
            while (iterator.hasNext()) {
                iterator.next().clear();
            }
        }
        for (final TournamentInfo tournamentInfo : collection) {
            final TournamentState state = tournamentInfo.getState();
            List<TournamentInfo> list;
            if ((list = this._infosByState.get(state)) == null) {
                list = new LinkedList<TournamentInfo>();
                this._infosByState.put(state, list);
            }
            list.add(tournamentInfo);
        }
        final List<TournamentInfo> list2 = this._infosByState.remove(TournamentState.PAUSED);
        if (list2 != null) {
            final List<TournamentInfo> list3 = this._infosByState.get(TournamentState.ONGOING);
            if (list3 != null) {
                list3.addAll(list2);
            }
            else {
                this._infosByState.put(TournamentState.ONGOING, list2);
            }
        }
        this._listAdapter.notifyDataSetChanged();
    }
    
    private void loadTournaments() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                TournamentListFragment.this._initialized = true;
                TournamentListFragment.this.showDialogWaiting(false, true, "", null);
                TournamentListFragment.this._tournamentList.enableFooter();
                TournamentListFragment.this._tournamentList.updateStarted();
                final ITournamentListService tournamentListService = ServiceProvider.getInstance().getTournamentListService();
                int countOfValues;
                if (TournamentListFragment.this._listAdapter != null && TournamentListFragment.this._listAdapter.getCountOfValues() > 3) {
                    countOfValues = TournamentListFragment.this._listAdapter.getCountOfValues();
                }
                else {
                    countOfValues = 20;
                }
                tournamentListService.getTournaments(0, countOfValues, new LoadCommandCallbackWithTimeout<List<TournamentInfo>>() {
                    public void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                        TournamentListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                            @Override
                            public void run() {
                                TournamentListFragment.this.hideDialogWaiting();
                                TournamentListFragment.this._tournamentList.updateFinished();
                                final IErrorPresenter access.600 = TournamentListFragment.this.getErrorHandler();
                                if (access.600 != null) {
                                    access.600.showViewForErrorCode(apiStatusCode, (IErrorPresenter.IReloadAction)new IErrorPresenter.IReloadAction() {
                                        @Override
                                        public void performReload() {
                                            TournamentListFragment.this.loadTournaments();
                                        }
                                    }, true);
                                }
                            }
                        });
                    }
                    
                    public void succeded(final List<TournamentInfo> list) {
                        TournamentListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
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
            ServiceProvider.getInstance().getTournamentListService().getTournaments(this._listAdapter.getCountOfValues(), 10, new LoadCommandCallbackWithTimeout<List<TournamentInfo>>() {
                public void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                    TournamentListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                        @Override
                        public void run() {
                            TournamentListFragment.this._tournamentList.updateFinished();
                            final IErrorPresenter access.1000 = TournamentListFragment.this.getErrorHandler();
                            if (access.1000 != null) {
                                access.1000.showViewForErrorCode(apiStatusCode, null);
                            }
                        }
                    });
                }
                
                public void succeded(final List<TournamentInfo> list) {
                    TournamentListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
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
    
    public String getHeaderText(final Resources resources) {
        return resources.getString(2131690387);
    }
    
    public MenuItem getHighlightedMenuItem() {
        return MenuItem.CHESSTV;
    }
    
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
    
    public boolean isGrabMenuEnabled() {
        return true;
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        final TournamentState[] values = TournamentState.values();
        for (int i = 0; i < values.length; ++i) {
            this._infosByState.put(values[i], new ArrayList<TournamentInfo>());
        }
        this._orderOfStatesToShow.add(TournamentState.ONGOING);
        this._orderOfStatesToShow.add(TournamentState.PAUSED);
        this._orderOfStatesToShow.add(TournamentState.UPCOMING);
        this._orderOfStatesToShow.add(TournamentState.FINISHED);
        this.loadTournaments();
    }
    
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View inflate = layoutInflater.inflate(2131427370, viewGroup, false);
        final ObservableRefreshingListView tournamentList = (ObservableRefreshingListView)inflate.findViewById(2131297183);
        this._tournamentList = tournamentList;
        (this._listAdapter = new TournamentListAdapter(this._infosByState, this._orderOfStatesToShow)).setHidesEmptySections(true);
        tournamentList.setAdapter((ListAdapter)this._listAdapter);
        this._tournamentList.setListScrollListener((UpdatingList.UpdatingListListener)this);
        tournamentList.setOnItemClickListener((AdapterView.OnItemClickListener)this);
        this._tournamentList.enableFooter();
        return inflate;
    }
    
    public void onItemClick(final AdapterView<?> adapterView, final View view, final int n, final long n2) {
        final TournamentInfo tournamentInfo = ((SectionedListAdapter<S, TournamentInfo>)this._listAdapter).getValueForPosition(n);
        if (tournamentInfo != null) {
            new ShowTournamentDetailsAction(this.getContentPresenter(), tournamentInfo).perform();
        }
    }
    
    public boolean showMenu() {
        return true;
    }
    
    private class TournamentListAdapter extends SectionedListAdapter<TournamentState, TournamentInfo>
    {
        public TournamentListAdapter(final Map<TournamentState, List<TournamentInfo>> map, final List<TournamentState> list) {
            super(map, list);
        }
        
        @Override
        protected View getViewForSectionHeader(final TournamentState tournamentState, final View view, final ViewGroup viewGroup) {
            Object o = view;
            if (view == null) {
                o = new TournamentListHeaderView((Context)TournamentListFragment.this.getActivity());
            }
            final TournamentListHeaderView tournamentListHeaderView = (TournamentListHeaderView)o;
            tournamentListHeaderView.setTitle(TournamentListFragment.this.getText(tournamentState.getNameResourceId()));
            return (View)tournamentListHeaderView;
        }
        
        @Override
        protected View getViewForValue(final TournamentInfo tournamentInfo, final View view, final ViewGroup viewGroup) {
            Object o;
            if (viewGroup != null) {
                o = viewGroup.getContext();
            }
            else {
                o = TournamentListFragment.this.getActivity();
            }
            Object o2 = view;
            if (view == null) {
                o2 = new TournamentInfoView((Context)o);
            }
            final TournamentInfoView tournamentInfoView = (TournamentInfoView)o2;
            tournamentInfoView.setTournamentInfo(tournamentInfo);
            return (View)tournamentInfoView;
        }
    }
}
