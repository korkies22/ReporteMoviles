/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.model.BaseObject;
import de.cisha.chess.model.Country;
import de.cisha.chess.model.Rating;
import java.io.Serializable;

public class Opponent
extends BaseObject
implements Serializable {
    private static final long serialVersionUID = 4867442321172613212L;
    private Country _country = null;
    private boolean _isPremium = false;
    private String _name = "";
    private Rating _rating = Rating.NO_RATING;
    private String _title = "";
    private String _type = "";

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Opponent)) {
            return false;
        }
        object = (Opponent)object;
        if (this.equals(object._country, this._country) && this.equals(object._name, this._name) && this.equals(object._rating, this._rating) && this.equals(object._title, this._title) && this.equals(object._type, this._type) && object._isPremium == this._isPremium) {
            return true;
        }
        return false;
    }

    public Country getCountry() {
        return this._country;
    }

    public String getName() {
        return this._name;
    }

    public String getNameAndTitle() {
        CharSequence charSequence;
        StringBuilder stringBuilder = new StringBuilder();
        if (this.hasTitle()) {
            charSequence = new StringBuilder();
            charSequence.append(this._title);
            charSequence.append(" ");
            charSequence = charSequence.toString();
        } else {
            charSequence = "";
        }
        stringBuilder.append((String)charSequence);
        charSequence = this._name != null ? this._name : "";
        stringBuilder.append((String)charSequence);
        return stringBuilder.toString();
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
        if (this._rating != null && this._rating.hasRating()) {
            return true;
        }
        return false;
    }

    public boolean hasTitle() {
        if (this._title != null && !this._title.trim().equals("")) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((527 + this.hashcode(this._country)) * 31 + this.hashcode(this._name)) * 31 + this.hashcode(this._rating)) * 31 + this.hashcode(this._title)) * 31 + this.hashcode(this._type);
    }

    public boolean isPremium() {
        return this._isPremium;
    }

    public void setCountry(Country country) {
        this._country = country;
    }

    public void setName(String string) {
        this._name = string;
    }

    public void setPremium(boolean bl) {
        this._isPremium = bl;
    }

    public void setRating(Rating rating) {
        this._rating = rating;
    }

    public void setTitle(String string) {
        this._title = string;
    }

    public void setType(String string) {
        this._type = string;
    }
}
