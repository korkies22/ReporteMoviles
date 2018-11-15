/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 */
package de.cisha.android.board.database;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.analyze.AnalyzeFragment;
import de.cisha.android.board.database.AbstractDatabaseListFragment;
import de.cisha.android.board.database.view.DatabaseListItemView;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.service.IGameService;
import de.cisha.android.board.service.IMembershipService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.ui.patterns.dialogs.RookieMoreDialogFragment;
import de.cisha.chess.model.GameID;
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

public class AnalyzesListFragment
extends AbstractDatabaseListFragment<DatabaseListItemView> {
    private DateFormat _dateFormat = SimpleDateFormat.getDateInstance(2, Locale.getDefault());
    private LayoutInflater _inflater;

    private int getIconResIdForType(GameType gameType) {
        int n;
        int n2 = n = 2131231440;
        if (gameType != null) {
            n2 = n;
            switch (.$SwitchMap$de$cisha$chess$model$GameType[gameType.ordinal()]) {
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
                    n2 = 2131231486;
                }
                case 6: 
                case 7: 
            }
        }
        return n2;
    }

    @Override
    public DatabaseListItemView createListItemView() {
        DatabaseListItemView databaseListItemView = (DatabaseListItemView)this._inflater.inflate(2131427412, null);
        databaseListItemView.setIconResourceId(2131231440);
        return databaseListItemView;
    }

    @Override
    public List<GameInfo> getGameInfoList() {
        return ServiceProvider.getInstance().getGameService().getAnalyzedGameInfos();
    }

    @Override
    public String getHeaderText(Resources resources) {
        return resources.getString(2131690079);
    }

    @Override
    public MenuItem getHighlightedMenuItem() {
        return MenuItem.MY_ANALYZE_GAMES;
    }

    @Override
    protected List<RookieMoreDialogFragment.ListOption> getListOptionsForGameInfo(GameInfo gameInfo) {
        LinkedList<RookieMoreDialogFragment.ListOption> linkedList = new LinkedList<RookieMoreDialogFragment.ListOption>();
        linkedList.add(new AbstractDatabaseListFragment.EditListOption(this, (Context)this.getActivity(), gameInfo, this.getContentPresenter()));
        linkedList.add(new AbstractDatabaseListFragment.CopyGameListOption(this, gameInfo, this.getActivity().getString(2131690081)));
        linkedList.add(new AbstractDatabaseListFragment.DeleteListOption((Context)this.getActivity(), gameInfo));
        return linkedList;
    }

    @Override
    protected int getMaximumNumbersOfItemsToShow() {
        return ServiceProvider.getInstance().getMembershipService().getNumberOfSavedAnalysisAccessible();
    }

    @Override
    public List<View> getRightButtons(Context context) {
        LinkedList<View> linkedList = new LinkedList<View>();
        context = new ImageView(context);
        context.setImageResource(2131231529);
        context.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                AnalyzesListFragment.this.getContentPresenter().showFragment(new AnalyzeFragment(), true, true);
            }
        });
        linkedList.add((View)context);
        return linkedList;
    }

    @Override
    public String getTrackingName() {
        return "AnalysisList";
    }

    @Override
    protected View inflateIntroView(ViewGroup viewGroup) {
        return LayoutInflater.from((Context)this.getActivity()).inflate(2131427410, viewGroup);
    }

    @Override
    public boolean isGrabMenuEnabled() {
        return true;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this._inflater = LayoutInflater.from((Context)this.getActivity());
    }

    @Override
    public void updateListItem(DatabaseListItemView databaseListItemView, GameInfo gameInfo, boolean bl) {
        CharSequence charSequence;
        block3 : {
            block2 : {
                databaseListItemView.setPlayerLeftName(gameInfo.getWhitePlayer().getName());
                databaseListItemView.setPlayerRightName(gameInfo.getBlackplayer().getName());
                databaseListItemView.setResultText(gameInfo.getResultStatus().getResult().getShortDescription());
                String string = gameInfo.getTitle();
                if (string == null) break block2;
                charSequence = string;
                if (!"".equals(string.trim())) break block3;
            }
            charSequence = new StringBuilder();
            charSequence.append(this.getActivity().getString(2131690080));
            charSequence.append(gameInfo.getGameID().getUuid());
            charSequence = charSequence.toString();
        }
        databaseListItemView.setTitle((String)charSequence);
        databaseListItemView.showTitle(gameInfo.hasPlayerName() ^ true);
        charSequence = new StringBuilder();
        charSequence.append(this.getActivity().getString(2131690077));
        charSequence.append(this._dateFormat.format(gameInfo.getDate()));
        databaseListItemView.setDateTimeText(charSequence.toString());
        databaseListItemView.setIconResourceId(this.getIconResIdForType(gameInfo.getOriginalGameType()));
        databaseListItemView.setEnabled(bl ^ true);
    }

}
