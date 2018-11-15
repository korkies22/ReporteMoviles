/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Matrix
 *  android.util.AttributeSet
 *  org.xmlpull.v1.XmlPullParser
 */
package android.support.graphics.drawable;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.support.graphics.drawable.AndroidResources;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;

private static class VectorDrawableCompat.VGroup {
    int mChangingConfigurations;
    final ArrayList<Object> mChildren = new ArrayList();
    private String mGroupName = null;
    private final Matrix mLocalMatrix = new Matrix();
    private float mPivotX = 0.0f;
    private float mPivotY = 0.0f;
    float mRotate = 0.0f;
    private float mScaleX = 1.0f;
    private float mScaleY = 1.0f;
    private final Matrix mStackedMatrix = new Matrix();
    private int[] mThemeAttrs;
    private float mTranslateX = 0.0f;
    private float mTranslateY = 0.0f;

    public VectorDrawableCompat.VGroup() {
    }

    public VectorDrawableCompat.VGroup(VectorDrawableCompat.VGroup object, ArrayMap<String, Object> arrayMap) {
        this.mRotate = object.mRotate;
        this.mPivotX = object.mPivotX;
        this.mPivotY = object.mPivotY;
        this.mScaleX = object.mScaleX;
        this.mScaleY = object.mScaleY;
        this.mTranslateX = object.mTranslateX;
        this.mTranslateY = object.mTranslateY;
        this.mThemeAttrs = object.mThemeAttrs;
        this.mGroupName = object.mGroupName;
        this.mChangingConfigurations = object.mChangingConfigurations;
        if (this.mGroupName != null) {
            arrayMap.put(this.mGroupName, this);
        }
        this.mLocalMatrix.set(object.mLocalMatrix);
        ArrayList<Object> arrayList = object.mChildren;
        for (int i = 0; i < arrayList.size(); ++i) {
            block8 : {
                block7 : {
                    block6 : {
                        object = arrayList.get(i);
                        if (object instanceof VectorDrawableCompat.VGroup) {
                            object = (VectorDrawableCompat.VGroup)object;
                            this.mChildren.add(new VectorDrawableCompat.VGroup((VectorDrawableCompat.VGroup)object, arrayMap));
                            continue;
                        }
                        if (!(object instanceof VectorDrawableCompat.VFullPath)) break block6;
                        object = new VectorDrawableCompat.VFullPath((VectorDrawableCompat.VFullPath)object);
                        break block7;
                    }
                    if (!(object instanceof VectorDrawableCompat.VClipPath)) break block8;
                    object = new VectorDrawableCompat.VClipPath((VectorDrawableCompat.VClipPath)object);
                }
                this.mChildren.add(object);
                if (object.mPathName == null) continue;
                arrayMap.put(object.mPathName, object);
                continue;
            }
            throw new IllegalStateException("Unknown object in the tree!");
        }
    }

    static /* synthetic */ Matrix access$200(VectorDrawableCompat.VGroup vGroup) {
        return vGroup.mStackedMatrix;
    }

    static /* synthetic */ Matrix access$300(VectorDrawableCompat.VGroup vGroup) {
        return vGroup.mLocalMatrix;
    }

    private void updateLocalMatrix() {
        this.mLocalMatrix.reset();
        this.mLocalMatrix.postTranslate(- this.mPivotX, - this.mPivotY);
        this.mLocalMatrix.postScale(this.mScaleX, this.mScaleY);
        this.mLocalMatrix.postRotate(this.mRotate, 0.0f, 0.0f);
        this.mLocalMatrix.postTranslate(this.mTranslateX + this.mPivotX, this.mTranslateY + this.mPivotY);
    }

    private void updateStateFromTypedArray(TypedArray object, XmlPullParser xmlPullParser) {
        this.mThemeAttrs = null;
        this.mRotate = TypedArrayUtils.getNamedFloat(object, xmlPullParser, "rotation", 5, this.mRotate);
        this.mPivotX = object.getFloat(1, this.mPivotX);
        this.mPivotY = object.getFloat(2, this.mPivotY);
        this.mScaleX = TypedArrayUtils.getNamedFloat(object, xmlPullParser, "scaleX", 3, this.mScaleX);
        this.mScaleY = TypedArrayUtils.getNamedFloat(object, xmlPullParser, "scaleY", 4, this.mScaleY);
        this.mTranslateX = TypedArrayUtils.getNamedFloat(object, xmlPullParser, "translateX", 6, this.mTranslateX);
        this.mTranslateY = TypedArrayUtils.getNamedFloat(object, xmlPullParser, "translateY", 7, this.mTranslateY);
        if ((object = object.getString(0)) != null) {
            this.mGroupName = object;
        }
        this.updateLocalMatrix();
    }

    public String getGroupName() {
        return this.mGroupName;
    }

    public Matrix getLocalMatrix() {
        return this.mLocalMatrix;
    }

    public float getPivotX() {
        return this.mPivotX;
    }

    public float getPivotY() {
        return this.mPivotY;
    }

    public float getRotation() {
        return this.mRotate;
    }

    public float getScaleX() {
        return this.mScaleX;
    }

    public float getScaleY() {
        return this.mScaleY;
    }

    public float getTranslateX() {
        return this.mTranslateX;
    }

    public float getTranslateY() {
        return this.mTranslateY;
    }

    public void inflate(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
        resources = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_GROUP);
        this.updateStateFromTypedArray((TypedArray)resources, xmlPullParser);
        resources.recycle();
    }

    public void setPivotX(float f) {
        if (f != this.mPivotX) {
            this.mPivotX = f;
            this.updateLocalMatrix();
        }
    }

    public void setPivotY(float f) {
        if (f != this.mPivotY) {
            this.mPivotY = f;
            this.updateLocalMatrix();
        }
    }

    public void setRotation(float f) {
        if (f != this.mRotate) {
            this.mRotate = f;
            this.updateLocalMatrix();
        }
    }

    public void setScaleX(float f) {
        if (f != this.mScaleX) {
            this.mScaleX = f;
            this.updateLocalMatrix();
        }
    }

    public void setScaleY(float f) {
        if (f != this.mScaleY) {
            this.mScaleY = f;
            this.updateLocalMatrix();
        }
    }

    public void setTranslateX(float f) {
        if (f != this.mTranslateX) {
            this.mTranslateX = f;
            this.updateLocalMatrix();
        }
    }

    public void setTranslateY(float f) {
        if (f != this.mTranslateY) {
            this.mTranslateY = f;
            this.updateLocalMatrix();
        }
    }
}
