/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.util.SparseArray
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import android.app.Activity;
import android.util.SparseArray;
import com.example.android.trivialdrivesample.util.SkuDetails;
import de.cisha.android.board.account.model.AfterPurchaseInformation;
import de.cisha.android.board.account.model.Product;
import de.cisha.android.board.account.model.ProductInformation;
import de.cisha.android.board.service.IPurchaseService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.chess.util.Logger;
import java.util.List;
import org.json.JSONObject;

class ViedeoService
implements LoadCommandCallback<SparseArray<SkuDetails>> {
    final /* synthetic */ LoadCommandCallback val$callback;
    final /* synthetic */ Activity val$callbackActivity;
    final /* synthetic */ int val$priceCategoryId;
    final /* synthetic */ VideoSeriesId val$seriesId;

    ViedeoService(int n, VideoSeriesId videoSeriesId, Activity activity, LoadCommandCallback loadCommandCallback) {
        this.val$priceCategoryId = n;
        this.val$seriesId = videoSeriesId;
        this.val$callbackActivity = activity;
        this.val$callback = loadCommandCallback;
    }

    @Override
    public void loadingCancelled() {
        this.val$callback.loadingCancelled();
    }

    @Override
    public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        this.val$callback.loadingFailed(aPIStatusCode, string, list, jSONObject);
    }

    @Override
    public void loadingSucceded(SparseArray<SkuDetails> object) {
        if ((object = (SkuDetails)object.get(this.val$priceCategoryId)) != null) {
            Logger logger = Logger.getInstance();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("purchase intent for id: ");
            stringBuilder.append(this.val$seriesId.getUuid());
            stringBuilder.append(" and price: ");
            stringBuilder.append(this.val$priceCategoryId);
            logger.debug("Purchase Service", stringBuilder.toString());
            ViedeoService.this._purchaseService.purchaseProduct(this.val$callbackActivity, new Product((SkuDetails)object, new ProductInformation(object.getSku(), this.val$seriesId.getUuid(), "series")), new LoadCommandCallback<AfterPurchaseInformation>(){

                @Override
                public void loadingCancelled() {
                    1.this.val$callback.loadingCancelled();
                }

                @Override
                public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                    1.this.val$callback.loadingFailed(aPIStatusCode, string, list, jSONObject);
                }

                @Override
                public void loadingSucceded(AfterPurchaseInformation afterPurchaseInformation) {
                    1.this.val$callback.loadingSucceded(null);
                }
            });
            return;
        }
        object = this.val$callback;
        APIStatusCode aPIStatusCode = APIStatusCode.INTERNAL_PURCHASE_ERROR;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("There are no product info from google for the given price category ");
        stringBuilder.append(this.val$priceCategoryId);
        object.loadingFailed(aPIStatusCode, stringBuilder.toString(), null, null);
    }

}
