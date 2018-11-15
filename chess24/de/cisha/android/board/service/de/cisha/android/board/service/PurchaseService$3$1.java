/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import de.cisha.android.board.account.model.ProductInformation;
import de.cisha.android.board.service.PurchaseService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

class PurchaseService
extends LoadCommandCallbackWithTimeout<Map<Integer, String>> {
    final /* synthetic */ List val$premiumProductIdentifiers;

    PurchaseService(List list) {
        this.val$premiumProductIdentifiers = list;
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        3.this.val$callback.loadingFailed(aPIStatusCode, string, list, jSONObject);
    }

    @Override
    protected void succeded(Map<Integer, String> object) {
        for (String string : object.values()) {
            this.val$premiumProductIdentifiers.add(new ProductInformation(string, "noId", "noType"));
        }
        3.this.val$callback.loadingSucceded(this.val$premiumProductIdentifiers);
    }
}
