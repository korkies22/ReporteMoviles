// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

import de.cisha.chess.model.CishaUUID;
import java.net.MalformedURLException;
import de.cisha.android.board.service.ServiceProvider;
import io.socket.ConnectionListener;
import io.socket.SocketIOException;
import io.socket.IOCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import java.util.Map;
import de.cisha.android.board.service.jsonparser.JSONNodeServerAdressRequestParser;
import de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader;
import de.cisha.android.board.service.NodeServerAddress;
import org.json.JSONArray;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import org.json.JSONException;
import de.cisha.android.board.broadcast.model.jsonparsers.JSONTournamentInfoParser;
import java.util.LinkedList;
import java.util.HashSet;
import org.json.JSONObject;
import de.cisha.chess.util.Logger;
import io.socket.IOAcknowledge;
import java.util.List;
import io.socket.SocketIO;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import android.content.Context;

public class SocketIOTournamentListService implements ITournamentListService, IBroadcastService
{
    public static final String COLLECTION_NAME = "webList";
    private static SocketIOTournamentListService _instance;
    private String _language;
    
    private SocketIOTournamentListService(final Context context) {
        this._language = context.getResources().getString(2131689583);
    }
    
    public static SocketIOTournamentListService getInstance(final Context context) {
        synchronized (SocketIOTournamentListService.class) {
            if (SocketIOTournamentListService._instance == null) {
                SocketIOTournamentListService._instance = new SocketIOTournamentListService(context);
            }
            return SocketIOTournamentListService._instance;
        }
    }
    
    private void getTournaments(final LoadCommandCallback<List<TournamentInfo>> loadCommandCallback, final int n, final int n2, final SocketIO socketIO, final long n3) {
        socketIO.emit("getTournaments", new IOAcknowledge() {
            @Override
            public void ack(final Object... array) {
                Logger.getInstance().debug("BroadcastSocketIO:", "getTournaments answered:");
                final int n = 0;
                for (int i = 0; i < array.length; ++i) {
                    final Logger instance = Logger.getInstance();
                    final StringBuilder sb = new StringBuilder();
                    sb.append("args[");
                    sb.append(i);
                    sb.append("]: ");
                    sb.append(array[i]);
                    instance.debug("BroadcastSocketIO:", sb.toString());
                }
                if (array.length > 0 && array[0] instanceof JSONObject) {
                    while (true) {
                        final JSONObject jsonObject = (JSONObject)array[0];
                        while (true) {
                            int n2 = 0;
                            Label_0312: {
                                try {
                                    final JSONArray jsonArray = jsonObject.getJSONArray("tournaments");
                                    final HashSet set = new HashSet<TournamentInfo>(jsonArray.length());
                                    final LinkedList<TournamentInfo> list = new LinkedList<TournamentInfo>();
                                    final JSONTournamentInfoParser jsonTournamentInfoParser = new JSONTournamentInfoParser(n3, SocketIOTournamentListService.this._language);
                                    n2 = n;
                                    if (n2 < jsonArray.length()) {
                                        final TournamentInfo result = jsonTournamentInfoParser.parseResult(jsonArray.getJSONObject(n2));
                                        if (set.add(result)) {
                                            list.add(result);
                                        }
                                        break Label_0312;
                                    }
                                    else {
                                        loadCommandCallback.loadingSucceded(list);
                                    }
                                }
                                catch (InvalidJsonForObjectException ex) {
                                    Logger.getInstance().debug(SocketIOTournamentListService.class.getName(), JSONException.class.getName(), ex);
                                    loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON, "received incorrec server json for getTournaments", null, null);
                                }
                                catch (JSONException ex2) {
                                    loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON, "received incorrec server json for getTournaments", null, null);
                                    Logger.getInstance().debug(SocketIOTournamentListService.class.getName(), InvalidJsonForObjectException.class.getName(), (Throwable)ex2);
                                }
                                break;
                            }
                            ++n2;
                            continue;
                        }
                    }
                }
                else {
                    loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON, "received incorrec server json for getTournaments", null, null);
                }
                socketIO.disconnect();
            }
        }, "webList", n, n2);
    }
    
    @Override
    public void getBroadcastServerAdress(final LoadCommandCallback<NodeServerAddress> loadCommandCallback) {
        new GeneralJSONAPICommandLoader<NodeServerAddress>().loadApiCommandGet(loadCommandCallback, "mobileAPI/GetBroadcastServer", null, new JSONNodeServerAdressRequestParser(), false);
    }
    
    @Override
    public void getTournaments(final int n, final int n2, final LoadCommandCallback<List<TournamentInfo>> loadCommandCallback) {
        this.getBroadcastServerAdress(new LoadCommandCallbackWithTimeout<NodeServerAddress>(20000) {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                loadCommandCallback.loadingFailed(apiStatusCode, s, null, null);
            }
            
            @Override
            protected void succeded(final NodeServerAddress nodeServerAddress) {
                while (true) {
                    while (true) {
                        try {
                            final SocketIO socketIO = new SocketIO(nodeServerAddress.getURLString());
                            socketIO.connect(new IOCallback() {
                                @Override
                                public void on(final String s, final IOAcknowledge ioAcknowledge, final Object... array) {
                                    final Logger instance = Logger.getInstance();
                                    final StringBuilder sb = new StringBuilder();
                                    sb.append("on:");
                                    sb.append(s);
                                    instance.debug("BroadcastSocketIO:", sb.toString());
                                    for (int i = 0; i < array.length; ++i) {
                                        final Logger instance2 = Logger.getInstance();
                                        final StringBuilder sb2 = new StringBuilder();
                                        sb2.append("args[");
                                        sb2.append(i);
                                        sb2.append("]: ");
                                        sb2.append(array[i]);
                                        instance2.debug("BroadcastSocketIO:", sb2.toString());
                                    }
                                }
                                
                                @Override
                                public void onConnect() {
                                    Logger.getInstance().debug("BroadcastSocketIO:", "onConnect:");
                                }
                                
                                @Override
                                public void onDisconnect() {
                                    Logger.getInstance().debug("BroadcastSocketIO:", "onDisconnect:");
                                    loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_UNKNOWN, "connection disconnected", null, null);
                                }
                                
                                @Override
                                public void onError(final SocketIOException ex) {
                                    Logger.getInstance().debug("BroadcastSocketIO:", "onError:", ex);
                                }
                                
                                @Override
                                public void onMessage(final String s, final IOAcknowledge ioAcknowledge) {
                                    final Logger instance = Logger.getInstance();
                                    final StringBuilder sb = new StringBuilder();
                                    sb.append("onMessage:");
                                    sb.append(s);
                                    instance.debug("BroadcastSocketIO:", sb.toString());
                                }
                                
                                @Override
                                public void onMessage(final JSONObject jsonObject, final IOAcknowledge ioAcknowledge) {
                                    final Logger instance = Logger.getInstance();
                                    final StringBuilder sb = new StringBuilder();
                                    sb.append("onMessage:");
                                    sb.append(jsonObject);
                                    instance.debug("BroadcastSocketIO:", sb.toString());
                                }
                            }, new ConnectionListener() {
                                @Override
                                public void onConnectionStateChanged(final boolean b) {
                                    final Logger instance = Logger.getInstance();
                                    final StringBuilder sb = new StringBuilder();
                                    sb.append("onConnectionstateChanged:");
                                    sb.append(b);
                                    instance.debug("BroadcastSocketIO:", sb.toString());
                                }
                            });
                            final CishaUUID authToken = ServiceProvider.getInstance().getLoginService().getAuthToken();
                            final IOAcknowledge ioAcknowledge = new IOAcknowledge() {
                                @Override
                                public void ack(final Object... array) {
                                    Logger.getInstance().debug("BroadcastSocketIO:", "connect answered:");
                                    for (int i = 0; i < array.length; ++i) {
                                        final Logger instance = Logger.getInstance();
                                        final StringBuilder sb = new StringBuilder();
                                        sb.append("args[");
                                        sb.append(i);
                                        sb.append("]: ");
                                        sb.append(array[i]);
                                        instance.debug("BroadcastSocketIO:", sb.toString());
                                    }
                                    long n;
                                    if (array.length > 0 && array[0] instanceof Long) {
                                        n = (long)array[0] - System.currentTimeMillis();
                                    }
                                    else {
                                        n = 0L;
                                    }
                                    SocketIOTournamentListService.this.getTournaments(loadCommandCallback, n, n2, socketIO, n);
                                }
                            };
                            if (authToken != null) {
                                final String uuid = authToken.getUuid();
                                socketIO.emit("connect", ioAcknowledge, uuid);
                                return;
                            }
                        }
                        catch (MalformedURLException ex) {
                            Logger.getInstance().debug(SocketIOTournamentListService.class.getName(), MalformedURLException.class.getName(), ex);
                            return;
                        }
                        final String uuid = "dummyToken";
                        continue;
                    }
                }
            }
        });
    }
}
