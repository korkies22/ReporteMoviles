/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Path
 */
package android.support.transition;

import android.graphics.Path;
import android.support.transition.PathMotion;

static final class Transition
extends PathMotion {
    Transition() {
    }

    @Override
    public Path getPath(float f, float f2, float f3, float f4) {
        Path path = new Path();
        path.moveTo(f, f2);
        path.lineTo(f3, f4);
        return path;
    }
}
