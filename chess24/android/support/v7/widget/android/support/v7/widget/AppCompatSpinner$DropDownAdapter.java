/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.database.DataSetObserver
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ListAdapter
 *  android.widget.SpinnerAdapter
 *  android.widget.ThemedSpinnerAdapter
 */
package android.support.v7.widget;

import android.content.res.Resources;
import android.database.DataSetObserver;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;

private static class AppCompatSpinner.DropDownAdapter
implements ListAdapter,
SpinnerAdapter {
    private SpinnerAdapter mAdapter;
    private ListAdapter mListAdapter;

    public AppCompatSpinner.DropDownAdapter(@Nullable SpinnerAdapter spinnerAdapter, @Nullable Resources.Theme theme) {
        this.mAdapter = spinnerAdapter;
        if (spinnerAdapter instanceof ListAdapter) {
            this.mListAdapter = (ListAdapter)spinnerAdapter;
        }
        if (theme != null) {
            if (Build.VERSION.SDK_INT >= 23 && spinnerAdapter instanceof android.widget.ThemedSpinnerAdapter) {
                if ((spinnerAdapter = (android.widget.ThemedSpinnerAdapter)spinnerAdapter).getDropDownViewTheme() != theme) {
                    spinnerAdapter.setDropDownViewTheme(theme);
                    return;
                }
            } else if (spinnerAdapter instanceof ThemedSpinnerAdapter && (spinnerAdapter = (ThemedSpinnerAdapter)spinnerAdapter).getDropDownViewTheme() == null) {
                spinnerAdapter.setDropDownViewTheme(theme);
            }
        }
    }

    public boolean areAllItemsEnabled() {
        ListAdapter listAdapter = this.mListAdapter;
        if (listAdapter != null) {
            return listAdapter.areAllItemsEnabled();
        }
        return true;
    }

    public int getCount() {
        if (this.mAdapter == null) {
            return 0;
        }
        return this.mAdapter.getCount();
    }

    public View getDropDownView(int n, View view, ViewGroup viewGroup) {
        if (this.mAdapter == null) {
            return null;
        }
        return this.mAdapter.getDropDownView(n, view, viewGroup);
    }

    public Object getItem(int n) {
        if (this.mAdapter == null) {
            return null;
        }
        return this.mAdapter.getItem(n);
    }

    public long getItemId(int n) {
        if (this.mAdapter == null) {
            return -1L;
        }
        return this.mAdapter.getItemId(n);
    }

    public int getItemViewType(int n) {
        return 0;
    }

    public View getView(int n, View view, ViewGroup viewGroup) {
        return this.getDropDownView(n, view, viewGroup);
    }

    public int getViewTypeCount() {
        return 1;
    }

    public boolean hasStableIds() {
        if (this.mAdapter != null && this.mAdapter.hasStableIds()) {
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        if (this.getCount() == 0) {
            return true;
        }
        return false;
    }

    public boolean isEnabled(int n) {
        ListAdapter listAdapter = this.mListAdapter;
        if (listAdapter != null) {
            return listAdapter.isEnabled(n);
        }
        return true;
    }

    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        if (this.mAdapter != null) {
            this.mAdapter.registerDataSetObserver(dataSetObserver);
        }
    }

    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(dataSetObserver);
        }
    }
}
