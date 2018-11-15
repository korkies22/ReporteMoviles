/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.graphics;

import android.support.v4.graphics.TypefaceCompatBaseImpl;
import android.support.v4.provider.FontsContractCompat;

class TypefaceCompatBaseImpl
implements TypefaceCompatBaseImpl.StyleExtractor<FontsContractCompat.FontInfo> {
    TypefaceCompatBaseImpl() {
    }

    @Override
    public int getWeight(FontsContractCompat.FontInfo fontInfo) {
        return fontInfo.getWeight();
    }

    @Override
    public boolean isItalic(FontsContractCompat.FontInfo fontInfo) {
        return fontInfo.isItalic();
    }
}
