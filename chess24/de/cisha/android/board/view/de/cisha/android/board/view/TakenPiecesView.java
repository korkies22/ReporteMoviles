/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.chess.model.ChessVariant;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.PieceInformation;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.PositionObserver;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TakenPiecesView
extends LinearLayout
implements PositionObserver {
    private ChessVariant _chessVariant;
    private boolean _colorWhite;
    private Position _currentPosition;
    private TextView _text;

    public TakenPiecesView(Context context) {
        super(context);
        this.init();
    }

    public TakenPiecesView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private int getTakenPeacesInPositionForPeace(Piece piece, Map<String, Integer> map) {
        int n = this._chessVariant.getNumberForPieceType(piece);
        int n2 = map.containsKey(piece.getPieceFigurineNotation()) ? map.get(piece.getPieceFigurineNotation()) : 0;
        return n - n2;
    }

    private void init() {
        this.setOrientation(0);
        TakenPiecesView.inflate((Context)this.getContext(), (int)2131427379, (ViewGroup)this);
        this._text = (TextView)this.findViewById(2131296345);
        this._currentPosition = new Position(FEN.STARTING_POSITION);
        this._colorWhite = true;
        this._chessVariant = ChessVariant.CLASSIC_CHESS;
        this.updateText();
    }

    private void setTextForPieces(Map<String, Integer> map) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Piece piece : this._chessVariant.getAllPieceTypes()) {
            int n;
            if (piece.getColor() != this._colorWhite || (n = this.getTakenPeacesInPositionForPeace(piece, map)) == 0) continue;
            stringBuilder.append(piece.getPieceUnicodeChar());
            stringBuilder.append(n);
            stringBuilder.append(" ");
        }
        this._text.setText((CharSequence)stringBuilder.toString());
    }

    private void updateText() {
        Object object = this._currentPosition.getAllPieces();
        TreeMap<String, Integer> treeMap = new TreeMap<String, Integer>();
        object = object.iterator();
        while (object.hasNext()) {
            Object object2 = ((PieceInformation)object.next()).getPiece();
            if (object2.getColor() != this._colorWhite) continue;
            if (!treeMap.containsKey(object2 = object2.getPieceFigurineNotation())) {
                treeMap.put((String)object2, 0);
            }
            treeMap.put((String)object2, (Integer)treeMap.get(object2) + 1);
        }
        this.setTextForPieces(treeMap);
    }

    @Override
    public void positionChanged(Position position, Move move) {
        this._currentPosition = position;
        this.post(new Runnable(){

            @Override
            public void run() {
                TakenPiecesView.this.updateText();
            }
        });
    }

    public void setColor(boolean bl) {
        this._colorWhite = bl;
        this.updateText();
    }

}
