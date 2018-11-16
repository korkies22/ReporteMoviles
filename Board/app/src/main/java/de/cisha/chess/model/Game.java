// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

import de.cisha.chess.model.position.Position;
import java.util.Date;
import java.io.Serializable;

public class Game extends AbstractMoveContainer implements Serializable, Content
{
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
    private GameStatus _result;
    private int _round;
    private String _site;
    private Date _startDate;
    private Position _startingPosition;
    private String _title;
    private Tournament _tournament;
    private GameType _type;
    private Opponent _whitePlayer;
    
    public Game() {
        this._round = -1;
        this._type = GameType.UNDEFINED;
        this._result = GameStatus.GAME_RUNNING;
        this._startDate = new Date();
        this._startingPosition = new Position(FEN.STARTING_POSITION);
        this._whitePlayer = new Opponent();
        this._blackPlayer = new Opponent();
        this._clockSetting = new ClockSetting(0, 0);
        this._event = "";
        this._site = "";
    }
    
    protected Game(final Game game) {
        super(game);
        this._round = -1;
        this._type = GameType.UNDEFINED;
        this._result = GameStatus.GAME_RUNNING;
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
    public void addMoveInMainLine(final Move firstMove) {
        if (this._firstMove == null) {
            this._firstMove = firstMove;
        }
        super.addMoveInMainLine(firstMove);
    }
    
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
        return this._result != null && this._result.isFinished();
    }
    
    @Override
    public boolean isMainLine() {
        return true;
    }
    
    public void setBlackPlayer(final Opponent blackPlayer) {
        this._blackPlayer = blackPlayer;
    }
    
    public void setBoard(final int board) {
        this._board = board;
    }
    
    public void setEvent(final String event) {
        this._event = event;
    }
    
    public void setGameId(final GameID gameId) {
        this._gameId = gameId;
    }
    
    public void setOriginalGameId(final GameID originalGameId) {
        this._originalGameId = originalGameId;
    }
    
    public void setOriginalGameType(final GameType originalGameType) {
        this._originalGameType = originalGameType;
    }
    
    public void setResult(final GameStatus gameStatus) {
        GameStatus game_RUNNING = gameStatus;
        if (gameStatus == null) {
            game_RUNNING = GameStatus.GAME_RUNNING;
        }
        this._result = game_RUNNING;
    }
    
    public void setRound(final int round) {
        this._round = round;
    }
    
    public void setSite(final String site) {
        this._site = site;
    }
    
    public void setStartDate(final Date startDate) {
        this._startDate = startDate;
    }
    
    public void setStartingPosition(final Position startingPosition) {
        this._startingPosition = startingPosition;
    }
    
    public void setTitle(final String title) {
        this._title = title;
    }
    
    public void setTournament(final Tournament tournament) {
        this._tournament = tournament;
    }
    
    public void setType(final GameType gameType) {
        if (this._type == GameType.UNDEFINED) {
            this._originalGameType = gameType;
        }
        this._type = gameType;
    }
    
    public void setWhitePlayer(final Opponent whitePlayer) {
        this._whitePlayer = whitePlayer;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Game: id:");
        sb.append(this.getId());
        sb.append(" :");
        String name;
        if (this._whitePlayer != null) {
            name = this._whitePlayer.getName();
        }
        else {
            name = "";
        }
        sb.append(name);
        sb.append(" - ");
        String name2;
        if (this._blackPlayer != null) {
            name2 = this._blackPlayer.getName();
        }
        else {
            name2 = "";
        }
        sb.append(name2);
        return sb.toString();
    }
}
