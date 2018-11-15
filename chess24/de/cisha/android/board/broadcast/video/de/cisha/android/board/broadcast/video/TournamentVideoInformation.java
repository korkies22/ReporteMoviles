/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package de.cisha.android.board.broadcast.video;

import android.net.Uri;
import de.cisha.chess.model.Country;

public class TournamentVideoInformation {
    Country _language;
    Uri _uri;

    public TournamentVideoInformation(Country country, Uri uri) {
        this._language = country;
        this._uri = uri;
    }

    public Country getLanguage() {
        return this._language;
    }

    public Uri getUri() {
        return this._uri;
    }
}
