// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

import java.util.List;
import de.cisha.chess.model.position.Position;
import java.util.TimerTask;
import java.util.Iterator;
import de.cisha.chess.util.Logger;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.Move;
import java.util.Map;
import java.util.Collections;
import java.util.WeakHashMap;
import java.util.Timer;
import de.cisha.android.board.broadcast.connection.IConnection;
import java.util.Set;
import de.cisha.chess.model.Game;

public class DummyTournamentGameConnection implements ITournamentGameConnection
{
    private Game _game;
    private boolean _isConnected;
    private Set<IConnectionListener> _listeners;
    private TournamentGameConnectionListener _myListener;
    private Timer _timer;
    int moveid;
    
    public DummyTournamentGameConnection(final TournamentGameID tournamentGameID, final TournamentGameConnectionListener myListener) {
        this._isConnected = false;
        this.moveid = 0;
        this._myListener = myListener;
        this._listeners = Collections.newSetFromMap(new WeakHashMap<IConnectionListener, Boolean>());
        this.addConnectionListener(myListener);
        this._game = new Game();
    }
    
    private void notifyListenersConnectionEstablished() {
        ((IConnection.IConnectionListener)this._myListener).connectionEstablished(this);
    }
    
    private void notifyListenersGameEnd() {
        this._myListener.gameStatusChanged(new GameStatus(GameResult.BLACK_WINS, GameEndReason.MATE));
    }
    
    private void notifyListenersNewMove(final Move move) {
        this._game.addMoveInMainLine(move);
        this._myListener.newMove(this.moveid++, move.getSEP(), this.moveid, 500);
        Logger.getInstance().debug("new move", move.getEAN());
    }
    
    @Override
    public void addConnectionListener(final IConnectionListener connectionListener) {
        this._listeners.add(connectionListener);
    }
    
    @Override
    public void close() {
        this._isConnected = false;
        if (this._timer != null) {
            this._timer.cancel();
        }
        final Iterator<IConnectionListener> iterator = this._listeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().connectionClosed(this);
        }
    }
    
    @Override
    public void connect() {
        this._isConnected = true;
        (this._timer = new Timer()).schedule(new TimerTask() {
            @Override
            public void run() {
                DummyTournamentGameConnection.this.notifyListenersConnectionEstablished();
            }
        }, 100L);
    }
    
    @Override
    public boolean isConnected() {
        return this._isConnected;
    }
    
    @Override
    public void loadGame() {
        this._timer.schedule(new TimerTask() {
            @Override
            public void run() {
                DummyTournamentGameConnection.this._myListener.tournamentGameLoaded(DummyTournamentGameConnection.this._game.copy());
            }
        }, 300L);
    }
    
    @Override
    public void subscribeToGame() {
        this._timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Position position;
                if (DummyTournamentGameConnection.this._game.getLastMoveinMainLine() != null) {
                    position = DummyTournamentGameConnection.this._game.getLastMoveinMainLine().getResultingPosition();
                }
                else {
                    position = DummyTournamentGameConnection.this._game.getStartingPosition();
                }
                final List<Move> allMoves = position.getAllMoves();
                if (allMoves.size() > 0) {
                    DummyTournamentGameConnection.this.notifyListenersNewMove(allMoves.get(0));
                    return;
                }
                DummyTournamentGameConnection.this.notifyListenersGameEnd();
                DummyTournamentGameConnection.this.close();
            }
        }, 1000L, 1000L);
    }
}
