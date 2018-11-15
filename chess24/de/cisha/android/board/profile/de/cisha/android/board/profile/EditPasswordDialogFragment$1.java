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
import de.cisha.android.board.profile.EditPasswordDialogFragment;
import de.cisha.android.ui.patterns.input.CustomEditPassword;

class EditPasswordDialogFragment
implements View.OnClickListener {
    EditPasswordDialogFragment() {
    }

    public void onClick(View object) {
        object = EditPasswordDialogFragment.this._editPasswordCurrent.getEditText().getText().toString();
        String string = EditPasswordDialogFragment.this._editPasswordNew.getEditText().getText().toString();
        String string2 = EditPasswordDialogFragment.this._editPasswordRepeat.getEditText().getText().toString();
        if (EditPasswordDialogFragment.this._listener != null) {
            EditPasswordDialogFragment.this._listener.onPasswordChanged((String)object, string, string2);
        }
    }
}
