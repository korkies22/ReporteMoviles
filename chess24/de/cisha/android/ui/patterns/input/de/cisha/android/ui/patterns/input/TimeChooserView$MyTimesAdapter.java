/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.TextView
 */
package de.cisha.android.ui.patterns.input;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import de.cisha.android.ui.patterns.input.TimeChooserView;
import java.util.List;

class TimeChooserView.MyTimesAdapter
extends BaseAdapter {
    private List<String> _times;

    public TimeChooserView.MyTimesAdapter(List<String> list) {
        this._times = list;
    }

    public int getCount() {
        return this._times.size();
    }

    public Object getItem(int n) {
        return this._times.get(n);
    }

    public long getItemId(int n) {
        return 0L;
    }

    public View getView(int n, View view, ViewGroup object) {
        object = (TextView)view;
        view = object;
        if (object == null) {
            view = new TextView(TimeChooserView.this.getContext());
            view.setTextSize(30.0f);
            view.setGravity(17);
        }
        object = new StringBuilder();
        object.append("  ");
        object.append(this._times.get(n));
        object.append("  ");
        view.setText((CharSequence)object.toString());
        view.setSelected(true);
        return view;
    }
}
