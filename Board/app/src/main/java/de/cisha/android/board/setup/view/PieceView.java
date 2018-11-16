// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.setup.view;

import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView.ScaleType;
import de.cisha.android.board.service.ServiceProvider;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.content.Context;
import de.cisha.chess.model.Piece;
import android.widget.ImageView;

public class PieceView extends ImageView
{
    private Piece _piece;
    
    public PieceView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
        if (this.isInEditMode()) {
            this.setImageDrawable(this.getResources().getDrawable(2131231786));
        }
    }
    
    public PieceView(final Context context, final Piece piece) {
        super(context);
        this.init();
        this.setPiece(piece);
    }
    
    private static Drawable getDrawableForPiece(final Piece piece, final Context context) {
        final int drawableIdForPiece = ServiceProvider.getInstance().getSettingsService().getDrawableIdForPiece(piece);
        if (drawableIdForPiece != -1) {
            return context.getResources().getDrawable(drawableIdForPiece);
        }
        return null;
    }
    
    private void init() {
        this.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }
    
    public Piece getPiece() {
        return this._piece;
    }
    
    public void setPiece(final Piece piece) {
        this._piece = piece;
        this.setImageDrawable(getDrawableForPiece(piece, this.getContext()));
    }
    
    public void setSelected(final boolean selected) {
        final boolean selected2 = this.isSelected();
        super.setSelected(selected);
        if (selected2 != selected) {
            float n;
            if (selected) {
                n = 1.0f;
            }
            else {
                n = 1.5f;
            }
            float n2;
            if (selected) {
                n2 = 1.5f;
            }
            else {
                n2 = 1.0f;
            }
            final ScaleAnimation scaleAnimation = new ScaleAnimation(n, n2, n, n2, 1, 0.5f, 1, 0.5f);
            scaleAnimation.setFillEnabled(true);
            scaleAnimation.setDuration(300L);
            scaleAnimation.setFillAfter(true);
            this.startAnimation((Animation)scaleAnimation);
        }
    }
}
