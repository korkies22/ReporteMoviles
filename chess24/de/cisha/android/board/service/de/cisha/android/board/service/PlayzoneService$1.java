/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import java.util.List;

class PlayzoneService
implements Runnable {
    final /* synthetic */ LoadCommandCallback val$callback;

    PlayzoneService(LoadCommandCallback loadCommandCallback) {
        this.val$callback = loadCommandCallback;
    }

    @Override
    public void run() {
        this.val$callback.loadingSucceded(PlayzoneService.this._cachedTimeControls);
    }
}
