/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.util;

public class TimeHelper {
    public static String getDurationString(long l) {
        CharSequence charSequence;
        int n = (int)(l / 1000L);
        int n2 = n / 60;
        n %= 60;
        int n3 = n2 / 60;
        n2 %= 60;
        StringBuilder stringBuilder = new StringBuilder();
        if (n3 > 0) {
            charSequence = new StringBuilder();
            charSequence.append("");
            charSequence.append(n3);
            charSequence.append(":");
            charSequence = charSequence.toString();
        } else {
            charSequence = "";
        }
        stringBuilder.append((String)charSequence);
        charSequence = n2 < 10 ? "0" : "";
        stringBuilder.append((String)charSequence);
        stringBuilder.append(n2);
        stringBuilder.append(":");
        charSequence = n < 10 ? "0" : "";
        stringBuilder.append((String)charSequence);
        stringBuilder.append(n);
        return stringBuilder.toString();
    }
}
