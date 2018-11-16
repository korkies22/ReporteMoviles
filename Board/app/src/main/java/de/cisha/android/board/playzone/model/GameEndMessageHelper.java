// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.model;

import de.cisha.chess.model.GameStatus;
import de.cisha.chess.util.Logger;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.GameEndReason;
import android.content.res.Resources;

public class GameEndMessageHelper
{
    private Resources _res;
    
    public GameEndMessageHelper(final Resources res) {
        this._res = res;
    }
    
    public String getDrawMessage(final GameEndReason gameEndReason, final String s, final boolean b) {
        switch (GameEndMessageHelper.1..SwitchMap.de.cisha.chess.model.GameEndReason[gameEndReason.ordinal()]) {
            default: {
                return this._res.getString(2131690151, new Object[] { s });
            }
            case 7: {
                return this._res.getString(2131690139);
            }
            case 6: {
                if (b) {
                    return this._res.getString(2131690138, new Object[] { s });
                }
                return this._res.getString(2131690135, new Object[] { s });
            }
            case 5: {
                return this._res.getString(2131690136, new Object[] { s });
            }
            case 4: {
                if (b) {
                    return this._res.getString(2131690141, new Object[] { s });
                }
                return this._res.getString(2131690140, new Object[] { s });
            }
            case 3: {
                return this._res.getString(2131690134, new Object[] { s });
            }
            case 2: {
                return this._res.getString(2131690156, new Object[] { s });
            }
            case 1: {
                return this._res.getString(2131690137, new Object[] { s });
            }
        }
    }
    
    public String getForfeitMessage(final GameResult gameResult, final String s, final boolean b) {
        final boolean hasWhiteLost = gameResult.hasWhiteLost();
        final boolean hasBlackLost = gameResult.hasBlackLost();
        final boolean hasWhiteWon = gameResult.hasWhiteWon();
        final boolean hasBlackWon = gameResult.hasBlackWon();
        if (hasWhiteLost && hasBlackLost) {
            return this._res.getString(2131690133);
        }
        if ((hasWhiteWon && b) || (hasBlackWon && !b)) {
            return this._res.getString(2131690146, new Object[] { s });
        }
        if ((hasWhiteLost && b) || (hasBlackLost && !b)) {
            return this._res.getString(2131690143);
        }
        final Logger instance = Logger.getInstance();
        final StringBuilder sb = new StringBuilder();
        sb.append("unknown forfeit reason: ");
        sb.append(gameResult);
        instance.error("GameEndMessageHelper", sb.toString());
        return "";
    }
    
    public String getGameEndTitleMessage(final GameStatus gameStatus, final boolean b) {
        final GameResult result = gameStatus.getResult();
        if (result != GameResult.BLACK_WINS && result != GameResult.WHITE_WINS) {
            if (result == GameResult.NO_RESULT) {
                return this._res.getString(2131689989);
            }
            return this._res.getString(2131689990);
        }
        else {
            if (result == GameResult.WHITE_WINS == b) {
                return this._res.getString(2131689992);
            }
            return this._res.getString(2131689991);
        }
    }
    
    public String getLoseMessage(final GameEndReason gameEndReason, final String s) {
        switch (GameEndMessageHelper.1..SwitchMap.de.cisha.chess.model.GameEndReason[gameEndReason.ordinal()]) {
            default: {
                return this._res.getString(2131690151, new Object[] { s });
            }
            case 11: {
                return this._res.getString(2131690155, new Object[] { s });
            }
            case 10: {
                return this._res.getString(2131690144, new Object[] { s });
            }
            case 9: {
                return this._res.getString(2131690153, new Object[] { s });
            }
            case 8: {
                return this._res.getString(2131690154, new Object[] { s });
            }
        }
    }
    
    public String getMessage(final GameStatus gameStatus, final boolean b, final boolean b2, final String s) {
        final GameEndReason reason = gameStatus.getReason();
        final GameResult result = gameStatus.getResult();
        if (result != GameResult.BLACK_WINS && result != GameResult.WHITE_WINS) {
            if (result == GameResult.NO_RESULT) {
                return this.getNoResultMessage(reason, s);
            }
            if (result == GameResult.DRAW) {
                return this.getDrawMessage(reason, s, b2);
            }
            if (result != null && result.isForfeiture()) {
                return this.getForfeitMessage(result, s, b);
            }
            final Logger instance = Logger.getInstance();
            final StringBuilder sb = new StringBuilder();
            sb.append("unknown gameEndReason: ");
            sb.append(result);
            instance.error("GameEndMessageHelper", sb.toString());
            return "";
        }
        else {
            if (result == GameResult.WHITE_WINS == b) {
                return this.getWinMessage(reason, s);
            }
            return this.getLoseMessage(reason, s);
        }
    }
    
    public String getNoResultMessage(final GameEndReason gameEndReason, final String s) {
        return this._res.getString(2131690142, new Object[] { s });
    }
    
    public String getWinMessage(final GameEndReason gameEndReason, final String s) {
        switch (GameEndMessageHelper.1..SwitchMap.de.cisha.chess.model.GameEndReason[gameEndReason.ordinal()]) {
            default: {
                return this._res.getString(2131690151, new Object[] { s });
            }
            case 11: {
                return this._res.getString(2131690148, new Object[] { s });
            }
            case 10: {
                return this._res.getString(2131690152, new Object[] { s });
            }
            case 9: {
                return this._res.getString(2131690157, new Object[] { s });
            }
            case 8: {
                return this._res.getString(2131690147, new Object[] { s });
            }
        }
    }
}
