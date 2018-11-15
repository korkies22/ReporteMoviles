/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

public class Rating {
    public static Rating NO_RATING = new Rating(-1);
    private static final int NO_RATING_INT = -1;
    private int _rating;

    public Rating(int n) {
        this._rating = n;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Rating)) {
            return false;
        }
        if (((Rating)object)._rating == this._rating) {
            return true;
        }
        return false;
    }

    public int getRating() {
        return this._rating;
    }

    public String getRatingString() {
        if (this.hasRating()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(this._rating);
            return stringBuilder.toString();
        }
        return "-";
    }

    public boolean hasRating() {
        if (this._rating != -1) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return 527 + this._rating;
    }

    public String toString() {
        return this.getRatingString();
    }
}
