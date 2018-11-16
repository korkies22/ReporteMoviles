// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.list;

import android.view.ViewGroup;
import android.view.View;
import android.widget.Filter;
import java.util.Iterator;
import java.util.HashSet;
import android.widget.ListView.FixedViewInfo;
import java.util.ArrayList;
import android.widget.ListAdapter;
import android.database.DataSetObserver;
import java.util.Set;
import android.widget.Filterable;
import android.widget.WrapperListAdapter;

public class MutableHeaderViewListAdapter implements WrapperListAdapter, Filterable
{
    private Set<DataSetObserver> _observers;
    private ListAdapter mAdapter;
    boolean mAreAllFixedViewsSelectable;
    ArrayList<ListView.FixedViewInfo> mFooterViewInfos;
    ArrayList<ListView.FixedViewInfo> mHeaderViewInfos;
    private boolean mIsFilterable;
    
    public MutableHeaderViewListAdapter() {
        this.mHeaderViewInfos = new ArrayList<ListView.FixedViewInfo>();
        this.mFooterViewInfos = new ArrayList<ListView.FixedViewInfo>();
        this._observers = new HashSet<DataSetObserver>();
        this.updateSelectability();
    }
    
    private boolean areAllListInfosSelectable(final ArrayList<ListView.FixedViewInfo> list) {
        if (list != null) {
            final Iterator<ListView.FixedViewInfo> iterator = list.iterator();
            while (iterator.hasNext()) {
                if (!iterator.next().isSelectable) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private void updateSelectability() {
        this.mAreAllFixedViewsSelectable = (this.areAllListInfosSelectable(this.mHeaderViewInfos) && this.areAllListInfosSelectable(this.mFooterViewInfos));
    }
    
    public void addFooter(final ListView.FixedViewInfo listView.FixedViewInfo) {
        synchronized (this) {
            this.mFooterViewInfos.add(listView.FixedViewInfo);
            this.updateObserversChange();
            this.updateSelectability();
        }
    }
    
    public void addHeader(final ListView.FixedViewInfo listView.FixedViewInfo) {
        synchronized (this) {
            this.mHeaderViewInfos.add(listView.FixedViewInfo);
            this.updateObserversChange();
            this.updateSelectability();
        }
    }
    
    public boolean areAllItemsEnabled() {
        return this.mAdapter == null || (this.mAreAllFixedViewsSelectable && this.mAdapter.areAllItemsEnabled());
    }
    
    public int getCount() {
        synchronized (this) {
            if (this.mAdapter != null) {
                final int footersCount = this.getFootersCount();
                final int headersCount = this.getHeadersCount();
                final int count = this.mAdapter.getCount();
                // monitorexit(this)
                return footersCount + headersCount + count;
            }
            return this.getFootersCount() + this.getHeadersCount();
        }
    }
    
    public Filter getFilter() {
        if (this.mIsFilterable) {
            return ((Filterable)this.mAdapter).getFilter();
        }
        return null;
    }
    
    public int getFootersCount() {
        return this.mFooterViewInfos.size();
    }
    
    public int getHeadersCount() {
        return this.mHeaderViewInfos.size();
    }
    
    public Object getItem(int count) {
        synchronized (this) {
            final int headersCount = this.getHeadersCount();
            if (count < headersCount) {
                return this.mHeaderViewInfos.get(count).data;
            }
            final int n = count - headersCount;
            count = 0;
            if (this.mAdapter != null && n < (count = this.mAdapter.getCount())) {
                return this.mAdapter.getItem(n);
            }
            return this.mFooterViewInfos.get(n - count).data;
        }
    }
    
    public long getItemId(int n) {
        synchronized (this) {
            final int headersCount = this.getHeadersCount();
            if (this.mAdapter != null && n >= headersCount) {
                n -= headersCount;
                if (n < this.mAdapter.getCount()) {
                    return this.mAdapter.getItemId(n);
                }
            }
            return -1L;
        }
    }
    
    public int getItemViewType(int n) {
        final int headersCount = this.getHeadersCount();
        if (this.mAdapter != null && n >= headersCount) {
            n -= headersCount;
            if (n < this.mAdapter.getCount()) {
                return this.mAdapter.getItemViewType(n);
            }
        }
        return -2;
    }
    
    public View getView(int count, View view, final ViewGroup viewGroup) {
        synchronized (this) {
            synchronized (this) {
                final int headersCount = this.getHeadersCount();
                if (count < headersCount) {
                    view = this.mHeaderViewInfos.get(count).view;
                    return view;
                }
                final int n = count - headersCount;
                count = 0;
                if (this.mAdapter != null && n < (count = this.mAdapter.getCount())) {
                    view = this.mAdapter.getView(n, view, viewGroup);
                    return view;
                }
                view = this.mFooterViewInfos.get(n - count).view;
                return view;
            }
        }
    }
    
    public int getViewTypeCount() {
        if (this.mAdapter != null) {
            return this.mAdapter.getViewTypeCount() + 1;
        }
        return 2;
    }
    
    public ListAdapter getWrappedAdapter() {
        return this.mAdapter;
    }
    
    public boolean hasStableIds() {
        return this.mAdapter != null && this.mAdapter.hasStableIds();
    }
    
    public boolean isEmpty() {
        return this.mAdapter == null || this.mAdapter.isEmpty();
    }
    
    public boolean isEnabled(int count) {
        synchronized (this) {
            final int headersCount = this.getHeadersCount();
            if (count < headersCount) {
                return this.mHeaderViewInfos.get(count).isSelectable;
            }
            final int n = count - headersCount;
            count = 0;
            if (this.mAdapter != null && n < (count = this.mAdapter.getCount())) {
                return this.mAdapter.isEnabled(n);
            }
            return this.mFooterViewInfos.get(n - count).isSelectable;
        }
    }
    
    public void registerDataSetObserver(final DataSetObserver dataSetObserver) {
        this._observers.add(dataSetObserver);
        if (this.mAdapter != null) {
            this.mAdapter.registerDataSetObserver(dataSetObserver);
        }
    }
    
    public boolean removeFooter(final View view) {
        // monitorenter(this)
        int n = 0;
        while (true) {
            try {
                if (n >= this.mFooterViewInfos.size()) {
                    return false;
                }
                if (this.mFooterViewInfos.get(n).view == view) {
                    this.mFooterViewInfos.remove(n);
                    this.updateObserversChange();
                    this.updateSelectability();
                    return true;
                }
            }
            finally {
            }
            // monitorexit(this)
            ++n;
        }
    }
    
    public boolean removeHeader(final View view) {
        // monitorenter(this)
        int n = 0;
        while (true) {
            try {
                if (n >= this.mHeaderViewInfos.size()) {
                    return false;
                }
                if (this.mHeaderViewInfos.get(n).view == view) {
                    this.mHeaderViewInfos.remove(n);
                    this.updateSelectability();
                    this.updateObserversChange();
                    return true;
                }
            }
            finally {
            }
            // monitorexit(this)
            ++n;
        }
    }
    
    public void setAdapter(final ListAdapter mAdapter) {
        synchronized (this) {
            this.mAdapter = mAdapter;
            this.mIsFilterable = (mAdapter instanceof Filterable);
            final Iterator<DataSetObserver> iterator = this._observers.iterator();
            while (iterator.hasNext()) {
                mAdapter.registerDataSetObserver((DataSetObserver)iterator.next());
            }
            this.updateSelectability();
            this.updateObserversChange();
        }
    }
    
    public void unregisterDataSetObserver(final DataSetObserver dataSetObserver) {
        this._observers.remove(dataSetObserver);
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(dataSetObserver);
        }
    }
    
    public void updateObserversChange() {
        final Iterator<DataSetObserver> iterator = this._observers.iterator();
        while (iterator.hasNext()) {
            iterator.next().onChanged();
        }
    }
}
