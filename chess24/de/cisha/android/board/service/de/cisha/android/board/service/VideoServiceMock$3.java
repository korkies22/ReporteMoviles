/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.video.model.Video;

class VideoServiceMock
implements Runnable {
    final /* synthetic */ LoadCommandCallback val$callback;
    final /* synthetic */ Video val$result;

    VideoServiceMock(LoadCommandCallback loadCommandCallback, Video video) {
        this.val$callback = loadCommandCallback;
        this.val$result = video;
    }

    @Override
    public void run() {
        this.val$callback.loadingSucceded(this.val$result);
    }
}
