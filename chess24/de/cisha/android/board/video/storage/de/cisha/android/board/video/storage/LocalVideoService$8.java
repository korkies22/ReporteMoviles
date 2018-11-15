/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  org.json.JSONObject
 */
package de.cisha.android.board.video.storage;

import android.net.Uri;
import de.cisha.android.board.service.ISerializedRepresentationHolder;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.video.model.Video;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.storage.ILocalVideoStorage;
import de.cisha.android.board.video.storage.LocalVideoInfo;
import de.cisha.android.board.video.storage.LocalVideoService;
import java.util.HashSet;
import java.util.List;
import org.json.JSONObject;

class LocalVideoService
extends LoadCommandCallbackWithTimeout<ISerializedRepresentationHolder<Video>> {
    final /* synthetic */ LocalVideoService.UniqueVideoId val$uniqueVideoId;

    LocalVideoService(int n, LocalVideoService.UniqueVideoId uniqueVideoId) {
        this.val$uniqueVideoId = uniqueVideoId;
        super(n);
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        LocalVideoService.this._setVideosUpdating.remove(this.val$uniqueVideoId);
    }

    @Override
    protected void succeded(final ISerializedRepresentationHolder<Video> iSerializedRepresentationHolder) {
        LocalVideoService.this.runOnBackgroundThread(new Runnable(){

            @Override
            public void run() {
                if (LocalVideoService.this._localVideoStorage.isVideoSeriesLocalAvailable(8.this.val$uniqueVideoId.getSeriesId())) {
                    LocalVideoService.this._localVideoStorage.putVideoJSON(8.this.val$uniqueVideoId.getSeriesId(), 8.this.val$uniqueVideoId.getVideoId(), iSerializedRepresentationHolder.getJSONData());
                    LocalVideoService.this.getLocalVideo(8.this.val$uniqueVideoId.getVideoId(), 8.this.val$uniqueVideoId.getSeriesId()).setVideoObjectAvailable(true);
                    LocalVideoService.this.loadVideoData(8.this.val$uniqueVideoId, ((Video)iSerializedRepresentationHolder.getObject()).getVideoUrlMp4());
                }
                LocalVideoService.this._setVideosUpdating.remove(8.this.val$uniqueVideoId);
            }
        });
    }

}
