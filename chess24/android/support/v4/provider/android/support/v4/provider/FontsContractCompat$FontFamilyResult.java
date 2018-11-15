/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.provider;

import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.provider.FontsContractCompat;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public static class FontsContractCompat.FontFamilyResult {
    public static final int STATUS_OK = 0;
    public static final int STATUS_UNEXPECTED_DATA_PROVIDED = 2;
    public static final int STATUS_WRONG_CERTIFICATES = 1;
    private final FontsContractCompat.FontInfo[] mFonts;
    private final int mStatusCode;

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public FontsContractCompat.FontFamilyResult(int n, @Nullable FontsContractCompat.FontInfo[] arrfontInfo) {
        this.mStatusCode = n;
        this.mFonts = arrfontInfo;
    }

    public FontsContractCompat.FontInfo[] getFonts() {
        return this.mFonts;
    }

    public int getStatusCode() {
        return this.mStatusCode;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    static @interface FontResultStatus {
    }

}
