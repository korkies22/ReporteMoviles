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
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class VideoSeriesListFragment
extends LoadCommandCallbackWithTimeout<SparseArray<SkuDetails>> {
    VideoSeriesListFragment() {
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        VideoSeriesListFragment.this.hideDialogWaiting();
        VideoSeriesListFragment.this.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

            @Override
            public void performReload() {
                VideoSeriesListFragment.this.loadProductPriceInformation();
            }
        }, false);
        VideoSeriesListFragment.this.loadOfflineSeries();
    }

    @Override
    protected void succeded(SparseArray<SkuDetails> sparseArray) {
        VideoSeriesListFragment.this._productPriceMap = sparseArray;
        VideoSeriesListFragment.this.hideDialogWaiting();
        VideoSeriesListFragment.this.loadSeriesList();
    }

}
