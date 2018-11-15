/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 */
package de.cisha.android.board.playzone.model;

import android.content.res.Resources;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.util.Logger;

public class GameEndMessageHelper {
    private Resources _res;

    public GameEndMessageHelper(Resources resources) {
        this._res = resources;
    }

    public String getDrawMessage(GameEndReason gameEndReason, String string, boolean bl) {
        switch (.$SwitchMap$de$cisha$chess$model$GameEndReason[gameEndReason.ordinal()]) {
            default: {
                return this._res.getString(2131690151, new Object[]{string});
            }
            case 7: {
                return this._res.getString(2131690139);
            }
            case 6: {
                if (bl) {
                    return this._res.getString(2131690138, new Object[]{string});
                }
                return this._res.getString(2131690135, new Object[]{string});
            }
            case 5: {
                return this._res.getString(2131690136, new Object[]{string});
            }
            case 4: {
                if (bl) {
                    return this._res.getString(2131690141, new Object[]{string});
                }
                return this._res.getString(2131690140, new Object[]{string});
            }
            case 3: {
                return this._res.getString(2131690134, new Object[]{string});
            }
            case 2: {
                return this._res.getString(2131690156, new Object[]{string});
            }
            case 1: 
        }
        return this._res.getString(2131690137, new Object[]{string});
    }

    public String getForfeitMessage(GameResult gameResult, String object, boolean bl) {
        boolean bl2 = gameResult.hasWhiteLost();
        boolean bl3 = gameResult.hasBlackLost();
        boolean bl4 = gameResult.hasWhiteWon();
        boolean bl5 = gameResult.hasBlackWon();
        if (bl2 && bl3) {
            return this._res.getString(2131690133);
        }
        if (bl4 && bl || bl5 && !bl) {
            return this._res.getString(2131690146, new Object[]{object});
        }
        if (bl2 && bl || bl3 && !bl) {
            return this._res.getString(2131690143);
        }
        object = Logger.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unknown forfeit reason: ");
        stringBuilder.append((Object)gameResult);
        object.error("GameEndMessageHelper", stringBuilder.toString());
        return "";
    }

    public String getGameEndTitleMessage(GameStatus object, boolean bl) {
        if ((object = object.getResult()) != GameResult.BLACK_WINS && object != GameResult.WHITE_WINS) {
            if (object == GameResult.NO_RESULT) {
                return this._res.getString(2131689989);
            }
            return this._res.getString(2131689990);
        }
        boolean bl2 = object == GameResult.WHITE_WINS;
        if (bl2 == bl) {
            return this._res.getString(2131689992);
        }
        return this._res.getString(2131689991);
    }

    public String getLoseMessage(GameEndReason gameEndReason, String string) {
        switch (.$SwitchMap$de$cisha$chess$model$GameEndReason[gameEndReason.ordinal()]) {
            default: {
                return this._res.getString(2131690151, new Object[]{string});
            }
            case 11: {
                return this._res.getString(2131690155, new Object[]{string});
            }
            case 10: {
                return this._res.getString(2131690144, new Object[]{string});
            }
            case 9: {
                return this._res.getString(2131690153, new Object[]{string});
            }
            case 8: 
        }
        return this._res.getString(2131690154, new Object[]{string});
    }

    public String getMessage(GameStatus object, boolean bl, boolean bl2, String object2) {
        Object object3 = object.getReason();
        if ((object = object.getResult()) != GameResult.BLACK_WINS && object != GameResult.WHITE_WINS) {
            if (object == GameResult.NO_RESULT) {
                return this.getNoResultMessage((GameEndReason)((Object)object3), (String)object2);
            }
            if (object == GameResult.DRAW) {
                return this.getDrawMessage((GameEndReason)((Object)object3), (String)object2, bl2);
            }
            if (object != null && object.isForfeiture()) {
                return this.getForfeitMessage((GameResult)((Object)object), (String)object2, bl);
            }
            object2 = Logger.getInstance();
            object3 = new StringBuilder();
            object3.append("unknown gameEndReason: ");
            object3.append(object);
            object2.error("GameEndMessageHelper", object3.toString());
            return "";
        }
        bl2 = object == GameResult.WHITE_WINS;
        if (bl2 == bl) {
            return this.getWinMessage((GameEndReason)((Object)object3), (String)object2);
        }
        return this.getLoseMessage((GameEndReason)((Object)object3), (String)object2);
    }

    public String getNoResultMessage(GameEndReason gameEndReason, String string) {
        return this._res.getString(2131690142, new Object[]{string});
    }

    public String getWinMessage(GameEndReason gameEndReason, String string) {
        switch (.$SwitchMap$de$cisha$chess$model$GameEndReason[gameEndReason.ordinal()]) {
            default: {
                return this._res.getString(2131690151, new Object[]{string});
            }
            case 11: {
                return this._res.getString(2131690148, new Object[]{string});
            }
            case 10: {
                return this._res.getString(2131690152, new Object[]{string});
            }
            case 9: {
                return this._res.getString(2131690157, new Object[]{string});
            }
            case 8: 
        }
        return this._res.getString(2131690147, new Object[]{string});
    }

}
