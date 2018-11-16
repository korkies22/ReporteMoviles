// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video;

import android.view.ViewGroup.LayoutParams;
import android.util.TypedValue;
import android.widget.LinearLayout.LayoutParams;
import android.content.Context;
import de.cisha.android.board.video.model.VideoInformation;
import android.net.Uri;
import android.view.View.OnClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.view.View;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import java.util.concurrent.Executors;
import java.util.HashMap;
import android.content.Intent;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import java.util.Set;
import de.cisha.android.board.mainmenu.view.MenuItem;
import android.content.res.Resources;
import android.app.Activity;
import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;
import de.cisha.android.board.modalfragments.ConversionContext;
import de.cisha.android.board.service.IMembershipService;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.chess.util.Logger;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import android.os.Bundle;
import java.util.Iterator;
import java.util.List;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.video.view.VideoSeriesHeaderView;
import android.widget.TextView;
import com.example.android.trivialdrivesample.util.SkuDetails;
import android.util.SparseArray;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.view.SingleVideoListItem;
import de.cisha.android.board.video.model.VideoId;
import java.util.Map;
import de.cisha.android.board.video.storage.ILocalVideoService;
import de.cisha.android.board.video.model.VideoSeries;
import android.view.ViewGroup;
import de.cisha.android.board.video.storage.LocalVideoInfo;
import de.cisha.android.board.video.storage.LocalVideoSeriesInfo;
import de.cisha.android.board.LoadingHelper;
import de.cisha.android.board.account.PurchaseResultReceiver;
import de.cisha.android.board.AbstractContentFragment;

public class VideoSeriesOverviewFragment extends AbstractContentFragment implements PurchaseResultReceiver, LoadingHelperListener, LocalVideoSeriesChangeListener, LocalVideoStateChangeListener
{
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
        final Map<VideoId, LocalVideoInfo> mapLocalVideoInfos = this._mapLocalVideoInfos;
        final Map<VideoId, SingleVideoListItem> mapSeriesIdView = this._mapSeriesIdView;
        if (mapLocalVideoInfos != null && mapSeriesIdView != null) {
            for (final Map.Entry<VideoId, SingleVideoListItem> entry : mapSeriesIdView.entrySet()) {
                entry.getValue().setLocalVideoInfo(mapLocalVideoInfos.get(entry.getKey()));
            }
        }
    }
    
    private void clearLocalVideoViewBinding() {
        final Iterator<SingleVideoListItem> iterator = this._mapSeriesIdView.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().setLocalVideoInfo(null);
        }
    }
    
    public static VideoSeriesOverviewFragment createVideoOverviewFragment(final VideoSeriesId videoSeriesId) {
        final Bundle arguments = new Bundle();
        if (videoSeriesId != null && videoSeriesId.getUuid() != null) {
            arguments.putString("series_id_key", videoSeriesId.getUuid());
        }
        final VideoSeriesOverviewFragment videoSeriesOverviewFragment = new VideoSeriesOverviewFragment();
        videoSeriesOverviewFragment.setArguments(arguments);
        return videoSeriesOverviewFragment;
    }
    
    private void loadVideoSeries() {
        final LoadingHelper loadingHelper = new LoadingHelper((LoadingHelper.LoadingHelperListener)this);
        final LoadCommandCallbackWithTimeout<SparseArray<SkuDetails>> loadCommandCallbackWithTimeout = new LoadCommandCallbackWithTimeout<SparseArray<SkuDetails>>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                Logger.getInstance().error(VideoSeriesOverviewFragment.class.getName(), s, null);
                loadingHelper.loadingFailed(this);
            }
            
            @Override
            protected void succeded(final SparseArray<SkuDetails> sparseArray) {
                VideoSeriesOverviewFragment.this._skuDetails = sparseArray;
                Logger.getInstance().debug(VideoSeriesOverviewFragment.class.getName(), "sku details loaded successfully");
                loadingHelper.loadingCompleted(this);
            }
        };
        if (this._seriesId != null) {
            final LoadCommandCallbackWithTimeout<VideoSeries> loadCommandCallbackWithTimeout2 = new LoadCommandCallbackWithTimeout<VideoSeries>() {
                @Override
                protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                    loadingHelper.loadingFailed(this);
                }
                
                @Override
                protected void succeded(final VideoSeries videoSeries) {
                    VideoSeriesOverviewFragment.this._currentVideoSeries = videoSeries;
                    loadingHelper.loadingCompleted(this);
                }
            };
            loadingHelper.addLoadable(loadCommandCallbackWithTimeout);
            loadingHelper.addLoadable(loadCommandCallbackWithTimeout2);
            ServiceProvider.getInstance().getPurchaseService().getSkuDetailsMap(loadCommandCallbackWithTimeout);
            ServiceProvider.getInstance().getVideoService().getVideoSeries(this._seriesId, loadCommandCallbackWithTimeout2);
        }
    }
    
    private void setLocalVideoInfos(final List<LocalVideoInfo> list) {
        this._mapLocalVideoInfos.clear();
        if (list != null) {
            for (final LocalVideoInfo localVideoInfo : list) {
                this._mapLocalVideoInfos.put(localVideoInfo.getVideoId(), localVideoInfo);
                localVideoInfo.addListener((LocalVideoInfo.LocalVideoStateChangeListener)this);
            }
        }
        this.bindLocalVideoInfos();
    }
    
    private void setLocalVideoSeries(final LocalVideoSeriesInfo localVideoSeries) {
        this._localVideoSeries = localVideoSeries;
        if (localVideoSeries != null) {
            localVideoSeries.setListener((LocalVideoSeriesInfo.LocalVideoSeriesChangeListener)this);
            if (localVideoSeries.isSeriesObjectAvailable()) {
                final VideoSeries videoSeries = this._localVideoService.getVideoSeries(localVideoSeries.getSeriesId());
                if ((this._currentVideoSeries = videoSeries) != null) {
                    this.videoLoaded(videoSeries);
                }
            }
            this.setLocalVideoInfos(localVideoSeries.getVideoList());
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
        ServiceProvider.getInstance().getVideoService().purchaseVideoSeries(this._seriesId, this._currentVideoSeries.getPriceCategoryId(), this.getActivity(), new LoadCommandCallback<Void>() {
            @Override
            public void loadingCancelled() {
                VideoSeriesOverviewFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        VideoSeriesOverviewFragment.this.hideDialogWaiting();
                    }
                });
            }
            
            @Override
            public void loadingFailed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                VideoSeriesOverviewFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        VideoSeriesOverviewFragment.this.hideDialogWaiting();
                    }
                });
            }
            
            @Override
            public void loadingSucceded(final Void void1) {
                VideoSeriesOverviewFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
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
            final int downloadProgress = (int)(this._localVideoSeries.getDownloadProgress() * 100.0f);
            final long n = this._localVideoSeries.getCurrentLoadedBytes() / 1000000L;
            final long n2 = this._localVideoSeries.getTotalBytes() / 1000000L;
            if (this._setProgressTextAction == null || downloadProgress != this._downloadProgress) {
                this._setProgressTextAction = new Runnable() {
                    @Override
                    public void run() {
                        VideoSeriesOverviewFragment.this._textViewDownloadProgress.setVisibility(0);
                        final TextView access.1800 = VideoSeriesOverviewFragment.this._textViewDownloadProgress;
                        final StringBuilder sb = new StringBuilder();
                        sb.append(downloadProgress);
                        sb.append("% (");
                        sb.append(n);
                        sb.append(" / ");
                        sb.append(n2);
                        sb.append(" MB)");
                        access.1800.setText((CharSequence)sb.toString());
                        VideoSeriesOverviewFragment.this._setProgressTextAction = null;
                    }
                };
                this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        final Runnable access.1900 = VideoSeriesOverviewFragment.this._setProgressTextAction;
                        if (access.1900 != null) {
                            VideoSeriesOverviewFragment.this._textViewDownloadProgress.postDelayed(access.1900, 500L);
                        }
                    }
                });
            }
            this._downloadProgress = downloadProgress;
        }
    }
    
    @Override
    public String getHeaderText(final Resources resources) {
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
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                VideoSeriesOverviewFragment.this.showDialogWaiting(true, true, "", null);
            }
        });
    }
    
    @Override
    public void loadingStop(final boolean b) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
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
    public void onActivityResult(final int n, final int n2, final Intent intent) {
        if (!ServiceProvider.getInstance().getPurchaseService().handleActivityResult(n, n2, intent)) {
            super.onActivityResult(n, n2, intent);
        }
    }
    
    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
        this._mapSeriesIdView = new HashMap<VideoId, SingleVideoListItem>();
        this._mapLocalVideoInfos = new HashMap<VideoId, LocalVideoInfo>();
        arguments = this.getArguments();
        if (arguments != null) {
            this._seriesId = new VideoSeriesId(arguments.getString("series_id_key"));
        }
        this._localVideoService = ServiceProvider.getInstance().getLocalVideoService();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
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
    public View onCreateView(final LayoutInflater layoutInflater, @Nullable final ViewGroup viewGroup, @Nullable final Bundle bundle) {
        final View inflate = layoutInflater.inflate(2131427579, viewGroup, false);
        this._contentGroup = (ViewGroup)inflate.findViewById(2131297229);
        this._videoSeriesHeader = (VideoSeriesHeaderView)inflate.findViewById(2131297225);
        this._textViewDownloadProgress = (TextView)inflate.findViewById(2131297226);
        final CompoundButton compoundButton = (CompoundButton)inflate.findViewById(2131297224);
        compoundButton.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                if (VideoSeriesOverviewFragment.this._currentVideoSeries != null && !VideoSeriesOverviewFragment.this._currentVideoSeries.isAccessible()) {
                    compoundButton.setChecked(false);
                    VideoSeriesOverviewFragment.this.showConversionOrStartPurchase();
                    return;
                }
                if (b) {
                    VideoSeriesOverviewFragment.this._localVideoService.enableOfflineModeForSeries(VideoSeriesOverviewFragment.this._seriesId);
                }
                else {
                    VideoSeriesOverviewFragment.this._localVideoService.disableOfflineModeForSeries(VideoSeriesOverviewFragment.this._seriesId);
                }
                VideoSeriesOverviewFragment.this.setLocalVideoSeries(VideoSeriesOverviewFragment.this._localVideoService.getLocalVideoSeries(VideoSeriesOverviewFragment.this._seriesId));
            }
        });
        compoundButton.setChecked(this._localVideoService.isVideoSeriesOfflineModeEnabled(this._seriesId));
        this._videoSeriesHeader.getViewPriceButton().setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                VideoSeriesOverviewFragment.this.showConversionOrStartPurchase();
            }
        });
        if (this._currentVideoSeries != null) {
            this.videoLoaded(this._currentVideoSeries);
        }
        return inflate;
    }
    
    @Override
    public void onLocalVideoChanged(final boolean b, final List<LocalVideoInfo> list) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                if (b && VideoSeriesOverviewFragment.this._currentVideoSeries == null) {
                    final VideoSeries videoSeries = VideoSeriesOverviewFragment.this._localVideoService.getVideoSeries(VideoSeriesOverviewFragment.this._seriesId);
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
    public void onLocalVideoStateChanged(final LocalVideoInfo localVideoInfo, final boolean b, final float n, final Uri uri, final LocalVideoServiceDownloadState localVideoServiceDownloadState) {
        this.updateDownloadProgress();
    }
    
    @Override
    public boolean showMenu() {
        return false;
    }
    
    protected void videoLoaded(final VideoSeries videoSeries) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                VideoSeriesOverviewFragment.this._videoSeriesHeader.setVideoSeries(videoSeries);
                if (VideoSeriesOverviewFragment.this._skuDetails != null && VideoSeriesOverviewFragment.this._skuDetails.get(videoSeries.getPriceCategoryId()) != null) {
                    VideoSeriesOverviewFragment.this._videoSeriesHeader.setViewPriceText(((SkuDetails)VideoSeriesOverviewFragment.this._skuDetails.get(videoSeries.getPriceCategoryId())).getPrice());
                }
                else {
                    VideoSeriesOverviewFragment.this._videoSeriesHeader.setViewPriceText("?");
                }
                VideoSeriesOverviewFragment.this._contentGroup.removeAllViews();
                VideoSeriesOverviewFragment.this._mapSeriesIdView.clear();
                for (final VideoInformation videoInformation : videoSeries.getVideoInformationList()) {
                    final SingleVideoListItem singleVideoListItem = new SingleVideoListItem((Context)VideoSeriesOverviewFragment.this.getActivity(), videoInformation);
                    VideoSeriesOverviewFragment.this._mapSeriesIdView.put(videoInformation.getId(), singleVideoListItem);
                    VideoSeriesOverviewFragment.this._contentGroup.addView((View)singleVideoListItem);
                    final LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)singleVideoListItem.getLayoutParams();
                    final int n = (int)TypedValue.applyDimension(1, 5.0f, VideoSeriesOverviewFragment.this.getResources().getDisplayMetrics());
                    layoutParams.setMargins(0, n, 0, n);
                    singleVideoListItem.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
                    singleVideoListItem.setOnVideoClickListener((View.OnClickListener)new View.OnClickListener() {
                        public void onClick(final View view) {
                            if (videoInformation.isAccessible()) {
                                final LocalVideoInfo localVideoInfo = VideoSeriesOverviewFragment.this._mapLocalVideoInfos.get(videoInformation.getId());
                                if (localVideoInfo == null || localVideoInfo.getState() == LocalVideoServiceDownloadState.COMPLETED) {
                                    VideoSeriesOverviewFragment.this.getContentPresenter().showFragment(VideoFragment.createVideoFragment(videoInformation.getId(), VideoSeriesOverviewFragment.this._seriesId), true, true);
                                }
                            }
                            else {
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
