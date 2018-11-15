/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.engine;

import de.cisha.android.board.playzone.AbstractPlayzoneFragment;
import de.cisha.android.board.playzone.engine.PlayzoneEngineFragment;
import de.cisha.android.board.playzone.engine.model.EngineGameAdapter;
import de.cisha.android.board.playzone.model.AbstractGameModel;

class PlayzoneEngineFragment.1EngineRunnable
implements Runnable {
    private EngineGameAdapter.Builder builder;

    public PlayzoneEngineFragment.1EngineRunnable(EngineGameAdapter.Builder builder) {
        this.builder = builder;
    }

    @Override
    public void run() {
        EngineGameAdapter engineGameAdapter = this.builder.create();
        PlayzoneEngineFragment.this.showMenusForPlayzoneState(AbstractPlayzoneFragment.PlayzoneState.PLAYING);
        PlayzoneEngineFragment.this.hideDialogWaiting();
        PlayzoneEngineFragment.this.gameChosen(engineGameAdapter);
    }
}
