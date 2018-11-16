// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import java.util.Date;

public interface ServerAPIStatus
{
    Date getDeprecationDate();
    
    boolean isDeprecated();
    
    boolean isValid();
}
