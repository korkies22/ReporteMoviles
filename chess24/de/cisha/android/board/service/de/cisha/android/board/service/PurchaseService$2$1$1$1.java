/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import android.content.Context;
import com.example.android.trivialdrivesample.util.Purchase;
import de.cisha.android.board.account.model.AfterPurchaseInformation;
import de.cisha.android.board.service.PurchaseService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.util.Logger;
import java.util.List;
import org.json.JSONObject;

class PurchaseService
extends LoadCommandCallbackWithTimeout<AfterPurchaseInformation> {
    final /* synthetic */ Purchase val$purchase;
    final /* synthetic */ String val$sku;

    PurchaseService(String string, Purchase purchase) {
        this.val$sku = string;
        this.val$purchase = purchase;
    }

    @Override
    protected void failed(APIStatusCode object, String charSequence, List<LoadFieldError> list, JSONObject jSONObject) {
        object = Logger.getInstance();
        charSequence = new StringBuilder();
        charSequence.append("restoring ");
        charSequence.append(this.val$sku);
        charSequence.append(" failed");
        object.debug("Purchase Service", charSequence.toString());
    }

    @Override
    protected void succeded(AfterPurchaseInformation object) {
        object = Logger.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("restoring ");
        stringBuilder.append(this.val$sku);
        stringBuilder.append(" succesful - consuming it");
        object.debug("Purchase Service", stringBuilder.toString());
        1.this.this$2.this$1.this$0.consumePurchase(1.this.this$2.this$1.val$context, this.val$purchase, 1.this.this$2.this$1.this$0.getConsumeCallbackForPurchase(this.val$purchase));
    }
}
