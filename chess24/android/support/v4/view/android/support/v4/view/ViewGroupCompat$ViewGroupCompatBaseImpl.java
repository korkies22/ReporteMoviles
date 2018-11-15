/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.v4.view;

import android.graphics.drawable.Drawable;
import android.support.compat.R;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewGroupCompat;
import android.view.View;
import android.view.ViewGroup;

static class ViewGroupCompat.ViewGroupCompatBaseImpl {
    ViewGroupCompat.ViewGroupCompatBaseImpl() {
    }

    public int getLayoutMode(ViewGroup viewGroup) {
        return 0;
    }

    public int getNestedScrollAxes(ViewGroup viewGroup) {
        if (viewGroup instanceof NestedScrollingParent) {
            return ((NestedScrollingParent)viewGroup).getNestedScrollAxes();
        }
        return 0;
    }

    public boolean isTransitionGroup(ViewGroup viewGroup) {
        Boolean bl = (Boolean)viewGroup.getTag(R.id.tag_transition_group);
        if (!(bl != null && bl.booleanValue() || viewGroup.getBackground() != null || ViewCompat.getTransitionName((View)viewGroup) != null)) {
            return false;
        }
        return true;
    }

    public void setLayoutMode(ViewGroup viewGroup, int n) {
    }

    public void setTransitionGroup(ViewGroup viewGroup, boolean bl) {
        viewGroup.setTag(R.id.tag_transition_group, (Object)bl);
    }
}
