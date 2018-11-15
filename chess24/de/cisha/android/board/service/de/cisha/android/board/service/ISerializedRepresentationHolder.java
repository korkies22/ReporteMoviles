/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import org.json.JSONObject;

public interface ISerializedRepresentationHolder<Type> {
    public JSONObject getJSONData();

    public Type getObject();
}
