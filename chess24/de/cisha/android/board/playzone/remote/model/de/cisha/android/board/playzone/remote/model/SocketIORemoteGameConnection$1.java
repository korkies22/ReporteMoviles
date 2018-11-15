/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.playzone.remote.model;

import android.util.Log;
import de.cisha.android.board.playzone.remote.model.RemoteGameDelegator;
import de.cisha.android.board.service.NodeServerAddress;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONNodeServerAddressParser;
import de.cisha.chess.model.ClockSetting;
import de.cisha.chess.util.Logger;
import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;
import org.json.JSONException;
import org.json.JSONObject;

class SocketIORemoteGameConnection
implements IOCallback {
    SocketIORemoteGameConnection() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public /* varargs */ void on(String string, IOAcknowledge object, Object ... object2) {
        Object object3;
        for (int i = 0; i < ((Object)object2).length; ++i) {
            object = Logger.getInstance();
            object3 = new StringBuilder();
            object3.append("on ");
            object3.append(string);
            object3.append(i);
            object3.append(" ");
            object3.append(object2[i].toString());
            object.debug("de.cisha.test.socketIO", object3.toString());
        }
        if (string.equals("initGameSession")) {
            try {
                object = new JSONObject(object2[0].toString());
                string = object.getString(de.cisha.android.board.playzone.remote.model.SocketIORemoteGameConnection.GAME_SESSION_TOKEN);
                boolean bl = object.getString("role").equals("w");
                object.optString("mode", "human");
                object = object.getJSONObject("playingServer");
                object2 = new JSONNodeServerAddressParser();
                try {
                    SocketIORemoteGameConnection.this._playingServerAddress = object2.parseResult((JSONObject)object);
                }
                catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                    object3 = Logger.getInstance();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Cant parse playingServer adress:");
                    stringBuilder.append(object);
                    object3.debug("SocketIORemoteGameConnection", stringBuilder.toString(), invalidJsonForObjectException);
                }
                object = new ClockSetting(SocketIORemoteGameConnection.this._baseTimeInSeconds, SocketIORemoteGameConnection.this._increment);
                SocketIORemoteGameConnection.this._delegate.onPairingSucceeded(bl, (ClockSetting)object, string, SocketIORemoteGameConnection.this._playingServerAddress);
                SocketIORemoteGameConnection.this.initGameSession(string, bl, SocketIORemoteGameConnection.this._delegate);
            }
            catch (JSONException jSONException) {
                Log.d((String)de.cisha.android.board.playzone.remote.model.SocketIORemoteGameConnection.class.getName(), (String)JSONException.class.getName(), (Throwable)jSONException);
            }
        } else if (string.equals("noMatch")) {
            SocketIORemoteGameConnection.this._delegate.onPairingFailedNoOpponentFound();
        } else if (string.equals("informNotAllowed")) {
            SocketIORemoteGameConnection.this._delegate.onPairingFailedNotAllowed();
        } else {
            object = Logger.getInstance();
            object2 = new StringBuilder();
            object2.append("unkown command from  pairing server:");
            object2.append(string);
            object.error("SocketIORemoteGameConnection:", object2.toString());
            SocketIORemoteGameConnection.this._delegate.onPairingFailed();
        }
        SocketIORemoteGameConnection.this._socketPairing.disconnect();
    }

    @Override
    public void onConnect() {
        Logger.getInstance().debug("de.cisha.test.socketIO", "onConnect ");
    }

    @Override
    public void onDisconnect() {
        Logger.getInstance().debug("de.cisha.test.socketIO", "onDisconnect ");
    }

    @Override
    public void onError(SocketIOException socketIOException) {
        Logger.getInstance().debug("de.cisha.test.socketIO", "Pairing: onError ", socketIOException);
        SocketIORemoteGameConnection.this._delegate.onPairingFailed();
    }

    @Override
    public void onMessage(String string, IOAcknowledge object) {
        object = Logger.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onMessage(String) ");
        stringBuilder.append(string.toString());
        object.debug("de.cisha.test.socketIO", stringBuilder.toString());
    }

    @Override
    public void onMessage(JSONObject jSONObject, IOAcknowledge object) {
        object = Logger.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onMessage(json) ");
        stringBuilder.append(jSONObject.toString());
        object.debug("de.cisha.test.socketIO", stringBuilder.toString());
    }
}
