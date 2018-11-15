/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 */
package de.cisha.chess.model.position.validator;

import android.content.Context;
import android.content.res.Resources;
import de.cisha.chess.R;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.PieceInformation;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.pieces.Pawn;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.validator.IPositionRuleValidator;
import java.util.List;

public class PawnInFirstOrLastRowValidator
implements IPositionRuleValidator {
    @Override
    public String getErrorMessage(Context context) {
        return context.getResources().getString(R.string.position_validation_rule_pawns_in_impossible_positions);
    }

    @Override
    public boolean verify(Position object) {
        Pawn pawn = Pawn.instanceForColor(true);
        for (PieceInformation pieceInformation : object.getAllPieces()) {
            int n;
            if (!pieceInformation.getPiece().equalsIgnoreColor(pawn) || (n = pieceInformation.getSquare().getRow()) != 0 && n != 7) continue;
            return false;
        }
        return true;
    }
}
