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
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

class PurchaseService
implements Runnable {
    final /* synthetic */ List val$resultListSkus;

    PurchaseService(List list) {
        this.val$resultListSkus = list;
    }

    @Override
    public void run() {
        SparseArray sparseArray = new SparseArray();
        Object object = new TreeMap<String, SkuDetails>();
        for (SkuDetails object2 : this.val$resultListSkus) {
            object.put(object2.getSku(), object2);
        }
        for (Map.Entry entry : 1.this.val$mapIdSKUString.entrySet()) {
            sparseArray.put(((Integer)entry.getKey()).intValue(), object.get(entry.getValue()));
        }
        1.this.this$2.this$1.this$0._skusVideoSeries = sparseArray;
        object = 1.this.this$2.this$1.this$0._skuDetailsCallbackList.iterator();
        while (object.hasNext()) {
            ((LoadCommandCallback)object.next()).loadingSucceded(sparseArray);
        }
        1.this.this$2.this$1.this$0._skuDetailsCallbackList.clear();
        1.this.this$2.this$1.this$0._skusRequested = false;
    }
}
