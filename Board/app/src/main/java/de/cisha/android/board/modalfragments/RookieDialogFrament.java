// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.modalfragments;

import java.util.Collection;
import java.util.Collections;
import de.cisha.android.board.registration.LoginActivity;
import android.content.Intent;
import java.util.LinkedList;
import java.util.List;
import android.view.View;
import android.content.Context;
import de.cisha.android.ui.patterns.buttons.CustomButtonNeutral;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import java.util.TreeSet;
import android.view.View.OnClickListener;
import java.util.Set;
import de.cisha.android.ui.patterns.dialogs.RookieInfoDialogFragment;

public class RookieDialogFrament extends RookieInfoDialogFragment
{
    private Set<RookieButtonType> _buttons;
    private View.OnClickListener _cancelClickListener;
    private View.OnClickListener _reloadClickListener;
    
    public RookieDialogFrament() {
        this._buttons = new TreeSet<RookieButtonType>();
    }
    
    protected CustomButton createCancelButton() {
        final CustomButtonNeutral customButtonNeutral = new CustomButtonNeutral((Context)this.getActivity());
        customButtonNeutral.setText(2131689944);
        customButtonNeutral.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
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
        final CustomButtonNeutral customButtonNeutral = new CustomButtonNeutral((Context)this.getActivity());
        customButtonNeutral.setText(2131689946);
        customButtonNeutral.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                RookieDialogFrament.this.dismiss();
            }
        });
        return customButtonNeutral;
    }
    
    @Override
    protected List<CustomButton> getButtons() {
        final LinkedList<CustomButtonNeutral> list = new LinkedList<CustomButtonNeutral>();
        if (this._buttons.contains(RookieButtonType.CANCEL)) {
            list.add((CustomButtonNeutral)this.createCancelButton());
        }
        if (this._buttons.contains(RookieButtonType.RELOAD)) {
            final CustomButtonNeutral customButtonNeutral = new CustomButtonNeutral((Context)this.getActivity());
            customButtonNeutral.setText(2131689947);
            if (customButtonNeutral != null) {
                customButtonNeutral.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                    public void onClick(final View view) {
                        if (RookieDialogFrament.this._reloadClickListener != null) {
                            RookieDialogFrament.this._reloadClickListener.onClick(view);
                            RookieDialogFrament.this._reloadClickListener = null;
                        }
                    }
                });
            }
            list.add(customButtonNeutral);
        }
        if (this._buttons.contains(RookieButtonType.SETTINGS)) {
            final CustomButtonNeutral customButtonNeutral2 = new CustomButtonNeutral((Context)this.getActivity());
            customButtonNeutral2.setText(2131689948);
            customButtonNeutral2.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    RookieDialogFrament.this.startActivity(new Intent("android.settings.WIRELESS_SETTINGS"));
                }
            });
            list.add(customButtonNeutral2);
        }
        if (this._buttons.contains(RookieButtonType.LOGIN)) {
            final CustomButtonNeutral customButtonNeutral3 = new CustomButtonNeutral((Context)this.getActivity());
            customButtonNeutral3.setText(2131689945);
            customButtonNeutral3.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    RookieDialogFrament.this.dismiss();
                    RookieDialogFrament.this.startActivity(new Intent((Context)RookieDialogFrament.this.getActivity(), (Class)LoginActivity.class));
                }
            });
            list.add(customButtonNeutral3);
        }
        return (List<CustomButton>)list;
    }
    
    public void setButtonTypes(final RookieButtonType... array) {
        this._buttons.clear();
        Collections.addAll(this._buttons, array);
    }
    
    public void setOnCancelButtonClickListener(final View.OnClickListener cancelClickListener) {
        this._cancelClickListener = cancelClickListener;
    }
    
    public void setOnReloadButtonClickListener(final View.OnClickListener reloadClickListener) {
        this._reloadClickListener = reloadClickListener;
    }
    
    public enum RookieButtonType
    {
        CANCEL, 
        LOGIN, 
        RELOAD, 
        SETTINGS;
    }
}
