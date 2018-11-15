/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.Editable
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.EditText
 */
package de.cisha.android.board.profile;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.profile.ProfileEditorFragment;
import de.cisha.android.board.profile.view.CustomCountryChoser;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.SetUserDataParamsBuilder;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.user.User;
import de.cisha.android.ui.patterns.input.CustomEditText;
import de.cisha.chess.model.Country;
import java.util.Date;
import java.util.Map;

class ProfileEditorFragment
implements View.OnClickListener {
    ProfileEditorFragment() {
    }

    public void onClick(View object) {
        if (ProfileEditorFragment.this._user != null) {
            object = new SetUserDataParamsBuilder().setFirstname(ProfileEditorFragment.this._firstnameField.getEditText().getText().toString()).setSurname(ProfileEditorFragment.this._surnameField.getEditText().getText().toString()).setCountry(ProfileEditorFragment.this._countryField.getCountry()).setBirthdate(ProfileEditorFragment.this._user.getBirthday()).setGender(ProfileEditorFragment.this._selectedGender).createParams();
            ProfileEditorFragment.this.showDialogWaiting(false, false, "", null);
            ServiceProvider.getInstance().getProfileDataService().setUserData((Map<String, String>)object, new ProfileEditorFragment.SetDataCallback(ProfileEditorFragment.this, (Map<String, String>)object));
            return;
        }
        ProfileEditorFragment.this.getContentPresenter().popCurrentFragment();
    }
}
