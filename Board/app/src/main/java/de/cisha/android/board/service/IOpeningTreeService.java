// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.android.board.analyze.model.OpeningPositionInformation;
import de.cisha.chess.model.position.Position;

public interface IOpeningTreeService
{
    void closeBook();
    
    OpeningPositionInformation getInformationForPosition(final Position p0);
}
