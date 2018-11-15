/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.TextView
 *  org.json.JSONObject
 */
package de.cisha.android.board.tactics;

import android.widget.TextView;
import de.cisha.android.board.LoadingHelperWithAPIStatusCode;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.model.Rating;
import java.util.List;
import org.json.JSONObject;

class TacticsStartFragment
extends LoadCommandCallbackWithTimeout<Rating> {
    TacticsStartFragment() {
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        TacticsStartFragment.this._loadingHelper.loadingFailed(this, aPIStatusCode);
    }

    @Override
    protected void succeded(Rating rating) {
        TacticsStartFragment.this._usersRating = rating;
        TacticsStartFragment.this._ratingTextView.setText((CharSequence)rating.getRatingString());
        TacticsStartFragment.this._loadingHelper.loadingCompleted(this);
    }
}
