/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.jsonparser.JSONVideoSeriesInfoListParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.video.model.VideoSeriesInformation;
import java.util.List;

class VideoServiceMock
implements Runnable {
    final /* synthetic */ LoadCommandCallback val$callback;
    final /* synthetic */ List val$result;

    VideoServiceMock(LoadCommandCallback loadCommandCallback, List list) {
        this.val$callback = loadCommandCallback;
        this.val$result = list;
    }

    @Override
    public void run() {
        this.val$callback.loadingSucceded(new JSONVideoSeriesInfoListParser.VideoSeriesInfoList(this.val$result, 1));
    }
}
