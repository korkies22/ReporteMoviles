/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.engine;

import de.cisha.android.board.playzone.engine.OpponentChooserFragment;
import de.cisha.android.board.playzone.model.PlayzoneGameSessionInfo;
import java.util.List;

class OpponentChooserFragment
implements Runnable {
    final /* synthetic */ List val$result;

    OpponentChooserFragment(List list) {
        this.val$result = list;
    }

    @Override
    public void run() {
        PlayzoneGameSessionInfo playzoneGameSessionInfo = (PlayzoneGameSessionInfo)this.val$result.get(0);
        1.this.this$0.resumeGame(playzoneGameSessionInfo);
    }
}
