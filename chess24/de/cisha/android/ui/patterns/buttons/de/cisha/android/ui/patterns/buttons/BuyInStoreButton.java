/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.FrameLayout
 *  android.widget.TextView
 */
package de.cisha.android.ui.patterns.buttons;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import de.cisha.android.ui.patterns.R;

public class BuyInStoreButton
extends FrameLayout {
    private View _background;
    private TextView _descriptionTextView;
    private TextView _priceTextView;

    public BuyInStoreButton(Context context) {
        super(context);
        this.initLayout(context);
    }

    public BuyInStoreButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initLayout(context);
    }

    public BuyInStoreButton(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.initLayout(context);
    }

    private void initLayout(Context context) {
        ((LayoutInflater)context.getSystemService("layout_inflater")).inflate(R.layout.buybutton, (ViewGroup)this, true);
        this._priceTextView = (TextView)this.findViewById(R.id.buy_button_price_text);
        this._descriptionTextView = (TextView)this.findViewById(R.id.buy_button_description);
        this._background = this.findViewById(R.id.buy_button_container);
        if (!this.isInEditMode()) {
            this._priceTextView.setText((CharSequence)"");
            this._descriptionTextView.setText((CharSequence)"");
        }
    }

    public void setDescriptionText(String string) {
        this._descriptionTextView.setText((CharSequence)string);
    }

    public void setEnabled(boolean bl) {
        super.setEnabled(bl);
        if (this._background != null) {
            this._background.setEnabled(bl);
        }
        if (this._priceTextView != null) {
            this._priceTextView.setEnabled(bl);
        }
        if (this._descriptionTextView != null) {
            this._descriptionTextView.setEnabled(bl);
        }
    }

    public void setPriceText(String string) {
        this._priceTextView.setText((CharSequence)string);
    }

    public void setWebPremiumStyleEnabled() {
        this._background.setBackgroundResource(R.drawable.custom_button_premium);
    }
}
