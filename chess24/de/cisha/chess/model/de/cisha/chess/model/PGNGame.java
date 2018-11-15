/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameHolder;
import de.cisha.chess.model.GameType;
import de.cisha.chess.util.PGNReader;

public class PGNGame
extends Game {
    private static final long serialVersionUID = -2309537943122771430L;
    private String _gameHeaderPGNString;
    private String _gamePGNMovesString = "";
    private boolean _isReadingMoves = false;
    private boolean _readMoves = true;

    public PGNGame() {
        this.setType(GameType.PGN_GAME);
    }

    public String getGamePGNString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this._gameHeaderPGNString);
        stringBuilder.append("\n\n");
        stringBuilder.append(this._gamePGNMovesString);
        return stringBuilder.toString();
    }

    public boolean hasReadMoveList() {
        if (!this._isReadingMoves && this._readMoves) {
            return true;
        }
        return false;
    }

    public void readPGNData(PGNReader pGNReader) {
        synchronized (this) {
            if (!this._readMoves) {
                this._isReadingMoves = true;
                PGNReader.PGNReaderFinishDelegate pGNReaderFinishDelegate = new PGNReader.PGNReaderFinishDelegate(){

                    @Override
                    public void finishedReadingMoves() {
                        PGNGame.this._isReadingMoves = false;
                    }
                };
                this._readMoves = true;
                pGNReader.readGameDataBlock(this, this._gamePGNMovesString, pGNReaderFinishDelegate, null);
                this._gamePGNMovesString = "";
            }
            return;
        }
    }

    public void setGamePGNString(String string) {
        synchronized (this) {
            this._gamePGNMovesString = string;
            this._readMoves = false;
            return;
        }
    }

    public void setHeaderString(String string) {
        this._gameHeaderPGNString = string;
    }

}
