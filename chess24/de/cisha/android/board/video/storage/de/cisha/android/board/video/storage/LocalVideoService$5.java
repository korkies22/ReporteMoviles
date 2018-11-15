/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.storage;

import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.storage.ILocalVideoStorage;

class LocalVideoService
implements Runnable {
    final /* synthetic */ VideoSeriesId val$seriesId;

    LocalVideoService(VideoSeriesId videoSeriesId) {
        this.val$seriesId = videoSeriesId;
    }

    @Override
    public void run() {
        if (LocalVideoService.this._localVideoStorage.setVideoSeriesAsLocal(this.val$seriesId, true)) {
            LocalVideoService.this.loadVideoSeries(this.val$seriesId);
            LocalVideoService.this.scheduleUpdateMissingDataProcess();
        }
    }
}
