/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import android.app.Activity;
import android.content.Context;
import com.example.android.trivialdrivesample.util.Purchase;
import de.cisha.android.board.account.model.AfterPurchaseInformation;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.PurchaseService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.user.User;
import java.util.List;
import org.json.JSONObject;

class PurchaseService
extends LoadCommandCallbackWithTimeout<AfterPurchaseInformation> {
    final /* synthetic */ Purchase val$info;

    PurchaseService(Purchase purchase) {
        this.val$info = purchase;
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String object, List<LoadFieldError> object2, JSONObject object3) {
        1.this.this$1.val$callback.loadingFailed(aPIStatusCode, (String)object, (List<LoadFieldError>)object2, null);
        if (aPIStatusCode == APIStatusCode.API_ERROR_PAYMENT_FAILED) {
            1.this.this$1.this$0.consumePurchase((Context)1.this.this$1.val$callbackActivity, this.val$info, 1.this.this$1.this$0.getConsumeCallbackForPurchase(this.val$info));
        }
        object = ServiceProvider.getInstance().getTrackingService();
        object2 = ITrackingService.TrackingCategory.SHOP;
        object3 = new StringBuilder();
        object3.append("API error: ");
        object3.append((Object)aPIStatusCode);
        object.trackEvent((ITrackingService.TrackingCategory)((Object)object2), "InApp purchase failed", object3.toString());
    }

    @Override
    protected void succeded(AfterPurchaseInformation afterPurchaseInformation) {
        ServiceProvider.getInstance().getProfileDataService().getUserData((LoadCommandCallback<User>)new LoadCommandCallbackWithTimeout<User>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
            }

            @Override
            protected void succeded(User user) {
            }
        });
        1.this.this$1.val$callback.loadingSucceded(afterPurchaseInformation);
        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.SHOP, "InApp purchase completed", null);
        1.this.this$1.this$0.consumePurchase((Context)1.this.this$1.val$callbackActivity, this.val$info, 1.this.this$1.this$0.getConsumeCallbackForPurchase(this.val$info));
    }

}
