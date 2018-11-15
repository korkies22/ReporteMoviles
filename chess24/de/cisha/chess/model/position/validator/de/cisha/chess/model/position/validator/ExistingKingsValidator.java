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
import de.cisha.chess.model.pieces.King;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.validator.IPositionRuleValidator;
import java.util.Iterator;
import java.util.List;

public class ExistingKingsValidator
implements IPositionRuleValidator {
    @Override
    public String getErrorMessage(Context context) {
        return context.getResources().getString(R.string.position_validation_rule_wrong_number_of_kings);
    }

    @Override
    public boolean verify(Position object) {
        int n;
        King king = King.instanceForColor(true);
        King king2 = King.instanceForColor(false);
        object = object.getAllPieces().iterator();
        int n2 = n = 0;
        while (object.hasNext()) {
            PieceInformation pieceInformation = (PieceInformation)object.next();
            if (pieceInformation.getPiece().equals(king)) {
                ++n2;
                continue;
            }
            if (!pieceInformation.getPiece().equals(king2)) continue;
            ++n;
        }
        if (n == 1 && n2 == 1) {
            return true;
        }
        return false;
    }
}
