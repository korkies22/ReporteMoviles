/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.PropertyValuesHolder
 *  android.graphics.Path
 *  android.graphics.PointF
 *  android.util.Property
 */
package android.support.transition;

import android.animation.PropertyValuesHolder;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.RequiresApi;
import android.support.transition.PropertyValuesHolderUtilsImpl;
import android.util.Property;

@RequiresApi(value=21)
class PropertyValuesHolderUtilsApi21
implements PropertyValuesHolderUtilsImpl {
    PropertyValuesHolderUtilsApi21() {
    }

    @Override
    public PropertyValuesHolder ofPointF(Property<?, PointF> property, Path path) {
        return PropertyValuesHolder.ofObject(property, null, (Path)path);
    }
}
