/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.transition;

import android.graphics.Matrix;
import android.support.transition.GhostViewApi21;
import android.support.transition.GhostViewImpl;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

static class GhostViewApi21.Creator
implements GhostViewImpl.Creator {
    GhostViewApi21.Creator() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public GhostViewImpl addGhost(View object, ViewGroup viewGroup, Matrix matrix) {
        GhostViewApi21.fetchAddGhostMethod();
        if (sAddGhostMethod == null) return null;
        try {
            return new GhostViewApi21((View)sAddGhostMethod.invoke(null, new Object[]{object, viewGroup, matrix}), null);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException(invocationTargetException.getCause());
        }
        catch (IllegalAccessException illegalAccessException) {
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void removeGhost(View view) {
        GhostViewApi21.fetchRemoveGhostMethod();
        if (sRemoveGhostMethod == null) return;
        try {
            sRemoveGhostMethod.invoke(null, new Object[]{view});
            return;
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException(invocationTargetException.getCause());
        }
        catch (IllegalAccessException illegalAccessException) {
            return;
        }
    }
}
