/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.video.model.VideoSeries;

class VideoServiceMock
implements Runnable {
    final /* synthetic */ LoadCommandCallback val$callback;
    final /* synthetic */ VideoSeries val$result;

    VideoServiceMock(LoadCommandCallback loadCommandCallback, VideoSeries videoSeries) {
        this.val$callback = loadCommandCallback;
        this.val$result = videoSeries;
    }

    @Override
    public void run() {
        this.val$callback.loadingSucceded(this.val$result);
    }
}
