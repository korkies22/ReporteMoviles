/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  org.json.JSONObject
 */
package de.cisha.android.board.widget;

import android.content.Context;
import android.graphics.Bitmap;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.widget.TournamentWidgetService;
import java.util.List;
import org.json.JSONObject;

class TournamentWidgetService.TournamentRemoteViewsFactory
extends LoadCommandCallbackWithTimeout<Bitmap> {
    TournamentWidgetService.TournamentRemoteViewsFactory() {
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
    }

    @Override
    protected void succeded(Bitmap bitmap) {
        TournamentRemoteViewsFactory.this.this$0.refreshTournamentsList(TournamentRemoteViewsFactory.this._context);
    }
}
