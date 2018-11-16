// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile;

import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;
import de.cisha.android.ui.patterns.input.CustomEditPassword;
import de.cisha.android.board.profile.view.FormErrorView;
import de.cisha.android.ui.patterns.dialogs.EmptyDialogFragment;

public class EditPasswordDialogFragment extends EmptyDialogFragment
{
    private FormErrorView _confirmationFailedView;
    private CustomEditPassword _editPasswordCurrent;
    private CustomEditPassword _editPasswordNew;
    private CustomEditPassword _editPasswordRepeat;
    private OnPasswordChangedListener _listener;
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setTitle(this.getActivity().getString(2131689604));
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, ViewGroup viewGroup, final Bundle bundle) {
        viewGroup = (ViewGroup)super.onCreateView(layoutInflater, viewGroup, bundle);
        layoutInflater.inflate(2131427399, viewGroup);
        return (View)viewGroup;
    }
    
    @Override
    public void onViewCreated(final View view, final Bundle bundle) {
        super.onViewCreated(view, bundle);
        final View viewById = view.findViewById(2131296485);
        final View viewById2 = view.findViewById(2131296487);
        this._editPasswordCurrent = (CustomEditPassword)view.findViewById(2131296429);
        this._editPasswordNew = (CustomEditPassword)view.findViewById(2131296430);
        this._editPasswordRepeat = (CustomEditPassword)view.findViewById(2131296431);
        this._confirmationFailedView = (FormErrorView)view.findViewById(2131296428);
        viewById2.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                final String string = EditPasswordDialogFragment.this._editPasswordCurrent.getEditText().getText().toString();
                final String string2 = EditPasswordDialogFragment.this._editPasswordNew.getEditText().getText().toString();
                final String string3 = EditPasswordDialogFragment.this._editPasswordRepeat.getEditText().getText().toString();
                if (EditPasswordDialogFragment.this._listener != null) {
                    EditPasswordDialogFragment.this._listener.onPasswordChanged(string, string2, string3);
                }
            }
        });
        viewById.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                EditPasswordDialogFragment.this.dismiss();
            }
        });
    }
    
    public void setErrors(final List<LoadFieldError> errors) {
        this._confirmationFailedView.setErrors(errors);
    }
    
    public void setOnPasswordChangeListener(final OnPasswordChangedListener listener) {
        this._listener = listener;
    }
    
    public interface OnPasswordChangedListener
    {
        void onPasswordChanged(final String p0, final String p1, final String p2);
    }
}
