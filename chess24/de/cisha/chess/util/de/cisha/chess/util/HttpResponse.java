/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.util;

public class HttpResponse {
    private String _body;
    private int _httpErrorCode;
    private State _state;

    private HttpResponse(State state, int n, String string) {
        this._state = state;
        this._httpErrorCode = n;
        this._body = string;
    }

    public String getBody() {
        return this._body;
    }

    public int getHttpErrorCode() {
        return this._httpErrorCode;
    }

    public boolean isHTTPError() {
        if (this._state == State.HTTP_ERROR) {
            return true;
        }
        return false;
    }

    public boolean isIOError() {
        if (this._state == State.IO_ERROR) {
            return true;
        }
        return false;
    }

    public boolean isOk() {
        if (this._state == State.OK) {
            return true;
        }
        return false;
    }

    public static class Builder {
        private String bBody = "";
        private int bErrorCode = 200;
        private State bState = State.OK;

        public HttpResponse create() {
            return new HttpResponse(this.bState, this.bErrorCode, this.bBody);
        }

        public Builder setBody(String string) {
            this.bBody = string;
            return this;
        }

        public Builder setHttpErrorCode(int n) {
            this.bErrorCode = n;
            return this;
        }

        public Builder setState(State state) {
            this.bState = state;
            return this;
        }
    }

    public static enum State {
        OK,
        HTTP_ERROR,
        IO_ERROR;
        

        private State() {
        }
    }

}
