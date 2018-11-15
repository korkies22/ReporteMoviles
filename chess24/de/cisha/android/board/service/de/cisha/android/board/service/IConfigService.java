/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.NodeServerAddress;
import de.cisha.android.board.service.ServerAPIStatus;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;

public interface IConfigService {
    public void checkServerBaseUrl(LoadCommandCallback<ServerAPIStatus> var1);

    public String getAPIVersion();

    public String getBasicAuthPasswort();

    public String getBasicAuthUsername();

    public void getMobileAPIURL(LoadCommandCallback<String> var1);

    public void getPairingServerURL(LoadCommandCallback<NodeServerAddress> var1);

    public String getVersionString();

    public boolean isDebugMode();

    public boolean useBasicAuth();
}
