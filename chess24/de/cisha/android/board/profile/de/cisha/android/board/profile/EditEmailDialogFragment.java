/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.text.Editable
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.EditText
 */
package de.cisha.android.board.profile;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import de.cisha.android.board.profile.view.FormErrorView;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.ui.patterns.dialogs.EmptyDialogFragment;
import de.cisha.android.ui.patterns.input.CustomEditText;
import java.util.List;

public class EditEmailDialogFragment
extends EmptyDialogFragment {
    private CustomEditText _editEmailField;
    private CustomEditText _editPasswordField;
    private FormErrorView _errorView;
    private OnEmailChangedListener _listener;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setTitle(this.getActivity().getString(2131689953));
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        viewGroup = (ViewGroup)super.onCreateView(layoutInflater, viewGroup, bundle);
        layoutInflater.inflate(2131427433, viewGroup);
        return viewGroup;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this._editEmailField = (CustomEditText)view.findViewById(2131296493);
        this._editPasswordField = (CustomEditText)view.findViewById(2131296494);
        this._errorView = (FormErrorView)view.findViewById(2131296492);
        view.findViewById(2131296485).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                EditEmailDialogFragment.this.dismiss();
            }
        });
        view.findViewById(2131296487).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (EditEmailDialogFragment.this._listener != null) {
                    EditEmailDialogFragment.this._listener.onEmailChanged(EditEmailDialogFragment.this._editEmailField.getEditText().getText().toString(), EditEmailDialogFragment.this._editPasswordField.getEditText().getText().toString());
                }
            }
        });
    }

    public void setErrors(List<LoadFieldError> list) {
        this._errorView.setErrors(list);
    }

    public void setOnEmailChangedListener(OnEmailChangedListener onEmailChangedListener) {
        this._listener = onEmailChangedListener;
    }

    public static interface OnEmailChangedListener {
        public void onEmailChanged(String var1, String var2);
    }

}
