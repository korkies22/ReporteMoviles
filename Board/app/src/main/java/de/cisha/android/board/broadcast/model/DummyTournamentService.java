// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.engine.EngineEvaluation;
import de.cisha.chess.model.FEN;
import java.util.TimerTask;
import de.cisha.android.board.broadcast.connection.IConnection;
import de.cisha.android.board.broadcast.model.jsonparsers.BroadcastTournamentType;
import java.util.Timer;
import java.util.Random;

public class DummyTournamentService implements ITournamentService
{
    private static String[] _dummyMoves;
    private static String[] _dummyMoves2;
    private static TournamentPlayer[] _dummyNames;
    private static DummyTournamentService _instance;
    private ITournamentConnection _connection;
    
    static {
        DummyTournamentService._dummyNames = new TournamentPlayer[] { new TournamentPlayer("Simon", 1), new TournamentPlayer("Lennart", 2), new TournamentPlayer("Kim", 3), new TournamentPlayer("Pascal", 4), new TournamentPlayer("Wolfgang", 5), new TournamentPlayer("Beatrice", 6), new TournamentPlayer("Felix", 7) };
        DummyTournamentService._dummyMoves = new String[] { "e2e4", "e7e5" };
        DummyTournamentService._dummyMoves2 = new String[] { "e2e4", "e7e5", "g1f3" };
    }
    
    public static DummyTournamentService getInstance() {
        if (DummyTournamentService._instance == null) {
            DummyTournamentService._instance = new DummyTournamentService();
        }
        return DummyTournamentService._instance;
    }
    
    private ITournamentConnection startConnectionRequest(final ITournamentConnection.TournamentConnectionListener tournamentConnectionListener, final ITournamentConnection.TournamentChangeListener tournamentChangeListener) {
        return this._connection = new ITournamentConnection() {
            private boolean _flagIsConnected = false;
            final Random _random = new Random();
            private Timer _timer = new Timer();
            final Tournament _tournament = new Tournament("tourni", "descri", BroadcastTournamentType.MULTIKNOCKOUT);
            
            @Override
            public void addConnectionListener(final IConnectionListener connectionListener) {
            }
            
            @Override
            public void close() {
                this._timer.cancel();
                this._flagIsConnected = false;
            }
            
            @Override
            public void connect() {
                this._flagIsConnected = true;
                this._timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ((IConnection.IConnectionListener)tournamentConnectionListener).connectionEstablished(DummyTournamentService.this._connection);
                    }
                }, 300L);
            }
            
            @Override
            public boolean isConnected() {
                return this._flagIsConnected;
            }
            
            @Override
            public void loadTournament() {
                this._timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        final TournamentRoundInfo currentRound = new TournamentRoundInfo() {
                            @Override
                            public int compareTo(final TournamentRoundInfo tournamentRoundInfo) {
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
                        ITournamentConnection.this._tournament.setCurrentRound(currentRound);
                        for (int i = 0; i < 20; ++i) {
                            final StringBuilder sb = new StringBuilder();
                            sb.append("");
                            sb.append(i);
                            final TournamentGameInfo tournamentGameInfo = new TournamentGameInfo(sb.toString(), DummyTournamentService._dummyNames[(int)(DummyTournamentService._dummyNames.length * ITournamentConnection.this._random.nextFloat())], DummyTournamentService._dummyNames[(int)(DummyTournamentService._dummyNames.length * ITournamentConnection.this._random.nextFloat())]);
                            String[] array;
                            if (new Random().nextBoolean()) {
                                array = DummyTournamentService._dummyMoves;
                            }
                            else {
                                array = DummyTournamentService._dummyMoves2;
                            }
                            tournamentGameInfo.setLastMovesEANsAndEarlierFEN(array, FEN.STARTING_POSITION);
                            tournamentGameInfo.setRemainingTimeBlack(120000L);
                            tournamentGameInfo.setWhitePlayerLeftSide(ITournamentConnection.this._random.nextBoolean());
                            tournamentGameInfo.setEngineEvaluation(new EngineEvaluation(14.0f * ITournamentConnection.this._random.nextFloat() - 7.0f));
                            tournamentGameInfo.setRemainingTimeWhite(120000L);
                            final float nextFloat = ITournamentConnection.this._random.nextFloat();
                            final double n = nextFloat;
                            if (n < 0.25) {
                                tournamentGameInfo.setStatus(new BroadcastGameStatus(GameResult.NO_RESULT, GameEndReason.NO_REASON, TournamentState.ONGOING));
                            }
                            else if (nextFloat < 0.5f) {
                                tournamentGameInfo.setStatus(new BroadcastGameStatus(GameResult.BLACK_WINS, GameEndReason.MATE, TournamentState.FINISHED));
                            }
                            else if (n < 0.75) {
                                tournamentGameInfo.setStatus(new BroadcastGameStatus(GameResult.DRAW, GameEndReason.DRAW_BY_MUTUAL_AGREEMENT, TournamentState.FINISHED));
                            }
                            else {
                                tournamentGameInfo.setStatus(new BroadcastGameStatus(GameResult.WHITE_WINS, GameEndReason.MATE, TournamentState.FINISHED));
                            }
                            ITournamentConnection.this._tournament.addGame(new Tournament.TournamentStructureKey("0", "0", "0"), currentRound, tournamentGameInfo);
                        }
                        ITournamentConnection.this._tournament.setStandingsEnabled(true);
                        if (tournamentConnectionListener != null) {
                            tournamentConnectionListener.tournamentLoaded(ITournamentConnection.this._tournament);
                        }
                    }
                }, 500L);
            }
            
            @Override
            public void subscribeToTournament() {
                this._timer.schedule(new TimerTask() {
                    final /* synthetic */ long val.startTime = System.currentTimeMillis();
                    
                    @Override
                    public void run() {
                        if (ITournamentConnection.this._tournament.getNumberOfGames() > 0) {
                            final int nextInt = ITournamentConnection.this._random.nextInt(ITournamentConnection.this._tournament.getNumberOfGames());
                            final Tournament tournament = ITournamentConnection.this._tournament;
                            final StringBuilder sb = new StringBuilder();
                            sb.append(nextInt);
                            sb.append("");
                            final TournamentGameInfo gameForId = tournament.getGameForId(sb.toString());
                            if (tournamentConnectionListener != null && gameForId.getStatus().getResult() == GameResult.NO_RESULT) {
                                String[] array;
                                if (new Random().nextBoolean()) {
                                    array = DummyTournamentService._dummyMoves;
                                }
                                else {
                                    array = DummyTournamentService._dummyMoves2;
                                }
                                gameForId.setLastMovesEANsAndEarlierFEN(array, null);
                                gameForId.setEngineEvaluation(new EngineEvaluation(14.0f * ITournamentConnection.this._random.nextFloat() - 7.0f));
                                final long currentTimeMillis = System.currentTimeMillis();
                                gameForId.setLastMoveTimeStamp(currentTimeMillis);
                                final long n = 120000L - (currentTimeMillis - this.val.startTime);
                                if (n <= 0L) {
                                    GameResult gameResult;
                                    if (ITournamentConnection.this._random.nextFloat() > 0.5f) {
                                        gameResult = GameResult.WHITE_WINS;
                                    }
                                    else {
                                        gameResult = GameResult.BLACK_WINS;
                                    }
                                    gameForId.setStatus(new BroadcastGameStatus(gameResult, GameEndReason.CLOCK_FLAG, TournamentState.FINISHED));
                                }
                                gameForId.setRemainingTimeWhite(n);
                                gameForId.setRemainingTimeBlack(n);
                                gameForId.setWhitePlayerActive(gameForId.isWhitePlayerActive() ^ true);
                                if (tournamentChangeListener != null) {
                                    tournamentChangeListener.tournamentGameChanged(gameForId);
                                }
                            }
                        }
                    }
                }, 1000L, 100L);
            }
        };
    }
    
    @Override
    public ITournamentConnection getTournamentConnection(final TournamentID tournamentID, final ITournamentConnection.TournamentConnectionListener tournamentConnectionListener, final ITournamentConnection.TournamentChangeListener tournamentChangeListener) {
        return this.startConnectionRequest(tournamentConnectionListener, tournamentChangeListener);
    }
    
    @Override
    public ITournamentGameConnection getTournamentGameConnection(final TournamentGameID tournamentGameID, final ITournamentGameConnection.TournamentGameConnectionListener tournamentGameConnectionListener) {
        return new DummyTournamentGameConnection(tournamentGameID, tournamentGameConnectionListener);
    }
}
