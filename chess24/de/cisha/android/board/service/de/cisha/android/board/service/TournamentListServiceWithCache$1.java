/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.broadcast.model.TournamentInfo;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import java.util.List;

class TournamentListServiceWithCache
implements Runnable {
    final /* synthetic */ LoadCommandCallback val$callback;
    final /* synthetic */ int val$limit;
    final /* synthetic */ int val$offset;

    TournamentListServiceWithCache(LoadCommandCallback loadCommandCallback, int n, int n2) {
        this.val$callback = loadCommandCallback;
        this.val$limit = n;
        this.val$offset = n2;
    }

    @Override
    public void run() {
        this.val$callback.loadingSucceded(TournamentListServiceWithCache.this.getCachedPart(this.val$limit, this.val$offset));
    }
}
