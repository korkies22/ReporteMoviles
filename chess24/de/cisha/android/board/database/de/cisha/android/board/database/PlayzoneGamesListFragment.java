/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.text.format.DateUtils
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package de.cisha.android.board.database;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.board.database.AbstractDatabaseListFragment;
import de.cisha.android.board.database.view.DatabaseListItemView;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.service.IGameService;
import de.cisha.android.board.service.IMembershipService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.ui.patterns.dialogs.RookieMoreDialogFragment;
import de.cisha.chess.model.GameInfo;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.GameType;
import de.cisha.chess.model.Opponent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class PlayzoneGamesListFragment
extends AbstractDatabaseListFragment<DatabaseListItemView> {
    private DateFormat _dateFormat = SimpleDateFormat.getDateInstance(2, Locale.getDefault());
    private LayoutInflater _inflater;

    @Override
    public DatabaseListItemView createListItemView() {
        DatabaseListItemView databaseListItemView = (DatabaseListItemView)this._inflater.inflate(2131427412, null);
        databaseListItemView.setIconResourceId(2131231484);
        return databaseListItemView;
    }

    @Override
    public List<GameInfo> getGameInfoList() {
        return ServiceProvider.getInstance().getGameService().getPlayzoneGameInfos();
    }

    @Override
    public String getHeaderText(Resources resources) {
        return resources.getString(2131690084);
    }

    @Override
    public MenuItem getHighlightedMenuItem() {
        return MenuItem.MY_PLAY_GAMES;
    }

    @Override
    protected List<RookieMoreDialogFragment.ListOption> getListOptionsForGameInfo(GameInfo gameInfo) {
        LinkedList<RookieMoreDialogFragment.ListOption> linkedList = new LinkedList<RookieMoreDialogFragment.ListOption>();
        linkedList.add(new AbstractDatabaseListFragment.CopyGameListOption(this, gameInfo, this.getActivity().getString(2131690088)));
        return linkedList;
    }

    @Override
    protected int getMaximumNumbersOfItemsToShow() {
        return ServiceProvider.getInstance().getMembershipService().getNumberOfSavedGamesAccessible();
    }

    @Override
    public String getTrackingName() {
        return "PlayzoneList";
    }

    @Override
    protected View inflateIntroView(ViewGroup viewGroup) {
        return this._inflater.inflate(2131427416, viewGroup);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this._inflater = LayoutInflater.from((Context)this.getActivity());
    }

    @Override
    public void updateListItem(DatabaseListItemView databaseListItemView, GameInfo gameInfo, boolean bl) {
        long l;
        databaseListItemView.setPlayerLeftName(gameInfo.getWhitePlayer().getName());
        databaseListItemView.setPlayerRightName(gameInfo.getBlackplayer().getName());
        databaseListItemView.setResultText(gameInfo.getResultStatus().getResult().getShortDescription());
        Object object = gameInfo.getDate();
        object = DateUtils.isToday((long)object.getTime()) ? ((l = (System.currentTimeMillis() - object.getTime()) / 60000L) > 60L ? this.getActivity().getString(2131690085, new Object[]{l /= 60L}) : this.getActivity().getString(2131690086, new Object[]{l})) : this._dateFormat.format(gameInfo.getDate());
        int n = 2131231484;
        if (gameInfo.getType() == GameType.GAME_VS_ENGINE) {
            n = 2131231486;
        }
        databaseListItemView.setIconResourceId(n);
        databaseListItemView.setDateTimeText((String)object);
        databaseListItemView.setEnabled(bl ^ true);
    }
}
