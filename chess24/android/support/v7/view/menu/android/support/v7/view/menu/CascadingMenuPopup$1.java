/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 */
package android.support.v7.view.menu;

import android.support.v7.view.menu.CascadingMenuPopup;
import android.support.v7.widget.MenuPopupWindow;
import android.view.View;
import android.view.ViewTreeObserver;
import java.util.Iterator;
import java.util.List;

class CascadingMenuPopup
implements ViewTreeObserver.OnGlobalLayoutListener {
    CascadingMenuPopup() {
    }

    public void onGlobalLayout() {
        if (CascadingMenuPopup.this.isShowing() && CascadingMenuPopup.this.mShowingMenus.size() > 0 && !CascadingMenuPopup.this.mShowingMenus.get((int)0).window.isModal()) {
            Object object = CascadingMenuPopup.this.mShownAnchorView;
            if (object != null && object.isShown()) {
                object = CascadingMenuPopup.this.mShowingMenus.iterator();
                while (object.hasNext()) {
                    ((CascadingMenuPopup.CascadingMenuInfo)object.next()).window.show();
                }
            } else {
                CascadingMenuPopup.this.dismiss();
            }
        }
    }
}
