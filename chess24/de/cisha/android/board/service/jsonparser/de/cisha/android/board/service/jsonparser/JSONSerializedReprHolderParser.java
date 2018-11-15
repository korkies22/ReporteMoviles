/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.service.ISerializedRepresentationHolder;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONSerializedReprHolderParser<Type>
extends JSONAPIResultParser<ISerializedRepresentationHolder<Type>> {
    private JSONAPIResultParser<Type> _parser;

    public JSONSerializedReprHolderParser(JSONAPIResultParser<Type> jSONAPIResultParser) {
        this._parser = jSONAPIResultParser;
    }

    @Override
    public ISerializedRepresentationHolder<Type> parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException, JSONException {
        return new MySerializeRepr(this._parser.parseResult(jSONObject), jSONObject);
    }

    private class MySerializeRepr
    implements ISerializedRepresentationHolder<Type> {
        private JSONObject _jsonData;
        private Type _object;

        public MySerializeRepr(Type Type, JSONObject jSONObject) {
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

}
