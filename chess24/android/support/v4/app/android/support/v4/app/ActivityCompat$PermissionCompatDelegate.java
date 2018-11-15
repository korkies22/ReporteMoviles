/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Intent
 */
package android.support.v4.app;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

public static interface ActivityCompat.PermissionCompatDelegate {
    public boolean onActivityResult(@NonNull Activity var1, @IntRange(from=0L) int var2, int var3, @Nullable Intent var4);

    public boolean requestPermissions(@NonNull Activity var1, @NonNull String[] var2, @IntRange(from=0L) int var3);
}
