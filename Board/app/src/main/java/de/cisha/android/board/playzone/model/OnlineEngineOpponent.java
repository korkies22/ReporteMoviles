// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class OnlineEngineOpponent
{
    @DatabaseField
    private int _color;
    @DatabaseField
    private String _description;
    @DatabaseField
    private boolean _isLocked;
    @DatabaseField
    private boolean _isRateGame;
    @DatabaseField(uniqueIndex = true)
    private String _name;
    @DatabaseField
    private String _profilePictureCouchIdString;
    @DatabaseField
    private int _timeControlIncrementSeconds;
    @DatabaseField
    private int _timeControlSeconds;
    @DatabaseField
    private String _uuidString;
    
    public int getColor() {
        return this._color;
    }
    
    public String getDescription() {
        return this._description;
    }
    
    public String getName() {
        return this._name;
    }
    
    public String getProfilePictureCouchIdString() {
        return this._profilePictureCouchIdString;
    }
    
    public int getTimeControlIncrementSeconds() {
        return this._timeControlIncrementSeconds;
    }
    
    public int getTimeControlSeconds() {
        return this._timeControlSeconds;
    }
    
    public String getUuidString() {
        return this._uuidString;
    }
    
    public boolean isLocked() {
        return this._isLocked;
    }
    
    public boolean isRateGame() {
        return this._isRateGame;
    }
    
    public void setColor(final int color) {
        this._color = color;
    }
    
    public void setDescription(final String description) {
        this._description = description;
    }
    
    public void setLocked(final boolean isLocked) {
        this._isLocked = isLocked;
    }
    
    public void setName(final String name) {
        this._name = name;
    }
    
    public void setProfilePictureCouchIdString(final String profilePictureCouchIdString) {
        this._profilePictureCouchIdString = profilePictureCouchIdString;
    }
    
    public void setRateGame(final boolean isRateGame) {
        this._isRateGame = isRateGame;
    }
    
    public void setTimeControlIncrementSeconds(final int timeControlIncrementSeconds) {
        this._timeControlIncrementSeconds = timeControlIncrementSeconds;
    }
    
    public void setTimeControlSeconds(final int timeControlSeconds) {
        this._timeControlSeconds = timeControlSeconds;
    }
    
    public void setUuidString(final String uuidString) {
        this._uuidString = uuidString;
    }
}
