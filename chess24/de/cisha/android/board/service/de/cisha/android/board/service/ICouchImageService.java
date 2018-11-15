/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 */
package de.cisha.android.board.service;

import android.graphics.Bitmap;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.chess.model.CishaUUID;

public interface ICouchImageService {
    public Bitmap getCouchImage(CishaUUID var1, int var2, int var3, CouchImageLoadCommandCallback var4);

    public static interface CouchImageLoadCommandCallback
    extends LoadCommandCallback<Bitmap> {
        public void noUpdateNeeded();
    }

}
