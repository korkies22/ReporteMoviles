/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package de.cisha.android.board.playzone.remote.view.aftergame;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import de.cisha.android.ui.patterns.dialogs.ConfirmCallback;
import de.cisha.android.ui.patterns.dialogs.SimpleDialogView;

public class RematchGameDialog
extends SimpleDialogView {
    private ConfirmCallback _callback;
    private CustomButton _noButton;
    private String _opponentName;
    private TextView _text;
    private CustomButton _yesButton;

    public RematchGameDialog(Context context, ConfirmCallback confirmCallback, boolean bl, String string) {
        super(context);
        this._callback = confirmCallback;
        this._opponentName = string;
        this.setTitle("Rematch");
        if (bl) {
            this.showLoadingView();
            this._callback.confirmed();
            return;
        }
        this.showConfirmView();
    }

    public void showConfirmView() {
        this.getContentViewGroup().removeAllViews();
        RematchGameDialog.inflate((Context)this.getContext(), (int)2131427487, (ViewGroup)this.getContentViewGroup());
        this._text = (TextView)this.getContentViewGroup().findViewById(2131296686);
        this._text.setText((CharSequence)this.getResources().getString(2131690149, new Object[]{this._opponentName}));
        this._yesButton = (CustomButton)this.getContentViewGroup().findViewById(2131296685);
        this._yesButton.setBackgroundResource(2131231105);
        this._yesButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                RematchGameDialog.this.showLoadingView();
                RematchGameDialog.this._callback.confirmed();
            }
        });
        this._noButton = (CustomButton)this.getContentViewGroup().findViewById(2131296684);
        this._noButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                RematchGameDialog.this._callback.canceled();
            }
        });
    }

    public void showLoadingView() {
        this.getContentViewGroup().removeAllViews();
        RematchGameDialog.inflate((Context)this.getContext(), (int)2131427489, (ViewGroup)this.getContentViewGroup());
        this.findViewById(2131296683).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                RematchGameDialog.this._callback.canceled();
            }
        });
    }

    public void showRematchDeclinedView() {
        this.getContentViewGroup().removeAllViews();
        RematchGameDialog.inflate((Context)this.getContext(), (int)2131427488, (ViewGroup)this.getContentViewGroup());
        ((TextView)this.findViewById(2131296687)).setText((CharSequence)this.getResources().getString(2131690145, new Object[]{this._opponentName}));
    }

}
