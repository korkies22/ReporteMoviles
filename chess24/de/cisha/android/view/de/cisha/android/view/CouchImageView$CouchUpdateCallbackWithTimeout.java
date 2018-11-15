/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  org.json.JSONObject
 */
package de.cisha.android.view;

import android.graphics.Bitmap;
import de.cisha.android.board.service.ICouchImageService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.view.CouchImageView;
import java.util.List;
import org.json.JSONObject;

private class CouchImageView.CouchUpdateCallbackWithTimeout
extends LoadCommandCallbackWithTimeout<Bitmap>
implements ICouchImageService.CouchImageLoadCommandCallback {
    private CouchImageView.CouchUpdateCallbackWithTimeout() {
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        CouchImageView.this._updateCallback = null;
    }

    @Override
    public void noUpdateNeeded() {
        if (!this.isCanceled()) {
            CouchImageView.this._updateCallback = null;
        }
    }

    @Override
    protected void succeded(Bitmap bitmap) {
        CouchImageView.this.setImageBitmap(bitmap);
        CouchImageView.this._updateCallback = null;
    }
}
