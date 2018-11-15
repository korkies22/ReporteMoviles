/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.SystemClock
 *  android.view.MenuItem
 */
package android.support.v7.view.menu;

import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.CascadingMenuPopup;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.MenuItemHoverListener;
import android.view.MenuItem;
import java.util.List;

class CascadingMenuPopup
implements MenuItemHoverListener {
    CascadingMenuPopup() {
    }

    @Override
    public void onItemHoverEnter(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem object) {
        int n;
        CascadingMenuPopup.CascadingMenuInfo cascadingMenuInfo;
        block4 : {
            Handler handler = CascadingMenuPopup.this.mSubMenuHoverHandler;
            cascadingMenuInfo = null;
            handler.removeCallbacksAndMessages(null);
            int n2 = CascadingMenuPopup.this.mShowingMenus.size();
            for (n = 0; n < n2; ++n) {
                if (menuBuilder != CascadingMenuPopup.this.mShowingMenus.get((int)n).menu) {
                    continue;
                }
                break block4;
            }
            n = -1;
        }
        if (n == -1) {
            return;
        }
        if (++n < CascadingMenuPopup.this.mShowingMenus.size()) {
            cascadingMenuInfo = CascadingMenuPopup.this.mShowingMenus.get(n);
        }
        object = new Runnable((MenuItem)object, menuBuilder){
            final /* synthetic */ MenuItem val$item;
            final /* synthetic */ MenuBuilder val$menu;
            {
                this.val$item = menuItem;
                this.val$menu = menuBuilder;
            }

            @Override
            public void run() {
                if (cascadingMenuInfo != null) {
                    CascadingMenuPopup.this.mShouldCloseImmediately = true;
                    cascadingMenuInfo.menu.close(false);
                    CascadingMenuPopup.this.mShouldCloseImmediately = false;
                }
                if (this.val$item.isEnabled() && this.val$item.hasSubMenu()) {
                    this.val$menu.performItemAction(this.val$item, 4);
                }
            }
        };
        long l = SystemClock.uptimeMillis();
        CascadingMenuPopup.this.mSubMenuHoverHandler.postAtTime((Runnable)object, (Object)menuBuilder, l + 200L);
    }

    @Override
    public void onItemHoverExit(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem) {
        CascadingMenuPopup.this.mSubMenuHoverHandler.removeCallbacksAndMessages((Object)menuBuilder);
    }

}
