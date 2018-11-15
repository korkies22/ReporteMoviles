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
import de.cisha.android.board.profile.EditEmailDialogFragment;
import de.cisha.android.ui.patterns.input.CustomEditText;

class EditEmailDialogFragment
implements View.OnClickListener {
    EditEmailDialogFragment() {
    }

    public void onClick(View view) {
        if (EditEmailDialogFragment.this._listener != null) {
            EditEmailDialogFragment.this._listener.onEmailChanged(EditEmailDialogFragment.this._editEmailField.getEditText().getText().toString(), EditEmailDialogFragment.this._editPasswordField.getEditText().getText().toString());
        }
    }
}
