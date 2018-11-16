// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.account.model;

import com.example.android.trivialdrivesample.util.SkuDetails;

public class Product
{
    private SkuDetails _product;
    private ProductInformation _productInformation;
    
    public Product(final SkuDetails product, final ProductInformation productInformation) {
        this._product = product;
        this._productInformation = productInformation;
    }
    
    public SkuDetails getApstoreProduct() {
        return this._product;
    }
    
    public ProductInformation getProductInformation() {
        return this._productInformation;
    }
}
