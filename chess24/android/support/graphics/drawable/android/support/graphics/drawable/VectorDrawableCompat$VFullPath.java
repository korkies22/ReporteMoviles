/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Paint
 *  android.graphics.Paint$Cap
 *  android.graphics.Paint$Join
 *  android.util.AttributeSet
 *  org.xmlpull.v1.XmlPullParser
 */
package android.support.graphics.drawable;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.graphics.drawable.AndroidResources;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.graphics.PathParser;
import android.util.AttributeSet;
import org.xmlpull.v1.XmlPullParser;

private static class VectorDrawableCompat.VFullPath
extends VectorDrawableCompat.VPath {
    private static final int FILL_TYPE_WINDING = 0;
    float mFillAlpha = 1.0f;
    int mFillColor = 0;
    int mFillRule = 0;
    float mStrokeAlpha = 1.0f;
    int mStrokeColor = 0;
    Paint.Cap mStrokeLineCap = Paint.Cap.BUTT;
    Paint.Join mStrokeLineJoin = Paint.Join.MITER;
    float mStrokeMiterlimit = 4.0f;
    float mStrokeWidth = 0.0f;
    private int[] mThemeAttrs;
    float mTrimPathEnd = 1.0f;
    float mTrimPathOffset = 0.0f;
    float mTrimPathStart = 0.0f;

    public VectorDrawableCompat.VFullPath() {
    }

    public VectorDrawableCompat.VFullPath(VectorDrawableCompat.VFullPath vFullPath) {
        super(vFullPath);
        this.mThemeAttrs = vFullPath.mThemeAttrs;
        this.mStrokeColor = vFullPath.mStrokeColor;
        this.mStrokeWidth = vFullPath.mStrokeWidth;
        this.mStrokeAlpha = vFullPath.mStrokeAlpha;
        this.mFillColor = vFullPath.mFillColor;
        this.mFillRule = vFullPath.mFillRule;
        this.mFillAlpha = vFullPath.mFillAlpha;
        this.mTrimPathStart = vFullPath.mTrimPathStart;
        this.mTrimPathEnd = vFullPath.mTrimPathEnd;
        this.mTrimPathOffset = vFullPath.mTrimPathOffset;
        this.mStrokeLineCap = vFullPath.mStrokeLineCap;
        this.mStrokeLineJoin = vFullPath.mStrokeLineJoin;
        this.mStrokeMiterlimit = vFullPath.mStrokeMiterlimit;
    }

    private Paint.Cap getStrokeLineCap(int n, Paint.Cap cap) {
        switch (n) {
            default: {
                return cap;
            }
            case 2: {
                return Paint.Cap.SQUARE;
            }
            case 1: {
                return Paint.Cap.ROUND;
            }
            case 0: 
        }
        return Paint.Cap.BUTT;
    }

    private Paint.Join getStrokeLineJoin(int n, Paint.Join join) {
        switch (n) {
            default: {
                return join;
            }
            case 2: {
                return Paint.Join.BEVEL;
            }
            case 1: {
                return Paint.Join.ROUND;
            }
            case 0: 
        }
        return Paint.Join.MITER;
    }

    private void updateStateFromTypedArray(TypedArray typedArray, XmlPullParser xmlPullParser) {
        this.mThemeAttrs = null;
        if (!TypedArrayUtils.hasAttribute(xmlPullParser, "pathData")) {
            return;
        }
        String string = typedArray.getString(0);
        if (string != null) {
            this.mPathName = string;
        }
        if ((string = typedArray.getString(2)) != null) {
            this.mNodes = PathParser.createNodesFromPathData(string);
        }
        this.mFillColor = TypedArrayUtils.getNamedColor(typedArray, xmlPullParser, "fillColor", 1, this.mFillColor);
        this.mFillAlpha = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "fillAlpha", 12, this.mFillAlpha);
        this.mStrokeLineCap = this.getStrokeLineCap(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "strokeLineCap", 8, -1), this.mStrokeLineCap);
        this.mStrokeLineJoin = this.getStrokeLineJoin(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "strokeLineJoin", 9, -1), this.mStrokeLineJoin);
        this.mStrokeMiterlimit = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "strokeMiterLimit", 10, this.mStrokeMiterlimit);
        this.mStrokeColor = TypedArrayUtils.getNamedColor(typedArray, xmlPullParser, "strokeColor", 3, this.mStrokeColor);
        this.mStrokeAlpha = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "strokeAlpha", 11, this.mStrokeAlpha);
        this.mStrokeWidth = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "strokeWidth", 4, this.mStrokeWidth);
        this.mTrimPathEnd = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "trimPathEnd", 6, this.mTrimPathEnd);
        this.mTrimPathOffset = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "trimPathOffset", 7, this.mTrimPathOffset);
        this.mTrimPathStart = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "trimPathStart", 5, this.mTrimPathStart);
        this.mFillRule = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "fillType", 13, this.mFillRule);
    }

    @Override
    public void applyTheme(Resources.Theme theme) {
        if (this.mThemeAttrs == null) {
            return;
        }
    }

    @Override
    public boolean canApplyTheme() {
        if (this.mThemeAttrs != null) {
            return true;
        }
        return false;
    }

    float getFillAlpha() {
        return this.mFillAlpha;
    }

    int getFillColor() {
        return this.mFillColor;
    }

    float getStrokeAlpha() {
        return this.mStrokeAlpha;
    }

    int getStrokeColor() {
        return this.mStrokeColor;
    }

    float getStrokeWidth() {
        return this.mStrokeWidth;
    }

    float getTrimPathEnd() {
        return this.mTrimPathEnd;
    }

    float getTrimPathOffset() {
        return this.mTrimPathOffset;
    }

    float getTrimPathStart() {
        return this.mTrimPathStart;
    }

    public void inflate(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
        resources = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_PATH);
        this.updateStateFromTypedArray((TypedArray)resources, xmlPullParser);
        resources.recycle();
    }

    void setFillAlpha(float f) {
        this.mFillAlpha = f;
    }

    void setFillColor(int n) {
        this.mFillColor = n;
    }

    void setStrokeAlpha(float f) {
        this.mStrokeAlpha = f;
    }

    void setStrokeColor(int n) {
        this.mStrokeColor = n;
    }

    void setStrokeWidth(float f) {
        this.mStrokeWidth = f;
    }

    void setTrimPathEnd(float f) {
        this.mTrimPathEnd = f;
    }

    void setTrimPathOffset(float f) {
        this.mTrimPathOffset = f;
    }

    void setTrimPathStart(float f) {
        this.mTrimPathStart = f;
    }
}
