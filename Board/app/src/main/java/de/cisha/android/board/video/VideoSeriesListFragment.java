// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video;

import de.cisha.android.view.CouchImageView;
import de.cisha.android.board.video.view.VideoAuthorAndEloRangeView;
import android.support.v7.widget.CardView;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.util.Logger;
import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;
import de.cisha.android.board.modalfragments.ConversionContext;
import android.app.Activity;
import de.cisha.android.board.service.IMembershipService;
import de.cisha.android.board.util.TimeHelper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.inputmethod.InputMethodManager;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.content.Intent;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import java.util.Set;
import android.view.View.OnClickListener;
import de.cisha.android.board.mainmenu.view.MenuItem;
import android.content.res.Resources;
import java.util.concurrent.Executors;
import de.cisha.android.board.service.jsonparser.JSONVideoSeriesInfoListParser;
import de.cisha.android.board.video.model.VideoLanguage;
import de.cisha.android.board.video.model.VideoFilter;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.ServiceProvider;
import android.net.NetworkInfo;
import android.net.ConnectivityManager;
import android.content.Context;
import java.util.Iterator;
import java.util.LinkedList;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.video.model.VideoSeriesInformation;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.service.IVideoService;
import java.util.concurrent.ExecutorService;
import android.widget.EditText;
import de.cisha.android.board.view.ScrollChromeView;
import android.support.v7.widget.RecyclerView;
import com.example.android.trivialdrivesample.util.SkuDetails;
import android.util.SparseArray;
import android.widget.ImageView;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import de.cisha.android.board.video.model.VideoSeriesId;
import java.util.List;
import de.cisha.android.board.account.PurchaseResultReceiver;
import android.support.v4.widget.SwipeRefreshLayout;
import de.cisha.android.board.AbstractContentFragment;

public class VideoSeriesListFragment extends AbstractContentFragment implements OnRefreshListener, PurchaseResultReceiver
{
    private List<VideoSeriesId> _allVideoSeriesIdsInOfflineMode;
    private String _currentSearchString;
    private DrawerLayout _drawerLayout;
    private View _editTextOverlay;
    private ImageView _filterButton;
    private ImageView _filterButtonOfflineVideos;
    private VideoFilterFragment _filterFragment;
    private SparseArray<SkuDetails> _productPriceMap;
    private RecyclerView _recyclerView;
    private ScrollChromeView _scrollChromeView;
    private ImageView _searchActionImageView;
    private EditText _searchEditTextView;
    private SeriesListAdapter _seriesListAdapter;
    private boolean _showOfflineOnly;
    private ExecutorService _singleThreadExecutor;
    private IVideoService _videoService;
    
    private List<VideoSeriesInformation> filterListOfflineOnly(final List<VideoSeriesInformation> list) {
        final LinkedList<VideoSeriesInformation> list2 = new LinkedList<VideoSeriesInformation>();
        if (this._allVideoSeriesIdsInOfflineMode != null) {
            for (final VideoSeriesInformation videoSeriesInformation : list) {
                if (this._allVideoSeriesIdsInOfflineMode.contains(videoSeriesInformation.getVideoSeriesId())) {
                    list2.add(videoSeriesInformation);
                }
            }
        }
        return list2;
    }
    
    private boolean isNetworkAvailable(final Context context) {
        final NetworkInfo activeNetworkInfo = ((ConnectivityManager)context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
    
    private void loadOfflineSeries() {
        this.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                VideoSeriesListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    final /* synthetic */ List val.seriesInfoList = ServiceProvider.getInstance().getLocalVideoService().getAllVideoSeriesInfoAvailable();
                    
                    @Override
                    public void run() {
                        VideoSeriesListFragment.this._seriesListAdapter = new SeriesListAdapter(this.val.seriesInfoList);
                        VideoSeriesListFragment.this._recyclerView.setAdapter((RecyclerView.Adapter)VideoSeriesListFragment.this._seriesListAdapter);
                        VideoSeriesListFragment.this.showReloadButton(this.val.seriesInfoList.isEmpty());
                    }
                });
            }
        });
    }
    
    private void loadProductPriceInformation() {
        this.showDialogWaiting(true, true, "", null);
        ServiceProvider.getInstance().getPurchaseService().getSkuDetailsMap(new LoadCommandCallbackWithTimeout<SparseArray<SkuDetails>>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                VideoSeriesListFragment.this.hideDialogWaiting();
                VideoSeriesListFragment.this.showViewForErrorCode(apiStatusCode, new IErrorPresenter.IReloadAction() {
                    @Override
                    public void performReload() {
                        VideoSeriesListFragment.this.loadProductPriceInformation();
                    }
                }, false);
                VideoSeriesListFragment.this.loadOfflineSeries();
            }
            
            @Override
            protected void succeded(final SparseArray<SkuDetails> sparseArray) {
                VideoSeriesListFragment.this._productPriceMap = sparseArray;
                VideoSeriesListFragment.this.hideDialogWaiting();
                VideoSeriesListFragment.this.loadSeriesList();
            }
        });
    }
    
    private void loadSeriesList() {
        final VideoFilter.Builder builder = new VideoFilter.Builder();
        if (this._filterFragment != null && this._filterFragment.isFilterSelected()) {
            if (this._filterFragment.getFilterIsPurchased()) {
                builder.setOnlyPurchasedItems();
            }
            builder.setMaximumLevel(this._filterFragment.getFilterMaxEloNumber());
            builder.setMinimumLevel(this._filterFragment.getFilterMinEloNumber());
            final Iterator<VideoLanguage> iterator = this._filterFragment.getFilterVideoLanguages().iterator();
            while (iterator.hasNext()) {
                builder.addLanguage(iterator.next());
            }
        }
        if (this._currentSearchString != null && !this._currentSearchString.isEmpty()) {
            builder.setSearchString(this._currentSearchString);
        }
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                VideoSeriesListFragment.this.showReloadButton(false);
                VideoSeriesListFragment.this.showDialogWaiting(false, true, null, null);
            }
        });
        this._videoService.getVideoSeriesList(builder.build(), new LoadCommandCallbackWithTimeout<JSONVideoSeriesInfoListParser.VideoSeriesInfoList>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                VideoSeriesListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        VideoSeriesListFragment.this.hideDialogWaiting();
                        VideoSeriesListFragment.this.showViewForErrorCode(apiStatusCode, new IErrorPresenter.IReloadAction() {
                            @Override
                            public void performReload() {
                                VideoSeriesListFragment.this.loadSeriesList();
                            }
                        });
                    }
                });
                VideoSeriesListFragment.this.loadOfflineSeries();
            }
            
            @Override
            protected void succeded(final JSONVideoSeriesInfoListParser.VideoSeriesInfoList list) {
                VideoSeriesListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        VideoSeriesListFragment.this.hideDialogWaiting();
                        List<VideoSeriesInformation> list;
                        if (VideoSeriesListFragment.this._showOfflineOnly) {
                            list = VideoSeriesListFragment.this.filterListOfflineOnly(list.getList());
                        }
                        else {
                            list = list.getList();
                        }
                        VideoSeriesListFragment.this._seriesListAdapter = new SeriesListAdapter(list);
                        VideoSeriesListFragment.this._recyclerView.setAdapter((RecyclerView.Adapter)VideoSeriesListFragment.this._seriesListAdapter);
                        VideoSeriesListFragment.this.showNoResultsView(list.getSeriesCount() == 0);
                    }
                });
            }
        });
    }
    
    private void runOnBackgroundThread(final Runnable runnable) {
        if (this._singleThreadExecutor == null) {
            this._singleThreadExecutor = Executors.newSingleThreadExecutor();
        }
        this._singleThreadExecutor.execute(runnable);
    }
    
    private void setCurrentSearchString(final String currentSearchString) {
        this._currentSearchString = currentSearchString;
        final String currentSearchString2 = this._currentSearchString;
        int visibility = 0;
        if (currentSearchString2 != null && !this._currentSearchString.isEmpty()) {
            this._scrollChromeView.setTopbarFixed(true);
            this._scrollChromeView.activateTopbar();
        }
        else {
            this._scrollChromeView.setTopbarFixed(false);
        }
        this._searchEditTextView.setEnabled(false);
        this._editTextOverlay.setClickable(true);
        final ImageView searchActionImageView = this._searchActionImageView;
        if (currentSearchString == null || currentSearchString.isEmpty()) {
            visibility = 8;
        }
        searchActionImageView.setVisibility(visibility);
    }
    
    private void showNoResultsView(final boolean b) {
        if (this.getView() != null) {
            final View viewById = this.getView().findViewById(2131297236);
            int visibility;
            if (b) {
                visibility = 0;
            }
            else {
                visibility = 8;
            }
            viewById.setVisibility(visibility);
        }
    }
    
    private void showReloadButton(final boolean b) {
        if (this.getView() != null) {
            final View viewById = this.getView().findViewById(2131297238);
            int visibility;
            if (b) {
                visibility = 0;
            }
            else {
                visibility = 8;
            }
            viewById.setVisibility(visibility);
        }
    }
    
    @Override
    public String getHeaderText(final Resources resources) {
        return resources.getString(2131690404);
    }
    
    @Override
    public MenuItem getHighlightedMenuItem() {
        return null;
    }
    
    @Override
    public List<View> getRightButtons(final Context context) {
        (this._filterButtonOfflineVideos = this.createNavbarButtonForRessource(context, 2131231884)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                VideoSeriesListFragment.this._showOfflineOnly ^= true;
                VideoSeriesListFragment.this.loadSeriesList();
                VideoSeriesListFragment.this._filterButtonOfflineVideos.setSelected(VideoSeriesListFragment.this._showOfflineOnly);
            }
        });
        this._filterButtonOfflineVideos.setSelected(this._showOfflineOnly);
        (this._filterButton = this.createNavbarButtonForRessource(context, 2131231132)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                if (!VideoSeriesListFragment.this._drawerLayout.isDrawerOpen(5)) {
                    VideoSeriesListFragment.this._drawerLayout.openDrawer(5);
                }
            }
        });
        final LinkedList<ImageView> list = (LinkedList<ImageView>)new LinkedList<View>();
        list.add((View)this._filterButtonOfflineVideos);
        list.add((View)this._filterButton);
        if (this._filterFragment != null) {
            this._filterButton.setSelected(this._filterFragment.isFilterSelected());
        }
        if (!this.isNetworkAvailable(context)) {
            this._filterButton.setVisibility(8);
            this._filterButtonOfflineVideos.setVisibility(8);
        }
        return (List<View>)list;
    }
    
    @Override
    public Set<SettingsMenuCategory> getSettingsMenuCategories() {
        return null;
    }
    
    @Override
    public String getTrackingName() {
        return "VideoSeriesList";
    }
    
    @Override
    public boolean isGrabMenuEnabled() {
        return true;
    }
    
    @Override
    public void onActivityResult(final int n, final int n2, final Intent intent) {
        if (!ServiceProvider.getInstance().getPurchaseService().handleActivityResult(n, n2, intent)) {
            super.onActivityResult(n, n2, intent);
        }
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this._productPriceMap = (SparseArray<SkuDetails>)new SparseArray(0);
        this._videoService = ServiceProvider.getInstance().getVideoService();
        if (this.isNetworkAvailable((Context)this.getActivity())) {
            this.loadProductPriceInformation();
            return;
        }
        this.loadOfflineSeries();
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View inflate = layoutInflater.inflate(2131427581, viewGroup, false);
        this._scrollChromeView = (ScrollChromeView)inflate.findViewById(2131297239);
        this._drawerLayout = (DrawerLayout)inflate.findViewById(2131297232);
        this._searchActionImageView = (ImageView)inflate.findViewById(2131297240);
        this._editTextOverlay = inflate.findViewById(2131297234);
        (this._searchEditTextView = (EditText)inflate.findViewById(2131297233)).setHint((CharSequence)this.getActivity().getString(2131690407));
        final EditText searchEditTextView = this._searchEditTextView;
        String currentSearchString;
        if (this._currentSearchString != null) {
            currentSearchString = this._currentSearchString;
        }
        else {
            currentSearchString = "";
        }
        searchEditTextView.setText((CharSequence)currentSearchString);
        this.setCurrentSearchString(this._currentSearchString);
        this._recyclerView = (RecyclerView)inflate.findViewById(2131296624);
        if (this._seriesListAdapter != null) {
            this._recyclerView.setAdapter((RecyclerView.Adapter)this._seriesListAdapter);
        }
        if (!this.isNetworkAvailable((Context)this.getActivity())) {
            this._scrollChromeView.deactivateTopbar();
        }
        return inflate;
    }
    
    @Override
    public void onRefresh() {
        this.loadSeriesList();
    }
    
    @Override
    public void onStart() {
        super.onStart();
        this.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                VideoSeriesListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    final /* synthetic */ List val.allVideoSeriesIdsInOfflineMode = ServiceProvider.getInstance().getLocalVideoService().getAllVideoSeriesIdsInOfflineMode();
                    
                    @Override
                    public void run() {
                        VideoSeriesListFragment.this._allVideoSeriesIdsInOfflineMode = (List<VideoSeriesId>)this.val.allVideoSeriesIdsInOfflineMode;
                        if (VideoSeriesListFragment.this._seriesListAdapter != null) {
                            ((RecyclerView.Adapter)VideoSeriesListFragment.this._seriesListAdapter).notifyDataSetChanged();
                        }
                    }
                });
            }
        });
    }
    
    @Override
    public void onViewCreated(final View view, final Bundle bundle) {
        super.onViewCreated(view, bundle);
        this._searchEditTextView.setSingleLine();
        this._searchEditTextView.setImeOptions(3);
        this._searchEditTextView.setOnEditorActionListener((TextView.OnEditorActionListener)new TextView.OnEditorActionListener() {
            public boolean onEditorAction(final TextView textView, final int n, final KeyEvent keyEvent) {
                if (n == 3) {
                    VideoSeriesListFragment.this.setCurrentSearchString(VideoSeriesListFragment.this._searchEditTextView.getText().toString());
                    VideoSeriesListFragment.this.loadSeriesList();
                    VideoSeriesListFragment.this._searchEditTextView.setEnabled(false);
                    VideoSeriesListFragment.this._editTextOverlay.setClickable(true);
                }
                return false;
            }
        });
        this._editTextOverlay.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                if (VideoSeriesListFragment.this._editTextOverlay.isClickable()) {
                    VideoSeriesListFragment.this._searchEditTextView.setEnabled(true);
                    VideoSeriesListFragment.this._editTextOverlay.setClickable(false);
                    VideoSeriesListFragment.this._searchEditTextView.requestFocus();
                    VideoSeriesListFragment.this._searchEditTextView.postDelayed((Runnable)new Runnable() {
                        @Override
                        public void run() {
                            ((InputMethodManager)VideoSeriesListFragment.this.getActivity().getSystemService("input_method")).showSoftInput((View)VideoSeriesListFragment.this._searchEditTextView, 0);
                        }
                    }, 50L);
                }
            }
        });
        this._searchActionImageView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                final boolean b = VideoSeriesListFragment.this._currentSearchString != null;
                VideoSeriesListFragment.this.setCurrentSearchString(null);
                VideoSeriesListFragment.this._searchEditTextView.setText((CharSequence)"");
                if (b) {
                    VideoSeriesListFragment.this.loadSeriesList();
                }
            }
        });
        this._recyclerView.setLayoutManager((RecyclerView.LayoutManager)new GridLayoutManager((Context)this.getActivity(), this.getActivity().getResources().getInteger(2131361810)));
        this._drawerLayout.setDrawerLockMode(1);
        this._drawerLayout.setDrawerListener((DrawerLayout.DrawerListener)new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerClosed(final View view) {
                VideoSeriesListFragment.this._filterButton.setSelected(VideoSeriesListFragment.this._filterFragment.isFilterSelected());
            }
            
            @Override
            public void onDrawerOpened(final View view) {
            }
            
            @Override
            public void onDrawerSlide(final View view, final float n) {
            }
            
            @Override
            public void onDrawerStateChanged(final int n) {
                if (n == 2) {
                    VideoSeriesListFragment.this._filterButton.setSelected(VideoSeriesListFragment.this._filterFragment.isFilterSelected());
                }
            }
        });
        this._filterFragment = (VideoFilterFragment)this.getChildFragmentManager().findFragmentByTag(VideoFilterFragment.class.getName());
        if (this._filterFragment == null) {
            this._filterFragment = new VideoFilterFragment();
            this.getChildFragmentManager().beginTransaction().add(2131297207, this._filterFragment, VideoFilterFragment.class.getName()).commit();
        }
        this._filterFragment.setFilterOnCancelButtonClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                VideoSeriesListFragment.this._drawerLayout.closeDrawers();
            }
        });
        this._filterFragment.setFilterOnSaveButtonClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                VideoSeriesListFragment.this._drawerLayout.closeDrawers();
                VideoSeriesListFragment.this.loadSeriesList();
            }
        });
        this._filterFragment.setFilterOnResetButtonClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                VideoSeriesListFragment.this._drawerLayout.closeDrawers();
                VideoSeriesListFragment.this.loadSeriesList();
            }
        });
        if (this._filterButton != null) {
            this._filterButton.setSelected(this._filterFragment.isFilterSelected());
        }
        if (this._filterButtonOfflineVideos != null) {
            this._filterButtonOfflineVideos.setSelected(this._showOfflineOnly);
        }
        view.findViewById(2131297237).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                VideoSeriesListFragment.this.loadSeriesList();
            }
        });
    }
    
    @Override
    public boolean showMenu() {
        return true;
    }
    
    protected void videoSeriesSelected(final VideoSeriesId videoSeriesId) {
        if (videoSeriesId != null) {
            this.getContentPresenter().showFragment(VideoSeriesOverviewFragment.createVideoOverviewFragment(videoSeriesId), true, true);
        }
    }
    
    public class SeriesListAdapter extends Adapter<ViewHolder>
    {
        private List<VideoSeriesInformation> _seriesList;
        
        public SeriesListAdapter(final List<VideoSeriesInformation> seriesList) {
            this._seriesList = seriesList;
        }
        
        @Override
        public int getItemCount() {
            return this._seriesList.size();
        }
        
        public void onBindViewHolder(final ViewHolder viewHolder, int n) {
            final VideoSeriesInformation videoSeriesInformation = this._seriesList.get(n);
            viewHolder._titleTextView.setText((CharSequence)videoSeriesInformation.getName());
            viewHolder._authorEloView.setAuthor(videoSeriesInformation.getAuthor());
            viewHolder._authorEloView.setEloRange(videoSeriesInformation.getEloRange());
            viewHolder._durationTextView.setText((CharSequence)TimeHelper.getDurationString(videoSeriesInformation.getDuration()));
            viewHolder._languageImageView.setImageResource(videoSeriesInformation.getLanguage().getImageResId());
            final TextView access.3100 = viewHolder._videoCountTextView;
            final StringBuilder sb = new StringBuilder();
            sb.append(videoSeriesInformation.getVideoCount());
            sb.append("");
            access.3100.setText((CharSequence)sb.toString());
            final TextView access.3101 = viewHolder._priceTextView;
            final boolean accessible = videoSeriesInformation.isAccessible();
            final int n2 = 8;
            final int n3 = 0;
            int visibility;
            if (accessible) {
                visibility = 8;
            }
            else {
                visibility = 0;
            }
            access.3101.setVisibility(visibility);
            final SkuDetails skuDetails = (SkuDetails)VideoSeriesListFragment.this._productPriceMap.get(videoSeriesInformation.getPriceCategoryId());
            if (skuDetails != null) {
                viewHolder._priceTextView.setText((CharSequence)skuDetails.getPrice());
                viewHolder._priceTextView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                    public void onClick(final View view) {
                        if (ServiceProvider.getInstance().getMembershipService().getMembershipLevel() != IMembershipService.MembershipLevel.MembershipLevelGuest) {
                            VideoSeriesListFragment.this.showDialogWaiting(true, true, "", null);
                            ServiceProvider.getInstance().getVideoService().purchaseVideoSeries(videoSeriesInformation.getVideoSeriesId(), videoSeriesInformation.getPriceCategoryId(), VideoSeriesListFragment.this.getActivity(), new LoadCommandCallback<Void>() {
                                @Override
                                public void loadingCancelled() {
                                    VideoSeriesListFragment.this.hideDialogWaiting();
                                }
                                
                                @Override
                                public void loadingFailed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                                    VideoSeriesListFragment.this.hideDialogWaiting();
                                    VideoSeriesListFragment.this.showViewForErrorCode(apiStatusCode, null);
                                }
                                
                                @Override
                                public void loadingSucceded(final Void void1) {
                                    VideoSeriesListFragment.this.hideDialogWaiting();
                                    videoSeriesInformation.setIsPurchased(true);
                                    videoSeriesInformation.setIsAccessible(true);
                                    ((RecyclerView.Adapter)VideoSeriesListFragment.this._seriesListAdapter).notifyItemChanged(n);
                                }
                            });
                            return;
                        }
                        VideoSeriesListFragment.this.getContentPresenter().showConversionDialog(null, ConversionContext.VIDEO_SERIES_OVERVIEW);
                    }
                });
            }
            else {
                viewHolder._priceTextView.setText((CharSequence)"-");
                viewHolder._priceTextView.setOnClickListener((View.OnClickListener)null);
                final Logger instance = Logger.getInstance();
                final String name = VideoSeriesListFragment.class.getName();
                final StringBuilder sb2 = new StringBuilder();
                sb2.append("product info is missing for price category ");
                sb2.append(videoSeriesInformation.getPriceCategoryId());
                instance.error(name, sb2.toString());
            }
            final View access.3102 = viewHolder._playButtonView;
            n = n2;
            if (videoSeriesInformation.isAccessible()) {
                n = 0;
            }
            access.3102.setVisibility(n);
            viewHolder._teaserImageView.setImageResource(2131231877);
            viewHolder._seriesId = videoSeriesInformation.getVideoSeriesId();
            final CishaUUID thumbCouchId = videoSeriesInformation.getThumbCouchId();
            if (thumbCouchId != null) {
                viewHolder._teaserImageView.setCouchId(thumbCouchId);
            }
            if (VideoSeriesListFragment.this._allVideoSeriesIdsInOfflineMode != null && VideoSeriesListFragment.this._allVideoSeriesIdsInOfflineMode.contains(videoSeriesInformation.getVideoSeriesId())) {
                n = 1;
            }
            else {
                n = 0;
            }
            final View access.3103 = viewHolder._offlineModeView;
            if (n != 0) {
                n = n3;
            }
            else {
                n = 4;
            }
            access.3103.setVisibility(n);
        }
        
        public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int n) {
            return new ViewHolder((CardView)LayoutInflater.from(viewGroup.getContext()).inflate(2131427582, viewGroup, false));
        }
        
        private class ViewHolder extends RecyclerView.ViewHolder
        {
            private VideoAuthorAndEloRangeView _authorEloView;
            private TextView _durationTextView;
            private ImageView _languageImageView;
            private View _offlineModeView;
            private View _playButtonView;
            private TextView _priceTextView;
            private VideoSeriesId _seriesId;
            private CouchImageView _teaserImageView;
            private TextView _titleTextView;
            private TextView _videoCountTextView;
            
            public ViewHolder(final CardView cardView) {
                super((View)cardView);
                this._titleTextView = (TextView)cardView.findViewById(2131297258);
                this._durationTextView = (TextView)cardView.findViewById(2131297252);
                this._languageImageView = (ImageView)cardView.findViewById(2131297254);
                this._priceTextView = (TextView)cardView.findViewById(2131297256);
                this._playButtonView = cardView.findViewById(2131297255);
                this._videoCountTextView = (TextView)cardView.findViewById(2131297259);
                this._teaserImageView = (CouchImageView)cardView.findViewById(2131297257);
                this._authorEloView = (VideoAuthorAndEloRangeView)cardView.findViewById(2131297198);
                this._offlineModeView = cardView.findViewById(2131297260);
                cardView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                    public void onClick(final View view) {
                        VideoSeriesListFragment.this.videoSeriesSelected(ViewHolder.this._seriesId);
                    }
                });
                this._playButtonView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                    public void onClick(final View view) {
                        VideoSeriesListFragment.this.videoSeriesSelected(ViewHolder.this._seriesId);
                    }
                });
            }
        }
    }
}
