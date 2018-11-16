// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.util;

public class HttpResponse
{
    private String _body;
    private int _httpErrorCode;
    private State _state;
    
    private HttpResponse(final State state, final int httpErrorCode, final String body) {
        this._state = state;
        this._httpErrorCode = httpErrorCode;
        this._body = body;
    }
    
    public String getBody() {
        return this._body;
    }
    
    public int getHttpErrorCode() {
        return this._httpErrorCode;
    }
    
    public boolean isHTTPError() {
        return this._state == State.HTTP_ERROR;
    }
    
    public boolean isIOError() {
        return this._state == State.IO_ERROR;
    }
    
    public boolean isOk() {
        return this._state == State.OK;
    }
    
    public static class Builder
    {
        private String bBody;
        private int bErrorCode;
        private State bState;
        
        public Builder() {
            this.bBody = "";
            this.bState = State.OK;
            this.bErrorCode = 200;
        }
        
        public HttpResponse create() {
            return new HttpResponse(this.bState, this.bErrorCode, this.bBody, null);
        }
        
        public Builder setBody(final String bBody) {
            this.bBody = bBody;
            return this;
        }
        
        public Builder setHttpErrorCode(final int bErrorCode) {
            this.bErrorCode = bErrorCode;
            return this;
        }
        
        public Builder setState(final State bState) {
            this.bState = bState;
            return this;
        }
    }
    
    public enum State
    {
        HTTP_ERROR, 
        IO_ERROR, 
        OK;
    }
}
