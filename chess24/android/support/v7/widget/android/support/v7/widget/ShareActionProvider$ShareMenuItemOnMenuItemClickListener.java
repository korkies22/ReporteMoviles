/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.view.MenuItem
 *  android.view.MenuItem$OnMenuItemClickListener
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.ActivityChooserModel;
import android.support.v7.widget.ShareActionProvider;
import android.view.MenuItem;

private class ShareActionProvider.ShareMenuItemOnMenuItemClickListener
implements MenuItem.OnMenuItemClickListener {
    ShareActionProvider.ShareMenuItemOnMenuItemClickListener() {
    }

    public boolean onMenuItemClick(MenuItem menuItem) {
        menuItem = ActivityChooserModel.get(ShareActionProvider.this.mContext, ShareActionProvider.this.mShareHistoryFileName).chooseActivity(menuItem.getItemId());
        if (menuItem != null) {
            String string = menuItem.getAction();
            if ("android.intent.action.SEND".equals(string) || "android.intent.action.SEND_MULTIPLE".equals(string)) {
                ShareActionProvider.this.updateIntent((Intent)menuItem);
            }
            ShareActionProvider.this.mContext.startActivity((Intent)menuItem);
        }
        return true;
    }
}
