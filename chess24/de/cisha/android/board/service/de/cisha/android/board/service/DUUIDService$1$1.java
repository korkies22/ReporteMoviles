/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.DUUIDService;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.chess.model.CishaUUID;

class DUUIDService
implements Runnable {
    DUUIDService() {
    }

    @Override
    public void run() {
        1.this.val$callback.loadingSucceded(1.this.this$0._duuid);
    }
}
