/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v4.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManagerImpl;
import android.view.View;

class FragmentManagerImpl
implements Runnable {
    FragmentManagerImpl() {
    }

    @Override
    public void run() {
        if (2.this.val$fragment.getAnimatingAway() != null) {
            2.this.val$fragment.setAnimatingAway(null);
            2.this.this$0.moveToState(2.this.val$fragment, 2.this.val$fragment.getStateAfterAnimating(), 0, 0, false);
        }
    }
}
