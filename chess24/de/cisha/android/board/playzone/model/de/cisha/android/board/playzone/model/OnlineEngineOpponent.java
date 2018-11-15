/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class OnlineEngineOpponent {
    @DatabaseField
    private int _color;
    @DatabaseField
    private String _description;
    @DatabaseField
    private boolean _isLocked;
    @DatabaseField
    private boolean _isRateGame;
    @DatabaseField(uniqueIndex=true)
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

    public void setColor(int n) {
        this._color = n;
    }

    public void setDescription(String string) {
        this._description = string;
    }

    public void setLocked(boolean bl) {
        this._isLocked = bl;
    }

    public void setName(String string) {
        this._name = string;
    }

    public void setProfilePictureCouchIdString(String string) {
        this._profilePictureCouchIdString = string;
    }

    public void setRateGame(boolean bl) {
        this._isRateGame = bl;
    }

    public void setTimeControlIncrementSeconds(int n) {
        this._timeControlIncrementSeconds = n;
    }

    public void setTimeControlSeconds(int n) {
        this._timeControlSeconds = n;
    }

    public void setUuidString(String string) {
        this._uuidString = string;
    }
}
