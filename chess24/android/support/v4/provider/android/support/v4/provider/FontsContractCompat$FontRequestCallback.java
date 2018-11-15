/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Typeface
 */
package android.support.v4.provider;

import android.graphics.Typeface;
import android.support.annotation.RestrictTo;
import android.support.v4.provider.FontsContractCompat;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public static class FontsContractCompat.FontRequestCallback {
    public static final int FAIL_REASON_FONT_LOAD_ERROR = -3;
    public static final int FAIL_REASON_FONT_NOT_FOUND = 1;
    public static final int FAIL_REASON_FONT_UNAVAILABLE = 2;
    public static final int FAIL_REASON_MALFORMED_QUERY = 3;
    public static final int FAIL_REASON_PROVIDER_NOT_FOUND = -1;
    public static final int FAIL_REASON_SECURITY_VIOLATION = -4;
    public static final int FAIL_REASON_WRONG_CERTIFICATES = -2;
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static final int RESULT_OK = 0;

    public void onTypefaceRequestFailed(int n) {
    }

    public void onTypefaceRetrieved(Typeface typeface) {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static @interface FontRequestFailReason {
    }

}
