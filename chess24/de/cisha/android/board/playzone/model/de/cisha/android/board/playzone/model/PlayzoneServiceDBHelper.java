/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 */
package de.cisha.android.board.playzone.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import de.cisha.android.board.playzone.model.OnlineEngineOpponent;
import de.cisha.chess.util.Logger;
import java.sql.SQLException;

public class PlayzoneServiceDBHelper
extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "PlayzoneDB";
    private static final int DATABASE_VERSION = 1;
    private Dao<OnlineEngineOpponent, String> _onlineEngineOpponentDao;

    public PlayzoneServiceDBHelper(Context context, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PlayzoneDB_");
        stringBuilder.append(string);
        super(context, stringBuilder.toString(), null, 1);
        this._onlineEngineOpponentDao = null;
    }

    public Dao<OnlineEngineOpponent, String> getOnlineEngineOpponentDao() {
        if (this._onlineEngineOpponentDao == null) {
            try {
                this._onlineEngineOpponentDao = this.getDao(OnlineEngineOpponent.class);
            }
            catch (SQLException sQLException) {
                Logger.getInstance().debug(PlayzoneServiceDBHelper.class.getName(), SQLException.class.getName(), sQLException);
            }
        }
        return this._onlineEngineOpponentDao;
    }

    @Override
    public void onCreate(SQLiteDatabase sQLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, OnlineEngineOpponent.class);
            return;
        }
        catch (SQLException sQLException) {
            Logger.getInstance().error(PlayzoneServiceDBHelper.class.getName(), SQLException.class.getName(), sQLException);
            return;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, ConnectionSource connectionSource, int n, int n2) {
    }
}
