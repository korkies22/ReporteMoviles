/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import android.content.Context;
import com.example.android.trivialdrivesample.util.IabHelper;
import com.example.android.trivialdrivesample.util.IabResult;
import com.example.android.trivialdrivesample.util.Inventory;
import com.example.android.trivialdrivesample.util.SkuDetails;
import de.cisha.android.board.account.model.Product;
import de.cisha.android.board.account.model.ProductInformation;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.util.Logger;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONObject;

class PurchaseService
extends LoadCommandCallbackWithTimeout<List<ProductInformation>> {
    final /* synthetic */ Context val$context;
    final /* synthetic */ LoadCommandCallback val$productCallback;

    PurchaseService(Context context, LoadCommandCallback loadCommandCallback) {
        this.val$context = context;
        this.val$productCallback = loadCommandCallback;
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        this.val$productCallback.loadingFailed(aPIStatusCode, string, list, null);
    }

    @Override
    protected void succeded(final List<ProductInformation> list) {
        final IabHelper iabHelper = PurchaseService.this.createIabHelper(this.val$context);
        iabHelper.startSetup(new IabHelper.OnIabSetupFinishedListener(){

            @Override
            public void onIabSetupFinished(IabResult object) {
                if (object.isSuccess()) {
                    object = new LinkedList();
                    Iterator iterator = list.iterator();
                    while (iterator.hasNext()) {
                        object.add(((ProductInformation)iterator.next()).getSku());
                    }
                    iabHelper.queryInventoryAsync(true, (List<String>)object, new IabHelper.QueryInventoryFinishedListener(){

                        @Override
                        public void onQueryInventoryFinished(IabResult object, Inventory object2) {
                            iabHelper.dispose();
                            if (object.isSuccess()) {
                                object = new LinkedList();
                                for (Object object3 : list) {
                                    String string = object3.getSku();
                                    Object object4 = object2.getSkuDetails(string);
                                    if (object4 != null) {
                                        object.add(new Product((SkuDetails)object4, (ProductInformation)object3));
                                        continue;
                                    }
                                    object3 = Logger.getInstance();
                                    object4 = new StringBuilder();
                                    object4.append("Error: product ");
                                    object4.append(string);
                                    object4.append(" not found in store");
                                    object3.error("Purchase Service", object4.toString());
                                }
                                1.this.val$productCallback.loadingSucceded(object);
                                return;
                            }
                            object2 = 1.this.val$productCallback;
                            APIStatusCode aPIStatusCode = PurchaseService.this.iabResponseToStatusCode((IabResult)object);
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("error loading products from playstore api:");
                            stringBuilder.append(object.getMessage());
                            object2.loadingFailed(aPIStatusCode, stringBuilder.toString(), null, null);
                        }
                    });
                    return;
                }
                LoadCommandCallback loadCommandCallback = 1.this.val$productCallback;
                APIStatusCode aPIStatusCode = PurchaseService.this.iabResponseToStatusCode((IabResult)object);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("error loading products from playstore api: Error connecting to iabservice ");
                stringBuilder.append(object.getMessage());
                loadCommandCallback.loadingFailed(aPIStatusCode, stringBuilder.toString(), null, null);
            }

        });
    }

}
