/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board;

import android.arch.lifecycle.ViewModelStoreOwner;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentView;
import de.cisha.android.board.profile.ProfileFragment;

class MainActivity
implements Runnable {
    MainActivity() {
    }

    @Override
    public void run() {
        if (MainActivity.this._currentContent != null) {
            MainActivity.this.getSupportFragmentManager().beginTransaction().remove((Fragment)((Object)MainActivity.this._currentContent)).commit();
        }
        boolean bl = MainActivity.this._flagIsCloseable;
        boolean bl2 = true;
        if (!bl && MainActivity.this.getSupportFragmentManager().getBackStackEntryCount() > 0) {
            MainActivity.this.getSupportFragmentManager().popBackStackImmediate();
            ViewModelStoreOwner viewModelStoreOwner = (AbstractContentFragment)MainActivity.this.getSupportFragmentManager().findFragmentById(2131296260);
            if (viewModelStoreOwner != null) {
                MainActivity.this.setCurrentContentFragment(viewModelStoreOwner);
                MainActivity.this.adjustViewsToCurrentFragment();
            }
            viewModelStoreOwner = MainActivity.this;
            if (MainActivity.this.getSupportFragmentManager().getBackStackEntryCount() != 0) {
                bl2 = false;
            }
            ((de.cisha.android.board.MainActivity)viewModelStoreOwner)._flagIsCloseable = bl2;
            return;
        }
        MainActivity.this.showFragment(new ProfileFragment(), false, false, true);
    }
}
