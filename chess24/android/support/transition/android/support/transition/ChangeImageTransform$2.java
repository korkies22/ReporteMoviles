/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.util.Property
 *  android.widget.ImageView
 */
package android.support.transition;

import android.graphics.Matrix;
import android.support.transition.ImageViewUtils;
import android.util.Property;
import android.widget.ImageView;

static final class ChangeImageTransform
extends Property<ImageView, Matrix> {
    ChangeImageTransform(Class class_, String string) {
        super(class_, string);
    }

    public Matrix get(ImageView imageView) {
        return null;
    }

    public void set(ImageView imageView, Matrix matrix) {
        ImageViewUtils.animateTransform(imageView, matrix);
    }
}
