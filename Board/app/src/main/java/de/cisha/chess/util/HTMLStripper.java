// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.util;

import java.util.Arrays;
import java.util.List;

public final class HTMLStripper
{
    public static String strip(final String s) {
        String replaceAll = "";
        if (s != null) {
            replaceAll = replaceAll;
            if (s.length() > 0) {
                replaceAll = s.replaceAll(String.format("<%s[^>]+>", tagsToRemove().toString()), "");
            }
        }
        return replaceAll;
    }
    
    private static List<String> tagsToRemove() {
        return Arrays.asList("a", "a/");
    }
}
