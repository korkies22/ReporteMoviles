/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.service.NodeServerAddress;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;

public interface IBroadcastService {
    public void getBroadcastServerAdress(LoadCommandCallback<NodeServerAddress> var1);
}
