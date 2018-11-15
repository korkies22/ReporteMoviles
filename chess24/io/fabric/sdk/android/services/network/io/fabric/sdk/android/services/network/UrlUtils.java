/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package io.fabric.sdk.android.services.network;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.TreeMap;

public final class UrlUtils {
    public static final String UTF8 = "UTF8";

    private UrlUtils() {
    }

    public static TreeMap<String, String> getQueryParams(String arrstring, boolean bl) {
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        if (arrstring == null) {
            return treeMap;
        }
        arrstring = arrstring.split("&");
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            String[] arrstring2 = arrstring[i].split("=");
            if (arrstring2.length == 2) {
                if (bl) {
                    treeMap.put(UrlUtils.urlDecode(arrstring2[0]), UrlUtils.urlDecode(arrstring2[1]));
                    continue;
                }
                treeMap.put(arrstring2[0], arrstring2[1]);
                continue;
            }
            if (TextUtils.isEmpty((CharSequence)arrstring2[0])) continue;
            if (bl) {
                treeMap.put(UrlUtils.urlDecode(arrstring2[0]), "");
                continue;
            }
            treeMap.put(arrstring2[0], "");
        }
        return treeMap;
    }

    public static TreeMap<String, String> getQueryParams(URI uRI, boolean bl) {
        return UrlUtils.getQueryParams(uRI.getRawQuery(), bl);
    }

    public static String percentEncode(String string) {
        if (string == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        string = UrlUtils.urlEncode(string);
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            int n2;
            char c = string.charAt(i);
            if (c == '*') {
                stringBuilder.append("%2A");
                continue;
            }
            if (c == '+') {
                stringBuilder.append("%20");
                continue;
            }
            if (c == '%' && (n2 = i + 2) < n && string.charAt(i + 1) == '7' && string.charAt(n2) == 'E') {
                stringBuilder.append('~');
                i = n2;
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static String urlDecode(String string) {
        if (string == null) {
            return "";
        }
        try {
            string = URLDecoder.decode(string, UTF8);
            return string;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException(unsupportedEncodingException.getMessage(), unsupportedEncodingException);
        }
    }

    public static String urlEncode(String string) {
        if (string == null) {
            return "";
        }
        try {
            string = URLEncoder.encode(string, UTF8);
            return string;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException(unsupportedEncodingException.getMessage(), unsupportedEncodingException);
        }
    }
}
