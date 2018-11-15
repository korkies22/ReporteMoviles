/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.database.DataSetObserver
 *  android.graphics.drawable.Drawable
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.support.v7.appcompat.R;
import android.support.v7.widget.ActivityChooserModel;
import android.support.v7.widget.ActivityChooserView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

private class ActivityChooserView.ActivityChooserViewAdapter
extends BaseAdapter {
    private static final int ITEM_VIEW_TYPE_ACTIVITY = 0;
    private static final int ITEM_VIEW_TYPE_COUNT = 3;
    private static final int ITEM_VIEW_TYPE_FOOTER = 1;
    public static final int MAX_ACTIVITY_COUNT_DEFAULT = 4;
    public static final int MAX_ACTIVITY_COUNT_UNLIMITED = Integer.MAX_VALUE;
    private ActivityChooserModel mDataModel;
    private boolean mHighlightDefaultActivity;
    private int mMaxActivityCount = 4;
    private boolean mShowDefaultActivity;
    private boolean mShowFooterView;

    ActivityChooserView.ActivityChooserViewAdapter() {
    }

    public int getActivityCount() {
        return this.mDataModel.getActivityCount();
    }

    public int getCount() {
        int n;
        int n2 = n = this.mDataModel.getActivityCount();
        if (!this.mShowDefaultActivity) {
            n2 = n;
            if (this.mDataModel.getDefaultActivity() != null) {
                n2 = n - 1;
            }
        }
        n2 = n = Math.min(n2, this.mMaxActivityCount);
        if (this.mShowFooterView) {
            n2 = n + 1;
        }
        return n2;
    }

    public ActivityChooserModel getDataModel() {
        return this.mDataModel;
    }

    public ResolveInfo getDefaultActivity() {
        return this.mDataModel.getDefaultActivity();
    }

    public int getHistorySize() {
        return this.mDataModel.getHistorySize();
    }

    public Object getItem(int n) {
        switch (this.getItemViewType(n)) {
            default: {
                throw new IllegalArgumentException();
            }
            case 1: {
                return null;
            }
            case 0: 
        }
        int n2 = n;
        if (!this.mShowDefaultActivity) {
            n2 = n;
            if (this.mDataModel.getDefaultActivity() != null) {
                n2 = n + 1;
            }
        }
        return this.mDataModel.getActivity(n2);
    }

    public long getItemId(int n) {
        return n;
    }

    public int getItemViewType(int n) {
        if (this.mShowFooterView && n == this.getCount() - 1) {
            return 1;
        }
        return 0;
    }

    public boolean getShowDefaultActivity() {
        return this.mShowDefaultActivity;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public View getView(int n, View view, ViewGroup viewGroup) {
        View view2;
        block10 : {
            block9 : {
                switch (this.getItemViewType(n)) {
                    default: {
                        throw new IllegalArgumentException();
                    }
                    case 1: {
                        View view3;
                        if (view != null) {
                            view3 = view;
                            if (view.getId() == 1) return view3;
                        }
                        view3 = LayoutInflater.from((Context)ActivityChooserView.this.getContext()).inflate(R.layout.abc_activity_chooser_view_list_item, viewGroup, false);
                        view3.setId(1);
                        ((TextView)view3.findViewById(R.id.title)).setText((CharSequence)ActivityChooserView.this.getContext().getString(R.string.abc_activity_chooser_view_see_all));
                        return view3;
                    }
                    case 0: 
                }
                if (view == null) break block9;
                view2 = view;
                if (view.getId() == R.id.list_item) break block10;
            }
            view2 = LayoutInflater.from((Context)ActivityChooserView.this.getContext()).inflate(R.layout.abc_activity_chooser_view_list_item, viewGroup, false);
        }
        view = ActivityChooserView.this.getContext().getPackageManager();
        viewGroup = (ImageView)view2.findViewById(R.id.icon);
        ResolveInfo resolveInfo = (ResolveInfo)this.getItem(n);
        viewGroup.setImageDrawable(resolveInfo.loadIcon((PackageManager)view));
        ((TextView)view2.findViewById(R.id.title)).setText(resolveInfo.loadLabel((PackageManager)view));
        if (this.mShowDefaultActivity && n == 0 && this.mHighlightDefaultActivity) {
            view2.setActivated(true);
            return view2;
        }
        view2.setActivated(false);
        return view2;
    }

    public int getViewTypeCount() {
        return 3;
    }

    public int measureContentWidth() {
        int n = this.mMaxActivityCount;
        this.mMaxActivityCount = Integer.MAX_VALUE;
        int n2 = View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
        int n3 = View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
        int n4 = this.getCount();
        int n5 = 0;
        View view = null;
        for (int i = 0; i < n4; ++i) {
            view = this.getView(i, view, null);
            view.measure(n2, n3);
            n5 = Math.max(n5, view.getMeasuredWidth());
        }
        this.mMaxActivityCount = n;
        return n5;
    }

    public void setDataModel(ActivityChooserModel activityChooserModel) {
        ActivityChooserModel activityChooserModel2 = ActivityChooserView.this.mAdapter.getDataModel();
        if (activityChooserModel2 != null && ActivityChooserView.this.isShown()) {
            activityChooserModel2.unregisterObserver((Object)ActivityChooserView.this.mModelDataSetObserver);
        }
        this.mDataModel = activityChooserModel;
        if (activityChooserModel != null && ActivityChooserView.this.isShown()) {
            activityChooserModel.registerObserver((Object)ActivityChooserView.this.mModelDataSetObserver);
        }
        this.notifyDataSetChanged();
    }

    public void setMaxActivityCount(int n) {
        if (this.mMaxActivityCount != n) {
            this.mMaxActivityCount = n;
            this.notifyDataSetChanged();
        }
    }

    public void setShowDefaultActivity(boolean bl, boolean bl2) {
        if (this.mShowDefaultActivity != bl || this.mHighlightDefaultActivity != bl2) {
            this.mShowDefaultActivity = bl;
            this.mHighlightDefaultActivity = bl2;
            this.notifyDataSetChanged();
        }
    }

    public void setShowFooterView(boolean bl) {
        if (this.mShowFooterView != bl) {
            this.mShowFooterView = bl;
            this.notifyDataSetChanged();
        }
    }
}
