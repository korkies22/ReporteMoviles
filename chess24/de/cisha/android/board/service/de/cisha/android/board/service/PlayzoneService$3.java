/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.playzone.model.OnlineEngineOpponent;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWrapper;
import java.util.List;

class PlayzoneService
extends LoadCommandCallbackWrapper<List<OnlineEngineOpponent>> {
    PlayzoneService(LoadCommandCallback loadCommandCallback) {
        super(loadCommandCallback);
    }

    @Override
    protected void succeded(List<OnlineEngineOpponent> list) {
        super.succeded(list);
        PlayzoneService.this.updateDbWithOpponents(list);
    }
}
