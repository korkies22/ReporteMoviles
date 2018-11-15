/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.modalfragments;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import de.cisha.android.board.registration.LoginActivity;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import de.cisha.android.ui.patterns.buttons.CustomButtonNeutral;
import de.cisha.android.ui.patterns.dialogs.RookieInfoDialogFragment;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class RookieDialogFrament
extends RookieInfoDialogFragment {
    private Set<RookieButtonType> _buttons = new TreeSet<RookieButtonType>();
    private View.OnClickListener _cancelClickListener;
    private View.OnClickListener _reloadClickListener;

    protected CustomButton createCancelButton() {
        CustomButtonNeutral customButtonNeutral = new CustomButtonNeutral((Context)this.getActivity());
        customButtonNeutral.setText(2131689944);
        customButtonNeutral.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                RookieDialogFrament.this.dismiss();
                if (RookieDialogFrament.this._cancelClickListener != null) {
                    RookieDialogFrament.this._cancelClickListener.onClick(view);
                    RookieDialogFrament.this._cancelClickListener = null;
                }
            }
        });
        return customButtonNeutral;
    }

    protected CustomButton createCloseButton() {
        CustomButtonNeutral customButtonNeutral = new CustomButtonNeutral((Context)this.getActivity());
        customButtonNeutral.setText(2131689946);
        customButtonNeutral.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                RookieDialogFrament.this.dismiss();
            }
        });
        return customButtonNeutral;
    }

    @Override
    protected List<CustomButton> getButtons() {
        CustomButtonNeutral customButtonNeutral;
        LinkedList<CustomButton> linkedList = new LinkedList<CustomButton>();
        if (this._buttons.contains((Object)RookieButtonType.CANCEL)) {
            linkedList.add(this.createCancelButton());
        }
        if (this._buttons.contains((Object)RookieButtonType.RELOAD)) {
            customButtonNeutral = new CustomButtonNeutral((Context)this.getActivity());
            customButtonNeutral.setText(2131689947);
            if (customButtonNeutral != null) {
                customButtonNeutral.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View view) {
                        if (RookieDialogFrament.this._reloadClickListener != null) {
                            RookieDialogFrament.this._reloadClickListener.onClick(view);
                            RookieDialogFrament.this._reloadClickListener = null;
                        }
                    }
                });
            }
            linkedList.add(customButtonNeutral);
        }
        if (this._buttons.contains((Object)RookieButtonType.SETTINGS)) {
            customButtonNeutral = new CustomButtonNeutral((Context)this.getActivity());
            customButtonNeutral.setText(2131689948);
            customButtonNeutral.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    RookieDialogFrament.this.startActivity(new Intent("android.settings.WIRELESS_SETTINGS"));
                }
            });
            linkedList.add(customButtonNeutral);
        }
        if (this._buttons.contains((Object)RookieButtonType.LOGIN)) {
            customButtonNeutral = new CustomButtonNeutral((Context)this.getActivity());
            customButtonNeutral.setText(2131689945);
            customButtonNeutral.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    RookieDialogFrament.this.dismiss();
                    RookieDialogFrament.this.startActivity(new Intent((Context)RookieDialogFrament.this.getActivity(), LoginActivity.class));
                }
            });
            linkedList.add(customButtonNeutral);
        }
        return linkedList;
    }

    public /* varargs */ void setButtonTypes(RookieButtonType ... arrrookieButtonType) {
        this._buttons.clear();
        Collections.addAll(this._buttons, arrrookieButtonType);
    }

    public void setOnCancelButtonClickListener(View.OnClickListener onClickListener) {
        this._cancelClickListener = onClickListener;
    }

    public void setOnReloadButtonClickListener(View.OnClickListener onClickListener) {
        this._reloadClickListener = onClickListener;
    }

    public static enum RookieButtonType {
        CANCEL,
        RELOAD,
        SETTINGS,
        LOGIN;
        

        private RookieButtonType() {
        }
    }

}
