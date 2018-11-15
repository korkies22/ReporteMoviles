/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.connection.IConnection;
import de.cisha.android.board.broadcast.model.BroadcastGameStatus;
import de.cisha.android.board.broadcast.model.DummyTournamentGameConnection;
import de.cisha.android.board.broadcast.model.ITournamentConnection;
import de.cisha.android.board.broadcast.model.ITournamentGameConnection;
import de.cisha.android.board.broadcast.model.ITournamentService;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentGameID;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentID;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import de.cisha.android.board.broadcast.model.TournamentState;
import de.cisha.android.board.broadcast.model.jsonparsers.BroadcastTournamentType;
import de.cisha.chess.engine.EngineEvaluation;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class DummyTournamentService
implements ITournamentService {
    private static String[] _dummyMoves;
    private static String[] _dummyMoves2;
    private static TournamentPlayer[] _dummyNames;
    private static DummyTournamentService _instance;
    private ITournamentConnection _connection;

    static {
        _dummyNames = new TournamentPlayer[]{new TournamentPlayer("Simon", 1), new TournamentPlayer("Lennart", 2), new TournamentPlayer("Kim", 3), new TournamentPlayer("Pascal", 4), new TournamentPlayer("Wolfgang", 5), new TournamentPlayer("Beatrice", 6), new TournamentPlayer("Felix", 7)};
        _dummyMoves = new String[]{"e2e4", "e7e5"};
        _dummyMoves2 = new String[]{"e2e4", "e7e5", "g1f3"};
    }

    private DummyTournamentService() {
    }

    public static DummyTournamentService getInstance() {
        if (_instance == null) {
            _instance = new DummyTournamentService();
        }
        return _instance;
    }

    private ITournamentConnection startConnectionRequest(final ITournamentConnection.TournamentConnectionListener tournamentConnectionListener, final ITournamentConnection.TournamentChangeListener tournamentChangeListener) {
        this._connection = new ITournamentConnection(){
            private boolean _flagIsConnected = false;
            final Random _random = new Random();
            private Timer _timer = new Timer();
            final Tournament _tournament = new Tournament("tourni", "descri", BroadcastTournamentType.MULTIKNOCKOUT);

            @Override
            public void addConnectionListener(IConnection.IConnectionListener iConnectionListener) {
            }

            @Override
            public void close() {
                this._timer.cancel();
                this._flagIsConnected = false;
            }

            @Override
            public void connect() {
                this._flagIsConnected = true;
                this._timer.schedule(new TimerTask(){

                    @Override
                    public void run() {
                        tournamentConnectionListener.connectionEstablished(DummyTournamentService.this._connection);
                    }
                }, 300L);
            }

            @Override
            public boolean isConnected() {
                return this._flagIsConnected;
            }

            @Override
            public void loadTournament() {
                this._timer.schedule(new TimerTask(){

                    @Override
                    public void run() {
                        TournamentRoundInfo tournamentRoundInfo = new TournamentRoundInfo(){

                            @Override
                            public int compareTo(TournamentRoundInfo tournamentRoundInfo) {
                                return 0;
                            }

                            @Override
                            public TournamentRoundInfo getMainRound() {
                                return this;
                            }

                            @Override
                            public String getRoundString() {
                                return "R-1";
                            }
                        };
                        1.this._tournament.setCurrentRound(tournamentRoundInfo);
                        for (int i = 0; i < 20; ++i) {
                            String[] arrstring = new String[]();
                            arrstring.append("");
                            arrstring.append(i);
                            TournamentGameInfo tournamentGameInfo = new TournamentGameInfo(arrstring.toString(), _dummyNames[(int)((float)_dummyNames.length * 1.this._random.nextFloat())], _dummyNames[(int)((float)_dummyNames.length * 1.this._random.nextFloat())]);
                            arrstring = new Random().nextBoolean() ? _dummyMoves : _dummyMoves2;
                            tournamentGameInfo.setLastMovesEANsAndEarlierFEN(arrstring, FEN.STARTING_POSITION);
                            tournamentGameInfo.setRemainingTimeBlack(120000L);
                            tournamentGameInfo.setWhitePlayerLeftSide(1.this._random.nextBoolean());
                            tournamentGameInfo.setEngineEvaluation(new EngineEvaluation(14.0f * 1.this._random.nextFloat() - 7.0f));
                            tournamentGameInfo.setRemainingTimeWhite(120000L);
                            float f = 1.this._random.nextFloat();
                            double d = f;
                            if (d < 0.25) {
                                tournamentGameInfo.setStatus(new BroadcastGameStatus(GameResult.NO_RESULT, GameEndReason.NO_REASON, TournamentState.ONGOING));
                            } else if (f < 0.5f) {
                                tournamentGameInfo.setStatus(new BroadcastGameStatus(GameResult.BLACK_WINS, GameEndReason.MATE, TournamentState.FINISHED));
                            } else if (d < 0.75) {
                                tournamentGameInfo.setStatus(new BroadcastGameStatus(GameResult.DRAW, GameEndReason.DRAW_BY_MUTUAL_AGREEMENT, TournamentState.FINISHED));
                            } else {
                                tournamentGameInfo.setStatus(new BroadcastGameStatus(GameResult.WHITE_WINS, GameEndReason.MATE, TournamentState.FINISHED));
                            }
                            1.this._tournament.addGame(new Tournament.TournamentStructureKey("0", "0", "0"), tournamentRoundInfo, tournamentGameInfo);
                        }
                        1.this._tournament.setStandingsEnabled(true);
                        if (tournamentConnectionListener != null) {
                            tournamentConnectionListener.tournamentLoaded(1.this._tournament);
                        }
                    }

                }, 500L);
            }

            @Override
            public void subscribeToTournament() {
                final long l = System.currentTimeMillis();
                this._timer.schedule(new TimerTask(){

                    @Override
                    public void run() {
                        if (1.this._tournament.getNumberOfGames() > 0) {
                            int n = 1.this._random.nextInt(1.this._tournament.getNumberOfGames());
                            Object object = 1.this._tournament;
                            Object object2 = new StringBuilder();
                            object2.append(n);
                            object2.append("");
                            object2 = object.getGameForId(object2.toString());
                            if (tournamentConnectionListener != null && object2.getStatus().getResult() == GameResult.NO_RESULT) {
                                object = new Random().nextBoolean() ? _dummyMoves : _dummyMoves2;
                                object2.setLastMovesEANsAndEarlierFEN((String[])object, null);
                                object2.setEngineEvaluation(new EngineEvaluation(14.0f * 1.this._random.nextFloat() - 7.0f));
                                long l2 = System.currentTimeMillis();
                                object2.setLastMoveTimeStamp(l2);
                                l2 = 120000L - (l2 - l);
                                if (l2 <= 0L) {
                                    object = 1.this._random.nextFloat() > 0.5f ? GameResult.WHITE_WINS : GameResult.BLACK_WINS;
                                    object2.setStatus(new BroadcastGameStatus((GameResult)((Object)object), GameEndReason.CLOCK_FLAG, TournamentState.FINISHED));
                                }
                                object2.setRemainingTimeWhite(l2);
                                object2.setRemainingTimeBlack(l2);
                                object2.setWhitePlayerActive(object2.isWhitePlayerActive() ^ true);
                                if (tournamentChangeListener != null) {
                                    tournamentChangeListener.tournamentGameChanged((TournamentGameInfo)object2);
                                }
                            }
                        }
                    }
                }, 1000L, 100L);
            }

        };
        return this._connection;
    }

    @Override
    public ITournamentConnection getTournamentConnection(TournamentID tournamentID, ITournamentConnection.TournamentConnectionListener tournamentConnectionListener, ITournamentConnection.TournamentChangeListener tournamentChangeListener) {
        return this.startConnectionRequest(tournamentConnectionListener, tournamentChangeListener);
    }

    @Override
    public ITournamentGameConnection getTournamentGameConnection(TournamentGameID tournamentGameID, ITournamentGameConnection.TournamentGameConnectionListener tournamentGameConnectionListener) {
        return new DummyTournamentGameConnection(tournamentGameID, tournamentGameConnectionListener);
    }

}
