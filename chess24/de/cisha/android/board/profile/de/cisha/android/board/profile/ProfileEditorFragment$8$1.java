/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile;

import de.cisha.android.board.profile.GenderOption;
import de.cisha.android.board.profile.ProfileEditorFragment;
import de.cisha.android.board.user.User;
import de.cisha.android.ui.patterns.dialogs.OptionDialogFragment;
import de.cisha.android.ui.patterns.input.CustomEditText;

class ProfileEditorFragment
implements OptionDialogFragment.OptionSelectionListener<GenderOption> {
    ProfileEditorFragment() {
    }

    @Override
    public void onOptionSelected(GenderOption genderOption) {
        8.this.this$0._selectedGender = genderOption.getSex();
        8.this.this$0._sexField.setText(genderOption.getName());
    }
}
