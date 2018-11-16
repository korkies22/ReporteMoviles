// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

import com.neovisionaries.i18n.CountryCode;

public class TournamentPlayer
{
    private CountryCode _country;
    private int _elo;
    private int _fideId;
    private String _fullName;
    private PlayerStanding _standing;
    private String _title;
    
    public TournamentPlayer(final String fullName, final int fideId) {
        this._title = "";
        this._fullName = fullName;
        this._fideId = fideId;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TournamentPlayer)) {
            return false;
        }
        final TournamentPlayer tournamentPlayer = (TournamentPlayer)o;
        return tournamentPlayer._fideId == this._fideId && this._fullName.equals(tournamentPlayer._fullName);
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
        String string = "";
        if (this._title != null) {
            string = string;
            if (!"".equals(this._title)) {
                final StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(this._title);
                sb.append(" ");
                string = sb.toString();
            }
        }
        final StringBuilder sb2 = new StringBuilder();
        sb2.append(string);
        sb2.append(this.getFullName());
        String s = sb2.toString();
        if (this._elo > 0) {
            final StringBuilder sb3 = new StringBuilder();
            sb3.append(s);
            sb3.append(" (");
            sb3.append(this._elo);
            sb3.append(")");
            s = sb3.toString();
        }
        return s;
    }
    
    public PlayerStanding getStanding() {
        return this._standing;
    }
    
    public String getTitle() {
        return this._title;
    }
    
    @Override
    public int hashCode() {
        return (17 + this._fideId * 32) * (32 + this._fullName.hashCode());
    }
    
    public void setCountry(final CountryCode country) {
        this._country = country;
    }
    
    public void setElo(final int elo) {
        this._elo = elo;
    }
    
    public void setFideId(final int fideId) {
        this._fideId = fideId;
    }
    
    public void setFullName(final String fullName) {
        this._fullName = fullName;
    }
    
    public void setStanding(final PlayerStanding standing) {
        this._standing = standing;
    }
    
    public void setTitle(final String title) {
        this._title = title;
    }
}
