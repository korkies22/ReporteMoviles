/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board;

import de.cisha.android.board.ILoadingPresenter;

class AbstractContentFragment
implements Runnable {
    final /* synthetic */ ILoadingPresenter val$loadingPresenter;

    AbstractContentFragment(ILoadingPresenter iLoadingPresenter) {
        this.val$loadingPresenter = iLoadingPresenter;
    }

    @Override
    public void run() {
        this.val$loadingPresenter.hideLoadingView();
    }
}
