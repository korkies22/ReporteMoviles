// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile.view;

import com.neovisionaries.i18n.CountryCode;
import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.View;
import android.widget.LinearLayout;

public class CountryRowView extends LinearLayout
{
    private View _divider;
    private ImageView _flag;
    private TextView _text;
    
    public CountryRowView(final Context context) {
        super(context);
        this.init();
    }
    
    public CountryRowView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        inflate(this.getContext(), 2131427408, (ViewGroup)this);
        this.setOrientation(1);
        (this._text = (TextView)this.findViewById(2131296461)).setText((CharSequence)"");
        this._flag = (ImageView)this.findViewById(2131296460);
        (this._divider = this.findViewById(2131296462)).setVisibility(8);
    }
    
    public void setCountry(final CountryCode countryCode) {
        if (countryCode != null) {
            this._flag.setImageResource(countryCode.getImageId());
            this._text.setText((CharSequence)countryCode.getDisplayName(this.getContext().getResources()));
            return;
        }
        this._flag.setImageResource(2131231366);
        this._text.setText(2131689939);
    }
    
    public void showTopDivider(final boolean b) {
        if (b) {
            this._divider.setVisibility(0);
            this.requestLayout();
            return;
        }
        this._divider.setVisibility(8);
    }
}
