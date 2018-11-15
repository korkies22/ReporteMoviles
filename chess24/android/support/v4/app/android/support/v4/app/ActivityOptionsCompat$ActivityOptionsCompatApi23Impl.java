/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.ActivityOptions
 *  android.app.PendingIntent
 */
package android.support.v4.app;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;

@RequiresApi(value=23)
private static class ActivityOptionsCompat.ActivityOptionsCompatApi23Impl
extends ActivityOptionsCompat.ActivityOptionsCompatApi16Impl {
    ActivityOptionsCompat.ActivityOptionsCompatApi23Impl(ActivityOptions activityOptions) {
        super(activityOptions);
    }

    @Override
    public void requestUsageTimeReport(PendingIntent pendingIntent) {
        this.mActivityOptions.requestUsageTimeReport(pendingIntent);
    }
}
