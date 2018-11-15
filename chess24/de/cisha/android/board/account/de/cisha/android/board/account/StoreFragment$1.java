/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.account;

import android.view.View;
import de.cisha.android.board.ModelHolder;
import de.cisha.android.board.account.model.Product;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.SubscriptionType;
import de.cisha.android.board.user.Subscription;
import de.cisha.android.board.user.User;
import java.util.List;

class StoreFragment
implements View.OnClickListener {
    StoreFragment() {
    }

    public void onClick(View view) {
        boolean bl = ServiceProvider.getInstance().getProfileDataService().getCurrentUserData().getSubscription(SubscriptionType.PREMIUM) == null;
        StoreFragment.this.showPurchaseDialog(StoreFragment.this._webSubscriptionHolder, bl, 2131690301, 2131690304);
    }
}
