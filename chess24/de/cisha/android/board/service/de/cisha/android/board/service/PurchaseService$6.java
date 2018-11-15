/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import com.example.android.trivialdrivesample.util.IabHelper;
import com.example.android.trivialdrivesample.util.IabResult;
import com.example.android.trivialdrivesample.util.Purchase;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class PurchaseService
implements IabHelper.OnIabSetupFinishedListener {
    final /* synthetic */ LoadCommandCallback val$callback;
    final /* synthetic */ IabHelper val$iabHelper;
    final /* synthetic */ Purchase val$purchase;

    PurchaseService(IabHelper iabHelper, Purchase purchase, LoadCommandCallback loadCommandCallback) {
        this.val$iabHelper = iabHelper;
        this.val$purchase = purchase;
        this.val$callback = loadCommandCallback;
    }

    @Override
    public void onIabSetupFinished(IabResult iabResult) {
        if (iabResult.isSuccess()) {
            this.val$iabHelper.consumeAsync(this.val$purchase, new IabHelper.OnConsumeFinishedListener(){

                @Override
                public void onConsumeFinished(Purchase object, IabResult iabResult) {
                    if (iabResult.isSuccess()) {
                        6.this.val$callback.loadingSucceded(null);
                    } else {
                        object = 6.this.val$callback;
                        APIStatusCode aPIStatusCode = PurchaseService.this.iabResponseToStatusCode(iabResult);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Error consuming purchase:");
                        stringBuilder.append(iabResult.getMessage());
                        object.loadingFailed(aPIStatusCode, stringBuilder.toString(), null, null);
                    }
                    6.this.val$iabHelper.dispose();
                }
            });
            return;
        }
        this.val$iabHelper.dispose();
        LoadCommandCallback loadCommandCallback = this.val$callback;
        APIStatusCode aPIStatusCode = PurchaseService.this.iabResponseToStatusCode(iabResult);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Error consuming purchase - no connection to iab service:");
        stringBuilder.append(iabResult.getMessage());
        loadCommandCallback.loadingFailed(aPIStatusCode, stringBuilder.toString(), null, null);
    }

}
