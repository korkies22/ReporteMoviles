/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package android.support.v4.database;

import android.text.TextUtils;

@Deprecated
public final class DatabaseUtilsCompat {
    private DatabaseUtilsCompat() {
    }

    @Deprecated
    public static String[] appendSelectionArgs(String[] arrstring, String[] arrstring2) {
        if (arrstring != null) {
            if (arrstring.length == 0) {
                return arrstring2;
            }
            String[] arrstring3 = new String[arrstring.length + arrstring2.length];
            System.arraycopy(arrstring, 0, arrstring3, 0, arrstring.length);
            System.arraycopy(arrstring2, 0, arrstring3, arrstring.length, arrstring2.length);
            return arrstring3;
        }
        return arrstring2;
    }

    @Deprecated
    public static String concatenateWhere(String string, String string2) {
        if (TextUtils.isEmpty((CharSequence)string)) {
            return string2;
        }
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append(string);
        stringBuilder.append(") AND (");
        stringBuilder.append(string2);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
