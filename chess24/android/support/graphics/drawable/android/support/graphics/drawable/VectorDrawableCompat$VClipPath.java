/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  org.xmlpull.v1.XmlPullParser
 */
package android.support.graphics.drawable;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.graphics.drawable.AndroidResources;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.graphics.PathParser;
import android.util.AttributeSet;
import org.xmlpull.v1.XmlPullParser;

private static class VectorDrawableCompat.VClipPath
extends VectorDrawableCompat.VPath {
    public VectorDrawableCompat.VClipPath() {
    }

    public VectorDrawableCompat.VClipPath(VectorDrawableCompat.VClipPath vClipPath) {
        super(vClipPath);
    }

    private void updateStateFromTypedArray(TypedArray object) {
        String string = object.getString(0);
        if (string != null) {
            this.mPathName = string;
        }
        if ((object = object.getString(1)) != null) {
            this.mNodes = PathParser.createNodesFromPathData((String)object);
        }
    }

    public void inflate(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
        if (!TypedArrayUtils.hasAttribute(xmlPullParser, "pathData")) {
            return;
        }
        resources = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_CLIP_PATH);
        this.updateStateFromTypedArray((TypedArray)resources);
        resources.recycle();
    }

    @Override
    public boolean isClipPath() {
        return true;
    }
}
