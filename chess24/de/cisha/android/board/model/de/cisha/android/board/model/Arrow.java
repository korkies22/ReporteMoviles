/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.model;

import de.cisha.android.board.model.ArrowCategory;
import de.cisha.chess.model.SEP;

public class Arrow {
    private ArrowCategory _category;
    private int _color;
    private SEP _sep;

    public Arrow(SEP sEP, ArrowCategory arrowCategory, int n) {
        this._sep = sEP;
        this._category = arrowCategory;
        this._color = n;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof Arrow;
        boolean bl2 = false;
        if (bl) {
            object = (Arrow)object;
            bl = bl2;
            if (object._color == this._color) {
                bl = bl2;
                if (object._category == this._category) {
                    bl = bl2;
                    if (object._sep.equals(this._sep)) {
                        bl = true;
                    }
                }
            }
            return bl;
        }
        return false;
    }

    public ArrowCategory getCategory() {
        return this._category;
    }

    public int getColor() {
        return this._color;
    }

    public SEP getSep() {
        return this._sep;
    }

    public int hashCode() {
        return 17 + 31 * this._color + 13 * this._sep.getSEPNumber() + 23 * this._category.hashCode();
    }
}
