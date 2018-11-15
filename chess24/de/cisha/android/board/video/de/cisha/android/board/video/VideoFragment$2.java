/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.video;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.video.model.Video;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoSeriesId;
import java.util.List;
import org.json.JSONObject;

class VideoFragment
extends LoadCommandCallbackWithTimeout<Video> {
    final /* synthetic */ VideoSeriesId val$seriesId;
    final /* synthetic */ VideoId val$videoId;

    VideoFragment(VideoId videoId, VideoSeriesId videoSeriesId) {
        this.val$videoId = videoId;
        this.val$seriesId = videoSeriesId;
    }

    @Override
    protected void failed(final APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        VideoFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                VideoFragment.this.hideDialogWaiting();
                VideoFragment.this.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

                    @Override
                    public void performReload() {
                        VideoFragment.this.loadVideo(2.this.val$videoId, 2.this.val$seriesId);
                    }
                }, true);
            }

        });
    }

    @Override
    protected void succeded(final Video video) {
        VideoFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                VideoFragment.this.hideDialogWaiting();
                VideoFragment.this.videoLoaded(video);
            }
        });
    }

}
