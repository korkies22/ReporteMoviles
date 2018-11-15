/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import com.example.android.trivialdrivesample.util.Purchase;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.util.Logger;
import java.util.List;
import org.json.JSONObject;

class PurchaseService
extends LoadCommandCallbackWithTimeout<Void> {
    final /* synthetic */ Purchase val$info;

    PurchaseService(Purchase purchase) {
        this.val$info = purchase;
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        Logger.getInstance().error("Purchase Service", string);
    }

    @Override
    protected void succeded(Void object) {
        object = Logger.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("consumed Purchase: ");
        stringBuilder.append(this.val$info.getSku());
        stringBuilder.append("Token: ");
        stringBuilder.append(this.val$info.getToken());
        object.debug("Purchase Service", stringBuilder.toString());
        PurchaseService.this.clearStoredPurchaseInfo(this.val$info.getOrderId());
    }
}
