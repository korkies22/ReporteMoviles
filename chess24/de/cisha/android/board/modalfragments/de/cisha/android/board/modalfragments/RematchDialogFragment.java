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
package de.cisha.android.board.modalfragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.board.ModelHolder;
import de.cisha.android.board.playzone.model.AfterGameInformation;
import de.cisha.android.board.playzone.view.PlayzoneResultView;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import de.cisha.android.ui.patterns.buttons.CustomButtonPositive;
import de.cisha.android.ui.patterns.dialogs.SimpleOkDialogFragment;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class RematchDialogFragment
extends SimpleOkDialogFragment {
    private ModelHolder<AfterGameInformation> _afterGameInformationHolder;
    private CustomButtonPositive _rematchButton;
    private View.OnClickListener _rematchClicklistener;
    private boolean _waitForResultView;

    public static RematchDialogFragment createInstance(ModelHolder<AfterGameInformation> modelHolder, boolean bl) {
        RematchDialogFragment rematchDialogFragment = new RematchDialogFragment();
        rematchDialogFragment._afterGameInformationHolder = modelHolder;
        rematchDialogFragment._waitForResultView = bl;
        return rematchDialogFragment;
    }

    public void deactivateRematchButton() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                if (RematchDialogFragment.this._rematchButton != null) {
                    RematchDialogFragment.this._rematchButton.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected List<CustomButton> getButtons() {
        LinkedList<CustomButton> linkedList = new LinkedList<CustomButton>();
        this._rematchButton = new CustomButtonPositive((Context)this.getActivity());
        this._rematchButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (RematchDialogFragment.this._rematchClicklistener != null) {
                    RematchDialogFragment.this._rematchClicklistener.onClick(view);
                }
                RematchDialogFragment.this.dismiss();
            }
        });
        this._rematchButton.setText(2131690116);
        linkedList.add(this._rematchButton);
        linkedList.addAll(super.getButtons());
        return linkedList;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup object, Bundle bundle) {
        layoutInflater = super.onCreateView(layoutInflater, (ViewGroup)object, bundle);
        if (this._afterGameInformationHolder != null) {
            object = new PlayzoneResultView((Context)this.getActivity());
            object.setWaitForResultLoaderEnabled(this._waitForResultView);
            object.setAfterGameInformationHolder(this._afterGameInformationHolder);
            this.getContentContainerView().addView((View)object);
        }
        return layoutInflater;
    }

    public void setOnRematchButtonClickListener(View.OnClickListener onClickListener) {
        this._rematchClicklistener = onClickListener;
    }

}
