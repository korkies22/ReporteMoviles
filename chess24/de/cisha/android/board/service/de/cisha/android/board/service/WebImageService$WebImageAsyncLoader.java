/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.os.AsyncTask
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import de.cisha.android.board.service.WebImageService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.util.Logger;
import java.util.List;
import org.json.JSONObject;

protected class WebImageService.WebImageAsyncLoader
extends AsyncTask<String, Void, Bitmap> {
    private LoadCommandCallback<Bitmap> _callback;

    public WebImageService.WebImageAsyncLoader(LoadCommandCallback<Bitmap> loadCommandCallback) {
        this._callback = loadCommandCallback;
    }

    protected /* varargs */ Bitmap doInBackground(String ... bitmap) {
        try {
            bitmap = WebImageService.this.getWebImage(bitmap[0]);
            return bitmap;
        }
        catch (WebImageService.NoImageFoundAtURLException noImageFoundAtURLException) {
            Logger.getInstance().debug(this.getClass().getName(), WebImageService.NoImageFoundAtURLException.class.getName(), noImageFoundAtURLException);
            return null;
        }
    }

    protected void onPostExecute(Bitmap bitmap) {
        if (this.isCancelled()) {
            if (bitmap != null) {
                this._callback.loadingSucceded(bitmap);
                return;
            }
            this._callback.loadingFailed(APIStatusCode.INTERNAL_UNKNOWN, "image didn't contain image", null, null);
            return;
        }
        this._callback.loadingCancelled();
    }
}
