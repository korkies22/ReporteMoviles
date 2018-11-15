/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.video;

import de.cisha.android.board.LoadingHelper;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.video.model.VideoSeries;
import java.util.List;
import org.json.JSONObject;

class VideoSeriesOverviewFragment
extends LoadCommandCallbackWithTimeout<VideoSeries> {
    final /* synthetic */ LoadingHelper val$loadingHelper;

    VideoSeriesOverviewFragment(LoadingHelper loadingHelper) {
        this.val$loadingHelper = loadingHelper;
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        this.val$loadingHelper.loadingFailed(this);
    }

    @Override
    protected void succeded(VideoSeries videoSeries) {
        VideoSeriesOverviewFragment.this._currentVideoSeries = videoSeries;
        this.val$loadingHelper.loadingCompleted(this);
    }
}
