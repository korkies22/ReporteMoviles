/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package android.support.v7.widget;

import android.content.Intent;
import android.support.v7.widget.ActivityChooserModel;
import java.util.List;

public static interface ActivityChooserModel.ActivitySorter {
    public void sort(Intent var1, List<ActivityChooserModel.ActivityResolveInfo> var2, List<ActivityChooserModel.HistoricalRecord> var3);
}
