/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.UserLoginData;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.user.User;
import de.cisha.chess.model.CishaUUID;

public interface ILoginService {
    public void addLoginListener(LoginObserver var1);

    public void authenticateAsGuest(LoadCommandCallback<UserLoginData> var1);

    public void authenticateByFacebook(String var1, LoadCommandCallback<UserLoginData> var2);

    public void authenticateByLogin(String var1, String var2, LoadCommandCallback<UserLoginData> var3);

    public CishaUUID getAuthToken();

    public void getUserByID(CishaUUID var1, LoadCommandCallback<User> var2);

    public String getUserPrefix();

    public CishaUUID getUserUUID();

    public boolean isLoggedIn();

    public void logOut(LogoutCallback var1);

    public void lostPassword(String var1, LoadCommandCallback<Boolean> var2);

    public void register(String var1, String var2, String var3, LoadCommandCallback<Boolean> var4);

    public void verifyAuthToken(LoadCommandCallback<UserLoginData> var1);

    public static interface LoginObserver {
        public void loginStateChanged(boolean var1);
    }

    public static interface LogoutCallback {
        public void logoutFailed(String var1);

        public void logoutSucceeded();
    }

}
