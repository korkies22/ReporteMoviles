/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 */
package de.cisha.android.board.playzone.engine.model;

import android.os.AsyncTask;
import de.cisha.android.board.engine.EvaluationInfo;
import de.cisha.android.board.engine.IMoveSource;
import de.cisha.android.board.playzone.engine.model.IEngineGameConnection;
import de.cisha.android.board.playzone.engine.model.IEngineHumanization;
import de.cisha.android.board.playzone.model.GameEndInformation;
import de.cisha.android.board.playzone.model.IGameDelegate;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.service.IMembershipService;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.user.User;
import de.cisha.chess.model.ClockSetting;
import de.cisha.chess.model.Country;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Opponent;
import de.cisha.chess.model.Rating;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.util.Logger;
import java.util.List;

public class EngineConnection
implements IEngineGameConnection {
    private IGameDelegate _delegate;
    private IEngineHumanization _engineHumanization;
    private boolean _engineIsWhite;
    private Game _game;
    private boolean _gameActive = false;
    private IMoveSource _moveSource;
    private TimeControl _time;

    public EngineConnection(IMoveSource iMoveSource, TimeControl timeControl, boolean bl, IGameDelegate iGameDelegate, IEngineHumanization iEngineHumanization) {
        this._time = timeControl;
        this._engineIsWhite = bl;
        this._delegate = iGameDelegate;
        this._engineHumanization = iEngineHumanization;
        this._moveSource = iMoveSource;
    }

    @Override
    public void acceptDrawOffer() {
        this._gameActive = false;
        GameStatus gameStatus = new GameStatus(GameResult.DRAW, GameEndReason.DRAW_BY_MUTUAL_AGREEMENT);
        this._delegate.onGameEndDetected(new GameEndInformation(gameStatus, this._game.getResultingPosition().getActiveColor()));
    }

    @Override
    public void disconnect() {
        this._moveSource.destroy();
    }

    @Override
    public void doMove(final Move move) {
        if (this._gameActive) {
            new AsyncTask<Void, Void, Move>(){

                protected /* varargs */ Move doInBackground(Void ... object) {
                    object = move != null ? move.getResultingPosition() : new Position(FEN.STARTING_POSITION);
                    List<EvaluationInfo> list = EngineConnection.this._moveSource.getMovesWithMaximumTime((Position)object, EngineConnection.this._engineHumanization.getMaximumThinkingTimeForNextMove());
                    Move move2 = null;
                    if (list != null && list.size() != 0) {
                        move2 = EngineConnection.this._engineHumanization.chooseMove(list);
                        object = move2;
                        if (EngineConnection.this._engineHumanization.resign()) {
                            EngineConnection.this._gameActive = false;
                            object = EngineConnection.this._engineIsWhite ? GameResult.BLACK_WINS : GameResult.WHITE_WINS;
                            object = new GameStatus((GameResult)((Object)object), GameEndReason.RESIGN);
                            EngineConnection.this._delegate.onGameEndDetected(new GameEndInformation((GameStatus)object, EngineConnection.this._engineIsWhite));
                            return move2;
                        }
                    } else {
                        Logger.getInstance().debug("analyse position", object.toString());
                        object = move2;
                    }
                    return object;
                }

                protected void onPostExecute(Move move2) {
                    if (move2 != null) {
                        EngineConnection.this.onEngineStopped(move2);
                    }
                }
            }.execute((Object[])new Void[]{null});
        }
    }

    @Override
    public void offerDraw() {
        if (this._engineHumanization.acceptDrawOffer()) {
            this.acceptDrawOffer();
        }
    }

    public void onEngineStopped(Move move) {
        if (this._gameActive) {
            this._delegate.onMove(move);
        }
    }

    @Override
    public void requestAbort() {
        if (this._game.getAllMainLineMoves().size() <= 2) {
            this._gameActive = false;
            GameStatus gameStatus = new GameStatus(GameResult.NO_RESULT, GameEndReason.ABORTED);
            this._delegate.onGameEndDetected(new GameEndInformation(gameStatus, this._game.getResultingPosition().getActiveColor()));
        }
    }

    @Override
    public void resign() {
        Object object;
        this._gameActive = false;
        if (this._game.getAllMainLineMoves().size() >= 2) {
            object = this._engineIsWhite ? GameResult.WHITE_WINS : GameResult.BLACK_WINS;
            object = new GameStatus((GameResult)((Object)object), GameEndReason.RESIGN);
        } else {
            object = new GameStatus(GameResult.NO_RESULT, GameEndReason.ABORTED);
        }
        this._delegate.onGameEndDetected(new GameEndInformation((GameStatus)object, this._game.getResultingPosition().getActiveColor()));
    }

    @Override
    public void setGameOnStart(Game game) {
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
        int n = this._time != null ? this._time.getMinutes() * 60000 : 0;
        this._game.getClockSetting().setBase(true, n);
        this._game.getClockSetting().setBase(false, n);
        Object object = this._game.getClockSetting();
        n = this._time != null ? this._time.getIncrement() * 1000 : 0;
        object.setIncrementPerMove(true, n);
        object = this._game.getClockSetting();
        n = this._time != null ? this._time.getIncrement() * 1000 : 0;
        object.setIncrementPerMove(false, n);
        Opponent opponent = new Opponent();
        User user = ServiceProvider.getInstance().getProfileDataService().getCurrentUserData();
        if (user != null) {
            object = this._game.getClockSetting().getClockType();
            user.getElo();
            switch (.$SwitchMap$de$cisha$chess$model$ClockSetting$GameClockType[object.ordinal()]) {
                default: {
                    object = user.getRatingClassical();
                    break;
                }
                case 2: {
                    object = user.getRatingBlitz();
                    break;
                }
                case 1: {
                    object = user.getRatingBullet();
                }
            }
            opponent.setName(user.getNickname());
            opponent.setRating((Rating)object);
            opponent.setCountry(user.getCountry());
            boolean bl = ServiceProvider.getInstance().getMembershipService().getMembershipLevel() == IMembershipService.MembershipLevel.MembershipLevelWebpremium;
            opponent.setPremium(bl);
        } else {
            opponent.setName("me");
        }
        object = new Opponent();
        object.setRating(this._engineHumanization.getRating());
        object.setName("Computer");
        if (this._engineIsWhite) {
            this._game.setWhitePlayer((Opponent)object);
            this._game.setBlackPlayer(opponent);
        } else {
            this._game.setWhitePlayer(opponent);
            this._game.setBlackPlayer((Opponent)object);
        }
        this._delegate.onGameStart(this._game, 0, 0);
        object = this._game.getAllMainLineMoves();
        if (!object.isEmpty()) {
            if ((object = (Move)object.get(object.size() - 1)).getResultingPosition().getActiveColor() == this._engineIsWhite) {
                this.doMove((Move)object);
                return;
            }
        } else if (this._engineIsWhite) {
            this.doMove(null);
        }
    }

}
