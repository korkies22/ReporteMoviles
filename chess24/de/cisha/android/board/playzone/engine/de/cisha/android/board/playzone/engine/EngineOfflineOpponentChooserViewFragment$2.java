/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.engine;

import de.cisha.android.board.action.BoardAction;
import de.cisha.android.board.playzone.engine.view.EngineOfflineOpponentChooserView;
import de.cisha.android.board.user.User;

class EngineOfflineOpponentChooserViewFragment
implements Runnable {
    final /* synthetic */ User val$user;

    EngineOfflineOpponentChooserViewFragment(User user) {
        this.val$user = user;
    }

    @Override
    public void run() {
        EngineOfflineOpponentChooserViewFragment.this._view.setPremiumFeatureWallEnabled(this.val$user.isPremium() ^ true, EngineOfflineOpponentChooserViewFragment.this._premiumButtonAction);
    }
}
