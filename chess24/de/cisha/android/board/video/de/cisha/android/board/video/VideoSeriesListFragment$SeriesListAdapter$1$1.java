/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.video;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.video.VideoSeriesListFragment;
import de.cisha.android.board.video.model.VideoSeriesInformation;
import java.util.List;
import org.json.JSONObject;

class VideoSeriesListFragment.SeriesListAdapter
implements LoadCommandCallback<Void> {
    VideoSeriesListFragment.SeriesListAdapter() {
    }

    @Override
    public void loadingCancelled() {
        1.this.this$1.this$0.hideDialogWaiting();
    }

    @Override
    public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        1.this.this$1.this$0.hideDialogWaiting();
        1.this.this$1.this$0.showViewForErrorCode(aPIStatusCode, null);
    }

    @Override
    public void loadingSucceded(Void void_) {
        1.this.this$1.this$0.hideDialogWaiting();
        1.this.val$series.setIsPurchased(true);
        1.this.val$series.setIsAccessible(true);
        1.this.this$1.this$0._seriesListAdapter.notifyItemChanged(1.this.val$position);
    }
}
