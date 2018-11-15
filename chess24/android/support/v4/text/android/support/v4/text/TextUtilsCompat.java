/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 */
package android.support.v4.text;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.text.ICUCompat;
import android.text.TextUtils;
import java.util.Locale;

public final class TextUtilsCompat {
    private static final String ARAB_SCRIPT_SUBTAG = "Arab";
    private static final String HEBR_SCRIPT_SUBTAG = "Hebr";
    private static final Locale ROOT = new Locale("", "");

    private TextUtilsCompat() {
    }

    private static int getLayoutDirectionFromFirstChar(@NonNull Locale locale) {
        switch (Character.getDirectionality(locale.getDisplayName(locale).charAt(0))) {
            default: {
                return 0;
            }
            case 1: 
            case 2: 
        }
        return 1;
    }

    public static int getLayoutDirectionFromLocale(@Nullable Locale locale) {
        if (Build.VERSION.SDK_INT >= 17) {
            return TextUtils.getLayoutDirectionFromLocale((Locale)locale);
        }
        if (locale != null && !locale.equals(ROOT)) {
            String string = ICUCompat.maximizeAndGetScript(locale);
            if (string == null) {
                return TextUtilsCompat.getLayoutDirectionFromFirstChar(locale);
            }
            if (string.equalsIgnoreCase(ARAB_SCRIPT_SUBTAG) || string.equalsIgnoreCase(HEBR_SCRIPT_SUBTAG)) {
                return 1;
            }
        }
        return 0;
    }

    @NonNull
    public static String htmlEncode(@NonNull String string) {
        if (Build.VERSION.SDK_INT >= 17) {
            return TextUtils.htmlEncode((String)string);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c != '\"') {
                if (c != '<') {
                    if (c != '>') {
                        switch (c) {
                            default: {
                                stringBuilder.append(c);
                                break;
                            }
                            case '\'': {
                                stringBuilder.append("&#39;");
                                break;
                            }
                            case '&': {
                                stringBuilder.append("&amp;");
                                break;
                            }
                        }
                        continue;
                    }
                    stringBuilder.append("&gt;");
                    continue;
                }
                stringBuilder.append("&lt;");
                continue;
            }
            stringBuilder.append("&quot;");
        }
        return stringBuilder.toString();
    }
}
