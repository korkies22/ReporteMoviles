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
import de.cisha.android.ui.patterns.input.CustomEditPassword;
import java.util.List;

public class EditPasswordDialogFragment
extends EmptyDialogFragment {
    private FormErrorView _confirmationFailedView;
    private CustomEditPassword _editPasswordCurrent;
    private CustomEditPassword _editPasswordNew;
    private CustomEditPassword _editPasswordRepeat;
    private OnPasswordChangedListener _listener;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setTitle(this.getActivity().getString(2131689604));
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        viewGroup = (ViewGroup)super.onCreateView(layoutInflater, viewGroup, bundle);
        layoutInflater.inflate(2131427399, viewGroup);
        return viewGroup;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        bundle = view.findViewById(2131296485);
        View view2 = view.findViewById(2131296487);
        this._editPasswordCurrent = (CustomEditPassword)view.findViewById(2131296429);
        this._editPasswordNew = (CustomEditPassword)view.findViewById(2131296430);
        this._editPasswordRepeat = (CustomEditPassword)view.findViewById(2131296431);
        this._confirmationFailedView = (FormErrorView)view.findViewById(2131296428);
        view2.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                object = EditPasswordDialogFragment.this._editPasswordCurrent.getEditText().getText().toString();
                String string = EditPasswordDialogFragment.this._editPasswordNew.getEditText().getText().toString();
                String string2 = EditPasswordDialogFragment.this._editPasswordRepeat.getEditText().getText().toString();
                if (EditPasswordDialogFragment.this._listener != null) {
                    EditPasswordDialogFragment.this._listener.onPasswordChanged((String)object, string, string2);
                }
            }
        });
        bundle.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                EditPasswordDialogFragment.this.dismiss();
            }
        });
    }

    public void setErrors(List<LoadFieldError> list) {
        this._confirmationFailedView.setErrors(list);
    }

    public void setOnPasswordChangeListener(OnPasswordChangedListener onPasswordChangedListener) {
        this._listener = onPasswordChangedListener;
    }

    public static interface OnPasswordChangedListener {
        public void onPasswordChanged(String var1, String var2, String var3);
    }

}
