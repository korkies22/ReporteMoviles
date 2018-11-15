/*
 * Decompiled with CFR 0_134.
 */
package android.arch.lifecycle;

import android.support.annotation.RestrictTo;
import java.util.HashMap;
import java.util.Map;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class MethodCallsLogger {
    private Map<String, Integer> mCalledMethods = new HashMap<String, Integer>();

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public boolean approveCall(String string, int n) {
        Integer n2 = this.mCalledMethods.get(string);
        boolean bl = false;
        int n3 = n2 != null ? n2 : 0;
        if ((n3 & n) != 0) {
            bl = true;
        }
        this.mCalledMethods.put(string, n | n3);
        return bl ^ true;
    }
}
