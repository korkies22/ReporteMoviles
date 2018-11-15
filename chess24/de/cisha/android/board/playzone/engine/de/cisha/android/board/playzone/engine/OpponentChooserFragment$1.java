/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.playzone.engine;

import de.cisha.android.board.playzone.model.PlayzoneGameSessionInfo;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class OpponentChooserFragment
extends LoadCommandCallbackWithTimeout<List<PlayzoneGameSessionInfo>> {
    OpponentChooserFragment() {
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
    }

    @Override
    protected void succeded(final List<PlayzoneGameSessionInfo> list) {
        if (!list.isEmpty()) {
            OpponentChooserFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                @Override
                public void run() {
                    PlayzoneGameSessionInfo playzoneGameSessionInfo = (PlayzoneGameSessionInfo)list.get(0);
                    OpponentChooserFragment.this.resumeGame(playzoneGameSessionInfo);
                }
            });
        }
    }

}
