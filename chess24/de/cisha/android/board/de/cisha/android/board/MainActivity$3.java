/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentView;
import de.cisha.android.board.mainmenu.view.MenuSlider;

class MainActivity
implements Runnable {
    final /* synthetic */ boolean val$addToBackStack;
    final /* synthetic */ boolean val$clearBackStack;
    final /* synthetic */ AbstractContentFragment val$fragment;

    MainActivity(boolean bl, boolean bl2, AbstractContentFragment abstractContentFragment) {
        this.val$addToBackStack = bl;
        this.val$clearBackStack = bl2;
        this.val$fragment = abstractContentFragment;
    }

    @Override
    public void run() {
        Object object = MainActivity.this.getSupportFragmentManager();
        if (!MainActivity.this._isDestroyed) {
            Fragment fragment;
            MainActivity.this.adjustViewsToCurrentFragment();
            if (MainActivity.this._menuSlider != null) {
                if (MainActivity.this._currentContent.showMenu()) {
                    MainActivity.this._menuSlider.setVisibility(0);
                } else {
                    MainActivity.this._menuSlider.setClosed();
                    MainActivity.this._menuSlider.setVisibility(8);
                }
            }
            FragmentTransaction fragmentTransaction = object.beginTransaction();
            if ((this.val$addToBackStack || MainActivity.this._flagIsCloseable) && !this.val$clearBackStack) {
                fragmentTransaction.addToBackStack(this.val$fragment.getClass().getName());
                MainActivity.this._flagIsCloseable = false;
            }
            if ((fragment = object.findFragmentById(2131296260)) != null) {
                fragmentTransaction.remove(fragment);
            }
            fragmentTransaction.commitAllowingStateLoss();
            object = object.beginTransaction();
            object.replace(2131296260, this.val$fragment, this.val$fragment.getClass().getName());
            object.commitAllowingStateLoss();
            if (this.val$clearBackStack) {
                MainActivity.this._flagIsCloseable = true;
            }
        }
    }
}
