/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader;
import org.json.JSONObject;

public static interface GeneralJSONAPICommandLoader.JSONOutputListener<E> {
    public void output(E var1, JSONObject var2);
}
