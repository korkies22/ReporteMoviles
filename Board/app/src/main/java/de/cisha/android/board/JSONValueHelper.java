// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board;

public class JSONValueHelper
{
    public static <T> T enumValueFromJsonStringValue(final String s, final JSONValue<T>[] array) {
        for (int i = 0; i < array.length; ++i) {
            final JSONValue<T> jsonValue = array[i];
            if (jsonValue.jsonValue().equals(s)) {
                return jsonValue.enumValue();
            }
        }
        return null;
    }
}
