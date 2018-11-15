/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 */
package de.cisha.android.board.service;

import android.graphics.Bitmap;
import de.cisha.android.board.profile.model.DashboardData;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.user.User;
import de.cisha.chess.model.CishaUUID;
import java.net.URL;
import java.util.Map;

public interface IProfileDataService {
    public void addUserDataChangedListener(IUserDataChangedListener var1);

    public DashboardData getCurrentDashboardData();

    public User getCurrentUserData();

    public void getDashboardData(LoadCommandCallback<DashboardData> var1);

    public void getUserData(LoadCommandCallback<User> var1);

    public void getUserForUuid(CishaUUID var1, LoadCommandCallback<User> var2);

    public void invalidateUserData();

    public void setEmail(String var1, String var2, LoadCommandCallback<Map<String, String>> var3);

    public void setPassword(String var1, String var2, String var3, LoadCommandCallback<Map<String, String>> var4);

    public void setProfileImage(Bitmap var1, LoadCommandCallback<SetProfileImageResponse> var2);

    public void setUserData(Map<String, String> var1, LoadCommandCallback<Map<String, String>> var2);

    public static interface IUserDataChangedListener {
        public void userDataChanged(User var1);
    }

    public static class SetProfileImageResponse {
        private CishaUUID _couchId;
        private String _revision;
        private URL _url;

        public SetProfileImageResponse(CishaUUID cishaUUID, URL uRL, String string) {
            this._couchId = cishaUUID;
            this._url = uRL;
            this._revision = string;
        }

        public CishaUUID getCouchId() {
            return this._couchId;
        }

        public String getRevision() {
            return this._revision;
        }

        public URL getUrl() {
            return this._url;
        }
    }

}
