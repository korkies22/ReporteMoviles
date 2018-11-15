/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.user;

import de.cisha.android.board.user.User;

public static enum User.Gender {
    MALE(1, 2131690226),
    FEMALE(2, 2131690225);
    
    private int _id;
    private int _resTitleId;

    private User.Gender(int n2, int n3) {
        this._id = n2;
        this._resTitleId = n3;
    }

    public static User.Gender from(int n) {
        switch (n) {
            default: {
                return MALE;
            }
            case 2: {
                return FEMALE;
            }
            case 1: 
        }
        return MALE;
    }

    public int getNameResId() {
        return this._resTitleId;
    }

    public int toInt() {
        return this._id;
    }
}
