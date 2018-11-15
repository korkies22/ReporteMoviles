/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 */
package de.cisha.android.board.util;

import android.os.AsyncTask;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameHolder;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.util.PGNReader;

class AndroidPGNReader
extends AsyncTask<Void, Void, Void> {
    final /* synthetic */ Game val$game;
    final /* synthetic */ String val$gameDataBlock;
    final /* synthetic */ PGNReader.PGNReaderFinishDelegate val$optionalFinishedNotifier;
    final /* synthetic */ GameHolder val$optionalGameHolderToUseForInsertingMoves;

    AndroidPGNReader(Game game, String string, GameHolder gameHolder, PGNReader.PGNReaderFinishDelegate pGNReaderFinishDelegate) {
        this.val$game = game;
        this.val$gameDataBlock = string;
        this.val$optionalGameHolderToUseForInsertingMoves = gameHolder;
        this.val$optionalFinishedNotifier = pGNReaderFinishDelegate;
    }

    protected /* varargs */ Void doInBackground(Void ... arrvoid) {
        AndroidPGNReader.this.readMoveBlock(this.val$game, this.val$gameDataBlock, 0, this.val$optionalGameHolderToUseForInsertingMoves);
        return null;
    }

    protected void onPostExecute(Void void_) {
        if (this.val$optionalFinishedNotifier != null) {
            this.val$optionalFinishedNotifier.finishedReadingMoves();
        }
        super.onPostExecute((Object)void_);
    }
}
