/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.widget.FrameLayout
 */
package android.support.transition;

import android.graphics.Matrix;
import android.support.transition.GhostViewApi14;
import android.support.transition.GhostViewImpl;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

static class GhostViewApi14.Creator
implements GhostViewImpl.Creator {
    GhostViewApi14.Creator() {
    }

    private static FrameLayout findFrameLayout(ViewGroup viewGroup) {
        while (!(viewGroup instanceof FrameLayout)) {
            if ((viewGroup = viewGroup.getParent()) instanceof ViewGroup) continue;
            return null;
        }
        return (FrameLayout)viewGroup;
    }

    @Override
    public GhostViewImpl addGhost(View view, ViewGroup viewGroup, Matrix object) {
        GhostViewApi14 ghostViewApi14 = GhostViewApi14.getGhostView(view);
        object = ghostViewApi14;
        if (ghostViewApi14 == null) {
            if ((viewGroup = GhostViewApi14.Creator.findFrameLayout(viewGroup)) == null) {
                return null;
            }
            object = new GhostViewApi14(view);
            viewGroup.addView((View)object);
        }
        ++object.mReferences;
        return object;
    }

    @Override
    public void removeGhost(View view) {
        if ((view = GhostViewApi14.getGhostView(view)) != null) {
            ViewParent viewParent;
            --view.mReferences;
            if (view.mReferences <= 0 && (viewParent = view.getParent()) instanceof ViewGroup) {
                viewParent = (ViewGroup)viewParent;
                viewParent.endViewTransition(view);
                viewParent.removeView(view);
            }
        }
    }
}
