/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.util;

import de.cisha.chess.model.BaseObject;
import de.cisha.chess.model.Country;
import de.cisha.chess.model.CountryImpl;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameHolder;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.Opponent;
import de.cisha.chess.model.PGNGame;
import de.cisha.chess.model.Rating;
import de.cisha.chess.model.Tournament;
import de.cisha.chess.model.position.MutablePosition;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.util.PGNReaderDelegate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class PGNReader {
    private static final String[] ALL_GAME_TERMINATION_MARKERS = new String[]{"0-1", "1-0", "1/2-1/2", "1/2", "*"};
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

    private PGNGame createGameFromHeader(String string) {
        PGNGame pGNGame = new PGNGame();
        StringTokenizer stringTokenizer = new StringTokenizer(string, "\n");
        while (stringTokenizer.hasMoreTokens()) {
            String string2 = stringTokenizer.nextToken().trim();
            if (string2.startsWith(HEADERFIELD_START) && string2.endsWith(HEADERFIELD_END) && string2.indexOf(32) > 1) {
                String string3 = string2.substring(1, string2.indexOf(32));
                int n = string2.indexOf(34);
                int n2 = n + 1;
                int n3 = string2.indexOf(34, n2);
                if (n != -1 && n3 != -1) {
                    string2 = string2.substring(n2, n3);
                    if (EVENT.equals(string3)) {
                        this.parseHeaderTournament(pGNGame, string2);
                    } else if (SITE.equals(string3)) {
                        pGNGame.setSite(string2);
                    } else if (DATE.equals(string3)) {
                        this.parseHeaderDate(pGNGame, string2);
                    } else if (ROUND.equals(string3)) {
                        this.parseHeaderRound(pGNGame, string2);
                    } else if (BOARD.equals(string3)) {
                        this.parseHeaderBoard(pGNGame, string2);
                    } else if (WHITE.equals(string3)) {
                        this.parseHeaderWhite(pGNGame, string2);
                    } else if (BLACK.equals(string3)) {
                        this.parseHeaderBlack(pGNGame, string2);
                    } else if (RESULT.equals(string3)) {
                        this.parseHeaderResult(pGNGame, string2);
                    } else if (WHITEELO.equals(string3)) {
                        this.parseWhiteElo(pGNGame, string2);
                    } else if (BLACKELO.equals(string3)) {
                        this.parseBlackElo(pGNGame, string2);
                    } else if (FENSTRING.equals(string3)) {
                        this.parseHeaderFEN(pGNGame, string2);
                    } else if (WHITECOUNTRY.equals(string3)) {
                        this.parseWhiteCountry(pGNGame, string2);
                    } else if (BLACKCOUNTRY.equals(string3)) {
                        this.parseBlackCountry(pGNGame, string2);
                    } else if (WHITETITLE.equals(string3)) {
                        this.parseWhiteTitle(pGNGame, string2);
                    } else if (BLACKTITLE.equals(string3)) {
                        this.parseBlackTitle(pGNGame, string2);
                    } else {
                        ANNOTATOR.equals(string3);
                    }
                }
            }
            pGNGame.setHeaderString(string);
        }
        return pGNGame;
    }

    private Move createMove(String string, MutablePosition mutablePosition) {
        return mutablePosition.createMoveFromSAN(string);
    }

    private Opponent getBlackOpponentSave(PGNGame pGNGame) {
        Opponent opponent;
        Opponent opponent2 = opponent = pGNGame.getBlackPlayer();
        if (opponent == null) {
            opponent2 = new Opponent();
            pGNGame.setBlackPlayer(opponent2);
        }
        return opponent2;
    }

    private String getGameDataString(String string, int n) {
        int n2 = string.indexOf(HEADERFIELD_START, n);
        int n3 = string.indexOf(COMMENT_START, n);
        string.indexOf(COMMENT_END, n);
        while (n2 > n3 && n3 != -1) {
            int n4 = string.indexOf(COMMENT_END, n3);
            if (n4 > n2) {
                n2 = string.indexOf(HEADERFIELD_START, n4);
                continue;
            }
            n3 = string.indexOf(COMMENT_START, n4);
        }
        if (n2 == -1) {
            return string.substring(n);
        }
        return string.substring(n, n2);
    }

    private String getSymbol(int n) {
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
                return "\u00b1";
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
            case 1: 
        }
        return "!";
    }

    private int getSymbolType(int n) {
        if (n > 0 && n <= 9) {
            return 1;
        }
        if (n >= 10 && n < 105 || n >= 130 && n <= 135) {
            return 2;
        }
        return 3;
    }

    private Opponent getWhiteOpponentSave(PGNGame pGNGame) {
        Opponent opponent;
        Opponent opponent2 = opponent = pGNGame.getWhitePlayer();
        if (opponent == null) {
            opponent2 = new Opponent();
            pGNGame.setWhitePlayer(opponent2);
        }
        return opponent2;
    }

    private boolean isComment(String string) {
        if (string != null) {
            return string.startsWith(COMMENT_START);
        }
        return false;
    }

    private boolean isMove(String string) {
        char c;
        if (string != null && string.length() >= 2 && ((c = string.charAt(0)) == 'B' || c == 'N' || c == 'K' || c == 'Q' || c == 'R' || c == 'O' || c >= 'a' && c <= 'h')) {
            return true;
        }
        return false;
    }

    private boolean isSymbol(String string) {
        if (string != null && string.startsWith("%")) {
            return true;
        }
        return false;
    }

    private boolean isTerminationToken(String string) {
        if (string != null && string.endsWith(VARIATION_END)) {
            return true;
        }
        for (int i = 0; i < ALL_GAME_TERMINATION_MARKERS.length; ++i) {
            if (!ALL_GAME_TERMINATION_MARKERS[i].equals(string)) continue;
            return true;
        }
        return false;
    }

    private boolean isVariation(String string) {
        if (string != null) {
            return string.startsWith(VARIATION_START);
        }
        return false;
    }

    private void parseBlackCountry(PGNGame pGNGame, String string) {
        this.getBlackOpponentSave(pGNGame).setCountry(this.getCountryForString(string));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void parseBlackElo(PGNGame baseObject, String string) {
        int n;
        block2 : {
            baseObject = this.getBlackOpponentSave((PGNGame)baseObject);
            try {
                n = Integer.parseInt(string);
                break block2;
            }
            catch (NumberFormatException numberFormatException) {}
            n = 0;
        }
        baseObject.setRating(new Rating(n));
    }

    private void parseBlackTitle(PGNGame pGNGame, String string) {
        this.getBlackOpponentSave(pGNGame).setTitle(string);
    }

    private void parseHeaderBlack(PGNGame pGNGame, String string) {
        this.getBlackOpponentSave(pGNGame).setName(string);
    }

    private void parseHeaderBoard(PGNGame pGNGame, String string) {
        try {
            pGNGame.setBoard(Integer.valueOf(string));
            return;
        }
        catch (NumberFormatException numberFormatException) {
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void parseHeaderDate(PGNGame pGNGame, String string) {
        if (string.length() != 10) return;
        try {
            Date date = new Date();
            pGNGame.setStartDate(date);
            date.setYear(Integer.valueOf(string.substring(0, 4)) - 1900);
            date.setMonth(Integer.valueOf(string.substring(5, 7)));
            date.setDate(Integer.valueOf(string.substring(8, 10)));
            return;
        }
        catch (NumberFormatException numberFormatException) {
            return;
        }
    }

    private void parseHeaderFEN(PGNGame pGNGame, String object) {
        if ((object = new FEN((String)object)).isValid()) {
            pGNGame.setStartingPosition(new Position((FEN)object));
        }
    }

    private void parseHeaderResult(PGNGame pGNGame, String string) {
        if (string.equals(GAME_TERMINATION_WHITEWINS)) {
            pGNGame.setResult(new GameStatus(GameResult.WHITE_WINS, GameEndReason.NO_REASON));
            return;
        }
        if (string.equals(GAME_TERMINATION_BLACKWINS)) {
            pGNGame.setResult(new GameStatus(GameResult.BLACK_WINS, GameEndReason.NO_REASON));
            return;
        }
        if (!string.equals(GAME_TERMINATION_DRAW) && !string.equals(GAME_TERMINATION_DRAW_2)) {
            pGNGame.setResult(GameStatus.GAME_RUNNING);
            return;
        }
        pGNGame.setResult(new GameStatus(GameResult.DRAW, GameEndReason.NO_REASON));
    }

    private void parseHeaderRound(PGNGame pGNGame, String string) {
        try {
            pGNGame.setRound(Integer.valueOf(string));
            return;
        }
        catch (NumberFormatException numberFormatException) {
            return;
        }
    }

    private void parseHeaderTournament(PGNGame pGNGame, String string) {
        Tournament tournament;
        Tournament tournament2 = tournament = pGNGame.getTournament();
        if (tournament == null) {
            tournament2 = new Tournament();
            pGNGame.setTournament(tournament2);
        }
        tournament2.setName(string);
    }

    private void parseHeaderWhite(PGNGame pGNGame, String string) {
        this.getWhiteOpponentSave(pGNGame).setName(string);
    }

    private void parseWhiteCountry(PGNGame pGNGame, String string) {
        this.getWhiteOpponentSave(pGNGame).setCountry(this.getCountryForString(string));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void parseWhiteElo(PGNGame baseObject, String string) {
        int n;
        block2 : {
            baseObject = this.getWhiteOpponentSave((PGNGame)baseObject);
            try {
                n = Integer.parseInt(string);
                break block2;
            }
            catch (NumberFormatException numberFormatException) {}
            n = 0;
        }
        baseObject.setRating(new Rating(n));
    }

    private void parseWhiteTitle(PGNGame pGNGame, String string) {
        this.getWhiteOpponentSave(pGNGame).setTitle(string);
    }

    private String readToken(String string, int n, String string2) {
        int n2 = string.length() + 1;
        int n3 = n2;
        for (int i = 0; i < string2.length(); ++i) {
            int n4 = string.indexOf(string2.charAt(i), n);
            int n5 = n3;
            if (n4 >= n) {
                n5 = n3;
                if (n4 < n3) {
                    n5 = n4;
                }
            }
            n3 = n5;
        }
        if (n3 >= n && n3 != n2) {
            return string.substring(n, n3 + 1);
        }
        return null;
    }

    protected Country getCountryForString(String string) {
        if (string != null) {
            return new CountryImpl(string, string, string, 0, 0);
        }
        return null;
    }

    public void readGameDataBlock(Game game, String string, PGNReaderFinishDelegate pGNReaderFinishDelegate, GameHolder gameHolder) {
        this.readMoveBlock(game, string, 0, gameHolder);
        if (pGNReaderFinishDelegate != null) {
            pGNReaderFinishDelegate.finishedReadingMoves();
        }
    }

    /*
     * Exception decompiling
     */
    protected int readMoveBlock(MoveContainer var1_1, String var2_2, int var3_3, GameHolder var4_4) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:367)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:66)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:374)
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

    @Deprecated
    public List<Game> readPGN(String string) {
        ArrayList<Game> arrayList = new ArrayList<Game>(10);
        String string2 = string.replace("\r", "");
        int n = 0;
        do {
            int n2;
            if ((n2 = string2.indexOf("\n\n", n)) > n) {
                string = string2.substring(n, n2);
                if (string != null && string.trim().startsWith(HEADERFIELD_START)) {
                    PGNGame pGNGame = this.createGameFromHeader(string);
                    arrayList.add(pGNGame);
                    n = this.readMoveBlock(pGNGame, string2, n2 + 2, null);
                    continue;
                }
                ++n;
                continue;
            }
            string = null;
        } while (string != null && !string.trim().equals(""));
        return arrayList;
    }

    public void readPGN(String string, PGNReaderDelegate pGNReaderDelegate) {
        String string2 = string.replace("\r", "");
        int n = 0;
        do {
            int n2;
            if ((n2 = string2.indexOf("\n\n", n)) > n) {
                string = string2.substring(n, n2);
                if (string != null && string.trim().startsWith(HEADERFIELD_START)) {
                    PGNGame pGNGame = this.createGameFromHeader(string);
                    String string3 = this.getGameDataString(string2, n2 += 2);
                    if (string3 != null) {
                        n = string3.length() + n2;
                        pGNGame.setGamePGNString(string3);
                    }
                    pGNReaderDelegate.addPGNGame(pGNGame);
                    continue;
                }
                ++n;
                continue;
            }
            string = null;
        } while (string != null && !string.trim().equals(""));
        pGNReaderDelegate.finishedReadingPGN();
    }

    public void readSingleGame(String string, GameHolder gameHolder, PGNReaderFinishDelegate pGNReaderFinishDelegate) {
        Object object;
        int n = string.indexOf("\n\n");
        if (n > 0 && (object = string.substring(0, n)) != null && object.trim().startsWith(HEADERFIELD_START)) {
            object = this.createGameFromHeader((String)object);
            gameHolder.setNewGame((Game)object);
            string = this.getGameDataString(string, n + 2);
            if (string != null) {
                this.readGameDataBlock((Game)object, string, pGNReaderFinishDelegate, gameHolder);
                return;
            }
            if (pGNReaderFinishDelegate != null) {
                pGNReaderFinishDelegate.finishedReadingMoves();
            }
        }
    }

    public static interface PGNReaderFinishDelegate {
        public void finishedReadingMoves();
    }

}
