/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile;

import de.cisha.android.board.user.User;
import de.cisha.android.ui.patterns.dialogs.OptionDialogFragment;

public class GenderOption
extends OptionDialogFragment.Option {
    private User.Gender _sex;

    public GenderOption(String string, User.Gender gender) {
        super(string);
        this._sex = gender;
    }

    public User.Gender getSex() {
        return this._sex;
    }
}
