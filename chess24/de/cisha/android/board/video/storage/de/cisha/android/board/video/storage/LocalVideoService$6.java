/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.DownloadManager
 *  android.content.Context
 *  android.net.Uri
 */
package de.cisha.android.board.video.storage;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.storage.ILocalVideoStorage;
import de.cisha.android.board.video.storage.LocalVideoInfo;
import de.cisha.android.board.video.storage.LocalVideoSeriesInfo;
import java.io.File;
import java.util.Iterator;
import java.util.List;

class LocalVideoService
implements Runnable {
    final /* synthetic */ VideoSeriesId val$seriesId;

    LocalVideoService(VideoSeriesId videoSeriesId) {
        this.val$seriesId = videoSeriesId;
    }

    @Override
    public void run() {
        if (LocalVideoService.this._localVideoStorage.setVideoSeriesAsLocal(this.val$seriesId, false)) {
            LocalVideoService.this.getLocalVideoSeries(this.val$seriesId).setSeriesObjectAvailable(false);
            Object object = LocalVideoService.this.getLocalVideoSeries(this.val$seriesId).getVideoList();
            LocalVideoService.this.getLocalVideoSeries(this.val$seriesId).setLocalVideoList(null);
            if (object != null) {
                DownloadManager downloadManager = (DownloadManager)LocalVideoService.this._context.getSystemService("download");
                object = object.iterator();
                while (object.hasNext()) {
                    LocalVideoInfo localVideoInfo = (LocalVideoInfo)object.next();
                    downloadManager.remove(new long[]{LocalVideoService.this._localVideoStorage.getDownloadIdForVideo(this.val$seriesId, localVideoInfo.getVideoId())});
                    localVideoInfo.setDownloadProgress(0L, 0L);
                    if (localVideoInfo.getVideoFile() != null) {
                        new File(localVideoInfo.getVideoFile().getPath()).delete();
                    }
                    localVideoInfo.setVideoFile(null);
                    localVideoInfo.setVideoObjectAvailable(false);
                }
            }
            LocalVideoService.this._localVideoStorage.removeAllDataForVideoSeries(this.val$seriesId);
        }
    }
}
