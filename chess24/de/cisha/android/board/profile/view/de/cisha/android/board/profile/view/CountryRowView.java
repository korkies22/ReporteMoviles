/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.profile.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.neovisionaries.i18n.CountryCode;

public class CountryRowView
extends LinearLayout {
    private View _divider;
    private ImageView _flag;
    private TextView _text;

    public CountryRowView(Context context) {
        super(context);
        this.init();
    }

    public CountryRowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        CountryRowView.inflate((Context)this.getContext(), (int)2131427408, (ViewGroup)this);
        this.setOrientation(1);
        this._text = (TextView)this.findViewById(2131296461);
        this._text.setText((CharSequence)"");
        this._flag = (ImageView)this.findViewById(2131296460);
        this._divider = this.findViewById(2131296462);
        this._divider.setVisibility(8);
    }

    public void setCountry(CountryCode countryCode) {
        if (countryCode != null) {
            this._flag.setImageResource(countryCode.getImageId());
            this._text.setText((CharSequence)countryCode.getDisplayName(this.getContext().getResources()));
            return;
        }
        this._flag.setImageResource(2131231366);
        this._text.setText(2131689939);
    }

    public void showTopDivider(boolean bl) {
        if (bl) {
            this._divider.setVisibility(0);
            this.requestLayout();
            return;
        }
        this._divider.setVisibility(8);
    }
}
