/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.ActivityOptions
 *  android.os.Bundle
 */
package android.support.v4.app;

import android.app.ActivityOptions;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;

@RequiresApi(value=16)
private static class ActivityOptionsCompat.ActivityOptionsCompatApi16Impl
extends ActivityOptionsCompat {
    protected final ActivityOptions mActivityOptions;

    ActivityOptionsCompat.ActivityOptionsCompatApi16Impl(ActivityOptions activityOptions) {
        this.mActivityOptions = activityOptions;
    }

    @Override
    public Bundle toBundle() {
        return this.mActivityOptions.toBundle();
    }

    @Override
    public void update(ActivityOptionsCompat activityOptionsCompat) {
        if (activityOptionsCompat instanceof ActivityOptionsCompat.ActivityOptionsCompatApi16Impl) {
            activityOptionsCompat = (ActivityOptionsCompat.ActivityOptionsCompatApi16Impl)activityOptionsCompat;
            this.mActivityOptions.update(activityOptionsCompat.mActivityOptions);
        }
    }
}
