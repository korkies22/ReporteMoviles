/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.SparseArray
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import android.util.SparseArray;
import com.example.android.trivialdrivesample.util.SkuDetails;
import de.cisha.android.board.service.PurchaseService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

class PurchaseService
implements Runnable {
    final /* synthetic */ JSONObject val$data;
    final /* synthetic */ APIStatusCode val$errorCode;
    final /* synthetic */ List val$errors;
    final /* synthetic */ String val$message;

    PurchaseService(APIStatusCode aPIStatusCode, String string, List list, JSONObject jSONObject) {
        this.val$errorCode = aPIStatusCode;
        this.val$message = string;
        this.val$errors = list;
        this.val$data = jSONObject;
    }

    @Override
    public void run() {
        Iterator iterator = 1.this.this$2.this$1.this$0._skuDetailsCallbackList.iterator();
        while (iterator.hasNext()) {
            ((LoadCommandCallback)iterator.next()).loadingFailed(this.val$errorCode, this.val$message, this.val$errors, this.val$data);
        }
        1.this.this$2.this$1.this$0._skuDetailsCallbackList.clear();
        1.this.this$2.this$1.this$0._skusRequested = false;
    }
}
