/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.AdapterView$OnItemLongClickListener
 *  android.widget.BaseAdapter
 *  android.widget.FrameLayout
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 */
package de.cisha.android.board.database;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.analyze.AnalyzeFragment;
import de.cisha.android.board.analyze.AnalyzeSavedToast;
import de.cisha.android.board.analyze.EditGameInformationFragment;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;
import de.cisha.android.board.modalfragments.ConversionContext;
import de.cisha.android.board.service.IGameService;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.ui.patterns.dialogs.AbstractDialogFragment;
import de.cisha.android.ui.patterns.dialogs.RookieMoreDialogFragment;
import de.cisha.chess.model.AbstractMoveContainer;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameID;
import de.cisha.chess.model.GameInfo;
import de.cisha.chess.model.GameType;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class AbstractDatabaseListFragment<ListItemViewType extends View>
extends AbstractContentFragment {
    private static final String MOREDIALOG = "moredialog";
    private List<GameInfo> _gameInfos;
    private View _introView;
    private AbstractDatabaseListFragment<ListItemViewType> _listAdapter;
    private int _numberOfItemsToShow;
    private AnalyzeSavedToast _toast;

    private void showConversionDialog() {
        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.ANALYSIS, "premium screen shown", "analyzed games");
        this.getContentPresenter().showConversionDialog(null, ConversionContext.SAVED_ANALYZES);
    }

    protected abstract ListItemViewType createListItemView();

    protected abstract List<GameInfo> getGameInfoList();

    protected abstract List<RookieMoreDialogFragment.ListOption> getListOptionsForGameInfo(GameInfo var1);

    protected abstract int getMaximumNumbersOfItemsToShow();

    @Override
    public Set<SettingsMenuCategory> getSettingsMenuCategories() {
        return null;
    }

    protected abstract View inflateIntroView(ViewGroup var1);

    @Override
    public boolean isGrabMenuEnabled() {
        return true;
    }

    protected void itemClicked(GameInfo gameInfo) {
        AnalyzeFragment analyzeFragment = new AnalyzeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("argGameId", (Serializable)gameInfo.getGameID());
        analyzeFragment.setArguments(bundle);
        this.getContentPresenter().showFragment(analyzeFragment, true, true);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this._gameInfos = new LinkedList<GameInfo>();
        this._listAdapter = new GameInfoListAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this._gameInfos = this.getGameInfoList();
        viewGroup = new FrameLayout((Context)this.getActivity());
        layoutInflater = (ListView)layoutInflater.inflate(2131427411, viewGroup, false);
        layoutInflater.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> object, View view, int n, long l) {
                if (n < AbstractDatabaseListFragment.this._numberOfItemsToShow) {
                    object = AbstractDatabaseListFragment.this._listAdapter.getItem(n);
                    AbstractDatabaseListFragment.this.itemClicked((GameInfo)object);
                    return;
                }
                AbstractDatabaseListFragment.this.showConversionDialog();
            }
        });
        layoutInflater.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            public boolean onItemLongClick(AdapterView<?> object, View object2, int n, long l) {
                if (n >= AbstractDatabaseListFragment.this._numberOfItemsToShow) {
                    AbstractDatabaseListFragment.this.showConversionDialog();
                    return true;
                }
                object = AbstractDatabaseListFragment.this._listAdapter.getItem(n);
                if ((object = AbstractDatabaseListFragment.this.getListOptionsForGameInfo((GameInfo)object)) != null && object.size() > 0) {
                    object2 = new RookieMoreDialogFragment();
                    object2.setListOptions((List<RookieMoreDialogFragment.ListOption>)object);
                    object2.show(AbstractDatabaseListFragment.this.getChildFragmentManager(), AbstractDatabaseListFragment.MOREDIALOG);
                    object2.setOnDialogCloseListener(new AbstractDialogFragment.OnDialogCloseListener(){

                        @Override
                        public void onDialogClosed() {
                            AbstractDatabaseListFragment.this.updateList();
                        }
                    });
                    return true;
                }
                return false;
            }

        });
        layoutInflater.setBackgroundResource(2131230870);
        layoutInflater.setAdapter(this._listAdapter);
        bundle = new RelativeLayout((Context)this.getActivity());
        this._toast = new AnalyzeSavedToast((Context)this.getActivity());
        this._toast.hide();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(13, -1);
        bundle.addView((View)this._toast, (ViewGroup.LayoutParams)layoutParams);
        viewGroup.addView((View)layoutInflater);
        viewGroup.addView((View)bundle);
        return viewGroup;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.updateList();
        DialogFragment dialogFragment = (DialogFragment)this.getChildFragmentManager().findFragmentByTag("moredialog");
        if (dialogFragment != null) {
            dialogFragment.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    protected void showAnalysisSavedToast() {
        this._toast.showFor(1000);
    }

    @Override
    public boolean showMenu() {
        return true;
    }

    protected void updateList() {
        this._gameInfos = this.getGameInfoList();
        if (this._gameInfos.size() == 0) {
            this._introView = this.inflateIntroView((ViewGroup)this.getView());
        }
        this._listAdapter.notifyDataSetChanged();
    }

    protected abstract void updateListItem(ListItemViewType var1, GameInfo var2, boolean var3);

    protected class CopyGameListOption
    implements RookieMoreDialogFragment.ListOption {
        private GameInfo _info;
        private String _string;

        public CopyGameListOption(GameInfo gameInfo, String string) {
            this._string = string;
            this._info = gameInfo;
        }

        @Override
        public void executeAction() {
            AbstractMoveContainer abstractMoveContainer = ServiceProvider.getInstance().getGameService().getGame(this._info.getGameID()).copy();
            abstractMoveContainer.setOriginalGameId(this._info.getGameID());
            abstractMoveContainer.setGameId(null);
            abstractMoveContainer.setType(GameType.ANALYZED_GAME);
            ServiceProvider.getInstance().getGameService().saveAnalysis((Game)abstractMoveContainer);
            AbstractDatabaseListFragment.this.showAnalysisSavedToast();
        }

        @Override
        public String getString() {
            return this._string;
        }
    }

    protected static class DeleteListOption
    implements RookieMoreDialogFragment.ListOption {
        private GameInfo _info;
        private String _string;

        public DeleteListOption(Context context, GameInfo gameInfo) {
            this._string = context.getString(2131690082);
            this._info = gameInfo;
        }

        @Override
        public void executeAction() {
            ServiceProvider.getInstance().getGameService().deleteGameWithId(this._info.getGameID());
        }

        @Override
        public String getString() {
            return this._string;
        }
    }

    protected class EditListOption
    implements RookieMoreDialogFragment.ListOption {
        private GameInfo _info;
        private IContentPresenter _presenter;
        private String _string;

        public EditListOption(Context context, GameInfo gameInfo, IContentPresenter iContentPresenter) {
            this._presenter = iContentPresenter;
            this._string = context.getString(2131690083);
            this._info = gameInfo;
        }

        @Override
        public void executeAction() {
            EditGameInformationFragment editGameInformationFragment = new EditGameInformationFragment();
            editGameInformationFragment.setGame(ServiceProvider.getInstance().getGameService().getGame(this._info.getGameID()));
            this._presenter.showFragment(editGameInformationFragment, false, true);
        }

        @Override
        public String getString() {
            return this._string;
        }
    }

    private class GameInfoListAdapter
    extends BaseAdapter {
        private GameInfoListAdapter() {
        }

        public int getCount() {
            return AbstractDatabaseListFragment.this._gameInfos.size();
        }

        public GameInfo getItem(int n) {
            return (GameInfo)AbstractDatabaseListFragment.this._gameInfos.get(n);
        }

        public long getItemId(int n) {
            return 0L;
        }

        public View getView(int n, View object, ViewGroup object2) {
            if (object == null) {
                object = AbstractDatabaseListFragment.this.createListItemView();
            }
            object2 = (GameInfo)AbstractDatabaseListFragment.this._gameInfos.get(n);
            AbstractDatabaseListFragment abstractDatabaseListFragment = AbstractDatabaseListFragment.this;
            boolean bl = n >= AbstractDatabaseListFragment.this._numberOfItemsToShow;
            abstractDatabaseListFragment.updateListItem(object, (GameInfo)object2, bl);
            return object;
        }

        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            if (AbstractDatabaseListFragment.this._introView != null) {
                View view = AbstractDatabaseListFragment.this._introView;
                int n = AbstractDatabaseListFragment.this._gameInfos.size() > 0 ? 8 : 0;
                view.setVisibility(n);
            }
            AbstractDatabaseListFragment.this._numberOfItemsToShow = AbstractDatabaseListFragment.this.getMaximumNumbersOfItemsToShow();
        }
    }

}
