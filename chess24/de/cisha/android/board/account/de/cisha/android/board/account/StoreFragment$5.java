/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  org.json.JSONObject
 */
package de.cisha.android.board.account;

import android.view.View;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.account.model.AfterPurchaseInformation;
import de.cisha.android.board.account.model.Product;
import de.cisha.android.board.account.model.ProductInformation;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.SubscriptionType;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.user.Subscription;
import de.cisha.android.board.user.User;
import java.util.Date;
import java.util.List;
import org.json.JSONObject;

class StoreFragment
implements LoadCommandCallback<AfterPurchaseInformation> {
    final /* synthetic */ Product val$product;

    StoreFragment(Product product) {
        this.val$product = product;
    }

    @Override
    public void loadingCancelled() {
        StoreFragment.this.hideDialogWaiting();
    }

    @Override
    public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        StoreFragment.this.hideDialogWaiting();
        StoreFragment.this.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

            @Override
            public void performReload() {
                StoreFragment.this.purchaseProduct(5.this.val$product);
            }
        });
    }

    @Override
    public void loadingSucceded(AfterPurchaseInformation afterPurchaseInformation) {
        StoreFragment.this.hideDialogWaiting();
        User user = ServiceProvider.getInstance().getProfileDataService().getCurrentUserData();
        if (user != null) {
            SubscriptionType subscriptionType = this.val$product.getProductInformation().isWebPremium() ? SubscriptionType.PREMIUM : SubscriptionType.PREMIUM_MOBILE;
            List<Subscription> list = user.getSubscriptions();
            list.add(new Subscription(subscriptionType, afterPurchaseInformation.getValidUntil(), true, Subscription.SubscriptionOrigin.MOBILE, Subscription.SubscriptionProvider.GOOGLE));
            user.setSubscriptions(list);
        }
        if (afterPurchaseInformation != null && StoreFragment.this.getView() != null) {
            StoreFragment.this.getView().post(new Runnable(){

                @Override
                public void run() {
                    StoreFragment.this.updateViewsToPremiumState();
                }
            });
        }
        ServiceProvider.getInstance().getProfileDataService().getUserData(new LoadCommandCallback<User>(){

            @Override
            public void loadingCancelled() {
                StoreFragment.this.updateViewsToPremiumState();
            }

            @Override
            public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                StoreFragment.this.updateViewsToPremiumState();
            }

            @Override
            public void loadingSucceded(User user) {
                StoreFragment.this.updateViewsToPremiumState();
            }
        });
    }

}
