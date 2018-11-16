// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.engine.model;

import de.cisha.chess.model.Move;
import de.cisha.chess.model.Opponent;
import de.cisha.chess.model.GameType;
import de.cisha.android.board.service.ServiceProvider;
import java.util.concurrent.Executors;
import de.cisha.android.board.playzone.model.GameEndInformation;
import de.cisha.android.board.playzone.model.IChessGameConnection;
import de.cisha.android.board.engine.StockfishEngine;
import de.cisha.android.board.playzone.model.ChessClock;
import de.cisha.chess.model.ClockSetting;
import de.cisha.chess.model.Game;
import de.cisha.android.board.playzone.model.IGameModelDelegate;
import android.content.Context;
import de.cisha.android.board.engine.IMoveSource;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.playzone.model.IGameDelegate;
import de.cisha.android.board.playzone.model.AbstractGameModel;

public class EngineGameAdapter extends AbstractGameModel implements IGameDelegate
{
    private IEngineGameConnection _engineConnection;
    private String _engineName;
    private TimeControl _time;
    
    private EngineGameAdapter(final int n, final TimeControl time, final IMoveSource moveSource, final Context context, final IGameModelDelegate gameModelDelegate, final boolean b, final String engineName, final Game gameOnStart) {
        super(gameModelDelegate, b);
        this._time = time;
        this._engineName = engineName;
        this._clock = new ChessClock(new ClockSetting(this._time.getMinutes() * 60, this._time.getIncrement()));
        final AbstractSimpleEngineHumanization abstractSimpleEngineHumanization = new AbstractSimpleEngineHumanization(n, this._playerIsWhite ^ true) {
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
                long timeMillis;
                if (!EngineGameAdapter.this._time.hasTimeControl()) {
                    timeMillis = 300000L;
                }
                else {
                    timeMillis = EngineGameAdapter.this.getClock().getTimeMillis(EngineGameAdapter.this._playerIsWhite ^ true);
                }
                if (timeMillis > 0L) {
                    return timeMillis;
                }
                return 0L;
            }
        };
        IMoveSource moveSource2 = moveSource;
        if (moveSource == null) {
            moveSource2 = new StockfishEngine(n);
        }
        (this._engineConnection = new EngineConnection(moveSource2, this._time, this._playerIsWhite ^ true, this, abstractSimpleEngineHumanization)).setGameOnStart(gameOnStart);
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
    public void onGameEndDetected(final GameEndInformation gameEndInformation) {
        this._gameHolder.setGameActive(false);
        this._game.setResult(gameEndInformation.getStatus());
        this._delegate.onGameEnd(gameEndInformation);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                if (EngineGameAdapter.this._game != null) {
                    ServiceProvider.getInstance().getGameService().savePlayzoneGame(EngineGameAdapter.this._game);
                }
            }
        });
    }
    
    @Override
    public void onGameStart(final Game game, final int n, final int n2) {
        game.setType(GameType.GAME_VS_ENGINE);
        game.setSite("chess24.com");
        game.setTitle("Game against computer");
        Opponent opponent;
        if (this._playerIsWhite) {
            opponent = game.getBlackPlayer();
        }
        else {
            opponent = game.getWhitePlayer();
        }
        opponent.setName(this._engineName);
        super.onGameStart(game, n, n2);
    }
    
    @Override
    public void onMove(final Move move) {
        this._gameHolder.doMoveInCurrentPosition(move.getSEP());
    }
    
    @Override
    public void onReceiveDrawOffer() {
        this._gameHolder.setOpponentsDrawOffer(true);
        this._delegate.onReceiveDrawOffer();
    }
    
    @Override
    public boolean playWithChessClocks() {
        return this._time != null;
    }
    
    @Override
    public void requestRematch() {
    }
    
    @Override
    public void startUp() {
        this._engineConnection.startUp();
        super.startUp();
    }
    
    public static class Builder
    {
        public IMoveSource _moveSource;
        private boolean bColor;
        private Context bContext;
        private IGameModelDelegate bDelegate;
        private int bElo;
        private Game bGame;
        private IMoveSource bMoveSource;
        private String bName;
        private TimeControl bTime;
        
        public Builder(final int bElo, final TimeControl bTime, final Context bContext, final IGameModelDelegate bDelegate) {
            this.bElo = bElo;
            this.bTime = bTime;
            this.bContext = bContext;
            this.bDelegate = bDelegate;
            this.bMoveSource = null;
            this.bColor = true;
            this.bName = "Computer";
        }
        
        public EngineGameAdapter create() {
            return new EngineGameAdapter(this.bElo, this.bTime, this.bMoveSource, this.bContext, this.bDelegate, this.bColor, this.bName, this.bGame, null);
        }
        
        public Builder setColor(final boolean bColor) {
            this.bColor = bColor;
            return this;
        }
        
        public Builder setGame(final Game bGame) {
            this.bGame = bGame;
            return this;
        }
        
        public Builder setMoveSource(final IMoveSource bMoveSource) {
            this.bMoveSource = bMoveSource;
            return this;
        }
        
        public Builder setName(final String bName) {
            this.bName = bName;
            return this;
        }
    }
}
