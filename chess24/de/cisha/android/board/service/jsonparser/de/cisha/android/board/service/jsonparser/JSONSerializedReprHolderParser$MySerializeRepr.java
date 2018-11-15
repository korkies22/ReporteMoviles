/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.service.ISerializedRepresentationHolder;
import de.cisha.android.board.service.jsonparser.JSONSerializedReprHolderParser;
import org.json.JSONObject;

private class JSONSerializedReprHolderParser.MySerializeRepr
implements ISerializedRepresentationHolder<Type> {
    private JSONObject _jsonData;
    private Type _object;

    public JSONSerializedReprHolderParser.MySerializeRepr(Type Type, JSONObject jSONObject) {
        this._object = Type;
        this._jsonData = jSONObject;
    }

    @Override
    public JSONObject getJSONData() {
        return this._jsonData;
    }

    @Override
    public Type getObject() {
        return this._object;
    }
}
