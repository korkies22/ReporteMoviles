/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 */
package de.cisha.android.board.service;

import android.graphics.Bitmap;
import de.cisha.android.board.service.ICouchImageService;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;

public static interface ICouchImageService.CouchImageLoadCommandCallback
extends LoadCommandCallback<Bitmap> {
    public void noUpdateNeeded();
}
