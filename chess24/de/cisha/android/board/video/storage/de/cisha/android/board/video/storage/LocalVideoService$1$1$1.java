/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.storage;

import de.cisha.android.board.service.jsonparser.JSONVideoSeriesInfoListParser;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.model.VideoSeriesInformation;
import de.cisha.android.board.video.storage.LocalVideoService;
import java.util.List;

class LocalVideoService
implements Runnable {
    final /* synthetic */ JSONVideoSeriesInfoListParser.VideoSeriesInfoList val$result;

    LocalVideoService(JSONVideoSeriesInfoListParser.VideoSeriesInfoList videoSeriesInfoList) {
        this.val$result = videoSeriesInfoList;
    }

    @Override
    public void run() {
        for (VideoSeriesInformation videoSeriesInformation : this.val$result.getList()) {
            if (videoSeriesInformation.isAccessible() || !1.this.val$localVideoSeries.contains(videoSeriesInformation.getVideoSeriesId())) continue;
            1.this.this$1.this$0.disableOfflineModeForSeries(videoSeriesInformation.getVideoSeriesId());
        }
    }
}
