/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.facebook.internal;

import com.facebook.FacebookRequestError;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

public final class FacebookRequestErrorClassification {
    public static final int EC_APP_TOO_MANY_CALLS = 4;
    public static final int EC_INVALID_SESSION = 102;
    public static final int EC_INVALID_TOKEN = 190;
    public static final int EC_RATE = 9;
    public static final int EC_SERVICE_UNAVAILABLE = 2;
    public static final int EC_TOO_MANY_USER_ACTION_CALLS = 341;
    public static final int EC_USER_TOO_MANY_CALLS = 17;
    public static final String KEY_LOGIN_RECOVERABLE = "login_recoverable";
    public static final String KEY_NAME = "name";
    public static final String KEY_OTHER = "other";
    public static final String KEY_RECOVERY_MESSAGE = "recovery_message";
    public static final String KEY_TRANSIENT = "transient";
    private static FacebookRequestErrorClassification defaultInstance;
    private final Map<Integer, Set<Integer>> loginRecoverableErrors;
    private final String loginRecoverableRecoveryMessage;
    private final Map<Integer, Set<Integer>> otherErrors;
    private final String otherRecoveryMessage;
    private final Map<Integer, Set<Integer>> transientErrors;
    private final String transientRecoveryMessage;

    FacebookRequestErrorClassification(Map<Integer, Set<Integer>> map, Map<Integer, Set<Integer>> map2, Map<Integer, Set<Integer>> map3, String string, String string2, String string3) {
        this.otherErrors = map;
        this.transientErrors = map2;
        this.loginRecoverableErrors = map3;
        this.otherRecoveryMessage = string;
        this.transientRecoveryMessage = string2;
        this.loginRecoverableRecoveryMessage = string3;
    }

    public static FacebookRequestErrorClassification createFromJSON(JSONArray jSONArray) {
        String string;
        String string2;
        String string3;
        if (jSONArray == null) {
            return null;
        }
        String string4 = string3 = null;
        Object object = string4;
        String string5 = string2 = (string = object);
        for (int i = 0; i < jSONArray.length(); ++i) {
            String string6;
            String string7;
            Object object2;
            Object object3;
            JSONObject jSONObject = jSONArray.optJSONObject(i);
            if (jSONObject == null) {
                object3 = string3;
                object2 = string4;
                string7 = string;
                string6 = string2;
            } else {
                String string8 = jSONObject.optString(KEY_NAME);
                if (string8 == null) {
                    object3 = string3;
                    object2 = string4;
                    string7 = string;
                    string6 = string2;
                } else if (string8.equalsIgnoreCase(KEY_OTHER)) {
                    string7 = jSONObject.optString(KEY_RECOVERY_MESSAGE, null);
                    object3 = FacebookRequestErrorClassification.parseJSONDefinition(jSONObject);
                    object2 = string4;
                    string6 = string2;
                } else if (string8.equalsIgnoreCase(KEY_TRANSIENT)) {
                    string6 = jSONObject.optString(KEY_RECOVERY_MESSAGE, null);
                    object2 = FacebookRequestErrorClassification.parseJSONDefinition(jSONObject);
                    object3 = string3;
                    string7 = string;
                } else {
                    object3 = string3;
                    object2 = string4;
                    string7 = string;
                    string6 = string2;
                    if (string8.equalsIgnoreCase(KEY_LOGIN_RECOVERABLE)) {
                        string5 = jSONObject.optString(KEY_RECOVERY_MESSAGE, null);
                        object = FacebookRequestErrorClassification.parseJSONDefinition(jSONObject);
                        string6 = string2;
                        string7 = string;
                        object2 = string4;
                        object3 = string3;
                    }
                }
            }
            string3 = object3;
            string4 = object2;
            string = string7;
            string2 = string6;
        }
        return new FacebookRequestErrorClassification((Map<Integer, Set<Integer>>)((Object)string3), (Map<Integer, Set<Integer>>)((Object)string4), (Map<Integer, Set<Integer>>)object, string, string2, string5);
    }

    public static FacebookRequestErrorClassification getDefaultErrorClassification() {
        synchronized (FacebookRequestErrorClassification.class) {
            if (defaultInstance == null) {
                defaultInstance = FacebookRequestErrorClassification.getDefaultErrorClassificationImpl();
            }
            FacebookRequestErrorClassification facebookRequestErrorClassification = defaultInstance;
            return facebookRequestErrorClassification;
        }
    }

    private static FacebookRequestErrorClassification getDefaultErrorClassificationImpl() {
        return new FacebookRequestErrorClassification(null, (Map<Integer, Set<Integer>>)new HashMap<Integer, Set<Integer>>(){
            {
                this.put(2, null);
                this.put(4, null);
                this.put(9, null);
                this.put(17, null);
                this.put(341, null);
            }
        }, (Map<Integer, Set<Integer>>)new HashMap<Integer, Set<Integer>>(){
            {
                this.put(102, null);
                this.put(190, null);
            }
        }, null, null, null);
    }

    private static Map<Integer, Set<Integer>> parseJSONDefinition(JSONObject object) {
        JSONArray jSONArray = object.optJSONArray("items");
        if (jSONArray.length() == 0) {
            return null;
        }
        HashMap<Integer, Set<Integer>> hashMap = new HashMap<Integer, Set<Integer>>();
        for (int i = 0; i < jSONArray.length(); ++i) {
            int n;
            object = jSONArray.optJSONObject(i);
            if (object == null || (n = object.optInt("code")) == 0) continue;
            JSONArray jSONArray2 = object.optJSONArray("subcodes");
            if (jSONArray2 != null && jSONArray2.length() > 0) {
                HashSet<Integer> hashSet = new HashSet<Integer>();
                int n2 = 0;
                do {
                    object = hashSet;
                    if (n2 < jSONArray2.length()) {
                        int n3 = jSONArray2.optInt(n2);
                        if (n3 != 0) {
                            hashSet.add(n3);
                        }
                        ++n2;
                        continue;
                    }
                    break;
                } while (true);
            } else {
                object = null;
            }
            hashMap.put(n, (Set<Integer>)object);
        }
        return hashMap;
    }

    public FacebookRequestError.Category classify(int n, int n2, boolean bl) {
        Set<Integer> set;
        if (bl) {
            return FacebookRequestError.Category.TRANSIENT;
        }
        if (this.otherErrors != null && this.otherErrors.containsKey(n) && ((set = this.otherErrors.get(n)) == null || set.contains(n2))) {
            return FacebookRequestError.Category.OTHER;
        }
        if (this.loginRecoverableErrors != null && this.loginRecoverableErrors.containsKey(n) && ((set = this.loginRecoverableErrors.get(n)) == null || set.contains(n2))) {
            return FacebookRequestError.Category.LOGIN_RECOVERABLE;
        }
        if (this.transientErrors != null && this.transientErrors.containsKey(n) && ((set = this.transientErrors.get(n)) == null || set.contains(n2))) {
            return FacebookRequestError.Category.TRANSIENT;
        }
        return FacebookRequestError.Category.OTHER;
    }

    public Map<Integer, Set<Integer>> getLoginRecoverableErrors() {
        return this.loginRecoverableErrors;
    }

    public Map<Integer, Set<Integer>> getOtherErrors() {
        return this.otherErrors;
    }

    public String getRecoveryMessage(FacebookRequestError.Category category) {
        switch (.$SwitchMap$com$facebook$FacebookRequestError$Category[category.ordinal()]) {
            default: {
                return null;
            }
            case 3: {
                return this.transientRecoveryMessage;
            }
            case 2: {
                return this.loginRecoverableRecoveryMessage;
            }
            case 1: 
        }
        return this.otherRecoveryMessage;
    }

    public Map<Integer, Set<Integer>> getTransientErrors() {
        return this.transientErrors;
    }

}
