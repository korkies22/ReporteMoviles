/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package de.cisha.android.board.service;

import android.content.Context;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import de.cisha.android.board.playzone.model.OnlineEngineOpponent;
import de.cisha.android.board.playzone.model.PlayzoneServiceDBHelper;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.chess.util.Logger;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

class PlayzoneService
implements Runnable {
    final /* synthetic */ List val$opponentList;

    PlayzoneService(List list) {
        this.val$opponentList = list;
    }

    @Override
    public void run() {
        Object object = ServiceProvider.getInstance().getLoginService().getUserPrefix();
        object = new PlayzoneServiceDBHelper(PlayzoneService.this._context, (String)object);
        Dao<OnlineEngineOpponent, String> dao = object.getOnlineEngineOpponentDao();
        try {
            dao.deleteBuilder().delete();
            Iterator iterator = this.val$opponentList.iterator();
            while (iterator.hasNext()) {
                dao.create((OnlineEngineOpponent)iterator.next());
            }
        }
        catch (SQLException sQLException) {
            Logger.getInstance().debug(de.cisha.android.board.service.PlayzoneService.class.getName(), SQLException.class.getName(), sQLException);
        }
        object.close();
    }
}
