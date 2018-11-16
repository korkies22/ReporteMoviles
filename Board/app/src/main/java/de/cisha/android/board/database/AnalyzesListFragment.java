// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.database;

import android.os.Bundle;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.analyze.AnalyzeFragment;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.content.Context;
import java.util.LinkedList;
import de.cisha.android.ui.patterns.dialogs.RookieMoreDialogFragment;
import de.cisha.android.board.mainmenu.view.MenuItem;
import android.content.res.Resources;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.chess.model.GameInfo;
import java.util.List;
import android.view.ViewGroup;
import android.view.View;
import de.cisha.chess.model.GameType;
import de.cisha.android.board.IContentPresenter;
import java.util.Locale;
import android.view.LayoutInflater;
import java.text.DateFormat;
import de.cisha.android.board.database.view.DatabaseListItemView;

public class AnalyzesListFragment extends AbstractDatabaseListFragment<DatabaseListItemView>
{
    private DateFormat _dateFormat;
    private LayoutInflater _inflater;
    
    public AnalyzesListFragment() {
        this._dateFormat = DateFormat.getDateInstance(2, Locale.getDefault());
    }
    
    private int getIconResIdForType(final GameType gameType) {
        int n = 2131231440;
        if (gameType != null) {
            n = n;
            switch (AnalyzesListFragment.2..SwitchMap.de.cisha.chess.model.GameType[gameType.ordinal()]) {
                default: {
                    return 2131231440;
                }
                case 5: {
                    return 2131231448;
                }
                case 4: {
                    return 2131231498;
                }
                case 3: {
                    return 2131231483;
                }
                case 2: {
                    return 2131231484;
                }
                case 1: {
                    n = 2131231486;
                }
                case 6:
                case 7: {
                    break;
                }
            }
        }
        return n;
    }
    
    public DatabaseListItemView createListItemView() {
        final DatabaseListItemView databaseListItemView = (DatabaseListItemView)this._inflater.inflate(2131427412, (ViewGroup)null);
        databaseListItemView.setIconResourceId(2131231440);
        return databaseListItemView;
    }
    
    public List<GameInfo> getGameInfoList() {
        return ServiceProvider.getInstance().getGameService().getAnalyzedGameInfos();
    }
    
    @Override
    public String getHeaderText(final Resources resources) {
        return resources.getString(2131690079);
    }
    
    @Override
    public MenuItem getHighlightedMenuItem() {
        return MenuItem.MY_ANALYZE_GAMES;
    }
    
    @Override
    protected List<RookieMoreDialogFragment.ListOption> getListOptionsForGameInfo(final GameInfo gameInfo) {
        final LinkedList<CopyGameListOption> list = (LinkedList<CopyGameListOption>)new LinkedList<DeleteListOption>();
        list.add((DeleteListOption)new EditListOption(this, (Context)this.getActivity(), gameInfo, this.getContentPresenter()));
        list.add((DeleteListOption)new CopyGameListOption(this, gameInfo, this.getActivity().getString(2131690081)));
        list.add(new DeleteListOption((Context)this.getActivity(), gameInfo));
        return (List<RookieMoreDialogFragment.ListOption>)list;
    }
    
    @Override
    protected int getMaximumNumbersOfItemsToShow() {
        return ServiceProvider.getInstance().getMembershipService().getNumberOfSavedAnalysisAccessible();
    }
    
    @Override
    public List<View> getRightButtons(final Context context) {
        final LinkedList<ImageView> list = (LinkedList<ImageView>)new LinkedList<View>();
        final ImageView imageView = new ImageView(context);
        imageView.setImageResource(2131231529);
        imageView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                AnalyzesListFragment.this.getContentPresenter().showFragment(new AnalyzeFragment(), true, true);
            }
        });
        list.add((View)imageView);
        return (List<View>)list;
    }
    
    @Override
    public String getTrackingName() {
        return "AnalysisList";
    }
    
    @Override
    protected View inflateIntroView(final ViewGroup viewGroup) {
        return LayoutInflater.from((Context)this.getActivity()).inflate(2131427410, viewGroup);
    }
    
    @Override
    public boolean isGrabMenuEnabled() {
        return true;
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
        final String title = gameInfo.getTitle();
        String string = null;
        Label_0109: {
            if (title != null) {
                string = title;
                if (!"".equals(title.trim())) {
                    break Label_0109;
                }
            }
            final StringBuilder sb = new StringBuilder();
            sb.append(this.getActivity().getString(2131690080));
            sb.append(gameInfo.getGameID().getUuid());
            string = sb.toString();
        }
        databaseListItemView.setTitle(string);
        databaseListItemView.showTitle(gameInfo.hasPlayerName() ^ true);
        final StringBuilder sb2 = new StringBuilder();
        sb2.append(this.getActivity().getString(2131690077));
        sb2.append(this._dateFormat.format(gameInfo.getDate()));
        databaseListItemView.setDateTimeText(sb2.toString());
        databaseListItemView.setIconResourceId(this.getIconResIdForType(gameInfo.getOriginalGameType()));
        databaseListItemView.setEnabled(b ^ true);
    }
}
