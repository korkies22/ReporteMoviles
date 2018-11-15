/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board;

import de.cisha.android.board.Chess24Application;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.UserLoginData;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.user.User;
import de.cisha.chess.util.Logger;
import java.util.List;
import org.json.JSONObject;

class Chess24Application
extends LoadCommandCallbackWithTimeout<UserLoginData> {
    Chess24Application() {
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> object, JSONObject object2) {
        object = Logger.getInstance();
        object2 = new StringBuilder();
        object2.append("login in with old authToken failed:");
        object2.append(string);
        object.debug("Chess24App", object2.toString());
        if (aPIStatusCode == APIStatusCode.API_ERROR_INVALID_AUTHTOKEN) {
            ServiceProvider.getInstance().getLoginService().logOut(new ILoginService.LogoutCallback(){

                @Override
                public void logoutFailed(String string) {
                }

                @Override
                public void logoutSucceeded() {
                }
            });
        }
    }

    @Override
    protected void succeded(UserLoginData userLoginData) {
        Logger logger = Logger.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("logged in with old uuid:");
        stringBuilder.append(userLoginData);
        logger.debug("Chess24App", stringBuilder.toString());
        ServiceProvider.getInstance().getProfileDataService().getUserData(new LoadCommandCallback<User>(){

            @Override
            public void loadingCancelled() {
            }

            @Override
            public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
            }

            @Override
            public void loadingSucceded(User user) {
            }
        });
    }

}
