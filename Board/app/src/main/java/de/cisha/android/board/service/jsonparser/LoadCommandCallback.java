// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import org.json.JSONObject;
import java.util.List;

public interface LoadCommandCallback<E>
{
    void loadingCancelled();
    
    void loadingFailed(final APIStatusCode p0, final String p1, final List<LoadFieldError> p2, final JSONObject p3);
    
    void loadingSucceded(final E p0);
}
