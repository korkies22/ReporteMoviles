/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

public interface LoadCommandCallback<E> {
    public void loadingCancelled();

    public void loadingFailed(APIStatusCode var1, String var2, List<LoadFieldError> var3, JSONObject var4);

    public void loadingSucceded(E var1);
}
