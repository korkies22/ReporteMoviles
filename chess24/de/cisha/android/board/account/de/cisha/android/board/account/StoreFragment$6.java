/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.account;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.account.model.AfterPurchaseInformation;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class StoreFragment
implements LoadCommandCallback<AfterPurchaseInformation> {
    StoreFragment() {
    }

    @Override
    public void loadingCancelled() {
        StoreFragment.this.hideDialogWaiting();
    }

    @Override
    public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        StoreFragment.this.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

            @Override
            public void performReload() {
                StoreFragment.this.restoreItems();
            }
        });
        StoreFragment.this.hideDialogWaiting();
    }

    @Override
    public void loadingSucceded(AfterPurchaseInformation afterPurchaseInformation) {
        StoreFragment.this.updateViewsToPremiumState();
        StoreFragment.this.hideDialogWaiting();
    }

}
