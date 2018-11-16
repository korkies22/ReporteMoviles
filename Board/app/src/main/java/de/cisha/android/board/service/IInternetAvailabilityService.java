// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

public interface IInternetAvailabilityService
{
    void addNetworkListener(final InternetAvailabiltyService.NetworkListener p0);
    
    boolean isNetworkAvailable();
    
    void removeNetworkListener(final InternetAvailabiltyService.NetworkListener p0);
}
