/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.video.VideoFragment;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoSeriesId;

class VideoFragment
implements Runnable {
    final /* synthetic */ APIStatusCode val$errorCode;

    VideoFragment(APIStatusCode aPIStatusCode) {
        this.val$errorCode = aPIStatusCode;
    }

    @Override
    public void run() {
        2.this.this$0.hideDialogWaiting();
        2.this.this$0.showViewForErrorCode(this.val$errorCode, new IErrorPresenter.IReloadAction(){

            @Override
            public void performReload() {
                2.this.this$0.loadVideo(2.this.val$videoId, 2.this.val$seriesId);
            }
        }, true);
    }

}
