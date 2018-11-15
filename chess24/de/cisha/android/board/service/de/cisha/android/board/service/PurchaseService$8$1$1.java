/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.util.SparseArray
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import android.os.Handler;
import android.util.SparseArray;
import com.example.android.trivialdrivesample.util.SkuDetails;
import de.cisha.android.board.service.PurchaseService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.json.JSONObject;

class PurchaseService
implements LoadCommandCallback<List<SkuDetails>> {
    final /* synthetic */ Map val$mapIdSKUString;

    PurchaseService(Map map) {
        this.val$mapIdSKUString = map;
    }

    @Override
    public void loadingCancelled() {
        1.this.this$1.this$0.getUiThreadHandler().post(new Runnable(){

            @Override
            public void run() {
                Iterator iterator = 1.this.this$1.this$0._skuDetailsCallbackList.iterator();
                while (iterator.hasNext()) {
                    ((LoadCommandCallback)iterator.next()).loadingCancelled();
                }
                1.this.this$1.this$0._skuDetailsCallbackList.clear();
                1.this.this$1.this$0._skusRequested = false;
            }
        });
    }

    @Override
    public void loadingFailed(final APIStatusCode aPIStatusCode, final String string, final List<LoadFieldError> list, final JSONObject jSONObject) {
        1.this.this$1.this$0.getUiThreadHandler().post(new Runnable(){

            @Override
            public void run() {
                Iterator iterator = 1.this.this$1.this$0._skuDetailsCallbackList.iterator();
                while (iterator.hasNext()) {
                    ((LoadCommandCallback)iterator.next()).loadingFailed(aPIStatusCode, string, list, jSONObject);
                }
                1.this.this$1.this$0._skuDetailsCallbackList.clear();
                1.this.this$1.this$0._skusRequested = false;
            }
        });
    }

    @Override
    public void loadingSucceded(final List<SkuDetails> list) {
        1.this.this$1.this$0.getUiThreadHandler().post(new Runnable(){

            @Override
            public void run() {
                SparseArray sparseArray = new SparseArray();
                Object object = new TreeMap<String, SkuDetails>();
                for (SkuDetails object2 : list) {
                    object.put(object2.getSku(), object2);
                }
                for (Map.Entry entry : 1.this.val$mapIdSKUString.entrySet()) {
                    sparseArray.put(((Integer)entry.getKey()).intValue(), object.get(entry.getValue()));
                }
                1.this.this$1.this$0._skusVideoSeries = sparseArray;
                object = 1.this.this$1.this$0._skuDetailsCallbackList.iterator();
                while (object.hasNext()) {
                    ((LoadCommandCallback)object.next()).loadingSucceded(sparseArray);
                }
                1.this.this$1.this$0._skuDetailsCallbackList.clear();
                1.this.this$1.this$0._skusRequested = false;
            }
        });
    }

}
