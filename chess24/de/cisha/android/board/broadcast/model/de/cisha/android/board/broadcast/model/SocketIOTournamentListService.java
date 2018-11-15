/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.broadcast.model;

import android.content.Context;
import android.content.res.Resources;
import de.cisha.android.board.broadcast.model.IBroadcastService;
import de.cisha.android.board.broadcast.model.ITournamentListService;
import de.cisha.android.board.broadcast.model.TournamentInfo;
import de.cisha.android.board.broadcast.model.jsonparsers.JSONTournamentInfoParser;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.NodeServerAddress;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.JSONNodeServerAdressRequestParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.util.Logger;
import io.socket.ConnectionListener;
import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SocketIOTournamentListService
implements ITournamentListService,
IBroadcastService {
    public static final String COLLECTION_NAME = "webList";
    private static SocketIOTournamentListService _instance;
    private String _language;

    private SocketIOTournamentListService(Context context) {
        this._language = context.getResources().getString(2131689583);
    }

    public static SocketIOTournamentListService getInstance(Context object) {
        synchronized (SocketIOTournamentListService.class) {
            if (_instance == null) {
                _instance = new SocketIOTournamentListService((Context)object);
            }
            object = _instance;
            return object;
        }
    }

    private void getTournaments(final LoadCommandCallback<List<TournamentInfo>> loadCommandCallback, int n, int n2, final SocketIO socketIO, final long l) {
        socketIO.emit("getTournaments", new IOAcknowledge(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public /* varargs */ void ack(Object ... jSONObject) {
                block10 : {
                    int n;
                    Object object;
                    Serializable serializable;
                    JSONTournamentInfoParser jSONTournamentInfoParser;
                    block9 : {
                        Logger.getInstance().debug("BroadcastSocketIO:", "getTournaments answered:");
                        int n2 = 0;
                        for (n = 0; n < ((JSONObject)jSONObject).length; ++n) {
                            object = Logger.getInstance();
                            serializable = new StringBuilder();
                            serializable.append("args[");
                            serializable.append(n);
                            serializable.append("]: ");
                            serializable.append((Object)jSONObject[n]);
                            object.debug("BroadcastSocketIO:", serializable.toString());
                        }
                        if (((JSONObject)jSONObject).length > 0 && jSONObject[0] instanceof JSONObject) {
                            jSONObject = jSONObject[0];
                            try {
                                jSONObject = jSONObject.getJSONArray("tournaments");
                                object = new HashSet(jSONObject.length());
                                serializable = new LinkedList();
                                jSONTournamentInfoParser = new JSONTournamentInfoParser(l, SocketIOTournamentListService.this._language);
                                n = n2;
                                break block9;
                            }
                            catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                                Logger.getInstance().debug(SocketIOTournamentListService.class.getName(), JSONException.class.getName(), invalidJsonForObjectException);
                                loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON, "received incorrec server json for getTournaments", null, null);
                            }
                            catch (JSONException jSONException) {
                                loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON, "received incorrec server json for getTournaments", null, null);
                                Logger.getInstance().debug(SocketIOTournamentListService.class.getName(), InvalidJsonForObjectException.class.getName(), (Throwable)jSONException);
                            }
                        }
                        loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON, "received incorrec server json for getTournaments", null, null);
                        break block10;
                    }
                    do {
                        if (n < jSONObject.length()) {
                            TournamentInfo tournamentInfo = jSONTournamentInfoParser.parseResult(jSONObject.getJSONObject(n));
                            if (object.add(tournamentInfo)) {
                                serializable.add(tournamentInfo);
                            }
                        } else {
                            loadCommandCallback.loadingSucceded(serializable);
                            break;
                        }
                        ++n;
                    } while (true);
                }
                socketIO.disconnect();
            }
        }, COLLECTION_NAME, n, n2);
    }

    @Override
    public void getBroadcastServerAdress(LoadCommandCallback<NodeServerAddress> loadCommandCallback) {
        new GeneralJSONAPICommandLoader<NodeServerAddress>().loadApiCommandGet(loadCommandCallback, "mobileAPI/GetBroadcastServer", null, new JSONNodeServerAdressRequestParser(), false);
    }

    @Override
    public void getTournaments(final int n, final int n2, final LoadCommandCallback<List<TournamentInfo>> loadCommandCallback) {
        this.getBroadcastServerAdress((LoadCommandCallback<NodeServerAddress>)new LoadCommandCallbackWithTimeout<NodeServerAddress>(20000){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                loadCommandCallback.loadingFailed(aPIStatusCode, string, null, null);
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            protected void succeded(NodeServerAddress object) {
                SocketIO socketIO;
                IOAcknowledge iOAcknowledge;
                try {
                    socketIO = new SocketIO(object.getURLString());
                    socketIO.connect(new IOCallback(){

                        @Override
                        public /* varargs */ void on(String object, IOAcknowledge object2, Object ... arrobject) {
                            object2 = Logger.getInstance();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("on:");
                            stringBuilder.append((String)object);
                            object2.debug("BroadcastSocketIO:", stringBuilder.toString());
                            for (int i = 0; i < arrobject.length; ++i) {
                                object = Logger.getInstance();
                                object2 = new StringBuilder();
                                object2.append("args[");
                                object2.append(i);
                                object2.append("]: ");
                                object2.append(arrobject[i]);
                                object.debug("BroadcastSocketIO:", object2.toString());
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
                        public void onError(SocketIOException socketIOException) {
                            Logger.getInstance().debug("BroadcastSocketIO:", "onError:", socketIOException);
                        }

                        @Override
                        public void onMessage(String string, IOAcknowledge object) {
                            object = Logger.getInstance();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("onMessage:");
                            stringBuilder.append(string);
                            object.debug("BroadcastSocketIO:", stringBuilder.toString());
                        }

                        @Override
                        public void onMessage(JSONObject jSONObject, IOAcknowledge object) {
                            object = Logger.getInstance();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("onMessage:");
                            stringBuilder.append((Object)jSONObject);
                            object.debug("BroadcastSocketIO:", stringBuilder.toString());
                        }
                    }, new ConnectionListener(){

                        @Override
                        public void onConnectionStateChanged(boolean bl) {
                            Logger logger = Logger.getInstance();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("onConnectionstateChanged:");
                            stringBuilder.append(bl);
                            logger.debug("BroadcastSocketIO:", stringBuilder.toString());
                        }
                    });
                    object = ServiceProvider.getInstance().getLoginService().getAuthToken();
                    iOAcknowledge = new IOAcknowledge(){

                        @Override
                        public /* varargs */ void ack(Object ... arrobject) {
                            Logger.getInstance().debug("BroadcastSocketIO:", "connect answered:");
                            for (int i = 0; i < arrobject.length; ++i) {
                                Logger logger = Logger.getInstance();
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("args[");
                                stringBuilder.append(i);
                                stringBuilder.append("]: ");
                                stringBuilder.append(arrobject[i]);
                                logger.debug("BroadcastSocketIO:", stringBuilder.toString());
                            }
                            long l = arrobject.length > 0 && arrobject[0] instanceof Long ? (Long)arrobject[0] - System.currentTimeMillis() : 0L;
                            SocketIOTournamentListService.this.getTournaments(loadCommandCallback, n, n2, socketIO, l);
                        }
                    };
                    object = object != null ? object.getUuid() : "dummyToken";
                }
                catch (MalformedURLException malformedURLException) {
                    Logger.getInstance().debug(SocketIOTournamentListService.class.getName(), MalformedURLException.class.getName(), malformedURLException);
                    return;
                }
                socketIO.emit("connect", iOAcknowledge, object);
            }

        });
    }

}
