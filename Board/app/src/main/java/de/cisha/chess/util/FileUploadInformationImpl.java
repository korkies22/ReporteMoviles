// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.util;

import java.io.InputStream;

public class FileUploadInformationImpl implements FileUploadInformation
{
    private String _mimeType;
    private String _name;
    private InputStream _stream;
    
    public FileUploadInformationImpl(final String name, final InputStream stream, final String mimeType) {
        this._name = name;
        this._stream = stream;
        this._mimeType = mimeType;
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
