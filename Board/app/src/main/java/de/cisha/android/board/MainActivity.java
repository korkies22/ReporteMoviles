// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board;

import android.support.v4.app.FragmentActivity;

import de.cisha.android.board.service.jsonparser.APIStatusCode;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;

import de.cisha.android.ui.patterns.dialogs.AbstractDialogFragment;
import de.cisha.android.board.account.StoreFragment;
import de.cisha.android.board.modalfragments.GoPremiumWithListDialogFragment;
import de.cisha.android.board.modalfragments.RegisterConversionDialogFragment;
import de.cisha.android.board.service.IMembershipService;
import de.cisha.android.board.modalfragments.ConversionContext;
import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;

import android.view.KeyEvent;
import android.support.v4.app.Fragment;

import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.broadcast.TournamentDetailsFragment;
import de.cisha.android.board.broadcast.TournamentListFragmentSingleView;
import de.cisha.android.board.playzone.remote.PlayzoneRemoteFragment;
import de.cisha.android.board.profile.ProfileFragment;
import de.cisha.android.board.mainmenu.view.ObservableMenu;

import android.app.Activity;

import de.cisha.android.board.mainmenu.view.SlideMenuInflater;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import de.cisha.android.board.account.PurchaseResultReceiver;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AlphaAnimation;
import android.view.MotionEvent;

import java.util.Set;
import java.lang.reflect.InvocationTargetException;

import de.cisha.chess.util.Logger;
import de.cisha.android.board.settings.SettingsViewController;

import android.view.LayoutInflater;

import de.cisha.android.board.mainmenu.SettingsMenuCategory;

import android.content.Intent;

import de.cisha.android.board.registration.LoginActivity;

import java.util.Iterator;

import de.cisha.android.board.pgn.PGNListFragment;

import android.net.Uri;

import java.util.List;

import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.content.Context;

import de.cisha.android.board.mainmenu.view.MenuItem;

import android.widget.LinearLayout;

import de.cisha.android.board.mainmenu.view.SettingsMenuSlider;
import de.cisha.android.board.mainmenu.view.MenuSlider;

import android.view.View;
import android.widget.TextView;

public class MainActivity extends BasicFragmentActivity implements IContentPresenter, IErrorPresenter, ILoadingPresenter {
    public static final String ACTION_SHOWFRAGMENT = "action_show_fragment";
    public static final String TOURNAMENT_ID = "tournamentId";
    private static final boolean FORCE_LOGIN = true;
    private static final String MAIN_ACTIVITY_DIALOG = "MainActivityDialog";
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
        final MenuItem highlightedMenuItem = this._currentContent.getHighlightedMenuItem();
        if (highlightedMenuItem != null) {
            highlightedMenuItem.highlight(true);
        } else {
            MenuItem.removeHighlightFromAll();
        }
        if (this._settingsMenuSlider != null) {
            this._settingsMenuSlider.bringToFront();
        }
        this.setHeaderText(this._currentContent.getHeaderText(this.getResources()));
        if (this._menuSlider != null) {
            final MenuSlider menuSlider = this._menuSlider;
            int visibility;
            if (this._currentContent.showMenu()) {
                visibility = 0;
            } else {
                visibility = 8;
            }
            menuSlider.setVisibility(visibility);
        }
        this._topLeftButtonContainer.removeAllViews();
        this._topRightButtonContainer.removeAllViews();
        if (!this._currentContent.showMenu()) {
            final List<View> leftButtons = this._currentContent.getLeftButtons((Context) this);
            if (leftButtons.size() == 0) {
                final ImageView imageView = (ImageView) this.getLayoutInflater().inflate(2131427462, (ViewGroup) this._topLeftButtonContainer, false);
                imageView.setImageResource(2131230867);
                imageView.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
                    public void onClick(final View view) {
                        MainActivity.this.onBackPressed();
                    }
                });
                this._topLeftButtonContainer.addView((View) imageView);
            } else {
                this.showLeftButtons(leftButtons);
            }
        }
        this.updateSettingsMenuSlider();
        this.showRightButtons(this._currentContent.getRightButtons((Context) this));
    }

    private void openPGNListFragmentWithData(final Uri uri) {
        final PGNListFragment pgnListFragment = new PGNListFragment();
        pgnListFragment.setUri(uri);
        this.showFragment(pgnListFragment, false, true);
    }

    private void setCurrentContentFragment(final AbstractContentFragment currentContent) {
        this._currentContent = currentContent;
        this.setRequestedOrientation(currentContent.getOrientation());
    }

    private void setHeaderText(final String text) {
        this._headerText.setText((CharSequence) text);
    }

    private void setSettingsTitle(final String headerText) {
        if (this._settingsMenuSlider != null) {
            this._settingsMenuSlider.setHeaderText(headerText);
        }
    }

    private void showLeftButtons(final List<View> list) {
        this._topLeftButtonContainer.removeAllViews();
        final Iterator<View> iterator = list.iterator();
        while (iterator.hasNext()) {
            this._topLeftButtonContainer.addView((View) iterator.next());
        }
    }

    private void showRightButtons(final List<View> list) {
        this._topRightButtonContainer.removeAllViews();
        final Iterator<View> iterator = list.iterator();
        while (iterator.hasNext()) {
            this._topRightButtonContainer.addView((View) iterator.next());
        }
    }

    private void startLoginActivity() {
        final Intent intent = new Intent((Context) this, (Class) LoginActivity.class);
        this.finish();
        this.startActivity(intent);
    }

    private void updateSettingsMenuSlider() {
        final Set<SettingsMenuCategory> settingsMenuCategories = this._currentContent.getSettingsMenuCategories();
        if (this._settingsMenuSlider != null) {
            this._settingsMenuSlider.getSettingsContentContainer().removeAllViews();
            this._settingsMenuSlider.setGrabMenuEnabled(settingsMenuCategories != null && this._currentContent.isGrabMenuEnabled());
            if (settingsMenuCategories != null) {
                for (final SettingsMenuCategory settingsMenuCategory : settingsMenuCategories) {
                    final View inflate = LayoutInflater.from((Context) this).inflate(2131427539, (ViewGroup) null);
                    ((TextView) inflate.findViewById(2131296969)).setText(this.getText(settingsMenuCategory.getTitleResId()));
                    ((ImageView) inflate.findViewById(2131296968)).setImageResource(settingsMenuCategory.getIconResId());
                    this._settingsMenuSlider.getSettingsContentContainer().addView(inflate, -1, -2);
                    final Class<? extends SettingsViewController> viewControllerClass = settingsMenuCategory.getViewControllerClass();
                    try {
                        this._settingsMenuSlider.getSettingsContentContainer().addView(((SettingsViewController) viewControllerClass.getDeclaredConstructor(Context.class).newInstance(this)).getSettingsView());
                    } catch (InvocationTargetException ex) {
                        Logger.getInstance().error(MainActivity.class.getName(), InvocationTargetException.class.getName(), ex);
                    } catch (IllegalAccessException ex2) {
                        Logger.getInstance().error(MainActivity.class.getName(), IllegalAccessException.class.getName(), ex2);
                    } catch (InstantiationException ex3) {
                        Logger.getInstance().error(MainActivity.class.getName(), InstantiationException.class.getName(), ex3);
                    } catch (IllegalArgumentException ex4) {
                        Logger.getInstance().error(MainActivity.class.getName(), IllegalArgumentException.class.getName(), ex4);
                    } catch (NoSuchMethodException ex5) {
                        Logger.getInstance().error(MainActivity.class.getName(), NoSuchMethodException.class.getName(), ex5);
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

    public boolean dispatchTouchEvent(MotionEvent dispatchSlideMotion) {
        MotionEvent dispatchSlideMotion2 = dispatchSlideMotion;
        if (this._menuSlider != null) {
            dispatchSlideMotion2 = dispatchSlideMotion;
            if (this._currentContent.showMenu()) {
                dispatchSlideMotion2 = this._menuSlider.dispatchSlideMotion(dispatchSlideMotion);
            }
        }
        dispatchSlideMotion = dispatchSlideMotion2;
        if (this._settingsMenuSlider != null) {
            dispatchSlideMotion = this._settingsMenuSlider.dispatchSlideMotion(dispatchSlideMotion2);
        }
        return super.dispatchTouchEvent(dispatchSlideMotion);
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
        final AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(500L);
        alphaAnimation.setAnimationListener((Animation.AnimationListener) new Animation.AnimationListener() {
            public void onAnimationEnd(final Animation animation) {
                MainActivity.this._loadingView.clearAnimation();
            }

            public void onAnimationRepeat(final Animation animation) {
            }

            public void onAnimationStart(final Animation animation) {
            }
        });
        this._loadingView.startAnimation((Animation) alphaAnimation);
        this._loadingView.setVisibility(4);
    }

    @Override
    protected void onActivityResult(final int n, final int n2, final Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (this._currentContent instanceof PurchaseResultReceiver) {
            ((PurchaseResultReceiver) this._currentContent).onActivityResult(n, n2, intent);
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
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this._isDestroyed = false;
        this.setContentView(2131427357);
        this.getWindow().setBackgroundDrawable((Drawable) null);
        this._loadingView = this.findViewById(2131296297);
        (this._headerText = (TextView) this.findViewById(2131296543)).setText((CharSequence) "Chess24");
        this._menuSlider = new SlideMenuInflater(this, this).addMainMenu();
        this._topLeftButtonContainer = (LinearLayout) this.findViewById(2131296609);
        this._topRightButtonContainer = (LinearLayout) this.findViewById(2131296610);
        this._settingsMenuSlider = (SettingsMenuSlider) ((ViewGroup) this.getLayoutInflater().inflate(2131427537, (ViewGroup) this.findViewById(2131296588))).findViewById(2131296956);
        this._menuSlider.observeOtherMenu(this._settingsMenuSlider);
        this._settingsMenuSlider.observeOtherMenu(this._menuSlider);
        this._settingsMenuSlider.setGrabMenuEnabled(this._currentContent != null && this._currentContent.isGrabMenuEnabled());
        this._menuSlider.findViewById(2131296606).setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            public void onClick(final View view) {
                MainActivity.this.showFragment(new ProfileFragment(), true, false, true);
            }
        });
        final Uri data = this.getIntent().getData();
        final String type = this.getIntent().getType();
        if ((data != null && data.getPath().contains(".pgn")) || (type != null && type.contains("pgn"))) {
            this.openPGNListFragmentWithData(data);
            return;
        }
        final String stringExtra = this.getIntent().getStringExtra("action_show_fragment");
        if (PlayzoneRemoteFragment.class.getName().equals(stringExtra)) {
            this.showFragment(new PlayzoneRemoteFragment(), true, true);
            return;
        }
        if (TournamentListFragmentSingleView.class.getName().equals(stringExtra)) {
            this.showFragment(new TournamentListFragmentSingleView(), false, false, true);
            return;
        }
        if (TournamentDetailsFragment.class.getName().equals(stringExtra)) {
            final String stringExtra2 = this.getIntent().getStringExtra("tournamentId");
            if (stringExtra2 != null) {
                final Bundle arguments = new Bundle();
                final TournamentDetailsFragment tournamentDetailsFragment = new TournamentDetailsFragment();
                arguments.putString("TOURNAMENTDETAIL_TOURNAMENTKEY", stringExtra2);
                tournamentDetailsFragment.setArguments(arguments);
                this.showFragment(tournamentDetailsFragment, true, true, true);
            }
        } else {
            if (!ServiceProvider.getInstance().getLoginService().isLoggedIn()) {
                this.startLoginActivity();
                return;
            }
            final Fragment fragmentById = this.getSupportFragmentManager().findFragmentById(2131296260);
            if (bundle != null && fragmentById != null) {
                this.setCurrentContentFragment((AbstractContentFragment) fragmentById);
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

    public boolean onKeyUp(final int n, final KeyEvent keyEvent) {
        if (this._currentContent != null && 82 == n) {
            if (this._currentContent.getSettingsMenuCategories() != null && this._currentContent.getSettingsMenuCategories().size() > 0) {
                if (!this._menuSlider.isClosed()) {
                    this._menuSlider.toggle(true);
                    return true;
                }
                this._settingsMenuSlider.toggle(true);
                return true;
            } else if (this._currentContent.showMenu()) {
                this._menuSlider.toggle(true);
                return true;
            }
        }
        return super.onKeyUp(n, keyEvent);
    }

    protected void onRestoreInstanceState(final Bundle bundle) {
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
        final Runnable popFragmentActionOnResume = new Runnable() {
            @Override
            public void run() {
                if (MainActivity.this._currentContent != null) {
                    MainActivity.this.getSupportFragmentManager().beginTransaction().remove((Fragment) MainActivity.this._currentContent).commit();
                }
                final boolean access.400 = MainActivity.this._flagIsCloseable;
                boolean b = true;
                if (!access.400 && MainActivity.this.getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    MainActivity.this.getSupportFragmentManager().popBackStackImmediate();
                    final AbstractContentFragment abstractContentFragment = (AbstractContentFragment) MainActivity.this.getSupportFragmentManager().findFragmentById(2131296260);
                    if (abstractContentFragment != null) {
                        MainActivity.this.setCurrentContentFragment(abstractContentFragment);
                        MainActivity.this.adjustViewsToCurrentFragment();
                    }
                    final MainActivity this.0 = MainActivity.this;
                    if (MainActivity.this.getSupportFragmentManager().getBackStackEntryCount() != 0) {
                        b = false;
                    }
                    this.0._flagIsCloseable = b;
                    return;
                }
                MainActivity.this.showFragment(new ProfileFragment(), false, false, true);
            }
        };
        if (!this.onSaveInstanceStateCalled()) {
            this.runOnUiThread((Runnable) popFragmentActionOnResume);
            return;
        }
        this._popFragmentActionOnResume = popFragmentActionOnResume;
    }

    @Override
    public void showConversionDialog(final AbstractConversionDialogFragment abstractConversionDialogFragment, final ConversionContext conversionContext) {
        if (!this.onSaveInstanceStateCalled()) {
            final IMembershipService.MembershipLevel membershipLevel = ServiceProvider.getInstance().getMembershipService().getMembershipLevel();
            AbstractConversionDialogFragment abstractConversionDialogFragment2;
            if ((abstractConversionDialogFragment2 = abstractConversionDialogFragment) == null) {
                if (membershipLevel == IMembershipService.MembershipLevel.MembershipLevelGuest) {
                    abstractConversionDialogFragment2 = new RegisterConversionDialogFragment();
                    abstractConversionDialogFragment2.setConversionClickListener((View.OnClickListener) new View.OnClickListener() {
                        public void onClick(final View view) {
                            final Intent intent = new Intent(MainActivity.this.getApplicationContext(), (Class) LoginActivity.class);
                            intent.setFlags(131072);
                            MainActivity.this.startActivity(intent);
                        }
                    });
                } else {
                    abstractConversionDialogFragment2 = new GoPremiumWithListDialogFragment();
                    abstractConversionDialogFragment2.setConversionClickListener((View.OnClickListener) new View.OnClickListener() {
                        public void onClick(final View view) {
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
    public void showDialog(final AbstractDialogFragment abstractDialogFragment) {
        abstractDialogFragment.show(this.getSupportFragmentManager(), "MainActivityDialog");
    }

    @Override
    public void showFragment(final AbstractContentFragment abstractContentFragment, final boolean b, final boolean b2) {
        this.showFragment(abstractContentFragment, b, b2, false);
    }

    public void showFragment(final AbstractContentFragment currentContentFragment, final boolean b, final boolean b2, final boolean b3) {
        if (!(currentContentFragment instanceof ProfileFragment) && !ServiceProvider.getInstance().getLoginService().isLoggedIn()) {
            this.startLoginActivity();
            return;
        }
        if (b) {
            this.showLoadingView();
        }
        if (this._menuSlider != null) {
            this._menuSlider.closeWithAnimation();
        }
        this.setCurrentContentFragment(currentContentFragment);
        this.runOnUiThread((Runnable) new Runnable() {
            final /* synthetic */ Runnable val.action = new Runnable(this, b2, b3, currentContentFragment) {
                final /* synthetic */ boolean val.addToBackStack;
                final /* synthetic */ boolean val.clearBackStack;
                final /* synthetic */ AbstractContentFragment val.fragment;

                @Override
                public void run() {
                    final FragmentManager supportFragmentManager = MainActivity.this.getSupportFragmentManager();
                    if (!MainActivity.this._isDestroyed) {
                        MainActivity.this.adjustViewsToCurrentFragment();
                        if (MainActivity.this._menuSlider != null) {
                            if (MainActivity.this._currentContent.showMenu()) {
                                MainActivity.this._menuSlider.setVisibility(0);
                            } else {
                                MainActivity.this._menuSlider.setClosed();
                                MainActivity.this._menuSlider.setVisibility(8);
                            }
                        }
                        final FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
                        if ((this.val.addToBackStack || MainActivity.this._flagIsCloseable) && !this.val.clearBackStack) {
                            beginTransaction.addToBackStack(this.val.fragment.getClass().getName());
                            MainActivity.this._flagIsCloseable = false;
                        }
                        final Fragment fragmentById = supportFragmentManager.findFragmentById(2131296260);
                        if (fragmentById != null) {
                            beginTransaction.remove(fragmentById);
                        }
                        beginTransaction.commitAllowingStateLoss();
                        final FragmentTransaction beginTransaction2 = supportFragmentManager.beginTransaction();
                        beginTransaction2.replace(2131296260, this.val.fragment, this.val.fragment.getClass().getName());
                        beginTransaction2.commitAllowingStateLoss();
                        if (this.val.clearBackStack) {
                            MainActivity.this._flagIsCloseable = true;
                        }
                    }
                }
            };

            @Override
            public void run() {
                if (!MainActivity.this.onSaveInstanceStateCalled()) {
                    MainActivity.this.runOnUiThread(this.val.action);
                    return;
                }
                MainActivity.this._showFragmentActionOnResume = this.val.action;
            }
        });
    }

    @Override
    public void showLoadingView() {
        this._loadingView.clearAnimation();
        this._loadingView.setVisibility(0);
    }

    @Override
    public void showViewForErrorCode(final APIStatusCode apiStatusCode, final IReloadAction reloadAction) {
        this.showViewForErrorCode(apiStatusCode, reloadAction, false);
    }

    @Override
    public void showViewForErrorCode(final APIStatusCode apiStatusCode, final IReloadAction reloadAction, final ICancelAction cancelAction) {
        final Runnable showErrorActionOnResume = new Runnable() {
            @Override
            public void run() {
                Object o;
                if (cancelAction != null) {
                    o = new View.OnClickListener() {
                        public void onClick(final View view) {
                            cancelAction.cancelPressed();
                        }
                    };
                } else {
                    o = null;
                }
                StatusCodeErrorHelper.handleErrorCode(MainActivity.this, apiStatusCode, reloadAction, (View.OnClickListener) o);
            }
        };
        if (!this.onSaveInstanceStateCalled()) {
            this.runOnUiThread((Runnable) showErrorActionOnResume);
            return;
        }
        this._showErrorActionOnResume = showErrorActionOnResume;
    }

    @Override
    public void showViewForErrorCode(final APIStatusCode apiStatusCode, final IReloadAction reloadAction, final boolean b) {
        ICancelAction cancelAction;
        if (b) {
            cancelAction = new ICancelAction() {
                @Override
                public void cancelPressed() {
                    MainActivity.this.popCurrentFragment();
                }
            };
        } else {
            cancelAction = null;
        }
        this.showViewForErrorCode(apiStatusCode, reloadAction, cancelAction);
    }
}
