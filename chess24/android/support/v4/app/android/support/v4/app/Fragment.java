/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.app.Activity
 *  android.content.ComponentCallbacks
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.SparseArray
 *  android.view.ContextMenu
 *  android.view.ContextMenu$ContextMenuInfo
 *  android.view.LayoutInflater
 *  android.view.LayoutInflater$Factory2
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnCreateContextMenuListener
 *  android.view.ViewGroup
 *  android.view.animation.Animation
 */
package android.support.v4.app;

import android.animation.Animator;
import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ViewModelStore;
import android.arch.lifecycle.ViewModelStoreOwner;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentHostCallback;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManagerImpl;
import android.support.v4.app.FragmentManagerNonConfig;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManagerImpl;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.app.SuperNotCalledException;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.view.LayoutInflaterCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Fragment
implements ComponentCallbacks,
View.OnCreateContextMenuListener,
LifecycleOwner,
ViewModelStoreOwner {
    static final int ACTIVITY_CREATED = 2;
    static final int CREATED = 1;
    static final int INITIALIZING = 0;
    static final int RESUMED = 5;
    static final int STARTED = 4;
    static final int STOPPED = 3;
    static final Object USE_DEFAULT_TRANSITION;
    private static final SimpleArrayMap<String, Class<?>> sClassMap;
    boolean mAdded;
    AnimationInfo mAnimationInfo;
    Bundle mArguments;
    int mBackStackNesting;
    boolean mCalled;
    FragmentManagerImpl mChildFragmentManager;
    FragmentManagerNonConfig mChildNonConfig;
    ViewGroup mContainer;
    int mContainerId;
    boolean mDeferStart;
    boolean mDetached;
    int mFragmentId;
    FragmentManagerImpl mFragmentManager;
    boolean mFromLayout;
    boolean mHasMenu;
    boolean mHidden;
    boolean mHiddenChanged;
    FragmentHostCallback mHost;
    boolean mInLayout;
    int mIndex = -1;
    View mInnerView;
    boolean mIsCreated;
    boolean mIsNewlyAdded;
    LayoutInflater mLayoutInflater;
    LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
    LoaderManagerImpl mLoaderManager;
    boolean mMenuVisible = true;
    Fragment mParentFragment;
    boolean mPerformedCreateView;
    float mPostponedAlpha;
    boolean mRemoving;
    boolean mRestored;
    boolean mRetainInstance;
    boolean mRetaining;
    Bundle mSavedFragmentState;
    @Nullable
    Boolean mSavedUserVisibleHint;
    SparseArray<Parcelable> mSavedViewState;
    int mState = 0;
    String mTag;
    Fragment mTarget;
    int mTargetIndex = -1;
    int mTargetRequestCode;
    boolean mUserVisibleHint = true;
    View mView;
    ViewModelStore mViewModelStore;
    String mWho;

    static {
        sClassMap = new SimpleArrayMap();
        USE_DEFAULT_TRANSITION = new Object();
    }

    private void callStartTransitionListener() {
        OnStartEnterTransitionListener onStartEnterTransitionListener;
        if (this.mAnimationInfo == null) {
            onStartEnterTransitionListener = null;
        } else {
            this.mAnimationInfo.mEnterTransitionPostponed = false;
            onStartEnterTransitionListener = this.mAnimationInfo.mStartEnterTransitionListener;
            this.mAnimationInfo.mStartEnterTransitionListener = null;
        }
        if (onStartEnterTransitionListener != null) {
            onStartEnterTransitionListener.onStartEnterTransition();
        }
    }

    private AnimationInfo ensureAnimationInfo() {
        if (this.mAnimationInfo == null) {
            this.mAnimationInfo = new AnimationInfo();
        }
        return this.mAnimationInfo;
    }

    public static Fragment instantiate(Context context, String string) {
        return Fragment.instantiate(context, string, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Fragment instantiate(Context object, String string, @Nullable Bundle object2) {
        try {
            Class<?> class_;
            Class<?> class_2 = class_ = sClassMap.get(string);
            if (class_ == null) {
                class_2 = object.getClassLoader().loadClass(string);
                sClassMap.put(string, class_2);
            }
            object = (Fragment)class_2.getConstructor(new Class[0]).newInstance(new Object[0]);
            if (object2 != null) {
                object2.setClassLoader(object.getClass().getClassLoader());
                object.setArguments((Bundle)object2);
            }
            return object;
        }
        catch (InvocationTargetException invocationTargetException) {
            object2 = new StringBuilder();
            object2.append("Unable to instantiate fragment ");
            object2.append(string);
            object2.append(": calling Fragment constructor caused an exception");
            throw new InstantiationException(object2.toString(), invocationTargetException);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            object2 = new StringBuilder();
            object2.append("Unable to instantiate fragment ");
            object2.append(string);
            object2.append(": could not find Fragment constructor");
            throw new InstantiationException(object2.toString(), noSuchMethodException);
        }
        catch (IllegalAccessException illegalAccessException) {
            object2 = new StringBuilder();
            object2.append("Unable to instantiate fragment ");
            object2.append(string);
            object2.append(": make sure class name exists, is public, and has an");
            object2.append(" empty constructor that is public");
            throw new InstantiationException(object2.toString(), illegalAccessException);
        }
        catch (java.lang.InstantiationException instantiationException) {
            object2 = new StringBuilder();
            object2.append("Unable to instantiate fragment ");
            object2.append(string);
            object2.append(": make sure class name exists, is public, and has an");
            object2.append(" empty constructor that is public");
            throw new InstantiationException(object2.toString(), instantiationException);
        }
        catch (ClassNotFoundException classNotFoundException) {
            object2 = new StringBuilder();
            object2.append("Unable to instantiate fragment ");
            object2.append(string);
            object2.append(": make sure class name exists, is public, and has an");
            object2.append(" empty constructor that is public");
            throw new InstantiationException(object2.toString(), classNotFoundException);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static boolean isSupportFragmentClass(Context context, String string) {
        Class<?> class_;
        try {
            Class<?> class_2;
            class_ = class_2 = sClassMap.get(string);
            if (class_2 != null) return Fragment.class.isAssignableFrom(class_);
        }
        catch (ClassNotFoundException classNotFoundException) {
            return false;
        }
        class_ = context.getClassLoader().loadClass(string);
        sClassMap.put(string, class_);
        return Fragment.class.isAssignableFrom(class_);
    }

    public void dump(String string, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        StringBuilder stringBuilder;
        Object object;
        printWriter.print(string);
        printWriter.print("mFragmentId=#");
        printWriter.print(Integer.toHexString(this.mFragmentId));
        printWriter.print(" mContainerId=#");
        printWriter.print(Integer.toHexString(this.mContainerId));
        printWriter.print(" mTag=");
        printWriter.println(this.mTag);
        printWriter.print(string);
        printWriter.print("mState=");
        printWriter.print(this.mState);
        printWriter.print(" mIndex=");
        printWriter.print(this.mIndex);
        printWriter.print(" mWho=");
        printWriter.print(this.mWho);
        printWriter.print(" mBackStackNesting=");
        printWriter.println(this.mBackStackNesting);
        printWriter.print(string);
        printWriter.print("mAdded=");
        printWriter.print(this.mAdded);
        printWriter.print(" mRemoving=");
        printWriter.print(this.mRemoving);
        printWriter.print(" mFromLayout=");
        printWriter.print(this.mFromLayout);
        printWriter.print(" mInLayout=");
        printWriter.println(this.mInLayout);
        printWriter.print(string);
        printWriter.print("mHidden=");
        printWriter.print(this.mHidden);
        printWriter.print(" mDetached=");
        printWriter.print(this.mDetached);
        printWriter.print(" mMenuVisible=");
        printWriter.print(this.mMenuVisible);
        printWriter.print(" mHasMenu=");
        printWriter.println(this.mHasMenu);
        printWriter.print(string);
        printWriter.print("mRetainInstance=");
        printWriter.print(this.mRetainInstance);
        printWriter.print(" mRetaining=");
        printWriter.print(this.mRetaining);
        printWriter.print(" mUserVisibleHint=");
        printWriter.println(this.mUserVisibleHint);
        if (this.mFragmentManager != null) {
            printWriter.print(string);
            printWriter.print("mFragmentManager=");
            printWriter.println(this.mFragmentManager);
        }
        if (this.mHost != null) {
            printWriter.print(string);
            printWriter.print("mHost=");
            printWriter.println(this.mHost);
        }
        if (this.mParentFragment != null) {
            printWriter.print(string);
            printWriter.print("mParentFragment=");
            printWriter.println(this.mParentFragment);
        }
        if (this.mArguments != null) {
            printWriter.print(string);
            printWriter.print("mArguments=");
            printWriter.println((Object)this.mArguments);
        }
        if (this.mSavedFragmentState != null) {
            printWriter.print(string);
            printWriter.print("mSavedFragmentState=");
            printWriter.println((Object)this.mSavedFragmentState);
        }
        if (this.mSavedViewState != null) {
            printWriter.print(string);
            printWriter.print("mSavedViewState=");
            printWriter.println(this.mSavedViewState);
        }
        if (this.mTarget != null) {
            printWriter.print(string);
            printWriter.print("mTarget=");
            printWriter.print(this.mTarget);
            printWriter.print(" mTargetRequestCode=");
            printWriter.println(this.mTargetRequestCode);
        }
        if (this.getNextAnim() != 0) {
            printWriter.print(string);
            printWriter.print("mNextAnim=");
            printWriter.println(this.getNextAnim());
        }
        if (this.mContainer != null) {
            printWriter.print(string);
            printWriter.print("mContainer=");
            printWriter.println((Object)this.mContainer);
        }
        if (this.mView != null) {
            printWriter.print(string);
            printWriter.print("mView=");
            printWriter.println((Object)this.mView);
        }
        if (this.mInnerView != null) {
            printWriter.print(string);
            printWriter.print("mInnerView=");
            printWriter.println((Object)this.mView);
        }
        if (this.getAnimatingAway() != null) {
            printWriter.print(string);
            printWriter.print("mAnimatingAway=");
            printWriter.println((Object)this.getAnimatingAway());
            printWriter.print(string);
            printWriter.print("mStateAfterAnimating=");
            printWriter.println(this.getStateAfterAnimating());
        }
        if (this.mLoaderManager != null) {
            printWriter.print(string);
            printWriter.println("Loader Manager:");
            object = this.mLoaderManager;
            stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append("  ");
            object.dump(stringBuilder.toString(), fileDescriptor, printWriter, arrstring);
        }
        if (this.mChildFragmentManager != null) {
            printWriter.print(string);
            object = new StringBuilder();
            object.append("Child ");
            object.append(this.mChildFragmentManager);
            object.append(":");
            printWriter.println(object.toString());
            object = this.mChildFragmentManager;
            stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append("  ");
            object.dump(stringBuilder.toString(), fileDescriptor, printWriter, arrstring);
        }
    }

    public final boolean equals(Object object) {
        return super.equals(object);
    }

    Fragment findFragmentByWho(String string) {
        if (string.equals(this.mWho)) {
            return this;
        }
        if (this.mChildFragmentManager != null) {
            return this.mChildFragmentManager.findFragmentByWho(string);
        }
        return null;
    }

    @Nullable
    public final FragmentActivity getActivity() {
        if (this.mHost == null) {
            return null;
        }
        return (FragmentActivity)this.mHost.getActivity();
    }

    public boolean getAllowEnterTransitionOverlap() {
        if (this.mAnimationInfo != null && this.mAnimationInfo.mAllowEnterTransitionOverlap != null) {
            return this.mAnimationInfo.mAllowEnterTransitionOverlap;
        }
        return true;
    }

    public boolean getAllowReturnTransitionOverlap() {
        if (this.mAnimationInfo != null && this.mAnimationInfo.mAllowReturnTransitionOverlap != null) {
            return this.mAnimationInfo.mAllowReturnTransitionOverlap;
        }
        return true;
    }

    View getAnimatingAway() {
        if (this.mAnimationInfo == null) {
            return null;
        }
        return this.mAnimationInfo.mAnimatingAway;
    }

    Animator getAnimator() {
        if (this.mAnimationInfo == null) {
            return null;
        }
        return this.mAnimationInfo.mAnimator;
    }

    @Nullable
    public final Bundle getArguments() {
        return this.mArguments;
    }

    @NonNull
    public final FragmentManager getChildFragmentManager() {
        if (this.mChildFragmentManager == null) {
            this.instantiateChildFragmentManager();
            if (this.mState >= 5) {
                this.mChildFragmentManager.dispatchResume();
            } else if (this.mState >= 4) {
                this.mChildFragmentManager.dispatchStart();
            } else if (this.mState >= 2) {
                this.mChildFragmentManager.dispatchActivityCreated();
            } else if (this.mState >= 1) {
                this.mChildFragmentManager.dispatchCreate();
            }
        }
        return this.mChildFragmentManager;
    }

    @Nullable
    public Context getContext() {
        if (this.mHost == null) {
            return null;
        }
        return this.mHost.getContext();
    }

    @Nullable
    public Object getEnterTransition() {
        if (this.mAnimationInfo == null) {
            return null;
        }
        return this.mAnimationInfo.mEnterTransition;
    }

    SharedElementCallback getEnterTransitionCallback() {
        if (this.mAnimationInfo == null) {
            return null;
        }
        return this.mAnimationInfo.mEnterTransitionCallback;
    }

    @Nullable
    public Object getExitTransition() {
        if (this.mAnimationInfo == null) {
            return null;
        }
        return this.mAnimationInfo.mExitTransition;
    }

    SharedElementCallback getExitTransitionCallback() {
        if (this.mAnimationInfo == null) {
            return null;
        }
        return this.mAnimationInfo.mExitTransitionCallback;
    }

    @Nullable
    public final FragmentManager getFragmentManager() {
        return this.mFragmentManager;
    }

    @Nullable
    public final Object getHost() {
        if (this.mHost == null) {
            return null;
        }
        return this.mHost.onGetHost();
    }

    public final int getId() {
        return this.mFragmentId;
    }

    public final LayoutInflater getLayoutInflater() {
        if (this.mLayoutInflater == null) {
            return this.performGetLayoutInflater(null);
        }
        return this.mLayoutInflater;
    }

    @Deprecated
    @NonNull
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public LayoutInflater getLayoutInflater(@Nullable Bundle bundle) {
        if (this.mHost == null) {
            throw new IllegalStateException("onGetLayoutInflater() cannot be executed until the Fragment is attached to the FragmentManager.");
        }
        bundle = this.mHost.onGetLayoutInflater();
        this.getChildFragmentManager();
        LayoutInflaterCompat.setFactory2((LayoutInflater)bundle, this.mChildFragmentManager.getLayoutInflaterFactory());
        return bundle;
    }

    @Override
    public Lifecycle getLifecycle() {
        return this.mLifecycleRegistry;
    }

    public LoaderManager getLoaderManager() {
        if (this.mLoaderManager != null) {
            return this.mLoaderManager;
        }
        this.mLoaderManager = new LoaderManagerImpl(this, this.getViewModelStore());
        return this.mLoaderManager;
    }

    int getNextAnim() {
        if (this.mAnimationInfo == null) {
            return 0;
        }
        return this.mAnimationInfo.mNextAnim;
    }

    int getNextTransition() {
        if (this.mAnimationInfo == null) {
            return 0;
        }
        return this.mAnimationInfo.mNextTransition;
    }

    int getNextTransitionStyle() {
        if (this.mAnimationInfo == null) {
            return 0;
        }
        return this.mAnimationInfo.mNextTransitionStyle;
    }

    @Nullable
    public final Fragment getParentFragment() {
        return this.mParentFragment;
    }

    public Object getReenterTransition() {
        if (this.mAnimationInfo == null) {
            return null;
        }
        if (this.mAnimationInfo.mReenterTransition == USE_DEFAULT_TRANSITION) {
            return this.getExitTransition();
        }
        return this.mAnimationInfo.mReenterTransition;
    }

    @NonNull
    public final Resources getResources() {
        return this.requireContext().getResources();
    }

    public final boolean getRetainInstance() {
        return this.mRetainInstance;
    }

    @Nullable
    public Object getReturnTransition() {
        if (this.mAnimationInfo == null) {
            return null;
        }
        if (this.mAnimationInfo.mReturnTransition == USE_DEFAULT_TRANSITION) {
            return this.getEnterTransition();
        }
        return this.mAnimationInfo.mReturnTransition;
    }

    @Nullable
    public Object getSharedElementEnterTransition() {
        if (this.mAnimationInfo == null) {
            return null;
        }
        return this.mAnimationInfo.mSharedElementEnterTransition;
    }

    @Nullable
    public Object getSharedElementReturnTransition() {
        if (this.mAnimationInfo == null) {
            return null;
        }
        if (this.mAnimationInfo.mSharedElementReturnTransition == USE_DEFAULT_TRANSITION) {
            return this.getSharedElementEnterTransition();
        }
        return this.mAnimationInfo.mSharedElementReturnTransition;
    }

    int getStateAfterAnimating() {
        if (this.mAnimationInfo == null) {
            return 0;
        }
        return this.mAnimationInfo.mStateAfterAnimating;
    }

    @NonNull
    public final String getString(@StringRes int n) {
        return this.getResources().getString(n);
    }

    @NonNull
    public final /* varargs */ String getString(@StringRes int n, Object ... arrobject) {
        return this.getResources().getString(n, arrobject);
    }

    @Nullable
    public final String getTag() {
        return this.mTag;
    }

    @Nullable
    public final Fragment getTargetFragment() {
        return this.mTarget;
    }

    public final int getTargetRequestCode() {
        return this.mTargetRequestCode;
    }

    @NonNull
    public final CharSequence getText(@StringRes int n) {
        return this.getResources().getText(n);
    }

    public boolean getUserVisibleHint() {
        return this.mUserVisibleHint;
    }

    @Nullable
    public View getView() {
        return this.mView;
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        if (this.getContext() == null) {
            throw new IllegalStateException("Can't access ViewModels from detached fragment");
        }
        if (this.mViewModelStore == null) {
            this.mViewModelStore = new ViewModelStore();
        }
        return this.mViewModelStore;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public final boolean hasOptionsMenu() {
        return this.mHasMenu;
    }

    public final int hashCode() {
        return super.hashCode();
    }

    void initState() {
        this.mIndex = -1;
        this.mWho = null;
        this.mAdded = false;
        this.mRemoving = false;
        this.mFromLayout = false;
        this.mInLayout = false;
        this.mRestored = false;
        this.mBackStackNesting = 0;
        this.mFragmentManager = null;
        this.mChildFragmentManager = null;
        this.mHost = null;
        this.mFragmentId = 0;
        this.mContainerId = 0;
        this.mTag = null;
        this.mHidden = false;
        this.mDetached = false;
        this.mRetaining = false;
    }

    void instantiateChildFragmentManager() {
        if (this.mHost == null) {
            throw new IllegalStateException("Fragment has not been attached yet.");
        }
        this.mChildFragmentManager = new FragmentManagerImpl();
        this.mChildFragmentManager.attachController(this.mHost, new FragmentContainer(){

            @Override
            public Fragment instantiate(Context context, String string, Bundle bundle) {
                return Fragment.this.mHost.instantiate(context, string, bundle);
            }

            @Nullable
            @Override
            public View onFindViewById(int n) {
                if (Fragment.this.mView == null) {
                    throw new IllegalStateException("Fragment does not have a view");
                }
                return Fragment.this.mView.findViewById(n);
            }

            @Override
            public boolean onHasView() {
                if (Fragment.this.mView != null) {
                    return true;
                }
                return false;
            }
        }, this);
    }

    public final boolean isAdded() {
        if (this.mHost != null && this.mAdded) {
            return true;
        }
        return false;
    }

    public final boolean isDetached() {
        return this.mDetached;
    }

    public final boolean isHidden() {
        return this.mHidden;
    }

    boolean isHideReplaced() {
        if (this.mAnimationInfo == null) {
            return false;
        }
        return this.mAnimationInfo.mIsHideReplaced;
    }

    final boolean isInBackStack() {
        if (this.mBackStackNesting > 0) {
            return true;
        }
        return false;
    }

    public final boolean isInLayout() {
        return this.mInLayout;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public final boolean isMenuVisible() {
        return this.mMenuVisible;
    }

    boolean isPostponed() {
        if (this.mAnimationInfo == null) {
            return false;
        }
        return this.mAnimationInfo.mEnterTransitionPostponed;
    }

    public final boolean isRemoving() {
        return this.mRemoving;
    }

    public final boolean isResumed() {
        if (this.mState >= 5) {
            return true;
        }
        return false;
    }

    public final boolean isStateSaved() {
        if (this.mFragmentManager == null) {
            return false;
        }
        return this.mFragmentManager.isStateSaved();
    }

    public final boolean isVisible() {
        if (this.isAdded() && !this.isHidden() && this.mView != null && this.mView.getWindowToken() != null && this.mView.getVisibility() == 0) {
            return true;
        }
        return false;
    }

    void noteStateNotSaved() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved();
        }
    }

    @CallSuper
    public void onActivityCreated(@Nullable Bundle bundle) {
        this.mCalled = true;
    }

    public void onActivityResult(int n, int n2, Intent intent) {
    }

    @Deprecated
    @CallSuper
    public void onAttach(Activity activity) {
        this.mCalled = true;
    }

    @CallSuper
    public void onAttach(Context object) {
        this.mCalled = true;
        object = this.mHost == null ? null : this.mHost.getActivity();
        if (object != null) {
            this.mCalled = false;
            this.onAttach((Activity)object);
        }
    }

    public void onAttachFragment(Fragment fragment) {
    }

    @CallSuper
    public void onConfigurationChanged(Configuration configuration) {
        this.mCalled = true;
    }

    public boolean onContextItemSelected(MenuItem menuItem) {
        return false;
    }

    @CallSuper
    public void onCreate(@Nullable Bundle bundle) {
        this.mCalled = true;
        this.restoreChildFragmentState(bundle);
        if (this.mChildFragmentManager != null && !this.mChildFragmentManager.isStateAtLeast(1)) {
            this.mChildFragmentManager.dispatchCreate();
        }
    }

    public Animation onCreateAnimation(int n, boolean bl, int n2) {
        return null;
    }

    public Animator onCreateAnimator(int n, boolean bl, int n2) {
        return null;
    }

    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        this.getActivity().onCreateContextMenu(contextMenu, view, contextMenuInfo);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return null;
    }

    @CallSuper
    public void onDestroy() {
        this.mCalled = true;
        if (this.mViewModelStore != null && !this.mHost.mFragmentManager.mStateSaved) {
            this.mViewModelStore.clear();
        }
    }

    public void onDestroyOptionsMenu() {
    }

    @CallSuper
    public void onDestroyView() {
        this.mCalled = true;
    }

    @CallSuper
    public void onDetach() {
        this.mCalled = true;
    }

    @NonNull
    public LayoutInflater onGetLayoutInflater(@Nullable Bundle bundle) {
        return this.getLayoutInflater(bundle);
    }

    public void onHiddenChanged(boolean bl) {
    }

    @Deprecated
    @CallSuper
    public void onInflate(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        this.mCalled = true;
    }

    @CallSuper
    public void onInflate(Context object, AttributeSet attributeSet, Bundle bundle) {
        this.mCalled = true;
        object = this.mHost == null ? null : this.mHost.getActivity();
        if (object != null) {
            this.mCalled = false;
            this.onInflate((Activity)object, attributeSet, bundle);
        }
    }

    @CallSuper
    public void onLowMemory() {
        this.mCalled = true;
    }

    public void onMultiWindowModeChanged(boolean bl) {
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return false;
    }

    public void onOptionsMenuClosed(Menu menu) {
    }

    @CallSuper
    public void onPause() {
        this.mCalled = true;
    }

    public void onPictureInPictureModeChanged(boolean bl) {
    }

    public void onPrepareOptionsMenu(Menu menu) {
    }

    public void onRequestPermissionsResult(int n, @NonNull String[] arrstring, @NonNull int[] arrn) {
    }

    @CallSuper
    public void onResume() {
        this.mCalled = true;
    }

    public void onSaveInstanceState(@NonNull Bundle bundle) {
    }

    @CallSuper
    public void onStart() {
        this.mCalled = true;
    }

    @CallSuper
    public void onStop() {
        this.mCalled = true;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
    }

    @CallSuper
    public void onViewStateRestored(@Nullable Bundle bundle) {
        this.mCalled = true;
    }

    @Nullable
    FragmentManager peekChildFragmentManager() {
        return this.mChildFragmentManager;
    }

    void performActivityCreated(Bundle object) {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved();
        }
        this.mState = 2;
        this.mCalled = false;
        this.onActivityCreated((Bundle)object);
        if (!this.mCalled) {
            object = new StringBuilder();
            object.append("Fragment ");
            object.append(this);
            object.append(" did not call through to super.onActivityCreated()");
            throw new SuperNotCalledException(object.toString());
        }
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchActivityCreated();
        }
    }

    void performConfigurationChanged(Configuration configuration) {
        this.onConfigurationChanged(configuration);
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchConfigurationChanged(configuration);
        }
    }

    boolean performContextItemSelected(MenuItem menuItem) {
        if (!this.mHidden) {
            if (this.onContextItemSelected(menuItem)) {
                return true;
            }
            if (this.mChildFragmentManager != null && this.mChildFragmentManager.dispatchContextItemSelected(menuItem)) {
                return true;
            }
        }
        return false;
    }

    void performCreate(Bundle object) {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved();
        }
        this.mState = 1;
        this.mCalled = false;
        this.onCreate((Bundle)object);
        this.mIsCreated = true;
        if (!this.mCalled) {
            object = new StringBuilder();
            object.append("Fragment ");
            object.append(this);
            object.append(" did not call through to super.onCreate()");
            throw new SuperNotCalledException(object.toString());
        }
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    boolean performCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        boolean bl = this.mHidden;
        boolean bl2 = false;
        boolean bl3 = false;
        if (!bl) {
            bl = bl3;
            if (this.mHasMenu) {
                bl = bl3;
                if (this.mMenuVisible) {
                    bl = true;
                    this.onCreateOptionsMenu(menu, menuInflater);
                }
            }
            bl2 = bl;
            if (this.mChildFragmentManager != null) {
                bl2 = bl | this.mChildFragmentManager.dispatchCreateOptionsMenu(menu, menuInflater);
            }
        }
        return bl2;
    }

    View performCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved();
        }
        this.mPerformedCreateView = true;
        return this.onCreateView(layoutInflater, viewGroup, bundle);
    }

    void performDestroy() {
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchDestroy();
        }
        this.mState = 0;
        this.mCalled = false;
        this.mIsCreated = false;
        this.onDestroy();
        if (!this.mCalled) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment ");
            stringBuilder.append(this);
            stringBuilder.append(" did not call through to super.onDestroy()");
            throw new SuperNotCalledException(stringBuilder.toString());
        }
        this.mChildFragmentManager = null;
    }

    void performDestroyView() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchDestroyView();
        }
        this.mState = 1;
        this.mCalled = false;
        this.onDestroyView();
        if (!this.mCalled) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment ");
            stringBuilder.append(this);
            stringBuilder.append(" did not call through to super.onDestroyView()");
            throw new SuperNotCalledException(stringBuilder.toString());
        }
        if (this.mLoaderManager != null) {
            this.mLoaderManager.markForRedelivery();
        }
        this.mPerformedCreateView = false;
    }

    void performDetach() {
        this.mCalled = false;
        this.onDetach();
        this.mLayoutInflater = null;
        if (!this.mCalled) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment ");
            stringBuilder.append(this);
            stringBuilder.append(" did not call through to super.onDetach()");
            throw new SuperNotCalledException(stringBuilder.toString());
        }
        if (this.mChildFragmentManager != null) {
            if (!this.mRetaining) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Child FragmentManager of ");
                stringBuilder.append(this);
                stringBuilder.append(" was not ");
                stringBuilder.append(" destroyed and this fragment is not retaining instance");
                throw new IllegalStateException(stringBuilder.toString());
            }
            this.mChildFragmentManager.dispatchDestroy();
            this.mChildFragmentManager = null;
        }
    }

    @NonNull
    LayoutInflater performGetLayoutInflater(@Nullable Bundle bundle) {
        this.mLayoutInflater = this.onGetLayoutInflater(bundle);
        return this.mLayoutInflater;
    }

    void performLowMemory() {
        this.onLowMemory();
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchLowMemory();
        }
    }

    void performMultiWindowModeChanged(boolean bl) {
        this.onMultiWindowModeChanged(bl);
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchMultiWindowModeChanged(bl);
        }
    }

    boolean performOptionsItemSelected(MenuItem menuItem) {
        if (!this.mHidden) {
            if (this.mHasMenu && this.mMenuVisible && this.onOptionsItemSelected(menuItem)) {
                return true;
            }
            if (this.mChildFragmentManager != null && this.mChildFragmentManager.dispatchOptionsItemSelected(menuItem)) {
                return true;
            }
        }
        return false;
    }

    void performOptionsMenuClosed(Menu menu) {
        if (!this.mHidden) {
            if (this.mHasMenu && this.mMenuVisible) {
                this.onOptionsMenuClosed(menu);
            }
            if (this.mChildFragmentManager != null) {
                this.mChildFragmentManager.dispatchOptionsMenuClosed(menu);
            }
        }
    }

    void performPause() {
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchPause();
        }
        this.mState = 4;
        this.mCalled = false;
        this.onPause();
        if (!this.mCalled) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment ");
            stringBuilder.append(this);
            stringBuilder.append(" did not call through to super.onPause()");
            throw new SuperNotCalledException(stringBuilder.toString());
        }
    }

    void performPictureInPictureModeChanged(boolean bl) {
        this.onPictureInPictureModeChanged(bl);
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchPictureInPictureModeChanged(bl);
        }
    }

    boolean performPrepareOptionsMenu(Menu menu) {
        boolean bl = this.mHidden;
        boolean bl2 = false;
        boolean bl3 = false;
        if (!bl) {
            bl = bl3;
            if (this.mHasMenu) {
                bl = bl3;
                if (this.mMenuVisible) {
                    bl = true;
                    this.onPrepareOptionsMenu(menu);
                }
            }
            bl2 = bl;
            if (this.mChildFragmentManager != null) {
                bl2 = bl | this.mChildFragmentManager.dispatchPrepareOptionsMenu(menu);
            }
        }
        return bl2;
    }

    void performReallyStop() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchReallyStop();
        }
        this.mState = 2;
    }

    void performResume() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved();
            this.mChildFragmentManager.execPendingActions();
        }
        this.mState = 5;
        this.mCalled = false;
        this.onResume();
        if (!this.mCalled) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment ");
            stringBuilder.append(this);
            stringBuilder.append(" did not call through to super.onResume()");
            throw new SuperNotCalledException(stringBuilder.toString());
        }
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchResume();
            this.mChildFragmentManager.execPendingActions();
        }
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }

    void performSaveInstanceState(Bundle bundle) {
        Parcelable parcelable;
        this.onSaveInstanceState(bundle);
        if (this.mChildFragmentManager != null && (parcelable = this.mChildFragmentManager.saveAllState()) != null) {
            bundle.putParcelable("android:support:fragments", parcelable);
        }
    }

    void performStart() {
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.noteStateNotSaved();
            this.mChildFragmentManager.execPendingActions();
        }
        this.mState = 4;
        this.mCalled = false;
        this.onStart();
        if (!this.mCalled) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment ");
            stringBuilder.append(this);
            stringBuilder.append(" did not call through to super.onStart()");
            throw new SuperNotCalledException(stringBuilder.toString());
        }
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchStart();
        }
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    void performStop() {
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        if (this.mChildFragmentManager != null) {
            this.mChildFragmentManager.dispatchStop();
        }
        this.mState = 3;
        this.mCalled = false;
        this.onStop();
        if (!this.mCalled) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment ");
            stringBuilder.append(this);
            stringBuilder.append(" did not call through to super.onStop()");
            throw new SuperNotCalledException(stringBuilder.toString());
        }
    }

    public void postponeEnterTransition() {
        this.ensureAnimationInfo().mEnterTransitionPostponed = true;
    }

    public void registerForContextMenu(View view) {
        view.setOnCreateContextMenuListener((View.OnCreateContextMenuListener)this);
    }

    public final void requestPermissions(@NonNull String[] arrstring, int n) {
        if (this.mHost == null) {
            arrstring = new StringBuilder();
            arrstring.append("Fragment ");
            arrstring.append(this);
            arrstring.append(" not attached to Activity");
            throw new IllegalStateException(arrstring.toString());
        }
        this.mHost.onRequestPermissionsFromFragment(this, arrstring, n);
    }

    @NonNull
    public final FragmentActivity requireActivity() {
        Object object = this.getActivity();
        if (object == null) {
            object = new StringBuilder();
            object.append("Fragment ");
            object.append(this);
            object.append(" not attached to an activity.");
            throw new IllegalStateException(object.toString());
        }
        return object;
    }

    @NonNull
    public final Context requireContext() {
        Object object = this.getContext();
        if (object == null) {
            object = new StringBuilder();
            object.append("Fragment ");
            object.append(this);
            object.append(" not attached to a context.");
            throw new IllegalStateException(object.toString());
        }
        return object;
    }

    @NonNull
    public final FragmentManager requireFragmentManager() {
        Object object = this.getFragmentManager();
        if (object == null) {
            object = new StringBuilder();
            object.append("Fragment ");
            object.append(this);
            object.append(" not associated with a fragment manager.");
            throw new IllegalStateException(object.toString());
        }
        return object;
    }

    @NonNull
    public final Object requireHost() {
        Object object = this.getHost();
        if (object == null) {
            object = new StringBuilder();
            object.append("Fragment ");
            object.append(this);
            object.append(" not attached to a host.");
            throw new IllegalStateException(object.toString());
        }
        return object;
    }

    void restoreChildFragmentState(@Nullable Bundle bundle) {
        if (bundle != null && (bundle = bundle.getParcelable("android:support:fragments")) != null) {
            if (this.mChildFragmentManager == null) {
                this.instantiateChildFragmentManager();
            }
            this.mChildFragmentManager.restoreAllState((Parcelable)bundle, this.mChildNonConfig);
            this.mChildNonConfig = null;
            this.mChildFragmentManager.dispatchCreate();
        }
    }

    final void restoreViewState(Bundle object) {
        if (this.mSavedViewState != null) {
            this.mInnerView.restoreHierarchyState(this.mSavedViewState);
            this.mSavedViewState = null;
        }
        this.mCalled = false;
        this.onViewStateRestored((Bundle)object);
        if (!this.mCalled) {
            object = new StringBuilder();
            object.append("Fragment ");
            object.append(this);
            object.append(" did not call through to super.onViewStateRestored()");
            throw new SuperNotCalledException(object.toString());
        }
    }

    public void setAllowEnterTransitionOverlap(boolean bl) {
        this.ensureAnimationInfo().mAllowEnterTransitionOverlap = bl;
    }

    public void setAllowReturnTransitionOverlap(boolean bl) {
        this.ensureAnimationInfo().mAllowReturnTransitionOverlap = bl;
    }

    void setAnimatingAway(View view) {
        this.ensureAnimationInfo().mAnimatingAway = view;
    }

    void setAnimator(Animator animator) {
        this.ensureAnimationInfo().mAnimator = animator;
    }

    public void setArguments(@Nullable Bundle bundle) {
        if (this.mIndex >= 0 && this.isStateSaved()) {
            throw new IllegalStateException("Fragment already active and state has been saved");
        }
        this.mArguments = bundle;
    }

    public void setEnterSharedElementCallback(SharedElementCallback sharedElementCallback) {
        this.ensureAnimationInfo().mEnterTransitionCallback = sharedElementCallback;
    }

    public void setEnterTransition(@Nullable Object object) {
        this.ensureAnimationInfo().mEnterTransition = object;
    }

    public void setExitSharedElementCallback(SharedElementCallback sharedElementCallback) {
        this.ensureAnimationInfo().mExitTransitionCallback = sharedElementCallback;
    }

    public void setExitTransition(@Nullable Object object) {
        this.ensureAnimationInfo().mExitTransition = object;
    }

    public void setHasOptionsMenu(boolean bl) {
        if (this.mHasMenu != bl) {
            this.mHasMenu = bl;
            if (this.isAdded() && !this.isHidden()) {
                this.mHost.onSupportInvalidateOptionsMenu();
            }
        }
    }

    void setHideReplaced(boolean bl) {
        this.ensureAnimationInfo().mIsHideReplaced = bl;
    }

    final void setIndex(int n, Fragment object) {
        this.mIndex = n;
        if (object != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(object.mWho);
            stringBuilder.append(":");
            stringBuilder.append(this.mIndex);
            this.mWho = stringBuilder.toString();
            return;
        }
        object = new StringBuilder();
        object.append("android:fragment:");
        object.append(this.mIndex);
        this.mWho = object.toString();
    }

    public void setInitialSavedState(@Nullable SavedState savedState) {
        if (this.mIndex >= 0) {
            throw new IllegalStateException("Fragment already active");
        }
        savedState = savedState != null && savedState.mState != null ? savedState.mState : null;
        this.mSavedFragmentState = savedState;
    }

    public void setMenuVisibility(boolean bl) {
        if (this.mMenuVisible != bl) {
            this.mMenuVisible = bl;
            if (this.mHasMenu && this.isAdded() && !this.isHidden()) {
                this.mHost.onSupportInvalidateOptionsMenu();
            }
        }
    }

    void setNextAnim(int n) {
        if (this.mAnimationInfo == null && n == 0) {
            return;
        }
        this.ensureAnimationInfo().mNextAnim = n;
    }

    void setNextTransition(int n, int n2) {
        if (this.mAnimationInfo == null && n == 0 && n2 == 0) {
            return;
        }
        this.ensureAnimationInfo();
        this.mAnimationInfo.mNextTransition = n;
        this.mAnimationInfo.mNextTransitionStyle = n2;
    }

    void setOnStartEnterTransitionListener(OnStartEnterTransitionListener object) {
        this.ensureAnimationInfo();
        if (object == this.mAnimationInfo.mStartEnterTransitionListener) {
            return;
        }
        if (object != null && this.mAnimationInfo.mStartEnterTransitionListener != null) {
            object = new StringBuilder();
            object.append("Trying to set a replacement startPostponedEnterTransition on ");
            object.append(this);
            throw new IllegalStateException(object.toString());
        }
        if (this.mAnimationInfo.mEnterTransitionPostponed) {
            this.mAnimationInfo.mStartEnterTransitionListener = object;
        }
        if (object != null) {
            object.startListening();
        }
    }

    public void setReenterTransition(@Nullable Object object) {
        this.ensureAnimationInfo().mReenterTransition = object;
    }

    public void setRetainInstance(boolean bl) {
        this.mRetainInstance = bl;
    }

    public void setReturnTransition(@Nullable Object object) {
        this.ensureAnimationInfo().mReturnTransition = object;
    }

    public void setSharedElementEnterTransition(@Nullable Object object) {
        this.ensureAnimationInfo().mSharedElementEnterTransition = object;
    }

    public void setSharedElementReturnTransition(@Nullable Object object) {
        this.ensureAnimationInfo().mSharedElementReturnTransition = object;
    }

    void setStateAfterAnimating(int n) {
        this.ensureAnimationInfo().mStateAfterAnimating = n;
    }

    public void setTargetFragment(@Nullable Fragment fragment, int n) {
        FragmentManager fragmentManager = this.getFragmentManager();
        Object object = fragment != null ? fragment.getFragmentManager() : null;
        if (fragmentManager != null && object != null && fragmentManager != object) {
            object = new StringBuilder();
            object.append("Fragment ");
            object.append(fragment);
            object.append(" must share the same FragmentManager to be set as a target fragment");
            throw new IllegalArgumentException(object.toString());
        }
        for (object = fragment; object != null; object = object.getTargetFragment()) {
            if (object != this) continue;
            object = new StringBuilder();
            object.append("Setting ");
            object.append(fragment);
            object.append(" as the target of ");
            object.append(this);
            object.append(" would create a target cycle");
            throw new IllegalArgumentException(object.toString());
        }
        this.mTarget = fragment;
        this.mTargetRequestCode = n;
    }

    public void setUserVisibleHint(boolean bl) {
        if (!this.mUserVisibleHint && bl && this.mState < 4 && this.mFragmentManager != null && this.isAdded()) {
            this.mFragmentManager.performPendingDeferredStart(this);
        }
        this.mUserVisibleHint = bl;
        bl = this.mState < 4 && !bl;
        this.mDeferStart = bl;
        if (this.mSavedFragmentState != null) {
            this.mSavedUserVisibleHint = this.mUserVisibleHint;
        }
    }

    public boolean shouldShowRequestPermissionRationale(@NonNull String string) {
        if (this.mHost != null) {
            return this.mHost.onShouldShowRequestPermissionRationale(string);
        }
        return false;
    }

    public void startActivity(Intent intent) {
        this.startActivity(intent, null);
    }

    public void startActivity(Intent object, @Nullable Bundle bundle) {
        if (this.mHost == null) {
            object = new StringBuilder();
            object.append("Fragment ");
            object.append(this);
            object.append(" not attached to Activity");
            throw new IllegalStateException(object.toString());
        }
        this.mHost.onStartActivityFromFragment(this, (Intent)object, -1, bundle);
    }

    public void startActivityForResult(Intent intent, int n) {
        this.startActivityForResult(intent, n, null);
    }

    public void startActivityForResult(Intent object, int n, @Nullable Bundle bundle) {
        if (this.mHost == null) {
            object = new StringBuilder();
            object.append("Fragment ");
            object.append(this);
            object.append(" not attached to Activity");
            throw new IllegalStateException(object.toString());
        }
        this.mHost.onStartActivityFromFragment(this, (Intent)object, n, bundle);
    }

    public void startIntentSenderForResult(IntentSender object, int n, @Nullable Intent intent, int n2, int n3, int n4, Bundle bundle) throws IntentSender.SendIntentException {
        if (this.mHost == null) {
            object = new StringBuilder();
            object.append("Fragment ");
            object.append(this);
            object.append(" not attached to Activity");
            throw new IllegalStateException(object.toString());
        }
        this.mHost.onStartIntentSenderFromFragment(this, (IntentSender)object, n, intent, n2, n3, n4, bundle);
    }

    public void startPostponedEnterTransition() {
        if (this.mFragmentManager != null && this.mFragmentManager.mHost != null) {
            if (Looper.myLooper() != this.mFragmentManager.mHost.getHandler().getLooper()) {
                this.mFragmentManager.mHost.getHandler().postAtFrontOfQueue(new Runnable(){

                    @Override
                    public void run() {
                        Fragment.this.callStartTransitionListener();
                    }
                });
                return;
            }
            this.callStartTransitionListener();
            return;
        }
        this.ensureAnimationInfo().mEnterTransitionPostponed = false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        DebugUtils.buildShortClassTag(this, stringBuilder);
        if (this.mIndex >= 0) {
            stringBuilder.append(" #");
            stringBuilder.append(this.mIndex);
        }
        if (this.mFragmentId != 0) {
            stringBuilder.append(" id=0x");
            stringBuilder.append(Integer.toHexString(this.mFragmentId));
        }
        if (this.mTag != null) {
            stringBuilder.append(" ");
            stringBuilder.append(this.mTag);
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public void unregisterForContextMenu(View view) {
        view.setOnCreateContextMenuListener(null);
    }

    static class AnimationInfo {
        private Boolean mAllowEnterTransitionOverlap;
        private Boolean mAllowReturnTransitionOverlap;
        View mAnimatingAway;
        Animator mAnimator;
        private Object mEnterTransition = null;
        SharedElementCallback mEnterTransitionCallback = null;
        boolean mEnterTransitionPostponed;
        private Object mExitTransition = null;
        SharedElementCallback mExitTransitionCallback = null;
        boolean mIsHideReplaced;
        int mNextAnim;
        int mNextTransition;
        int mNextTransitionStyle;
        private Object mReenterTransition = Fragment.USE_DEFAULT_TRANSITION;
        private Object mReturnTransition = Fragment.USE_DEFAULT_TRANSITION;
        private Object mSharedElementEnterTransition = null;
        private Object mSharedElementReturnTransition = Fragment.USE_DEFAULT_TRANSITION;
        OnStartEnterTransitionListener mStartEnterTransitionListener;
        int mStateAfterAnimating;

        AnimationInfo() {
        }
    }

    public static class InstantiationException
    extends RuntimeException {
        public InstantiationException(String string, Exception exception) {
            super(string, exception);
        }
    }

    static interface OnStartEnterTransitionListener {
        public void onStartEnterTransition();

        public void startListening();
    }

    public static class SavedState
    implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        final Bundle mState;

        SavedState(Bundle bundle) {
            this.mState = bundle;
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            this.mState = parcel.readBundle();
            if (classLoader != null && this.mState != null) {
                this.mState.setClassLoader(classLoader);
            }
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeBundle(this.mState);
        }

    }

}
