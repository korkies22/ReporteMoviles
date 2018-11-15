/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile;

import de.cisha.android.board.profile.model.PlayzoneStatisticData;
import de.cisha.android.board.profile.view.YourGamesView;

class YourGamesViewController
implements Runnable {
    final /* synthetic */ PlayzoneStatisticData val$data;

    YourGamesViewController(PlayzoneStatisticData playzoneStatisticData) {
        this.val$data = playzoneStatisticData;
    }

    @Override
    public void run() {
        YourGamesViewController.this._view.setPlayzonGameData(this.val$data);
    }
}
