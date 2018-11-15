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
import de.cisha.android.board.profile.EditEmailDialogFragment;
import de.cisha.android.ui.patterns.input.CustomEditText;

class ProfileEditorFragment
implements View.OnClickListener {
    ProfileEditorFragment() {
    }

    public void onClick(View view) {
        ProfileEditorFragment.this._emailField.getEditText().requestFocus();
        ProfileEditorFragment.this._dialogEditEmail = new EditEmailDialogFragment();
        ProfileEditorFragment.this._dialogEditEmail.setOnEmailChangedListener(new EditEmailDialogFragment.OnEmailChangedListener(){

            @Override
            public void onEmailChanged(String string, String string2) {
                ProfileEditorFragment.this.setEmail(string, string2);
            }
        });
        ProfileEditorFragment.this._dialogEditEmail.show(ProfileEditorFragment.this.getFragmentManager(), null);
    }

}
