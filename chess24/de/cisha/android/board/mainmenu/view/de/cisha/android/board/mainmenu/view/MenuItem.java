/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 */
package de.cisha.android.board.mainmenu.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.LandingFragment;
import de.cisha.android.board.account.StoreFragment;
import de.cisha.android.board.analyze.AnalyzeFragment;
import de.cisha.android.board.broadcast.TournamentListFragment;
import de.cisha.android.board.database.AnalyzesListFragment;
import de.cisha.android.board.database.PlayzoneGamesListFragment;
import de.cisha.android.board.feedback.FeedbackFragment;
import de.cisha.android.board.news.NewsFragment;
import de.cisha.android.board.playzone.engine.OpponentChooserFragment;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.tactics.TacticsStartFragment;
import de.cisha.android.board.video.VideoFragment;
import de.cisha.android.board.video.VideoSeriesListFragment;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

public enum MenuItem {
    FRIENDS(2131690054, 2131231462, 2131231461),
    MESSAGES(2131690058, 2131231480, 2131231479),
    CHALLANGES(2131690051, 2131231447, 2131231446),
    NEWS(2131690093, 2131231482, 2131231481, false, new MenuAction(){

        @Override
        public void performMenuAction(Activity activity, IContentPresenter iContentPresenter) {
            iContentPresenter.showFragment(new NewsFragment(), true, false);
        }
    }),
    PLAYNOW(2131690061, 2131231485, 2131231484, false, new MenuAction(){

        @Override
        public void performMenuAction(Activity activity, IContentPresenter iContentPresenter) {
            iContentPresenter.showFragment(new OpponentChooserFragment(), true, false);
        }
    }),
    PLAYVSCOMPUTER(2131690062, 2131231487, 2131231486, false, new MenuAction(){

        @Override
        public void performMenuAction(Activity activity, IContentPresenter iContentPresenter) {
            iContentPresenter.showFragment(new OpponentChooserFragment(), true, false);
        }
    }),
    ANALYZE(2131690064, 2131231441, 2131231440, false, new MenuAction(){

        @Override
        public void performMenuAction(Activity object, IContentPresenter iContentPresenter) {
            object = new AnalyzeFragment();
            object.setShowsMenu(true);
            iContentPresenter.showFragment((AbstractContentFragment)object, true, false);
        }
    }),
    LEADER_BOARDS(2131690057, 2131231470, 2131231469),
    GAMESETTINGS(2131690055, 2131231464, 2131231463),
    TESTYOURCHESS(2131690069, 2131231501, 2131231500),
    BEGINNERS(2131690048, 2131231443, 2131231442, false, new MenuAction(){

        @Override
        public void performMenuAction(Activity activity, IContentPresenter iContentPresenter) {
            iContentPresenter.showFragment(new VideoFragment(), true, false);
        }
    }),
    ADVANCED(2131690047, 2131231468, 2131231467),
    TACTIC(2131690068, 2131231499, 2131231498, false, new MenuAction(){

        @Override
        public void performMenuAction(Activity activity, IContentPresenter iContentPresenter) {
            iContentPresenter.showFragment(new TacticsStartFragment(), true, false);
        }
    }),
    MY_PLAY_GAMES(2131690060, 2131231493, 2131231492, false, new MenuAction(){

        @Override
        public void performMenuAction(Activity activity, IContentPresenter iContentPresenter) {
            iContentPresenter.showFragment(new PlayzoneGamesListFragment(), true, false);
        }
    }),
    MY_ANALYZE_GAMES(2131690059, 2131231491, 2131231490, false, new MenuAction(){

        @Override
        public void performMenuAction(Activity activity, IContentPresenter iContentPresenter) {
            iContentPresenter.showFragment(new AnalyzesListFragment(), true, false);
        }
    }),
    CHESSTV(2131690050, 2131231449, 2131231448, false, new MenuAction(){

        @Override
        public void performMenuAction(Activity activity, IContentPresenter iContentPresenter) {
            iContentPresenter.showFragment(new TournamentListFragment(), true, false);
        }
    }),
    EBOOKS(2131690053, 2131231455, 2131231454),
    SUBSCRIPTIONS(2131690067, 2131231497, 2131231496, false, new MenuAction(){

        @Override
        public void performMenuAction(Activity activity, IContentPresenter iContentPresenter) {
            iContentPresenter.showFragment(new StoreFragment(), true, false);
        }
    }),
    LESSONS(2131690052, 2131231472, 2131231471),
    BIBLIOTHEK(2131690049, 2131231474, 2131231473),
    HELP(2131690056, 2131231466, 2131231465),
    PROFILSETTINGS(2131690063, 2131231489, 2131231488),
    FEEDBACK(2131689978, 2131231460, 2131231459, false, new MenuAction(){

        @Override
        public void performMenuAction(Activity activity, IContentPresenter iContentPresenter) {
            iContentPresenter.showFragment(new FeedbackFragment(), true, false);
        }
    }),
    SIGNOUT(2131690065, 2131231478, 2131231477, false, new MenuAction(){

        @Override
        public void performMenuAction(Activity activity, IContentPresenter iContentPresenter) {
            ServiceProvider.getInstance().getLoginService().logOut(new ILoginService.LogoutCallback(){

                @Override
                public void logoutFailed(String string) {
                }

                @Override
                public void logoutSucceeded() {
                }
            });
            iContentPresenter.showFragment(new LandingFragment(), true, false);
        }

    }),
    VIDEO_SERIES(2131690070, 2131231505, 2131231504, false, new MenuAction(){

        @Override
        public void performMenuAction(Activity activity, IContentPresenter iContentPresenter) {
            iContentPresenter.showFragment(new VideoSeriesListFragment(), true, false);
        }
    });
    
    private MenuAction _action;
    private boolean _highlight = false;
    private int _iconId;
    private int _iconIdInactive;
    private boolean _isNew;
    private Set<MenuItemListener> _listener;
    private String _notification;
    private boolean _show;
    private int _titleResId;

    private MenuItem(int n2, int n3, int n4) {
        this(n2, n3, n4, false, null);
    }

    private MenuItem(int n2, int n3, int n4, boolean bl, MenuAction menuAction) {
        this._isNew = bl;
        this._show = true;
        this._titleResId = n2;
        this._iconId = n3;
        this._iconIdInactive = n4;
        this._action = menuAction;
        this._listener = Collections.newSetFromMap(new WeakHashMap());
    }

    public static void removeHighlightFromAll() {
        MenuItem[] arrmenuItem = MenuItem.values();
        int n = arrmenuItem.length;
        for (int i = 0; i < n; ++i) {
            arrmenuItem[i].setHighlighted(false);
        }
    }

    private void setHighlighted(boolean bl) {
        this._highlight = bl;
        Iterator<MenuItemListener> iterator = this._listener.iterator();
        while (iterator.hasNext()) {
            iterator.next().highlight(bl);
        }
    }

    private static void startActivity(Class<? extends Activity> intent, Activity activity) {
        intent = new Intent((Context)activity, intent);
        intent.setFlags(603979776);
        activity.startActivity(intent);
        activity.overridePendingTransition(2130771985, 2130771986);
    }

    public void addNotificationListener(MenuItemListener menuItemListener) {
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

    public String getTitle(Resources resources) {
        return resources.getString(this._titleResId);
    }

    public boolean hasAction() {
        if (this._action != null) {
            return true;
        }
        return false;
    }

    public void highlight(boolean bl) {
        if (bl) {
            for (MenuItem menuItem : MenuItem.values()) {
                bl = menuItem == this;
                menuItem.setHighlighted(bl);
            }
        } else {
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

    protected void performAction(Activity activity, IContentPresenter iContentPresenter) {
        if (this._action != null) {
            this._action.performMenuAction(activity, iContentPresenter);
        }
    }

    public void setIconIdInactive(int n) {
        this._iconIdInactive = n;
    }

    public void setNotification(String string) {
        this._notification = string;
        Iterator<MenuItemListener> iterator = this._listener.iterator();
        while (iterator.hasNext()) {
            iterator.next().notificationUpdate(string);
        }
    }

    public void setTitle(int n) {
        this._titleResId = n;
        Iterator<MenuItemListener> iterator = this._listener.iterator();
        while (iterator.hasNext()) {
            iterator.next().menuTitleChanged();
        }
    }

    public MenuItem show(boolean bl) {
        this._show = bl;
        Iterator<MenuItemListener> iterator = this._listener.iterator();
        while (iterator.hasNext()) {
            iterator.next().show(bl);
        }
        return this;
    }

    public static interface MenuAction {
        public void performMenuAction(Activity var1, IContentPresenter var2);
    }

    public static interface MenuItemListener {
        public void highlight(boolean var1);

        public void menuTitleChanged();

        public void notificationUpdate(String var1);

        public void show(boolean var1);
    }

}
