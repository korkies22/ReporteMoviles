/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.os.AsyncTask
 */
package de.cisha.android.view;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import de.cisha.android.board.service.ICouchImageService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.view.CouchImageView;
import de.cisha.chess.model.CishaUUID;

private class CouchImageView.CouchImageLoader
extends AsyncTask<CishaUUID, Void, Bitmap> {
    private CouchImageView.CouchImageLoader() {
    }

    protected /* varargs */ Bitmap doInBackground(CishaUUID ... object) {
        if (CouchImageView.this._updateCallback != null) {
            CouchImageView.this._updateCallback.cancel();
        }
        object = new CouchImageView.CouchUpdateCallbackWithTimeout(CouchImageView.this, null);
        CouchImageView.this._updateCallback = (CouchImageView.CouchUpdateCallbackWithTimeout)object;
        return ServiceProvider.getInstance().getCouchImageService().getCouchImage(CouchImageView.this._couchId, CouchImageView.this.getWidth(), CouchImageView.this.getHeight(), (ICouchImageService.CouchImageLoadCommandCallback)object);
    }

    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            CouchImageView.this.setImageBitmap(bitmap);
        }
    }
}
