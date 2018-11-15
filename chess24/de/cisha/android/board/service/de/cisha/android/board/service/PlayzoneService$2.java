/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWrapper;
import java.util.List;

class PlayzoneService
extends LoadCommandCallbackWrapper<List<TimeControl>> {
    PlayzoneService(LoadCommandCallback loadCommandCallback) {
        super(loadCommandCallback);
    }

    @Override
    public void loadingSucceded(List<TimeControl> list) {
        synchronized (this) {
            super.loadingSucceded(list);
            PlayzoneService.this._cachedTimeControls = list;
            return;
        }
    }
}
