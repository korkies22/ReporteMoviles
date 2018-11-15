/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.database.DataSetObserver
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.Filter
 *  android.widget.Filterable
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.ListView$FixedViewInfo
 *  android.widget.WrapperListAdapter
 */
package de.cisha.android.ui.list;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.WrapperListAdapter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MutableHeaderViewListAdapter
implements WrapperListAdapter,
Filterable {
    private Set<DataSetObserver> _observers = new HashSet<DataSetObserver>();
    private ListAdapter mAdapter;
    boolean mAreAllFixedViewsSelectable;
    ArrayList<ListView.FixedViewInfo> mFooterViewInfos = new ArrayList();
    ArrayList<ListView.FixedViewInfo> mHeaderViewInfos = new ArrayList();
    private boolean mIsFilterable;

    public MutableHeaderViewListAdapter() {
        this.updateSelectability();
    }

    private boolean areAllListInfosSelectable(ArrayList<ListView.FixedViewInfo> object) {
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                if (((ListView.FixedViewInfo)object.next()).isSelectable) continue;
                return false;
            }
        }
        return true;
    }

    private void updateSelectability() {
        boolean bl = this.areAllListInfosSelectable(this.mHeaderViewInfos) && this.areAllListInfosSelectable(this.mFooterViewInfos);
        this.mAreAllFixedViewsSelectable = bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addFooter(ListView.FixedViewInfo fixedViewInfo) {
        synchronized (this) {
            this.mFooterViewInfos.add(fixedViewInfo);
            this.updateObserversChange();
            this.updateSelectability();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addHeader(ListView.FixedViewInfo fixedViewInfo) {
        synchronized (this) {
            this.mHeaderViewInfos.add(fixedViewInfo);
            this.updateObserversChange();
            this.updateSelectability();
            return;
        }
    }

    public boolean areAllItemsEnabled() {
        if (this.mAdapter != null) {
            if (this.mAreAllFixedViewsSelectable && this.mAdapter.areAllItemsEnabled()) {
                return true;
            }
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getCount() {
        synchronized (this) {
            if (this.mAdapter != null) {
                int n = this.getFootersCount();
                int n2 = this.getHeadersCount();
                int n3 = this.mAdapter.getCount();
                return n + n2 + n3;
            }
            int n = this.getFootersCount();
            int n4 = this.getHeadersCount();
            return n + n4;
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Object getItem(int n) {
        synchronized (this) {
            int n2 = this.getHeadersCount();
            if (n < n2) {
                return this.mHeaderViewInfos.get((int)n).data;
            }
            int n3 = n - n2;
            n = 0;
            if (this.mAdapter == null) return this.mFooterViewInfos.get((int)(n3 - n)).data;
            n = n2 = this.mAdapter.getCount();
            if (n3 >= n2) return this.mFooterViewInfos.get((int)(n3 - n)).data;
            return this.mAdapter.getItem(n3);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long getItemId(int n) {
        synchronized (this) {
            int n2 = this.getHeadersCount();
            if (this.mAdapter == null) return -1L;
            if (n < n2) return -1L;
            if ((n -= n2) >= this.mAdapter.getCount()) return -1L;
            return this.mAdapter.getItemId(n);
        }
    }

    public int getItemViewType(int n) {
        int n2 = this.getHeadersCount();
        if (this.mAdapter != null && n >= n2 && (n -= n2) < this.mAdapter.getCount()) {
            return this.mAdapter.getItemViewType(n);
        }
        return -2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public View getView(int n, View view, ViewGroup viewGroup) {
        synchronized (this) {
            synchronized (this) {
                int n2 = this.getHeadersCount();
                if (n < n2) {
                    return this.mHeaderViewInfos.get((int)n).view;
                }
                int n3 = n - n2;
                n = 0;
                if (this.mAdapter == null) return this.mFooterViewInfos.get((int)(n3 - n)).view;
                n = n2 = this.mAdapter.getCount();
                if (n3 >= n2) return this.mFooterViewInfos.get((int)(n3 - n)).view;
                return this.mAdapter.getView(n3, view, viewGroup);
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
        if (this.mAdapter != null) {
            return this.mAdapter.hasStableIds();
        }
        return false;
    }

    public boolean isEmpty() {
        if (this.mAdapter != null && !this.mAdapter.isEmpty()) {
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isEnabled(int n) {
        synchronized (this) {
            int n2 = this.getHeadersCount();
            if (n < n2) {
                return this.mHeaderViewInfos.get((int)n).isSelectable;
            }
            int n3 = n - n2;
            n = 0;
            if (this.mAdapter == null) return this.mFooterViewInfos.get((int)(n3 - n)).isSelectable;
            n = n2 = this.mAdapter.getCount();
            if (n3 >= n2) return this.mFooterViewInfos.get((int)(n3 - n)).isSelectable;
            return this.mAdapter.isEnabled(n3);
        }
    }

    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        this._observers.add(dataSetObserver);
        if (this.mAdapter != null) {
            this.mAdapter.registerDataSetObserver(dataSetObserver);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean removeFooter(View view) {
        synchronized (this) {
            int n = 0;
            while (n < this.mFooterViewInfos.size()) {
                if (this.mFooterViewInfos.get((int)n).view == view) {
                    this.mFooterViewInfos.remove(n);
                    this.updateObserversChange();
                    this.updateSelectability();
                    return true;
                }
                ++n;
            }
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean removeHeader(View view) {
        synchronized (this) {
            int n = 0;
            while (n < this.mHeaderViewInfos.size()) {
                if (this.mHeaderViewInfos.get((int)n).view == view) {
                    this.mHeaderViewInfos.remove(n);
                    this.updateSelectability();
                    this.updateObserversChange();
                    return true;
                }
                ++n;
            }
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setAdapter(ListAdapter listAdapter) {
        synchronized (this) {
            this.mAdapter = listAdapter;
            this.mIsFilterable = listAdapter instanceof Filterable;
            Iterator<DataSetObserver> iterator = this._observers.iterator();
            do {
                if (!iterator.hasNext()) {
                    this.updateSelectability();
                    this.updateObserversChange();
                    return;
                }
                listAdapter.registerDataSetObserver(iterator.next());
            } while (true);
        }
    }

    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        this._observers.remove((Object)dataSetObserver);
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(dataSetObserver);
        }
    }

    public void updateObserversChange() {
        Iterator<DataSetObserver> iterator = this._observers.iterator();
        while (iterator.hasNext()) {
            iterator.next().onChanged();
        }
    }
}
