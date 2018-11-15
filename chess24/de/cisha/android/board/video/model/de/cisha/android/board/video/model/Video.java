/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package de.cisha.android.board.video.model;

import android.net.Uri;
import de.cisha.android.board.model.BoardMarks;
import de.cisha.android.board.video.command.VideoCommand;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.MoveContainer;
import java.util.List;
import java.util.Map;

public class Video {
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

    public Video(String string, String string2, String string3, String string4, long l, CishaUUID cishaUUID, Uri uri, List<Game> list, List<VideoCommand> list2, Map<MoveContainer, BoardMarks> map) {
        this._title = string;
        this._author = string2;
        this._authorTitleCode = string3;
        this._description = string4;
        this._durationMillis = l;
        this._teaserImageCouchId = cishaUUID;
        this._videoUrlMp4 = uri;
        this._games = list;
        this._commands = list2;
        this._boardMarkings = map;
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
