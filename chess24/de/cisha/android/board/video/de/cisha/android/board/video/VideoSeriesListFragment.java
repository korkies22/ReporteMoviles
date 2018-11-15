/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.os.Bundle
 *  android.text.Editable
 *  android.util.SparseArray
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.inputmethod.InputMethodManager
 *  android.widget.EditText
 *  android.widget.ImageView
 *  android.widget.TextView
 *  android.widget.TextView$OnEditorActionListener
 *  org.json.JSONObject
 */
package de.cisha.android.board.video;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.trivialdrivesample.util.SkuDetails;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.account.PurchaseResultReceiver;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;
import de.cisha.android.board.modalfragments.ConversionContext;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.service.IMembershipService;
import de.cisha.android.board.service.IPurchaseService;
import de.cisha.android.board.service.IVideoService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.JSONVideoSeriesInfoListParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.util.TimeHelper;
import de.cisha.android.board.video.VideoFilterFragment;
import de.cisha.android.board.video.VideoSeriesOverviewFragment;
import de.cisha.android.board.video.model.EloRangeRepresentation;
import de.cisha.android.board.video.model.VideoFilter;
import de.cisha.android.board.video.model.VideoLanguage;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.model.VideoSeriesInformation;
import de.cisha.android.board.video.storage.ILocalVideoService;
import de.cisha.android.board.video.view.VideoAuthorAndEloRangeView;
import de.cisha.android.board.view.ScrollChromeView;
import de.cisha.android.view.CouchImageView;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.util.Logger;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONObject;

public class VideoSeriesListFragment
extends AbstractContentFragment
implements SwipeRefreshLayout.OnRefreshListener,
PurchaseResultReceiver {
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

    private List<VideoSeriesInformation> filterListOfflineOnly(List<VideoSeriesInformation> object) {
        LinkedList<VideoSeriesInformation> linkedList = new LinkedList<VideoSeriesInformation>();
        if (this._allVideoSeriesIdsInOfflineMode != null) {
            object = object.iterator();
            while (object.hasNext()) {
                VideoSeriesInformation videoSeriesInformation = (VideoSeriesInformation)object.next();
                if (!this._allVideoSeriesIdsInOfflineMode.contains(videoSeriesInformation.getVideoSeriesId())) continue;
                linkedList.add(videoSeriesInformation);
            }
        }
        return linkedList;
    }

    private boolean isNetworkAvailable(Context context) {
        if ((context = ((ConnectivityManager)context.getSystemService("connectivity")).getActiveNetworkInfo()) != null && context.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private void loadOfflineSeries() {
        this.runOnBackgroundThread(new Runnable(){

            @Override
            public void run() {
                final List<VideoSeriesInformation> list = ServiceProvider.getInstance().getLocalVideoService().getAllVideoSeriesInfoAvailable();
                VideoSeriesListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        VideoSeriesListFragment.this._seriesListAdapter = new SeriesListAdapter(list);
                        VideoSeriesListFragment.this._recyclerView.setAdapter(VideoSeriesListFragment.this._seriesListAdapter);
                        VideoSeriesListFragment.this.showReloadButton(list.isEmpty());
                    }
                });
            }

        });
    }

    private void loadProductPriceInformation() {
        this.showDialogWaiting(true, true, "", null);
        ServiceProvider.getInstance().getPurchaseService().getSkuDetailsMap((LoadCommandCallback<SparseArray<SkuDetails>>)new LoadCommandCallbackWithTimeout<SparseArray<SkuDetails>>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                VideoSeriesListFragment.this.hideDialogWaiting();
                VideoSeriesListFragment.this.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

                    @Override
                    public void performReload() {
                        VideoSeriesListFragment.this.loadProductPriceInformation();
                    }
                }, false);
                VideoSeriesListFragment.this.loadOfflineSeries();
            }

            @Override
            protected void succeded(SparseArray<SkuDetails> sparseArray) {
                VideoSeriesListFragment.this._productPriceMap = sparseArray;
                VideoSeriesListFragment.this.hideDialogWaiting();
                VideoSeriesListFragment.this.loadSeriesList();
            }

        });
    }

    private void loadSeriesList() {
        VideoFilter.Builder builder = new VideoFilter.Builder();
        if (this._filterFragment != null && this._filterFragment.isFilterSelected()) {
            if (this._filterFragment.getFilterIsPurchased()) {
                builder.setOnlyPurchasedItems();
            }
            builder.setMaximumLevel(this._filterFragment.getFilterMaxEloNumber());
            builder.setMinimumLevel(this._filterFragment.getFilterMinEloNumber());
            Iterator<VideoLanguage> iterator = this._filterFragment.getFilterVideoLanguages().iterator();
            while (iterator.hasNext()) {
                builder.addLanguage(iterator.next());
            }
        }
        if (this._currentSearchString != null && !this._currentSearchString.isEmpty()) {
            builder.setSearchString(this._currentSearchString);
        }
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                VideoSeriesListFragment.this.showReloadButton(false);
                VideoSeriesListFragment.this.showDialogWaiting(false, true, null, null);
            }
        });
        this._videoService.getVideoSeriesList(builder.build(), (LoadCommandCallback<JSONVideoSeriesInfoListParser.VideoSeriesInfoList>)new LoadCommandCallbackWithTimeout<JSONVideoSeriesInfoListParser.VideoSeriesInfoList>(){

            @Override
            protected void failed(final APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                VideoSeriesListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        VideoSeriesListFragment.this.hideDialogWaiting();
                        VideoSeriesListFragment.this.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

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
            protected void succeded(final JSONVideoSeriesInfoListParser.VideoSeriesInfoList videoSeriesInfoList) {
                VideoSeriesListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        VideoSeriesListFragment.this.hideDialogWaiting();
                        Object object = VideoSeriesListFragment.this._showOfflineOnly ? VideoSeriesListFragment.this.filterListOfflineOnly(videoSeriesInfoList.getList()) : videoSeriesInfoList.getList();
                        VideoSeriesListFragment.this._seriesListAdapter = new SeriesListAdapter((List<VideoSeriesInformation>)object);
                        VideoSeriesListFragment.this._recyclerView.setAdapter(VideoSeriesListFragment.this._seriesListAdapter);
                        object = VideoSeriesListFragment.this;
                        boolean bl = videoSeriesInfoList.getSeriesCount() == 0;
                        ((VideoSeriesListFragment)object).showNoResultsView(bl);
                    }
                });
            }

        });
    }

    private void runOnBackgroundThread(Runnable runnable) {
        if (this._singleThreadExecutor == null) {
            this._singleThreadExecutor = Executors.newSingleThreadExecutor();
        }
        this._singleThreadExecutor.execute(runnable);
    }

    private void setCurrentSearchString(String string) {
        String string2 = this._currentSearchString = string;
        int n = 0;
        if (string2 != null && !this._currentSearchString.isEmpty()) {
            this._scrollChromeView.setTopbarFixed(true);
            this._scrollChromeView.activateTopbar();
        } else {
            this._scrollChromeView.setTopbarFixed(false);
        }
        this._searchEditTextView.setEnabled(false);
        this._editTextOverlay.setClickable(true);
        string2 = this._searchActionImageView;
        if (string == null || string.isEmpty()) {
            n = 8;
        }
        string2.setVisibility(n);
    }

    private void showNoResultsView(boolean bl) {
        if (this.getView() != null) {
            View view = this.getView().findViewById(2131297236);
            int n = bl ? 0 : 8;
            view.setVisibility(n);
        }
    }

    private void showReloadButton(boolean bl) {
        if (this.getView() != null) {
            View view = this.getView().findViewById(2131297238);
            int n = bl ? 0 : 8;
            view.setVisibility(n);
        }
    }

    @Override
    public String getHeaderText(Resources resources) {
        return resources.getString(2131690404);
    }

    @Override
    public MenuItem getHighlightedMenuItem() {
        return null;
    }

    @Override
    public List<View> getRightButtons(Context context) {
        this._filterButtonOfflineVideos = this.createNavbarButtonForRessource(context, 2131231884);
        this._filterButtonOfflineVideos.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                VideoSeriesListFragment.this._showOfflineOnly = VideoSeriesListFragment.this._showOfflineOnly ^ true;
                VideoSeriesListFragment.this.loadSeriesList();
                VideoSeriesListFragment.this._filterButtonOfflineVideos.setSelected(VideoSeriesListFragment.this._showOfflineOnly);
            }
        });
        this._filterButtonOfflineVideos.setSelected(this._showOfflineOnly);
        this._filterButton = this.createNavbarButtonForRessource(context, 2131231132);
        this._filterButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (!VideoSeriesListFragment.this._drawerLayout.isDrawerOpen(5)) {
                    VideoSeriesListFragment.this._drawerLayout.openDrawer(5);
                }
            }
        });
        LinkedList<View> linkedList = new LinkedList<View>();
        linkedList.add((View)this._filterButtonOfflineVideos);
        linkedList.add((View)this._filterButton);
        if (this._filterFragment != null) {
            this._filterButton.setSelected(this._filterFragment.isFilterSelected());
        }
        if (!this.isNetworkAvailable(context)) {
            this._filterButton.setVisibility(8);
            this._filterButtonOfflineVideos.setVisibility(8);
        }
        return linkedList;
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
    public void onActivityResult(int n, int n2, Intent intent) {
        if (!ServiceProvider.getInstance().getPurchaseService().handleActivityResult(n, n2, intent)) {
            super.onActivityResult(n, n2, intent);
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this._productPriceMap = new SparseArray(0);
        this._videoService = ServiceProvider.getInstance().getVideoService();
        if (this.isNetworkAvailable((Context)this.getActivity())) {
            this.loadProductPriceInformation();
            return;
        }
        this.loadOfflineSeries();
    }

    @Override
    public View onCreateView(LayoutInflater object, ViewGroup viewGroup, Bundle bundle) {
        viewGroup = object.inflate(2131427581, viewGroup, false);
        this._scrollChromeView = (ScrollChromeView)viewGroup.findViewById(2131297239);
        this._drawerLayout = (DrawerLayout)viewGroup.findViewById(2131297232);
        this._searchActionImageView = (ImageView)viewGroup.findViewById(2131297240);
        this._editTextOverlay = viewGroup.findViewById(2131297234);
        this._searchEditTextView = (EditText)viewGroup.findViewById(2131297233);
        this._searchEditTextView.setHint((CharSequence)this.getActivity().getString(2131690407));
        bundle = this._searchEditTextView;
        object = this._currentSearchString != null ? this._currentSearchString : "";
        bundle.setText((CharSequence)object);
        this.setCurrentSearchString(this._currentSearchString);
        this._recyclerView = (RecyclerView)viewGroup.findViewById(2131296624);
        if (this._seriesListAdapter != null) {
            this._recyclerView.setAdapter(this._seriesListAdapter);
        }
        if (!this.isNetworkAvailable((Context)this.getActivity())) {
            this._scrollChromeView.deactivateTopbar();
        }
        return viewGroup;
    }

    @Override
    public void onRefresh() {
        this.loadSeriesList();
    }

    @Override
    public void onStart() {
        super.onStart();
        this.runOnBackgroundThread(new Runnable(){

            @Override
            public void run() {
                final List<VideoSeriesId> list = ServiceProvider.getInstance().getLocalVideoService().getAllVideoSeriesIdsInOfflineMode();
                VideoSeriesListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        VideoSeriesListFragment.this._allVideoSeriesIdsInOfflineMode = list;
                        if (VideoSeriesListFragment.this._seriesListAdapter != null) {
                            VideoSeriesListFragment.this._seriesListAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }

        });
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this._searchEditTextView.setSingleLine();
        this._searchEditTextView.setImeOptions(3);
        this._searchEditTextView.setOnEditorActionListener(new TextView.OnEditorActionListener(){

            public boolean onEditorAction(TextView textView, int n, KeyEvent keyEvent) {
                if (n == 3) {
                    VideoSeriesListFragment.this.setCurrentSearchString(VideoSeriesListFragment.this._searchEditTextView.getText().toString());
                    VideoSeriesListFragment.this.loadSeriesList();
                    VideoSeriesListFragment.this._searchEditTextView.setEnabled(false);
                    VideoSeriesListFragment.this._editTextOverlay.setClickable(true);
                }
                return false;
            }
        });
        this._editTextOverlay.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (VideoSeriesListFragment.this._editTextOverlay.isClickable()) {
                    VideoSeriesListFragment.this._searchEditTextView.setEnabled(true);
                    VideoSeriesListFragment.this._editTextOverlay.setClickable(false);
                    VideoSeriesListFragment.this._searchEditTextView.requestFocus();
                    VideoSeriesListFragment.this._searchEditTextView.postDelayed(new Runnable(){

                        @Override
                        public void run() {
                            ((InputMethodManager)VideoSeriesListFragment.this.getActivity().getSystemService("input_method")).showSoftInput((View)VideoSeriesListFragment.this._searchEditTextView, 0);
                        }
                    }, 50L);
                }
            }

        });
        this._searchActionImageView.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                boolean bl = VideoSeriesListFragment.this._currentSearchString != null;
                VideoSeriesListFragment.this.setCurrentSearchString(null);
                VideoSeriesListFragment.this._searchEditTextView.setText((CharSequence)"");
                if (bl) {
                    VideoSeriesListFragment.this.loadSeriesList();
                }
            }
        });
        int n = this.getActivity().getResources().getInteger(2131361810);
        this._recyclerView.setLayoutManager(new GridLayoutManager((Context)this.getActivity(), n));
        this._drawerLayout.setDrawerLockMode(1);
        this._drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener(){

            @Override
            public void onDrawerClosed(View view) {
                VideoSeriesListFragment.this._filterButton.setSelected(VideoSeriesListFragment.this._filterFragment.isFilterSelected());
            }

            @Override
            public void onDrawerOpened(View view) {
            }

            @Override
            public void onDrawerSlide(View view, float f) {
            }

            @Override
            public void onDrawerStateChanged(int n) {
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
        this._filterFragment.setFilterOnCancelButtonClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                VideoSeriesListFragment.this._drawerLayout.closeDrawers();
            }
        });
        this._filterFragment.setFilterOnSaveButtonClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                VideoSeriesListFragment.this._drawerLayout.closeDrawers();
                VideoSeriesListFragment.this.loadSeriesList();
            }
        });
        this._filterFragment.setFilterOnResetButtonClickListener(new View.OnClickListener(){

            public void onClick(View view) {
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
        view.findViewById(2131297237).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                VideoSeriesListFragment.this.loadSeriesList();
            }
        });
    }

    @Override
    public boolean showMenu() {
        return true;
    }

    protected void videoSeriesSelected(VideoSeriesId object) {
        if (object != null) {
            object = VideoSeriesOverviewFragment.createVideoOverviewFragment((VideoSeriesId)object);
            this.getContentPresenter().showFragment((AbstractContentFragment)object, true, true);
        }
    }

    public class SeriesListAdapter
    extends RecyclerView.Adapter<SeriesListAdapter$ViewHolder> {
        private List<VideoSeriesInformation> _seriesList;

        public SeriesListAdapter(List<VideoSeriesInformation> list) {
            this._seriesList = list;
        }

        @Override
        public int getItemCount() {
            return this._seriesList.size();
        }

        @Override
        public void onBindViewHolder(SeriesListAdapter$ViewHolder seriesListAdapter$ViewHolder, final int n) {
            final VideoSeriesInformation videoSeriesInformation = this._seriesList.get(n);
            seriesListAdapter$ViewHolder._titleTextView.setText((CharSequence)videoSeriesInformation.getName());
            seriesListAdapter$ViewHolder._authorEloView.setAuthor(videoSeriesInformation.getAuthor());
            seriesListAdapter$ViewHolder._authorEloView.setEloRange(videoSeriesInformation.getEloRange());
            seriesListAdapter$ViewHolder._durationTextView.setText((CharSequence)TimeHelper.getDurationString(videoSeriesInformation.getDuration()));
            seriesListAdapter$ViewHolder._languageImageView.setImageResource(videoSeriesInformation.getLanguage().getImageResId());
            Object object = seriesListAdapter$ViewHolder._videoCountTextView;
            CharSequence charSequence = new StringBuilder();
            charSequence.append(videoSeriesInformation.getVideoCount());
            charSequence.append("");
            object.setText((CharSequence)charSequence.toString());
            object = seriesListAdapter$ViewHolder._priceTextView;
            boolean bl = videoSeriesInformation.isAccessible();
            int n2 = 8;
            int n3 = 0;
            int n4 = bl ? 8 : 0;
            object.setVisibility(n4);
            object = (SkuDetails)VideoSeriesListFragment.this._productPriceMap.get(videoSeriesInformation.getPriceCategoryId());
            if (object != null) {
                seriesListAdapter$ViewHolder._priceTextView.setText((CharSequence)object.getPrice());
                seriesListAdapter$ViewHolder._priceTextView.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View view) {
                        if (ServiceProvider.getInstance().getMembershipService().getMembershipLevel() != IMembershipService.MembershipLevel.MembershipLevelGuest) {
                            VideoSeriesListFragment.this.showDialogWaiting(true, true, "", null);
                            ServiceProvider.getInstance().getVideoService().purchaseVideoSeries(videoSeriesInformation.getVideoSeriesId(), videoSeriesInformation.getPriceCategoryId(), VideoSeriesListFragment.this.getActivity(), new LoadCommandCallback<Void>(){

                                @Override
                                public void loadingCancelled() {
                                    VideoSeriesListFragment.this.hideDialogWaiting();
                                }

                                @Override
                                public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                                    VideoSeriesListFragment.this.hideDialogWaiting();
                                    VideoSeriesListFragment.this.showViewForErrorCode(aPIStatusCode, null);
                                }

                                @Override
                                public void loadingSucceded(Void void_) {
                                    VideoSeriesListFragment.this.hideDialogWaiting();
                                    videoSeriesInformation.setIsPurchased(true);
                                    videoSeriesInformation.setIsAccessible(true);
                                    VideoSeriesListFragment.this._seriesListAdapter.notifyItemChanged(n);
                                }
                            });
                            return;
                        }
                        VideoSeriesListFragment.this.getContentPresenter().showConversionDialog(null, ConversionContext.VIDEO_SERIES_OVERVIEW);
                    }

                });
            } else {
                seriesListAdapter$ViewHolder._priceTextView.setText((CharSequence)"-");
                seriesListAdapter$ViewHolder._priceTextView.setOnClickListener(null);
                object = Logger.getInstance();
                charSequence = VideoSeriesListFragment.class.getName();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("product info is missing for price category ");
                stringBuilder.append(videoSeriesInformation.getPriceCategoryId());
                object.error((String)charSequence, stringBuilder.toString());
            }
            object = seriesListAdapter$ViewHolder._playButtonView;
            n = n2;
            if (videoSeriesInformation.isAccessible()) {
                n = 0;
            }
            object.setVisibility(n);
            seriesListAdapter$ViewHolder._teaserImageView.setImageResource(2131231877);
            seriesListAdapter$ViewHolder._seriesId = videoSeriesInformation.getVideoSeriesId();
            object = videoSeriesInformation.getThumbCouchId();
            if (object != null) {
                seriesListAdapter$ViewHolder._teaserImageView.setCouchId((CishaUUID)object);
            }
            n = VideoSeriesListFragment.this._allVideoSeriesIdsInOfflineMode != null && VideoSeriesListFragment.this._allVideoSeriesIdsInOfflineMode.contains(videoSeriesInformation.getVideoSeriesId()) ? 1 : 0;
            seriesListAdapter$ViewHolder = seriesListAdapter$ViewHolder._offlineModeView;
            n = n != 0 ? n3 : 4;
            seriesListAdapter$ViewHolder.setVisibility(n);
        }

        @Override
        public SeriesListAdapter$ViewHolder onCreateViewHolder(ViewGroup viewGroup, int n) {
            return new SeriesListAdapter$ViewHolder((CardView)LayoutInflater.from((Context)viewGroup.getContext()).inflate(2131427582, viewGroup, false));
        }

    }

    private class SeriesListAdapter$ViewHolder
    extends RecyclerView.ViewHolder {
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

        public SeriesListAdapter$ViewHolder(CardView cardView) {
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
            cardView.setOnClickListener(new View.OnClickListener(SeriesListAdapter.this){
                final /* synthetic */ SeriesListAdapter val$this$1;
                {
                    this.val$this$1 = seriesListAdapter;
                }

                public void onClick(View view) {
                    VideoSeriesListFragment.this.videoSeriesSelected(SeriesListAdapter$ViewHolder.this._seriesId);
                }
            });
            this._playButtonView.setOnClickListener(new View.OnClickListener(SeriesListAdapter.this){
                final /* synthetic */ SeriesListAdapter val$this$1;
                {
                    this.val$this$1 = seriesListAdapter;
                }

                public void onClick(View view) {
                    VideoSeriesListFragment.this.videoSeriesSelected(SeriesListAdapter$ViewHolder.this._seriesId);
                }
            });
        }

    }

}
