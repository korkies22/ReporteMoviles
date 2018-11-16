// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import de.cisha.android.board.user.User;
import de.cisha.chess.model.CishaUUID;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;

public interface ILoginService
{
    void addLoginListener(final LoginObserver p0);
    
    void authenticateAsGuest(final LoadCommandCallback<UserLoginData> p0);
    
    void authenticateByFacebook(final String p0, final LoadCommandCallback<UserLoginData> p1);
    
    void authenticateByLogin(final String p0, final String p1, final LoadCommandCallback<UserLoginData> p2);
    
    CishaUUID getAuthToken();
    
    void getUserByID(final CishaUUID p0, final LoadCommandCallback<User> p1);
    
    String getUserPrefix();
    
    CishaUUID getUserUUID();
    
    boolean isLoggedIn();
    
    void logOut(final LogoutCallback p0);
    
    void lostPassword(final String p0, final LoadCommandCallback<Boolean> p1);
    
    void register(final String p0, final String p1, final String p2, final LoadCommandCallback<Boolean> p3);
    
    void verifyAuthToken(final LoadCommandCallback<UserLoginData> p0);
    
    public interface LoginObserver
    {
        void loginStateChanged(final boolean p0);
    }
    
    public interface LogoutCallback
    {
        void logoutFailed(final String p0);
        
        void logoutSucceeded();
    }
}
