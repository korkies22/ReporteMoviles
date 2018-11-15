/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote.model;

import de.cisha.android.board.playzone.model.IGameModelDelegate;
import de.cisha.android.board.playzone.remote.model.EloRange;
import de.cisha.android.board.playzone.remote.model.IRemoteGameConnection;
import de.cisha.android.board.playzone.remote.model.RemoteGameAdapter;
import de.cisha.android.board.playzone.remote.model.SocketIORemoteGameConnection;
import de.cisha.android.board.service.NodeServerAddress;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.model.ClockSetting;

public static class RemoteGameAdapter.Builder {
    private CishaUUID _authToken;
    private ClockSetting _clockSetting;
    private IRemoteGameConnection _connection;
    private IGameModelDelegate _delegate;
    private EloRange _eloRange;
    private String _engineUuid;
    private String _gameSessionToken;
    private NodeServerAddress _pairing;
    private boolean _playerIsWhite;
    private NodeServerAddress _playing;
    private boolean _ratedGame;
    private boolean _reconnect = false;
    private boolean _rematch = false;

    private RemoteGameAdapter.Builder(CishaUUID cishaUUID, IGameModelDelegate iGameModelDelegate, NodeServerAddress nodeServerAddress) {
        this._authToken = cishaUUID;
        this._delegate = iGameModelDelegate;
        this._pairing = nodeServerAddress;
    }

    public RemoteGameAdapter.Builder(CishaUUID cishaUUID, String string, IGameModelDelegate iGameModelDelegate, NodeServerAddress nodeServerAddress) {
        this(cishaUUID, iGameModelDelegate, nodeServerAddress);
        this._gameSessionToken = string;
        this._rematch = true;
        this._clockSetting = new ClockSetting(0, 0);
    }

    public RemoteGameAdapter.Builder(CishaUUID cishaUUID, String string, boolean bl, IGameModelDelegate iGameModelDelegate, NodeServerAddress nodeServerAddress, NodeServerAddress nodeServerAddress2) {
        this(cishaUUID, iGameModelDelegate, nodeServerAddress2);
        this._gameSessionToken = string;
        this._playerIsWhite = bl;
        this._playing = nodeServerAddress;
        this._reconnect = true;
    }

    public RemoteGameAdapter.Builder(ClockSetting clockSetting, CishaUUID cishaUUID, IGameModelDelegate iGameModelDelegate, NodeServerAddress nodeServerAddress) {
        this(cishaUUID, iGameModelDelegate, nodeServerAddress);
        this._delegate = iGameModelDelegate;
        this._clockSetting = clockSetting;
    }

    public RemoteGameAdapter.Builder(String string, ClockSetting clockSetting, boolean bl, CishaUUID cishaUUID, IGameModelDelegate iGameModelDelegate, NodeServerAddress nodeServerAddress) {
        this(clockSetting, cishaUUID, iGameModelDelegate, nodeServerAddress);
        this._engineUuid = string;
        this._ratedGame = bl;
    }

    static /* synthetic */ IGameModelDelegate access$100(RemoteGameAdapter.Builder builder) {
        return builder._delegate;
    }

    static /* synthetic */ String access$1000(RemoteGameAdapter.Builder builder) {
        return builder._engineUuid;
    }

    static /* synthetic */ boolean access$1100(RemoteGameAdapter.Builder builder) {
        return builder._ratedGame;
    }

    static /* synthetic */ EloRange access$1200(RemoteGameAdapter.Builder builder) {
        return builder._eloRange;
    }

    static /* synthetic */ ClockSetting access$1300(RemoteGameAdapter.Builder builder) {
        return builder._clockSetting;
    }

    static /* synthetic */ boolean access$200(RemoteGameAdapter.Builder builder) {
        return builder._playerIsWhite;
    }

    static /* synthetic */ NodeServerAddress access$300(RemoteGameAdapter.Builder builder) {
        return builder._pairing;
    }

    static /* synthetic */ NodeServerAddress access$400(RemoteGameAdapter.Builder builder) {
        return builder._playing;
    }

    static /* synthetic */ CishaUUID access$500(RemoteGameAdapter.Builder builder) {
        return builder._authToken;
    }

    static /* synthetic */ IRemoteGameConnection access$600(RemoteGameAdapter.Builder builder) {
        return builder._connection;
    }

    static /* synthetic */ boolean access$700(RemoteGameAdapter.Builder builder) {
        return builder._reconnect;
    }

    static /* synthetic */ boolean access$800(RemoteGameAdapter.Builder builder) {
        return builder._rematch;
    }

    static /* synthetic */ String access$900(RemoteGameAdapter.Builder builder) {
        return builder._gameSessionToken;
    }

    public RemoteGameAdapter build() {
        if (this._connection == null) {
            this._connection = new SocketIORemoteGameConnection(this._playing, this._pairing, this._authToken);
        }
        return new RemoteGameAdapter(this, null);
    }

    public RemoteGameAdapter.Builder connection(IRemoteGameConnection iRemoteGameConnection) {
        this._connection = iRemoteGameConnection;
        return this;
    }

    public RemoteGameAdapter.Builder setEloRange(EloRange eloRange) {
        this._eloRange = eloRange;
        return this;
    }
}
