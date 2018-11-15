/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.video;

import android.view.View;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.video.VideoFragment;
import de.cisha.android.board.video.VideoSeriesOverviewFragment;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoInformation;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.storage.LocalVideoInfo;
import java.util.Map;

class VideoSeriesOverviewFragment
implements View.OnClickListener {
    final /* synthetic */ VideoInformation val$video;

    VideoSeriesOverviewFragment(VideoInformation videoInformation) {
        this.val$video = videoInformation;
    }

    public void onClick(View object) {
        if (this.val$video.isAccessible()) {
            object = (LocalVideoInfo)7.this.this$0._mapLocalVideoInfos.get(this.val$video.getId());
            if (object == null || object.getState() == LocalVideoInfo.LocalVideoServiceDownloadState.COMPLETED) {
                object = VideoFragment.createVideoFragment(this.val$video.getId(), 7.this.this$0._seriesId);
                7.this.this$0.getContentPresenter().showFragment((AbstractContentFragment)object, true, true);
                return;
            }
        } else {
            7.this.this$0.showConversionOrStartPurchase();
        }
    }
}
