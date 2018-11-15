/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.android.trivialdrivesample.util.IabHelper;
import com.example.android.trivialdrivesample.util.IabResult;
import com.example.android.trivialdrivesample.util.Inventory;
import com.example.android.trivialdrivesample.util.Purchase;
import de.cisha.android.board.account.model.AfterPurchaseInformation;
import de.cisha.android.board.account.model.ProductInformation;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.PurchaseService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.util.Logger;
import java.util.List;
import org.json.JSONObject;

class PurchaseService
implements IabHelper.QueryInventoryFinishedListener {
    PurchaseService() {
    }

    @Override
    public void onQueryInventoryFinished(IabResult object, Inventory object2) {
        1.this.val$iabHelper.dispose();
        if (object.isSuccess()) {
            Logger.getInstance().debug("Purchase Service", "trying to restore purchases");
            for (Object object3 : 1.this.val$resultList) {
                Object object4 = object3.getSku();
                if (!object2.hasPurchase((String)object4)) continue;
                Purchase purchase = object2.getPurchase((String)object4);
                object4 = new LoadCommandCallbackWithTimeout<AfterPurchaseInformation>((String)object4, purchase){
                    final /* synthetic */ Purchase val$purchase;
                    final /* synthetic */ String val$sku;
                    {
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
                        1.this.this$1.this$0.consumePurchase(1.this.this$1.val$context, this.val$purchase, 1.this.this$1.this$0.getConsumeCallbackForPurchase(this.val$purchase));
                    }
                };
                if (object3.getType() == ProductInformation.ProductType.PREMIUM_SUBSCRIPTION_AUTORENEWING) {
                    1.this.this$1.this$0.verifySubscriptionToken(purchase, 1.this.this$1.val$restoreCallback);
                    continue;
                }
                if (object3.getType() == ProductInformation.ProductType.PREMIUM_SUBSCRIPTION) {
                    1.this.this$1.this$0.verifySubscriptionToken(purchase, (LoadCommandCallback)object4);
                    continue;
                }
                Logger.getInstance().debug("purchasetoken", purchase.getToken());
                Object object5 = 1.this.this$1.this$0._context.getSharedPreferences(de.cisha.android.board.service.PurchaseService.PURCHASE_SERVICE_PREF, 0);
                object3 = new StringBuilder();
                object3.append(purchase.getOrderId());
                object3.append(1.this.this$1.this$0.PREF_PURCHASE_INFO_CONTENT_ID_SUFFIX);
                object3 = object5.getString(object3.toString(), "");
                Object object6 = new StringBuilder();
                object6.append(purchase.getOrderId());
                object6.append(1.this.this$1.this$0.PREF_PURCHASE_INFO_CONTENT_TYPE_SUFFIX);
                object5 = object5.getString(object6.toString(), "");
                if (!object3.isEmpty() && !object5.isEmpty()) {
                    object6 = Logger.getInstance();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("verify pricetier purchase with id ");
                    stringBuilder.append((String)object3);
                    stringBuilder.append(" type ");
                    stringBuilder.append((String)object5);
                    object6.debug("Purchase Service", stringBuilder.toString());
                    1.this.this$1.this$0.verifyPriceTierPurchaseToken(purchase, (String)object3, (String)object5, (LoadCommandCallback)object4);
                    continue;
                }
                object4 = Logger.getInstance();
                object3 = new StringBuilder();
                object3.append("consuming without charging - error. OrderId:");
                object3.append(purchase.getOrderId());
                object4.error("Purchase Service", object3.toString());
                1.this.this$1.this$0.consumePurchase(1.this.this$1.val$context, purchase, 1.this.this$1.this$0.getConsumeCallbackForPurchase(purchase));
            }
            ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.SHOP, "Restore", "not updated");
            1.this.this$1.val$restoreCallback.loadingSucceded(null);
            return;
        }
        object2 = 1.this.this$1.val$restoreCallback;
        APIStatusCode aPIStatusCode = 1.this.this$1.this$0.iabResponseToStatusCode((IabResult)object);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("error loading products from playstore api:");
        stringBuilder.append(object.getMessage());
        object2.loadingFailed(aPIStatusCode, stringBuilder.toString(), null, null);
        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.SHOP, "Restore", "error");
    }

}
