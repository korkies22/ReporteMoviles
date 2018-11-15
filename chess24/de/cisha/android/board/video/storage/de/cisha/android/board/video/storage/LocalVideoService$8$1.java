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
import de.cisha.android.board.video.model.Video;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.storage.ILocalVideoStorage;
import de.cisha.android.board.video.storage.LocalVideoInfo;
import de.cisha.android.board.video.storage.LocalVideoService;
import java.util.HashSet;
import org.json.JSONObject;

class LocalVideoService
implements Runnable {
    final /* synthetic */ ISerializedRepresentationHolder val$result;

    LocalVideoService(ISerializedRepresentationHolder iSerializedRepresentationHolder) {
        this.val$result = iSerializedRepresentationHolder;
    }

    @Override
    public void run() {
        if (8.this.this$0._localVideoStorage.isVideoSeriesLocalAvailable(8.this.val$uniqueVideoId.getSeriesId())) {
            8.this.this$0._localVideoStorage.putVideoJSON(8.this.val$uniqueVideoId.getSeriesId(), 8.this.val$uniqueVideoId.getVideoId(), this.val$result.getJSONData());
            8.this.this$0.getLocalVideo(8.this.val$uniqueVideoId.getVideoId(), 8.this.val$uniqueVideoId.getSeriesId()).setVideoObjectAvailable(true);
            8.this.this$0.loadVideoData(8.this.val$uniqueVideoId, ((Video)this.val$result.getObject()).getVideoUrlMp4());
        }
        8.this.this$0._setVideosUpdating.remove(8.this.val$uniqueVideoId);
    }
}
