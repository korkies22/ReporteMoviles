/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.util.SparseArray
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import android.content.Context;
import android.os.Handler;
import android.util.SparseArray;
import com.example.android.trivialdrivesample.util.SkuDetails;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.json.JSONObject;

class PurchaseService
implements Runnable {
    final /* synthetic */ LoadCommandCallback val$skuCallback;

    PurchaseService(LoadCommandCallback loadCommandCallback) {
        this.val$skuCallback = loadCommandCallback;
    }

    @Override
    public void run() {
        if (PurchaseService.this._skusVideoSeries != null) {
            this.val$skuCallback.loadingSucceded(PurchaseService.this._skusVideoSeries);
            return;
        }
        if (!PurchaseService.this._skusRequested) {
            PurchaseService.this._skusRequested = true;
            PurchaseService.this._skuDetailsCallbackList.add(this.val$skuCallback);
            LoadCommandCallbackWithTimeout<Map<Integer, String>> loadCommandCallbackWithTimeout = new LoadCommandCallbackWithTimeout<Map<Integer, String>>(){

                @Override
                protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                    8.this.val$skuCallback.loadingFailed(aPIStatusCode, string, list, jSONObject);
                }

                @Override
                protected void succeded(final Map<Integer, String> map) {
                    PurchaseService.this.getAllPriceTierSkus(PurchaseService.this._context, new LinkedList<String>(map.values()), new LoadCommandCallback<List<SkuDetails>>(){

                        @Override
                        public void loadingCancelled() {
                            PurchaseService.this.getUiThreadHandler().post(new Runnable(){

                                @Override
                                public void run() {
                                    Iterator iterator = PurchaseService.this._skuDetailsCallbackList.iterator();
                                    while (iterator.hasNext()) {
                                        ((LoadCommandCallback)iterator.next()).loadingCancelled();
                                    }
                                    PurchaseService.this._skuDetailsCallbackList.clear();
                                    PurchaseService.this._skusRequested = false;
                                }
                            });
                        }

                        @Override
                        public void loadingFailed(final APIStatusCode aPIStatusCode, final String string, final List<LoadFieldError> list, final JSONObject jSONObject) {
                            PurchaseService.this.getUiThreadHandler().post(new Runnable(){

                                @Override
                                public void run() {
                                    Iterator iterator = PurchaseService.this._skuDetailsCallbackList.iterator();
                                    while (iterator.hasNext()) {
                                        ((LoadCommandCallback)iterator.next()).loadingFailed(aPIStatusCode, string, list, jSONObject);
                                    }
                                    PurchaseService.this._skuDetailsCallbackList.clear();
                                    PurchaseService.this._skusRequested = false;
                                }
                            });
                        }

                        @Override
                        public void loadingSucceded(final List<SkuDetails> list) {
                            PurchaseService.this.getUiThreadHandler().post(new Runnable(){

                                @Override
                                public void run() {
                                    SparseArray sparseArray = new SparseArray();
                                    Object object = new TreeMap<String, SkuDetails>();
                                    for (SkuDetails object2 : list) {
                                        object.put(object2.getSku(), object2);
                                    }
                                    for (Map.Entry entry : map.entrySet()) {
                                        sparseArray.put(((Integer)entry.getKey()).intValue(), object.get(entry.getValue()));
                                    }
                                    PurchaseService.this._skusVideoSeries = sparseArray;
                                    object = PurchaseService.this._skuDetailsCallbackList.iterator();
                                    while (object.hasNext()) {
                                        ((LoadCommandCallback)object.next()).loadingSucceded(sparseArray);
                                    }
                                    PurchaseService.this._skuDetailsCallbackList.clear();
                                    PurchaseService.this._skusRequested = false;
                                }
                            });
                        }

                    });
                }

            };
            PurchaseService.this.getPriceCategories((LoadCommandCallback<Map<Integer, String>>)loadCommandCallbackWithTimeout);
            return;
        }
        PurchaseService.this._skuDetailsCallbackList.add(this.val$skuCallback);
    }

}
