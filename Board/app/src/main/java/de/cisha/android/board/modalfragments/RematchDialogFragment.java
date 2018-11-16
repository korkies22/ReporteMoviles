// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.modalfragments;

import de.cisha.android.board.playzone.view.PlayzoneResultView;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import java.util.Collection;
import android.view.View;
import android.content.Context;
import java.util.LinkedList;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import java.util.List;
import android.view.View.OnClickListener;
import de.cisha.android.ui.patterns.buttons.CustomButtonPositive;
import de.cisha.android.board.playzone.model.AfterGameInformation;
import de.cisha.android.board.ModelHolder;
import de.cisha.android.ui.patterns.dialogs.SimpleOkDialogFragment;

public class RematchDialogFragment extends SimpleOkDialogFragment
{
    private ModelHolder<AfterGameInformation> _afterGameInformationHolder;
    private CustomButtonPositive _rematchButton;
    private View.OnClickListener _rematchClicklistener;
    private boolean _waitForResultView;
    
    public static RematchDialogFragment createInstance(final ModelHolder<AfterGameInformation> afterGameInformationHolder, final boolean waitForResultView) {
        final RematchDialogFragment rematchDialogFragment = new RematchDialogFragment();
        rematchDialogFragment._afterGameInformationHolder = afterGameInformationHolder;
        rematchDialogFragment._waitForResultView = waitForResultView;
        return rematchDialogFragment;
    }
    
    public void deactivateRematchButton() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
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
        final LinkedList<Object> list = (LinkedList<Object>)new LinkedList<CustomButton>();
        (this._rematchButton = new CustomButtonPositive((Context)this.getActivity())).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                if (RematchDialogFragment.this._rematchClicklistener != null) {
                    RematchDialogFragment.this._rematchClicklistener.onClick(view);
                }
                RematchDialogFragment.this.dismiss();
            }
        });
        this._rematchButton.setText(2131690116);
        list.add(this._rematchButton);
        list.addAll(super.getButtons());
        return (List<CustomButton>)list;
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        if (this._afterGameInformationHolder != null) {
            final PlayzoneResultView playzoneResultView = new PlayzoneResultView((Context)this.getActivity());
            playzoneResultView.setWaitForResultLoaderEnabled(this._waitForResultView);
            playzoneResultView.setAfterGameInformationHolder(this._afterGameInformationHolder);
            this.getContentContainerView().addView((View)playzoneResultView);
        }
        return onCreateView;
    }
    
    public void setOnRematchButtonClickListener(final View.OnClickListener rematchClicklistener) {
        this._rematchClicklistener = rematchClicklistener;
    }
}
