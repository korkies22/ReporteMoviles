/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.accessibility.AccessibilityEvent
 */
package android.support.v4.view;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.compat.R;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

public final class ViewGroupCompat {
    static final ViewGroupCompatBaseImpl IMPL = Build.VERSION.SDK_INT >= 21 ? new ViewGroupCompatApi21Impl() : (Build.VERSION.SDK_INT >= 18 ? new ViewGroupCompatApi18Impl() : new ViewGroupCompatBaseImpl());
    public static final int LAYOUT_MODE_CLIP_BOUNDS = 0;
    public static final int LAYOUT_MODE_OPTICAL_BOUNDS = 1;

    private ViewGroupCompat() {
    }

    public static int getLayoutMode(ViewGroup viewGroup) {
        return IMPL.getLayoutMode(viewGroup);
    }

    public static int getNestedScrollAxes(@NonNull ViewGroup viewGroup) {
        return IMPL.getNestedScrollAxes(viewGroup);
    }

    public static boolean isTransitionGroup(ViewGroup viewGroup) {
        return IMPL.isTransitionGroup(viewGroup);
    }

    @Deprecated
    public static boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        return viewGroup.onRequestSendAccessibilityEvent(view, accessibilityEvent);
    }

    public static void setLayoutMode(ViewGroup viewGroup, int n) {
        IMPL.setLayoutMode(viewGroup, n);
    }

    @Deprecated
    public static void setMotionEventSplittingEnabled(ViewGroup viewGroup, boolean bl) {
        viewGroup.setMotionEventSplittingEnabled(bl);
    }

    public static void setTransitionGroup(ViewGroup viewGroup, boolean bl) {
        IMPL.setTransitionGroup(viewGroup, bl);
    }

    @RequiresApi(value=18)
    static class ViewGroupCompatApi18Impl
    extends ViewGroupCompatBaseImpl {
        ViewGroupCompatApi18Impl() {
        }

        @Override
        public int getLayoutMode(ViewGroup viewGroup) {
            return viewGroup.getLayoutMode();
        }

        @Override
        public void setLayoutMode(ViewGroup viewGroup, int n) {
            viewGroup.setLayoutMode(n);
        }
    }

    @RequiresApi(value=21)
    static class ViewGroupCompatApi21Impl
    extends ViewGroupCompatApi18Impl {
        ViewGroupCompatApi21Impl() {
        }

        @Override
        public int getNestedScrollAxes(ViewGroup viewGroup) {
            return viewGroup.getNestedScrollAxes();
        }

        @Override
        public boolean isTransitionGroup(ViewGroup viewGroup) {
            return viewGroup.isTransitionGroup();
        }

        @Override
        public void setTransitionGroup(ViewGroup viewGroup, boolean bl) {
            viewGroup.setTransitionGroup(bl);
        }
    }

    static class ViewGroupCompatBaseImpl {
        ViewGroupCompatBaseImpl() {
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

}
