/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package android.support.v7.widget;

import android.content.Intent;
import android.support.v7.widget.ActivityChooserModel;
import android.support.v7.widget.ShareActionProvider;

private class ShareActionProvider.ShareActivityChooserModelPolicy
implements ActivityChooserModel.OnChooseActivityListener {
    ShareActionProvider.ShareActivityChooserModelPolicy() {
    }

    @Override
    public boolean onChooseActivity(ActivityChooserModel activityChooserModel, Intent intent) {
        if (ShareActionProvider.this.mOnShareTargetSelectedListener != null) {
            ShareActionProvider.this.mOnShareTargetSelectedListener.onShareTargetSelected(ShareActionProvider.this, intent);
        }
        return false;
    }
}
