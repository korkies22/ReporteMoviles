/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ResolveInfo
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnLongClickListener
 *  android.widget.Adapter
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.FrameLayout
 *  android.widget.PopupWindow
 *  android.widget.PopupWindow$OnDismissListener
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.v4.view.ActionProvider;
import android.support.v7.widget.ActivityChooserModel;
import android.support.v7.widget.ActivityChooserView;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

private class ActivityChooserView.Callbacks
implements AdapterView.OnItemClickListener,
View.OnClickListener,
View.OnLongClickListener,
PopupWindow.OnDismissListener {
    ActivityChooserView.Callbacks() {
    }

    private void notifyOnDismissListener() {
        if (ActivityChooserView.this.mOnDismissListener != null) {
            ActivityChooserView.this.mOnDismissListener.onDismiss();
        }
    }

    public void onClick(View view) {
        block7 : {
            block6 : {
                block5 : {
                    if (view != ActivityChooserView.this.mDefaultActivityButton) break block5;
                    ActivityChooserView.this.dismissPopup();
                    view = ActivityChooserView.this.mAdapter.getDefaultActivity();
                    int n = ActivityChooserView.this.mAdapter.getDataModel().getActivityIndex((ResolveInfo)view);
                    view = ActivityChooserView.this.mAdapter.getDataModel().chooseActivity(n);
                    if (view != null) {
                        view.addFlags(524288);
                        ActivityChooserView.this.getContext().startActivity((Intent)view);
                        return;
                    }
                    break block6;
                }
                if (view != ActivityChooserView.this.mExpandActivityOverflowButton) break block7;
                ActivityChooserView.this.mIsSelectingDefaultActivity = false;
                ActivityChooserView.this.showPopupUnchecked(ActivityChooserView.this.mInitialActivityCount);
            }
            return;
        }
        throw new IllegalArgumentException();
    }

    public void onDismiss() {
        this.notifyOnDismissListener();
        if (ActivityChooserView.this.mProvider != null) {
            ActivityChooserView.this.mProvider.subUiVisibilityChanged(false);
        }
    }

    public void onItemClick(AdapterView<?> intent, View view, int n, long l) {
        switch (((ActivityChooserView.ActivityChooserViewAdapter)intent.getAdapter()).getItemViewType(n)) {
            default: {
                throw new IllegalArgumentException();
            }
            case 1: {
                ActivityChooserView.this.showPopupUnchecked(Integer.MAX_VALUE);
                return;
            }
            case 0: 
        }
        ActivityChooserView.this.dismissPopup();
        if (ActivityChooserView.this.mIsSelectingDefaultActivity) {
            if (n > 0) {
                ActivityChooserView.this.mAdapter.getDataModel().setDefaultActivity(n);
                return;
            }
        } else {
            if (!ActivityChooserView.this.mAdapter.getShowDefaultActivity()) {
                ++n;
            }
            intent = ActivityChooserView.this.mAdapter.getDataModel().chooseActivity(n);
            if (intent != null) {
                intent.addFlags(524288);
                ActivityChooserView.this.getContext().startActivity(intent);
            }
        }
    }

    public boolean onLongClick(View view) {
        if (view == ActivityChooserView.this.mDefaultActivityButton) {
            if (ActivityChooserView.this.mAdapter.getCount() > 0) {
                ActivityChooserView.this.mIsSelectingDefaultActivity = true;
                ActivityChooserView.this.showPopupUnchecked(ActivityChooserView.this.mInitialActivityCount);
            }
            return true;
        }
        throw new IllegalArgumentException();
    }
}
