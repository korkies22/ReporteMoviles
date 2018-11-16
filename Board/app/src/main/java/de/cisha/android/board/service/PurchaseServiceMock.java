// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import java.util.Calendar;
import de.cisha.android.board.account.model.AfterPurchaseInformation;
import android.app.Activity;
import android.content.Intent;
import android.util.SparseArray;
import android.os.Handler;
import java.util.LinkedList;
import java.util.List;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import android.content.Context;
import de.cisha.android.board.account.model.ProductInformation;
import org.json.JSONException;
import de.cisha.chess.util.Logger;
import com.example.android.trivialdrivesample.util.SkuDetails;
import org.json.JSONObject;
import de.cisha.android.board.account.model.Product;

public class PurchaseServiceMock implements IPurchaseService
{
    private Product createProduct(final int n, final String s, final String s2, final String s3, final String s4, final String s5) {
        SkuDetails skuDetails;
        try {
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("productId", (Object)s);
            jsonObject.put("type", (Object)s2);
            jsonObject.put("price", (Object)s3);
            jsonObject.put("title", (Object)s4);
            jsonObject.put("description", (Object)s5);
            skuDetails = new SkuDetails(jsonObject.toString());
        }
        catch (JSONException ex) {
            Logger.getInstance().error(PurchaseServiceMock.class.getName(), JSONException.class.getName(), (Throwable)ex);
            skuDetails = null;
        }
        return new Product(skuDetails, new ProductInformation(skuDetails.getSku(), n, true, false));
    }
    
    @Override
    public void getAllPremiumSubscriptionProducts(final Context context, final LoadCommandCallback<List<Product>> loadCommandCallback) {
        final LinkedList<Product> list = new LinkedList<Product>();
        list.add(this.createProduct(1, "ID1", "TYPE", "PRICE1", "TITLE1", "DESCRIPTION1"));
        list.add(this.createProduct(12, "ID2", "TYPE", "PRICE2", "TITLE2", "DESCRIPTION2"));
        list.add(this.createProduct(3, "ID3", "TYPE", "PRICE3", "TITLE3", "DESCRIPTION3"));
        new Handler().postDelayed((Runnable)new Runnable() {
            @Override
            public void run() {
                loadCommandCallback.loadingSucceded(list);
            }
        }, 1000L);
    }
    
    @Override
    public void getSkuDetailsMap(final LoadCommandCallback<SparseArray<SkuDetails>> loadCommandCallback) {
    }
    
    @Override
    public boolean handleActivityResult(final int n, final int n2, final Intent intent) {
        return true;
    }
    
    @Override
    public void purchaseProduct(final Activity activity, final Product product, final LoadCommandCallback<AfterPurchaseInformation> loadCommandCallback) {
        final Calendar instance = Calendar.getInstance();
        instance.add(5, 2);
        loadCommandCallback.loadingSucceded(new AfterPurchaseInformation(100000L, instance.getTime()));
    }
    
    @Override
    public void restorePurchases(final Context context, final LoadCommandCallback<AfterPurchaseInformation> loadCommandCallback) {
        final Calendar instance = Calendar.getInstance();
        instance.add(5, 2);
        loadCommandCallback.loadingSucceded(new AfterPurchaseInformation(100000L, instance.getTime()));
    }
}
