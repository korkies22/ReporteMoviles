/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 */
package de.cisha.chess.model;

import android.content.res.Resources;
import de.cisha.chess.R;

public class ClockSetting {
    private int _baseBlack = n *= 1000;
    private int _baseWhite = 0;
    private int _incrementPerMoveBlack = 0;
    private int _incrementPerMoveWhite = 0;
    private int _timeGoneSinceLastMove = 0;

    public ClockSetting(int n, int n2) {
        this._baseWhite = n;
        this._incrementPerMoveBlack = n = n2 * 1000;
        this._incrementPerMoveWhite = n;
    }

    public boolean equals(Object object) {
        if (object instanceof ClockSetting) {
            if ((object = (ClockSetting)object).getBase(true) == this._baseWhite && object.getBase(false) == this._baseBlack && object.getIncrementPerMove(true) == this._incrementPerMoveWhite && object.getIncrementPerMove(false) == this._incrementPerMoveBlack && object.getTimeGoneSinceLastMove() == this._timeGoneSinceLastMove) {
                return true;
            }
            return false;
        }
        return false;
    }

    public int getBase(boolean bl) {
        if (bl) {
            return this._baseWhite;
        }
        return this._baseBlack;
    }

    public GameClockType getClockType() {
        int n = this._baseWhite + 30 * this._incrementPerMoveWhite;
        if (n == 0) {
            return GameClockType.WITHOUT_TIME;
        }
        if (n < 180000) {
            return GameClockType.BULLET;
        }
        if (n < 720000) {
            return GameClockType.BLITZ;
        }
        if (n < 3600000) {
            return GameClockType.RAPID;
        }
        return GameClockType.STANDARD;
    }

    public int getIncrementPerMove(boolean bl) {
        if (bl) {
            return this._incrementPerMoveWhite;
        }
        return this._incrementPerMoveBlack;
    }

    public int getTimeGoneSinceLastMove() {
        return this._timeGoneSinceLastMove;
    }

    public int hashCode() {
        return this._baseBlack * this._baseWhite + this._incrementPerMoveBlack + this._incrementPerMoveWhite;
    }

    public boolean isEmpty() {
        if (this._baseBlack == 0 && this._baseWhite == 0 && this._incrementPerMoveBlack == 0 && this._incrementPerMoveWhite == 0) {
            return true;
        }
        return false;
    }

    public void setBase(boolean bl, int n) {
        if (bl) {
            this._baseWhite = n;
            return;
        }
        this._baseBlack = n;
    }

    public void setIncrementPerMove(boolean bl, int n) {
        if (bl) {
            this._incrementPerMoveWhite = n;
            return;
        }
        this._incrementPerMoveBlack = n;
    }

    public void setTimeGoneSinceLastMove(int n) {
        this._timeGoneSinceLastMove = n;
    }

    public static enum GameClockType {
        BULLET(R.string.game_type_bullet),
        BLITZ(R.string.game_type_blitz),
        RAPID(R.string.game_type_rapid),
        STANDARD(R.string.game_type_standard),
        WITHOUT_TIME(R.string.game_type_no_time);
        
        public int _resStringId;

        private GameClockType(int n2) {
            this._resStringId = n2;
        }

        public String getString(Resources resources) {
            return resources.getString(this._resStringId);
        }
    }

}
