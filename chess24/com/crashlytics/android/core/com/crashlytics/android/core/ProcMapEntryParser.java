/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.ProcMapEntry;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class ProcMapEntryParser {
    private static final Pattern MAP_REGEX = Pattern.compile("\\s*(\\p{XDigit}+)-\\s*(\\p{XDigit}+)\\s+(.{4})\\s+\\p{XDigit}+\\s+.+\\s+\\d+\\s+(.*)");

    private ProcMapEntryParser() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ProcMapEntry parse(String string) {
        Matcher matcher = MAP_REGEX.matcher(string);
        if (!matcher.matches()) {
            return null;
        }
        try {
            long l = Long.valueOf(matcher.group(1), 16);
            return new ProcMapEntry(l, Long.valueOf(matcher.group(2), 16) - l, matcher.group(3), matcher.group(4));
        }
        catch (Exception exception) {}
        Logger logger = Fabric.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not parse map entry: ");
        stringBuilder.append(string);
        logger.d("CrashlyticsCore", stringBuilder.toString());
        return null;
    }
}
