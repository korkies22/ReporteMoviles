// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import android.graphics.Bitmap;
import de.cisha.chess.model.CishaUUID;

public interface ICouchImageService
{
    Bitmap getCouchImage(final CishaUUID p0, final int p1, final int p2, final CouchImageLoadCommandCallback p3);
    
    public interface CouchImageLoadCommandCallback extends LoadCommandCallback<Bitmap>
    {
        void noUpdateNeeded();
    }
}
