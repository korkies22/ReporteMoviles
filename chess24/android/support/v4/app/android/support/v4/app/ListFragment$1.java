/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.ListView
 */
package android.support.v4.app;

import android.view.View;
import android.widget.ListView;

class ListFragment
implements Runnable {
    ListFragment() {
    }

    @Override
    public void run() {
        ListFragment.this.mList.focusableViewAvailable((View)ListFragment.this.mList);
    }
}
