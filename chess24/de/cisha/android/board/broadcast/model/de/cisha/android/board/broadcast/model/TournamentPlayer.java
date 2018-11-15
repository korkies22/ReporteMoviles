/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import com.neovisionaries.i18n.CountryCode;
import de.cisha.android.board.broadcast.model.PlayerStanding;

public class TournamentPlayer {
    private CountryCode _country;
    private int _elo;
    private int _fideId;
    private String _fullName;
    private PlayerStanding _standing;
    private String _title = "";

    public TournamentPlayer(String string, int n) {
        this._fullName = string;
        this._fideId = n;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof TournamentPlayer)) {
            return false;
        }
        object = (TournamentPlayer)object;
        if (object._fideId == this._fideId && this._fullName.equals(object._fullName)) {
            return true;
        }
        return false;
    }

    public CountryCode getCountry() {
        return this._country;
    }

    public int getElo() {
        return this._elo;
    }

    public int getFideId() {
        return this._fideId;
    }

    public String getFullName() {
        return this._fullName;
    }

    public String getNameWithTitleAndRating() {
        CharSequence charSequence;
        CharSequence charSequence2 = charSequence = "";
        if (this._title != null) {
            charSequence2 = charSequence;
            if (!"".equals(this._title)) {
                charSequence2 = new StringBuilder();
                charSequence2.append("");
                charSequence2.append(this._title);
                charSequence2.append(" ");
                charSequence2 = charSequence2.toString();
            }
        }
        charSequence = new StringBuilder();
        charSequence.append((String)charSequence2);
        charSequence.append(this.getFullName());
        charSequence2 = charSequence = charSequence.toString();
        if (this._elo > 0) {
            charSequence2 = new StringBuilder();
            charSequence2.append((String)charSequence);
            charSequence2.append(" (");
            charSequence2.append(this._elo);
            charSequence2.append(")");
            charSequence2 = charSequence2.toString();
        }
        return charSequence2;
    }

    public PlayerStanding getStanding() {
        return this._standing;
    }

    public String getTitle() {
        return this._title;
    }

    public int hashCode() {
        return (17 + this._fideId * 32) * (32 + this._fullName.hashCode());
    }

    public void setCountry(CountryCode countryCode) {
        this._country = countryCode;
    }

    public void setElo(int n) {
        this._elo = n;
    }

    public void setFideId(int n) {
        this._fideId = n;
    }

    public void setFullName(String string) {
        this._fullName = string;
    }

    public void setStanding(PlayerStanding playerStanding) {
        this._standing = playerStanding;
    }

    public void setTitle(String string) {
        this._title = string;
    }
}
