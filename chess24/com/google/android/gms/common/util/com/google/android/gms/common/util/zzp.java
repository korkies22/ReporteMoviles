/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.google.android.gms.common.util;

import android.text.TextUtils;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class zzp {
    private static final Pattern zzaGX = Pattern.compile("\\\\.");
    private static final Pattern zzaGY = Pattern.compile("[\\\\\"/\b\f\n\r\t]");

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static String zzdC(String var0) {
        var2_1 = var0;
        if (TextUtils.isEmpty((CharSequence)var0) != false) return var2_1;
        var4_2 = zzp.zzaGY.matcher(var0);
        var2_1 = null;
        block9 : while (var4_2.find()) {
            var3_5 = var2_1;
            if (var2_1 == null) {
                var3_6 = new StringBuffer();
            }
            if ((var1_3 = var4_2.group().charAt(0)) != '\"') {
                if (var1_3 != '/') {
                    if (var1_3 != '\\') {
                        switch (var1_3) {
                            default: {
                                switch (var1_3) {
                                    default: {
                                        var2_1 = var3_4;
                                        continue block9;
                                    }
                                    case '\r': {
                                        var2_1 = "\\\\r";
                                        ** break;
                                    }
                                    case '\f': 
                                }
                                var2_1 = "\\\\f";
                                ** break;
                            }
                            case '\n': {
                                var2_1 = "\\\\n";
                                ** break;
                            }
                            case '\t': {
                                var2_1 = "\\\\t";
                                ** break;
                            }
                            case '\b': 
                        }
                        var2_1 = "\\\\b";
                    } else {
                        var2_1 = "\\\\\\\\";
                    }
                } else {
                    var2_1 = "\\\\/";
                }
            } else {
                var2_1 = "\\\\\\\"";
            }
lbl38: // 8 sources:
            var4_2.appendReplacement((StringBuffer)var3_4, var2_1);
            var2_1 = var3_4;
        }
        if (var2_1 == null) {
            return var0;
        }
        var4_2.appendTail((StringBuffer)var2_1);
        return var2_1.toString();
    }

    /*
     * Loose catch block
     * Enabled aggressive exception aggregation
     */
    public static boolean zzf(Object object, Object object2) {
        if (object == null && object2 == null) {
            return true;
        }
        if (object != null) {
            if (object2 == null) {
                return false;
            }
            if (object instanceof JSONObject && object2 instanceof JSONObject) {
                object = (JSONObject)object;
                object2 = (JSONObject)object2;
                if (object.length() != object2.length()) {
                    return false;
                }
                Iterator iterator = object.keys();
                while (iterator.hasNext()) {
                    String string = (String)iterator.next();
                    if (!object2.has(string)) {
                        return false;
                    }
                    boolean bl = zzp.zzf(object.get(string), object2.get(string));
                    if (bl) continue;
                    return false;
                }
                return true;
            }
            if (object instanceof JSONArray && object2 instanceof JSONArray) {
                object = (JSONArray)object;
                object2 = (JSONArray)object2;
                if (object.length() != object2.length()) {
                    return false;
                }
                for (int i = 0; i < object.length(); ++i) {
                    boolean bl = zzp.zzf(object.get(i), object2.get(i));
                    if (bl) continue;
                    return false;
                }
                return true;
            }
            return object.equals(object2);
        }
        return false;
        catch (JSONException jSONException) {
            return false;
        }
        catch (JSONException jSONException) {
            return false;
        }
    }
}
