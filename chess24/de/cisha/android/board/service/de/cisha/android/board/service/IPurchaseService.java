/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.util.SparseArray
 */
package de.cisha.android.board.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;
import com.example.android.trivialdrivesample.util.SkuDetails;
import de.cisha.android.board.account.model.AfterPurchaseInformation;
import de.cisha.android.board.account.model.Product;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import java.util.List;

public interface IPurchaseService {
    public void getAllPremiumSubscriptionProducts(Context var1, LoadCommandCallback<List<Product>> var2);

    public void getSkuDetailsMap(LoadCommandCallback<SparseArray<SkuDetails>> var1);

    public boolean handleActivityResult(int var1, int var2, Intent var3);

    public void purchaseProduct(Activity var1, Product var2, LoadCommandCallback<AfterPurchaseInformation> var3);

    public void restorePurchases(Context var1, LoadCommandCallback<AfterPurchaseInformation> var2);
}
