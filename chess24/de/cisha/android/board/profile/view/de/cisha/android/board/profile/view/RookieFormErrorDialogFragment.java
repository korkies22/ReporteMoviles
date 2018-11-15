/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 */
package de.cisha.android.board.profile.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.board.profile.view.FormErrorView;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import de.cisha.android.ui.patterns.buttons.CustomButtonNeutral;
import de.cisha.android.ui.patterns.dialogs.RookieInfoDialogFragment;
import java.util.LinkedList;
import java.util.List;

public class RookieFormErrorDialogFragment
extends RookieInfoDialogFragment {
    private FormErrorView _errorView;
    private List<LoadFieldError> _errors;

    public RookieFormErrorDialogFragment() {
        this.setCancelable(true);
    }

    @Override
    protected List<CustomButton> getButtons() {
        LinkedList<CustomButton> linkedList = new LinkedList<CustomButton>();
        CustomButtonNeutral customButtonNeutral = new CustomButtonNeutral((Context)this.getActivity());
        customButtonNeutral.setText(2131689601);
        customButtonNeutral.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                RookieFormErrorDialogFragment.this.dismiss();
            }
        });
        linkedList.add(customButtonNeutral);
        return linkedList;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this._errorView = new FormErrorView((Context)this.getActivity());
        this.getContentContainerView().addView((View)this._errorView, -2, -2);
        this.setErrors(this._errors);
        this.setTitle(this.getActivity().getString(2131689974));
    }

    public void setErrors(List<LoadFieldError> list) {
        this._errors = list;
        if (this._errorView != null) {
            this._errorView.setErrors(this._errors);
        }
    }

}
