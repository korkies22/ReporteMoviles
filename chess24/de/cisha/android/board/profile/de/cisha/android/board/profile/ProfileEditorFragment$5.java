/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.EditText
 */
package de.cisha.android.board.profile;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.EditText;
import de.cisha.android.board.profile.EditPasswordDialogFragment;
import de.cisha.android.ui.patterns.input.CustomEditPassword;

class ProfileEditorFragment
implements View.OnClickListener {
    ProfileEditorFragment() {
    }

    public void onClick(View view) {
        ProfileEditorFragment.this._passwordField.getEditText().requestFocus();
        ProfileEditorFragment.this._dialogEditPassword = new EditPasswordDialogFragment();
        ProfileEditorFragment.this._dialogEditPassword.setOnPasswordChangeListener(new EditPasswordDialogFragment.OnPasswordChangedListener(){

            @Override
            public void onPasswordChanged(String string, String string2, String string3) {
                ProfileEditorFragment.this.setPassword(string, string2, string3);
            }
        });
        ProfileEditorFragment.this._dialogEditPassword.show(ProfileEditorFragment.this.getFragmentManager(), null);
    }

}
