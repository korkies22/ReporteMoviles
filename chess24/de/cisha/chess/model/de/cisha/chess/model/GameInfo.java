/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameID;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.GameType;
import de.cisha.chess.model.Opponent;
import java.util.Date;

public class GameInfo {
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

    public GameInfo(Game game) {
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

    public GameInfo(GameID gameID) {
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
        if (this._blackplayer != null && this._blackplayer.getName() != null && !"".equals(this._blackplayer.getName().trim()) || this._whitePlayer != null && this._whitePlayer.getName() != null && !"".equals(this._whitePlayer.getName().trim())) {
            return true;
        }
        return false;
    }

    public void setBlackplayer(Opponent opponent) {
        this._blackplayer = opponent;
    }

    public void setDate(Date date) {
        this._date = date;
    }

    public void setEvent(String string) {
        this._event = string;
    }

    public void setOriginalGameID(GameID gameID) {
        this._originGameID = gameID;
    }

    public void setOriginalGameType(GameType gameType) {
        this._originGameType = gameType;
    }

    public void setResultStatus(GameStatus gameStatus) {
        this._resultStatus = gameStatus;
    }

    public void setSite(String string) {
        this._site = string;
    }

    public void setTitle(String string) {
        this._title = string;
    }

    public void setType(GameType gameType) {
        this._type = gameType;
    }

    public void setWhitePlayer(Opponent opponent) {
        this._whitePlayer = opponent;
    }
}
