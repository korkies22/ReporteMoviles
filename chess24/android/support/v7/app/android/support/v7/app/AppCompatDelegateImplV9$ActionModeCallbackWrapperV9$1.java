/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewParent
 *  android.widget.PopupWindow
 */
package android.support.v7.app;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v7.app.AppCompatDelegateImplV9;
import android.support.v7.widget.ActionBarContextView;
import android.view.View;
import android.view.ViewParent;
import android.widget.PopupWindow;

class AppCompatDelegateImplV9.ActionModeCallbackWrapperV9
extends ViewPropertyAnimatorListenerAdapter {
    AppCompatDelegateImplV9.ActionModeCallbackWrapperV9() {
    }

    @Override
    public void onAnimationEnd(View view) {
        ActionModeCallbackWrapperV9.this.this$0.mActionModeView.setVisibility(8);
        if (ActionModeCallbackWrapperV9.this.this$0.mActionModePopup != null) {
            ActionModeCallbackWrapperV9.this.this$0.mActionModePopup.dismiss();
        } else if (ActionModeCallbackWrapperV9.this.this$0.mActionModeView.getParent() instanceof View) {
            ViewCompat.requestApplyInsets((View)ActionModeCallbackWrapperV9.this.this$0.mActionModeView.getParent());
        }
        ActionModeCallbackWrapperV9.this.this$0.mActionModeView.removeAllViews();
        ActionModeCallbackWrapperV9.this.this$0.mFadeAnim.setListener(null);
        ActionModeCallbackWrapperV9.this.this$0.mFadeAnim = null;
    }
}
