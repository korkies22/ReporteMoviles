/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.content.res.Resources
 *  android.graphics.Typeface
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.playzone.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TimeControlView
extends LinearLayout {
    private int _activeResource;
    private int _colorDefault;
    private int _colorSelected;
    private TextView _incrementUnit;
    private TextView _incrementValue;
    private List<View> _incrementView;
    private int _passiveResource;
    private TextView _plus;
    private TextView _unit;
    private TextView _value;
    private View _view;

    public TimeControlView(Context context) {
        super(context);
        this.inlateView(context);
    }

    public TimeControlView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.inlateView(context);
    }

    private void inlateView(Context context) {
        this._activeResource = 2131231594;
        this._passiveResource = 2131231593;
        Typeface typeface = Typeface.createFromAsset((AssetManager)context.getAssets(), (String)"fonts/DS-DIGI.TTF");
        this._colorSelected = context.getResources().getColor(2131099813);
        this._colorDefault = context.getResources().getColor(2131099812);
        context = LayoutInflater.from((Context)context).inflate(2131427504, null);
        this._view = context.findViewById(2131297163);
        this.addView((View)context, -1, -2);
        this._value = (TextView)this._view.findViewById(2131297162);
        this._unit = (TextView)this._view.findViewById(2131297165);
        this._incrementValue = (TextView)this._view.findViewById(2131297160);
        this._incrementUnit = (TextView)this._view.findViewById(2131297161);
        this._value.setTypeface(typeface);
        this._incrementValue.setTypeface(typeface);
        this._plus = (TextView)this.findViewById(2131297164);
        this._incrementView = new LinkedList<View>();
        this._incrementView.add((View)this._incrementValue);
        this._incrementView.add((View)this._plus);
        this._incrementView.add((View)this._incrementUnit);
        this.setSelected(false);
    }

    public void setBackgroundResources(int n, int n2) {
        this._activeResource = n;
        this._passiveResource = n2;
        this.setSelected(this.isSelected());
    }

    public void setGravity(int n) {
        super.setGravity(n);
        ((LinearLayout)this._view).setGravity(n);
    }

    public void setSelected(boolean bl) {
        super.setSelected(bl);
        int n = bl ? this._activeResource : this._passiveResource;
        this._view.setBackgroundResource(n);
        n = bl ? this._colorSelected : this._colorDefault;
        this._unit.setTextColor(n);
        this._value.setTextColor(n);
        this._incrementUnit.setTextColor(n);
        this._incrementValue.setTextColor(n);
        this._plus.setTextColor(n);
    }

    public void setTimeControlUnit(String string) {
        this._unit.setText((CharSequence)string);
    }

    public void setTimeControlValue(int n, int n2) {
        Object object = this._incrementValue;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n2);
        stringBuilder.append("");
        object.setText((CharSequence)stringBuilder.toString());
        n2 = n2 != 0 ? 0 : 8;
        object = this._incrementView.iterator();
        while (object.hasNext()) {
            ((View)object.next()).setVisibility(n2);
        }
        object = this._value;
        stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append("");
        object.setText((CharSequence)stringBuilder.toString());
    }
}
