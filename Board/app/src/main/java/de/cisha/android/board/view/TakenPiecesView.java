// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.view;

import de.cisha.chess.model.Move;
import java.util.Iterator;
import java.util.List;
import de.cisha.chess.model.PieceInformation;
import java.util.TreeMap;
import de.cisha.chess.model.FEN;
import android.view.ViewGroup;
import java.util.Map;
import de.cisha.chess.model.Piece;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.ChessVariant;
import de.cisha.chess.model.position.PositionObserver;
import android.widget.LinearLayout;

public class TakenPiecesView extends LinearLayout implements PositionObserver
{
    private ChessVariant _chessVariant;
    private boolean _colorWhite;
    private Position _currentPosition;
    private TextView _text;
    
    public TakenPiecesView(final Context context) {
        super(context);
        this.init();
    }
    
    public TakenPiecesView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private int getTakenPeacesInPositionForPeace(final Piece piece, final Map<String, Integer> map) {
        final int numberForPieceType = this._chessVariant.getNumberForPieceType(piece);
        int intValue;
        if (map.containsKey(piece.getPieceFigurineNotation())) {
            intValue = map.get(piece.getPieceFigurineNotation());
        }
        else {
            intValue = 0;
        }
        return numberForPieceType - intValue;
    }
    
    private void init() {
        this.setOrientation(0);
        inflate(this.getContext(), 2131427379, (ViewGroup)this);
        this._text = (TextView)this.findViewById(2131296345);
        this._currentPosition = new Position(FEN.STARTING_POSITION);
        this._colorWhite = true;
        this._chessVariant = ChessVariant.CLASSIC_CHESS;
        this.updateText();
    }
    
    private void setTextForPieces(final Map<String, Integer> map) {
        final StringBuilder sb = new StringBuilder();
        final Piece[] allPieceTypes = this._chessVariant.getAllPieceTypes();
        for (int i = 0; i < allPieceTypes.length; ++i) {
            final Piece piece = allPieceTypes[i];
            if (piece.getColor() == this._colorWhite) {
                final int takenPeacesInPositionForPeace = this.getTakenPeacesInPositionForPeace(piece, map);
                if (takenPeacesInPositionForPeace != 0) {
                    sb.append(piece.getPieceUnicodeChar());
                    sb.append(takenPeacesInPositionForPeace);
                    sb.append(" ");
                }
            }
        }
        this._text.setText((CharSequence)sb.toString());
    }
    
    private void updateText() {
        final List<PieceInformation> allPieces = this._currentPosition.getAllPieces();
        final TreeMap<Object, Integer> textForPieces = new TreeMap<Object, Integer>();
        final Iterator<PieceInformation> iterator = allPieces.iterator();
        while (iterator.hasNext()) {
            final Piece piece = iterator.next().getPiece();
            if (piece.getColor() == this._colorWhite) {
                final String pieceFigurineNotation = piece.getPieceFigurineNotation();
                if (!textForPieces.containsKey(pieceFigurineNotation)) {
                    textForPieces.put(pieceFigurineNotation, 0);
                }
                textForPieces.put(pieceFigurineNotation, textForPieces.get(pieceFigurineNotation) + 1);
            }
        }
        this.setTextForPieces((Map<String, Integer>)textForPieces);
    }
    
    public void positionChanged(final Position currentPosition, final Move move) {
        this._currentPosition = currentPosition;
        this.post((Runnable)new Runnable() {
            @Override
            public void run() {
                TakenPiecesView.this.updateText();
            }
        });
    }
    
    public void setColor(final boolean colorWhite) {
        this._colorWhite = colorWhite;
        this.updateText();
    }
}
