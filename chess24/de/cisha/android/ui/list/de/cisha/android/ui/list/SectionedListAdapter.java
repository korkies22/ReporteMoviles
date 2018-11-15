/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 */
package de.cisha.android.ui.list;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class SectionedListAdapter<S, V>
extends BaseAdapter {
    private Map<S, List<V>> _backingMap;
    private boolean _hideEmptySections;
    private Map<S, Boolean> _mapSectionFlagOpened;
    private final boolean _openendSectionDefaultValue;
    private List<S> _sections;

    public SectionedListAdapter(Map<S, List<V>> map, List<S> list) {
        this(map, list, true);
    }

    public SectionedListAdapter(Map<S, List<V>> map, List<S> list, boolean bl) {
        this._backingMap = map;
        this._sections = list;
        this._openendSectionDefaultValue = bl;
        this._mapSectionFlagOpened = new HashMap<S, Boolean>();
        this._hideEmptySections = false;
    }

    private Boolean getIsOpenendFlagForSection(S object) {
        boolean bl = (object = this._mapSectionFlagOpened.get(object)) != null ? object.booleanValue() : this._openendSectionDefaultValue;
        return bl;
    }

    private S getSectionHeaderForPosition(int n) {
        Iterator<S> iterator = this._sections.iterator();
        int n2 = 0;
        while (iterator.hasNext()) {
            Object object = iterator.next();
            boolean bl = this.shouldShowSectionHeader(object);
            if (n2 == n && bl) {
                return (S)object;
            }
            int n3 = n2;
            if (this.isOpened(object)) {
                object = this._backingMap.get(object);
                n3 = n2;
                if (object != null) {
                    n3 = n2 + object.size();
                }
            }
            n2 = n3;
            if (!bl) continue;
            n2 = n3 + 1;
        }
        return null;
    }

    private boolean isOpened(S s) {
        return this.getIsOpenendFlagForSection(s);
    }

    private boolean isSectionHeader(int n) {
        if (this.getSectionHeaderForPosition(n) != null) {
            return true;
        }
        return false;
    }

    private boolean shouldShowSectionHeader(S object) {
        if (!this._hideEmptySections) {
            return true;
        }
        if ((object = this._backingMap.get(object)) != null && object.size() > 0) {
            return true;
        }
        return false;
    }

    public int getCount() {
        Iterator<S> iterator = this._sections.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            S s = iterator.next();
            int n2 = n;
            if (this.isOpened(s)) {
                List<V> list = this._backingMap.get(s);
                n2 = n;
                if (list != null) {
                    n2 = n + list.size();
                }
            }
            n = n2;
            if (!this.shouldShowSectionHeader(s)) continue;
            n = n2 + 1;
        }
        return n;
    }

    public int getCountOfValues() {
        Iterator<S> iterator = this._sections.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            Object object = iterator.next();
            if (!this.isOpened(object) || (object = this._backingMap.get(object)) == null) continue;
            n += object.size();
        }
        return n;
    }

    public Object getItem(int n) {
        if (this.isSectionHeader(n)) {
            return this.getSectionHeaderForPosition(n);
        }
        return this.getValueForPosition(n);
    }

    public long getItemId(int n) {
        return this.getItem(n).hashCode();
    }

    public int getItemViewType(int n) {
        return this.isSectionHeader(n) ^ true;
    }

    public V getValueForPosition(int n) {
        Iterator<S> iterator = this._sections.iterator();
        int n2 = 0;
        while (iterator.hasNext()) {
            Object object = iterator.next();
            int n3 = n2;
            if (this.shouldShowSectionHeader(object)) {
                n3 = n2 + 1;
            }
            if (n < n3) break;
            n2 = n3;
            if (!this.isOpened(object)) continue;
            object = this._backingMap.get(object);
            n2 = n3;
            if (object == null) continue;
            n2 = object.size() + n3;
            if (n < n3 || n >= n2) continue;
            return (V)object.get(n - n3);
        }
        return null;
    }

    public View getView(int n, View view, ViewGroup viewGroup) {
        if (this.isSectionHeader(n)) {
            S s = this.getSectionHeaderForPosition(n);
            if (s != null) {
                return this.getViewForSectionHeader(s, view, viewGroup);
            }
        } else {
            V v = this.getValueForPosition(n);
            if (v != null) {
                return this.getViewForValue(v, view, viewGroup);
            }
        }
        return null;
    }

    protected abstract View getViewForSectionHeader(S var1, View var2, ViewGroup var3);

    protected abstract View getViewForValue(V var1, View var2, ViewGroup var3);

    public int getViewTypeCount() {
        return 2;
    }

    public void setHidesEmptySections(boolean bl) {
        this._hideEmptySections = bl;
        this.notifyDataSetChanged();
    }

    public void toggleOpenCloseSection(S s) {
        Boolean bl = this.getIsOpenendFlagForSection(s);
        this._mapSectionFlagOpened.put(s, bl ^ true);
        this.notifyDataSetChanged();
    }
}
