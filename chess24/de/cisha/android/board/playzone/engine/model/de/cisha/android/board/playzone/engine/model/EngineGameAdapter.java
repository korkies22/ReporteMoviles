/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package de.cisha.android.board.playzone.engine.model;

import android.content.Context;
import de.cisha.android.board.engine.IMoveSource;
import de.cisha.android.board.engine.StockfishEngine;
import de.cisha.android.board.playzone.engine.model.AbstractSimpleEngineHumanization;
import de.cisha.android.board.playzone.engine.model.EngineConnection;
import de.cisha.android.board.playzone.engine.model.IEngineGameConnection;
import de.cisha.android.board.playzone.engine.model.IEngineHumanization;
import de.cisha.android.board.playzone.model.AbstractGameModel;
import de.cisha.android.board.playzone.model.ChessClock;
import de.cisha.android.board.playzone.model.GameEndInformation;
import de.cisha.android.board.playzone.model.IChessGameConnection;
import de.cisha.android.board.playzone.model.IGameDelegate;
import de.cisha.android.board.playzone.model.IGameModelDelegate;
import de.cisha.android.board.playzone.model.PlayzoneGameHolder;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.service.IGameService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.chess.model.AbstractMoveContainer;
import de.cisha.chess.model.ClockSetting;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.GameType;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Opponent;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.position.Position;
import java.util.concurrent.Executors;

public class EngineGameAdapter
extends AbstractGameModel
implements IGameDelegate {
    private IEngineGameConnection _engineConnection;
    private String _engineName;
    private TimeControl _time;

    private EngineGameAdapter(int n, TimeControl object, IMoveSource iMoveSource, Context object2, IGameModelDelegate iGameModelDelegate, boolean bl, String string, Game game) {
        super(iGameModelDelegate, bl);
        this._time = object;
        this._engineName = string;
        this._clock = new ChessClock(new ClockSetting(this._time.getMinutes() * 60, this._time.getIncrement()));
        object2 = new AbstractSimpleEngineHumanization(n, this._playerIsWhite ^ true){

            @Override
            protected int getActionCounter() {
                return EngineGameAdapter.this.getPosition().getActionCounter();
            }

            @Override
            protected long getBaseTime() {
                if (!EngineGameAdapter.this._time.hasTimeControl()) {
                    return 300000L;
                }
                return EngineGameAdapter.this._time.getMinutes() * 60000;
            }

            @Override
            protected long getMillisSinceLastMove() {
                return EngineGameAdapter.this.getClock().getTimeSinceLastMove();
            }

            @Override
            protected long getRemainingMillis() {
                long l = !EngineGameAdapter.this._time.hasTimeControl() ? 300000L : EngineGameAdapter.this.getClock().getTimeMillis(EngineGameAdapter.this._playerIsWhite ^ true);
                if (l > 0L) {
                    return l;
                }
                return 0L;
            }
        };
        object = iMoveSource;
        if (iMoveSource == null) {
            object = new StockfishEngine(n);
        }
        this._engineConnection = new EngineConnection((IMoveSource)object, this._time, this._playerIsWhite ^ true, this, (IEngineHumanization)object2);
        this._engineConnection.setGameOnStart(game);
    }

    @Override
    public void destroy() {
        this._engineConnection.disconnect();
        super.destroy();
    }

    @Override
    protected IChessGameConnection getConnection() {
        return this._engineConnection;
    }

    public Game getGameClone() {
        return this._game.copy();
    }

    @Override
    public boolean isRemoteGame() {
        return false;
    }

    @Override
    public void offerOrAcceptDraw() {
        if (this._gameHolder.opponentsDrawOfferPending()) {
            this._engineConnection.acceptDrawOffer();
            return;
        }
        this._engineConnection.offerDraw();
        this._gameHolder.setPlayersDrawOffer(true);
    }

    @Override
    public void onGameEndDetected(GameEndInformation gameEndInformation) {
        this._gameHolder.setGameActive(false);
        this._game.setResult(gameEndInformation.getStatus());
        this._delegate.onGameEnd(gameEndInformation);
        Executors.newSingleThreadExecutor().execute(new Runnable(){

            @Override
            public void run() {
                if (EngineGameAdapter.this._game != null) {
                    ServiceProvider.getInstance().getGameService().savePlayzoneGame(EngineGameAdapter.this._game);
                }
            }
        });
    }

    @Override
    public void onGameStart(Game game, int n, int n2) {
        game.setType(GameType.GAME_VS_ENGINE);
        game.setSite("chess24.com");
        game.setTitle("Game against computer");
        Opponent opponent = this._playerIsWhite ? game.getBlackPlayer() : game.getWhitePlayer();
        opponent.setName(this._engineName);
        super.onGameStart(game, n, n2);
    }

    @Override
    public void onMove(Move move) {
        this._gameHolder.doMoveInCurrentPosition(move.getSEP());
    }

    @Override
    public void onReceiveDrawOffer() {
        this._gameHolder.setOpponentsDrawOffer(true);
        this._delegate.onReceiveDrawOffer();
    }

    @Override
    public boolean playWithChessClocks() {
        if (this._time != null) {
            return true;
        }
        return false;
    }

    @Override
    public void requestRematch() {
    }

    @Override
    public void startUp() {
        this._engineConnection.startUp();
        super.startUp();
    }

    public static class Builder {
        public IMoveSource _moveSource;
        private boolean bColor;
        private Context bContext;
        private IGameModelDelegate bDelegate;
        private int bElo;
        private Game bGame;
        private IMoveSource bMoveSource;
        private String bName;
        private TimeControl bTime;

        public Builder(int n, TimeControl timeControl, Context context, IGameModelDelegate iGameModelDelegate) {
            this.bElo = n;
            this.bTime = timeControl;
            this.bContext = context;
            this.bDelegate = iGameModelDelegate;
            this.bMoveSource = null;
            this.bColor = true;
            this.bName = "Computer";
        }

        public EngineGameAdapter create() {
            return new EngineGameAdapter(this.bElo, this.bTime, this.bMoveSource, this.bContext, this.bDelegate, this.bColor, this.bName, this.bGame);
        }

        public Builder setColor(boolean bl) {
            this.bColor = bl;
            return this;
        }

        public Builder setGame(Game game) {
            this.bGame = game;
            return this;
        }

        public Builder setMoveSource(IMoveSource iMoveSource) {
            this.bMoveSource = iMoveSource;
            return this;
        }

        public Builder setName(String string) {
            this.bName = string;
            return this;
        }
    }

}
