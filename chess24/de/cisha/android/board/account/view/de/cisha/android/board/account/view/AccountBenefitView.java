/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.account.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.android.board.R;

public class AccountBenefitView
extends LinearLayout {
    private TextView _description;
    private TextView _header;
    private ImageView _image;

    public AccountBenefitView(Context context, Drawable drawable, String string, String string2) {
        super(context);
        this.init(drawable, string, string2);
    }

    public AccountBenefitView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(attributeSet);
    }

    private void init(Drawable drawable, String string, String string2) {
        this.setOrientation(1);
        ((LayoutInflater)this.getContext().getSystemService("layout_inflater")).inflate(2131427355, (ViewGroup)this, true);
        this._image = (ImageView)this.findViewById(2131296265);
        this._image.setImageDrawable(drawable);
        this._description = (TextView)this.findViewById(2131296263);
        this._description.setText((CharSequence)string2);
        this._header = (TextView)this.findViewById(2131296264);
        this._header.setText((CharSequence)string);
    }

    private void init(AttributeSet object) {
        TypedArray typedArray = this.getContext().obtainStyledAttributes(object, R.styleable.AccountBenefitView);
        int n = typedArray.getResourceId(1, 0);
        object = n != 0 ? this.getContext().getResources().getDrawable(n) : null;
        String string = typedArray.getString(2);
        String string2 = typedArray.getString(0);
        typedArray.recycle();
        this.init((Drawable)object, string, string2);
    }
}
