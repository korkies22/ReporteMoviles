// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.util;

public class TimeHelper
{
    public static String getDurationString(final long n) {
        final int n2 = (int)(n / 1000L);
        final int n3 = n2 / 60;
        final int n4 = n2 % 60;
        final int n5 = n3 / 60;
        final int n6 = n3 % 60;
        final StringBuilder sb = new StringBuilder();
        String string;
        if (n5 > 0) {
            final StringBuilder sb2 = new StringBuilder();
            sb2.append("");
            sb2.append(n5);
            sb2.append(":");
            string = sb2.toString();
        }
        else {
            string = "";
        }
        sb.append(string);
        String s;
        if (n6 < 10) {
            s = "0";
        }
        else {
            s = "";
        }
        sb.append(s);
        sb.append(n6);
        sb.append(":");
        String s2;
        if (n4 < 10) {
            s2 = "0";
        }
        else {
            s2 = "";
        }
        sb.append(s2);
        sb.append(n4);
        return sb.toString();
    }
}
