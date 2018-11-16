// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.chess.model.Move;
import java.util.Iterator;
import de.cisha.chess.openingbook.BookMove;
import java.util.ArrayList;
import java.util.List;
import de.cisha.android.board.analyze.model.OpeningMoveInformation;
import java.util.LinkedList;
import de.cisha.android.board.analyze.model.OpeningPositionInformation;
import java.io.IOException;
import de.cisha.chess.util.Logger;
import de.cisha.chess.openingbook.OpeningBookBin;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.pieces.King;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.SEP;
import android.content.Context;
import de.cisha.chess.openingbook.OpeningBook;

public class OpeningTreeServicePolyglotMemory implements IOpeningTreeService
{
    private static OpeningTreeServicePolyglotMemory _instance;
    private OpeningBook _book;
    private Context _context;
    
    private OpeningTreeServicePolyglotMemory(final Context context) {
        this._context = context;
    }
    
    private SEP adjustCastlingForBinSEP(final SEP sep, final Position position) {
        final Piece piece = position.getPieceFor(Square.SQUARE_E1);
        final Piece piece2 = position.getPieceFor(Square.SQUARE_E8);
        if (sep.equals(new SEP("e1h1")) && piece instanceof King) {
            return new SEP("e1g1");
        }
        if (sep.equals(new SEP("e1a1")) && piece instanceof King) {
            return new SEP("e1c1");
        }
        if (sep.equals(new SEP("e8h8")) && piece2 instanceof King) {
            return new SEP("e8g8");
        }
        SEP sep2 = sep;
        if (sep.equals(new SEP("e8a8"))) {
            sep2 = sep;
            if (piece2 instanceof King) {
                sep2 = new SEP("e8c8");
            }
        }
        return sep2;
    }
    
    public static IOpeningTreeService getInstance(final Context context) {
        synchronized (OpeningTreeServicePolyglotMemory.class) {
            if (OpeningTreeServicePolyglotMemory._instance == null) {
                OpeningTreeServicePolyglotMemory._instance = new OpeningTreeServicePolyglotMemory(context);
            }
            return OpeningTreeServicePolyglotMemory._instance;
        }
    }
    
    private void openBook() {
        try {
            this._book = new OpeningBookBin(this._context.getAssets().open("books/book100k.bin"));
        }
        catch (IOException ex) {
            Logger.getInstance().debug(OpeningTreeServicePolyglotMemory.class.getName(), IOException.class.getName(), ex);
        }
    }
    
    @Override
    public void closeBook() {
        this._book = null;
    }
    
    @Override
    public OpeningPositionInformation getInformationForPosition(final Position position) {
        if (this._book == null) {
            this.openBook();
        }
        final OpeningBook book = this._book;
        final OpeningPositionInformation openingPositionInformation = new OpeningPositionInformation(new LinkedList<OpeningMoveInformation>());
        if (book != null) {
            final List<BookMove> bookEntriesForFEN = book.getBookEntriesForFEN(position.getFEN());
            final ArrayList list = new ArrayList<OpeningMoveInformation>(bookEntriesForFEN.size());
            for (final BookMove bookMove : bookEntriesForFEN) {
                final Move move = position.createMoveFrom(this.adjustCastlingForBinSEP(bookMove.getSEP(), position));
                if (move != null) {
                    final int numberOfGames = bookMove.getNumberOfGames();
                    final int whiteWins = bookMove.getWhiteWins();
                    final int blackWins = bookMove.getBlackWins();
                    final float n = whiteWins;
                    final float n2 = numberOfGames;
                    final float n3 = n / n2;
                    final float n4 = blackWins / n2;
                    list.add(new OpeningMoveInformation(move, numberOfGames, n3, n4, 1.0f - n3 - n4, 0.0f, 0.0f));
                }
            }
            return new OpeningPositionInformation((List<OpeningMoveInformation>)list);
        }
        return openingPositionInformation;
    }
}
