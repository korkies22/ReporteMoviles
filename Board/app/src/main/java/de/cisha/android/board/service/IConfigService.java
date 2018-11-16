// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.android.board.service.jsonparser.LoadCommandCallback;

public interface IConfigService
{
    void checkServerBaseUrl(final LoadCommandCallback<ServerAPIStatus> p0);
    
    String getAPIVersion();
    
    String getBasicAuthPasswort();
    
    String getBasicAuthUsername();
    
    void getMobileAPIURL(final LoadCommandCallback<String> p0);
    
    void getPairingServerURL(final LoadCommandCallback<NodeServerAddress> p0);
    
    String getVersionString();
    
    boolean isDebugMode();
    
    boolean useBasicAuth();
}
