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
import de.cisha.android.board.service.PurchaseService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class PurchaseService
implements IabHelper.OnConsumeFinishedListener {
    PurchaseService() {
    }

    @Override
    public void onConsumeFinished(Purchase object, IabResult iabResult) {
        if (iabResult.isSuccess()) {
            6.this.val$callback.loadingSucceded(null);
        } else {
            object = 6.this.val$callback;
            APIStatusCode aPIStatusCode = 6.this.this$0.iabResponseToStatusCode(iabResult);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error consuming purchase:");
            stringBuilder.append(iabResult.getMessage());
            object.loadingFailed(aPIStatusCode, stringBuilder.toString(), null, null);
        }
        6.this.val$iabHelper.dispose();
    }
}
