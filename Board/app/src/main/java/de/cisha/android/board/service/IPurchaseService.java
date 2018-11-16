// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.android.board.account.model.AfterPurchaseInformation;
import android.app.Activity;
import android.content.Intent;
import com.example.android.trivialdrivesample.util.SkuDetails;
import android.util.SparseArray;
import de.cisha.android.board.account.model.Product;
import java.util.List;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import android.content.Context;

public interface IPurchaseService
{
    void getAllPremiumSubscriptionProducts(final Context p0, final LoadCommandCallback<List<Product>> p1);
    
    void getSkuDetailsMap(final LoadCommandCallback<SparseArray<SkuDetails>> p0);
    
    boolean handleActivityResult(final int p0, final int p1, final Intent p2);
    
    void purchaseProduct(final Activity p0, final Product p1, final LoadCommandCallback<AfterPurchaseInformation> p2);
    
    void restorePurchases(final Context p0, final LoadCommandCallback<AfterPurchaseInformation> p1);
}
