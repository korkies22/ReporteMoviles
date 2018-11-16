// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.service.NodeServerAddress;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;

public interface IBroadcastService
{
    void getBroadcastServerAdress(final LoadCommandCallback<NodeServerAddress> p0);
}
