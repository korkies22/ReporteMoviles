// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.account.view;

import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.util.AttributeSet;
import android.content.Context;
import de.cisha.android.board.account.model.Product;
import de.cisha.android.ui.patterns.buttons.BuyInStoreButton;
import android.widget.FrameLayout;

public class PurchaseItemView extends FrameLayout
{
    private BuyInStoreButton _buyButton;
    private Product _product;
    
    public PurchaseItemView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    public PurchaseItemView(final Context context, final Product product) {
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
            final int months = this._product.getProductInformation().getMonths();
            final StringBuilder sb = new StringBuilder();
            sb.append(months);
            sb.append(" ");
            String s;
            if (months == 1) {
                s = this.getContext().getString(2131690297);
            }
            else {
                s = this.getContext().getString(2131690298);
            }
            sb.append(s);
            this._buyButton.setDescriptionText(sb.toString());
            final BuyInStoreButton buyButton = this._buyButton;
            final StringBuilder sb2 = new StringBuilder();
            sb2.append("");
            sb2.append(this._product.getApstoreProduct().getPrice());
            buyButton.setPriceText(sb2.toString());
            if (this._product.getProductInformation().isWebPremium()) {
                this._buyButton.setWebPremiumStyleEnabled();
            }
        }
        else {
            this._buyButton.setDescriptionText("");
            this._buyButton.setPriceText("");
        }
    }
    
    public void setBuyButtonClickListener(final View.OnClickListener onClickListener) {
        this._buyButton.setOnClickListener(onClickListener);
    }
    
    public void setEnabled(final boolean b) {
        super.setEnabled(b);
        this._buyButton.setEnabled(b);
    }
    
    public void setExtendsModeOn(final boolean b) {
        this.updateBuyButtonTexts();
    }
    
    public void setProduct(final Product product) {
        this._product = product;
        this.updateBuyButtonTexts();
    }
}
