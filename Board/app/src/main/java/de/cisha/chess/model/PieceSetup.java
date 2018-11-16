// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

import java.util.ArrayList;
import de.cisha.chess.model.pieces.Pawn;
import de.cisha.chess.model.pieces.Queen;
import de.cisha.chess.model.pieces.Rook;
import de.cisha.chess.model.pieces.Knight;
import de.cisha.chess.model.pieces.King;
import de.cisha.chess.model.pieces.Bishop;
import java.util.Iterator;
import java.util.List;
import java.lang.reflect.Array;

public abstract class PieceSetup extends BaseObject
{
    private static final long serialVersionUID = -5744328030441914649L;
    protected Piece[][] _pieces;
    
    public PieceSetup() {
        this._pieces = (Piece[][])Array.newInstance(Piece.class, 8, 8);
    }
    
    public PieceSetup(final FEN fen) {
        this._pieces = (Piece[][])Array.newInstance(Piece.class, 8, 8);
        for (int i = 0; i < 8; ++i) {
            if (fen.isValid()) {
                final char[] expandRank = this.expandRank(fen.getRank(i + 1));
                for (int j = 0; j < expandRank.length; ++j) {
                    this._pieces[i][j] = this.getPiece(expandRank[j]);
                }
            }
        }
    }
    
    public PieceSetup(final PieceSetup pieceSetup) {
        this._pieces = (Piece[][])Array.newInstance(Piece.class, 8, 8);
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                this._pieces[i][j] = pieceSetup._pieces[i][j];
            }
        }
    }
    
    public PieceSetup(final List<PieceInformation> list) {
        this._pieces = (Piece[][])Array.newInstance(Piece.class, 8, 8);
        for (final PieceInformation pieceInformation : list) {
            this.setPiece(pieceInformation.getPiece(), pieceInformation.getSquare());
        }
    }
    
    private char[] expandRank(final String s) {
        final char[] array = new char[8];
        int n;
        for (int i = n = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            if (char1 >= '1' && char1 <= '8') {
                for (char c = '\0'; c < char1 - '1' + '\u0001'; ++c) {
                    array[n] = '-';
                    ++n;
                }
            }
            else {
                array[n] = char1;
                ++n;
            }
        }
        return array;
    }
    
    private Piece getPiece(final char c) {
        if (c == 'B') {
            return Bishop.WHITE;
        }
        if (c == 'K') {
            return King.WHITE;
        }
        if (c == 'N') {
            return Knight.WHITE;
        }
        if (c == 'b') {
            return Bishop.BLACK;
        }
        if (c == 'k') {
            return King.BLACK;
        }
        if (c == 'n') {
            return Knight.BLACK;
        }
        switch (c) {
            default: {
                switch (c) {
                    default: {
                        return null;
                    }
                    case 'r': {
                        return Rook.BLACK;
                    }
                    case 'q': {
                        return Queen.BLACK;
                    }
                    case 'p': {
                        return Pawn.BLACK;
                    }
                }
                break;
            }
            case 'R': {
                return Rook.WHITE;
            }
            case 'Q': {
                return Queen.WHITE;
            }
            case 'P': {
                return Pawn.WHITE;
            }
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof PieceSetup) {
            final PieceSetup pieceSetup = (PieceSetup)o;
            for (int i = 0; i < 8; ++i) {
                for (int j = 0; j < 8; ++j) {
                    if (this._pieces[i][j] != null && pieceSetup._pieces[i][j] != null) {
                        if (!this._pieces[i][j].equals(pieceSetup._pieces[i][j])) {
                            return false;
                        }
                    }
                    else if (this._pieces[i][j] != pieceSetup._pieces[i][j]) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    public List<PieceInformation> getAllPieces() {
        final ArrayList<PieceInformation> list = new ArrayList<PieceInformation>(32);
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                final Piece piece = this._pieces[i][j];
                if (piece != null) {
                    list.add(new PieceInformation(piece, Square.instanceForRowAndColumn(i, j)));
                }
            }
        }
        return list;
    }
    
    public Square getKingSquareForColor(final boolean b) {
        final King instanceForColor = King.instanceForColor(b);
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (instanceForColor.equals(this._pieces[i][j])) {
                    return Square.instanceForRowAndColumn(i, j);
                }
            }
        }
        return null;
    }
    
    public Piece getPieceFor(final Square square) {
        if (square != null && square.isValid()) {
            return this._pieces[square.getRow()][square.getColumn()];
        }
        return null;
    }
    
    @Override
    public int hashCode() {
        int n;
        for (int i = n = 0; i < 8; ++i) {
            int n2;
            for (int j = 0; j < 8; ++j, n = n2) {
                n2 = n;
                if (this._pieces[i][j] != null) {
                    n2 = (n ^ Integer.rotateLeft(this._pieces[i][j].hashCode(), (8 * j + i) % 32));
                }
            }
        }
        return n;
    }
    
    public boolean isAttacked(final Square square, final boolean b) {
        throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar.2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar.2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
    }
    
    public boolean isCheck(final boolean b) {
        final Square kingSquareForColor = this.getKingSquareForColor(b);
        return kingSquareForColor != null && this.isAttacked(kingSquareForColor, b ^ true);
    }
    
    public boolean isEmpty(final Square square) {
        return this.getPieceFor(square) == null;
    }
    
    protected void removePiece(final Square square) {
        this.setPiece(null, square);
    }
    
    protected void setPiece(final Piece piece, final Square square) {
        if (square != null && square.isValid()) {
            this._pieces[square.getRow()][square.getColumn()] = piece;
        }
    }
}
