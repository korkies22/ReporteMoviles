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
import de.cisha.android.board.profile.view.FormErrorView;
import de.cisha.android.ui.patterns.input.CustomEditText;
import de.cisha.android.ui.patterns.dialogs.EmptyDialogFragment;

public class EditEmailDialogFragment extends EmptyDialogFragment
{
    private CustomEditText _editEmailField;
    private CustomEditText _editPasswordField;
    private FormErrorView _errorView;
    private OnEmailChangedListener _listener;
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setTitle(this.getActivity().getString(2131689953));
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, ViewGroup viewGroup, final Bundle bundle) {
        viewGroup = (ViewGroup)super.onCreateView(layoutInflater, viewGroup, bundle);
        layoutInflater.inflate(2131427433, viewGroup);
        return (View)viewGroup;
    }
    
    @Override
    public void onViewCreated(final View view, final Bundle bundle) {
        super.onViewCreated(view, bundle);
        this._editEmailField = (CustomEditText)view.findViewById(2131296493);
        this._editPasswordField = (CustomEditText)view.findViewById(2131296494);
        this._errorView = (FormErrorView)view.findViewById(2131296492);
        view.findViewById(2131296485).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                EditEmailDialogFragment.this.dismiss();
            }
        });
        view.findViewById(2131296487).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                if (EditEmailDialogFragment.this._listener != null) {
                    EditEmailDialogFragment.this._listener.onEmailChanged(EditEmailDialogFragment.this._editEmailField.getEditText().getText().toString(), EditEmailDialogFragment.this._editPasswordField.getEditText().getText().toString());
                }
            }
        });
    }
    
    public void setErrors(final List<LoadFieldError> errors) {
        this._errorView.setErrors(errors);
    }
    
    public void setOnEmailChangedListener(final OnEmailChangedListener listener) {
        this._listener = listener;
    }
    
    public interface OnEmailChangedListener
    {
        void onEmailChanged(final String p0, final String p1);
    }
}
