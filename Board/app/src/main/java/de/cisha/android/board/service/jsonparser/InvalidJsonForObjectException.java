// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

public class InvalidJsonForObjectException extends Exception
{
    private static final long serialVersionUID = -4928966488411790780L;
    
    public InvalidJsonForObjectException() {
    }
    
    public InvalidJsonForObjectException(final String s) {
        super(s);
    }
    
    public InvalidJsonForObjectException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public InvalidJsonForObjectException(final Throwable t) {
        super(t);
    }
}
