/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Intent
 *  android.content.pm.ActivityInfo
 *  android.content.pm.ResolveInfo
 */
package android.support.v7.widget;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.support.v7.widget.ActivityChooserModel;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

private static final class ActivityChooserModel.DefaultSorter
implements ActivityChooserModel.ActivitySorter {
    private static final float WEIGHT_DECAY_COEFFICIENT = 0.95f;
    private final Map<ComponentName, ActivityChooserModel.ActivityResolveInfo> mPackageNameToActivityMap = new HashMap<ComponentName, ActivityChooserModel.ActivityResolveInfo>();

    ActivityChooserModel.DefaultSorter() {
    }

    @Override
    public void sort(Intent object, List<ActivityChooserModel.ActivityResolveInfo> list, List<ActivityChooserModel.HistoricalRecord> list2) {
        int n;
        Object object2;
        object = this.mPackageNameToActivityMap;
        object.clear();
        int n2 = list.size();
        for (n = 0; n < n2; ++n) {
            object2 = list.get(n);
            object2.weight = 0.0f;
            object.put(new ComponentName(object2.resolveInfo.activityInfo.packageName, object2.resolveInfo.activityInfo.name), object2);
        }
        float f = 1.0f;
        for (n = list2.size() - 1; n >= 0; --n) {
            object2 = list2.get(n);
            ActivityChooserModel.ActivityResolveInfo activityResolveInfo = (ActivityChooserModel.ActivityResolveInfo)object.get((Object)object2.activity);
            float f2 = f;
            if (activityResolveInfo != null) {
                activityResolveInfo.weight += object2.weight * f;
                f2 = f * 0.95f;
            }
            f = f2;
        }
        Collections.sort(list);
    }
}
