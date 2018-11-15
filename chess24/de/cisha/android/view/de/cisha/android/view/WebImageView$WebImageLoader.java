/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.os.AsyncTask
 */
package de.cisha.android.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import de.cisha.android.board.service.WebImageService;
import de.cisha.android.view.WebImageView;
import de.cisha.chess.util.Logger;

protected class WebImageView.WebImageLoader
extends AsyncTask<String, Void, Bitmap> {
    protected /* varargs */ Bitmap doInBackground(String ... bitmap) {
        try {
            bitmap = WebImageService.getInstance(WebImageView.this.getContext()).getWebImage(bitmap[0]);
            return bitmap;
        }
        catch (WebImageService.NoImageFoundAtURLException noImageFoundAtURLException) {
            Logger.getInstance().debug(WebImageView.WebImageLoader.class.getName(), WebImageService.NoImageFoundAtURLException.class.getName(), noImageFoundAtURLException);
            return null;
        }
    }

    protected void onPostExecute(Bitmap bitmap) {
        if (!this.isCancelled()) {
            if (bitmap != null) {
                WebImageView.this.setWebImage(bitmap);
            } else {
                WebImageView.this.webImageLoadingFailed();
            }
        }
        super.onPostExecute((Object)bitmap);
    }
}
