/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.content.res;

import android.support.annotation.NonNull;
import android.support.v4.content.res.FontResourcesParserCompat;

public static final class FontResourcesParserCompat.FontFamilyFilesResourceEntry
implements FontResourcesParserCompat.FamilyResourceEntry {
    @NonNull
    private final FontResourcesParserCompat.FontFileResourceEntry[] mEntries;

    public FontResourcesParserCompat.FontFamilyFilesResourceEntry(@NonNull FontResourcesParserCompat.FontFileResourceEntry[] arrfontFileResourceEntry) {
        this.mEntries = arrfontFileResourceEntry;
    }

    @NonNull
    public FontResourcesParserCompat.FontFileResourceEntry[] getEntries() {
        return this.mEntries;
    }
}
