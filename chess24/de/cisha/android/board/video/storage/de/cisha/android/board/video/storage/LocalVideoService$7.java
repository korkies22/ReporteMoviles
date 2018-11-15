/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.video.storage;

import de.cisha.android.board.service.ISerializedRepresentationHolder;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoInformation;
import de.cisha.android.board.video.model.VideoSeries;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.storage.ILocalVideoStorage;
import de.cisha.android.board.video.storage.LocalVideoInfo;
import de.cisha.android.board.video.storage.LocalVideoSeriesInfo;
import de.cisha.android.board.video.storage.LocalVideoService;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.json.JSONObject;

class LocalVideoService
extends LoadCommandCallbackWithTimeout<ISerializedRepresentationHolder<VideoSeries>> {
    final /* synthetic */ VideoSeriesId val$seriesId;

    LocalVideoService(VideoSeriesId videoSeriesId) {
        this.val$seriesId = videoSeriesId;
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        LocalVideoService.this._setSeriesUpdating.remove(this.val$seriesId);
    }

    @Override
    protected void succeded(final ISerializedRepresentationHolder<VideoSeries> iSerializedRepresentationHolder) {
        LocalVideoService.this.runOnBackgroundThread(new Runnable(){

            @Override
            public void run() {
                if (LocalVideoService.this._localVideoStorage.isVideoSeriesLocalAvailable(7.this.val$seriesId)) {
                    Object object;
                    Object object2 = new LinkedList<VideoId>();
                    Object object3 = ((VideoSeries)iSerializedRepresentationHolder.getObject()).getVideoInformationList();
                    LocalVideoService.this._localVideoStorage.putVideoSeriesJSON(7.this.val$seriesId, iSerializedRepresentationHolder.getJSONData());
                    object3 = object3.iterator();
                    while (object3.hasNext()) {
                        object = ((VideoInformation)object3.next()).getId();
                        object2.add(object);
                        LocalVideoService.this._localVideoStorage.putVideoJSON(7.this.val$seriesId, (VideoId)object, null);
                    }
                    object3 = LocalVideoService.this.getLocalVideoSeries(7.this.val$seriesId);
                    object3.setSeriesObjectAvailable(true);
                    object = new LinkedList();
                    object2 = object2.iterator();
                    while (object2.hasNext()) {
                        VideoId videoId = (VideoId)object2.next();
                        object.add(LocalVideoService.this.getLocalVideo(videoId, 7.this.val$seriesId));
                        LocalVideoService.this.loadVideo(new LocalVideoService.UniqueVideoId(7.this.val$seriesId, videoId));
                    }
                    object3.setLocalVideoList((List<LocalVideoInfo>)object);
                }
                LocalVideoService.this._setSeriesUpdating.remove(7.this.val$seriesId);
            }
        });
    }

}
