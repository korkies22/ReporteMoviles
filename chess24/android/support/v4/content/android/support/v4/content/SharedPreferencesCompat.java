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

@Deprecated
public final class SharedPreferencesCompat {
    private SharedPreferencesCompat() {
    }

    @Deprecated
    public static final class EditorCompat {
        private static EditorCompat sInstance;
        private final EditorCompat$Helper mHelper = new EditorCompat$Helper();

        private EditorCompat() {
        }

        @Deprecated
        public static EditorCompat getInstance() {
            if (sInstance == null) {
                sInstance = new EditorCompat();
            }
            return sInstance;
        }

        @Deprecated
        public void apply(@NonNull SharedPreferences.Editor editor) {
            this.mHelper.apply(editor);
        }
    }

    private static class EditorCompat$Helper {
        EditorCompat$Helper() {
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

}
