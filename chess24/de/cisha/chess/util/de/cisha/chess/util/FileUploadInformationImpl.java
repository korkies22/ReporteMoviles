/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.util;

import de.cisha.chess.util.HTTPHelper;
import java.io.InputStream;

public class FileUploadInformationImpl
implements HTTPHelper.FileUploadInformation {
    private String _mimeType;
    private String _name;
    private InputStream _stream;

    public FileUploadInformationImpl(String string, InputStream inputStream, String string2) {
        this._name = string;
        this._stream = inputStream;
        this._mimeType = string2;
    }

    @Override
    public String getMimeType() {
        return this._mimeType;
    }

    @Override
    public String getName() {
        return this._name;
    }

    @Override
    public InputStream getStream() {
        return this._stream;
    }
}
