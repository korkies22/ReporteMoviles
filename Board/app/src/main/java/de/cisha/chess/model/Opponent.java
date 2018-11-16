// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

import java.io.Serializable;

public class Opponent extends BaseObject implements Serializable
{
    private static final long serialVersionUID = 4867442321172613212L;
    private Country _country;
    private boolean _isPremium;
    private String _name;
    private Rating _rating;
    private String _title;
    private String _type;
    
    public Opponent() {
        this._name = "";
        this._title = "";
        this._country = null;
        this._type = "";
        this._rating = Rating.NO_RATING;
        this._isPremium = false;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Opponent)) {
            return false;
        }
        final Opponent opponent = (Opponent)o;
        return this.equals(opponent._country, this._country) && this.equals(opponent._name, this._name) && this.equals(opponent._rating, this._rating) && this.equals(opponent._title, this._title) && this.equals(opponent._type, this._type) && opponent._isPremium == this._isPremium;
    }
    
    public Country getCountry() {
        return this._country;
    }
    
    public String getName() {
        return this._name;
    }
    
    public String getNameAndTitle() {
        final StringBuilder sb = new StringBuilder();
        String string;
        if (this.hasTitle()) {
            final StringBuilder sb2 = new StringBuilder();
            sb2.append(this._title);
            sb2.append(" ");
            string = sb2.toString();
        }
        else {
            string = "";
        }
        sb.append(string);
        String name;
        if (this._name != null) {
            name = this._name;
        }
        else {
            name = "";
        }
        sb.append(name);
        return sb.toString();
    }
    
    public Rating getRating() {
        if (this._rating != null) {
            return this._rating;
        }
        return Rating.NO_RATING;
    }
    
    public String getTitle() {
        return this._title;
    }
    
    public String getType() {
        return this._type;
    }
    
    public boolean hasRating() {
        return this._rating != null && this._rating.hasRating();
    }
    
    public boolean hasTitle() {
        return this._title != null && !this._title.trim().equals("");
    }
    
    @Override
    public int hashCode() {
        return ((((527 + this.hashcode(this._country)) * 31 + this.hashcode(this._name)) * 31 + this.hashcode(this._rating)) * 31 + this.hashcode(this._title)) * 31 + this.hashcode(this._type);
    }
    
    public boolean isPremium() {
        return this._isPremium;
    }
    
    public void setCountry(final Country country) {
        this._country = country;
    }
    
    public void setName(final String name) {
        this._name = name;
    }
    
    public void setPremium(final boolean isPremium) {
        this._isPremium = isPremium;
    }
    
    public void setRating(final Rating rating) {
        this._rating = rating;
    }
    
    public void setTitle(final String title) {
        this._title = title;
    }
    
    public void setType(final String type) {
        this._type = type;
    }
}
