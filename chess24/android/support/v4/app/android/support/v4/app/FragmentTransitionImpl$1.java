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

class FragmentTransitionImpl
implements Runnable {
    final /* synthetic */ ArrayList val$inNames;
    final /* synthetic */ int val$numSharedElements;
    final /* synthetic */ ArrayList val$outNames;
    final /* synthetic */ ArrayList val$sharedElementsIn;
    final /* synthetic */ ArrayList val$sharedElementsOut;

    FragmentTransitionImpl(int n, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, ArrayList arrayList4) {
        this.val$numSharedElements = n;
        this.val$sharedElementsIn = arrayList;
        this.val$inNames = arrayList2;
        this.val$sharedElementsOut = arrayList3;
        this.val$outNames = arrayList4;
    }

    @Override
    public void run() {
        for (int i = 0; i < this.val$numSharedElements; ++i) {
            ViewCompat.setTransitionName((View)this.val$sharedElementsIn.get(i), (String)this.val$inNames.get(i));
            ViewCompat.setTransitionName((View)this.val$sharedElementsOut.get(i), (String)this.val$outNames.get(i));
        }
    }
}
