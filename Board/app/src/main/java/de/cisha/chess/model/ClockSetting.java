// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

import android.content.res.Resources;
import de.cisha.chess.R;

public class ClockSetting
{
    private int _baseBlack;
    private int _baseWhite;
    private int _incrementPerMoveBlack;
    private int _incrementPerMoveWhite;
    private int _timeGoneSinceLastMove;
    
    public ClockSetting(int n, final int n2) {
        this._timeGoneSinceLastMove = 0;
        this._baseWhite = 0;
        this._baseBlack = 0;
        this._incrementPerMoveWhite = 0;
        this._incrementPerMoveBlack = 0;
        n *= 1000;
        this._baseBlack = n;
        this._baseWhite = n;
        n = n2 * 1000;
        this._incrementPerMoveBlack = n;
        this._incrementPerMoveWhite = n;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof ClockSetting) {
            final ClockSetting clockSetting = (ClockSetting)o;
            return clockSetting.getBase(true) == this._baseWhite && clockSetting.getBase(false) == this._baseBlack && clockSetting.getIncrementPerMove(true) == this._incrementPerMoveWhite && clockSetting.getIncrementPerMove(false) == this._incrementPerMoveBlack && clockSetting.getTimeGoneSinceLastMove() == this._timeGoneSinceLastMove;
        }
        return false;
    }
    
    public int getBase(final boolean b) {
        if (b) {
            return this._baseWhite;
        }
        return this._baseBlack;
    }
    
    public GameClockType getClockType() {
        final int n = this._baseWhite + 30 * this._incrementPerMoveWhite;
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
    
    public int getIncrementPerMove(final boolean b) {
        if (b) {
            return this._incrementPerMoveWhite;
        }
        return this._incrementPerMoveBlack;
    }
    
    public int getTimeGoneSinceLastMove() {
        return this._timeGoneSinceLastMove;
    }
    
    @Override
    public int hashCode() {
        return this._baseBlack * this._baseWhite + this._incrementPerMoveBlack + this._incrementPerMoveWhite;
    }
    
    public boolean isEmpty() {
        return this._baseBlack == 0 && this._baseWhite == 0 && this._incrementPerMoveBlack == 0 && this._incrementPerMoveWhite == 0;
    }
    
    public void setBase(final boolean b, final int n) {
        if (b) {
            this._baseWhite = n;
            return;
        }
        this._baseBlack = n;
    }
    
    public void setIncrementPerMove(final boolean b, final int n) {
        if (b) {
            this._incrementPerMoveWhite = n;
            return;
        }
        this._incrementPerMoveBlack = n;
    }
    
    public void setTimeGoneSinceLastMove(final int timeGoneSinceLastMove) {
        this._timeGoneSinceLastMove = timeGoneSinceLastMove;
    }
    
    public enum GameClockType
    {
        BLITZ(R.string.game_type_blitz), 
        BULLET(R.string.game_type_bullet), 
        RAPID(R.string.game_type_rapid), 
        STANDARD(R.string.game_type_standard), 
        WITHOUT_TIME(R.string.game_type_no_time);
        
        public int _resStringId;
        
        private GameClockType(final int resStringId) {
            this._resStringId = resStringId;
        }
        
        public String getString(final Resources resources) {
            return resources.getString(this._resStringId);
        }
    }
}
