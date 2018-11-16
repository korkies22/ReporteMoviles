// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.chess.engine.UCIEngine;

public interface IEngineService
{
    UCIEngine getDefaultSingleEngine();
    
    @Deprecated
    UCIEngine getEngineWithName(final String p0);
}
