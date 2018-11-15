/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.animation.AlphaAnimation
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.widget.FrameLayout
 *  android.widget.ViewSwitcher
 */
package de.cisha.android.board.setup.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ViewSwitcher;
import de.cisha.android.board.setup.view.PieceView;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.pieces.Bishop;
import de.cisha.chess.model.pieces.King;
import de.cisha.chess.model.pieces.Knight;
import de.cisha.chess.model.pieces.Pawn;
import de.cisha.chess.model.pieces.Queen;
import de.cisha.chess.model.pieces.Rook;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class PieceBar
extends FrameLayout {
    private boolean _actualColor;
    private boolean _color = true;
    private View _deletePieceView;
    private Map<Class<? extends Piece>, View> _mapPieceView;
    private HashMap<Class<? extends Piece>, View> _mapPieceView2;
    private View.OnClickListener _onArrowClickListener;
    private View _overlayDelete;
    private View _piecesFirstSet;
    private View _piecesSecondSet;
    private ViewSwitcher _switch;

    public PieceBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    public PieceBar(Context context, boolean bl) {
        super(context);
        this._color = bl;
        this.init();
    }

    private void init() {
        PieceBar.inflate((Context)this.getContext(), (int)2131427543, (ViewGroup)this);
        if (this.isInEditMode()) {
            this.findViewById(2131297000).setVisibility(8);
            return;
        }
        this._piecesFirstSet = this.findViewById(2131297002);
        this._mapPieceView = new HashMap<Class<? extends Piece>, View>();
        this._mapPieceView2 = new HashMap();
        this._overlayDelete = this.findViewById(2131297000);
        this._overlayDelete.setVisibility(8);
        this._deletePieceView = this.findViewById(2131296996);
        this._switch = (ViewSwitcher)this.findViewById(2131297005);
        this.setPieceColor(true, this._color);
        this._actualColor = this._color;
        this._onArrowClickListener = new View.OnClickListener(){

            public void onClick(View view) {
                PieceBar.this.toggleColor();
            }
        };
        this._piecesFirstSet.findViewById(2131296993).setOnClickListener(this._onArrowClickListener);
    }

    private void initPieceView(boolean bl, Piece piece, int n) {
        Object object = bl ? this._piecesFirstSet : this._piecesSecondSet;
        PieceView pieceView = (PieceView)object.findViewById(n);
        pieceView.setPiece(piece);
        object = bl ? this._mapPieceView : this._mapPieceView2;
        object.put(piece.getClass(), pieceView);
    }

    private void toggleColor() {
        ViewSwitcher viewSwitcher = this._switch;
        Context context = this.getContext();
        int n = this._actualColor ? 2130771994 : 2130771991;
        viewSwitcher.setOutAnimation(context, n);
        viewSwitcher = this._switch;
        context = this.getContext();
        n = this._actualColor ? 2130771993 : 2130771990;
        viewSwitcher.setInAnimation(context, n);
        this._switch.showNext();
        this._actualColor ^= true;
    }

    public void enableDeleteMode(boolean bl) {
        if (bl) {
            this.selectPiece(null);
        }
        this._deletePieceView.setSelected(bl);
    }

    public Collection<View> getAllPieceViews() {
        LinkedList<View> linkedList = new LinkedList<View>(this._mapPieceView.values());
        linkedList.addAll(this._mapPieceView2.values());
        return linkedList;
    }

    public void hideTrash() {
        this._deletePieceView.setVisibility(4);
    }

    public void selectPiece(Piece piece) {
        this._deletePieceView.setSelected(false);
        Object object = new LinkedList<View>(this._mapPieceView.values());
        object.addAll(this._mapPieceView2.values());
        object = object.iterator();
        while (object.hasNext()) {
            View view = (View)object.next();
            if (!(view instanceof PieceView)) continue;
            view.setSelected(((PieceView)view).getPiece().equals(piece));
        }
    }

    public void setMultipleColorEnabled(boolean bl) {
        if (bl && this._switch.getChildCount() == 1) {
            this._piecesSecondSet = LayoutInflater.from((Context)this.getContext()).inflate(2131427544, (ViewGroup)this._switch, false);
            this._switch.addView(this._piecesSecondSet);
            this.setPieceColor(false, this._color ^ true);
            this._piecesFirstSet.findViewById(2131296993).setVisibility(0);
            this._piecesSecondSet.findViewById(2131296992).setVisibility(0);
            this._piecesSecondSet.findViewById(2131296992).setOnClickListener(this._onArrowClickListener);
            return;
        }
        if (!bl && this._switch.getChildCount() == 2) {
            this._switch.removeViewAt(1);
            this._mapPieceView2.clear();
            this._piecesFirstSet.findViewById(2131296993).setVisibility(8);
            this._piecesSecondSet.findViewById(2131296992).setVisibility(8);
        }
    }

    public void setPieceColor(boolean bl, boolean bl2) {
        this.initPieceView(bl, King.instanceForColor(bl2), 2131296998);
        this.initPieceView(bl, Queen.instanceForColor(bl2), 2131297003);
        this.initPieceView(bl, Rook.instanceForColor(bl2), 2131297004);
        this.initPieceView(bl, Knight.instanceForColor(bl2), 2131296999);
        this.initPieceView(bl, Bishop.instanceForColor(bl2), 2131296994);
        this.initPieceView(bl, Pawn.instanceForColor(bl2), 2131297001);
    }

    public void showDragDeleteHint(final boolean bl) {
        this._overlayDelete.clearAnimation();
        if (bl || this._overlayDelete.getVisibility() == 0) {
            float f = 0.7f;
            float f2 = bl ? 0.0f : 0.7f;
            if (!bl) {
                f = 0.0f;
            }
            AlphaAnimation alphaAnimation = new AlphaAnimation(f2, f);
            long l = bl ? 600L : 200L;
            alphaAnimation.setDuration(l);
            alphaAnimation.setFillAfter(true);
            alphaAnimation.setAnimationListener(new Animation.AnimationListener(){

                public void onAnimationEnd(Animation animation) {
                    PieceBar.this.post(new Runnable(){

                        @Override
                        public void run() {
                            if (!bl) {
                                PieceBar.this._overlayDelete.setVisibility(4);
                                PieceBar.this._overlayDelete.clearAnimation();
                            }
                        }
                    });
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                }

            });
            this._overlayDelete.setVisibility(0);
            this._overlayDelete.startAnimation((Animation)alphaAnimation);
        }
    }

}
