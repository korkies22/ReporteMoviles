/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.video.VideoSeriesListFragment;

class VideoSeriesListFragment
implements Runnable {
    final /* synthetic */ APIStatusCode val$errorCode;

    VideoSeriesListFragment(APIStatusCode aPIStatusCode) {
        this.val$errorCode = aPIStatusCode;
    }

    @Override
    public void run() {
        5.this.this$0.hideDialogWaiting();
        5.this.this$0.showViewForErrorCode(this.val$errorCode, new IErrorPresenter.IReloadAction(){

            @Override
            public void performReload() {
                5.this.this$0.loadSeriesList();
            }
        });
    }

}
