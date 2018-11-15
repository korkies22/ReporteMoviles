/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import de.cisha.android.board.account.model.AfterPurchaseInformation;
import de.cisha.android.board.service.ViedeoService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class ViedeoService
implements LoadCommandCallback<AfterPurchaseInformation> {
    ViedeoService() {
    }

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
}
