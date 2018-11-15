/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.InternetAvailabiltyService;

public interface IInternetAvailabilityService {
    public void addNetworkListener(InternetAvailabiltyService.NetworkListener var1);

    public boolean isNetworkAvailable();

    public void removeNetworkListener(InternetAvailabiltyService.NetworkListener var1);
}
