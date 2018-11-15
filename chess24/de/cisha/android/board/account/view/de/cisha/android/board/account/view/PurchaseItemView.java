/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.FrameLayout
 */
package de.cisha.android.board.account.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.example.android.trivialdrivesample.util.SkuDetails;
import de.cisha.android.board.account.model.Product;
import de.cisha.android.board.account.model.ProductInformation;
import de.cisha.android.ui.patterns.buttons.BuyInStoreButton;

public class PurchaseItemView
extends FrameLayout {
    private BuyInStoreButton _buyButton;
    private Product _product;

    public PurchaseItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    public PurchaseItemView(Context context, Product product) {
        super(context);
        this.init();
        this.setProduct(product);
    }

    private void init() {
        ((LayoutInflater)this.getContext().getSystemService("layout_inflater")).inflate(2131427526, (ViewGroup)this, true);
        this._buyButton = (BuyInStoreButton)this.findViewById(2131296887);
    }

    private void updateBuyButtonTexts() {
        if (this._product != null) {
            int n = this._product.getProductInformation().getMonths();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(n);
            stringBuilder.append(" ");
            Object object = n == 1 ? this.getContext().getString(2131690297) : this.getContext().getString(2131690298);
            stringBuilder.append((String)object);
            object = stringBuilder.toString();
            this._buyButton.setDescriptionText((String)object);
            object = this._buyButton;
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(this._product.getApstoreProduct().getPrice());
            object.setPriceText(stringBuilder.toString());
            if (this._product.getProductInformation().isWebPremium()) {
                this._buyButton.setWebPremiumStyleEnabled();
                return;
            }
        } else {
            this._buyButton.setDescriptionText("");
            this._buyButton.setPriceText("");
        }
    }

    public void setBuyButtonClickListener(View.OnClickListener onClickListener) {
        this._buyButton.setOnClickListener(onClickListener);
    }

    public void setEnabled(boolean bl) {
        super.setEnabled(bl);
        this._buyButton.setEnabled(bl);
    }

    public void setExtendsModeOn(boolean bl) {
        this.updateBuyButtonTexts();
    }

    public void setProduct(Product product) {
        this._product = product;
        this.updateBuyButtonTexts();
    }
}
