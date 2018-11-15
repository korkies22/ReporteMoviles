/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.SparseArray
 */
package de.cisha.android.board.service;

import android.util.SparseArray;
import com.example.android.trivialdrivesample.util.SkuDetails;
import de.cisha.android.board.service.PurchaseService;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import java.util.Iterator;
import java.util.List;

class PurchaseService
implements Runnable {
    PurchaseService() {
    }

    @Override
    public void run() {
        Iterator iterator = 1.this.this$2.this$1.this$0._skuDetailsCallbackList.iterator();
        while (iterator.hasNext()) {
            ((LoadCommandCallback)iterator.next()).loadingCancelled();
        }
        1.this.this$2.this$1.this$0._skuDetailsCallbackList.clear();
        1.this.this$2.this$1.this$0._skusRequested = false;
    }
}
