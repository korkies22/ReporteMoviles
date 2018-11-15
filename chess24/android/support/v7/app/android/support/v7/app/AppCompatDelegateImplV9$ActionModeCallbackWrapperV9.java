/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewParent
 *  android.view.Window
 *  android.widget.PopupWindow
 */
package android.support.v7.app;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegateImplV9;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.ActionBarContextView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import android.widget.PopupWindow;

class AppCompatDelegateImplV9.ActionModeCallbackWrapperV9
implements ActionMode.Callback {
    private ActionMode.Callback mWrapped;

    public AppCompatDelegateImplV9.ActionModeCallbackWrapperV9(ActionMode.Callback callback) {
        this.mWrapped = callback;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        return this.mWrapped.onActionItemClicked(actionMode, menuItem);
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        return this.mWrapped.onCreateActionMode(actionMode, menu);
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        this.mWrapped.onDestroyActionMode(actionMode);
        if (AppCompatDelegateImplV9.this.mActionModePopup != null) {
            AppCompatDelegateImplV9.this.mWindow.getDecorView().removeCallbacks(AppCompatDelegateImplV9.this.mShowActionModePopup);
        }
        if (AppCompatDelegateImplV9.this.mActionModeView != null) {
            AppCompatDelegateImplV9.this.endOnGoingFadeAnimation();
            AppCompatDelegateImplV9.this.mFadeAnim = ViewCompat.animate((View)AppCompatDelegateImplV9.this.mActionModeView).alpha(0.0f);
            AppCompatDelegateImplV9.this.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter(){

                @Override
                public void onAnimationEnd(View view) {
                    AppCompatDelegateImplV9.this.mActionModeView.setVisibility(8);
                    if (AppCompatDelegateImplV9.this.mActionModePopup != null) {
                        AppCompatDelegateImplV9.this.mActionModePopup.dismiss();
                    } else if (AppCompatDelegateImplV9.this.mActionModeView.getParent() instanceof View) {
                        ViewCompat.requestApplyInsets((View)AppCompatDelegateImplV9.this.mActionModeView.getParent());
                    }
                    AppCompatDelegateImplV9.this.mActionModeView.removeAllViews();
                    AppCompatDelegateImplV9.this.mFadeAnim.setListener(null);
                    AppCompatDelegateImplV9.this.mFadeAnim = null;
                }
            });
        }
        if (AppCompatDelegateImplV9.this.mAppCompatCallback != null) {
            AppCompatDelegateImplV9.this.mAppCompatCallback.onSupportActionModeFinished(AppCompatDelegateImplV9.this.mActionMode);
        }
        AppCompatDelegateImplV9.this.mActionMode = null;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return this.mWrapped.onPrepareActionMode(actionMode, menu);
    }

}
