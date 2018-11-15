/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.account;

import de.cisha.android.board.account.StoreFragment;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.user.User;
import java.util.List;
import org.json.JSONObject;

class StoreFragment
implements LoadCommandCallback<User> {
    StoreFragment() {
    }

    @Override
    public void loadingCancelled() {
        5.this.this$0.updateViewsToPremiumState();
    }

    @Override
    public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        5.this.this$0.updateViewsToPremiumState();
    }

    @Override
    public void loadingSucceded(User user) {
        5.this.this$0.updateViewsToPremiumState();
    }
}
