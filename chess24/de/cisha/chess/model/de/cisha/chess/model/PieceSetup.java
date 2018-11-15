/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.model.BaseObject;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.PieceInformation;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.pieces.Bishop;
import de.cisha.chess.model.pieces.King;
import de.cisha.chess.model.pieces.Knight;
import de.cisha.chess.model.pieces.Pawn;
import de.cisha.chess.model.pieces.Queen;
import de.cisha.chess.model.pieces.Rook;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class PieceSetup
extends BaseObject {
    private static final long serialVersionUID = -5744328030441914649L;
    protected Piece[][] _pieces = (Piece[][])Array.newInstance(Piece.class, 8, 8);

    public PieceSetup() {
    }

    public PieceSetup(FEN fEN) {
        for (int i = 0; i < 8; ++i) {
            if (!fEN.isValid()) continue;
            char[] arrc = this.expandRank(fEN.getRank(i + 1));
            for (int j = 0; j < arrc.length; ++j) {
                Piece piece;
                this._pieces[i][j] = piece = this.getPiece(arrc[j]);
            }
        }
    }

    public PieceSetup(PieceSetup pieceSetup) {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                this._pieces[i][j] = pieceSetup._pieces[i][j];
            }
        }
    }

    public PieceSetup(List<PieceInformation> object) {
        object = object.iterator();
        while (object.hasNext()) {
            PieceInformation pieceInformation = (PieceInformation)object.next();
            this.setPiece(pieceInformation.getPiece(), pieceInformation.getSquare());
        }
    }

    private char[] expandRank(String string) {
        int n;
        char[] arrc = new char[8];
        int n2 = n = 0;
        while (n < string.length()) {
            char c = string.charAt(n);
            if (c >= '1' && c <= '8') {
                for (int i = 0; i < c - 49 + 1; ++i) {
                    arrc[n2] = 45;
                    ++n2;
                }
            } else {
                arrc[n2] = c;
                ++n2;
            }
            ++n;
        }
        return arrc;
    }

    private Piece getPiece(char c) {
        if (c != 'B') {
            if (c != 'K') {
                if (c != 'N') {
                    if (c != 'b') {
                        if (c != 'k') {
                            if (c != 'n') {
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
                                            case 'p': 
                                        }
                                        return Pawn.BLACK;
                                    }
                                    case 'R': {
                                        return Rook.WHITE;
                                    }
                                    case 'Q': {
                                        return Queen.WHITE;
                                    }
                                    case 'P': 
                                }
                                return Pawn.WHITE;
                            }
                            return Knight.BLACK;
                        }
                        return King.BLACK;
                    }
                    return Bishop.BLACK;
                }
                return Knight.WHITE;
            }
            return King.WHITE;
        }
        return Bishop.WHITE;
    }

    public boolean equals(Object object) {
        if (object instanceof PieceSetup) {
            object = (PieceSetup)object;
            for (int i = 0; i < 8; ++i) {
                for (int j = 0; j < 8; ++j) {
                    if (!(this._pieces[i][j] != null && object._pieces[i][j] != null ? !this._pieces[i][j].equals(object._pieces[i][j]) : this._pieces[i][j] != object._pieces[i][j])) continue;
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public List<PieceInformation> getAllPieces() {
        ArrayList<PieceInformation> arrayList = new ArrayList<PieceInformation>(32);
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                Piece piece = this._pieces[i][j];
                if (piece == null) continue;
                arrayList.add(new PieceInformation(piece, Square.instanceForRowAndColumn(i, j)));
            }
        }
        return arrayList;
    }

    public Square getKingSquareForColor(boolean bl) {
        King king = King.instanceForColor(bl);
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (!king.equals(this._pieces[i][j])) continue;
                return Square.instanceForRowAndColumn(i, j);
            }
        }
        return null;
    }

    public Piece getPieceFor(Square square) {
        if (square != null && square.isValid()) {
            return this._pieces[square.getRow()][square.getColumn()];
        }
        return null;
    }

    public int hashCode() {
        int n;
        int n2 = n = 0;
        while (n < 8) {
            for (int i = 0; i < 8; ++i) {
                int n3 = n2;
                if (this._pieces[n][i] != null) {
                    n3 = n2 ^ Integer.rotateLeft(this._pieces[n][i].hashCode(), (8 * i + n) % 32);
                }
                n2 = n3;
            }
            ++n;
        }
        return n2;
    }

    public boolean isAttacked(Square square, boolean bl) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    public boolean isCheck(boolean bl) {
        Square square = this.getKingSquareForColor(bl);
        if (square != null && this.isAttacked(square, bl ^ true)) {
            return true;
        }
        return false;
    }

    public boolean isEmpty(Square square) {
        if (this.getPieceFor(square) == null) {
            return true;
        }
        return false;
    }

    protected void removePiece(Square square) {
        this.setPiece(null, square);
    }

    protected void setPiece(Piece piece, Square square) {
        if (square != null && square.isValid()) {
            this._pieces[square.getRow()][square.getColumn()] = piece;
        }
    }
}
