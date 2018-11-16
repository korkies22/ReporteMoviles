// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

public class LoadFieldError
{
    private String _key;
    private String _message;
    
    public LoadFieldError(final String key, final String message) {
        this._key = key;
        this._message = message;
    }
    
    public String getKey() {
        return this._key;
    }
    
    public String getMessage() {
        return this._message;
    }
}
