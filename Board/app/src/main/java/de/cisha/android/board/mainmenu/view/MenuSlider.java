// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.mainmenu.view;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Interpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.WindowManager;
import java.util.WeakHashMap;
import android.view.View.MeasureSpec;
import android.graphics.Color;
import java.util.Iterator;
import android.view.View.OnClickListener;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.ImageView;
import java.util.Map;
import android.view.View;
import android.view.ViewGroup;

public abstract class MenuSlider extends ViewGroup implements ObservableMenu
{
    private static final int CLICK_SENSITIVITY = 30;
    private static int CONTENT_VIEW_HEIGHT = 0;
    private static final int SLIDE_DURATION = 350;
    private boolean _active;
    private boolean _animationRunning;
    private boolean _cancelGrab;
    private int _displayWidth;
    private int _grabBoundX;
    private boolean _grabEnabled;
    private View _menuButtonFrame;
    private boolean _menuMoving;
    private boolean _menuOpened;
    private MyMenuObserver _observerOfOtherMenu;
    private Map<MenuObserver, MenuObserver> _observers;
    private ImageView _shadow;
    private View _slideableView;
    private int _startX;
    private int _startY;
    private int _testcount;
    private boolean _touchPerformed;
    private int _xOffsetForClosedMenu;
    private int _xOffsetForOpenedMenu;
    private int _xOffsetOnStart;
    
    public MenuSlider(final Context context) {
        super(context);
        this._menuOpened = false;
        this._animationRunning = false;
        this._menuMoving = false;
        this._grabBoundX = 50;
        this._grabEnabled = true;
        this._testcount = 0;
        this._active = true;
    }
    
    public MenuSlider(final Context context, final AttributeSet set) {
        super(context, set);
        this._menuOpened = false;
        this._animationRunning = false;
        this._menuMoving = false;
        this._grabBoundX = 50;
        this._grabEnabled = true;
        this._testcount = 0;
        this._active = true;
    }
    
    private int getClosedXCoordinate() {
        return this._xOffsetForClosedMenu;
    }
    
    public static int getContentViewHeight() {
        return MenuSlider.CONTENT_VIEW_HEIGHT;
    }
    
    private int getOpenedXCoordinate() {
        return this._xOffsetForOpenedMenu;
    }
    
    private void init() {
        this.setOnTouchListener((View.OnTouchListener)new View.OnTouchListener() {
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
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
        (this._menuButtonFrame = this.getImageFrame()).bringToFront();
        this._menuButtonFrame.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                if (MenuSlider.this.menuShouldAnimate()) {
                    if (MenuSlider.this._menuOpened) {
                        MenuSlider.this.notifyObserversNewX(0.999f);
                    }
                    else {
                        MenuSlider.this.notifyObserversNewX(0.001f);
                    }
                }
                MenuSlider.this.toggle(true);
            }
        });
    }
    
    private boolean menuShouldAnimate() {
        return !this._animationRunning && (this._active || this._menuOpened);
    }
    
    private void notifyObserversClosed() {
        final Iterator<MenuObserver> iterator = this._observers.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().menuClosed();
        }
    }
    
    private void notifyObserversNewX() {
        final float abs = Math.abs((this.getClosedXCoordinate() - this.getScrollX()) / (this.getClosedXCoordinate() - this.getOpenedXCoordinate()));
        this.setBackgroundColor(Color.argb((int)(140.0f * abs), 0, 0, 0));
        this.notifyObserversNewX(abs);
    }
    
    private void notifyObserversNewX(final float n) {
        final Iterator<MenuObserver> iterator = this._observers.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().menuOpenedTo(n);
        }
    }
    
    private void notifyObserversOpened() {
        final Iterator<MenuObserver> iterator = this._observers.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().menuOpened();
        }
    }
    
    public void addMenuObserver(final MenuObserver menuObserver) {
        this._observers.put(menuObserver, menuObserver);
    }
    
    public void closeWithAnimation() {
        if (this._menuOpened) {
            this.toggle(true);
        }
    }
    
    public MotionEvent dispatchSlideMotion(final MotionEvent motionEvent) {
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
                if (this._animationRunning || this._cancelGrab) {
                    return motionEvent;
                }
                if (this.leftSideMenu() && !this._menuOpened && this._startX > this._grabBoundX) {
                    return motionEvent;
                }
                if (!this.leftSideMenu() && !this._menuOpened && this._startX < this._displayWidth - this._grabBoundX) {
                    return motionEvent;
                }
                final int n = (int)motionEvent.getRawX() - this._startX;
                if (this._menuMoving) {
                    final int xOffsetOnStart = this._xOffsetOnStart;
                    int n2;
                    if (n < 0) {
                        n2 = 30;
                    }
                    else {
                        n2 = -30;
                    }
                    final int n3 = xOffsetOnStart - n - n2;
                    if (this.leftSideMenu()) {
                        int n4;
                        if (n3 > this.getClosedXCoordinate()) {
                            n4 = this.getClosedXCoordinate();
                        }
                        else if ((n4 = n3) < this.getOpenedXCoordinate()) {
                            n4 = this.getOpenedXCoordinate();
                        }
                        this.scrollTo(n4, 0);
                    }
                    else {
                        int n5;
                        if (n3 < this.getClosedXCoordinate()) {
                            n5 = this.getClosedXCoordinate();
                        }
                        else if ((n5 = n3) > this.getOpenedXCoordinate()) {
                            n5 = this.getOpenedXCoordinate();
                        }
                        this.scrollTo(n5, 0);
                    }
                    motionEvent.setAction(3);
                    this._touchPerformed = true;
                    return motionEvent;
                }
                if (Math.abs(n) < (5 + Math.abs((int)motionEvent.getRawY() - this._startY)) * 2) {
                    return motionEvent;
                }
                this._menuMoving = true;
                return motionEvent;
            }
            case 1: {
                if (this._touchPerformed) {
                    this.toggle(false);
                    this._menuMoving = false;
                    return motionEvent;
                }
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
            case 3: {
                return motionEvent;
            }
        }
    }
    
    protected abstract View getImageFrame();
    
    protected abstract ImageView getMenuButton();
    
    protected abstract ImageView getMenuShadow();
    
    protected abstract View getMenuSlideContainer();
    
    public int getSliderImageWidth() {
        final View imageFrame = this.getImageFrame();
        if (imageFrame != null) {
            imageFrame.measure(View.MeasureSpec.makeMeasureSpec(10000, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(10000, Integer.MIN_VALUE));
            return imageFrame.getMeasuredWidth();
        }
        return 0;
    }
    
    public boolean isClosed() {
        return this._menuOpened ^ true;
    }
    
    public abstract boolean leftSideMenu();
    
    public void observeOtherMenu(final ObservableMenu observableMenu) {
        observableMenu.addMenuObserver(this._observerOfOtherMenu = new MyMenuObserver());
    }
    
    protected void onFinishInflate() {
        this._observers = new WeakHashMap<MenuObserver, MenuObserver>();
        this.init();
        this._displayWidth = ((WindowManager)this.getContext().getSystemService("window")).getDefaultDisplay().getWidth();
    }
    
    protected void onLayout(final boolean b, final int n, final int n2, final int n3, final int n4) {
        for (int i = 0; i < this.getChildCount(); ++i) {
            this.getChildAt(i).layout(n, n2, n3, n4);
        }
    }
    
    protected void onMeasure(final int n, final int n2) {
        this.measureChildren(n, n2);
        this.setMeasuredDimension(resolveSize(this.getMeasuredWidth(), n), resolveSize(this.getMeasuredHeight(), n2));
        if (this.leftSideMenu()) {
            this._xOffsetForClosedMenu = this._slideableView.getMeasuredWidth() - this._menuButtonFrame.getMeasuredWidth();
            this._xOffsetForOpenedMenu = 0;
            return;
        }
        this._xOffsetForClosedMenu = (this.getMeasuredWidth() - this._menuButtonFrame.getMeasuredWidth()) * -1;
        this._xOffsetForOpenedMenu = -1 * (this.getMeasuredWidth() - this._slideableView.getMeasuredWidth());
    }
    
    protected void onScrollChanged(final int n, final int n2, final int n3, final int n4) {
        super.onScrollChanged(n, n2, n3, n4);
        this.notifyObserversNewX();
    }
    
    protected void onSizeChanged(final int n, final int content_VIEW_HEIGHT, final int n2, final int n3) {
        super.onSizeChanged(n, content_VIEW_HEIGHT, n2, n3);
        if (MenuSlider.CONTENT_VIEW_HEIGHT == 0) {
            MenuSlider.CONTENT_VIEW_HEIGHT = content_VIEW_HEIGHT;
        }
        this.scrollTo(this.getClosedXCoordinate(), 0);
    }
    
    public void removeMenuObserver(final MenuObserver menuObserver) {
        this._observers.remove(menuObserver);
    }
    
    public void setClosed() {
        if (this._menuOpened) {
            this._menuOpened = false;
            this.scrollTo(this.getClosedXCoordinate(), 0);
            this.notifyObserversClosed();
        }
    }
    
    public void setGrabMenuEnabled(final boolean grabEnabled) {
        this._grabEnabled = grabEnabled;
    }
    
    public void setMenuGrabBound(final int grabBoundX) {
        this._grabBoundX = grabBoundX;
    }
    
    public void toggle(final boolean b) {
        if (this.menuShouldAnimate()) {
            this._animationRunning = true;
            int n;
            if (b) {
                if (this._menuOpened) {
                    n = this.getClosedXCoordinate();
                }
                else {
                    n = this.getOpenedXCoordinate();
                }
            }
            else {
                float n2;
                if (this._menuOpened) {
                    n2 = 0.7f;
                }
                else {
                    n2 = 0.3f;
                }
                if ((this.getClosedXCoordinate() - this.getScrollX()) / (this.getClosedXCoordinate() - this.getOpenedXCoordinate()) > n2) {
                    n = this.getOpenedXCoordinate();
                }
                else {
                    n = this.getClosedXCoordinate();
                }
            }
            final boolean b2 = n != this.getClosedXCoordinate();
            final SlideAnimation slideAnimation = new SlideAnimation(this, n);
            slideAnimation.setDuration(350L);
            slideAnimation.setRepeatCount(0);
            slideAnimation.setFillAfter(true);
            slideAnimation.setInterpolator((Interpolator)new DecelerateInterpolator(2.0f));
            slideAnimation.setAnimationListener((Animation.AnimationListener)new Animation.AnimationListener() {
                public void onAnimationEnd(final Animation animation) {
                    MenuSlider.this._menuOpened = b2;
                    if (MenuSlider.this._menuOpened) {
                        MenuSlider.this.notifyObserversOpened();
                    }
                    else {
                        MenuSlider.this.notifyObserversClosed();
                    }
                    MenuSlider.this._animationRunning = false;
                    MenuSlider.this.clearAnimation();
                }
                
                public void onAnimationRepeat(final Animation animation) {
                }
                
                public void onAnimationStart(final Animation animation) {
                }
            });
            this.startAnimation((Animation)slideAnimation);
        }
    }
    
    private class MyMenuObserver implements MenuObserver
    {
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
        public void menuOpenedTo(final float n) {
            MenuSlider.this.getMenuButton().setAlpha((int)((1.0f - n) * 255.0f));
            if (n > 0.0f) {
                MenuSlider.this._active = false;
                return;
            }
            MenuSlider.this._active = true;
        }
    }
}
