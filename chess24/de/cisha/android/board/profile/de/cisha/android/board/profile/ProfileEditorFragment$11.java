/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile;

import de.cisha.android.board.profile.view.CustomCountryChoser;
import de.cisha.android.board.user.User;
import de.cisha.android.ui.patterns.input.CustomEditDate;
import de.cisha.android.ui.patterns.input.CustomEditPassword;
import de.cisha.android.ui.patterns.input.CustomEditText;
import de.cisha.chess.model.Country;
import java.util.Date;

class ProfileEditorFragment
implements Runnable {
    ProfileEditorFragment() {
    }

    @Override
    public void run() {
        if (ProfileEditorFragment.this._user != null && ProfileEditorFragment.this._viewCreated) {
            ProfileEditorFragment.this._passwordField.setText("dummylongpassword");
            CustomEditText customEditText = ProfileEditorFragment.this._emailField;
            Object object = ProfileEditorFragment.this._user.getEmail() != null ? ProfileEditorFragment.this._user.getEmail() : "";
            customEditText.setText((CharSequence)object);
            customEditText = ProfileEditorFragment.this._surnameField;
            object = ProfileEditorFragment.this._user.getSurname() != null ? ProfileEditorFragment.this._user.getSurname() : "";
            customEditText.setText((CharSequence)object);
            customEditText = ProfileEditorFragment.this._firstnameField;
            object = ProfileEditorFragment.this._user.getFirstname() != null ? ProfileEditorFragment.this._user.getFirstname() : "";
            customEditText.setText((CharSequence)object);
            customEditText = ProfileEditorFragment.this._usernameField;
            object = ProfileEditorFragment.this._user.getNickname() != null ? ProfileEditorFragment.this._user.getNickname() : "";
            customEditText.setText((CharSequence)object);
            ProfileEditorFragment.this._countryField.setCountry(ProfileEditorFragment.this._user.getCountry());
            object = ProfileEditorFragment.this._user.getBirthday();
            if (object != null) {
                ProfileEditorFragment.this._dateField.setDate((Date)object);
            }
            object = ProfileEditorFragment.this._user.getGender() != null ? ProfileEditorFragment.this._user.getGender() : User.Gender.MALE;
            ProfileEditorFragment.this._selectedGender = (User.Gender)((Object)object);
            ProfileEditorFragment.this._sexField.setText(ProfileEditorFragment.this.getString(object.getNameResId()));
        }
    }
}
