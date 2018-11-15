/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.content.res;

import android.support.annotation.NonNull;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.support.v4.provider.FontRequest;

public static final class FontResourcesParserCompat.ProviderResourceEntry
implements FontResourcesParserCompat.FamilyResourceEntry {
    @NonNull
    private final FontRequest mRequest;
    private final int mStrategy;
    private final int mTimeoutMs;

    public FontResourcesParserCompat.ProviderResourceEntry(@NonNull FontRequest fontRequest, int n, int n2) {
        this.mRequest = fontRequest;
        this.mStrategy = n;
        this.mTimeoutMs = n2;
    }

    public int getFetchStrategy() {
        return this.mStrategy;
    }

    @NonNull
    public FontRequest getRequest() {
        return this.mRequest;
    }

    public int getTimeout() {
        return this.mTimeoutMs;
    }
}
