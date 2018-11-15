/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.animation.Animation
 *  android.view.animation.ScaleAnimation
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 */
package de.cisha.android.board.setup.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.SettingsService;
import de.cisha.chess.model.Piece;

public class PieceView
extends ImageView {
    private Piece _piece;

    public PieceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
        if (this.isInEditMode()) {
            this.setImageDrawable(this.getResources().getDrawable(2131231786));
        }
    }

    public PieceView(Context context, Piece piece) {
        super(context);
        this.init();
        this.setPiece(piece);
    }

    private static Drawable getDrawableForPiece(Piece piece, Context context) {
        int n = ServiceProvider.getInstance().getSettingsService().getDrawableIdForPiece(piece);
        if (n != -1) {
            return context.getResources().getDrawable(n);
        }
        return null;
    }

    private void init() {
        this.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }

    public Piece getPiece() {
        return this._piece;
    }

    public void setPiece(Piece piece) {
        this._piece = piece;
        this.setImageDrawable(PieceView.getDrawableForPiece(piece, this.getContext()));
    }

    public void setSelected(boolean bl) {
        boolean bl2 = this.isSelected();
        super.setSelected(bl);
        if (bl2 != bl) {
            float f = bl ? 1.0f : 1.5f;
            float f2 = bl ? 1.5f : 1.0f;
            ScaleAnimation scaleAnimation = new ScaleAnimation(f, f2, f, f2, 1, 0.5f, 1, 0.5f);
            scaleAnimation.setFillEnabled(true);
            scaleAnimation.setDuration(300L);
            scaleAnimation.setFillAfter(true);
            this.startAnimation((Animation)scaleAnimation);
        }
    }
}
