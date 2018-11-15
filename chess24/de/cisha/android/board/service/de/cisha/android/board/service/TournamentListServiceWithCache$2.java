/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.broadcast.model.TournamentInfo;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWrapper;
import java.util.List;

class TournamentListServiceWithCache
extends LoadCommandCallbackWrapper<List<TournamentInfo>> {
    final /* synthetic */ int val$offset;

    TournamentListServiceWithCache(LoadCommandCallback loadCommandCallback, int n) {
        this.val$offset = n;
        super(loadCommandCallback);
    }

    @Override
    protected void succeded(List<TournamentInfo> list) {
        super.succeded(list);
        TournamentListServiceWithCache.this.cachePart(this.val$offset, list);
    }
}
