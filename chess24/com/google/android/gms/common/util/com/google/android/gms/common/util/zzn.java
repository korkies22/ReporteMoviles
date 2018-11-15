/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class zzn {
    private static final Pattern zzaGU = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
    private static final Pattern zzaGV = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");
    private static final Pattern zzaGW = Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");

    private static String decode(String string, String string2) {
        if (string2 == null) {
            string2 = "ISO-8859-1";
        }
        try {
            string = URLDecoder.decode(string, string2);
            return string;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new IllegalArgumentException(unsupportedEncodingException);
        }
    }

    public static Map<String, String> zza(URI object, String string) {
        block4 : {
            Map<String, String> map = Collections.emptyMap();
            Object object2 = object.getRawQuery();
            object = map;
            if (object2 != null) {
                object = map;
                if (object2.length() > 0) {
                    map = new HashMap<String, String>();
                    object2 = new Scanner((String)object2);
                    object2.useDelimiter("&");
                    do {
                        object = map;
                        if (!object2.hasNext()) break block4;
                        String[] arrstring = object2.next().split("=");
                        if (arrstring.length == 0 || arrstring.length > 2) break;
                        String string2 = zzn.decode(arrstring[0], string);
                        object = null;
                        if (arrstring.length == 2) {
                            object = zzn.decode(arrstring[1], string);
                        }
                        map.put(string2, (String)object);
                    } while (true);
                    throw new IllegalArgumentException("bad parameter");
                }
            }
        }
        return object;
    }
}
