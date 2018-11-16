// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

import de.cisha.chess.util.PGNReader;

public class PGNGame extends Game
{
    private static final long serialVersionUID = -2309537943122771430L;
    private String _gameHeaderPGNString;
    private String _gamePGNMovesString;
    private boolean _isReadingMoves;
    private boolean _readMoves;
    
    public PGNGame() {
        this._gamePGNMovesString = "";
        this._readMoves = true;
        this._isReadingMoves = false;
        this.setType(GameType.PGN_GAME);
    }
    
    public String getGamePGNString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this._gameHeaderPGNString);
        sb.append("\n\n");
        sb.append(this._gamePGNMovesString);
        return sb.toString();
    }
    
    public boolean hasReadMoveList() {
        return !this._isReadingMoves && this._readMoves;
    }
    
    public void readPGNData(final PGNReader pgnReader) {
        synchronized (this) {
            if (!this._readMoves) {
                this._isReadingMoves = true;
                final PGNReader.PGNReaderFinishDelegate pgnReaderFinishDelegate = new PGNReader.PGNReaderFinishDelegate() {
                    @Override
                    public void finishedReadingMoves() {
                        PGNGame.this._isReadingMoves = false;
                    }
                };
                this._readMoves = true;
                pgnReader.readGameDataBlock(this, this._gamePGNMovesString, (PGNReader.PGNReaderFinishDelegate)pgnReaderFinishDelegate, null);
                this._gamePGNMovesString = "";
            }
        }
    }
    
    public void setGamePGNString(final String gamePGNMovesString) {
        synchronized (this) {
            this._gamePGNMovesString = gamePGNMovesString;
            this._readMoves = false;
        }
    }
    
    public void setHeaderString(final String gameHeaderPGNString) {
        this._gameHeaderPGNString = gameHeaderPGNString;
    }
}
