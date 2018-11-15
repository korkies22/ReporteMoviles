/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.transition;

import android.support.transition.ChangeTransform;
import android.support.v4.view.ViewCompat;
import android.view.View;

private static class ChangeTransform.Transforms {
    final float mRotationX;
    final float mRotationY;
    final float mRotationZ;
    final float mScaleX;
    final float mScaleY;
    final float mTranslationX;
    final float mTranslationY;
    final float mTranslationZ;

    ChangeTransform.Transforms(View view) {
        this.mTranslationX = view.getTranslationX();
        this.mTranslationY = view.getTranslationY();
        this.mTranslationZ = ViewCompat.getTranslationZ(view);
        this.mScaleX = view.getScaleX();
        this.mScaleY = view.getScaleY();
        this.mRotationX = view.getRotationX();
        this.mRotationY = view.getRotationY();
        this.mRotationZ = view.getRotation();
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof ChangeTransform.Transforms;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (ChangeTransform.Transforms)object;
        bl = bl2;
        if (object.mTranslationX == this.mTranslationX) {
            bl = bl2;
            if (object.mTranslationY == this.mTranslationY) {
                bl = bl2;
                if (object.mTranslationZ == this.mTranslationZ) {
                    bl = bl2;
                    if (object.mScaleX == this.mScaleX) {
                        bl = bl2;
                        if (object.mScaleY == this.mScaleY) {
                            bl = bl2;
                            if (object.mRotationX == this.mRotationX) {
                                bl = bl2;
                                if (object.mRotationY == this.mRotationY) {
                                    bl = bl2;
                                    if (object.mRotationZ == this.mRotationZ) {
                                        bl = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return bl;
    }

    public int hashCode() {
        float f = this.mTranslationX;
        int n = 0;
        int n2 = f != 0.0f ? Float.floatToIntBits(this.mTranslationX) : 0;
        int n3 = this.mTranslationY != 0.0f ? Float.floatToIntBits(this.mTranslationY) : 0;
        int n4 = this.mTranslationZ != 0.0f ? Float.floatToIntBits(this.mTranslationZ) : 0;
        int n5 = this.mScaleX != 0.0f ? Float.floatToIntBits(this.mScaleX) : 0;
        int n6 = this.mScaleY != 0.0f ? Float.floatToIntBits(this.mScaleY) : 0;
        int n7 = this.mRotationX != 0.0f ? Float.floatToIntBits(this.mRotationX) : 0;
        int n8 = this.mRotationY != 0.0f ? Float.floatToIntBits(this.mRotationY) : 0;
        if (this.mRotationZ != 0.0f) {
            n = Float.floatToIntBits(this.mRotationZ);
        }
        return 31 * ((((((n2 * 31 + n3) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) + n;
    }

    public void restore(View view) {
        ChangeTransform.setTransforms(view, this.mTranslationX, this.mTranslationY, this.mTranslationZ, this.mScaleX, this.mScaleY, this.mRotationX, this.mRotationY, this.mRotationZ);
    }
}
