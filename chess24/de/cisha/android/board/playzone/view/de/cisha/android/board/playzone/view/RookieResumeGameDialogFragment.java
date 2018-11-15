/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.playzone.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import de.cisha.android.ui.patterns.buttons.CustomButtonNeutral;
import de.cisha.android.ui.patterns.buttons.CustomButtonPositive;
import de.cisha.android.ui.patterns.dialogs.RookieInfoDialogFragment;
import java.util.LinkedList;
import java.util.List;

public class RookieResumeGameDialogFragment
extends RookieInfoDialogFragment {
    private ResumeGameDialogListener _resumeGameDialogListener;

    public RookieResumeGameDialogFragment() {
        this.setCancelable(true);
    }

    private void notifyListenerDiscard() {
        if (this._resumeGameDialogListener != null) {
            this._resumeGameDialogListener.onDiscardClicked();
        }
    }

    private void notifyListenerResume() {
        if (this._resumeGameDialogListener != null) {
            this._resumeGameDialogListener.onResumeClicked();
        }
    }

    @Override
    protected List<CustomButton> getButtons() {
        LinkedList<CustomButton> linkedList = new LinkedList<CustomButton>();
        CustomButton customButton = new CustomButtonNeutral((Context)this.getActivity());
        customButton.setText(2131689952);
        customButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                RookieResumeGameDialogFragment.this.notifyListenerDiscard();
                RookieResumeGameDialogFragment.this.dismiss();
            }
        });
        linkedList.add(customButton);
        customButton = new CustomButtonPositive((Context)this.getActivity());
        customButton.setText(2131690272);
        customButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                RookieResumeGameDialogFragment.this.notifyListenerResume();
                RookieResumeGameDialogFragment.this.dismiss();
            }
        });
        linkedList.add(customButton);
        return linkedList;
    }

    @Override
    protected RookieInfoDialogFragment.RookieType getRookieType() {
        return RookieInfoDialogFragment.RookieType.RUNNING_ENGINE;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setTitle(this.getString(2131690276));
    }

    public void setResumeGameDialogListener(ResumeGameDialogListener resumeGameDialogListener) {
        this._resumeGameDialogListener = resumeGameDialogListener;
    }

    public static interface ResumeGameDialogListener {
        public void onDiscardClicked();

        public void onResumeClicked();
    }

}
