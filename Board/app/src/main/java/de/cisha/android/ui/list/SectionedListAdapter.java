// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.list;

import android.view.ViewGroup;
import android.view.View;
import java.util.Iterator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.BaseAdapter;

public abstract class SectionedListAdapter<S, V> extends BaseAdapter
{
    private Map<S, List<V>> _backingMap;
    private boolean _hideEmptySections;
    private Map<S, Boolean> _mapSectionFlagOpened;
    private final boolean _openendSectionDefaultValue;
    private List<S> _sections;
    
    public SectionedListAdapter(final Map<S, List<V>> map, final List<S> list) {
        this(map, list, true);
    }
    
    public SectionedListAdapter(final Map<S, List<V>> backingMap, final List<S> sections, final boolean openendSectionDefaultValue) {
        this._backingMap = backingMap;
        this._sections = sections;
        this._openendSectionDefaultValue = openendSectionDefaultValue;
        this._mapSectionFlagOpened = new HashMap<S, Boolean>();
        this._hideEmptySections = false;
    }
    
    private Boolean getIsOpenendFlagForSection(final S n) {
        final Boolean b = this._mapSectionFlagOpened.get(n);
        boolean b2;
        if (b != null) {
            b2 = b;
        }
        else {
            b2 = this._openendSectionDefaultValue;
        }
        return b2;
    }
    
    private S getSectionHeaderForPosition(final int n) {
        final Iterator<S> iterator = this._sections.iterator();
        int n2 = 0;
        while (iterator.hasNext()) {
            final S next = iterator.next();
            final boolean shouldShowSectionHeader = this.shouldShowSectionHeader(next);
            if (n2 == n && shouldShowSectionHeader) {
                return next;
            }
            int n3 = n2;
            if (this.isOpened(next)) {
                final List<V> list = this._backingMap.get(next);
                n3 = n2;
                if (list != null) {
                    n3 = n2 + list.size();
                }
            }
            n2 = n3;
            if (!shouldShowSectionHeader) {
                continue;
            }
            n2 = n3 + 1;
        }
        return null;
    }
    
    private boolean isOpened(final S n) {
        return this.getIsOpenendFlagForSection(n);
    }
    
    private boolean isSectionHeader(final int n) {
        return this.getSectionHeaderForPosition(n) != null;
    }
    
    private boolean shouldShowSectionHeader(final S n) {
        if (!this._hideEmptySections) {
            return true;
        }
        final List<V> list = this._backingMap.get(n);
        return list != null && list.size() > 0;
    }
    
    public int getCount() {
        final Iterator<S> iterator = this._sections.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            final S next = iterator.next();
            int n2 = n;
            if (this.isOpened(next)) {
                final List<V> list = this._backingMap.get(next);
                n2 = n;
                if (list != null) {
                    n2 = n + list.size();
                }
            }
            n = n2;
            if (this.shouldShowSectionHeader(next)) {
                n = n2 + 1;
            }
        }
        return n;
    }
    
    public int getCountOfValues() {
        final Iterator<S> iterator = this._sections.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            final S next = iterator.next();
            if (this.isOpened(next)) {
                final List<V> list = this._backingMap.get(next);
                if (list == null) {
                    continue;
                }
                n += list.size();
            }
        }
        return n;
    }
    
    public Object getItem(final int n) {
        if (this.isSectionHeader(n)) {
            return this.getSectionHeaderForPosition(n);
        }
        return this.getValueForPosition(n);
    }
    
    public long getItemId(final int n) {
        return this.getItem(n).hashCode();
    }
    
    public int getItemViewType(final int n) {
        return (this.isSectionHeader(n) ^ true) ? 1 : 0;
    }
    
    public V getValueForPosition(final int n) {
        final Iterator<S> iterator = this._sections.iterator();
        int n2 = 0;
        while (iterator.hasNext()) {
            final S next = iterator.next();
            int n3 = n2;
            if (this.shouldShowSectionHeader(next)) {
                n3 = n2 + 1;
            }
            if (n < n3) {
                break;
            }
            n2 = n3;
            if (!this.isOpened(next)) {
                continue;
            }
            final List<V> list = this._backingMap.get(next);
            n2 = n3;
            if (list == null) {
                continue;
            }
            n2 = list.size() + n3;
            if (n >= n3 && n < n2) {
                return list.get(n - n3);
            }
        }
        return null;
    }
    
    public View getView(final int n, final View view, final ViewGroup viewGroup) {
        if (this.isSectionHeader(n)) {
            final S sectionHeaderForPosition = this.getSectionHeaderForPosition(n);
            if (sectionHeaderForPosition != null) {
                return this.getViewForSectionHeader(sectionHeaderForPosition, view, viewGroup);
            }
        }
        else {
            final V valueForPosition = this.getValueForPosition(n);
            if (valueForPosition != null) {
                return this.getViewForValue(valueForPosition, view, viewGroup);
            }
        }
        return null;
    }
    
    protected abstract View getViewForSectionHeader(final S p0, final View p1, final ViewGroup p2);
    
    protected abstract View getViewForValue(final V p0, final View p1, final ViewGroup p2);
    
    public int getViewTypeCount() {
        return 2;
    }
    
    public void setHidesEmptySections(final boolean hideEmptySections) {
        this._hideEmptySections = hideEmptySections;
        this.notifyDataSetChanged();
    }
    
    public void toggleOpenCloseSection(final S n) {
        this._mapSectionFlagOpened.put(n, this.getIsOpenendFlagForSection(n) ^ true);
        this.notifyDataSetChanged();
    }
}
