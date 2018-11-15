/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.v7.widget.DefaultItemAnimator;
import java.util.ArrayList;

class DefaultItemAnimator
implements Runnable {
    final /* synthetic */ ArrayList val$changes;

    DefaultItemAnimator(ArrayList arrayList) {
        this.val$changes = arrayList;
    }

    @Override
    public void run() {
        for (DefaultItemAnimator.ChangeInfo changeInfo : this.val$changes) {
            DefaultItemAnimator.this.animateChangeImpl(changeInfo);
        }
        this.val$changes.clear();
        DefaultItemAnimator.this.mChangesList.remove(this.val$changes);
    }
}
