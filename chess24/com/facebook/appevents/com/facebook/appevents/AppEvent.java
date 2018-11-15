/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.appevents;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.facebook.FacebookException;
import com.facebook.LoggingBehavior;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

class AppEvent
implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final HashSet<String> validatedIdentifiers = new HashSet();
    private final String checksum;
    private final boolean isImplicit;
    private final JSONObject jsonObject;
    private final String name;

    public AppEvent(String string, String string2, Double d, Bundle bundle, boolean bl, @Nullable UUID uUID) throws JSONException, FacebookException {
        this.jsonObject = AppEvent.getJSONObjectForAppEvent(string, string2, d, bundle, bl, uUID);
        this.isImplicit = bl;
        this.name = string2;
        this.checksum = this.calculateChecksum();
    }

    private AppEvent(String string, boolean bl, String string2) throws JSONException {
        this.jsonObject = new JSONObject(string);
        this.isImplicit = bl;
        this.name = this.jsonObject.optString("_eventName");
        this.checksum = string2;
    }

    private static String bytesToHex(byte[] arrby) {
        StringBuffer stringBuffer = new StringBuffer();
        int n = arrby.length;
        for (int i = 0; i < n; ++i) {
            stringBuffer.append(String.format("%02x", arrby[i]));
        }
        return stringBuffer.toString();
    }

    private String calculateChecksum() {
        if (Build.VERSION.SDK_INT > 19) {
            return AppEvent.md5Checksum(this.jsonObject.toString());
        }
        Object object = new ArrayList();
        Object object2 = this.jsonObject.keys();
        while (object2.hasNext()) {
            object.add(object2.next());
        }
        Collections.sort(object);
        object2 = new StringBuilder();
        object = object.iterator();
        while (object.hasNext()) {
            String string = (String)object.next();
            object2.append(string);
            object2.append(" = ");
            object2.append(this.jsonObject.optString(string));
            object2.append('\n');
        }
        return AppEvent.md5Checksum(object2.toString());
    }

    private static JSONObject getJSONObjectForAppEvent(String object, String string2, Double object2, Bundle bundle, boolean bl, @Nullable UUID uUID) throws FacebookException, JSONException {
        AppEvent.validateIdentifier(string2);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("_eventName", (Object)string2);
        jSONObject.put("_eventName_md5", (Object)AppEvent.md5Checksum(string2));
        jSONObject.put("_logTime", System.currentTimeMillis() / 1000L);
        jSONObject.put("_ui", object);
        if (uUID != null) {
            jSONObject.put("_session_id", (Object)uUID);
        }
        if (object2 != null) {
            jSONObject.put("_valueToSum", object2.doubleValue());
        }
        if (bl) {
            jSONObject.put("_implicitlyLogged", (Object)"1");
        }
        if (bundle != null) {
            for (String string2 : bundle.keySet()) {
                AppEvent.validateIdentifier(string2);
                object2 = bundle.get(string2);
                if (!(object2 instanceof String) && !(object2 instanceof Number)) {
                    throw new FacebookException(String.format("Parameter value '%s' for key '%s' should be a string or a numeric type.", object2, string2));
                }
                jSONObject.put(string2, (Object)object2.toString());
            }
        }
        if (!bl) {
            Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "Created app event '%s'", jSONObject.toString());
        }
        return jSONObject;
    }

    private static String md5Checksum(String object) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            object = object.getBytes("UTF-8");
            messageDigest.update((byte[])object, 0, ((byte[])object).length);
            object = AppEvent.bytesToHex(messageDigest.digest());
            return object;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            Utility.logd("Failed to generate checksum: ", unsupportedEncodingException);
            return "1";
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            Utility.logd("Failed to generate checksum: ", noSuchAlgorithmException);
            return "0";
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static void validateIdentifier(String string) throws FacebookException {
        if (string != null && string.length() != 0 && string.length() <= 40) {
            HashSet<String> hashSet = validatedIdentifiers;
            // MONITORENTER : hashSet
            boolean bl = validatedIdentifiers.contains(string);
            // MONITOREXIT : hashSet
            if (bl) return;
            if (!string.matches("^[0-9a-zA-Z_]+[0-9a-zA-Z _-]*$")) throw new FacebookException(String.format("Skipping event named '%s' due to illegal name - must be under 40 chars and alphanumeric, _, - or space, and not start with a space or hyphen.", string));
            hashSet = validatedIdentifiers;
            // MONITORENTER : hashSet
            validatedIdentifiers.add(string);
            // MONITOREXIT : hashSet
            return;
        }
        String string2 = string;
        if (string != null) throw new FacebookException(String.format(Locale.ROOT, "Identifier '%s' must be less than %d characters", string2, 40));
        string2 = "<None Provided>";
        throw new FacebookException(String.format(Locale.ROOT, "Identifier '%s' must be less than %d characters", string2, 40));
    }

    private Object writeReplace() {
        return new SerializationProxyV2(this.jsonObject.toString(), this.isImplicit, this.checksum);
    }

    public boolean getIsImplicit() {
        return this.isImplicit;
    }

    public JSONObject getJSONObject() {
        return this.jsonObject;
    }

    public String getName() {
        return this.name;
    }

    public boolean isChecksumValid() {
        if (this.checksum == null) {
            return true;
        }
        return this.calculateChecksum().equals(this.checksum);
    }

    public String toString() {
        return String.format("\"%s\", implicit: %b, json: %s", this.jsonObject.optString("_eventName"), this.isImplicit, this.jsonObject.toString());
    }

    static class SerializationProxyV1
    implements Serializable {
        private static final long serialVersionUID = -2488473066578201069L;
        private final boolean isImplicit;
        private final String jsonString;

        private SerializationProxyV1(String string, boolean bl) {
            this.jsonString = string;
            this.isImplicit = bl;
        }

        private Object readResolve() throws JSONException {
            return new AppEvent(this.jsonString, this.isImplicit, null);
        }
    }

    static class SerializationProxyV2
    implements Serializable {
        private static final long serialVersionUID = 20160803001L;
        private final String checksum;
        private final boolean isImplicit;
        private final String jsonString;

        private SerializationProxyV2(String string, boolean bl, String string2) {
            this.jsonString = string;
            this.isImplicit = bl;
            this.checksum = string2;
        }

        private Object readResolve() throws JSONException {
            return new AppEvent(this.jsonString, this.isImplicit, this.checksum);
        }
    }

}
