/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.account.model;

import com.example.android.trivialdrivesample.util.SkuDetails;
import de.cisha.android.board.account.model.ProductInformation;

public class Product {
    private SkuDetails _product;
    private ProductInformation _productInformation;

    public Product(SkuDetails skuDetails, ProductInformation productInformation) {
        this._product = skuDetails;
        this._productInformation = productInformation;
    }

    public SkuDetails getApstoreProduct() {
        return this._product;
    }

    public ProductInformation getProductInformation() {
        return this._productInformation;
    }
}
