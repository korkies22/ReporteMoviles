/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 */
package de.cisha.android.board.engine;

import android.os.AsyncTask;
import de.cisha.chess.engine.UCIEngine;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.position.Position;

class EngineController
extends AsyncTask<Void, Void, Move> {
    final /* synthetic */ int val$engineThinkingTime;

    EngineController(int n) {
        this.val$engineThinkingTime = n;
    }

    protected /* varargs */ Move doInBackground(Void ... object) {
        EngineController.this.notifiyObserversEngineStart();
        object = EngineController.this._engine.getMoveWithThinkingTime(this.val$engineThinkingTime);
        if (object != null) {
            return EngineController.this._engine.getCurrentPosition().createMoveFrom((SEP)object);
        }
        return null;
    }

    protected void onPostExecute(Move move) {
        EngineController.this.notifiyObserversEngineStop(move);
    }
}
