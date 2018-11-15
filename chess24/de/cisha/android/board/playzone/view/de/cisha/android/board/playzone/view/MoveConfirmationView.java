/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.playzone.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.chess.model.Move;

public class MoveConfirmationView
extends LinearLayout {
    private MoveConfirmationViewDelegate _delegate;
    private TextView _headerText;
    private Move _move;

    public MoveConfirmationView(Context context) {
        super(context);
        this.setupView();
    }

    public MoveConfirmationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setupView();
    }

    private void cancelMovePressed() {
        if (this._delegate != null) {
            this._delegate.cancelMove(this._move);
        }
    }

    private void confirmMovePressed() {
        if (this._delegate != null) {
            this._delegate.confirmMove(this._move);
        }
    }

    private void setupView() {
        this.setOrientation(1);
        this.setBackgroundResource(2131231526);
        MoveConfirmationView.inflate((Context)this.getContext(), (int)2131427461, (ViewGroup)this);
        this._headerText = (TextView)this.findViewById(2131296620);
        View view = this.findViewById(2131296619);
        View view2 = this.findViewById(2131296618);
        view.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MoveConfirmationView.this.confirmMovePressed();
            }
        });
        view2.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MoveConfirmationView.this.cancelMovePressed();
            }
        });
    }

    public void setMoveConfirmationDelegate(MoveConfirmationViewDelegate moveConfirmationViewDelegate) {
        this._delegate = moveConfirmationViewDelegate;
    }

    public void setMoveToShow(Move object) {
        String string = "";
        this._move = object;
        if (object != null) {
            string = object.getFAN();
        }
        object = this.getContext().getResources().getString(2131690076, new Object[]{string});
        this._headerText.setText((CharSequence)object);
    }

    public static interface MoveConfirmationViewDelegate {
        public void cancelMove(Move var1);

        public void confirmMove(Move var1);
    }

}
