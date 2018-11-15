/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.content;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;

public final class MimeTypeFilter {
    private MimeTypeFilter() {
    }

    @Nullable
    public static String matches(@Nullable String arrstring, @NonNull String[] arrstring2) {
        if (arrstring == null) {
            return null;
        }
        arrstring = arrstring.split("/");
        for (String string : arrstring2) {
            if (!MimeTypeFilter.mimeTypeAgainstFilter(arrstring, string.split("/"))) continue;
            return string;
        }
        return null;
    }

    @Nullable
    public static String matches(@Nullable String[] arrstring, @NonNull String arrstring2) {
        if (arrstring == null) {
            return null;
        }
        arrstring2 = arrstring2.split("/");
        for (String string : arrstring) {
            if (!MimeTypeFilter.mimeTypeAgainstFilter(string.split("/"), arrstring2)) continue;
            return string;
        }
        return null;
    }

    public static boolean matches(@Nullable String string, @NonNull String string2) {
        if (string == null) {
            return false;
        }
        return MimeTypeFilter.mimeTypeAgainstFilter(string.split("/"), string2.split("/"));
    }

    @NonNull
    public static String[] matchesMany(@Nullable String[] arrstring, @NonNull String arrstring2) {
        if (arrstring == null) {
            return new String[0];
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        arrstring2 = arrstring2.split("/");
        for (String string : arrstring) {
            if (!MimeTypeFilter.mimeTypeAgainstFilter(string.split("/"), arrstring2)) continue;
            arrayList.add(string);
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    private static boolean mimeTypeAgainstFilter(@NonNull String[] arrstring, @NonNull String[] arrstring2) {
        if (arrstring2.length != 2) {
            throw new IllegalArgumentException("Ill-formatted MIME type filter. Must be type/subtype.");
        }
        if (!arrstring2[0].isEmpty() && !arrstring2[1].isEmpty()) {
            if (arrstring.length != 2) {
                return false;
            }
            if (!"*".equals(arrstring2[0]) && !arrstring2[0].equals(arrstring[0])) {
                return false;
            }
            if (!"*".equals(arrstring2[1]) && !arrstring2[1].equals(arrstring[1])) {
                return false;
            }
            return true;
        }
        throw new IllegalArgumentException("Ill-formatted MIME type filter. Type or subtype empty.");
    }
}
