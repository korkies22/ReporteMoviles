// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import java.net.URL;
import android.graphics.Bitmap;
import java.util.Map;
import de.cisha.chess.model.CishaUUID;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.user.User;
import de.cisha.android.board.profile.model.DashboardData;

public interface IProfileDataService
{
    void addUserDataChangedListener(final IUserDataChangedListener p0);
    
    DashboardData getCurrentDashboardData();
    
    User getCurrentUserData();
    
    void getDashboardData(final LoadCommandCallback<DashboardData> p0);
    
    void getUserData(final LoadCommandCallback<User> p0);
    
    void getUserForUuid(final CishaUUID p0, final LoadCommandCallback<User> p1);
    
    void invalidateUserData();
    
    void setEmail(final String p0, final String p1, final LoadCommandCallback<Map<String, String>> p2);
    
    void setPassword(final String p0, final String p1, final String p2, final LoadCommandCallback<Map<String, String>> p3);
    
    void setProfileImage(final Bitmap p0, final LoadCommandCallback<SetProfileImageResponse> p1);
    
    void setUserData(final Map<String, String> p0, final LoadCommandCallback<Map<String, String>> p1);
    
    public interface IUserDataChangedListener
    {
        void userDataChanged(final User p0);
    }
    
    public static class SetProfileImageResponse
    {
        private CishaUUID _couchId;
        private String _revision;
        private URL _url;
        
        public SetProfileImageResponse(final CishaUUID couchId, final URL url, final String revision) {
            this._couchId = couchId;
            this._url = url;
            this._revision = revision;
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
