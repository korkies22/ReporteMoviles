/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package android.support.v4.provider;

import android.net.Uri;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.v4.provider.FontsContractCompat;
import android.support.v4.util.Preconditions;

public static class FontsContractCompat.FontInfo {
    private final boolean mItalic;
    private final int mResultCode;
    private final int mTtcIndex;
    private final Uri mUri;
    private final int mWeight;

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public FontsContractCompat.FontInfo(@NonNull Uri uri, @IntRange(from=0L) int n, @IntRange(from=1L, to=1000L) int n2, boolean bl, int n3) {
        this.mUri = Preconditions.checkNotNull(uri);
        this.mTtcIndex = n;
        this.mWeight = n2;
        this.mItalic = bl;
        this.mResultCode = n3;
    }

    public int getResultCode() {
        return this.mResultCode;
    }

    @IntRange(from=0L)
    public int getTtcIndex() {
        return this.mTtcIndex;
    }

    @NonNull
    public Uri getUri() {
        return this.mUri;
    }

    @IntRange(from=1L, to=1000L)
    public int getWeight() {
        return this.mWeight;
    }

    public boolean isItalic() {
        return this.mItalic;
    }
}
