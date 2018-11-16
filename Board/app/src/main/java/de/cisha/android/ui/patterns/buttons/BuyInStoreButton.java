// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.buttons;

import android.view.ViewGroup;
import de.cisha.android.ui.patterns.R;
import android.view.LayoutInflater;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;
import android.view.View;
import android.widget.FrameLayout;

public class BuyInStoreButton extends FrameLayout
{
    private View _background;
    private TextView _descriptionTextView;
    private TextView _priceTextView;
    
    public BuyInStoreButton(final Context context) {
        super(context);
        this.initLayout(context);
    }
    
    public BuyInStoreButton(final Context context, final AttributeSet set) {
        super(context, set);
        this.initLayout(context);
    }
    
    public BuyInStoreButton(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.initLayout(context);
    }
    
    private void initLayout(final Context context) {
        ((LayoutInflater)context.getSystemService("layout_inflater")).inflate(R.layout.buybutton, (ViewGroup)this, true);
        this._priceTextView = (TextView)this.findViewById(R.id.buy_button_price_text);
        this._descriptionTextView = (TextView)this.findViewById(R.id.buy_button_description);
        this._background = this.findViewById(R.id.buy_button_container);
        if (!this.isInEditMode()) {
            this._priceTextView.setText((CharSequence)"");
            this._descriptionTextView.setText((CharSequence)"");
        }
    }
    
    public void setDescriptionText(final String text) {
        this._descriptionTextView.setText((CharSequence)text);
    }
    
    public void setEnabled(final boolean b) {
        super.setEnabled(b);
        if (this._background != null) {
            this._background.setEnabled(b);
        }
        if (this._priceTextView != null) {
            this._priceTextView.setEnabled(b);
        }
        if (this._descriptionTextView != null) {
            this._descriptionTextView.setEnabled(b);
        }
    }
    
    public void setPriceText(final String text) {
        this._priceTextView.setText((CharSequence)text);
    }
    
    public void setWebPremiumStyleEnabled() {
        this._background.setBackgroundResource(R.drawable.custom_button_premium);
    }
}
