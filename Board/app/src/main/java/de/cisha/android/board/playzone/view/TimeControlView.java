// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.view;

import java.util.Iterator;
import java.util.LinkedList;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.content.Context;
import android.view.View;
import java.util.List;
import android.widget.TextView;
import android.widget.LinearLayout;

public class TimeControlView extends LinearLayout
{
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
    
    public TimeControlView(final Context context) {
        super(context);
        this.inlateView(context);
    }
    
    public TimeControlView(final Context context, final AttributeSet set) {
        super(context, set);
        this.inlateView(context);
    }
    
    private void inlateView(final Context context) {
        this._activeResource = 2131231594;
        this._passiveResource = 2131231593;
        final Typeface fromAsset = Typeface.createFromAsset(context.getAssets(), "fonts/DS-DIGI.TTF");
        this._colorSelected = context.getResources().getColor(2131099813);
        this._colorDefault = context.getResources().getColor(2131099812);
        final View inflate = LayoutInflater.from(context).inflate(2131427504, (ViewGroup)null);
        this._view = inflate.findViewById(2131297163);
        this.addView(inflate, -1, -2);
        this._value = (TextView)this._view.findViewById(2131297162);
        this._unit = (TextView)this._view.findViewById(2131297165);
        this._incrementValue = (TextView)this._view.findViewById(2131297160);
        this._incrementUnit = (TextView)this._view.findViewById(2131297161);
        this._value.setTypeface(fromAsset);
        this._incrementValue.setTypeface(fromAsset);
        this._plus = (TextView)this.findViewById(2131297164);
        (this._incrementView = new LinkedList<View>()).add((View)this._incrementValue);
        this._incrementView.add((View)this._plus);
        this._incrementView.add((View)this._incrementUnit);
        this.setSelected(false);
    }
    
    public void setBackgroundResources(final int activeResource, final int passiveResource) {
        this._activeResource = activeResource;
        this._passiveResource = passiveResource;
        this.setSelected(this.isSelected());
    }
    
    public void setGravity(final int n) {
        super.setGravity(n);
        ((LinearLayout)this._view).setGravity(n);
    }
    
    public void setSelected(final boolean selected) {
        super.setSelected(selected);
        int backgroundResource;
        if (selected) {
            backgroundResource = this._activeResource;
        }
        else {
            backgroundResource = this._passiveResource;
        }
        this._view.setBackgroundResource(backgroundResource);
        int textColor;
        if (selected) {
            textColor = this._colorSelected;
        }
        else {
            textColor = this._colorDefault;
        }
        this._unit.setTextColor(textColor);
        this._value.setTextColor(textColor);
        this._incrementUnit.setTextColor(textColor);
        this._incrementValue.setTextColor(textColor);
        this._plus.setTextColor(textColor);
    }
    
    public void setTimeControlUnit(final String text) {
        this._unit.setText((CharSequence)text);
    }
    
    public void setTimeControlValue(final int n, int visibility) {
        final TextView incrementValue = this._incrementValue;
        final StringBuilder sb = new StringBuilder();
        sb.append(visibility);
        sb.append("");
        incrementValue.setText((CharSequence)sb.toString());
        if (visibility != 0) {
            visibility = 0;
        }
        else {
            visibility = 8;
        }
        final Iterator<View> iterator = this._incrementView.iterator();
        while (iterator.hasNext()) {
            iterator.next().setVisibility(visibility);
        }
        final TextView value = this._value;
        final StringBuilder sb2 = new StringBuilder();
        sb2.append(n);
        sb2.append("");
        value.setText((CharSequence)sb2.toString());
    }
}
