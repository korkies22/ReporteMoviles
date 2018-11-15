/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.util;

import java.util.Arrays;
import java.util.List;

public final class HTMLStripper {
    public static String strip(String string) {
        String string2;
        String string3 = string2 = "";
        if (string != null) {
            string3 = string2;
            if (string.length() > 0) {
                string3 = string.replaceAll(String.format("<%s[^>]+>", HTMLStripper.tagsToRemove().toString()), "");
            }
        }
        return string3;
    }

    private static List<String> tagsToRemove() {
        return Arrays.asList("a", "a/");
    }
}
