/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.DisplayMetrics
 *  android.util.SparseArray
 *  android.util.TypedValue
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 */
package de.cisha.android.board.video;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.android.trivialdrivesample.util.SkuDetails;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.video.VideoFragment;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoInformation;
import de.cisha.android.board.video.model.VideoSeries;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.storage.LocalVideoInfo;
import de.cisha.android.board.video.view.SingleVideoListItem;
import de.cisha.android.board.video.view.VideoSeriesHeaderView;
import java.util.List;
import java.util.Map;

class VideoSeriesOverviewFragment
implements Runnable {
    final /* synthetic */ VideoSeries val$result;

    VideoSeriesOverviewFragment(VideoSeries videoSeries) {
        this.val$result = videoSeries;
    }

    @Override
    public void run() {
        VideoSeriesOverviewFragment.this._videoSeriesHeader.setVideoSeries(this.val$result);
        if (VideoSeriesOverviewFragment.this._skuDetails != null && VideoSeriesOverviewFragment.this._skuDetails.get(this.val$result.getPriceCategoryId()) != null) {
            VideoSeriesOverviewFragment.this._videoSeriesHeader.setViewPriceText(((SkuDetails)VideoSeriesOverviewFragment.this._skuDetails.get(this.val$result.getPriceCategoryId())).getPrice());
        } else {
            VideoSeriesOverviewFragment.this._videoSeriesHeader.setViewPriceText("?");
        }
        VideoSeriesOverviewFragment.this._contentGroup.removeAllViews();
        VideoSeriesOverviewFragment.this._mapSeriesIdView.clear();
        for (final VideoInformation videoInformation : this.val$result.getVideoInformationList()) {
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

}
