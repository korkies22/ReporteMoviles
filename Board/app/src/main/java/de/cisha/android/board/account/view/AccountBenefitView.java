// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.account.view;

import android.content.res.TypedArray;
import de.cisha.android.board.R;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.util.AttributeSet;
import android.graphics.drawable.Drawable;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;

public class AccountBenefitView extends LinearLayout
{
    private TextView _description;
    private TextView _header;
    private ImageView _image;
    
    public AccountBenefitView(final Context context, final Drawable drawable, final String s, final String s2) {
        super(context);
        this.init(drawable, s, s2);
    }
    
    public AccountBenefitView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init(set);
    }
    
    private void init(final Drawable imageDrawable, final String text, final String text2) {
        this.setOrientation(1);
        ((LayoutInflater)this.getContext().getSystemService("layout_inflater")).inflate(2131427355, (ViewGroup)this, true);
        (this._image = (ImageView)this.findViewById(2131296265)).setImageDrawable(imageDrawable);
        (this._description = (TextView)this.findViewById(2131296263)).setText((CharSequence)text2);
        (this._header = (TextView)this.findViewById(2131296264)).setText((CharSequence)text);
    }
    
    private void init(final AttributeSet set) {
        final TypedArray obtainStyledAttributes = this.getContext().obtainStyledAttributes(set, R.styleable.AccountBenefitView);
        final int resourceId = obtainStyledAttributes.getResourceId(1, 0);
        Drawable drawable;
        if (resourceId != 0) {
            drawable = this.getContext().getResources().getDrawable(resourceId);
        }
        else {
            drawable = null;
        }
        final String string = obtainStyledAttributes.getString(2);
        final String string2 = obtainStyledAttributes.getString(0);
        obtainStyledAttributes.recycle();
        this.init(drawable, string, string2);
    }
}
