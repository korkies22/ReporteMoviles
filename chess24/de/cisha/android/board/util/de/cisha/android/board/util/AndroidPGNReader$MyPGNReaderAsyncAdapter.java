/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 */
package de.cisha.android.board.util;

import android.os.AsyncTask;
import de.cisha.android.board.util.AndroidPGNReader;
import de.cisha.chess.model.PGNGame;
import de.cisha.chess.util.PGNReaderDelegate;

private class AndroidPGNReader.MyPGNReaderAsyncAdapter
extends AsyncTask<String, PGNGame, Void>
implements PGNReaderDelegate {
    private PGNReaderDelegate _delegate;

    public AndroidPGNReader.MyPGNReaderAsyncAdapter(PGNReaderDelegate pGNReaderDelegate) {
        this._delegate = pGNReaderDelegate;
    }

    @Override
    public void addPGNGame(PGNGame pGNGame) {
        this.publishProgress((Object[])new PGNGame[]{pGNGame});
    }

    protected /* varargs */ Void doInBackground(String ... arrstring) {
        String string = arrstring[0];
        arrstring[0] = null;
        AndroidPGNReader.super.readPGN(string, this);
        return null;
    }

    @Override
    public void finishedReadingPGN() {
    }

    protected void onPostExecute(Void void_) {
        this._delegate.finishedReadingPGN();
    }

    protected /* varargs */ void onProgressUpdate(PGNGame ... arrpGNGame) {
        this._delegate.addPGNGame(arrpGNGame[0]);
    }
}
