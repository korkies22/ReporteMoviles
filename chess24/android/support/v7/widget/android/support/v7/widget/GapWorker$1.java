/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.v7.widget.GapWorker;
import android.support.v7.widget.RecyclerView;
import java.util.Comparator;

static final class GapWorker
implements Comparator<GapWorker.Task> {
    GapWorker() {
    }

    @Override
    public int compare(GapWorker.Task task, GapWorker.Task task2) {
        RecyclerView recyclerView = task.view;
        int n = 1;
        int n2 = recyclerView == null ? 1 : 0;
        int n3 = task2.view == null;
        if (n2 != n3) {
            if (task.view == null) {
                return 1;
            }
            return -1;
        }
        if (task.immediate != task2.immediate) {
            n2 = n;
            if (task.immediate) {
                n2 = -1;
            }
            return n2;
        }
        n2 = task2.viewVelocity - task.viewVelocity;
        if (n2 != 0) {
            return n2;
        }
        n2 = task.distanceToItem - task2.distanceToItem;
        if (n2 != 0) {
            return n2;
        }
        return 0;
    }
}
