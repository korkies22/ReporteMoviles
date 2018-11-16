// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile;

import de.cisha.android.board.user.User;
import de.cisha.android.ui.patterns.dialogs.OptionDialogFragment;

public class GenderOption extends Option
{
    private User.Gender _sex;
    
    public GenderOption(final String s, final User.Gender sex) {
        super(s);
        this._sex = sex;
    }
    
    public User.Gender getSex() {
        return this._sex;
    }
}
