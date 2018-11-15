/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.os;

import android.support.annotation.RestrictTo;
import java.util.Locale;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
final class LocaleHelper {
    LocaleHelper() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static Locale forLanguageTag(String string) {
        Object object;
        if (string.contains("-")) {
            object = string.split("-");
            if (((String[])object).length > 2) {
                return new Locale(object[0], object[1], object[2]);
            }
            if (((String[])object).length > 1) {
                return new Locale(object[0], object[1]);
            }
            if (((String[])object).length == 1) {
                return new Locale(object[0]);
            }
        } else {
            if (!string.contains("_")) return new Locale(string);
            object = string.split("_");
            if (((String[])object).length > 2) {
                return new Locale(object[0], object[1], (String)object[2]);
            }
            if (((String[])object).length > 1) {
                return new Locale(object[0], (String)object[1]);
            }
            if (((String[])object).length == 1) {
                return new Locale((String)object[0]);
            }
        }
        object = new StringBuilder();
        object.append("Can not parse language tag: [");
        object.append(string);
        object.append("]");
        throw new IllegalArgumentException(object.toString());
    }

    static String toLanguageTag(Locale locale) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(locale.getLanguage());
        String string = locale.getCountry();
        if (string != null && !string.isEmpty()) {
            stringBuilder.append("-");
            stringBuilder.append(locale.getCountry());
        }
        return stringBuilder.toString();
    }
}
