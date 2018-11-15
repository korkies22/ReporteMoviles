/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.ContentObserver
 *  android.database.Cursor
 *  android.database.DataSetObserver
 *  android.os.Handler
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.Filter
 *  android.widget.FilterQueryProvider
 *  android.widget.Filterable
 */
package android.support.v4.widget;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.support.annotation.RestrictTo;
import android.support.v4.widget.CursorFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;

public abstract class CursorAdapter
extends BaseAdapter
implements Filterable,
CursorFilter.CursorFilterClient {
    @Deprecated
    public static final int FLAG_AUTO_REQUERY = 1;
    public static final int FLAG_REGISTER_CONTENT_OBSERVER = 2;
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    protected boolean mAutoRequery;
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    protected ChangeObserver mChangeObserver;
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    protected Context mContext;
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    protected Cursor mCursor;
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    protected CursorFilter mCursorFilter;
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    protected DataSetObserver mDataSetObserver;
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    protected boolean mDataValid;
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    protected FilterQueryProvider mFilterQueryProvider;
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    protected int mRowIDColumn;

    @Deprecated
    public CursorAdapter(Context context, Cursor cursor) {
        this.init(context, cursor, 1);
    }

    public CursorAdapter(Context context, Cursor cursor, int n) {
        this.init(context, cursor, n);
    }

    public CursorAdapter(Context context, Cursor cursor, boolean bl) {
        int n = bl ? 1 : 2;
        this.init(context, cursor, n);
    }

    public abstract void bindView(View var1, Context var2, Cursor var3);

    @Override
    public void changeCursor(Cursor cursor) {
        if ((cursor = this.swapCursor(cursor)) != null) {
            cursor.close();
        }
    }

    @Override
    public CharSequence convertToString(Cursor cursor) {
        if (cursor == null) {
            return "";
        }
        return cursor.toString();
    }

    public int getCount() {
        if (this.mDataValid && this.mCursor != null) {
            return this.mCursor.getCount();
        }
        return 0;
    }

    @Override
    public Cursor getCursor() {
        return this.mCursor;
    }

    public View getDropDownView(int n, View view, ViewGroup viewGroup) {
        if (this.mDataValid) {
            this.mCursor.moveToPosition(n);
            View view2 = view;
            if (view == null) {
                view2 = this.newDropDownView(this.mContext, this.mCursor, viewGroup);
            }
            this.bindView(view2, this.mContext, this.mCursor);
            return view2;
        }
        return null;
    }

    public Filter getFilter() {
        if (this.mCursorFilter == null) {
            this.mCursorFilter = new CursorFilter(this);
        }
        return this.mCursorFilter;
    }

    public FilterQueryProvider getFilterQueryProvider() {
        return this.mFilterQueryProvider;
    }

    public Object getItem(int n) {
        if (this.mDataValid && this.mCursor != null) {
            this.mCursor.moveToPosition(n);
            return this.mCursor;
        }
        return null;
    }

    public long getItemId(int n) {
        if (this.mDataValid && this.mCursor != null) {
            if (this.mCursor.moveToPosition(n)) {
                return this.mCursor.getLong(this.mRowIDColumn);
            }
            return 0L;
        }
        return 0L;
    }

    public View getView(int n, View object, ViewGroup viewGroup) {
        if (!this.mDataValid) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        }
        if (!this.mCursor.moveToPosition(n)) {
            object = new StringBuilder();
            object.append("couldn't move cursor to position ");
            object.append(n);
            throw new IllegalStateException(object.toString());
        }
        View view = object;
        if (object == null) {
            view = this.newView(this.mContext, this.mCursor, viewGroup);
        }
        this.bindView(view, this.mContext, this.mCursor);
        return view;
    }

    public boolean hasStableIds() {
        return true;
    }

    void init(Context context, Cursor cursor, int n) {
        boolean bl = false;
        if ((n & 1) == 1) {
            n |= 2;
            this.mAutoRequery = true;
        } else {
            this.mAutoRequery = false;
        }
        if (cursor != null) {
            bl = true;
        }
        this.mCursor = cursor;
        this.mDataValid = bl;
        this.mContext = context;
        int n2 = bl ? cursor.getColumnIndexOrThrow("_id") : -1;
        this.mRowIDColumn = n2;
        if ((n & 2) == 2) {
            this.mChangeObserver = new ChangeObserver();
            this.mDataSetObserver = new MyDataSetObserver();
        } else {
            this.mChangeObserver = null;
            this.mDataSetObserver = null;
        }
        if (bl) {
            if (this.mChangeObserver != null) {
                cursor.registerContentObserver((ContentObserver)this.mChangeObserver);
            }
            if (this.mDataSetObserver != null) {
                cursor.registerDataSetObserver(this.mDataSetObserver);
            }
        }
    }

    @Deprecated
    protected void init(Context context, Cursor cursor, boolean bl) {
        int n = bl ? 1 : 2;
        this.init(context, cursor, n);
    }

    public View newDropDownView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return this.newView(context, cursor, viewGroup);
    }

    public abstract View newView(Context var1, Cursor var2, ViewGroup var3);

    protected void onContentChanged() {
        if (this.mAutoRequery && this.mCursor != null && !this.mCursor.isClosed()) {
            this.mDataValid = this.mCursor.requery();
        }
    }

    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence charSequence) {
        if (this.mFilterQueryProvider != null) {
            return this.mFilterQueryProvider.runQuery(charSequence);
        }
        return this.mCursor;
    }

    public void setFilterQueryProvider(FilterQueryProvider filterQueryProvider) {
        this.mFilterQueryProvider = filterQueryProvider;
    }

    public Cursor swapCursor(Cursor cursor) {
        if (cursor == this.mCursor) {
            return null;
        }
        Cursor cursor2 = this.mCursor;
        if (cursor2 != null) {
            if (this.mChangeObserver != null) {
                cursor2.unregisterContentObserver((ContentObserver)this.mChangeObserver);
            }
            if (this.mDataSetObserver != null) {
                cursor2.unregisterDataSetObserver(this.mDataSetObserver);
            }
        }
        this.mCursor = cursor;
        if (cursor != null) {
            if (this.mChangeObserver != null) {
                cursor.registerContentObserver((ContentObserver)this.mChangeObserver);
            }
            if (this.mDataSetObserver != null) {
                cursor.registerDataSetObserver(this.mDataSetObserver);
            }
            this.mRowIDColumn = cursor.getColumnIndexOrThrow("_id");
            this.mDataValid = true;
            this.notifyDataSetChanged();
            return cursor2;
        }
        this.mRowIDColumn = -1;
        this.mDataValid = false;
        this.notifyDataSetInvalidated();
        return cursor2;
    }

    private class ChangeObserver
    extends ContentObserver {
        ChangeObserver() {
            super(new Handler());
        }

        public boolean deliverSelfNotifications() {
            return true;
        }

        public void onChange(boolean bl) {
            CursorAdapter.this.onContentChanged();
        }
    }

    private class MyDataSetObserver
    extends DataSetObserver {
        MyDataSetObserver() {
        }

        public void onChanged() {
            CursorAdapter.this.mDataValid = true;
            CursorAdapter.this.notifyDataSetChanged();
        }

        public void onInvalidated() {
            CursorAdapter.this.mDataValid = false;
            CursorAdapter.this.notifyDataSetInvalidated();
        }
    }

}
