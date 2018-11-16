// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.model;

import android.net.Uri;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.model.Game;
import de.cisha.android.board.video.command.VideoCommand;
import java.util.List;
import de.cisha.android.board.model.BoardMarks;
import de.cisha.chess.model.MoveContainer;
import java.util.Map;

public class Video
{
    private String _author;
    private String _authorTitleCode;
    private Map<MoveContainer, BoardMarks> _boardMarkings;
    private List<VideoCommand> _commands;
    private String _description;
    private long _durationMillis;
    private List<Game> _games;
    private CishaUUID _teaserImageCouchId;
    private String _title;
    private Uri _videoUrlMp4;
    
    public Video(final String title, final String author, final String authorTitleCode, final String description, final long durationMillis, final CishaUUID teaserImageCouchId, final Uri videoUrlMp4, final List<Game> games, final List<VideoCommand> commands, final Map<MoveContainer, BoardMarks> boardMarkings) {
        this._title = title;
        this._author = author;
        this._authorTitleCode = authorTitleCode;
        this._description = description;
        this._durationMillis = durationMillis;
        this._teaserImageCouchId = teaserImageCouchId;
        this._videoUrlMp4 = videoUrlMp4;
        this._games = games;
        this._commands = commands;
        this._boardMarkings = boardMarkings;
    }
    
    public String getAuthor() {
        return this._author;
    }
    
    public String getAuthorTitleCode() {
        return this._authorTitleCode;
    }
    
    public Map<MoveContainer, BoardMarks> getBoardMarkings() {
        return this._boardMarkings;
    }
    
    public List<VideoCommand> getCommands() {
        return this._commands;
    }
    
    public String getDescription() {
        return this._description;
    }
    
    public long getDurationMillis() {
        return this._durationMillis;
    }
    
    public List<Game> getGames() {
        return this._games;
    }
    
    public CishaUUID getTeaserImageCouchId() {
        return this._teaserImageCouchId;
    }
    
    public String getTitle() {
        return this._title;
    }
    
    public Uri getVideoUrlMp4() {
        return this._videoUrlMp4;
    }
}
