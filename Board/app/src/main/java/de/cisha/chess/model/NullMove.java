// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

import de.cisha.chess.model.position.MutablePosition;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.pieces.special.NullPiece;
import de.cisha.chess.model.position.AbstractPosition;

public class NullMove extends Move
{
    public static final String EAN = "a1a1";
    public static final String SAN = "--";
    private static final long serialVersionUID = -7250395501875819452L;
    
    protected NullMove(final NullMove nullMove) {
        super(nullMove);
    }
    
    public NullMove(final AbstractPosition abstractPosition) {
        super(Square.SQUARE_A1, Square.SQUARE_A1, false, false, false, false, false, null, NullPiece.instanceForColor(abstractPosition.getActiveColor()), null, "", null, false);
        final MutablePosition mutablePosition = new MutablePosition(abstractPosition);
        mutablePosition.doNullMove();
        this.setResultingPosition(mutablePosition.getSafePosition());
    }
    
    @Override
    public NullMove copy() {
        return new NullMove(this);
    }
    
    @Override
    public String getEAN() {
        return "a1a1";
    }
    
    @Override
    public String getFAN() {
        return "--";
    }
    
    @Override
    public String getLAN() {
        return "--";
    }
    
    @Override
    public String getSAN() {
        return "--";
    }
    
    @Override
    public boolean isNullMove() {
        return true;
    }
}
