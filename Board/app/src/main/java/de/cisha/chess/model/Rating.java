// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

public class Rating
{
    public static Rating NO_RATING;
    private static final int NO_RATING_INT = -1;
    private int _rating;
    
    static {
        Rating.NO_RATING = new Rating(-1);
    }
    
    public Rating(final int rating) {
        this._rating = rating;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (o instanceof Rating && ((Rating)o)._rating == this._rating);
    }
    
    public int getRating() {
        return this._rating;
    }
    
    public String getRatingString() {
        if (this.hasRating()) {
            final StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(this._rating);
            return sb.toString();
        }
        return "-";
    }
    
    public boolean hasRating() {
        return this._rating != -1;
    }
    
    @Override
    public int hashCode() {
        return 527 + this._rating;
    }
    
    @Override
    public String toString() {
        return this.getRatingString();
    }
}
