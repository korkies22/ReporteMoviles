// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model.position;

import de.cisha.chess.model.PieceInformation;
import java.util.List;
import de.cisha.chess.model.FEN;

public final class Position extends AbstractPosition
{
    public Position() {
    }
    
    public Position(final FEN fen) {
        super(fen);
    }
    
    public Position(final AbstractPosition abstractPosition) {
        super(abstractPosition);
    }
    
    public Position(final List<PieceInformation> list) {
        super(list);
    }
    
    @Override
    public MutablePosition getMutablePosition() {
        return new MutablePosition(this);
    }
    
    @Override
    public Position getSafePosition() {
        return this;
    }
}
