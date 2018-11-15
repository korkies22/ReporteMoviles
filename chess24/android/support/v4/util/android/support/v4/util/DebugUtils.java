/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.util;

import android.support.annotation.RestrictTo;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class DebugUtils {
    public static void buildShortClassTag(Object object, StringBuilder stringBuilder) {
        String string;
        block6 : {
            String string2;
            block5 : {
                if (object == null) {
                    stringBuilder.append("null");
                    return;
                }
                string2 = object.getClass().getSimpleName();
                if (string2 == null) break block5;
                string = string2;
                if (string2.length() > 0) break block6;
            }
            string2 = object.getClass().getName();
            int n = string2.lastIndexOf(46);
            string = string2;
            if (n > 0) {
                string = string2.substring(n + 1);
            }
        }
        stringBuilder.append(string);
        stringBuilder.append('{');
        stringBuilder.append(Integer.toHexString(System.identityHashCode(object)));
    }
}
