/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.mainmenu.view;

class MenuItemView
implements Runnable {
    final /* synthetic */ boolean val$show;

    MenuItemView(boolean bl) {
        this.val$show = bl;
    }

    @Override
    public void run() {
        de.cisha.android.board.mainmenu.view.MenuItemView menuItemView = MenuItemView.this;
        int n = this.val$show ? 0 : 8;
        menuItemView.setVisibility(n);
    }
}
