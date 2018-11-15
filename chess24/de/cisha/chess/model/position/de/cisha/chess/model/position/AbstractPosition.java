/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model.position;

import de.cisha.chess.model.FEN;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.PieceInformation;
import de.cisha.chess.model.PieceSetup;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.pieces.Bishop;
import de.cisha.chess.model.pieces.King;
import de.cisha.chess.model.pieces.Knight;
import de.cisha.chess.model.pieces.Pawn;
import de.cisha.chess.model.pieces.Queen;
import de.cisha.chess.model.pieces.Rook;
import de.cisha.chess.model.position.MutablePosition;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.util.FENHelper;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractPosition
extends PieceSetup
implements Serializable {
    public static final boolean COLOR_BLACK = false;
    public static final boolean COLOR_WHITE = true;
    protected static final int NO_ENPASSANT = -1;
    private static final long serialVersionUID = 7590842537455257610L;
    protected int _actionCounter;
    protected boolean _activeColor;
    protected boolean _blackCanCastleLong;
    protected boolean _blackCanCastleShort;
    protected int _epColumn;
    protected int _moveCounter;
    protected boolean _whiteCanCastleLong;
    protected boolean _whiteCanCastleShort;

    public AbstractPosition() {
        this._whiteCanCastleLong = false;
        this._whiteCanCastleShort = false;
        this._blackCanCastleLong = false;
        this._blackCanCastleShort = false;
        this._activeColor = true;
        this._actionCounter = 0;
        this._moveCounter = 0;
        this._epColumn = -1;
    }

    public AbstractPosition(FEN fEN) {
        boolean bl;
        super(fEN);
        boolean bl2 = false;
        this._whiteCanCastleLong = false;
        this._whiteCanCastleShort = false;
        this._blackCanCastleLong = false;
        this._blackCanCastleShort = false;
        this._activeColor = true;
        this._actionCounter = 0;
        this._moveCounter = 0;
        this._epColumn = -1;
        String string = fEN.getCastlingString();
        if (string != null) {
            bl = string.contains("Q") || string.contains("A");
            this._whiteCanCastleLong = bl;
            bl = string.contains("K") || string.contains("H");
            this._whiteCanCastleShort = bl;
            bl = string.contains("q") || string.contains("a");
            this._blackCanCastleLong = bl;
            bl = string.contains("k") || string.contains("h");
            this._blackCanCastleShort = bl;
        }
        if ("-".equals(string = fEN.getEnPassantString())) {
            this._epColumn = -1;
        } else if (string.length() > 1) {
            this._epColumn = string.charAt(0) - 97;
        }
        bl = bl2;
        if ('w' == fEN.getActiveColorChar()) {
            bl = true;
        }
        this._activeColor = bl;
        this._actionCounter = fEN.getNoActionNumber();
        this._moveCounter = fEN.getMoveNumber() * 2 - 2;
        if (!this._activeColor) {
            ++this._moveCounter;
        }
    }

    public AbstractPosition(AbstractPosition abstractPosition) {
        super(abstractPosition);
        this._whiteCanCastleLong = false;
        this._whiteCanCastleShort = false;
        this._blackCanCastleLong = false;
        this._blackCanCastleShort = false;
        this._activeColor = true;
        this._actionCounter = 0;
        this._moveCounter = 0;
        this._epColumn = -1;
        this._whiteCanCastleLong = abstractPosition._whiteCanCastleLong;
        this._whiteCanCastleShort = abstractPosition._whiteCanCastleShort;
        this._blackCanCastleLong = abstractPosition._blackCanCastleLong;
        this._blackCanCastleShort = abstractPosition._blackCanCastleShort;
        this._activeColor = abstractPosition._activeColor;
        this._actionCounter = abstractPosition._actionCounter;
        this._moveCounter = abstractPosition._moveCounter;
        this._epColumn = abstractPosition._epColumn;
    }

    public AbstractPosition(List<PieceInformation> list) {
        super(list);
        this._whiteCanCastleLong = false;
        this._whiteCanCastleShort = false;
        this._blackCanCastleLong = false;
        this._blackCanCastleShort = false;
        this._activeColor = true;
        this._actionCounter = 0;
        this._moveCounter = 0;
        this._epColumn = -1;
    }

    private int createIntFromChar(char c) {
        return '\u0001' + c - 49;
    }

    protected static String getCastlingCan(boolean bl, boolean bl2) {
        int n = bl ? 1 : 8;
        char c = bl2 ? (char)'g' : 'c';
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("e");
        stringBuilder.append(n);
        stringBuilder.append(c);
        stringBuilder.append(n);
        return stringBuilder.toString();
    }

    private Piece getPieceForSANChar(char c, boolean bl) {
        if (c != 'B') {
            if (c != 'K') {
                if (c != 'N') {
                    switch (c) {
                        default: {
                            return null;
                        }
                        case 'R': {
                            return Rook.instanceForColor(bl);
                        }
                        case 'Q': 
                    }
                    return Queen.instanceForColor(bl);
                }
                return Knight.instanceForColor(bl);
            }
            return King.instanceForColor(bl);
        }
        return Bishop.instanceForColor(bl);
    }

    public Move createMoveFrom(SEP serializable) {
        boolean bl = this.isMovePossible(serializable.getStartSquare(), serializable.getEndSquare());
        Piece piece = null;
        Object object = piece;
        if (bl) {
            Piece piece2 = this.getPieceFor(serializable.getStartSquare());
            Piece piece3 = this.getPieceFor(serializable.getEndSquare());
            object = piece;
            if (piece2 != null) {
                boolean bl2;
                if (this.isEnPassant(piece2, serializable.getStartSquare(), serializable.getEndSquare())) {
                    char c = serializable.getEndSquare().getFile();
                    int n = piece2.getColor() ? 5 : 4;
                    piece = this.getPieceFor(Square.instanceForFileAndRank(c, n));
                    bl = true;
                } else {
                    piece = piece3;
                    bl = false;
                }
                if (this.isPromotion(piece2, serializable.getStartSquare(), serializable.getEndSquare())) {
                    piece3 = this.getPromotionPiece(serializable.getPromotionPiece(), piece2.getColor());
                    bl2 = true;
                } else {
                    piece3 = null;
                    bl2 = false;
                }
                object = "";
                if (piece2 instanceof Pawn) {
                    if (piece != null) {
                        object = new StringBuilder();
                        object.append("");
                        object.append(serializable.getStartSquare().getFile());
                        object = object.toString();
                    }
                } else {
                    object = this.getConcurrentStringForMove((SEP)serializable, piece2);
                }
                Serializable serializable2 = serializable.getStartSquare();
                Serializable serializable3 = serializable.getEndSquare();
                boolean bl3 = piece != null;
                serializable2 = new Move((Square)serializable2, (Square)serializable3, false, false, bl3, bl, bl2, piece, piece2, piece3, (String)object, this.getSafePosition(), false);
                serializable3 = this.getMutablePosition();
                serializable3.doMove((Move)serializable2);
                serializable2 = serializable3.getSafePosition();
                boolean bl4 = serializable2.isCheck(piece2.getColor() ^ true);
                boolean bl5 = serializable2.isCheckMate();
                serializable3 = serializable.getStartSquare();
                serializable = serializable.getEndSquare();
                bl3 = piece != null;
                object = new Move((Square)serializable3, (Square)serializable, bl4, bl5, bl3, bl, bl2, piece, piece2, piece3, (String)object, (Position)serializable2, false);
            }
        }
        return object;
    }

    public Move createMoveFromCAN(String string) {
        return this.createMoveFrom(new SEP(this.translateCastling(string)));
    }

    public Move createMoveFromSAN(String string) {
        if ((string = string.replace("+", "").replace("x", "").replace("#", "")).length() >= 2) {
            char c = string.charAt(0);
            if (!string.equals("0-0") && !string.equals("O-O")) {
                if (!string.equals("0-0-0") && !string.equals("O-O-O")) {
                    if (c >= 'a' && c <= 'h') {
                        return this.createPawnMoveFromSan(string);
                    }
                    return this.createNonePawnMoveFromSan(string);
                }
                if (this._activeColor) {
                    return this.createMoveFrom(new SEP(Square.SQUARE_E1, Square.SQUARE_C1));
                }
                return this.createMoveFrom(new SEP(Square.SQUARE_E8, Square.SQUARE_C8));
            }
            if (this._activeColor) {
                return this.createMoveFrom(new SEP(Square.SQUARE_E1, Square.SQUARE_G1));
            }
            return this.createMoveFrom(new SEP(Square.SQUARE_E8, Square.SQUARE_G8));
        }
        return null;
    }

    /*
     * Exception decompiling
     */
    protected Move createNonePawnMoveFromSan(String var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[DOLOOP]], but top level block is 4[SIMPLE_IF_TAKEN]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:424)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:476)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2898)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:716)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:186)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:131)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:378)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:884)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:786)
        // org.benf.cfr.reader.Main.doClass(Main.java:54)
        // org.benf.cfr.reader.Main.main(Main.java:247)
        throw new IllegalStateException("Decompilation failed");
    }

    protected Move createPawnMoveFromSan(String object) {
        String string;
        Square square;
        int n = !(object.endsWith("Q") || object.endsWith("N") || object.endsWith("B") || object.endsWith("R") || object.endsWith("q") || object.endsWith("n") || object.endsWith("b") || object.endsWith("r")) ? 0 : 1;
        if (n != 0) {
            object = object.replace("=", "");
            n = FENHelper.getPromotionTypeForFENChar(object.charAt(object.length() - 1));
            string = object.substring(0, object.length() - 1);
        } else {
            n = 0;
            string = object;
        }
        int n2 = string.length();
        if (n2 == 2) {
            int n3 = this.createIntFromChar(string.charAt(1));
            n2 = this._activeColor ? n3 - 1 : n3 + 1;
            Square square2 = Square.instanceForFileAndRank(string.charAt(0), n3);
            Square square3 = Square.instanceForFileAndRank(string.charAt(0), n2);
            square = square2;
            object = square3;
            if (this.isEmpty(square3)) {
                if (this._activeColor) {
                    object = Square.instanceForFileAndRank(string.charAt(0), n2 - 1);
                    square = square2;
                } else {
                    object = Square.instanceForFileAndRank(string.charAt(0), n2 + 1);
                    square = square2;
                }
            }
        } else if (n2 == 3) {
            int n4 = this.createIntFromChar(string.charAt(2));
            char c = string.charAt(0);
            n2 = this._activeColor ? n4 - 1 : n4 + 1;
            object = Square.instanceForFileAndRank(c, n2);
            square = Square.instanceForFileAndRank(string.charAt(1), n4);
        } else {
            object = Square.instanceForFileAndRank(string.charAt(0), this.createIntFromChar(string.charAt(1)));
            square = Square.instanceForFileAndRank(string.charAt(2), this.createIntFromChar(string.charAt(3)));
        }
        return this.createMoveFrom(new SEP((Square)object, square, n));
    }

    @Override
    public boolean equals(Object object) {
        boolean bl;
        boolean bl2 = bl = super.equals(object);
        if (bl) {
            bl2 = bl;
            if (object instanceof Position) {
                object = (Position)object;
                bl2 = object._activeColor == this._activeColor && object._actionCounter == this._actionCounter && object._blackCanCastleLong == this._blackCanCastleLong && object._blackCanCastleShort == this._blackCanCastleShort && object._whiteCanCastleLong == this._whiteCanCastleLong && object._whiteCanCastleShort == this._whiteCanCastleShort && object._moveCounter == this._moveCounter && (object._epColumn == this._epColumn || !this.isEnPassantReallyPossible() && !object.isEnPassantReallyPossible());
            }
        }
        return bl2;
    }

    public int getActionCounter() {
        return this._actionCounter;
    }

    public boolean getActiveColor() {
        return this._activeColor;
    }

    public List<Move> getAllMoves() {
        LinkedList<Move> linkedList = new LinkedList<Move>();
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                Object object = this._pieces[i][j];
                if (object == null || object.getColor() != this._activeColor) continue;
                Square square = Square.instanceForRowAndColumn(i, j);
                object = object.getAllMoves(this, square).iterator();
                while (object.hasNext()) {
                    linkedList.add(this.createMoveFrom(new SEP(square, (Square)object.next(), 3)));
                }
            }
        }
        return linkedList;
    }

    protected List<PieceInformation> getConcurrentPieces(Piece piece, Square square, Square square2) {
        LinkedList<PieceInformation> linkedList = new LinkedList<PieceInformation>();
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                Square square3;
                Piece piece2 = this._pieces[i][j];
                if (piece2 == null || !piece2.equals(piece) || square.equals(square3 = Square.instanceForRowAndColumn(i, j)) || !piece2.canMove(square3, square2, this)) continue;
                linkedList.add(new PieceInformation(piece2, square3));
            }
        }
        return linkedList;
    }

    protected String getConcurrentStringForMove(SEP sEP, Piece object) {
        Object object2 = "";
        object = this.getConcurrentPieces((Piece)object, sEP.getStartSquare(), sEP.getEndSquare()).iterator();
        boolean bl = false;
        boolean bl2 = false;
        while (object.hasNext()) {
            PieceInformation pieceInformation = (PieceInformation)object.next();
            if (pieceInformation.getSquare().getColumn() != sEP.getStartSquare().getColumn()) {
                bl = true;
                continue;
            }
            if (pieceInformation.getSquare().getRow() == sEP.getStartSquare().getRow()) continue;
            bl2 = true;
        }
        object = object2;
        if (bl) {
            object = new StringBuilder();
            object.append("");
            object.append(sEP.getStartSquare().getFile());
            object = object.toString();
        }
        object2 = object;
        if (bl2) {
            object2 = new StringBuilder();
            object2.append((String)object);
            object2.append(sEP.getStartSquare().getRank());
            object2 = object2.toString();
        }
        return object2;
    }

    public Square getEnPassantSquare() {
        if (this.isEnPassantPossible()) {
            int n = this._activeColor ? 5 : 2;
            return Square.instanceForRowAndColumn(n, this._epColumn);
        }
        return null;
    }

    public FEN getFEN() {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:632)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:716)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    public int getHalfMoveNumber() {
        return this._moveCounter;
    }

    public int getMoveNumber() {
        return (this._moveCounter + 1) / 2;
    }

    public abstract MutablePosition getMutablePosition();

    protected Piece getPromotionPiece(int n, boolean bl) {
        switch (n) {
            default: {
                return null;
            }
            case 3: {
                return Queen.instanceForColor(bl);
            }
            case 2: {
                return Bishop.instanceForColor(bl);
            }
            case 1: {
                return Knight.instanceForColor(bl);
            }
            case 0: 
        }
        return Rook.instanceForColor(bl);
    }

    public abstract Position getSafePosition();

    public boolean hasLegalMove() {
        for (PieceInformation pieceInformation : this.getAllPieces()) {
            if (pieceInformation.getPiece().getColor() != this._activeColor || !pieceInformation.getPiece().hasLegalMove(this, pieceInformation.getSquare())) continue;
            return true;
        }
        return false;
    }

    protected boolean isCastling(Piece piece, Square square, Square square2) {
        if (piece instanceof King) {
            if (square.getColumn() + 1 < square2.getColumn()) {
                return true;
            }
            if (square.getColumn() - 1 > square2.getColumn()) {
                return true;
            }
        }
        return false;
    }

    public boolean isCastlingPossible(boolean bl, boolean bl2) {
        if (bl) {
            if (bl2) {
                return this._whiteCanCastleShort;
            }
            return this._whiteCanCastleLong;
        }
        if (bl2) {
            return this._blackCanCastleShort;
        }
        return this._blackCanCastleLong;
    }

    public boolean isCheck() {
        return this.isCheck(this._activeColor);
    }

    public boolean isCheckMate() {
        if (this.isCheck() && !this.hasLegalMove()) {
            return true;
        }
        return false;
    }

    protected boolean isEnPassant(Piece piece, Square square, Square square2) {
        if (piece instanceof Pawn && (piece.getColor() && square.getRank() == 5 && square2.getRank() == 6 || piece.isBlack() && square.getRank() == 4 && square2.getRank() == 3) && (square2.getFile() == square.getFile() + '\u0001' || square2.getFile() == square.getFile() - '\u0001') && this.isEmpty(square2)) {
            return true;
        }
        return false;
    }

    public boolean isEnPassantPossible() {
        if (this._epColumn > -1) {
            return true;
        }
        return false;
    }

    public boolean isEnPassantReallyPossible() {
        if (this.isEnPassantPossible()) {
            int n = 3;
            if (this._activeColor) {
                n = 4;
            }
            Square[] arrsquare = new Square[]{Square.instanceForRowAndColumn(n, this._epColumn + 1), Square.instanceForRowAndColumn(n, this._epColumn - 1)};
            for (n = 0; n < arrsquare.length; ++n) {
                Serializable serializable = arrsquare[n];
                if (!serializable.isValid() || (serializable = this.getPieceFor((Square)serializable)) == null || !serializable.equals(Pawn.instanceForColor(this._activeColor))) continue;
                return true;
            }
        }
        return false;
    }

    public boolean isMovePossible(Square square, Square square2) {
        Piece piece = this.getPieceFor(square);
        if (piece != null && piece.getColor() == this._activeColor) {
            return piece.getAllMoves(this, square).contains(square2);
        }
        return false;
    }

    public boolean isPromotion(Piece piece, Square square, Square square2) {
        if (piece instanceof Pawn) {
            if (piece.getColor() && square.getRank() == 7) {
                return true;
            }
            if (piece.isBlack() && square.getRank() == 2) {
                return true;
            }
        }
        return false;
    }

    public boolean isStaleMate() {
        if (!this.isCheck() && !this.hasLegalMove()) {
            return true;
        }
        return false;
    }

    public boolean isValid() {
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        int n11 = 0;
        int n12 = 0;
        boolean bl = false;
        for (int i = 0; i <= 7; ++i) {
            int n13 = n9;
            n9 = n8;
            int n14 = n5;
            int n15 = n4;
            int n16 = 0;
            n8 = n13;
            n13 = n12;
            n12 = n10;
            n5 = n7;
            n7 = n6;
            n4 = n14;
            n6 = n15;
            n10 = n2;
            for (n14 = n16; n14 <= 7; ++n14) {
                int n17;
                block37 : {
                    block36 : {
                        Square square = Square.instanceForRowAndColumn(i, n14);
                        Piece piece = this.getPieceFor(square);
                        boolean bl2 = piece instanceof King;
                        n2 = n3;
                        if (bl2) {
                            n2 = n3;
                            if (piece.getColor()) {
                                n2 = n3 + 1;
                            }
                        }
                        n15 = n13;
                        if (bl2) {
                            n15 = n13;
                            if (!piece.getColor()) {
                                n15 = n13 + 1;
                            }
                        }
                        bl2 = piece instanceof Pawn;
                        n13 = n12;
                        if (bl2) {
                            n13 = n12;
                            if (piece.getColor()) {
                                n13 = n12 + 1;
                            }
                        }
                        n16 = n11;
                        if (bl2) {
                            n16 = n11;
                            if (!piece.getColor()) {
                                n16 = n11 + 1;
                            }
                        }
                        boolean bl3 = piece instanceof Knight;
                        n3 = n10;
                        if (bl3) {
                            n3 = n10;
                            if (piece.getColor()) {
                                n3 = n10 + 1;
                            }
                        }
                        n11 = n5;
                        if (bl3) {
                            n11 = n5;
                            if (!piece.getColor()) {
                                n11 = n5 + 1;
                            }
                        }
                        bl3 = piece instanceof Queen;
                        n5 = n4;
                        if (bl3) {
                            n5 = n4;
                            if (piece.getColor()) {
                                n5 = n4 + 1;
                            }
                        }
                        n17 = n8;
                        if (bl3) {
                            n17 = n8;
                            if (!piece.getColor()) {
                                n17 = n8 + 1;
                            }
                        }
                        bl3 = piece instanceof Bishop;
                        n4 = n;
                        if (bl3) {
                            n4 = n;
                            if (piece.getColor()) {
                                n4 = n + 1;
                            }
                        }
                        n8 = n7;
                        if (bl3) {
                            n8 = n7;
                            if (!piece.getColor()) {
                                n8 = n7 + 1;
                            }
                        }
                        bl3 = piece instanceof Rook;
                        n7 = n6;
                        if (bl3) {
                            n7 = n6;
                            if (piece.getColor()) {
                                n7 = n6 + 1;
                            }
                        }
                        n12 = n9;
                        if (bl3) {
                            n12 = n9;
                            if (!piece.getColor()) {
                                n12 = n9 + 1;
                            }
                        }
                        if (bl2 && square.getRow() == 0) break block36;
                        if (!bl2 || square.getRow() != 7) break block37;
                    }
                    bl = true;
                }
                n = n4;
                n10 = n3;
                n3 = n2;
                n6 = n7;
                n4 = n5;
                n7 = n8;
                n5 = n11;
                n9 = n12;
                n12 = n13;
                n11 = n16;
                n13 = n15;
                n8 = n17;
            }
            n2 = n10;
            n10 = n4;
            n14 = n5;
            n15 = n8;
            n4 = n6;
            n5 = n10;
            n6 = n7;
            n7 = n14;
            n8 = n9;
            n9 = n15;
            n10 = n12;
            n12 = n13;
        }
        n = n > 2 ? (n -= 2) : 0;
        n2 = n2 > 2 ? n3 - 2 : 0;
        n4 = n4 > 2 ? (n4 -= 2) : 0;
        n5 = n5 > 1 ? --n5 : 0;
        n6 = n6 > 2 ? (n6 -= 2) : 0;
        n7 = n7 > 2 ? -2 : 0;
        n8 = n8 > 2 ? (n8 -= 2) : 0;
        n9 = n9 > 1 ? --n9 : 0;
        if (n + n2 + n5 + n4 + n10 > 8) {
            return false;
        }
        if (n6 + n7 + n9 + n8 + n11 > 8) {
            return false;
        }
        if (n3 != 1) {
            return false;
        }
        if (n12 != 1) {
            return false;
        }
        if (bl) {
            return false;
        }
        if (this.isCheck(true) && !this._activeColor) {
            return false;
        }
        if (this.isCheck(false) && this._activeColor) {
            return false;
        }
        return true;
    }

    public String toString() {
        FEN fEN = this.getFEN();
        if (fEN != null) {
            return fEN.getFENString();
        }
        return Object.super.toString();
    }

    public String translateCastling(String string) {
        return this.translateCastlingFromSan(this.translateCastlingFromKingTakesRook(string));
    }

    protected String translateCastlingFromKingTakesRook(String string) {
        if (string.length() == 4) {
            boolean bl = false;
            Square square = Square.instanceForFileAndRank(string.charAt(0), Character.getNumericValue(string.charAt(1)));
            Square square2 = Square.instanceForFileAndRank(string.charAt(2), Character.getNumericValue(string.charAt(3)));
            Piece piece = this.getPieceFor(square);
            Piece piece2 = this.getPieceFor(square2);
            if (piece2 != null && piece.getColor() == piece2.getColor() && piece instanceof King && piece2 instanceof Rook) {
                boolean bl2 = piece.getColor();
                if (square.getFile() < square2.getFile()) {
                    bl = true;
                }
                return AbstractPosition.getCastlingCan(bl2, bl);
            }
        }
        return string;
    }

    protected String translateCastlingFromSan(String string) {
        if (string.contains("O-O")) {
            return AbstractPosition.getCastlingCan(this.getActiveColor(), string.equals("O-O"));
        }
        return string;
    }
}
