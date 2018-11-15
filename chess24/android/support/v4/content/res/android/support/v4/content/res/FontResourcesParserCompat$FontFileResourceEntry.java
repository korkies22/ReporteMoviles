/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.content.res;

import android.support.annotation.NonNull;
import android.support.v4.content.res.FontResourcesParserCompat;

public static final class FontResourcesParserCompat.FontFileResourceEntry {
    @NonNull
    private final String mFileName;
    private boolean mItalic;
    private int mResourceId;
    private int mWeight;

    public FontResourcesParserCompat.FontFileResourceEntry(@NonNull String string, int n, boolean bl, int n2) {
        this.mFileName = string;
        this.mWeight = n;
        this.mItalic = bl;
        this.mResourceId = n2;
    }

    @NonNull
    public String getFileName() {
        return this.mFileName;
    }

    public int getResourceId() {
        return this.mResourceId;
    }

    public int getWeight() {
        return this.mWeight;
    }

    public boolean isItalic() {
        return this.mItalic;
    }
}
