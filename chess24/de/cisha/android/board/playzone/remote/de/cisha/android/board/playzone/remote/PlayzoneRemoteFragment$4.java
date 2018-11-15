/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.playzone.remote;

import de.cisha.android.board.playzone.model.PlayzoneGameSessionInfo;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.util.Logger;
import java.util.List;
import org.json.JSONObject;

class PlayzoneRemoteFragment
implements LoadCommandCallback<List<PlayzoneGameSessionInfo>> {
    PlayzoneRemoteFragment() {
    }

    @Override
    public void loadingCancelled() {
    }

    @Override
    public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> object, JSONObject object2) {
        object = Logger.getInstance();
        object2 = this.getClass().getName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("error loading open Games: errorCode: ");
        stringBuilder.append((Object)aPIStatusCode);
        stringBuilder.append(" message: ");
        stringBuilder.append(string);
        object.error((String)object2, stringBuilder.toString());
        PlayzoneRemoteFragment.this.loadingFinished();
    }

    @Override
    public void loadingSucceded(List<PlayzoneGameSessionInfo> list) {
        PlayzoneRemoteFragment.this._openGameSessions = list;
        if (!PlayzoneRemoteFragment.this.hasRunningGame() && list != null && list.size() > 0) {
            PlayzoneRemoteFragment.this.resumeGame(list.get(0));
            PlayzoneRemoteFragment.this._openGameSessions = null;
        }
        PlayzoneRemoteFragment.this.loadingFinished();
    }
}
