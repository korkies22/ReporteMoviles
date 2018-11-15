/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.content.res.Configuration
 */
package android.support.v7.content.res;

import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.content.res.AppCompatResources;

private static class AppCompatResources.ColorStateListCacheEntry {
    final Configuration configuration;
    final ColorStateList value;

    AppCompatResources.ColorStateListCacheEntry(@NonNull ColorStateList colorStateList, @NonNull Configuration configuration) {
        this.value = colorStateList;
        this.configuration = configuration;
    }
}
