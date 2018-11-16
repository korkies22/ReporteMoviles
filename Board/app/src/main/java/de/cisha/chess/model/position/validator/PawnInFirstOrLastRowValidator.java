// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model.position.validator;

import java.util.Iterator;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.PieceInformation;
import de.cisha.chess.model.pieces.Pawn;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.R;
import android.content.Context;

public class PawnInFirstOrLastRowValidator implements IPositionRuleValidator
{
    @Override
    public String getErrorMessage(final Context context) {
        return context.getResources().getString(R.string.position_validation_rule_pawns_in_impossible_positions);
    }
    
    @Override
    public boolean verify(final Position position) {
        final Pawn instanceForColor = Pawn.instanceForColor(true);
        for (final PieceInformation pieceInformation : position.getAllPieces()) {
            if (pieceInformation.getPiece().equalsIgnoreColor(instanceForColor)) {
                final int row = pieceInformation.getSquare().getRow();
                if (row == 0 || row == 7) {
                    return false;
                }
                continue;
            }
        }
        return true;
    }
}
