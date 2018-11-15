/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.ActivityOptions
 *  android.graphics.Rect
 */
package android.support.v4.app;

import android.app.ActivityOptions;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;

@RequiresApi(value=24)
private static class ActivityOptionsCompat.ActivityOptionsCompatApi24Impl
extends ActivityOptionsCompat.ActivityOptionsCompatApi23Impl {
    ActivityOptionsCompat.ActivityOptionsCompatApi24Impl(ActivityOptions activityOptions) {
        super(activityOptions);
    }

    @Override
    public Rect getLaunchBounds() {
        return this.mActivityOptions.getLaunchBounds();
    }

    @Override
    public ActivityOptionsCompat setLaunchBounds(@Nullable Rect rect) {
        return new ActivityOptionsCompat.ActivityOptionsCompatApi24Impl(this.mActivityOptions.setLaunchBounds(rect));
    }
}
