/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package de.cisha.android.board.view;

import android.support.v4.app.FragmentManager;
import android.view.View;
import de.cisha.android.board.RookiePromotionFragment;
import de.cisha.android.board.view.BoardView;
import java.lang.ref.WeakReference;

class BoardViewFactory
implements BoardView.PromotionPresenter {
    private RookiePromotionFragment _fragment;
    private WeakReference<FragmentManager> _fragmentManagerRef;
    final /* synthetic */ FragmentManager val$fragmentManager;

    BoardViewFactory(FragmentManager fragmentManager) {
        this.val$fragmentManager = fragmentManager;
        this._fragmentManagerRef = new WeakReference<FragmentManager>(this.val$fragmentManager);
    }

    @Override
    public void dismissDialog() {
        if (this._fragment != null) {
            this._fragment.dismiss();
        }
    }

    @Override
    public boolean isPromotionShown() {
        if (this._fragment != null && this._fragment.isVisible()) {
            return true;
        }
        return false;
    }

    @Override
    public void showPromotionDialog(View view) {
        FragmentManager fragmentManager = (FragmentManager)this._fragmentManagerRef.get();
        if (fragmentManager != null) {
            this._fragment = new RookiePromotionFragment();
            this._fragment.setPieceChooserView(view);
            this._fragment.show(fragmentManager, "");
        }
    }
}
