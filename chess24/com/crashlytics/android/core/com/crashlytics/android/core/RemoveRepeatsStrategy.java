/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.StackTraceTrimmingStrategy;
import java.util.HashMap;

class RemoveRepeatsStrategy
implements StackTraceTrimmingStrategy {
    private final int maxRepetitions;

    public RemoveRepeatsStrategy() {
        this(1);
    }

    public RemoveRepeatsStrategy(int n) {
        this.maxRepetitions = n;
    }

    private static boolean isRepeatingSequence(StackTraceElement[] arrstackTraceElement, int n, int n2) {
        int n3 = n2 - n;
        if (n2 + n3 > arrstackTraceElement.length) {
            return false;
        }
        for (int i = 0; i < n3; ++i) {
            if (arrstackTraceElement[n + i].equals(arrstackTraceElement[n2 + i])) continue;
            return false;
        }
        return true;
    }

    private static StackTraceElement[] trimRepeats(StackTraceElement[] arrstackTraceElement, int n) {
        int n2;
        HashMap<StackTraceElement, Integer> hashMap = new HashMap<StackTraceElement, Integer>();
        StackTraceElement[] arrstackTraceElement2 = new StackTraceElement[arrstackTraceElement.length];
        int n3 = n2 = 0;
        int n4 = 1;
        while (n2 < arrstackTraceElement.length) {
            int n5;
            StackTraceElement stackTraceElement = arrstackTraceElement[n2];
            Integer n6 = (Integer)hashMap.get(stackTraceElement);
            if (n6 != null && RemoveRepeatsStrategy.isRepeatingSequence(arrstackTraceElement, n6, n2)) {
                int n7 = n2 - n6;
                int n8 = n3;
                n5 = n4;
                if (n4 < n) {
                    System.arraycopy(arrstackTraceElement, n2, arrstackTraceElement2, n3, n7);
                    n8 = n3 + n7;
                    n5 = n4 + 1;
                }
                n7 = n7 - 1 + n2;
                n3 = n8;
                n4 = n5;
                n5 = n7;
            } else {
                arrstackTraceElement2[n3] = arrstackTraceElement[n2];
                ++n3;
                n4 = 1;
                n5 = n2;
            }
            hashMap.put(stackTraceElement, n2);
            n2 = n5 + 1;
        }
        arrstackTraceElement = new StackTraceElement[n3];
        System.arraycopy(arrstackTraceElement2, 0, arrstackTraceElement, 0, arrstackTraceElement.length);
        return arrstackTraceElement;
    }

    @Override
    public StackTraceElement[] getTrimmedStackTrace(StackTraceElement[] arrstackTraceElement) {
        StackTraceElement[] arrstackTraceElement2 = RemoveRepeatsStrategy.trimRepeats(arrstackTraceElement, this.maxRepetitions);
        if (arrstackTraceElement2.length < arrstackTraceElement.length) {
            return arrstackTraceElement2;
        }
        return arrstackTraceElement;
    }
}
