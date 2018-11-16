// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

import java.util.Date;

public class GameInfo
{
    private Opponent _blackplayer;
    private Date _date;
    private String _event;
    private GameID _gameID;
    private GameID _originGameID;
    private GameType _originGameType;
    private GameStatus _resultStatus;
    private String _site;
    private String _title;
    private GameType _type;
    private Opponent _whitePlayer;
    
    public GameInfo(final Game game) {
        this._title = game.getTitle();
        this._event = game.getEvent();
        this._site = game.getSite();
        this._date = game.getStartDate();
        this._gameID = game.getGameId();
        this._originGameID = game.getOriginalGameId();
        this._type = game.getType();
        this._whitePlayer = game.getWhitePlayer();
        this._blackplayer = game.getBlackPlayer();
        this._resultStatus = game.getResult();
        this._originGameType = game.getOriginalGameType();
    }
    
    public GameInfo(final GameID gameID) {
        this._gameID = gameID;
    }
    
    public Opponent getBlackplayer() {
        return this._blackplayer;
    }
    
    public Date getDate() {
        return this._date;
    }
    
    public String getEvent() {
        return this._event;
    }
    
    public GameID getGameID() {
        return this._gameID;
    }
    
    public GameID getOriginalGameID() {
        return this._originGameID;
    }
    
    public GameType getOriginalGameType() {
        return this._originGameType;
    }
    
    public GameStatus getResultStatus() {
        return this._resultStatus;
    }
    
    public String getSite() {
        return this._site;
    }
    
    public String getTitle() {
        return this._title;
    }
    
    public GameType getType() {
        return this._type;
    }
    
    public Opponent getWhitePlayer() {
        return this._whitePlayer;
    }
    
    public boolean hasPlayerName() {
        return (this._blackplayer != null && this._blackplayer.getName() != null && !"".equals(this._blackplayer.getName().trim())) || (this._whitePlayer != null && this._whitePlayer.getName() != null && !"".equals(this._whitePlayer.getName().trim()));
    }
    
    public void setBlackplayer(final Opponent blackplayer) {
        this._blackplayer = blackplayer;
    }
    
    public void setDate(final Date date) {
        this._date = date;
    }
    
    public void setEvent(final String event) {
        this._event = event;
    }
    
    public void setOriginalGameID(final GameID originGameID) {
        this._originGameID = originGameID;
    }
    
    public void setOriginalGameType(final GameType originGameType) {
        this._originGameType = originGameType;
    }
    
    public void setResultStatus(final GameStatus resultStatus) {
        this._resultStatus = resultStatus;
    }
    
    public void setSite(final String site) {
        this._site = site;
    }
    
    public void setTitle(final String title) {
        this._title = title;
    }
    
    public void setType(final GameType type) {
        this._type = type;
    }
    
    public void setWhitePlayer(final Opponent whitePlayer) {
        this._whitePlayer = whitePlayer;
    }
}
