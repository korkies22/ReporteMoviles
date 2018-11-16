// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.model;

import com.j256.ormlite.table.TableUtils;
import com.j256.ormlite.support.ConnectionSource;
import android.database.sqlite.SQLiteDatabase;
import java.sql.SQLException;
import de.cisha.chess.util.Logger;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.content.Context;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;

public class PlayzoneServiceDBHelper extends OrmLiteSqliteOpenHelper
{
    private static final String DATABASE_NAME = "PlayzoneDB";
    private static final int DATABASE_VERSION = 1;
    private Dao<OnlineEngineOpponent, String> _onlineEngineOpponentDao;
    
    public PlayzoneServiceDBHelper(final Context context, final String s) {
        final StringBuilder sb = new StringBuilder();
        sb.append("PlayzoneDB_");
        sb.append(s);
        super(context, sb.toString(), null, 1);
        this._onlineEngineOpponentDao = null;
    }
    
    public Dao<OnlineEngineOpponent, String> getOnlineEngineOpponentDao() {
        if (this._onlineEngineOpponentDao == null) {
            try {
                this._onlineEngineOpponentDao = this.getDao(OnlineEngineOpponent.class);
            }
            catch (SQLException ex) {
                Logger.getInstance().debug(PlayzoneServiceDBHelper.class.getName(), SQLException.class.getName(), ex);
            }
        }
        return this._onlineEngineOpponentDao;
    }
    
    @Override
    public void onCreate(final SQLiteDatabase sqLiteDatabase, final ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, OnlineEngineOpponent.class);
        }
        catch (SQLException ex) {
            Logger.getInstance().error(PlayzoneServiceDBHelper.class.getName(), SQLException.class.getName(), ex);
        }
    }
    
    @Override
    public void onUpgrade(final SQLiteDatabase sqLiteDatabase, final ConnectionSource connectionSource, final int n, final int n2) {
    }
}
