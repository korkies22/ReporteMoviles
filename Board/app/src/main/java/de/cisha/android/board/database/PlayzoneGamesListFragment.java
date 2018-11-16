// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.database;

import java.util.Date;
import de.cisha.chess.model.GameType;
import android.text.format.DateUtils;
import android.content.Context;
import android.os.Bundle;
import java.util.LinkedList;
import de.cisha.android.ui.patterns.dialogs.RookieMoreDialogFragment;
import de.cisha.android.board.mainmenu.view.MenuItem;
import android.content.res.Resources;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.chess.model.GameInfo;
import java.util.List;
import android.view.ViewGroup;
import android.view.View;
import java.util.Locale;
import android.view.LayoutInflater;
import java.text.DateFormat;
import de.cisha.android.board.database.view.DatabaseListItemView;

public class PlayzoneGamesListFragment extends AbstractDatabaseListFragment<DatabaseListItemView>
{
    private DateFormat _dateFormat;
    private LayoutInflater _inflater;
    
    public PlayzoneGamesListFragment() {
        this._dateFormat = DateFormat.getDateInstance(2, Locale.getDefault());
    }
    
    public DatabaseListItemView createListItemView() {
        final DatabaseListItemView databaseListItemView = (DatabaseListItemView)this._inflater.inflate(2131427412, (ViewGroup)null);
        databaseListItemView.setIconResourceId(2131231484);
        return databaseListItemView;
    }
    
    public List<GameInfo> getGameInfoList() {
        return ServiceProvider.getInstance().getGameService().getPlayzoneGameInfos();
    }
    
    @Override
    public String getHeaderText(final Resources resources) {
        return resources.getString(2131690084);
    }
    
    @Override
    public MenuItem getHighlightedMenuItem() {
        return MenuItem.MY_PLAY_GAMES;
    }
    
    @Override
    protected List<RookieMoreDialogFragment.ListOption> getListOptionsForGameInfo(final GameInfo gameInfo) {
        final LinkedList<CopyGameListOption> list = (LinkedList<CopyGameListOption>)new LinkedList<RookieMoreDialogFragment.ListOption>();
        list.add(new CopyGameListOption(this, gameInfo, this.getActivity().getString(2131690088)));
        return (List<RookieMoreDialogFragment.ListOption>)list;
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
    protected View inflateIntroView(final ViewGroup viewGroup) {
        return this._inflater.inflate(2131427416, viewGroup);
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this._inflater = LayoutInflater.from((Context)this.getActivity());
    }
    
    public void updateListItem(final DatabaseListItemView databaseListItemView, final GameInfo gameInfo, final boolean b) {
        databaseListItemView.setPlayerLeftName(gameInfo.getWhitePlayer().getName());
        databaseListItemView.setPlayerRightName(gameInfo.getBlackplayer().getName());
        databaseListItemView.setResultText(gameInfo.getResultStatus().getResult().getShortDescription());
        final Date date = gameInfo.getDate();
        String dateTimeText;
        if (DateUtils.isToday(date.getTime())) {
            final long n = (System.currentTimeMillis() - date.getTime()) / 60000L;
            if (n > 60L) {
                dateTimeText = this.getActivity().getString(2131690085, new Object[] { n / 60L });
            }
            else {
                dateTimeText = this.getActivity().getString(2131690086, new Object[] { n });
            }
        }
        else {
            dateTimeText = this._dateFormat.format(gameInfo.getDate());
        }
        int iconResourceId = 2131231484;
        if (gameInfo.getType() == GameType.GAME_VS_ENGINE) {
            iconResourceId = 2131231486;
        }
        databaseListItemView.setIconResourceId(iconResourceId);
        databaseListItemView.setDateTimeText(dateTimeText);
        databaseListItemView.setEnabled(b ^ true);
    }
}
