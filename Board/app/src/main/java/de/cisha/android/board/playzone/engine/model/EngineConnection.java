// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.engine.model;

import de.cisha.chess.model.Rating;
import de.cisha.android.board.user.User;
import de.cisha.chess.model.ClockSetting;
import de.cisha.android.board.service.IMembershipService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.chess.model.Opponent;
import de.cisha.android.board.engine.EvaluationInfo;
import java.util.List;
import de.cisha.chess.util.Logger;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.FEN;
import android.os.AsyncTask;
import de.cisha.chess.model.Move;
import de.cisha.android.board.playzone.model.GameEndInformation;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.engine.IMoveSource;
import de.cisha.chess.model.Game;
import de.cisha.android.board.playzone.model.IGameDelegate;

public class EngineConnection implements IEngineGameConnection
{
    private IGameDelegate _delegate;
    private IEngineHumanization _engineHumanization;
    private boolean _engineIsWhite;
    private Game _game;
    private boolean _gameActive;
    private IMoveSource _moveSource;
    private TimeControl _time;
    
    public EngineConnection(final IMoveSource moveSource, final TimeControl time, final boolean engineIsWhite, final IGameDelegate delegate, final IEngineHumanization engineHumanization) {
        this._gameActive = false;
        this._time = time;
        this._engineIsWhite = engineIsWhite;
        this._delegate = delegate;
        this._engineHumanization = engineHumanization;
        this._moveSource = moveSource;
    }
    
    @Override
    public void acceptDrawOffer() {
        this._gameActive = false;
        this._delegate.onGameEndDetected(new GameEndInformation(new GameStatus(GameResult.DRAW, GameEndReason.DRAW_BY_MUTUAL_AGREEMENT), this._game.getResultingPosition().getActiveColor()));
    }
    
    @Override
    public void disconnect() {
        this._moveSource.destroy();
    }
    
    @Override
    public void doMove(final Move move) {
        if (this._gameActive) {
            new AsyncTask<Void, Void, Move>() {
                protected Move doInBackground(final Void... array) {
                    Position resultingPosition;
                    if (move != null) {
                        resultingPosition = move.getResultingPosition();
                    }
                    else {
                        resultingPosition = new Position(FEN.STARTING_POSITION);
                    }
                    final List<EvaluationInfo> movesWithMaximumTime = EngineConnection.this._moveSource.getMovesWithMaximumTime(resultingPosition, EngineConnection.this._engineHumanization.getMaximumThinkingTimeForNextMove());
                    final Move move = null;
                    Move chooseMove;
                    if (movesWithMaximumTime != null && movesWithMaximumTime.size() != 0) {
                        chooseMove = EngineConnection.this._engineHumanization.chooseMove(movesWithMaximumTime);
                        if (EngineConnection.this._engineHumanization.resign()) {
                            EngineConnection.this._gameActive = false;
                            GameResult gameResult;
                            if (EngineConnection.this._engineIsWhite) {
                                gameResult = GameResult.BLACK_WINS;
                            }
                            else {
                                gameResult = GameResult.WHITE_WINS;
                            }
                            EngineConnection.this._delegate.onGameEndDetected(new GameEndInformation(new GameStatus(gameResult, GameEndReason.RESIGN), EngineConnection.this._engineIsWhite));
                            return chooseMove;
                        }
                    }
                    else {
                        Logger.getInstance().debug("analyse position", resultingPosition.toString());
                        chooseMove = move;
                    }
                    return chooseMove;
                }
                
                protected void onPostExecute(final Move move) {
                    if (move != null) {
                        EngineConnection.this.onEngineStopped(move);
                    }
                }
            }.execute((Object[])new Void[] { null });
        }
    }
    
    @Override
    public void offerDraw() {
        if (this._engineHumanization.acceptDrawOffer()) {
            this.acceptDrawOffer();
        }
    }
    
    public void onEngineStopped(final Move move) {
        if (this._gameActive) {
            this._delegate.onMove(move);
        }
    }
    
    @Override
    public void requestAbort() {
        if (this._game.getAllMainLineMoves().size() <= 2) {
            this._gameActive = false;
            this._delegate.onGameEndDetected(new GameEndInformation(new GameStatus(GameResult.NO_RESULT, GameEndReason.ABORTED), this._game.getResultingPosition().getActiveColor()));
        }
    }
    
    @Override
    public void resign() {
        this._gameActive = false;
        GameStatus gameStatus;
        if (this._game.getAllMainLineMoves().size() >= 2) {
            GameResult gameResult;
            if (this._engineIsWhite) {
                gameResult = GameResult.WHITE_WINS;
            }
            else {
                gameResult = GameResult.BLACK_WINS;
            }
            gameStatus = new GameStatus(gameResult, GameEndReason.RESIGN);
        }
        else {
            gameStatus = new GameStatus(GameResult.NO_RESULT, GameEndReason.ABORTED);
        }
        this._delegate.onGameEndDetected(new GameEndInformation(gameStatus, this._game.getResultingPosition().getActiveColor()));
    }
    
    @Override
    public void setGameOnStart(final Game game) {
        if (!this._gameActive) {
            this._game = game;
        }
    }
    
    @Override
    public void startUp() {
        this._gameActive = true;
        if (this._game == null) {
            this._game = new Game();
        }
        int n;
        if (this._time != null) {
            n = this._time.getMinutes() * 60000;
        }
        else {
            n = 0;
        }
        this._game.getClockSetting().setBase(true, n);
        this._game.getClockSetting().setBase(false, n);
        final ClockSetting clockSetting = this._game.getClockSetting();
        int n2;
        if (this._time != null) {
            n2 = this._time.getIncrement() * 1000;
        }
        else {
            n2 = 0;
        }
        clockSetting.setIncrementPerMove(true, n2);
        final ClockSetting clockSetting2 = this._game.getClockSetting();
        int n3;
        if (this._time != null) {
            n3 = this._time.getIncrement() * 1000;
        }
        else {
            n3 = 0;
        }
        clockSetting2.setIncrementPerMove(false, n3);
        final Opponent opponent = new Opponent();
        final User currentUserData = ServiceProvider.getInstance().getProfileDataService().getCurrentUserData();
        if (currentUserData != null) {
            final ClockSetting.GameClockType clockType = this._game.getClockSetting().getClockType();
            currentUserData.getElo();
            Rating rating = null;
            switch (EngineConnection.2..SwitchMap.de.cisha.chess.model.ClockSetting.GameClockType[clockType.ordinal()]) {
                default: {
                    rating = currentUserData.getRatingClassical();
                    break;
                }
                case 2: {
                    rating = currentUserData.getRatingBlitz();
                    break;
                }
                case 1: {
                    rating = currentUserData.getRatingBullet();
                    break;
                }
            }
            opponent.setName(currentUserData.getNickname());
            opponent.setRating(rating);
            opponent.setCountry(currentUserData.getCountry());
            opponent.setPremium(ServiceProvider.getInstance().getMembershipService().getMembershipLevel() == IMembershipService.MembershipLevel.MembershipLevelWebpremium);
        }
        else {
            opponent.setName("me");
        }
        final Opponent opponent2 = new Opponent();
        opponent2.setRating(this._engineHumanization.getRating());
        opponent2.setName("Computer");
        if (this._engineIsWhite) {
            this._game.setWhitePlayer(opponent2);
            this._game.setBlackPlayer(opponent);
        }
        else {
            this._game.setWhitePlayer(opponent);
            this._game.setBlackPlayer(opponent2);
        }
        this._delegate.onGameStart(this._game, 0, 0);
        final List<Move> allMainLineMoves = this._game.getAllMainLineMoves();
        if (!allMainLineMoves.isEmpty()) {
            final Move move = allMainLineMoves.get(allMainLineMoves.size() - 1);
            if (move.getResultingPosition().getActiveColor() == this._engineIsWhite) {
                this.doMove(move);
            }
        }
        else if (this._engineIsWhite) {
            this.doMove(null);
        }
    }
}
