// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.mainmenu.view;

import android.content.res.Resources;
import android.content.Context;
import android.content.Intent;
import java.util.Iterator;
import java.util.Map;
import java.util.Collections;
import java.util.WeakHashMap;
import de.cisha.android.board.video.VideoSeriesListFragment;
import de.cisha.android.board.LandingFragment;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.feedback.FeedbackFragment;
import de.cisha.android.board.account.StoreFragment;
import de.cisha.android.board.broadcast.TournamentListFragment;
import de.cisha.android.board.database.AnalyzesListFragment;
import de.cisha.android.board.database.PlayzoneGamesListFragment;
import de.cisha.android.board.tactics.TacticsStartFragment;
import de.cisha.android.board.video.VideoFragment;
import de.cisha.android.board.analyze.AnalyzeFragment;
import de.cisha.android.board.playzone.engine.OpponentChooserFragment;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.news.NewsFragment;
import de.cisha.android.board.IContentPresenter;
import android.app.Activity;
import java.util.Set;

public enum MenuItem
{
    ADVANCED(2131690047, 2131231468, 2131231467), 
    ANALYZE(2131690064, 2131231441, 2131231440, false, (MenuAction)new MenuAction() {
        @Override
        public void performMenuAction(final Activity activity, final IContentPresenter contentPresenter) {
            final AnalyzeFragment analyzeFragment = new AnalyzeFragment();
            analyzeFragment.setShowsMenu(true);
            contentPresenter.showFragment(analyzeFragment, true, false);
        }
    }), 
    BEGINNERS(2131690048, 2131231443, 2131231442, false, (MenuAction)new MenuAction() {
        @Override
        public void performMenuAction(final Activity activity, final IContentPresenter contentPresenter) {
            contentPresenter.showFragment(new VideoFragment(), true, false);
        }
    }), 
    BIBLIOTHEK(2131690049, 2131231474, 2131231473), 
    CHALLANGES(2131690051, 2131231447, 2131231446), 
    CHESSTV(2131690050, 2131231449, 2131231448, false, (MenuAction)new MenuAction() {
        @Override
        public void performMenuAction(final Activity activity, final IContentPresenter contentPresenter) {
            contentPresenter.showFragment(new TournamentListFragment(), true, false);
        }
    }), 
    EBOOKS(2131690053, 2131231455, 2131231454), 
    FEEDBACK(2131689978, 2131231460, 2131231459, false, (MenuAction)new MenuAction() {
        @Override
        public void performMenuAction(final Activity activity, final IContentPresenter contentPresenter) {
            contentPresenter.showFragment(new FeedbackFragment(), true, false);
        }
    }), 
    FRIENDS(2131690054, 2131231462, 2131231461), 
    GAMESETTINGS(2131690055, 2131231464, 2131231463), 
    HELP(2131690056, 2131231466, 2131231465), 
    LEADER_BOARDS(2131690057, 2131231470, 2131231469), 
    LESSONS(2131690052, 2131231472, 2131231471), 
    MESSAGES(2131690058, 2131231480, 2131231479), 
    MY_ANALYZE_GAMES(2131690059, 2131231491, 2131231490, false, (MenuAction)new MenuAction() {
        @Override
        public void performMenuAction(final Activity activity, final IContentPresenter contentPresenter) {
            contentPresenter.showFragment(new AnalyzesListFragment(), true, false);
        }
    }), 
    MY_PLAY_GAMES(2131690060, 2131231493, 2131231492, false, (MenuAction)new MenuAction() {
        @Override
        public void performMenuAction(final Activity activity, final IContentPresenter contentPresenter) {
            contentPresenter.showFragment(new PlayzoneGamesListFragment(), true, false);
        }
    }), 
    NEWS(2131690093, 2131231482, 2131231481, false, (MenuAction)new MenuAction() {
        @Override
        public void performMenuAction(final Activity activity, final IContentPresenter contentPresenter) {
            contentPresenter.showFragment(new NewsFragment(), true, false);
        }
    }), 
    PLAYNOW(2131690061, 2131231485, 2131231484, false, (MenuAction)new MenuAction() {
        @Override
        public void performMenuAction(final Activity activity, final IContentPresenter contentPresenter) {
            contentPresenter.showFragment(new OpponentChooserFragment(), true, false);
        }
    }), 
    PLAYVSCOMPUTER(2131690062, 2131231487, 2131231486, false, (MenuAction)new MenuAction() {
        @Override
        public void performMenuAction(final Activity activity, final IContentPresenter contentPresenter) {
            contentPresenter.showFragment(new OpponentChooserFragment(), true, false);
        }
    }), 
    PROFILSETTINGS(2131690063, 2131231489, 2131231488), 
    SIGNOUT(2131690065, 2131231478, 2131231477, false, (MenuAction)new MenuAction() {
        @Override
        public void performMenuAction(final Activity activity, final IContentPresenter contentPresenter) {
            ServiceProvider.getInstance().getLoginService().logOut((ILoginService.LogoutCallback)new ILoginService.LogoutCallback() {
                @Override
                public void logoutFailed(final String s) {
                }
                
                @Override
                public void logoutSucceeded() {
                }
            });
            contentPresenter.showFragment(new LandingFragment(), true, false);
        }
    }), 
    SUBSCRIPTIONS(2131690067, 2131231497, 2131231496, false, (MenuAction)new MenuAction() {
        @Override
        public void performMenuAction(final Activity activity, final IContentPresenter contentPresenter) {
            contentPresenter.showFragment(new StoreFragment(), true, false);
        }
    }), 
    TACTIC(2131690068, 2131231499, 2131231498, false, (MenuAction)new MenuAction() {
        @Override
        public void performMenuAction(final Activity activity, final IContentPresenter contentPresenter) {
            contentPresenter.showFragment(new TacticsStartFragment(), true, false);
        }
    }), 
    TESTYOURCHESS(2131690069, 2131231501, 2131231500), 
    VIDEO_SERIES(2131690070, 2131231505, 2131231504, false, (MenuAction)new MenuAction() {
        @Override
        public void performMenuAction(final Activity activity, final IContentPresenter contentPresenter) {
            contentPresenter.showFragment(new VideoSeriesListFragment(), true, false);
        }
    });
    
    private MenuAction _action;
    private boolean _highlight;
    private int _iconId;
    private int _iconIdInactive;
    private boolean _isNew;
    private Set<MenuItemListener> _listener;
    private String _notification;
    private boolean _show;
    private int _titleResId;
    
    private MenuItem(final int n2, final int n3, final int n4) {
        this(n2, n3, n4, false, null);
    }
    
    private MenuItem(final int titleResId, final int iconId, final int iconIdInactive, final boolean isNew, final MenuAction action) {
        this._highlight = false;
        this._isNew = isNew;
        this._show = true;
        this._titleResId = titleResId;
        this._iconId = iconId;
        this._iconIdInactive = iconIdInactive;
        this._action = action;
        this._listener = Collections.newSetFromMap(new WeakHashMap<MenuItemListener, Boolean>());
    }
    
    public static void removeHighlightFromAll() {
        final MenuItem[] values = values();
        for (int length = values.length, i = 0; i < length; ++i) {
            values[i].setHighlighted(false);
        }
    }
    
    private void setHighlighted(final boolean highlight) {
        this._highlight = highlight;
        final Iterator<MenuItemListener> iterator = this._listener.iterator();
        while (iterator.hasNext()) {
            iterator.next().highlight(highlight);
        }
    }
    
    private static void startActivity(final Class<? extends Activity> clazz, final Activity activity) {
        final Intent intent = new Intent((Context)activity, (Class)clazz);
        intent.setFlags(603979776);
        activity.startActivity(intent);
        activity.overridePendingTransition(2130771985, 2130771986);
    }
    
    public void addNotificationListener(final MenuItemListener menuItemListener) {
        this._listener.add(menuItemListener);
    }
    
    public int getIconId() {
        return this._iconId;
    }
    
    public int getIconIdInactive() {
        return this._iconIdInactive;
    }
    
    public String getNotification() {
        return this._notification;
    }
    
    public String getTitle(final Resources resources) {
        return resources.getString(this._titleResId);
    }
    
    public boolean hasAction() {
        return this._action != null;
    }
    
    public void highlight(final boolean b) {
        if (b) {
            final MenuItem[] values = values();
            for (int length = values.length, i = 0; i < length; ++i) {
                final MenuItem menuItem = values[i];
                menuItem.setHighlighted(menuItem == this);
            }
        }
        else {
            this.setHighlighted(false);
        }
    }
    
    public boolean isHighlighted() {
        return this._highlight;
    }
    
    public boolean isNew() {
        return this._isNew;
    }
    
    public boolean isShown() {
        return this._show;
    }
    
    protected void performAction(final Activity activity, final IContentPresenter contentPresenter) {
        if (this._action != null) {
            this._action.performMenuAction(activity, contentPresenter);
        }
    }
    
    public void setIconIdInactive(final int iconIdInactive) {
        this._iconIdInactive = iconIdInactive;
    }
    
    public void setNotification(final String notification) {
        this._notification = notification;
        final Iterator<MenuItemListener> iterator = this._listener.iterator();
        while (iterator.hasNext()) {
            iterator.next().notificationUpdate(notification);
        }
    }
    
    public void setTitle(final int titleResId) {
        this._titleResId = titleResId;
        final Iterator<MenuItemListener> iterator = this._listener.iterator();
        while (iterator.hasNext()) {
            iterator.next().menuTitleChanged();
        }
    }
    
    public MenuItem show(final boolean show) {
        this._show = show;
        final Iterator<MenuItemListener> iterator = this._listener.iterator();
        while (iterator.hasNext()) {
            iterator.next().show(show);
        }
        return this;
    }
    
    public interface MenuAction
    {
        void performMenuAction(final Activity p0, final IContentPresenter p1);
    }
    
    public interface MenuItemListener
    {
        void highlight(final boolean p0);
        
        void menuTitleChanged();
        
        void notificationUpdate(final String p0);
        
        void show(final boolean p0);
    }
}
