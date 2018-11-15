/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.model.AbstractMoveContainer;
import de.cisha.chess.model.ClockSetting;
import de.cisha.chess.model.Content;
import de.cisha.chess.model.ContentId;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.GameID;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.GameType;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.Opponent;
import de.cisha.chess.model.Tournament;
import de.cisha.chess.model.position.Position;
import java.io.Serializable;
import java.util.Date;

public class Game
extends AbstractMoveContainer
implements Serializable,
Content {
    private static final long serialVersionUID = -3985217764437220118L;
    private Opponent _blackPlayer;
    private int _board;
    private ClockSetting _clockSetting;
    private ContentId _contentId;
    private String _event;
    private Move _firstMove;
    private GameID _gameId;
    private GameID _originalGameId;
    private GameType _originalGameType;
    private GameStatus _result = GameStatus.GAME_RUNNING;
    private int _round = -1;
    private String _site;
    private Date _startDate;
    private Position _startingPosition;
    private String _title;
    private Tournament _tournament;
    private GameType _type = GameType.UNDEFINED;
    private Opponent _whitePlayer;

    public Game() {
        this._startDate = new Date();
        this._startingPosition = new Position(FEN.STARTING_POSITION);
        this._whitePlayer = new Opponent();
        this._blackPlayer = new Opponent();
        this._clockSetting = new ClockSetting(0, 0);
        this._event = "";
        this._site = "";
    }

    protected Game(Game game) {
        super(game);
        this._startingPosition = game.getStartingPosition();
        this._startDate = game.getStartDate();
        this._whitePlayer = game.getWhitePlayer();
        this._blackPlayer = game.getBlackPlayer();
        this._clockSetting = game.getClockSetting();
        this._originalGameId = game.getOriginalGameId();
        this._gameId = game.getGameId();
        this._result = game.getResult();
        this._board = game.getBoard();
        this._round = game.getRound();
        this._title = game.getTitle();
        this._tournament = game.getTournament();
        this.setType(game.getType());
        this._originalGameType = game.getOriginalGameType();
        this._event = game.getEvent();
        this._site = game.getSite();
    }

    @Override
    public void addMoveInMainLine(Move move) {
        if (this._firstMove == null) {
            this._firstMove = move;
        }
        super.addMoveInMainLine(move);
    }

    @Override
    public Game copy() {
        return new Game(this);
    }

    public Opponent getBlackPlayer() {
        return this._blackPlayer;
    }

    public int getBoard() {
        return this._board;
    }

    public ClockSetting getClockSetting() {
        return this._clockSetting;
    }

    @Override
    public ContentId getContentId() {
        return this._contentId;
    }

    public String getEvent() {
        return this._event;
    }

    @Override
    public Move getFirstMove() {
        return this.getNextMove();
    }

    @Override
    public Game getGame() {
        return this;
    }

    public GameID getGameId() {
        return this._gameId;
    }

    public GameID getOriginalGameId() {
        return this._originalGameId;
    }

    public GameType getOriginalGameType() {
        return this._originalGameType;
    }

    public GameStatus getResult() {
        return this._result;
    }

    @Override
    public Position getResultingPosition() {
        return this.getStartingPosition();
    }

    public int getRound() {
        return this._round;
    }

    public String getSite() {
        return this._site;
    }

    public Date getStartDate() {
        return this._startDate;
    }

    public Position getStartingPosition() {
        return this._startingPosition;
    }

    public String getTitle() {
        return this._title;
    }

    public Tournament getTournament() {
        return this._tournament;
    }

    public GameType getType() {
        return this._type;
    }

    public Opponent getWhitePlayer() {
        return this._whitePlayer;
    }

    public boolean isFinished() {
        if (this._result != null) {
            return this._result.isFinished();
        }
        return false;
    }

    @Override
    public boolean isMainLine() {
        return true;
    }

    public void setBlackPlayer(Opponent opponent) {
        this._blackPlayer = opponent;
    }

    public void setBoard(int n) {
        this._board = n;
    }

    public void setEvent(String string) {
        this._event = string;
    }

    public void setGameId(GameID gameID) {
        this._gameId = gameID;
    }

    public void setOriginalGameId(GameID gameID) {
        this._originalGameId = gameID;
    }

    public void setOriginalGameType(GameType gameType) {
        this._originalGameType = gameType;
    }

    public void setResult(GameStatus gameStatus) {
        GameStatus gameStatus2 = gameStatus;
        if (gameStatus == null) {
            gameStatus2 = GameStatus.GAME_RUNNING;
        }
        this._result = gameStatus2;
    }

    public void setRound(int n) {
        this._round = n;
    }

    public void setSite(String string) {
        this._site = string;
    }

    public void setStartDate(Date date) {
        this._startDate = date;
    }

    public void setStartingPosition(Position position) {
        this._startingPosition = position;
    }

    public void setTitle(String string) {
        this._title = string;
    }

    public void setTournament(Tournament tournament) {
        this._tournament = tournament;
    }

    public void setType(GameType gameType) {
        if (this._type == GameType.UNDEFINED) {
            this._originalGameType = gameType;
        }
        this._type = gameType;
    }

    public void setWhitePlayer(Opponent opponent) {
        this._whitePlayer = opponent;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Game: id:");
        stringBuilder.append(this.getId());
        stringBuilder.append(" :");
        String string = this._whitePlayer != null ? this._whitePlayer.getName() : "";
        stringBuilder.append(string);
        stringBuilder.append(" - ");
        string = this._blackPlayer != null ? this._blackPlayer.getName() : "";
        stringBuilder.append(string);
        return stringBuilder.toString();
    }
}
