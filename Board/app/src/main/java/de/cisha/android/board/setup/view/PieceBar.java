// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.setup.view;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AlphaAnimation;
import de.cisha.chess.model.pieces.Pawn;
import de.cisha.chess.model.pieces.Bishop;
import de.cisha.chess.model.pieces.Knight;
import de.cisha.chess.model.pieces.Rook;
import de.cisha.chess.model.pieces.Queen;
import de.cisha.chess.model.pieces.King;
import android.view.LayoutInflater;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Collection;
import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.ViewSwitcher;
import android.view.View.OnClickListener;
import java.util.HashMap;
import de.cisha.chess.model.Piece;
import java.util.Map;
import android.view.View;
import android.widget.FrameLayout;

public class PieceBar extends FrameLayout
{
    private boolean _actualColor;
    private boolean _color;
    private View _deletePieceView;
    private Map<Class<? extends Piece>, View> _mapPieceView;
    private HashMap<Class<? extends Piece>, View> _mapPieceView2;
    private View.OnClickListener _onArrowClickListener;
    private View _overlayDelete;
    private View _piecesFirstSet;
    private View _piecesSecondSet;
    private ViewSwitcher _switch;
    
    public PieceBar(final Context context, final AttributeSet set) {
        super(context, set);
        this._color = true;
        this.init();
    }
    
    public PieceBar(final Context context, final boolean color) {
        super(context);
        this._color = true;
        this._color = color;
        this.init();
    }
    
    private void init() {
        inflate(this.getContext(), 2131427543, (ViewGroup)this);
        if (this.isInEditMode()) {
            this.findViewById(2131297000).setVisibility(8);
            return;
        }
        this._piecesFirstSet = this.findViewById(2131297002);
        this._mapPieceView = new HashMap<Class<? extends Piece>, View>();
        this._mapPieceView2 = new HashMap<Class<? extends Piece>, View>();
        (this._overlayDelete = this.findViewById(2131297000)).setVisibility(8);
        this._deletePieceView = this.findViewById(2131296996);
        this._switch = (ViewSwitcher)this.findViewById(2131297005);
        this.setPieceColor(true, this._color);
        this._actualColor = this._color;
        this._onArrowClickListener = (View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                PieceBar.this.toggleColor();
            }
        };
        this._piecesFirstSet.findViewById(2131296993).setOnClickListener(this._onArrowClickListener);
    }
    
    private void initPieceView(final boolean b, final Piece piece, final int n) {
        View view;
        if (b) {
            view = this._piecesFirstSet;
        }
        else {
            view = this._piecesSecondSet;
        }
        final PieceView pieceView = (PieceView)view.findViewById(n);
        pieceView.setPiece(piece);
        Map<Class<? extends Piece>, View> map;
        if (b) {
            map = this._mapPieceView;
        }
        else {
            map = this._mapPieceView2;
        }
        map.put(piece.getClass(), (View)pieceView);
    }
    
    private void toggleColor() {
        final ViewSwitcher switch1 = this._switch;
        final Context context = this.getContext();
        int n;
        if (this._actualColor) {
            n = 2130771994;
        }
        else {
            n = 2130771991;
        }
        switch1.setOutAnimation(context, n);
        final ViewSwitcher switch2 = this._switch;
        final Context context2 = this.getContext();
        int n2;
        if (this._actualColor) {
            n2 = 2130771993;
        }
        else {
            n2 = 2130771990;
        }
        switch2.setInAnimation(context2, n2);
        this._switch.showNext();
        this._actualColor ^= true;
    }
    
    public void enableDeleteMode(final boolean selected) {
        if (selected) {
            this.selectPiece(null);
        }
        this._deletePieceView.setSelected(selected);
    }
    
    public Collection<View> getAllPieceViews() {
        final LinkedList<Object> list = (LinkedList<Object>)new LinkedList<View>(this._mapPieceView.values());
        list.addAll(this._mapPieceView2.values());
        return (Collection<View>)list;
    }
    
    public void hideTrash() {
        this._deletePieceView.setVisibility(4);
    }
    
    public void selectPiece(final Piece piece) {
        this._deletePieceView.setSelected(false);
        final LinkedList<View> list = new LinkedList<View>(this._mapPieceView.values());
        list.addAll((Collection<?>)this._mapPieceView2.values());
        for (final View view : list) {
            if (view instanceof PieceView) {
                view.setSelected(((PieceView)view).getPiece().equals(piece));
            }
        }
    }
    
    public void setMultipleColorEnabled(final boolean b) {
        if (b && this._switch.getChildCount() == 1) {
            this._piecesSecondSet = LayoutInflater.from(this.getContext()).inflate(2131427544, (ViewGroup)this._switch, false);
            this._switch.addView(this._piecesSecondSet);
            this.setPieceColor(false, this._color ^ true);
            this._piecesFirstSet.findViewById(2131296993).setVisibility(0);
            this._piecesSecondSet.findViewById(2131296992).setVisibility(0);
            this._piecesSecondSet.findViewById(2131296992).setOnClickListener(this._onArrowClickListener);
            return;
        }
        if (!b && this._switch.getChildCount() == 2) {
            this._switch.removeViewAt(1);
            this._mapPieceView2.clear();
            this._piecesFirstSet.findViewById(2131296993).setVisibility(8);
            this._piecesSecondSet.findViewById(2131296992).setVisibility(8);
        }
    }
    
    public void setPieceColor(final boolean b, final boolean b2) {
        this.initPieceView(b, King.instanceForColor(b2), 2131296998);
        this.initPieceView(b, Queen.instanceForColor(b2), 2131297003);
        this.initPieceView(b, Rook.instanceForColor(b2), 2131297004);
        this.initPieceView(b, Knight.instanceForColor(b2), 2131296999);
        this.initPieceView(b, Bishop.instanceForColor(b2), 2131296994);
        this.initPieceView(b, Pawn.instanceForColor(b2), 2131297001);
    }
    
    public void showDragDeleteHint(final boolean b) {
        this._overlayDelete.clearAnimation();
        if (b || this._overlayDelete.getVisibility() == 0) {
            float n = 0.7f;
            float n2;
            if (b) {
                n2 = 0.0f;
            }
            else {
                n2 = 0.7f;
            }
            if (!b) {
                n = 0.0f;
            }
            final AlphaAnimation alphaAnimation = new AlphaAnimation(n2, n);
            long duration;
            if (b) {
                duration = 600L;
            }
            else {
                duration = 200L;
            }
            alphaAnimation.setDuration(duration);
            alphaAnimation.setFillAfter(true);
            alphaAnimation.setAnimationListener((Animation.AnimationListener)new Animation.AnimationListener() {
                public void onAnimationEnd(final Animation animation) {
                    PieceBar.this.post((Runnable)new Runnable() {
                        @Override
                        public void run() {
                            if (!b) {
                                PieceBar.this._overlayDelete.setVisibility(4);
                                PieceBar.this._overlayDelete.clearAnimation();
                            }
                        }
                    });
                }
                
                public void onAnimationRepeat(final Animation animation) {
                }
                
                public void onAnimationStart(final Animation animation) {
                }
            });
            this._overlayDelete.setVisibility(0);
            this._overlayDelete.startAnimation((Animation)alphaAnimation);
        }
    }
}
