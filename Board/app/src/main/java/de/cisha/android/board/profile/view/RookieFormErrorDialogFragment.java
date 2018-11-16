// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile.view;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import de.cisha.android.ui.patterns.buttons.CustomButtonNeutral;
import java.util.LinkedList;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import de.cisha.android.ui.patterns.dialogs.RookieInfoDialogFragment;

public class RookieFormErrorDialogFragment extends RookieInfoDialogFragment
{
    private FormErrorView _errorView;
    private List<LoadFieldError> _errors;
    
    public RookieFormErrorDialogFragment() {
        this.setCancelable(true);
    }
    
    @Override
    protected List<CustomButton> getButtons() {
        final LinkedList<CustomButtonNeutral> list = (LinkedList<CustomButtonNeutral>)new LinkedList<CustomButton>();
        final CustomButtonNeutral customButtonNeutral = new CustomButtonNeutral((Context)this.getActivity());
        customButtonNeutral.setText(2131689601);
        customButtonNeutral.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                RookieFormErrorDialogFragment.this.dismiss();
            }
        });
        list.add(customButtonNeutral);
        return (List<CustomButton>)list;
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }
    
    @Override
    public void onViewCreated(final View view, final Bundle bundle) {
        super.onViewCreated(view, bundle);
        this._errorView = new FormErrorView((Context)this.getActivity());
        this.getContentContainerView().addView((View)this._errorView, -2, -2);
        this.setErrors(this._errors);
        this.setTitle(this.getActivity().getString(2131689974));
    }
    
    public void setErrors(final List<LoadFieldError> errors) {
        this._errors = errors;
        if (this._errorView != null) {
            this._errorView.setErrors(this._errors);
        }
    }
}
