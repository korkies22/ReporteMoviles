// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import org.json.JSONObject;

public interface ISerializedRepresentationHolder<Type>
{
    JSONObject getJSONData();
    
    Type getObject();
}
