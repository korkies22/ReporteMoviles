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
import de.cisha.android.board.playzone.engine.model.IEngineHumanization;
import de.cisha.android.board.playzone.model.GameEndInformation;
import de.cisha.android.board.playzone.model.IGameDelegate;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.util.Logger;
import java.util.List;

class EngineConnection
extends AsyncTask<Void, Void, Move> {
    final /* synthetic */ Move val$move;

    EngineConnection(Move move) {
        this.val$move = move;
    }

    protected /* varargs */ Move doInBackground(Void ... object) {
        object = this.val$move != null ? this.val$move.getResultingPosition() : new Position(FEN.STARTING_POSITION);
        List<EvaluationInfo> list = EngineConnection.this._moveSource.getMovesWithMaximumTime((Position)object, EngineConnection.this._engineHumanization.getMaximumThinkingTimeForNextMove());
        Move move = null;
        if (list != null && list.size() != 0) {
            move = EngineConnection.this._engineHumanization.chooseMove(list);
            object = move;
            if (EngineConnection.this._engineHumanization.resign()) {
                EngineConnection.this._gameActive = false;
                object = EngineConnection.this._engineIsWhite ? GameResult.BLACK_WINS : GameResult.WHITE_WINS;
                object = new GameStatus((GameResult)((Object)object), GameEndReason.RESIGN);
                EngineConnection.this._delegate.onGameEndDetected(new GameEndInformation((GameStatus)object, EngineConnection.this._engineIsWhite));
                return move;
            }
        } else {
            Logger.getInstance().debug("analyse position", object.toString());
            object = move;
        }
        return object;
    }

    protected void onPostExecute(Move move) {
        if (move != null) {
            EngineConnection.this.onEngineStopped(move);
        }
    }
}
