/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.AssetManager
 */
package de.cisha.android.board.service;

import android.content.Context;
import android.content.res.AssetManager;
import de.cisha.android.board.analyze.model.OpeningMoveInformation;
import de.cisha.android.board.analyze.model.OpeningPositionInformation;
import de.cisha.android.board.service.IOpeningTreeService;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.pieces.King;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.openingbook.BookMove;
import de.cisha.chess.openingbook.OpeningBook;
import de.cisha.chess.openingbook.OpeningBookBin;
import de.cisha.chess.util.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class OpeningTreeServicePolyglotMemory
implements IOpeningTreeService {
    private static OpeningTreeServicePolyglotMemory _instance;
    private OpeningBook _book;
    private Context _context;

    private OpeningTreeServicePolyglotMemory(Context context) {
        this._context = context;
    }

    private SEP adjustCastlingForBinSEP(SEP sEP, Position serializable) {
        Piece piece = serializable.getPieceFor(Square.SQUARE_E1);
        Piece piece2 = serializable.getPieceFor(Square.SQUARE_E8);
        if (sEP.equals(new SEP("e1h1")) && piece instanceof King) {
            return new SEP("e1g1");
        }
        if (sEP.equals(new SEP("e1a1")) && piece instanceof King) {
            return new SEP("e1c1");
        }
        if (sEP.equals(new SEP("e8h8")) && piece2 instanceof King) {
            return new SEP("e8g8");
        }
        serializable = sEP;
        if (sEP.equals(new SEP("e8a8"))) {
            serializable = sEP;
            if (piece2 instanceof King) {
                serializable = new SEP("e8c8");
            }
        }
        return serializable;
    }

    public static IOpeningTreeService getInstance(Context object) {
        synchronized (OpeningTreeServicePolyglotMemory.class) {
            if (_instance == null) {
                _instance = new OpeningTreeServicePolyglotMemory((Context)object);
            }
            object = _instance;
            return object;
        }
    }

    private void openBook() {
        try {
            this._book = new OpeningBookBin(this._context.getAssets().open("books/book100k.bin"));
            return;
        }
        catch (IOException iOException) {
            Logger.getInstance().debug(OpeningTreeServicePolyglotMemory.class.getName(), IOException.class.getName(), iOException);
            return;
        }
    }

    @Override
    public void closeBook() {
        this._book = null;
    }

    @Override
    public OpeningPositionInformation getInformationForPosition(Position position) {
        if (this._book == null) {
            this.openBook();
        }
        Object object = this._book;
        Iterator iterator = new OpeningPositionInformation(new LinkedList<OpeningMoveInformation>());
        if (object != null) {
            iterator = object.getBookEntriesForFEN(position.getFEN());
            object = new ArrayList(iterator.size());
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                BookMove bookMove = (BookMove)iterator.next();
                Move move = position.createMoveFrom(this.adjustCastlingForBinSEP(bookMove.getSEP(), position));
                if (move == null) continue;
                int n = bookMove.getNumberOfGames();
                int n2 = bookMove.getWhiteWins();
                int n3 = bookMove.getBlackWins();
                float f = n2;
                float f2 = n;
                f2 = (float)n3 / f2;
                object.add(new OpeningMoveInformation(move, n, f, f2, 1.0f - (f /= f2) - f2, 0.0f, 0.0f));
            }
            return new OpeningPositionInformation((List<OpeningMoveInformation>)object);
        }
        return iterator;
    }
}
