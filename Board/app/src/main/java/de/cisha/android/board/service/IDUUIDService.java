// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.chess.model.CishaUUID;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;

public interface IDUUIDService
{
    void getDuuid(final LoadCommandCallback<CishaUUID> p0);
    
    void renewDuuid();
}
