// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import org.json.JSONException;
import org.json.JSONObject;
import de.cisha.android.board.service.ISerializedRepresentationHolder;

public class JSONSerializedReprHolderParser<Type> extends JSONAPIResultParser<ISerializedRepresentationHolder<Type>>
{
    private JSONAPIResultParser<Type> _parser;
    
    public JSONSerializedReprHolderParser(final JSONAPIResultParser<Type> parser) {
        this._parser = parser;
    }
    
    @Override
    public ISerializedRepresentationHolder<Type> parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException, JSONException {
        return new MySerializeRepr(this._parser.parseResult(jsonObject), jsonObject);
    }
    
    private class MySerializeRepr implements ISerializedRepresentationHolder<Type>
    {
        private JSONObject _jsonData;
        private Type _object;
        
        public MySerializeRepr(final Type object, final JSONObject jsonData) {
            this._object = object;
            this._jsonData = jsonData;
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
