/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Handler
 *  android.util.SparseArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.SparseArray;
import com.example.android.trivialdrivesample.util.SkuDetails;
import de.cisha.android.board.account.model.AfterPurchaseInformation;
import de.cisha.android.board.account.model.Product;
import de.cisha.android.board.account.model.ProductInformation;
import de.cisha.android.board.service.IPurchaseService;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.chess.util.Logger;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class PurchaseServiceMock
implements IPurchaseService {
    private Product createProduct(int n, String object, String string, String string2, String string3, String string4) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("productId", object);
            jSONObject.put("type", (Object)string);
            jSONObject.put("price", (Object)string2);
            jSONObject.put("title", (Object)string3);
            jSONObject.put("description", (Object)string4);
            object = new SkuDetails(jSONObject.toString());
        }
        catch (JSONException jSONException) {
            Logger.getInstance().error(PurchaseServiceMock.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
            object = null;
        }
        return new Product((SkuDetails)object, new ProductInformation(object.getSku(), n, true, false));
    }

    @Override
    public void getAllPremiumSubscriptionProducts(Context object, final LoadCommandCallback<List<Product>> loadCommandCallback) {
        object = new LinkedList();
        object.add(this.createProduct(1, "ID1", "TYPE", "PRICE1", "TITLE1", "DESCRIPTION1"));
        object.add(this.createProduct(12, "ID2", "TYPE", "PRICE2", "TITLE2", "DESCRIPTION2"));
        object.add(this.createProduct(3, "ID3", "TYPE", "PRICE3", "TITLE3", "DESCRIPTION3"));
        new Handler().postDelayed(new Runnable((List)object){
            final /* synthetic */ List val$result;
            {
                this.val$result = list;
            }

            @Override
            public void run() {
                loadCommandCallback.loadingSucceded(this.val$result);
            }
        }, 1000L);
    }

    @Override
    public void getSkuDetailsMap(LoadCommandCallback<SparseArray<SkuDetails>> loadCommandCallback) {
    }

    @Override
    public boolean handleActivityResult(int n, int n2, Intent intent) {
        return true;
    }

    @Override
    public void purchaseProduct(Activity object, Product product, LoadCommandCallback<AfterPurchaseInformation> loadCommandCallback) {
        object = Calendar.getInstance();
        object.add(5, 2);
        loadCommandCallback.loadingSucceded(new AfterPurchaseInformation(100000L, object.getTime()));
    }

    @Override
    public void restorePurchases(Context object, LoadCommandCallback<AfterPurchaseInformation> loadCommandCallback) {
        object = Calendar.getInstance();
        object.add(5, 2);
        loadCommandCallback.loadingSucceded(new AfterPurchaseInformation(100000L, object.getTime()));
    }

}
