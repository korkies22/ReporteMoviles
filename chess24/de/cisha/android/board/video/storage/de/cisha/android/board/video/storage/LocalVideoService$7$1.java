/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.video.storage;

import de.cisha.android.board.service.ISerializedRepresentationHolder;
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
implements Runnable {
    final /* synthetic */ ISerializedRepresentationHolder val$result;

    LocalVideoService(ISerializedRepresentationHolder iSerializedRepresentationHolder) {
        this.val$result = iSerializedRepresentationHolder;
    }

    @Override
    public void run() {
        if (7.this.this$0._localVideoStorage.isVideoSeriesLocalAvailable(7.this.val$seriesId)) {
            Object object;
            Object object2 = new LinkedList<VideoId>();
            Object object3 = ((VideoSeries)this.val$result.getObject()).getVideoInformationList();
            7.this.this$0._localVideoStorage.putVideoSeriesJSON(7.this.val$seriesId, this.val$result.getJSONData());
            object3 = object3.iterator();
            while (object3.hasNext()) {
                object = ((VideoInformation)object3.next()).getId();
                object2.add(object);
                7.this.this$0._localVideoStorage.putVideoJSON(7.this.val$seriesId, (VideoId)object, null);
            }
            object3 = 7.this.this$0.getLocalVideoSeries(7.this.val$seriesId);
            object3.setSeriesObjectAvailable(true);
            object = new LinkedList();
            object2 = object2.iterator();
            while (object2.hasNext()) {
                VideoId videoId = (VideoId)object2.next();
                object.add(7.this.this$0.getLocalVideo(videoId, 7.this.val$seriesId));
                7.this.this$0.loadVideo(new LocalVideoService.UniqueVideoId(7.this.val$seriesId, videoId));
            }
            object3.setLocalVideoList((List<LocalVideoInfo>)object);
        }
        7.this.this$0._setSeriesUpdating.remove(7.this.val$seriesId);
    }
}
