// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model.position.validator;

import java.util.Iterator;
import de.cisha.chess.model.PieceInformation;
import de.cisha.chess.model.pieces.King;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.R;
import android.content.Context;

public class ExistingKingsValidator implements IPositionRuleValidator
{
    @Override
    public String getErrorMessage(final Context context) {
        return context.getResources().getString(R.string.position_validation_rule_wrong_number_of_kings);
    }
    
    @Override
    public boolean verify(final Position position) {
        final King instanceForColor = King.instanceForColor(true);
        final King instanceForColor2 = King.instanceForColor(false);
        final Iterator<PieceInformation> iterator = position.getAllPieces().iterator();
        int n2;
        int n = n2 = 0;
        while (iterator.hasNext()) {
            final PieceInformation pieceInformation = iterator.next();
            if (pieceInformation.getPiece().equals(instanceForColor)) {
                ++n2;
            }
            else {
                if (!pieceInformation.getPiece().equals(instanceForColor2)) {
                    continue;
                }
                ++n;
            }
        }
        return n == 1 && n2 == 1;
    }
}
