/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board;

import de.cisha.android.board.JSONValue;

public class JSONValueHelper {
    public static <T> T enumValueFromJsonStringValue(String string, JSONValue<T>[] arrjSONValue) {
        for (JSONValue<T> jSONValue : arrjSONValue) {
            if (!jSONValue.jsonValue().equals(string)) continue;
            return jSONValue.enumValue();
        }
        return null;
    }
}
