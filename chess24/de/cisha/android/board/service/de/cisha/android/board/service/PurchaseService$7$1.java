/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import com.example.android.trivialdrivesample.util.IabHelper;
import com.example.android.trivialdrivesample.util.IabResult;
import com.example.android.trivialdrivesample.util.Inventory;
import com.example.android.trivialdrivesample.util.SkuDetails;
import de.cisha.android.board.service.PurchaseService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.util.Logger;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONObject;

class PurchaseService
implements IabHelper.QueryInventoryFinishedListener {
    PurchaseService() {
    }

    @Override
    public void onQueryInventoryFinished(IabResult object, Inventory object2) {
        7.this.val$iabHelper.dispose();
        if (object.isSuccess()) {
            object = new LinkedList();
            for (String string : 7.this.val$skus) {
                Object object3 = object2.getSkuDetails(string);
                if (object3 != null) {
                    object.add(object3);
                    continue;
                }
                object3 = Logger.getInstance();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error: product ");
                stringBuilder.append(string);
                stringBuilder.append(" not found in store");
                object3.error("Purchase Service", stringBuilder.toString());
            }
            7.this.val$skuCallback.loadingSucceded(object);
            return;
        }
        object2 = 7.this.val$skuCallback;
        APIStatusCode aPIStatusCode = 7.this.this$0.iabResponseToStatusCode((IabResult)object);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("error loading products from playstore api:");
        stringBuilder.append(object.getMessage());
        object2.loadingFailed(aPIStatusCode, stringBuilder.toString(), null, null);
    }
}
