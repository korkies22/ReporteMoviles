/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Color
 *  android.util.AttributeSet
 *  android.view.Display
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.WindowManager
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.widget.ImageView
 */
package de.cisha.android.board.mainmenu.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import de.cisha.android.board.mainmenu.view.MenuObserver;
import de.cisha.android.board.mainmenu.view.ObservableMenu;
import de.cisha.android.board.mainmenu.view.SlideAnimation;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

public abstract class MenuSlider
extends ViewGroup
implements ObservableMenu {
    private static final int CLICK_SENSITIVITY = 30;
    private static int CONTENT_VIEW_HEIGHT = 0;
    private static final int SLIDE_DURATION = 350;
    private boolean _active = true;
    private boolean _animationRunning = false;
    private boolean _cancelGrab;
    private int _displayWidth;
    private int _grabBoundX = 50;
    private boolean _grabEnabled = true;
    private View _menuButtonFrame;
    private boolean _menuMoving = false;
    private boolean _menuOpened = false;
    private MyMenuObserver _observerOfOtherMenu;
    private Map<MenuObserver, MenuObserver> _observers;
    private ImageView _shadow;
    private View _slideableView;
    private int _startX;
    private int _startY;
    private int _testcount = 0;
    private boolean _touchPerformed;
    private int _xOffsetForClosedMenu;
    private int _xOffsetForOpenedMenu;
    private int _xOffsetOnStart;

    public MenuSlider(Context context) {
        super(context);
    }

    public MenuSlider(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private int getClosedXCoordinate() {
        return this._xOffsetForClosedMenu;
    }

    public static int getContentViewHeight() {
        return CONTENT_VIEW_HEIGHT;
    }

    private int getOpenedXCoordinate() {
        return this._xOffsetForOpenedMenu;
    }

    private void init() {
        this.setOnTouchListener(new View.OnTouchListener(){

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!MenuSlider.this._menuOpened) {
                    return false;
                }
                if (motionEvent.getAction() == 1) {
                    MenuSlider.this.closeWithAnimation();
                }
                return true;
            }
        });
        this._shadow = this.getMenuShadow();
        this._slideableView = this.getMenuSlideContainer();
        this._menuButtonFrame = this.getImageFrame();
        this._menuButtonFrame.bringToFront();
        this._menuButtonFrame.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (MenuSlider.this.menuShouldAnimate()) {
                    if (MenuSlider.this._menuOpened) {
                        MenuSlider.this.notifyObserversNewX(0.999f);
                    } else {
                        MenuSlider.this.notifyObserversNewX(0.001f);
                    }
                }
                MenuSlider.this.toggle(true);
            }
        });
    }

    private boolean menuShouldAnimate() {
        if (!this._animationRunning && (this._active || this._menuOpened)) {
            return true;
        }
        return false;
    }

    private void notifyObserversClosed() {
        Iterator<MenuObserver> iterator = this._observers.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().menuClosed();
        }
    }

    private void notifyObserversNewX() {
        int n = this.getScrollX();
        float f = this.getClosedXCoordinate();
        float f2 = this.getOpenedXCoordinate();
        f = Math.abs((float)(this.getClosedXCoordinate() - n) / (f - f2));
        this.setBackgroundColor(Color.argb((int)((int)(140.0f * f)), (int)0, (int)0, (int)0));
        this.notifyObserversNewX(f);
    }

    private void notifyObserversNewX(float f) {
        Iterator<MenuObserver> iterator = this._observers.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().menuOpenedTo(f);
        }
    }

    private void notifyObserversOpened() {
        Iterator<MenuObserver> iterator = this._observers.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().menuOpened();
        }
    }

    @Override
    public void addMenuObserver(MenuObserver menuObserver) {
        this._observers.put(menuObserver, menuObserver);
    }

    public void closeWithAnimation() {
        if (this._menuOpened) {
            this.toggle(true);
        }
    }

    public MotionEvent dispatchSlideMotion(MotionEvent motionEvent) {
        if (!this._active && !this._menuOpened) {
            return motionEvent;
        }
        if (this._animationRunning) {
            this._cancelGrab = true;
            return motionEvent;
        }
        switch (motionEvent.getAction()) {
            default: {
                return motionEvent;
            }
            case 2: {
                if (!this._menuOpened && !this._grabEnabled) {
                    return motionEvent;
                }
                if (this._animationRunning || this._cancelGrab) break;
                if (this.leftSideMenu() && !this._menuOpened && this._startX > this._grabBoundX) {
                    return motionEvent;
                }
                if (!this.leftSideMenu() && !this._menuOpened && this._startX < this._displayWidth - this._grabBoundX) {
                    return motionEvent;
                }
                int n = (int)motionEvent.getRawX() - this._startX;
                if (!this._menuMoving) {
                    int n2 = (int)motionEvent.getRawY();
                    int n3 = this._startY;
                    if (Math.abs(n) < (5 + Math.abs(n2 - n3)) * 2) {
                        return motionEvent;
                    }
                    this._menuMoving = true;
                    return motionEvent;
                }
                int n4 = this._xOffsetOnStart;
                int n5 = n < 0 ? 30 : -30;
                n = n4 - n - n5;
                if (this.leftSideMenu()) {
                    if (n > this.getClosedXCoordinate()) {
                        n5 = this.getClosedXCoordinate();
                    } else {
                        n5 = n;
                        if (n < this.getOpenedXCoordinate()) {
                            n5 = this.getOpenedXCoordinate();
                        }
                    }
                    this.scrollTo(n5, 0);
                } else {
                    if (n < this.getClosedXCoordinate()) {
                        n5 = this.getClosedXCoordinate();
                    } else {
                        n5 = n;
                        if (n > this.getOpenedXCoordinate()) {
                            n5 = this.getOpenedXCoordinate();
                        }
                    }
                    this.scrollTo(n5, 0);
                }
                motionEvent.setAction(3);
                this._touchPerformed = true;
                return motionEvent;
            }
            case 1: {
                if (!this._touchPerformed) break;
                this.toggle(false);
                this._menuMoving = false;
                return motionEvent;
            }
            case 0: {
                this._startX = (int)motionEvent.getRawX();
                this._startY = (int)motionEvent.getRawY();
                this._xOffsetOnStart = this.getScrollX();
                this._touchPerformed = false;
                this._cancelGrab = false;
                this._menuMoving = false;
            }
            case 3: 
        }
        return motionEvent;
    }

    protected abstract View getImageFrame();

    protected abstract ImageView getMenuButton();

    protected abstract ImageView getMenuShadow();

    protected abstract View getMenuSlideContainer();

    public int getSliderImageWidth() {
        View view = this.getImageFrame();
        if (view != null) {
            view.measure(View.MeasureSpec.makeMeasureSpec((int)10000, (int)Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec((int)10000, (int)Integer.MIN_VALUE));
            return view.getMeasuredWidth();
        }
        return 0;
    }

    public boolean isClosed() {
        return this._menuOpened ^ true;
    }

    public abstract boolean leftSideMenu();

    public void observeOtherMenu(ObservableMenu observableMenu) {
        this._observerOfOtherMenu = new MyMenuObserver();
        observableMenu.addMenuObserver(this._observerOfOtherMenu);
    }

    protected void onFinishInflate() {
        this._observers = new WeakHashMap<MenuObserver, MenuObserver>();
        this.init();
        this._displayWidth = ((WindowManager)this.getContext().getSystemService("window")).getDefaultDisplay().getWidth();
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        for (int i = 0; i < this.getChildCount(); ++i) {
            this.getChildAt(i).layout(n, n2, n3, n4);
        }
    }

    protected void onMeasure(int n, int n2) {
        this.measureChildren(n, n2);
        this.setMeasuredDimension(MenuSlider.resolveSize((int)this.getMeasuredWidth(), (int)n), MenuSlider.resolveSize((int)this.getMeasuredHeight(), (int)n2));
        if (this.leftSideMenu()) {
            this._xOffsetForClosedMenu = this._slideableView.getMeasuredWidth() - this._menuButtonFrame.getMeasuredWidth();
            this._xOffsetForOpenedMenu = 0;
            return;
        }
        this._xOffsetForClosedMenu = (this.getMeasuredWidth() - this._menuButtonFrame.getMeasuredWidth()) * -1;
        this._xOffsetForOpenedMenu = -1 * (this.getMeasuredWidth() - this._slideableView.getMeasuredWidth());
    }

    protected void onScrollChanged(int n, int n2, int n3, int n4) {
        super.onScrollChanged(n, n2, n3, n4);
        this.notifyObserversNewX();
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        if (CONTENT_VIEW_HEIGHT == 0) {
            CONTENT_VIEW_HEIGHT = n2;
        }
        this.scrollTo(this.getClosedXCoordinate(), 0);
    }

    @Override
    public void removeMenuObserver(MenuObserver menuObserver) {
        this._observers.remove(menuObserver);
    }

    public void setClosed() {
        if (this._menuOpened) {
            this._menuOpened = false;
            this.scrollTo(this.getClosedXCoordinate(), 0);
            this.notifyObserversClosed();
        }
    }

    public void setGrabMenuEnabled(boolean bl) {
        this._grabEnabled = bl;
    }

    public void setMenuGrabBound(int n) {
        this._grabBoundX = n;
    }

    public void toggle(final boolean bl) {
        if (this.menuShouldAnimate()) {
            int n;
            this._animationRunning = true;
            if (bl) {
                n = this._menuOpened ? this.getClosedXCoordinate() : this.getOpenedXCoordinate();
            } else {
                float f = this._menuOpened ? 0.7f : 0.3f;
                float f2 = this.getClosedXCoordinate();
                float f3 = this.getOpenedXCoordinate();
                n = ((float)this.getClosedXCoordinate() - (float)this.getScrollX()) / (f2 - f3) > f ? this.getOpenedXCoordinate() : this.getClosedXCoordinate();
            }
            bl = n != this.getClosedXCoordinate();
            SlideAnimation slideAnimation = new SlideAnimation(this, n);
            slideAnimation.setDuration(350L);
            slideAnimation.setRepeatCount(0);
            slideAnimation.setFillAfter(true);
            slideAnimation.setInterpolator((Interpolator)new DecelerateInterpolator(2.0f));
            slideAnimation.setAnimationListener(new Animation.AnimationListener(){

                public void onAnimationEnd(Animation animation) {
                    MenuSlider.this._menuOpened = bl;
                    if (MenuSlider.this._menuOpened) {
                        MenuSlider.this.notifyObserversOpened();
                    } else {
                        MenuSlider.this.notifyObserversClosed();
                    }
                    MenuSlider.this._animationRunning = false;
                    MenuSlider.this.clearAnimation();
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                }
            });
            this.startAnimation((Animation)slideAnimation);
        }
    }

    private class MyMenuObserver
    implements MenuObserver {
        private MyMenuObserver() {
        }

        @Override
        public void menuClosed() {
            MenuSlider.this.getMenuButton().setAlpha(255);
            MenuSlider.this._active = true;
        }

        @Override
        public void menuOpened() {
            MenuSlider.this.getMenuButton().setAlpha(0);
            MenuSlider.this._active = false;
        }

        @Override
        public void menuOpenedTo(float f) {
            int n = (int)((1.0f - f) * 255.0f);
            MenuSlider.this.getMenuButton().setAlpha(n);
            if (f > 0.0f) {
                MenuSlider.this._active = false;
                return;
            }
            MenuSlider.this._active = true;
        }
    }

}
