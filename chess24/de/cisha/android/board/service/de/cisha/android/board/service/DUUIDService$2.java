/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.util.Logger;
import java.util.List;
import org.json.JSONObject;

class DUUIDService
extends LoadCommandCallbackWithTimeout<CishaUUID> {
    DUUIDService() {
    }

    @Override
    public void failed(APIStatusCode object, String string, List<LoadFieldError> object2, JSONObject jSONObject) {
        DUUIDService.this._isInitializing = false;
        object = Logger.getInstance();
        object2 = new StringBuilder();
        object2.append("Error getting Device UUID: ");
        object2.append(string);
        object.debug(object2.toString());
    }

    @Override
    public void succeded(CishaUUID cishaUUID) {
        DUUIDService.this._duuid = cishaUUID;
        DUUIDService.this._initialized = true;
        Logger.getInstance().debug("Loading duuid succeded");
        DUUIDService.this.storeUuid();
        DUUIDService.this._isInitializing = false;
    }
}
