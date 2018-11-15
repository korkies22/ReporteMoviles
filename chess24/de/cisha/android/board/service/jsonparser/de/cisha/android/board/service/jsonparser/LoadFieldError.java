/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service.jsonparser;

public class LoadFieldError {
    private String _key;
    private String _message;

    public LoadFieldError(String string, String string2) {
        this._key = string;
        this._message = string2;
    }

    public String getKey() {
        return this._key;
    }

    public String getMessage() {
        return this._message;
    }
}
