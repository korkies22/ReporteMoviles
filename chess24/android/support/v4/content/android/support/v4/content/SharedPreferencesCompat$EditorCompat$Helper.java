/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 */
package android.support.v4.content;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.content.SharedPreferencesCompat;

private static class SharedPreferencesCompat.EditorCompat.Helper {
    SharedPreferencesCompat.EditorCompat.Helper() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void apply(@NonNull SharedPreferences.Editor editor) {
        try {
            editor.apply();
            return;
        }
        catch (AbstractMethodError abstractMethodError) {}
        editor.commit();
    }
}
