/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.graphics;

import android.support.v4.content.res.FontResourcesParserCompat;
import android.support.v4.graphics.TypefaceCompatBaseImpl;

class TypefaceCompatBaseImpl
implements TypefaceCompatBaseImpl.StyleExtractor<FontResourcesParserCompat.FontFileResourceEntry> {
    TypefaceCompatBaseImpl() {
    }

    @Override
    public int getWeight(FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry) {
        return fontFileResourceEntry.getWeight();
    }

    @Override
    public boolean isItalic(FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry) {
        return fontFileResourceEntry.isItalic();
    }
}
