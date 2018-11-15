/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.video;

import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class VideoSeriesOverviewFragment
implements LoadCommandCallback<Void> {
    VideoSeriesOverviewFragment() {
    }

    @Override
    public void loadingCancelled() {
        VideoSeriesOverviewFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                VideoSeriesOverviewFragment.this.hideDialogWaiting();
            }
        });
    }

    @Override
    public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        VideoSeriesOverviewFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                VideoSeriesOverviewFragment.this.hideDialogWaiting();
            }
        });
    }

    @Override
    public void loadingSucceded(Void void_) {
        VideoSeriesOverviewFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                VideoSeriesOverviewFragment.this.hideDialogWaiting();
                VideoSeriesOverviewFragment.this.loadVideoSeries();
            }
        });
    }

}
