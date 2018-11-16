// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.database;

import android.widget.BaseAdapter;
import de.cisha.android.board.analyze.EditGameInformationFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameType;
import de.cisha.chess.model.GameID;
import android.support.v4.app.DialogFragment;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ListAdapter;
import de.cisha.android.ui.patterns.dialogs.AbstractDialogFragment;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.content.Context;
import android.widget.FrameLayout;
import android.view.LayoutInflater;
import java.util.LinkedList;
import java.io.Serializable;
import android.os.Bundle;
import de.cisha.android.board.analyze.AnalyzeFragment;
import android.view.ViewGroup;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import java.util.Set;
import de.cisha.android.ui.patterns.dialogs.RookieMoreDialogFragment;
import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;
import de.cisha.android.board.modalfragments.ConversionContext;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.analyze.AnalyzeSavedToast;
import de.cisha.chess.model.GameInfo;
import java.util.List;
import de.cisha.android.board.AbstractContentFragment;
import android.view.View;

public abstract class AbstractDatabaseListFragment<ListItemViewType extends View> extends AbstractContentFragment
{
    private static final String MOREDIALOG = "moredialog";
    private List<GameInfo> _gameInfos;
    private View _introView;
    private GameInfoListAdapter _listAdapter;
    private int _numberOfItemsToShow;
    private AnalyzeSavedToast _toast;
    
    private void showConversionDialog() {
        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.ANALYSIS, "premium screen shown", "analyzed games");
        this.getContentPresenter().showConversionDialog(null, ConversionContext.SAVED_ANALYZES);
    }
    
    protected abstract ListItemViewType createListItemView();
    
    protected abstract List<GameInfo> getGameInfoList();
    
    protected abstract List<RookieMoreDialogFragment.ListOption> getListOptionsForGameInfo(final GameInfo p0);
    
    protected abstract int getMaximumNumbersOfItemsToShow();
    
    @Override
    public Set<SettingsMenuCategory> getSettingsMenuCategories() {
        return null;
    }
    
    protected abstract View inflateIntroView(final ViewGroup p0);
    
    @Override
    public boolean isGrabMenuEnabled() {
        return true;
    }
    
    protected void itemClicked(final GameInfo gameInfo) {
        final AnalyzeFragment analyzeFragment = new AnalyzeFragment();
        final Bundle arguments = new Bundle();
        arguments.putSerializable("argGameId", (Serializable)gameInfo.getGameID());
        analyzeFragment.setArguments(arguments);
        this.getContentPresenter().showFragment(analyzeFragment, true, true);
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this._gameInfos = new LinkedList<GameInfo>();
        this._listAdapter = new GameInfoListAdapter();
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        this._gameInfos = this.getGameInfoList();
        final FrameLayout frameLayout = new FrameLayout((Context)this.getActivity());
        final ListView listView = (ListView)layoutInflater.inflate(2131427411, (ViewGroup)frameLayout, false);
        listView.setOnItemClickListener((AdapterView.OnItemClickListener)new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int n, final long n2) {
                if (n < AbstractDatabaseListFragment.this._numberOfItemsToShow) {
                    AbstractDatabaseListFragment.this.itemClicked(AbstractDatabaseListFragment.this._listAdapter.getItem(n));
                    return;
                }
                AbstractDatabaseListFragment.this.showConversionDialog();
            }
        });
        listView.setOnItemLongClickListener((AdapterView.OnItemLongClickListener)new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(final AdapterView<?> adapterView, final View view, final int n, final long n2) {
                if (n >= AbstractDatabaseListFragment.this._numberOfItemsToShow) {
                    AbstractDatabaseListFragment.this.showConversionDialog();
                    return true;
                }
                final List<RookieMoreDialogFragment.ListOption> listOptionsForGameInfo = AbstractDatabaseListFragment.this.getListOptionsForGameInfo(AbstractDatabaseListFragment.this._listAdapter.getItem(n));
                if (listOptionsForGameInfo != null && listOptionsForGameInfo.size() > 0) {
                    final RookieMoreDialogFragment rookieMoreDialogFragment = new RookieMoreDialogFragment();
                    rookieMoreDialogFragment.setListOptions(listOptionsForGameInfo);
                    rookieMoreDialogFragment.show(AbstractDatabaseListFragment.this.getChildFragmentManager(), "moredialog");
                    rookieMoreDialogFragment.setOnDialogCloseListener((AbstractDialogFragment.OnDialogCloseListener)new AbstractDialogFragment.OnDialogCloseListener() {
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
        listView.setBackgroundResource(2131230870);
        listView.setAdapter((ListAdapter)this._listAdapter);
        final RelativeLayout relativeLayout = new RelativeLayout((Context)this.getActivity());
        (this._toast = new AnalyzeSavedToast((Context)this.getActivity())).hide();
        final RelativeLayout.LayoutParams relativeLayout.LayoutParams = new RelativeLayout.LayoutParams(-2, -2);
        relativeLayout.LayoutParams.addRule(13, -1);
        relativeLayout.addView((View)this._toast, (ViewGroup.LayoutParams)relativeLayout.LayoutParams);
        frameLayout.addView((View)listView);
        frameLayout.addView((View)relativeLayout);
        return (View)frameLayout;
    }
    
    @Override
    public void onStart() {
        super.onStart();
        this.updateList();
        final DialogFragment dialogFragment = (DialogFragment)this.getChildFragmentManager().findFragmentByTag("moredialog");
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
    
    protected abstract void updateListItem(final ListItemViewType p0, final GameInfo p1, final boolean p2);
    
    protected class CopyGameListOption implements ListOption
    {
        private GameInfo _info;
        private String _string;
        
        public CopyGameListOption(final GameInfo info, final String string) {
            this._string = string;
            this._info = info;
        }
        
        @Override
        public void executeAction() {
            final Game copy = ServiceProvider.getInstance().getGameService().getGame(this._info.getGameID()).copy();
            copy.setOriginalGameId(this._info.getGameID());
            copy.setGameId(null);
            copy.setType(GameType.ANALYZED_GAME);
            ServiceProvider.getInstance().getGameService().saveAnalysis(copy);
            AbstractDatabaseListFragment.this.showAnalysisSavedToast();
        }
        
        @Override
        public String getString() {
            return this._string;
        }
    }
    
    protected static class DeleteListOption implements ListOption
    {
        private GameInfo _info;
        private String _string;
        
        public DeleteListOption(final Context context, final GameInfo info) {
            this._string = context.getString(2131690082);
            this._info = info;
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
    
    protected class EditListOption implements ListOption
    {
        private GameInfo _info;
        private IContentPresenter _presenter;
        private String _string;
        
        public EditListOption(final Context context, final GameInfo info, final IContentPresenter presenter) {
            this._presenter = presenter;
            this._string = context.getString(2131690083);
            this._info = info;
        }
        
        @Override
        public void executeAction() {
            final EditGameInformationFragment editGameInformationFragment = new EditGameInformationFragment();
            editGameInformationFragment.setGame(ServiceProvider.getInstance().getGameService().getGame(this._info.getGameID()));
            this._presenter.showFragment(editGameInformationFragment, false, true);
        }
        
        @Override
        public String getString() {
            return this._string;
        }
    }
    
    private class GameInfoListAdapter extends BaseAdapter
    {
        public int getCount() {
            return AbstractDatabaseListFragment.this._gameInfos.size();
        }
        
        public GameInfo getItem(final int n) {
            return AbstractDatabaseListFragment.this._gameInfos.get(n);
        }
        
        public long getItemId(final int n) {
            return 0L;
        }
        
        public View getView(final int n, View listItemView, final ViewGroup viewGroup) {
            if (listItemView == null) {
                listItemView = AbstractDatabaseListFragment.this.createListItemView();
            }
            AbstractDatabaseListFragment.this.updateListItem(listItemView, AbstractDatabaseListFragment.this._gameInfos.get(n), n >= AbstractDatabaseListFragment.this._numberOfItemsToShow);
            return listItemView;
        }
        
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            if (AbstractDatabaseListFragment.this._introView != null) {
                final View access.500 = AbstractDatabaseListFragment.this._introView;
                int visibility;
                if (AbstractDatabaseListFragment.this._gameInfos.size() > 0) {
                    visibility = 8;
                }
                else {
                    visibility = 0;
                }
                access.500.setVisibility(visibility);
            }
            AbstractDatabaseListFragment.this._numberOfItemsToShow = AbstractDatabaseListFragment.this.getMaximumNumbersOfItemsToShow();
        }
    }
}
