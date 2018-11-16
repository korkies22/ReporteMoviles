// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.video;

import android.net.Uri;
import de.cisha.chess.model.Country;

public class TournamentVideoInformation
{
    Country _language;
    Uri _uri;
    
    public TournamentVideoInformation(final Country language, final Uri uri) {
        this._language = language;
        this._uri = uri;
    }
    
    public Country getLanguage() {
        return this._language;
    }
    
    public Uri getUri() {
        return this._uri;
    }
}
