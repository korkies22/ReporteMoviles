// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.util;

import java.util.ArrayList;
import java.util.List;
import java.io.PrintStream;
import de.cisha.chess.model.position.AbstractPosition;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.GameHolder;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.CountryImpl;
import de.cisha.chess.model.Country;
import de.cisha.chess.model.Tournament;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.FEN;
import java.util.Date;
import de.cisha.chess.model.Rating;
import de.cisha.chess.model.Opponent;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.position.MutablePosition;
import java.util.StringTokenizer;
import de.cisha.chess.model.PGNGame;

public class PGNReader
{
    private static final String[] ALL_GAME_TERMINATION_MARKERS;
    private static final String ANNOTATOR = "Annotator";
    private static final String BLACK = "Black";
    private static final String BLACKCOUNTRY = "BlackCountry";
    private static final String BLACKELO = "BlackElo";
    private static final String BLACKFIDE_ID = "BlackFideId";
    private static final String BLACKTITLE = "BlackTitle";
    private static final String BLACK_TEAM = "BlackClub";
    private static final String BOARD = "Board";
    private static final String COMMENT_END = "}";
    private static final String COMMENT_START = "{";
    private static final String DATE = "Date";
    private static final String EVENT = "Event";
    private static final String FENSTRING = "FEN";
    private static final String GAME_TERMINATION_BLACKWINS = "0-1";
    private static final String GAME_TERMINATION_DRAW = "1/2-1/2";
    private static final String GAME_TERMINATION_DRAW_2 = "1/2";
    private static final String GAME_TERMINATION_RUNNING = "*";
    private static final String GAME_TERMINATION_WHITEWINS = "1-0";
    private static final String HEADERFIELD_END = "]";
    private static final String HEADERFIELD_START = "[";
    private static final String RESULT = "Result";
    private static final String ROUND = "Round";
    private static final String SITE = "Site";
    private static final int SYMBOL_TYPE_MISC = 3;
    private static final int SYMBOL_TYPE_MOVE = 1;
    private static final int SYMBOL_TYPE_POSITION = 2;
    private static final String VARIATION_END = ")";
    private static final String VARIATION_START = "(";
    private static final String WHITE = "White";
    private static final String WHITECOUNTRY = "WhiteCountry";
    private static final String WHITEELO = "WhiteElo";
    private static final String WHITEFIDE_ID = "WhiteFideId";
    private static final String WHITETITLE = "WhiteTitle";
    private static final String WHITE_TEAM = "WhiteClub";
    
    static {
        ALL_GAME_TERMINATION_MARKERS = new String[] { "0-1", "1-0", "1/2-1/2", "1/2", "*" };
    }
    
    private PGNGame createGameFromHeader(final String headerString) {
        final PGNGame pgnGame = new PGNGame();
        final StringTokenizer stringTokenizer = new StringTokenizer(headerString, "\n");
        while (stringTokenizer.hasMoreTokens()) {
            final String trim = stringTokenizer.nextToken().trim();
            if (trim.startsWith("[") && trim.endsWith("]") && trim.indexOf(32) > 1) {
                final String substring = trim.substring(1, trim.indexOf(32));
                final int index = trim.indexOf(34);
                final int n = index + 1;
                final int index2 = trim.indexOf(34, n);
                if (index != -1 && index2 != -1) {
                    final String substring2 = trim.substring(n, index2);
                    if ("Event".equals(substring)) {
                        this.parseHeaderTournament(pgnGame, substring2);
                    }
                    else if ("Site".equals(substring)) {
                        pgnGame.setSite(substring2);
                    }
                    else if ("Date".equals(substring)) {
                        this.parseHeaderDate(pgnGame, substring2);
                    }
                    else if ("Round".equals(substring)) {
                        this.parseHeaderRound(pgnGame, substring2);
                    }
                    else if ("Board".equals(substring)) {
                        this.parseHeaderBoard(pgnGame, substring2);
                    }
                    else if ("White".equals(substring)) {
                        this.parseHeaderWhite(pgnGame, substring2);
                    }
                    else if ("Black".equals(substring)) {
                        this.parseHeaderBlack(pgnGame, substring2);
                    }
                    else if ("Result".equals(substring)) {
                        this.parseHeaderResult(pgnGame, substring2);
                    }
                    else if ("WhiteElo".equals(substring)) {
                        this.parseWhiteElo(pgnGame, substring2);
                    }
                    else if ("BlackElo".equals(substring)) {
                        this.parseBlackElo(pgnGame, substring2);
                    }
                    else if ("FEN".equals(substring)) {
                        this.parseHeaderFEN(pgnGame, substring2);
                    }
                    else if ("WhiteCountry".equals(substring)) {
                        this.parseWhiteCountry(pgnGame, substring2);
                    }
                    else if ("BlackCountry".equals(substring)) {
                        this.parseBlackCountry(pgnGame, substring2);
                    }
                    else if ("WhiteTitle".equals(substring)) {
                        this.parseWhiteTitle(pgnGame, substring2);
                    }
                    else if ("BlackTitle".equals(substring)) {
                        this.parseBlackTitle(pgnGame, substring2);
                    }
                    else {
                        "Annotator".equals(substring);
                    }
                }
            }
            pgnGame.setHeaderString(headerString);
        }
        return pgnGame;
    }
    
    private Move createMove(final String s, final MutablePosition mutablePosition) {
        return mutablePosition.createMoveFromSAN(s);
    }
    
    private Opponent getBlackOpponentSave(final PGNGame pgnGame) {
        Opponent blackPlayer;
        if ((blackPlayer = pgnGame.getBlackPlayer()) == null) {
            blackPlayer = new Opponent();
            pgnGame.setBlackPlayer(blackPlayer);
        }
        return blackPlayer;
    }
    
    private String getGameDataString(final String s, final int n) {
        int n2 = s.indexOf("[", n);
        int n3 = s.indexOf("{", n);
        s.indexOf("}", n);
        while (n2 > n3 && n3 != -1) {
            final int index = s.indexOf("}", n3);
            if (index > n2) {
                n2 = s.indexOf("[", index);
            }
            else {
                n3 = s.indexOf("{", index);
            }
        }
        if (n2 == -1) {
            return s.substring(n);
        }
        return s.substring(n, n2);
    }
    
    private String getSymbol(final int n) {
        switch (n) {
            default: {
                return "";
            }
            case 22:
            case 23: {
                return "\u2299";
            }
            case 21: {
                return "-+";
            }
            case 20: {
                return "+-";
            }
            case 19: {
                return "-+";
            }
            case 18: {
                return "+-";
            }
            case 17: {
                return "-/+";
            }
            case 16: {
                return "±";
            }
            case 15: {
                return "=/+";
            }
            case 14: {
                return "+/=";
            }
            case 13: {
                return "\u221e";
            }
            case 12: {
                return "=";
            }
            case 11: {
                return "=";
            }
            case 10: {
                return "=";
            }
            case 9: {
                return "???";
            }
            case 7:
            case 8: {
                return "\u25a1";
            }
            case 6: {
                return "?!";
            }
            case 5: {
                return "!?";
            }
            case 4: {
                return "??";
            }
            case 3: {
                return "!!";
            }
            case 2: {
                return "?";
            }
            case 1: {
                return "!";
            }
        }
    }
    
    private int getSymbolType(final int n) {
        if (n > 0 && n <= 9) {
            return 1;
        }
        if ((n >= 10 && n < 105) || (n >= 130 && n <= 135)) {
            return 2;
        }
        return 3;
    }
    
    private Opponent getWhiteOpponentSave(final PGNGame pgnGame) {
        Opponent whitePlayer;
        if ((whitePlayer = pgnGame.getWhitePlayer()) == null) {
            whitePlayer = new Opponent();
            pgnGame.setWhitePlayer(whitePlayer);
        }
        return whitePlayer;
    }
    
    private boolean isComment(final String s) {
        return s != null && s.startsWith("{");
    }
    
    private boolean isMove(final String s) {
        if (s != null && s.length() >= 2) {
            final char char1 = s.charAt(0);
            if (char1 == 'B' || char1 == 'N' || char1 == 'K' || char1 == 'Q' || char1 == 'R' || char1 == 'O' || (char1 >= 'a' && char1 <= 'h')) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isSymbol(final String s) {
        return s != null && s.startsWith("%");
    }
    
    private boolean isTerminationToken(final String s) {
        if (s != null && s.endsWith(")")) {
            return true;
        }
        for (int i = 0; i < PGNReader.ALL_GAME_TERMINATION_MARKERS.length; ++i) {
            if (PGNReader.ALL_GAME_TERMINATION_MARKERS[i].equals(s)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isVariation(final String s) {
        return s != null && s.startsWith("(");
    }
    
    private void parseBlackCountry(final PGNGame pgnGame, final String s) {
        this.getBlackOpponentSave(pgnGame).setCountry(this.getCountryForString(s));
    }
    
    private void parseBlackElo(PGNGame blackOpponentSave, final String s) {
        blackOpponentSave = (PGNGame)this.getBlackOpponentSave(blackOpponentSave);
        while (true) {
            try {
                int int1 = Integer.parseInt(s);
                while (true) {
                    ((Opponent)blackOpponentSave).setRating(new Rating(int1));
                    return;
                    int1 = 0;
                    continue;
                }
            }
            catch (NumberFormatException ex) {}
            continue;
        }
    }
    
    private void parseBlackTitle(final PGNGame pgnGame, final String title) {
        this.getBlackOpponentSave(pgnGame).setTitle(title);
    }
    
    private void parseHeaderBlack(final PGNGame pgnGame, final String name) {
        this.getBlackOpponentSave(pgnGame).setName(name);
    }
    
    private void parseHeaderBoard(final PGNGame pgnGame, final String s) {
        try {
            pgnGame.setBoard(Integer.valueOf(s));
        }
        catch (NumberFormatException ex) {}
    }
    
    private void parseHeaderDate(final PGNGame pgnGame, final String s) {
        if (s.length() != 10) {
            return;
        }
        try {
            final Date startDate = new Date();
            pgnGame.setStartDate(startDate);
            startDate.setYear(Integer.valueOf(s.substring(0, 4)) - 1900);
            startDate.setMonth(Integer.valueOf(s.substring(5, 7)));
            startDate.setDate(Integer.valueOf(s.substring(8, 10)));
        }
        catch (NumberFormatException ex) {}
    }
    
    private void parseHeaderFEN(final PGNGame pgnGame, final String s) {
        final FEN fen = new FEN(s);
        if (fen.isValid()) {
            pgnGame.setStartingPosition(new Position(fen));
        }
    }
    
    private void parseHeaderResult(final PGNGame pgnGame, final String s) {
        if (s.equals("1-0")) {
            pgnGame.setResult(new GameStatus(GameResult.WHITE_WINS, GameEndReason.NO_REASON));
            return;
        }
        if (s.equals("0-1")) {
            pgnGame.setResult(new GameStatus(GameResult.BLACK_WINS, GameEndReason.NO_REASON));
            return;
        }
        if (!s.equals("1/2-1/2") && !s.equals("1/2")) {
            pgnGame.setResult(GameStatus.GAME_RUNNING);
            return;
        }
        pgnGame.setResult(new GameStatus(GameResult.DRAW, GameEndReason.NO_REASON));
    }
    
    private void parseHeaderRound(final PGNGame pgnGame, final String s) {
        try {
            pgnGame.setRound(Integer.valueOf(s));
        }
        catch (NumberFormatException ex) {}
    }
    
    private void parseHeaderTournament(final PGNGame pgnGame, final String name) {
        Tournament tournament;
        if ((tournament = pgnGame.getTournament()) == null) {
            tournament = new Tournament();
            pgnGame.setTournament(tournament);
        }
        tournament.setName(name);
    }
    
    private void parseHeaderWhite(final PGNGame pgnGame, final String name) {
        this.getWhiteOpponentSave(pgnGame).setName(name);
    }
    
    private void parseWhiteCountry(final PGNGame pgnGame, final String s) {
        this.getWhiteOpponentSave(pgnGame).setCountry(this.getCountryForString(s));
    }
    
    private void parseWhiteElo(PGNGame whiteOpponentSave, final String s) {
        whiteOpponentSave = (PGNGame)this.getWhiteOpponentSave(whiteOpponentSave);
        while (true) {
            try {
                int int1 = Integer.parseInt(s);
                while (true) {
                    ((Opponent)whiteOpponentSave).setRating(new Rating(int1));
                    return;
                    int1 = 0;
                    continue;
                }
            }
            catch (NumberFormatException ex) {}
            continue;
        }
    }
    
    private void parseWhiteTitle(final PGNGame pgnGame, final String title) {
        this.getWhiteOpponentSave(pgnGame).setTitle(title);
    }
    
    private String readToken(final String s, final int n, final String s2) {
        final int n2 = s.length() + 1;
        int i = 0;
        int n3 = n2;
        while (i < s2.length()) {
            final int index = s.indexOf(s2.charAt(i), n);
            int n4 = n3;
            if (index >= n && index < (n4 = n3)) {
                n4 = index;
            }
            ++i;
            n3 = n4;
        }
        if (n3 >= n && n3 != n2) {
            return s.substring(n, n3 + 1);
        }
        return null;
    }
    
    protected Country getCountryForString(final String s) {
        if (s != null) {
            return new CountryImpl(s, s, s, 0, 0);
        }
        return null;
    }
    
    public void readGameDataBlock(final Game game, final String s, final PGNReaderFinishDelegate pgnReaderFinishDelegate, final GameHolder gameHolder) {
        this.readMoveBlock(game, s, 0, gameHolder);
        if (pgnReaderFinishDelegate != null) {
            pgnReaderFinishDelegate.finishedReadingMoves();
        }
    }
    
    protected int readMoveBlock(MoveContainer moveContainer, final String s, int n, final GameHolder gameHolder) {
        final boolean b = gameHolder != null;
        final MutablePosition mutablePosition = new MutablePosition(moveContainer.getResultingPosition());
        int moveBlock = n;
    Label_0588_Outer:
        while (true) {
            String s2 = this.readToken(s, moveBlock, " \n.");
            Label_0582: {
                if (s2 == null) {
                    break Label_0582;
                }
                int length = s2.length();
                s2 = s2.trim();
                n = 0;
                while (s2.endsWith(")")) {
                    String substring = s2;
                    int n2;
                    if ((n2 = length) > 1) {
                        substring = s2.substring(0, s2.length() - 1);
                        n2 = substring.length() + 1;
                    }
                    if (substring.startsWith("{")) {
                        s2 = substring;
                        length = n2;
                        if (substring.indexOf("}") == -1) {
                            continue Label_0588_Outer;
                        }
                    }
                    n = 1;
                    s2 = substring;
                    length = n2;
                }
            Label_0588:
                while (true) {
                    if (this.isMove(s2)) {
                        final Move move = this.createMove(s2.replace("\n", ""), mutablePosition);
                        if (move != null) {
                            if (b) {
                                gameHolder.addMove(moveContainer, move);
                            }
                            else {
                                moveContainer.addMove(move);
                            }
                            mutablePosition.doMove(move);
                            moveContainer = move;
                        }
                        else {
                            final PrintStream out = System.out;
                            final StringBuilder sb = new StringBuilder();
                            sb.append("de.cisha.android.board.PGNReader:error creating move: ");
                            sb.append(s2);
                            sb.append(" at char ");
                            sb.append(moveBlock);
                            sb.append("");
                            out.println(sb.toString());
                        }
                        moveBlock += length;
                        length = n;
                        n = moveBlock;
                        break Label_0588;
                    }
                    if (this.isComment(s2)) {
                        final int index = s.indexOf("}", moveBlock);
                        final String substring2 = s.substring(moveBlock + 1, index);
                        final int n3 = index + 1;
                        moveContainer.setComment(substring2);
                        moveBlock = n3;
                        if (n != 0) {
                            moveBlock = n3 + 1;
                        }
                        length = n;
                        n = moveBlock;
                        break Label_0588;
                    }
                    if (this.isVariation(s2)) {
                        moveBlock = this.readMoveBlock(moveContainer.getParent(), s, s.indexOf("(", moveBlock) + 1, gameHolder);
                        length = n;
                        n = moveBlock;
                        break Label_0588;
                    }
                    Label_0566: {
                        if (!this.isSymbol(s2)) {
                            break Label_0566;
                        }
                        int intValue;
                        String symbol;
                        PrintStream out2;
                        StringBuilder sb2;
                        Label_0509_Outer:Label_0476_Outer:
                        while (true) {
                            while (true) {
                                Label_0617: {
                                    while (true) {
                                        try {
                                            intValue = Integer.valueOf(s2.substring(1));
                                            symbol = this.getSymbol(intValue);
                                            switch (this.getSymbolType(intValue)) {
                                                case 2: {
                                                    moveContainer.setSymbolPosition(symbol);
                                                    break;
                                                }
                                                case 1: {
                                                    moveContainer.setSymbolMove(symbol);
                                                    break;
                                                }
                                                default: {
                                                    break Label_0617;
                                                }
                                            }
                                            while (true) {
                                                moveBlock += length;
                                                length = n;
                                                n = moveBlock;
                                                if (s2 != null && !this.isTerminationToken(s2)) {
                                                    moveBlock = n;
                                                    if (length == 0) {
                                                        break;
                                                    }
                                                }
                                                return n;
                                                out2 = System.out;
                                                sb2 = new StringBuilder();
                                                sb2.append("de.cisha.android.board.util.PGNReader: Illegal symbol token: ");
                                                sb2.append(s2);
                                                out2.println(sb2.toString());
                                                continue Label_0509_Outer;
                                                length = 0;
                                                n = moveBlock;
                                                continue Label_0588;
                                                moveContainer.setSymbolMisc(symbol);
                                                continue Label_0509_Outer;
                                            }
                                            moveBlock += length;
                                            length = n;
                                            n = moveBlock;
                                            continue Label_0588;
                                        }
                                        catch (NumberFormatException ex) {}
                                        continue Label_0476_Outer;
                                    }
                                }
                                continue;
                            }
                        }
                    }
                    break;
                }
            }
        }
    }
    
    @Deprecated
    public List<Game> readPGN(String substring) {
        final ArrayList<PGNGame> list = (ArrayList<PGNGame>)new ArrayList<Game>(10);
        final String replace = substring.replace("\r", "");
        int moveBlock = 0;
        do {
            final int index = replace.indexOf("\n\n", moveBlock);
            if (index > moveBlock) {
                substring = replace.substring(moveBlock, index);
                if (substring != null && substring.trim().startsWith("[")) {
                    final PGNGame gameFromHeader = this.createGameFromHeader(substring);
                    list.add(gameFromHeader);
                    moveBlock = this.readMoveBlock(gameFromHeader, replace, index + 2, null);
                }
                else {
                    ++moveBlock;
                }
            }
            else {
                substring = null;
            }
        } while (substring != null && !substring.trim().equals(""));
        return (List<Game>)list;
    }
    
    public void readPGN(String substring, final PGNReaderDelegate pgnReaderDelegate) {
        final String replace = substring.replace("\r", "");
        int n = 0;
        do {
            final int index = replace.indexOf("\n\n", n);
            if (index > n) {
                substring = replace.substring(n, index);
                if (substring != null && substring.trim().startsWith("[")) {
                    final PGNGame gameFromHeader = this.createGameFromHeader(substring);
                    final int n2 = index + 2;
                    final String gameDataString = this.getGameDataString(replace, n2);
                    if (gameDataString != null) {
                        n = gameDataString.length() + n2;
                        gameFromHeader.setGamePGNString(gameDataString);
                    }
                    pgnReaderDelegate.addPGNGame(gameFromHeader);
                }
                else {
                    ++n;
                }
            }
            else {
                substring = null;
            }
        } while (substring != null && !substring.trim().equals(""));
        pgnReaderDelegate.finishedReadingPGN();
    }
    
    public void readSingleGame(String gameDataString, final GameHolder gameHolder, final PGNReaderFinishDelegate pgnReaderFinishDelegate) {
        final int index = gameDataString.indexOf("\n\n");
        if (index > 0) {
            final String substring = gameDataString.substring(0, index);
            if (substring != null && substring.trim().startsWith("[")) {
                final PGNGame gameFromHeader = this.createGameFromHeader(substring);
                gameHolder.setNewGame(gameFromHeader);
                gameDataString = this.getGameDataString(gameDataString, index + 2);
                if (gameDataString != null) {
                    this.readGameDataBlock(gameFromHeader, gameDataString, pgnReaderFinishDelegate, gameHolder);
                    return;
                }
                if (pgnReaderFinishDelegate != null) {
                    pgnReaderFinishDelegate.finishedReadingMoves();
                }
            }
        }
    }
    
    public interface PGNReaderFinishDelegate
    {
        void finishedReadingMoves();
    }
}
