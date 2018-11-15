/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 */
package de.cisha.android.board.video;

import android.widget.CompoundButton;
import de.cisha.android.board.video.model.VideoSeries;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.storage.ILocalVideoService;
import de.cisha.android.board.video.storage.LocalVideoSeriesInfo;

class VideoSeriesOverviewFragment
implements CompoundButton.OnCheckedChangeListener {
    final /* synthetic */ CompoundButton val$downloadToggle;

    VideoSeriesOverviewFragment(CompoundButton compoundButton) {
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
}
