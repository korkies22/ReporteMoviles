/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.connection.IConnection;
import de.cisha.android.board.broadcast.model.ITournamentGameConnection;
import de.cisha.android.board.broadcast.model.TournamentGameID;
import de.cisha.chess.model.AbstractMoveContainer;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.util.Logger;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.WeakHashMap;

public class DummyTournamentGameConnection
implements ITournamentGameConnection {
    private Game _game;
    private boolean _isConnected = false;
    private Set<IConnection.IConnectionListener> _listeners;
    private ITournamentGameConnection.TournamentGameConnectionListener _myListener;
    private Timer _timer;
    int moveid = 0;

    public DummyTournamentGameConnection(TournamentGameID tournamentGameID, ITournamentGameConnection.TournamentGameConnectionListener tournamentGameConnectionListener) {
        this._myListener = tournamentGameConnectionListener;
        this._listeners = Collections.newSetFromMap(new WeakHashMap());
        this.addConnectionListener(tournamentGameConnectionListener);
        this._game = new Game();
    }

    private void notifyListenersConnectionEstablished() {
        this._myListener.connectionEstablished(this);
    }

    private void notifyListenersGameEnd() {
        this._myListener.gameStatusChanged(new GameStatus(GameResult.BLACK_WINS, GameEndReason.MATE));
    }

    private void notifyListenersNewMove(Move move) {
        this._game.addMoveInMainLine(move);
        ITournamentGameConnection.TournamentGameConnectionListener tournamentGameConnectionListener = this._myListener;
        int n = this.moveid;
        this.moveid = n + 1;
        tournamentGameConnectionListener.newMove(n, move.getSEP(), this.moveid, 500);
        Logger.getInstance().debug("new move", move.getEAN());
    }

    @Override
    public void addConnectionListener(IConnection.IConnectionListener iConnectionListener) {
        this._listeners.add(iConnectionListener);
    }

    @Override
    public void close() {
        this._isConnected = false;
        if (this._timer != null) {
            this._timer.cancel();
        }
        Iterator<IConnection.IConnectionListener> iterator = this._listeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().connectionClosed(this);
        }
    }

    @Override
    public void connect() {
        this._isConnected = true;
        this._timer = new Timer();
        this._timer.schedule(new TimerTask(){

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
        this._timer.schedule(new TimerTask(){

            @Override
            public void run() {
                AbstractMoveContainer abstractMoveContainer = DummyTournamentGameConnection.this._game.copy();
                DummyTournamentGameConnection.this._myListener.tournamentGameLoaded((Game)abstractMoveContainer);
            }
        }, 300L);
    }

    @Override
    public void subscribeToGame() {
        this._timer.schedule(new TimerTask(){

            @Override
            public void run() {
                Object object = DummyTournamentGameConnection.this._game.getLastMoveinMainLine() != null ? DummyTournamentGameConnection.this._game.getLastMoveinMainLine().getResultingPosition() : DummyTournamentGameConnection.this._game.getStartingPosition();
                if ((object = object.getAllMoves()).size() > 0) {
                    DummyTournamentGameConnection.this.notifyListenersNewMove((Move)object.get(0));
                    return;
                }
                DummyTournamentGameConnection.this.notifyListenersGameEnd();
                DummyTournamentGameConnection.this.close();
            }
        }, 1000L, 1000L);
    }

}
