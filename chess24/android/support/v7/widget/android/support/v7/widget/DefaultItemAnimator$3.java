/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;

class DefaultItemAnimator
implements Runnable {
    final /* synthetic */ ArrayList val$additions;

    DefaultItemAnimator(ArrayList arrayList) {
        this.val$additions = arrayList;
    }

    @Override
    public void run() {
        for (RecyclerView.ViewHolder viewHolder : this.val$additions) {
            DefaultItemAnimator.this.animateAddImpl(viewHolder);
        }
        this.val$additions.clear();
        DefaultItemAnimator.this.mAdditionsList.remove(this.val$additions);
    }
}
