/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.net.Uri
 *  android.os.Bundle
 *  android.util.DisplayMetrics
 *  android.util.SparseArray
 *  android.util.TypedValue
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 *  org.json.JSONObject
 */
package de.cisha.android.board.video;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.android.trivialdrivesample.util.SkuDetails;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.LoadingHelper;
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
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.video.VideoFragment;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoInformation;
import de.cisha.android.board.video.model.VideoSeries;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.storage.ILocalVideoService;
import de.cisha.android.board.video.storage.LocalVideoInfo;
import de.cisha.android.board.video.storage.LocalVideoSeriesInfo;
import de.cisha.android.board.video.view.SingleVideoListItem;
import de.cisha.android.board.video.view.VideoSeriesHeaderView;
import de.cisha.chess.util.Logger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import org.json.JSONObject;

public class VideoSeriesOverviewFragment
extends AbstractContentFragment
implements PurchaseResultReceiver,
LoadingHelper.LoadingHelperListener,
LocalVideoSeriesInfo.LocalVideoSeriesChangeListener,
LocalVideoInfo.LocalVideoStateChangeListener {
    private static final String ARGS_SERIES_ID_KEY = "series_id_key";
    private ViewGroup _contentGroup;
    private VideoSeries _currentVideoSeries;
    private int _downloadProgress;
    private LocalVideoSeriesInfo _localVideoSeries;
    private ILocalVideoService _localVideoService;
    private Map<VideoId, LocalVideoInfo> _mapLocalVideoInfos;
    private Map<VideoId, SingleVideoListItem> _mapSeriesIdView;
    private VideoSeriesId _seriesId;
    private volatile Runnable _setProgressTextAction;
    private SparseArray<SkuDetails> _skuDetails;
    private TextView _textViewDownloadProgress;
    private VideoSeriesHeaderView _videoSeriesHeader;

    private void bindLocalVideoInfos() {
        Map<VideoId, LocalVideoInfo> map = this._mapLocalVideoInfos;
        Map<VideoId, SingleVideoListItem> map2 = this._mapSeriesIdView;
        if (map != null && map2 != null) {
            for (Map.Entry entry : map2.entrySet()) {
                VideoId videoId = (VideoId)entry.getKey();
                ((SingleVideoListItem)entry.getValue()).setLocalVideoInfo(map.get(videoId));
            }
        }
    }

    private void clearLocalVideoViewBinding() {
        Iterator<SingleVideoListItem> iterator = this._mapSeriesIdView.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().setLocalVideoInfo(null);
        }
    }

    public static VideoSeriesOverviewFragment createVideoOverviewFragment(VideoSeriesId object) {
        Bundle bundle = new Bundle();
        if (object != null && object.getUuid() != null) {
            bundle.putString(ARGS_SERIES_ID_KEY, object.getUuid());
        }
        object = new VideoSeriesOverviewFragment();
        object.setArguments(bundle);
        return object;
    }

    private void loadVideoSeries() {
        final LoadingHelper loadingHelper = new LoadingHelper(this);
        LoadCommandCallbackWithTimeout<SparseArray<SkuDetails>> loadCommandCallbackWithTimeout = new LoadCommandCallbackWithTimeout<SparseArray<SkuDetails>>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                Logger.getInstance().error(VideoSeriesOverviewFragment.class.getName(), string, null);
                loadingHelper.loadingFailed(this);
            }

            @Override
            protected void succeded(SparseArray<SkuDetails> sparseArray) {
                VideoSeriesOverviewFragment.this._skuDetails = sparseArray;
                Logger.getInstance().debug(VideoSeriesOverviewFragment.class.getName(), "sku details loaded successfully");
                loadingHelper.loadingCompleted(this);
            }
        };
        if (this._seriesId != null) {
            LoadCommandCallbackWithTimeout<VideoSeries> loadCommandCallbackWithTimeout2 = new LoadCommandCallbackWithTimeout<VideoSeries>(){

                @Override
                protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                    loadingHelper.loadingFailed(this);
                }

                @Override
                protected void succeded(VideoSeries videoSeries) {
                    VideoSeriesOverviewFragment.this._currentVideoSeries = videoSeries;
                    loadingHelper.loadingCompleted(this);
                }
            };
            loadingHelper.addLoadable(loadCommandCallbackWithTimeout);
            loadingHelper.addLoadable(loadCommandCallbackWithTimeout2);
            ServiceProvider.getInstance().getPurchaseService().getSkuDetailsMap((LoadCommandCallback<SparseArray<SkuDetails>>)loadCommandCallbackWithTimeout);
            ServiceProvider.getInstance().getVideoService().getVideoSeries(this._seriesId, (LoadCommandCallback<VideoSeries>)loadCommandCallbackWithTimeout2);
        }
    }

    private void setLocalVideoInfos(List<LocalVideoInfo> object) {
        this._mapLocalVideoInfos.clear();
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                LocalVideoInfo localVideoInfo = (LocalVideoInfo)object.next();
                this._mapLocalVideoInfos.put(localVideoInfo.getVideoId(), localVideoInfo);
                localVideoInfo.addListener(this);
            }
        }
        this.bindLocalVideoInfos();
    }

    private void setLocalVideoSeries(LocalVideoSeriesInfo localVideoSeriesInfo) {
        this._localVideoSeries = localVideoSeriesInfo;
        if (localVideoSeriesInfo != null) {
            localVideoSeriesInfo.setListener(this);
            if (localVideoSeriesInfo.isSeriesObjectAvailable()) {
                VideoSeries videoSeries;
                this._currentVideoSeries = videoSeries = this._localVideoService.getVideoSeries(localVideoSeriesInfo.getSeriesId());
                if (videoSeries != null) {
                    this.videoLoaded(videoSeries);
                }
            }
            this.setLocalVideoInfos(localVideoSeriesInfo.getVideoList());
            this.updateDownloadProgress();
            return;
        }
        this.clearLocalVideoViewBinding();
    }

    private void showConversionOrStartPurchase() {
        if (this._currentVideoSeries != null && ServiceProvider.getInstance().getMembershipService().getMembershipLevel() != IMembershipService.MembershipLevel.MembershipLevelGuest) {
            this.startVideoPurchase();
            return;
        }
        if (this._currentVideoSeries != null) {
            this.getContentPresenter().showConversionDialog(null, ConversionContext.VIDEO_SERIES_OVERVIEW);
        }
    }

    private void startVideoPurchase() {
        this.showDialogWaiting(true, true, "", null);
        ServiceProvider.getInstance().getVideoService().purchaseVideoSeries(this._seriesId, this._currentVideoSeries.getPriceCategoryId(), this.getActivity(), new LoadCommandCallback<Void>(){

            @Override
            public void loadingCancelled() {
                VideoSeriesOverviewFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        VideoSeriesOverviewFragment.this.hideDialogWaiting();
                    }
                });
            }

            @Override
            public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                VideoSeriesOverviewFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        VideoSeriesOverviewFragment.this.hideDialogWaiting();
                    }
                });
            }

            @Override
            public void loadingSucceded(Void void_) {
                VideoSeriesOverviewFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        VideoSeriesOverviewFragment.this.hideDialogWaiting();
                        VideoSeriesOverviewFragment.this.loadVideoSeries();
                    }
                });
            }

        });
    }

    private void updateDownloadProgress() {
        if (this._localVideoSeries != null) {
            final int n = (int)(this._localVideoSeries.getDownloadProgress() * 100.0f);
            final long l = this._localVideoSeries.getCurrentLoadedBytes() / 1000000L;
            final long l2 = this._localVideoSeries.getTotalBytes() / 1000000L;
            if (this._setProgressTextAction == null || n != this._downloadProgress) {
                this._setProgressTextAction = new Runnable(){

                    @Override
                    public void run() {
                        VideoSeriesOverviewFragment.this._textViewDownloadProgress.setVisibility(0);
                        TextView textView = VideoSeriesOverviewFragment.this._textViewDownloadProgress;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(n);
                        stringBuilder.append("% (");
                        stringBuilder.append(l);
                        stringBuilder.append(" / ");
                        stringBuilder.append(l2);
                        stringBuilder.append(" MB)");
                        textView.setText((CharSequence)stringBuilder.toString());
                        VideoSeriesOverviewFragment.this._setProgressTextAction = null;
                    }
                };
                this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        Runnable runnable = VideoSeriesOverviewFragment.this._setProgressTextAction;
                        if (runnable != null) {
                            VideoSeriesOverviewFragment.this._textViewDownloadProgress.postDelayed(runnable, 500L);
                        }
                    }
                });
            }
            this._downloadProgress = n;
        }
    }

    @Override
    public String getHeaderText(Resources resources) {
        return resources.getString(2131690394);
    }

    @Override
    public MenuItem getHighlightedMenuItem() {
        return MenuItem.ADVANCED;
    }

    @Override
    public Set<SettingsMenuCategory> getSettingsMenuCategories() {
        return null;
    }

    @Override
    public String getTrackingName() {
        return "VideoDetails";
    }

    @Override
    public boolean isGrabMenuEnabled() {
        return true;
    }

    @Override
    public void loadingStart() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                VideoSeriesOverviewFragment.this.showDialogWaiting(true, true, "", null);
            }
        });
    }

    @Override
    public void loadingStop(boolean bl) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                VideoSeriesOverviewFragment.this.hideDialogWaiting();
                if (VideoSeriesOverviewFragment.this._currentVideoSeries != null) {
                    VideoSeriesOverviewFragment.this.videoLoaded(VideoSeriesOverviewFragment.this._currentVideoSeries);
                }
            }
        });
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
        this._mapSeriesIdView = new HashMap<VideoId, SingleVideoListItem>();
        this._mapLocalVideoInfos = new HashMap<VideoId, LocalVideoInfo>();
        bundle = this.getArguments();
        if (bundle != null) {
            this._seriesId = new VideoSeriesId(bundle.getString(ARGS_SERIES_ID_KEY));
        }
        this._localVideoService = ServiceProvider.getInstance().getLocalVideoService();
        Executors.newSingleThreadExecutor().execute(new Runnable(){

            @Override
            public void run() {
                if (VideoSeriesOverviewFragment.this._localVideoService.isVideoSeriesOfflineModeEnabled(VideoSeriesOverviewFragment.this._seriesId)) {
                    VideoSeriesOverviewFragment.this.setLocalVideoSeries(VideoSeriesOverviewFragment.this._localVideoService.getLocalVideoSeries(VideoSeriesOverviewFragment.this._seriesId));
                    return;
                }
                VideoSeriesOverviewFragment.this.loadVideoSeries();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2131427579, viewGroup, false);
        this._contentGroup = (ViewGroup)layoutInflater.findViewById(2131297229);
        this._videoSeriesHeader = (VideoSeriesHeaderView)layoutInflater.findViewById(2131297225);
        this._textViewDownloadProgress = (TextView)layoutInflater.findViewById(2131297226);
        viewGroup = (CompoundButton)layoutInflater.findViewById(2131297224);
        viewGroup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener((CompoundButton)viewGroup){
            final /* synthetic */ CompoundButton val$downloadToggle;
            {
                this.val$downloadToggle = compoundButton;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                if (VideoSeriesOverviewFragment.this._currentVideoSeries != null && !VideoSeriesOverviewFragment.this._currentVideoSeries.isAccessible()) {
                    this.val$downloadToggle.setChecked(false);
                    VideoSeriesOverviewFragment.this.showConversionOrStartPurchase();
                    return;
                }
                if (bl) {
                    VideoSeriesOverviewFragment.this._localVideoService.enableOfflineModeForSeries(VideoSeriesOverviewFragment.this._seriesId);
                } else {
                    VideoSeriesOverviewFragment.this._localVideoService.disableOfflineModeForSeries(VideoSeriesOverviewFragment.this._seriesId);
                }
                VideoSeriesOverviewFragment.this.setLocalVideoSeries(VideoSeriesOverviewFragment.this._localVideoService.getLocalVideoSeries(VideoSeriesOverviewFragment.this._seriesId));
            }
        });
        viewGroup.setChecked(this._localVideoService.isVideoSeriesOfflineModeEnabled(this._seriesId));
        this._videoSeriesHeader.getViewPriceButton().setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                VideoSeriesOverviewFragment.this.showConversionOrStartPurchase();
            }
        });
        if (this._currentVideoSeries != null) {
            this.videoLoaded(this._currentVideoSeries);
        }
        return layoutInflater;
    }

    @Override
    public void onLocalVideoChanged(final boolean bl, final List<LocalVideoInfo> list) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                if (bl && VideoSeriesOverviewFragment.this._currentVideoSeries == null) {
                    VideoSeries videoSeries = VideoSeriesOverviewFragment.this._localVideoService.getVideoSeries(VideoSeriesOverviewFragment.this._seriesId);
                    VideoSeriesOverviewFragment.this._currentVideoSeries = videoSeries;
                    if (videoSeries != null) {
                        VideoSeriesOverviewFragment.this.videoLoaded(videoSeries);
                    }
                }
                VideoSeriesOverviewFragment.this.setLocalVideoInfos(list);
            }
        });
    }

    @Override
    public void onLocalVideoStateChanged(LocalVideoInfo localVideoInfo, boolean bl, float f, Uri uri, LocalVideoInfo.LocalVideoServiceDownloadState localVideoServiceDownloadState) {
        this.updateDownloadProgress();
    }

    @Override
    public boolean showMenu() {
        return false;
    }

    protected void videoLoaded(final VideoSeries videoSeries) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                VideoSeriesOverviewFragment.this._videoSeriesHeader.setVideoSeries(videoSeries);
                if (VideoSeriesOverviewFragment.this._skuDetails != null && VideoSeriesOverviewFragment.this._skuDetails.get(videoSeries.getPriceCategoryId()) != null) {
                    VideoSeriesOverviewFragment.this._videoSeriesHeader.setViewPriceText(((SkuDetails)VideoSeriesOverviewFragment.this._skuDetails.get(videoSeries.getPriceCategoryId())).getPrice());
                } else {
                    VideoSeriesOverviewFragment.this._videoSeriesHeader.setViewPriceText("?");
                }
                VideoSeriesOverviewFragment.this._contentGroup.removeAllViews();
                VideoSeriesOverviewFragment.this._mapSeriesIdView.clear();
                for (final VideoInformation videoInformation : videoSeries.getVideoInformationList()) {
                    SingleVideoListItem singleVideoListItem = new SingleVideoListItem((Context)VideoSeriesOverviewFragment.this.getActivity(), videoInformation);
                    VideoSeriesOverviewFragment.this._mapSeriesIdView.put(videoInformation.getId(), singleVideoListItem);
                    VideoSeriesOverviewFragment.this._contentGroup.addView((View)singleVideoListItem);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)singleVideoListItem.getLayoutParams();
                    int n = (int)TypedValue.applyDimension((int)1, (float)5.0f, (DisplayMetrics)VideoSeriesOverviewFragment.this.getResources().getDisplayMetrics());
                    layoutParams.setMargins(0, n, 0, n);
                    singleVideoListItem.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
                    singleVideoListItem.setOnVideoClickListener(new View.OnClickListener(){

                        public void onClick(View object) {
                            if (videoInformation.isAccessible()) {
                                object = (LocalVideoInfo)VideoSeriesOverviewFragment.this._mapLocalVideoInfos.get(videoInformation.getId());
                                if (object == null || object.getState() == LocalVideoInfo.LocalVideoServiceDownloadState.COMPLETED) {
                                    object = VideoFragment.createVideoFragment(videoInformation.getId(), VideoSeriesOverviewFragment.this._seriesId);
                                    VideoSeriesOverviewFragment.this.getContentPresenter().showFragment((AbstractContentFragment)object, true, true);
                                    return;
                                }
                            } else {
                                VideoSeriesOverviewFragment.this.showConversionOrStartPurchase();
                            }
                        }
                    });
                }
                VideoSeriesOverviewFragment.this.bindLocalVideoInfos();
            }

        });
    }

}
