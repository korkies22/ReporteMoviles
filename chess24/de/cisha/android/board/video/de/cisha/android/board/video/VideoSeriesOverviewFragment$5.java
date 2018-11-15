/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.SparseArray
 *  org.json.JSONObject
 */
package de.cisha.android.board.video;

import android.util.SparseArray;
import com.example.android.trivialdrivesample.util.SkuDetails;
import de.cisha.android.board.LoadingHelper;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.util.Logger;
import java.util.List;
import org.json.JSONObject;

class VideoSeriesOverviewFragment
extends LoadCommandCallbackWithTimeout<SparseArray<SkuDetails>> {
    final /* synthetic */ LoadingHelper val$loadingHelper;

    VideoSeriesOverviewFragment(LoadingHelper loadingHelper) {
        this.val$loadingHelper = loadingHelper;
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        Logger.getInstance().error(de.cisha.android.board.video.VideoSeriesOverviewFragment.class.getName(), string, null);
        this.val$loadingHelper.loadingFailed(this);
    }

    @Override
    protected void succeded(SparseArray<SkuDetails> sparseArray) {
        VideoSeriesOverviewFragment.this._skuDetails = sparseArray;
        Logger.getInstance().debug(de.cisha.android.board.video.VideoSeriesOverviewFragment.class.getName(), "sku details loaded successfully");
        this.val$loadingHelper.loadingCompleted(this);
    }
}
