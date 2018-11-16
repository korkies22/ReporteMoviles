// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.remote.view.aftergame;

import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.widget.TextView;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import de.cisha.android.ui.patterns.dialogs.ConfirmCallback;
import de.cisha.android.ui.patterns.dialogs.SimpleDialogView;

public class RematchGameDialog extends SimpleDialogView
{
    private ConfirmCallback _callback;
    private CustomButton _noButton;
    private String _opponentName;
    private TextView _text;
    private CustomButton _yesButton;
    
    public RematchGameDialog(final Context context, final ConfirmCallback callback, final boolean b, final String opponentName) {
        super(context);
        this._callback = callback;
        this._opponentName = opponentName;
        this.setTitle("Rematch");
        if (b) {
            this.showLoadingView();
            this._callback.confirmed();
            return;
        }
        this.showConfirmView();
    }
    
    public void showConfirmView() {
        this.getContentViewGroup().removeAllViews();
        inflate(this.getContext(), 2131427487, this.getContentViewGroup());
        (this._text = (TextView)this.getContentViewGroup().findViewById(2131296686)).setText((CharSequence)this.getResources().getString(2131690149, new Object[] { this._opponentName }));
        (this._yesButton = (CustomButton)this.getContentViewGroup().findViewById(2131296685)).setBackgroundResource(2131231105);
        this._yesButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                RematchGameDialog.this.showLoadingView();
                RematchGameDialog.this._callback.confirmed();
            }
        });
        (this._noButton = (CustomButton)this.getContentViewGroup().findViewById(2131296684)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                RematchGameDialog.this._callback.canceled();
            }
        });
    }
    
    public void showLoadingView() {
        this.getContentViewGroup().removeAllViews();
        inflate(this.getContext(), 2131427489, this.getContentViewGroup());
        this.findViewById(2131296683).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                RematchGameDialog.this._callback.canceled();
            }
        });
    }
    
    public void showRematchDeclinedView() {
        this.getContentViewGroup().removeAllViews();
        inflate(this.getContext(), 2131427488, this.getContentViewGroup());
        ((TextView)this.findViewById(2131296687)).setText((CharSequence)this.getResources().getString(2131690145, new Object[] { this._opponentName }));
    }
}
