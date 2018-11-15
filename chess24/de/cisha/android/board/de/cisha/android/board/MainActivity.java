/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.Bundle
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.Window
 *  android.view.animation.AlphaAnimation
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board;

import android.app.Activity;
import android.arch.lifecycle.ViewModelStoreOwner;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.BasicFragmentActivity;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.IContentView;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.ILoadingPresenter;
import de.cisha.android.board.StatusCodeErrorHelper;
import de.cisha.android.board.account.PurchaseResultReceiver;
import de.cisha.android.board.account.StoreFragment;
import de.cisha.android.board.broadcast.TournamentDetailsFragment;
import de.cisha.android.board.broadcast.TournamentListFragmentSingleView;
import de.cisha.android.board.mainmenu.SettingsMenuCategory;
import de.cisha.android.board.mainmenu.view.MainMenuSlider;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.mainmenu.view.MenuSlider;
import de.cisha.android.board.mainmenu.view.ObservableMenu;
import de.cisha.android.board.mainmenu.view.SettingsMenuSlider;
import de.cisha.android.board.mainmenu.view.SlideMenuInflater;
import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;
import de.cisha.android.board.modalfragments.ConversionContext;
import de.cisha.android.board.modalfragments.GoPremiumWithListDialogFragment;
import de.cisha.android.board.modalfragments.RegisterConversionDialogFragment;
import de.cisha.android.board.pgn.PGNListFragment;
import de.cisha.android.board.playzone.remote.PlayzoneRemoteFragment;
import de.cisha.android.board.profile.ProfileFragment;
import de.cisha.android.board.registration.LoginActivity;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.IMembershipService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.settings.SettingsViewController;
import de.cisha.android.ui.patterns.dialogs.AbstractDialogFragment;
import de.cisha.chess.util.Logger;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainActivity
extends BasicFragmentActivity
implements IContentPresenter,
IErrorPresenter,
ILoadingPresenter {
    public static final String ACTION_SHOWFRAGMENT = "action_show_fragment";
    private static final boolean FORCE_LOGIN = true;
    private static final String MAIN_ACTIVITY_DIALOG = "MainActivityDialog";
    public static final String TOURNAMENT_ID = "tournamentId";
    private IContentView _currentContent;
    private boolean _flagIsCloseable;
    private TextView _headerText;
    private boolean _isDestroyed;
    private View _loadingView;
    private MenuSlider _menuSlider;
    private Runnable _popFragmentActionOnResume;
    private SettingsMenuSlider _settingsMenuSlider;
    private Runnable _showErrorActionOnResume;
    private Runnable _showFragmentActionOnResume;
    private LinearLayout _topLeftButtonContainer;
    private LinearLayout _topRightButtonContainer;

    private void adjustViewsToCurrentFragment() {
        this.setHeaderText(this._currentContent.getHeaderText(this.getResources()));
        this._menuSlider.setMenuGrabBound(35);
        this._menuSlider.setGrabMenuEnabled(this._currentContent.isGrabMenuEnabled());
        this._menuSlider.setClosed();
        Object object = this._currentContent.getHighlightedMenuItem();
        if (object != null) {
            object.highlight(true);
        } else {
            MenuItem.removeHighlightFromAll();
        }
        if (this._settingsMenuSlider != null) {
            this._settingsMenuSlider.bringToFront();
        }
        this.setHeaderText(this._currentContent.getHeaderText(this.getResources()));
        if (this._menuSlider != null) {
            object = this._menuSlider;
            int n = this._currentContent.showMenu() ? 0 : 8;
            object.setVisibility(n);
        }
        this._topLeftButtonContainer.removeAllViews();
        this._topRightButtonContainer.removeAllViews();
        if (!this._currentContent.showMenu()) {
            object = this._currentContent.getLeftButtons((Context)this);
            if (object.size() == 0) {
                object = (ImageView)this.getLayoutInflater().inflate(2131427462, (ViewGroup)this._topLeftButtonContainer, false);
                object.setImageResource(2131230867);
                object.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View view) {
                        MainActivity.this.onBackPressed();
                    }
                });
                this._topLeftButtonContainer.addView((View)object);
            } else {
                this.showLeftButtons((List<View>)object);
            }
        }
        this.updateSettingsMenuSlider();
        this.showRightButtons(this._currentContent.getRightButtons((Context)this));
    }

    private void openPGNListFragmentWithData(Uri uri) {
        PGNListFragment pGNListFragment = new PGNListFragment();
        pGNListFragment.setUri(uri);
        this.showFragment(pGNListFragment, false, true);
    }

    private void setCurrentContentFragment(AbstractContentFragment abstractContentFragment) {
        this._currentContent = abstractContentFragment;
        this.setRequestedOrientation(abstractContentFragment.getOrientation());
    }

    private void setHeaderText(String string) {
        this._headerText.setText((CharSequence)string);
    }

    private void setSettingsTitle(String string) {
        if (this._settingsMenuSlider != null) {
            this._settingsMenuSlider.setHeaderText(string);
        }
    }

    private void showLeftButtons(List<View> object) {
        this._topLeftButtonContainer.removeAllViews();
        object = object.iterator();
        while (object.hasNext()) {
            View view = (View)object.next();
            this._topLeftButtonContainer.addView(view);
        }
    }

    private void showRightButtons(List<View> object) {
        this._topRightButtonContainer.removeAllViews();
        object = object.iterator();
        while (object.hasNext()) {
            View view = (View)object.next();
            this._topRightButtonContainer.addView(view);
        }
    }

    private void startLoginActivity() {
        Intent intent = new Intent((Context)this, LoginActivity.class);
        this.finish();
        this.startActivity(intent);
    }

    private void updateSettingsMenuSlider() {
        Object object = this._currentContent.getSettingsMenuCategories();
        if (this._settingsMenuSlider != null) {
            this._settingsMenuSlider.getSettingsContentContainer().removeAllViews();
            Object object2 = this._settingsMenuSlider;
            boolean bl = object != null && this._currentContent.isGrabMenuEnabled();
            object2.setGrabMenuEnabled(bl);
            if (object != null) {
                object = object.iterator();
                while (object.hasNext()) {
                    object2 = (SettingsMenuCategory)((Object)object.next());
                    View view = LayoutInflater.from((Context)this).inflate(2131427539, null);
                    ((TextView)view.findViewById(2131296969)).setText(this.getText(object2.getTitleResId()));
                    ((ImageView)view.findViewById(2131296968)).setImageResource(object2.getIconResId());
                    this._settingsMenuSlider.getSettingsContentContainer().addView(view, -1, -2);
                    object2 = object2.getViewControllerClass();
                    try {
                        object2 = (SettingsViewController)object2.getDeclaredConstructor(Context.class).newInstance(this);
                        this._settingsMenuSlider.getSettingsContentContainer().addView(object2.getSettingsView());
                    }
                    catch (InvocationTargetException invocationTargetException) {
                        Logger.getInstance().error(MainActivity.class.getName(), InvocationTargetException.class.getName(), invocationTargetException);
                    }
                    catch (IllegalAccessException illegalAccessException) {
                        Logger.getInstance().error(MainActivity.class.getName(), IllegalAccessException.class.getName(), illegalAccessException);
                    }
                    catch (InstantiationException instantiationException) {
                        Logger.getInstance().error(MainActivity.class.getName(), InstantiationException.class.getName(), instantiationException);
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        Logger.getInstance().error(MainActivity.class.getName(), IllegalArgumentException.class.getName(), illegalArgumentException);
                    }
                    catch (NoSuchMethodException noSuchMethodException) {
                        Logger.getInstance().error(MainActivity.class.getName(), NoSuchMethodException.class.getName(), noSuchMethodException);
                    }
                }
                this._settingsMenuSlider.setVisibility(0);
                this._topRightButtonContainer.setPadding(0, 0, this._settingsMenuSlider.getSliderImageWidth(), 0);
                return;
            }
            this._settingsMenuSlider.setVisibility(8);
            this._topRightButtonContainer.setPadding(0, 0, 0, 0);
        }
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        MotionEvent motionEvent2 = motionEvent;
        if (this._menuSlider != null) {
            motionEvent2 = motionEvent;
            if (this._currentContent.showMenu()) {
                motionEvent2 = this._menuSlider.dispatchSlideMotion(motionEvent);
            }
        }
        motionEvent = motionEvent2;
        if (this._settingsMenuSlider != null) {
            motionEvent = this._settingsMenuSlider.dispatchSlideMotion(motionEvent2);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override
    public int getContentMaxHeight() {
        return this.findViewById(2131296260).getHeight();
    }

    @Override
    public int getContentMaxWidth() {
        return this.findViewById(2131296260).getWidth();
    }

    @Override
    public void hideLoadingView() {
        if (this._loadingView.getVisibility() != 0) {
            return;
        }
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(500L);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener(){

            public void onAnimationEnd(Animation animation) {
                MainActivity.this._loadingView.clearAnimation();
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });
        this._loadingView.startAnimation((Animation)alphaAnimation);
        this._loadingView.setVisibility(4);
    }

    @Override
    protected void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (this._currentContent instanceof PurchaseResultReceiver) {
            ((PurchaseResultReceiver)((Object)this._currentContent)).onActivityResult(n, n2, intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (this._menuSlider != null && !this._menuSlider.isClosed()) {
            this._menuSlider.closeWithAnimation();
            return;
        }
        if (this._settingsMenuSlider != null && !this._settingsMenuSlider.isClosed()) {
            this._settingsMenuSlider.closeWithAnimation();
            return;
        }
        if (this._currentContent != null && !this._currentContent.onBackPressed()) {
            this.showLoadingView();
            this.popCurrentFragment();
        }
    }

    @Override
    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        this._isDestroyed = false;
        this.setContentView(2131427357);
        this.getWindow().setBackgroundDrawable(null);
        this._loadingView = this.findViewById(2131296297);
        this._headerText = (TextView)this.findViewById(2131296543);
        this._headerText.setText((CharSequence)"Chess24");
        this._menuSlider = new SlideMenuInflater(this, this).addMainMenu();
        this._topLeftButtonContainer = (LinearLayout)this.findViewById(2131296609);
        this._topRightButtonContainer = (LinearLayout)this.findViewById(2131296610);
        this._settingsMenuSlider = (SettingsMenuSlider)((ViewGroup)this.getLayoutInflater().inflate(2131427537, (ViewGroup)this.findViewById(2131296588))).findViewById(2131296956);
        this._menuSlider.observeOtherMenu(this._settingsMenuSlider);
        this._settingsMenuSlider.observeOtherMenu(this._menuSlider);
        Object object2 = this._settingsMenuSlider;
        boolean bl = this._currentContent == null ? false : this._currentContent.isGrabMenuEnabled();
        object2.setGrabMenuEnabled(bl);
        this._menuSlider.findViewById(2131296606).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MainActivity.this.showFragment(new ProfileFragment(), true, false, true);
            }
        });
        object2 = this.getIntent().getData();
        Object object3 = this.getIntent().getType();
        if (object2 != null && object2.getPath().contains(".pgn") || object3 != null && object3.contains("pgn")) {
            this.openPGNListFragmentWithData((Uri)object2);
            return;
        }
        object2 = this.getIntent().getStringExtra(ACTION_SHOWFRAGMENT);
        if (PlayzoneRemoteFragment.class.getName().equals(object2)) {
            this.showFragment(new PlayzoneRemoteFragment(), true, true);
            return;
        }
        if (TournamentListFragmentSingleView.class.getName().equals(object2)) {
            this.showFragment(new TournamentListFragmentSingleView(), false, false, true);
            return;
        }
        if (TournamentDetailsFragment.class.getName().equals(object2)) {
            object = this.getIntent().getStringExtra(TOURNAMENT_ID);
            if (object != null) {
                object2 = new Bundle();
                object3 = new TournamentDetailsFragment();
                object2.putString("TOURNAMENTDETAIL_TOURNAMENTKEY", (String)object);
                object3.setArguments((Bundle)object2);
                this.showFragment((AbstractContentFragment)object3, true, true, true);
                return;
            }
        } else {
            if (!ServiceProvider.getInstance().getLoginService().isLoggedIn()) {
                this.startLoginActivity();
                return;
            }
            object2 = this.getSupportFragmentManager().findFragmentById(2131296260);
            if (object != null && object2 != null) {
                this.setCurrentContentFragment((AbstractContentFragment)object2);
                this.adjustViewsToCurrentFragment();
                return;
            }
            this.showFragment(new ProfileFragment(), false, false, true);
        }
    }

    @Override
    protected void onDestroy() {
        this._isDestroyed = true;
        super.onDestroy();
    }

    public boolean onKeyUp(int n, KeyEvent keyEvent) {
        if (this._currentContent != null && 82 == n) {
            if (this._currentContent.getSettingsMenuCategories() != null && this._currentContent.getSettingsMenuCategories().size() > 0) {
                if (!this._menuSlider.isClosed()) {
                    this._menuSlider.toggle(true);
                    return true;
                }
                this._settingsMenuSlider.toggle(true);
                return true;
            }
            if (this._currentContent.showMenu()) {
                this._menuSlider.toggle(true);
                return true;
            }
        }
        return super.onKeyUp(n, keyEvent);
    }

    protected void onRestoreInstanceState(Bundle bundle) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        this._isDestroyed = false;
        if (this._showErrorActionOnResume != null) {
            this.runOnUiThread(this._showErrorActionOnResume);
            this._showErrorActionOnResume = null;
        }
        if (this._showFragmentActionOnResume != null) {
            this.runOnUiThread(this._showFragmentActionOnResume);
            this._showFragmentActionOnResume = null;
        }
        if (this._popFragmentActionOnResume != null) {
            this.runOnUiThread(this._popFragmentActionOnResume);
            this._popFragmentActionOnResume = null;
        }
    }

    @Override
    public void popCurrentFragment() {
        if (this._flagIsCloseable) {
            this.finish();
            return;
        }
        Runnable runnable = new Runnable(){

            @Override
            public void run() {
                if (MainActivity.this._currentContent != null) {
                    MainActivity.this.getSupportFragmentManager().beginTransaction().remove((Fragment)((Object)MainActivity.this._currentContent)).commit();
                }
                boolean bl = MainActivity.this._flagIsCloseable;
                boolean bl2 = true;
                if (!bl && MainActivity.this.getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    MainActivity.this.getSupportFragmentManager().popBackStackImmediate();
                    ViewModelStoreOwner viewModelStoreOwner = (AbstractContentFragment)MainActivity.this.getSupportFragmentManager().findFragmentById(2131296260);
                    if (viewModelStoreOwner != null) {
                        MainActivity.this.setCurrentContentFragment(viewModelStoreOwner);
                        MainActivity.this.adjustViewsToCurrentFragment();
                    }
                    viewModelStoreOwner = MainActivity.this;
                    if (MainActivity.this.getSupportFragmentManager().getBackStackEntryCount() != 0) {
                        bl2 = false;
                    }
                    ((MainActivity)viewModelStoreOwner)._flagIsCloseable = bl2;
                    return;
                }
                MainActivity.this.showFragment(new ProfileFragment(), false, false, true);
            }
        };
        if (!this.onSaveInstanceStateCalled()) {
            this.runOnUiThread(runnable);
            return;
        }
        this._popFragmentActionOnResume = runnable;
    }

    @Override
    public void showConversionDialog(AbstractConversionDialogFragment abstractConversionDialogFragment, ConversionContext conversionContext) {
        if (!this.onSaveInstanceStateCalled()) {
            IMembershipService.MembershipLevel membershipLevel = ServiceProvider.getInstance().getMembershipService().getMembershipLevel();
            AbstractConversionDialogFragment abstractConversionDialogFragment2 = abstractConversionDialogFragment;
            if (abstractConversionDialogFragment == null) {
                if (membershipLevel == IMembershipService.MembershipLevel.MembershipLevelGuest) {
                    abstractConversionDialogFragment2 = new RegisterConversionDialogFragment();
                    abstractConversionDialogFragment2.setConversionClickListener(new View.OnClickListener(){

                        public void onClick(View view) {
                            view = new Intent(MainActivity.this.getApplicationContext(), LoginActivity.class);
                            view.setFlags(131072);
                            MainActivity.this.startActivity((Intent)view);
                        }
                    });
                } else {
                    abstractConversionDialogFragment2 = new GoPremiumWithListDialogFragment();
                    abstractConversionDialogFragment2.setConversionClickListener(new View.OnClickListener(){

                        public void onClick(View view) {
                            MainActivity.this.showFragment(new StoreFragment(), false, true);
                        }
                    });
                }
            }
            if (conversionContext != null) {
                abstractConversionDialogFragment2.setArguments(AbstractConversionDialogFragment.createFragmentParams(conversionContext));
            }
            abstractConversionDialogFragment2.show(this.getSupportFragmentManager(), null);
        }
    }

    @Override
    public void showDialog(AbstractDialogFragment abstractDialogFragment) {
        abstractDialogFragment.show(this.getSupportFragmentManager(), MAIN_ACTIVITY_DIALOG);
    }

    @Override
    public void showFragment(AbstractContentFragment abstractContentFragment, boolean bl, boolean bl2) {
        this.showFragment(abstractContentFragment, bl, bl2, false);
    }

    public void showFragment(final AbstractContentFragment abstractContentFragment, boolean bl, final boolean bl2, final boolean bl3) {
        if (!(abstractContentFragment instanceof ProfileFragment) && !ServiceProvider.getInstance().getLoginService().isLoggedIn()) {
            this.startLoginActivity();
            return;
        }
        if (bl) {
            this.showLoadingView();
        }
        if (this._menuSlider != null) {
            this._menuSlider.closeWithAnimation();
        }
        this.setCurrentContentFragment(abstractContentFragment);
        this.runOnUiThread(new Runnable(new Runnable(){

            @Override
            public void run() {
                Object object = MainActivity.this.getSupportFragmentManager();
                if (!MainActivity.this._isDestroyed) {
                    Fragment fragment;
                    MainActivity.this.adjustViewsToCurrentFragment();
                    if (MainActivity.this._menuSlider != null) {
                        if (MainActivity.this._currentContent.showMenu()) {
                            MainActivity.this._menuSlider.setVisibility(0);
                        } else {
                            MainActivity.this._menuSlider.setClosed();
                            MainActivity.this._menuSlider.setVisibility(8);
                        }
                    }
                    FragmentTransaction fragmentTransaction = object.beginTransaction();
                    if ((bl2 || MainActivity.this._flagIsCloseable) && !bl3) {
                        fragmentTransaction.addToBackStack(abstractContentFragment.getClass().getName());
                        MainActivity.this._flagIsCloseable = false;
                    }
                    if ((fragment = object.findFragmentById(2131296260)) != null) {
                        fragmentTransaction.remove(fragment);
                    }
                    fragmentTransaction.commitAllowingStateLoss();
                    object = object.beginTransaction();
                    object.replace(2131296260, abstractContentFragment, abstractContentFragment.getClass().getName());
                    object.commitAllowingStateLoss();
                    if (bl3) {
                        MainActivity.this._flagIsCloseable = true;
                    }
                }
            }
        }){
            final /* synthetic */ Runnable val$action;
            {
                this.val$action = runnable;
            }

            @Override
            public void run() {
                if (!MainActivity.this.onSaveInstanceStateCalled()) {
                    MainActivity.this.runOnUiThread(this.val$action);
                    return;
                }
                MainActivity.this._showFragmentActionOnResume = this.val$action;
            }
        });
    }

    @Override
    public void showLoadingView() {
        this._loadingView.clearAnimation();
        this._loadingView.setVisibility(0);
    }

    @Override
    public void showViewForErrorCode(APIStatusCode aPIStatusCode, IErrorPresenter.IReloadAction iReloadAction) {
        this.showViewForErrorCode(aPIStatusCode, iReloadAction, false);
    }

    @Override
    public void showViewForErrorCode(APIStatusCode object, IErrorPresenter.IReloadAction iReloadAction, final IErrorPresenter.ICancelAction iCancelAction) {
        object = new Runnable((APIStatusCode)((Object)object), iReloadAction){
            final /* synthetic */ APIStatusCode val$errorCode;
            final /* synthetic */ IErrorPresenter.IReloadAction val$reloadAction;
            {
                this.val$errorCode = aPIStatusCode;
                this.val$reloadAction = iReloadAction;
            }

            @Override
            public void run() {
                View.OnClickListener onClickListener = iCancelAction != null ? new View.OnClickListener(){

                    public void onClick(View view) {
                        iCancelAction.cancelPressed();
                    }
                } : null;
                StatusCodeErrorHelper.handleErrorCode(MainActivity.this, this.val$errorCode, this.val$reloadAction, onClickListener);
            }

        };
        if (!this.onSaveInstanceStateCalled()) {
            this.runOnUiThread((Runnable)object);
            return;
        }
        this._showErrorActionOnResume = object;
    }

    @Override
    public void showViewForErrorCode(APIStatusCode aPIStatusCode, IErrorPresenter.IReloadAction iReloadAction, boolean bl) {
        IErrorPresenter.ICancelAction iCancelAction = bl ? new IErrorPresenter.ICancelAction(){

            @Override
            public void cancelPressed() {
                MainActivity.this.popCurrentFragment();
            }
        } : null;
        this.showViewForErrorCode(aPIStatusCode, iReloadAction, iCancelAction);
    }

}
