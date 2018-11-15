/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v4.app;

import android.support.v4.view.ViewCompat;
import android.view.View;
import java.util.ArrayList;
import java.util.Map;

class FragmentTransitionImpl
implements Runnable {
    final /* synthetic */ Map val$nameOverrides;
    final /* synthetic */ ArrayList val$sharedElementsIn;

    FragmentTransitionImpl(ArrayList arrayList, Map map) {
        this.val$sharedElementsIn = arrayList;
        this.val$nameOverrides = map;
    }

    @Override
    public void run() {
        int n = this.val$sharedElementsIn.size();
        for (int i = 0; i < n; ++i) {
            View view = (View)this.val$sharedElementsIn.get(i);
            String string = ViewCompat.getTransitionName(view);
            ViewCompat.setTransitionName(view, (String)this.val$nameOverrides.get(string));
        }
    }
}
