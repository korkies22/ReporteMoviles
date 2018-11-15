/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.app;

import java.util.ArrayList;

static final class FragmentTransition
implements Runnable {
    final /* synthetic */ ArrayList val$exitingViews;

    FragmentTransition(ArrayList arrayList) {
        this.val$exitingViews = arrayList;
    }

    @Override
    public void run() {
        android.support.v4.app.FragmentTransition.setViewVisibility(this.val$exitingViews, 4);
    }
}
