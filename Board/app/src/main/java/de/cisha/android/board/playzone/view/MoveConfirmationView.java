// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.view;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import de.cisha.chess.model.Move;
import android.widget.TextView;
import android.widget.LinearLayout;

public class MoveConfirmationView extends LinearLayout
{
    private MoveConfirmationViewDelegate _delegate;
    private TextView _headerText;
    private Move _move;
    
    public MoveConfirmationView(final Context context) {
        super(context);
        this.setupView();
    }
    
    public MoveConfirmationView(final Context context, final AttributeSet set) {
        super(context, set);
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
        inflate(this.getContext(), 2131427461, (ViewGroup)this);
        this._headerText = (TextView)this.findViewById(2131296620);
        final View viewById = this.findViewById(2131296619);
        final View viewById2 = this.findViewById(2131296618);
        viewById.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                MoveConfirmationView.this.confirmMovePressed();
            }
        });
        viewById2.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                MoveConfirmationView.this.cancelMovePressed();
            }
        });
    }
    
    public void setMoveConfirmationDelegate(final MoveConfirmationViewDelegate delegate) {
        this._delegate = delegate;
    }
    
    public void setMoveToShow(final Move move) {
        String fan = "";
        this._move = move;
        if (move != null) {
            fan = move.getFAN();
        }
        this._headerText.setText((CharSequence)this.getContext().getResources().getString(2131690076, new Object[] { fan }));
    }
    
    public interface MoveConfirmationViewDelegate
    {
        void cancelMove(final Move p0);
        
        void confirmMove(final Move p0);
    }
}
