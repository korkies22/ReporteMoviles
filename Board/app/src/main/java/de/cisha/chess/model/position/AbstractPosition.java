// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model.position;

import java.util.LinkedList;
import de.cisha.chess.util.FENHelper;
import java.util.Iterator;
import java.util.ArrayList;
import de.cisha.chess.model.pieces.Pawn;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.pieces.Queen;
import de.cisha.chess.model.pieces.Rook;
import de.cisha.chess.model.pieces.Knight;
import de.cisha.chess.model.pieces.King;
import de.cisha.chess.model.pieces.Bishop;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.PieceInformation;
import java.util.List;
import de.cisha.chess.model.FEN;
import java.io.Serializable;
import de.cisha.chess.model.PieceSetup;

public abstract class AbstractPosition extends PieceSetup implements Serializable
{
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
    
    public AbstractPosition(final FEN fen) {
        super(fen);
        final boolean b = false;
        this._whiteCanCastleLong = false;
        this._whiteCanCastleShort = false;
        this._blackCanCastleLong = false;
        this._blackCanCastleShort = false;
        this._activeColor = true;
        this._actionCounter = 0;
        this._moveCounter = 0;
        this._epColumn = -1;
        final String castlingString = fen.getCastlingString();
        if (castlingString != null) {
            this._whiteCanCastleLong = (castlingString.contains("Q") || castlingString.contains("A"));
            this._whiteCanCastleShort = (castlingString.contains("K") || castlingString.contains("H"));
            this._blackCanCastleLong = (castlingString.contains("q") || castlingString.contains("a"));
            this._blackCanCastleShort = (castlingString.contains("k") || castlingString.contains("h"));
        }
        final String enPassantString = fen.getEnPassantString();
        if ("-".equals(enPassantString)) {
            this._epColumn = -1;
        }
        else if (enPassantString.length() > 1) {
            this._epColumn = enPassantString.charAt(0) - 'a';
        }
        boolean activeColor = b;
        if ('w' == fen.getActiveColorChar()) {
            activeColor = true;
        }
        this._activeColor = activeColor;
        this._actionCounter = fen.getNoActionNumber();
        this._moveCounter = fen.getMoveNumber() * 2 - 2;
        if (!this._activeColor) {
            ++this._moveCounter;
        }
    }
    
    public AbstractPosition(final AbstractPosition abstractPosition) {
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
    
    public AbstractPosition(final List<PieceInformation> list) {
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
    
    private int createIntFromChar(final char c) {
        return '\u0001' + c - '1';
    }
    
    protected static String getCastlingCan(final boolean b, final boolean b2) {
        int n;
        if (b) {
            n = 1;
        }
        else {
            n = 8;
        }
        char c;
        if (b2) {
            c = 'g';
        }
        else {
            c = 'c';
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("e");
        sb.append(n);
        sb.append(c);
        sb.append(n);
        return sb.toString();
    }
    
    private Piece getPieceForSANChar(final char c, final boolean b) {
        if (c == 'B') {
            return Bishop.instanceForColor(b);
        }
        if (c == 'K') {
            return King.instanceForColor(b);
        }
        if (c == 'N') {
            return Knight.instanceForColor(b);
        }
        switch (c) {
            default: {
                return null;
            }
            case 'R': {
                return Rook.instanceForColor(b);
            }
            case 'Q': {
                return Queen.instanceForColor(b);
            }
        }
    }
    
    public Move createMoveFrom(final SEP sep) {
        final boolean movePossible = this.isMovePossible(sep.getStartSquare(), sep.getEndSquare());
        Move move = null;
        if (movePossible) {
            final Piece piece = this.getPieceFor(sep.getStartSquare());
            final Piece piece2 = this.getPieceFor(sep.getEndSquare());
            move = move;
            if (piece != null) {
                Piece piece3;
                boolean b;
                if (this.isEnPassant(piece, sep.getStartSquare(), sep.getEndSquare())) {
                    final char file = sep.getEndSquare().getFile();
                    int n;
                    if (piece.getColor()) {
                        n = 5;
                    }
                    else {
                        n = 4;
                    }
                    piece3 = this.getPieceFor(Square.instanceForFileAndRank(file, n));
                    b = true;
                }
                else {
                    piece3 = piece2;
                    b = false;
                }
                Piece promotionPiece;
                boolean b2;
                if (this.isPromotion(piece, sep.getStartSquare(), sep.getEndSquare())) {
                    promotionPiece = this.getPromotionPiece(sep.getPromotionPiece(), piece.getColor());
                    b2 = true;
                }
                else {
                    promotionPiece = null;
                    b2 = false;
                }
                String s = "";
                if (piece instanceof Pawn) {
                    if (piece3 != null) {
                        final StringBuilder sb = new StringBuilder();
                        sb.append("");
                        sb.append(sep.getStartSquare().getFile());
                        s = sb.toString();
                    }
                }
                else {
                    s = this.getConcurrentStringForMove(sep, piece);
                }
                final Move move2 = new Move(sep.getStartSquare(), sep.getEndSquare(), false, false, piece3 != null, b, b2, piece3, piece, promotionPiece, s, this.getSafePosition(), false);
                final MutablePosition mutablePosition = this.getMutablePosition();
                mutablePosition.doMove(move2);
                final Position safePosition = mutablePosition.getSafePosition();
                move = new Move(sep.getStartSquare(), sep.getEndSquare(), safePosition.isCheck(piece.getColor() ^ true), safePosition.isCheckMate(), piece3 != null, b, b2, piece3, piece, promotionPiece, s, safePosition, false);
            }
        }
        return move;
    }
    
    public Move createMoveFromCAN(final String s) {
        return this.createMoveFrom(new SEP(this.translateCastling(s)));
    }
    
    public Move createMoveFromSAN(String replace) {
        replace = replace.replace("+", "").replace("x", "").replace("#", "");
        if (replace.length() < 2) {
            return null;
        }
        final char char1 = replace.charAt(0);
        if (!replace.equals("0-0") && !replace.equals("O-O")) {
            if (!replace.equals("0-0-0") && !replace.equals("O-O-O")) {
                if (char1 >= 'a' && char1 <= 'h') {
                    return this.createPawnMoveFromSan(replace);
                }
                return this.createNonePawnMoveFromSan(replace);
            }
            else {
                if (this._activeColor) {
                    return this.createMoveFrom(new SEP(Square.SQUARE_E1, Square.SQUARE_C1));
                }
                return this.createMoveFrom(new SEP(Square.SQUARE_E8, Square.SQUARE_C8));
            }
        }
        else {
            if (this._activeColor) {
                return this.createMoveFrom(new SEP(Square.SQUARE_E1, Square.SQUARE_G1));
            }
            return this.createMoveFrom(new SEP(Square.SQUARE_E8, Square.SQUARE_G8));
        }
    }
    
    protected Move createNonePawnMoveFromSan(final String s) {
        final int length = s.length();
        final Square instanceForFileAndRank = Square.instanceForFileAndRank(s.charAt(length - 2), this.createIntFromChar(s.charAt(length - 1)));
        String substring = "";
        if (length == 4) {
            substring = s.substring(1, 2);
        }
        Square square2 = null;
        Label_0352: {
            if (length != 5) {
                final Piece pieceForSANChar = this.getPieceForSANChar(s.charAt(0), this._activeColor);
                final List<PieceInformation> allPieces = this.getAllPieces();
                final ArrayList<PieceInformation> list = new ArrayList<PieceInformation>();
                while (true) {
                    for (final PieceInformation pieceInformation : allPieces) {
                        if (pieceInformation.getPiece().equals(pieceForSANChar) && pieceInformation.getPiece().canMove(pieceInformation.getSquare(), instanceForFileAndRank, this)) {
                            if (substring.length() == 0) {
                                final Square square = pieceInformation.getSquare();
                                square2 = square;
                                if (substring.length() != 1) {
                                    break Label_0352;
                                }
                                final char char1 = substring.charAt(0);
                                if (char1 >= 'a' && char1 <= 'h') {
                                    final Iterator<Object> iterator2 = list.iterator();
                                    PieceInformation pieceInformation2;
                                    do {
                                        square2 = square;
                                        if (!iterator2.hasNext()) {
                                            break Label_0352;
                                        }
                                        pieceInformation2 = iterator2.next();
                                    } while (char1 != pieceInformation2.getSquare().getFile());
                                    square2 = pieceInformation2.getSquare();
                                    break Label_0352;
                                }
                                square2 = square;
                                if (char1 < '1') {
                                    break Label_0352;
                                }
                                square2 = square;
                                if (char1 <= '8') {
                                    final int i = this.createIntFromChar(char1);
                                    final Iterator<Object> iterator3 = list.iterator();
                                    PieceInformation pieceInformation3;
                                    do {
                                        square2 = square;
                                        if (!iterator3.hasNext()) {
                                            break Label_0352;
                                        }
                                        pieceInformation3 = iterator3.next();
                                    } while (i != pieceInformation3.getSquare().getRank());
                                    square2 = pieceInformation3.getSquare();
                                }
                                break Label_0352;
                            }
                            else {
                                list.add(pieceInformation);
                            }
                        }
                    }
                    final Square square = null;
                    continue;
                }
            }
            square2 = Square.instanceForFileAndRank(s.charAt(1), this.createIntFromChar(s.charAt(2)));
        }
        if (square2 != null && instanceForFileAndRank.isValid()) {
            return this.createMoveFrom(new SEP(square2, instanceForFileAndRank));
        }
        return null;
    }
    
    protected Move createPawnMoveFromSan(String replace) {
        int promotionTypeForFENChar;
        String substring;
        if (replace.endsWith("Q") || replace.endsWith("N") || replace.endsWith("B") || replace.endsWith("R") || replace.endsWith("q") || replace.endsWith("n") || replace.endsWith("b") || replace.endsWith("r")) {
            replace = replace.replace("=", "");
            promotionTypeForFENChar = FENHelper.getPromotionTypeForFENChar(replace.charAt(replace.length() - 1));
            substring = replace.substring(0, replace.length() - 1);
        }
        else {
            promotionTypeForFENChar = 0;
            substring = replace;
        }
        final int length = substring.length();
        Square square;
        Square square2;
        if (length == 2) {
            final int intFromChar = this.createIntFromChar(substring.charAt(1));
            int n;
            if (this._activeColor) {
                n = intFromChar - 1;
            }
            else {
                n = intFromChar + 1;
            }
            final Square instanceForFileAndRank = Square.instanceForFileAndRank(substring.charAt(0), intFromChar);
            final Square instanceForFileAndRank2 = Square.instanceForFileAndRank(substring.charAt(0), n);
            square = instanceForFileAndRank;
            square2 = instanceForFileAndRank2;
            if (this.isEmpty(instanceForFileAndRank2)) {
                if (this._activeColor) {
                    square2 = Square.instanceForFileAndRank(substring.charAt(0), n - 1);
                    square = instanceForFileAndRank;
                }
                else {
                    square2 = Square.instanceForFileAndRank(substring.charAt(0), n + 1);
                    square = instanceForFileAndRank;
                }
            }
        }
        else if (length == 3) {
            final int intFromChar2 = this.createIntFromChar(substring.charAt(2));
            final char char1 = substring.charAt(0);
            int n2;
            if (this._activeColor) {
                n2 = intFromChar2 - 1;
            }
            else {
                n2 = intFromChar2 + 1;
            }
            square2 = Square.instanceForFileAndRank(char1, n2);
            square = Square.instanceForFileAndRank(substring.charAt(1), intFromChar2);
        }
        else {
            square2 = Square.instanceForFileAndRank(substring.charAt(0), this.createIntFromChar(substring.charAt(1)));
            square = Square.instanceForFileAndRank(substring.charAt(2), this.createIntFromChar(substring.charAt(3)));
        }
        return this.createMoveFrom(new SEP(square2, square, promotionTypeForFENChar));
    }
    
    @Override
    public boolean equals(final Object o) {
        boolean equals;
        final boolean b = equals = super.equals(o);
        if (b) {
            equals = b;
            if (o instanceof Position) {
                final Position position = (Position)o;
                equals = (position._activeColor == this._activeColor && position._actionCounter == this._actionCounter && position._blackCanCastleLong == this._blackCanCastleLong && position._blackCanCastleShort == this._blackCanCastleShort && position._whiteCanCastleLong == this._whiteCanCastleLong && position._whiteCanCastleShort == this._whiteCanCastleShort && position._moveCounter == this._moveCounter && (position._epColumn == this._epColumn || (!this.isEnPassantReallyPossible() && !position.isEnPassantReallyPossible())));
            }
        }
        return equals;
    }
    
    public int getActionCounter() {
        return this._actionCounter;
    }
    
    public boolean getActiveColor() {
        return this._activeColor;
    }
    
    public List<Move> getAllMoves() {
        final LinkedList<Move> list = new LinkedList<Move>();
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                final Piece piece = this._pieces[i][j];
                if (piece != null && piece.getColor() == this._activeColor) {
                    final Square instanceForRowAndColumn = Square.instanceForRowAndColumn(i, j);
                    final Iterator<Square> iterator = piece.getAllMoves(this, instanceForRowAndColumn).iterator();
                    while (iterator.hasNext()) {
                        list.add(this.createMoveFrom(new SEP(instanceForRowAndColumn, iterator.next(), 3)));
                    }
                }
            }
        }
        return list;
    }
    
    protected List<PieceInformation> getConcurrentPieces(final Piece piece, final Square square, final Square square2) {
        final LinkedList<PieceInformation> list = new LinkedList<PieceInformation>();
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                final Piece piece2 = this._pieces[i][j];
                if (piece2 != null && piece2.equals(piece)) {
                    final Square instanceForRowAndColumn = Square.instanceForRowAndColumn(i, j);
                    if (!square.equals(instanceForRowAndColumn) && piece2.canMove(instanceForRowAndColumn, square2, this)) {
                        list.add(new PieceInformation(piece2, instanceForRowAndColumn));
                    }
                }
            }
        }
        return list;
    }
    
    protected String getConcurrentStringForMove(final SEP sep, final Piece piece) {
        final String s = "";
        final Iterator<PieceInformation> iterator = this.getConcurrentPieces(piece, sep.getStartSquare(), sep.getEndSquare()).iterator();
        boolean b = false;
        boolean b2 = false;
        while (iterator.hasNext()) {
            final PieceInformation pieceInformation = iterator.next();
            if (pieceInformation.getSquare().getColumn() != sep.getStartSquare().getColumn()) {
                b = true;
            }
            else {
                if (pieceInformation.getSquare().getRow() == sep.getStartSquare().getRow()) {
                    continue;
                }
                b2 = true;
            }
        }
        String string = s;
        if (b) {
            final StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(sep.getStartSquare().getFile());
            string = sb.toString();
        }
        String string2 = string;
        if (b2) {
            final StringBuilder sb2 = new StringBuilder();
            sb2.append(string);
            sb2.append(sep.getStartSquare().getRank());
            string2 = sb2.toString();
        }
        return string2;
    }
    
    public Square getEnPassantSquare() {
        if (this.isEnPassantPossible()) {
            int n;
            if (this._activeColor) {
                n = 5;
            }
            else {
                n = 2;
            }
            return Square.instanceForRowAndColumn(n, this._epColumn);
        }
        return null;
    }
    
    public FEN getFEN() {
        throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.TypeAnalyze.e1expr(TypeTransformer.java:496)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.TypeAnalyze.exExpr(TypeTransformer.java:713)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.TypeAnalyze.e2expr(TypeTransformer.java:632)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.TypeAnalyze.exExpr(TypeTransformer.java:716)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar.2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar.2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
    }
    
    public int getHalfMoveNumber() {
        return this._moveCounter;
    }
    
    public int getMoveNumber() {
        return (this._moveCounter + 1) / 2;
    }
    
    public abstract MutablePosition getMutablePosition();
    
    protected Piece getPromotionPiece(final int n, final boolean b) {
        switch (n) {
            default: {
                return null;
            }
            case 3: {
                return Queen.instanceForColor(b);
            }
            case 2: {
                return Bishop.instanceForColor(b);
            }
            case 1: {
                return Knight.instanceForColor(b);
            }
            case 0: {
                return Rook.instanceForColor(b);
            }
        }
    }
    
    public abstract Position getSafePosition();
    
    public boolean hasLegalMove() {
        for (final PieceInformation pieceInformation : this.getAllPieces()) {
            if (pieceInformation.getPiece().getColor() == this._activeColor && pieceInformation.getPiece().hasLegalMove(this, pieceInformation.getSquare())) {
                return true;
            }
        }
        return false;
    }
    
    protected boolean isCastling(final Piece piece, final Square square, final Square square2) {
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
    
    public boolean isCastlingPossible(final boolean b, final boolean b2) {
        if (b) {
            if (b2) {
                return this._whiteCanCastleShort;
            }
            return this._whiteCanCastleLong;
        }
        else {
            if (b2) {
                return this._blackCanCastleShort;
            }
            return this._blackCanCastleLong;
        }
    }
    
    public boolean isCheck() {
        return this.isCheck(this._activeColor);
    }
    
    public boolean isCheckMate() {
        return this.isCheck() && !this.hasLegalMove();
    }
    
    protected boolean isEnPassant(final Piece piece, final Square square, final Square square2) {
        return piece instanceof Pawn && ((piece.getColor() && square.getRank() == 5 && square2.getRank() == 6) || (piece.isBlack() && square.getRank() == 4 && square2.getRank() == 3)) && (square2.getFile() == square.getFile() + '\u0001' || square2.getFile() == square.getFile() - '\u0001') && this.isEmpty(square2);
    }
    
    public boolean isEnPassantPossible() {
        return this._epColumn > -1;
    }
    
    public boolean isEnPassantReallyPossible() {
        if (this.isEnPassantPossible()) {
            int n = 3;
            if (this._activeColor) {
                n = 4;
            }
            final Square[] array = { Square.instanceForRowAndColumn(n, this._epColumn + 1), Square.instanceForRowAndColumn(n, this._epColumn - 1) };
            for (int i = 0; i < array.length; ++i) {
                final Square square = array[i];
                if (square.isValid()) {
                    final Piece piece = this.getPieceFor(square);
                    if (piece != null && piece.equals(Pawn.instanceForColor(this._activeColor))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean isMovePossible(final Square square, final Square square2) {
        final Piece piece = this.getPieceFor(square);
        return piece != null && piece.getColor() == this._activeColor && piece.getAllMoves(this, square).contains(square2);
    }
    
    public boolean isPromotion(final Piece piece, final Square square, final Square square2) {
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
        return !this.isCheck() && !this.hasLegalMove();
    }
    
    public boolean isValid() {
        int i = 0;
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
        boolean b = false;
        while (i <= 7) {
            final int n13 = n9;
            int n14 = n8;
            final int n15 = n5;
            final int n16 = n4;
            final int n17 = 0;
            int n18 = n13;
            int n19 = n12;
            int n20 = n10;
            int n21 = n7;
            int n22 = n6;
            int n23 = n15;
            int n24 = n16;
            int n25 = n2;
            int n26;
            int n27;
            int n28;
            int n29;
            int n30;
            int n31;
            int n32;
            int n33;
            int n34;
            int n35;
            int n36;
            int n37;
            for (int j = n17; j <= 7; ++j, n = n34, n25 = n30, n3 = n26, n24 = n36, n23 = n32, n22 = n35, n21 = n31, n14 = n37, n20 = n28, n11 = n29, n19 = n27, n18 = n33) {
                final Square instanceForRowAndColumn = Square.instanceForRowAndColumn(i, j);
                final Piece piece = this.getPieceFor(instanceForRowAndColumn);
                final boolean b2 = piece instanceof King;
                n26 = n3;
                if (b2) {
                    n26 = n3;
                    if (piece.getColor()) {
                        n26 = n3 + 1;
                    }
                }
                n27 = n19;
                if (b2) {
                    n27 = n19;
                    if (!piece.getColor()) {
                        n27 = n19 + 1;
                    }
                }
                final boolean b3 = piece instanceof Pawn;
                n28 = n20;
                if (b3) {
                    n28 = n20;
                    if (piece.getColor()) {
                        n28 = n20 + 1;
                    }
                }
                n29 = n11;
                if (b3) {
                    n29 = n11;
                    if (!piece.getColor()) {
                        n29 = n11 + 1;
                    }
                }
                final boolean b4 = piece instanceof Knight;
                n30 = n25;
                if (b4) {
                    n30 = n25;
                    if (piece.getColor()) {
                        n30 = n25 + 1;
                    }
                }
                n31 = n21;
                if (b4) {
                    n31 = n21;
                    if (!piece.getColor()) {
                        n31 = n21 + 1;
                    }
                }
                final boolean b5 = piece instanceof Queen;
                n32 = n23;
                if (b5) {
                    n32 = n23;
                    if (piece.getColor()) {
                        n32 = n23 + 1;
                    }
                }
                n33 = n18;
                if (b5) {
                    n33 = n18;
                    if (!piece.getColor()) {
                        n33 = n18 + 1;
                    }
                }
                final boolean b6 = piece instanceof Bishop;
                n34 = n;
                if (b6) {
                    n34 = n;
                    if (piece.getColor()) {
                        n34 = n + 1;
                    }
                }
                n35 = n22;
                if (b6) {
                    n35 = n22;
                    if (!piece.getColor()) {
                        n35 = n22 + 1;
                    }
                }
                final boolean b7 = piece instanceof Rook;
                n36 = n24;
                if (b7) {
                    n36 = n24;
                    if (piece.getColor()) {
                        n36 = n24 + 1;
                    }
                }
                n37 = n14;
                if (b7) {
                    n37 = n14;
                    if (!piece.getColor()) {
                        n37 = n14 + 1;
                    }
                }
                if (!b3 || instanceForRowAndColumn.getRow() != 0) {
                    if (!b3 || instanceForRowAndColumn.getRow() != 7) {
                        continue;
                    }
                }
                b = true;
            }
            ++i;
            n2 = n25;
            final int n38 = n23;
            final int n39 = n21;
            final int n40 = n18;
            n4 = n24;
            n5 = n38;
            n6 = n22;
            n7 = n39;
            n8 = n14;
            n9 = n40;
            n10 = n20;
            n12 = n19;
        }
        int n41;
        if (n > 2) {
            n41 = n - 2;
        }
        else {
            n41 = 0;
        }
        int n42;
        if (n2 > 2) {
            n42 = n3 - 2;
        }
        else {
            n42 = 0;
        }
        int n43;
        if (n4 > 2) {
            n43 = n4 - 2;
        }
        else {
            n43 = 0;
        }
        int n44;
        if (n5 > 1) {
            n44 = n5 - 1;
        }
        else {
            n44 = 0;
        }
        int n45;
        if (n6 > 2) {
            n45 = n6 - 2;
        }
        else {
            n45 = 0;
        }
        int n46;
        if (n7 > 2) {
            n46 = -2;
        }
        else {
            n46 = 0;
        }
        int n47;
        if (n8 > 2) {
            n47 = n8 - 2;
        }
        else {
            n47 = 0;
        }
        int n48;
        if (n9 > 1) {
            n48 = n9 - 1;
        }
        else {
            n48 = 0;
        }
        return n41 + n42 + n44 + n43 + n10 <= 8 && n45 + n46 + n48 + n47 + n11 <= 8 && n3 == 1 && n12 == 1 && !b && (!this.isCheck(true) || this._activeColor) && (!this.isCheck(false) || !this._activeColor);
    }
    
    @Override
    public String toString() {
        final FEN fen = this.getFEN();
        if (fen != null) {
            return fen.getFENString();
        }
        return super.toString();
    }
    
    public String translateCastling(final String s) {
        return this.translateCastlingFromSan(this.translateCastlingFromKingTakesRook(s));
    }
    
    protected String translateCastlingFromKingTakesRook(final String s) {
        if (s.length() == 4) {
            boolean b = false;
            final Square instanceForFileAndRank = Square.instanceForFileAndRank(s.charAt(0), Character.getNumericValue(s.charAt(1)));
            final Square instanceForFileAndRank2 = Square.instanceForFileAndRank(s.charAt(2), Character.getNumericValue(s.charAt(3)));
            final Piece piece = this.getPieceFor(instanceForFileAndRank);
            final Piece piece2 = this.getPieceFor(instanceForFileAndRank2);
            if (piece2 != null && piece.getColor() == piece2.getColor() && piece instanceof King && piece2 instanceof Rook) {
                final boolean color = piece.getColor();
                if (instanceForFileAndRank.getFile() < instanceForFileAndRank2.getFile()) {
                    b = true;
                }
                return getCastlingCan(color, b);
            }
        }
        return s;
    }
    
    protected String translateCastlingFromSan(final String s) {
        if (s.contains("O-O")) {
            return getCastlingCan(this.getActiveColor(), s.equals("O-O"));
        }
        return s;
    }
}
