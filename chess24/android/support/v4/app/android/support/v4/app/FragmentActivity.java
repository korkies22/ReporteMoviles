/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.content.res.Configuration
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.os.Parcelable
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.Window
 *  android.view.WindowManager
 *  android.view.WindowManager$LayoutParams
 */
package android.support.v4.app;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ViewModelStore;
import android.arch.lifecycle.ViewModelStoreOwner;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.BaseFragmentActivityApi16;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentController;
import android.support.v4.app.FragmentHostCallback;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManagerNonConfig;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManagerImpl;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.util.SparseArrayCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

public class FragmentActivity
extends BaseFragmentActivityApi16
implements ViewModelStoreOwner,
ActivityCompat.OnRequestPermissionsResultCallback,
ActivityCompat.RequestPermissionsRequestCodeValidator {
    static final String ALLOCATED_REQUEST_INDICIES_TAG = "android:support:request_indicies";
    static final String FRAGMENTS_TAG = "android:support:fragments";
    static final int MAX_NUM_PENDING_FRAGMENT_ACTIVITY_RESULTS = 65534;
    static final int MSG_REALLY_STOPPED = 1;
    static final int MSG_RESUME_PENDING = 2;
    static final String NEXT_CANDIDATE_REQUEST_INDEX_TAG = "android:support:next_request_index";
    static final String REQUEST_FRAGMENT_WHO_TAG = "android:support:request_fragment_who";
    private static final String TAG = "FragmentActivity";
    boolean mCreated;
    final FragmentController mFragments = FragmentController.createController(new HostCallbacks());
    final Handler mHandler = new Handler(){

        public void handleMessage(Message message) {
            switch (message.what) {
                default: {
                    super.handleMessage(message);
                    return;
                }
                case 2: {
                    FragmentActivity.this.onResumeFragments();
                    FragmentActivity.this.mFragments.execPendingActions();
                    return;
                }
                case 1: 
            }
            if (FragmentActivity.this.mStopped) {
                FragmentActivity.this.doReallyStop(false);
            }
        }
    };
    LoaderManager mLoaderManager;
    int mNextCandidateRequestIndex;
    SparseArrayCompat<String> mPendingFragmentActivityResults;
    boolean mReallyStopped = true;
    boolean mRequestedPermissionsFromFragment;
    boolean mResumed;
    boolean mRetaining;
    boolean mStopped = true;
    private ViewModelStore mViewModelStore;

    private int allocateRequestIndex(Fragment fragment) {
        if (this.mPendingFragmentActivityResults.size() >= 65534) {
            throw new IllegalStateException("Too many pending Fragment activity results.");
        }
        while (this.mPendingFragmentActivityResults.indexOfKey(this.mNextCandidateRequestIndex) >= 0) {
            this.mNextCandidateRequestIndex = (this.mNextCandidateRequestIndex + 1) % 65534;
        }
        int n = this.mNextCandidateRequestIndex;
        this.mPendingFragmentActivityResults.put(n, fragment.mWho);
        this.mNextCandidateRequestIndex = (this.mNextCandidateRequestIndex + 1) % 65534;
        return n;
    }

    private void markFragmentsCreated() {
        while (FragmentActivity.markState(this.getSupportFragmentManager(), Lifecycle.State.CREATED)) {
        }
    }

    private static boolean markState(FragmentManager object, Lifecycle.State state) {
        object = object.getFragments().iterator();
        boolean bl = false;
        while (object.hasNext()) {
            Object object2 = (Fragment)object.next();
            if (object2 == null) continue;
            boolean bl2 = bl;
            if (object2.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                object2.mLifecycleRegistry.markState(state);
                bl2 = true;
            }
            object2 = object2.peekChildFragmentManager();
            bl = bl2;
            if (object2 == null) continue;
            bl = bl2 | FragmentActivity.markState((FragmentManager)object2, state);
        }
        return bl;
    }

    @Override
    final View dispatchFragmentsOnCreateView(View view, String string, Context context, AttributeSet attributeSet) {
        return this.mFragments.onCreateView(view, string, context, attributeSet);
    }

    void doReallyStop(boolean bl) {
        if (!this.mReallyStopped) {
            this.mReallyStopped = true;
            this.mRetaining = bl;
            this.mHandler.removeMessages(1);
            this.onReallyStop();
        }
    }

    public void dump(String string, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        super.dump(string, fileDescriptor, printWriter, arrstring);
        printWriter.print(string);
        printWriter.print("Local FragmentActivity ");
        printWriter.print(Integer.toHexString(System.identityHashCode(this)));
        printWriter.println(" State:");
        CharSequence charSequence = new StringBuilder();
        charSequence.append(string);
        charSequence.append("  ");
        charSequence = charSequence.toString();
        printWriter.print((String)charSequence);
        printWriter.print("mCreated=");
        printWriter.print(this.mCreated);
        printWriter.print("mResumed=");
        printWriter.print(this.mResumed);
        printWriter.print(" mStopped=");
        printWriter.print(this.mStopped);
        printWriter.print(" mReallyStopped=");
        printWriter.println(this.mReallyStopped);
        if (this.mLoaderManager != null) {
            this.mLoaderManager.dump((String)charSequence, fileDescriptor, printWriter, arrstring);
        }
        this.mFragments.getSupportFragmentManager().dump(string, fileDescriptor, printWriter, arrstring);
    }

    public Object getLastCustomNonConfigurationInstance() {
        NonConfigurationInstances nonConfigurationInstances = (NonConfigurationInstances)this.getLastNonConfigurationInstance();
        if (nonConfigurationInstances != null) {
            return nonConfigurationInstances.custom;
        }
        return null;
    }

    @Override
    public Lifecycle getLifecycle() {
        return super.getLifecycle();
    }

    public FragmentManager getSupportFragmentManager() {
        return this.mFragments.getSupportFragmentManager();
    }

    public LoaderManager getSupportLoaderManager() {
        if (this.mLoaderManager != null) {
            return this.mLoaderManager;
        }
        this.mLoaderManager = new LoaderManagerImpl(this, this.getViewModelStore());
        return this.mLoaderManager;
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        if (this.getApplication() == null) {
            throw new IllegalStateException("Your activity is not yet attached to the Application instance. You can't request ViewModel before onCreate call.");
        }
        if (this.mViewModelStore == null) {
            this.mViewModelStore = new ViewModelStore();
        }
        return this.mViewModelStore;
    }

    protected void onActivityResult(int n, int n2, Intent object) {
        this.mFragments.noteStateNotSaved();
        int n3 = n >> 16;
        if (n3 != 0) {
            String string = this.mPendingFragmentActivityResults.get(--n3);
            this.mPendingFragmentActivityResults.remove(n3);
            if (string == null) {
                Log.w((String)TAG, (String)"Activity result delivered for unknown Fragment.");
                return;
            }
            Fragment fragment = this.mFragments.findFragmentByWho(string);
            if (fragment == null) {
                object = new StringBuilder();
                object.append("Activity result no fragment exists for who: ");
                object.append(string);
                Log.w((String)TAG, (String)object.toString());
                return;
            }
            fragment.onActivityResult(n & 65535, n2, (Intent)object);
            return;
        }
        ActivityCompat.PermissionCompatDelegate permissionCompatDelegate = ActivityCompat.getPermissionCompatDelegate();
        if (permissionCompatDelegate != null && permissionCompatDelegate.onActivityResult(this, n, n2, (Intent)object)) {
            return;
        }
        super.onActivityResult(n, n2, (Intent)object);
    }

    public void onAttachFragment(Fragment fragment) {
    }

    public void onBackPressed() {
        FragmentManager fragmentManager = this.mFragments.getSupportFragmentManager();
        boolean bl = fragmentManager.isStateSaved();
        if (bl && Build.VERSION.SDK_INT <= 25) {
            return;
        }
        if (bl || !fragmentManager.popBackStackImmediate()) {
            super.onBackPressed();
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mFragments.noteStateNotSaved();
        this.mFragments.dispatchConfigurationChanged(configuration);
    }

    @Override
    protected void onCreate(@Nullable Bundle arrstring) {
        FragmentController fragmentController = this.mFragments;
        int[] arrn = null;
        fragmentController.attachHost(null);
        super.onCreate((Bundle)arrstring);
        NonConfigurationInstances nonConfigurationInstances = (NonConfigurationInstances)this.getLastNonConfigurationInstance();
        if (nonConfigurationInstances != null) {
            this.mViewModelStore = nonConfigurationInstances.viewModelStore;
        }
        if (arrstring != null) {
            fragmentController = arrstring.getParcelable(FRAGMENTS_TAG);
            FragmentController fragmentController2 = this.mFragments;
            if (nonConfigurationInstances != null) {
                arrn = nonConfigurationInstances.fragments;
            }
            fragmentController2.restoreAllState((Parcelable)fragmentController, (FragmentManagerNonConfig)arrn);
            if (arrstring.containsKey(NEXT_CANDIDATE_REQUEST_INDEX_TAG)) {
                this.mNextCandidateRequestIndex = arrstring.getInt(NEXT_CANDIDATE_REQUEST_INDEX_TAG);
                arrn = arrstring.getIntArray(ALLOCATED_REQUEST_INDICIES_TAG);
                arrstring = arrstring.getStringArray(REQUEST_FRAGMENT_WHO_TAG);
                if (arrn != null && arrstring != null && arrn.length == arrstring.length) {
                    this.mPendingFragmentActivityResults = new SparseArrayCompat(arrn.length);
                    for (int i = 0; i < arrn.length; ++i) {
                        this.mPendingFragmentActivityResults.put(arrn[i], arrstring[i]);
                    }
                } else {
                    Log.w((String)TAG, (String)"Invalid requestCode mapping in savedInstanceState.");
                }
            }
        }
        if (this.mPendingFragmentActivityResults == null) {
            this.mPendingFragmentActivityResults = new SparseArrayCompat();
            this.mNextCandidateRequestIndex = 0;
        }
        this.mFragments.dispatchCreate();
    }

    public boolean onCreatePanelMenu(int n, Menu menu) {
        if (n == 0) {
            return super.onCreatePanelMenu(n, menu) | this.mFragments.dispatchCreateOptionsMenu(menu, this.getMenuInflater());
        }
        return super.onCreatePanelMenu(n, menu);
    }

    protected void onDestroy() {
        super.onDestroy();
        this.doReallyStop(false);
        if (this.mViewModelStore != null && !this.mRetaining) {
            this.mViewModelStore.clear();
        }
        this.mFragments.dispatchDestroy();
    }

    public void onLowMemory() {
        super.onLowMemory();
        this.mFragments.dispatchLowMemory();
    }

    public boolean onMenuItemSelected(int n, MenuItem menuItem) {
        if (super.onMenuItemSelected(n, menuItem)) {
            return true;
        }
        if (n != 0) {
            if (n != 6) {
                return false;
            }
            return this.mFragments.dispatchContextItemSelected(menuItem);
        }
        return this.mFragments.dispatchOptionsItemSelected(menuItem);
    }

    @CallSuper
    public void onMultiWindowModeChanged(boolean bl) {
        this.mFragments.dispatchMultiWindowModeChanged(bl);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.mFragments.noteStateNotSaved();
    }

    public void onPanelClosed(int n, Menu menu) {
        if (n == 0) {
            this.mFragments.dispatchOptionsMenuClosed(menu);
        }
        super.onPanelClosed(n, menu);
    }

    protected void onPause() {
        super.onPause();
        this.mResumed = false;
        if (this.mHandler.hasMessages(2)) {
            this.mHandler.removeMessages(2);
            this.onResumeFragments();
        }
        this.mFragments.dispatchPause();
    }

    @CallSuper
    public void onPictureInPictureModeChanged(boolean bl) {
        this.mFragments.dispatchPictureInPictureModeChanged(bl);
    }

    protected void onPostResume() {
        super.onPostResume();
        this.mHandler.removeMessages(2);
        this.onResumeFragments();
        this.mFragments.execPendingActions();
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        return super.onPreparePanel(0, view, menu);
    }

    public boolean onPreparePanel(int n, View view, Menu menu) {
        if (n == 0 && menu != null) {
            return this.onPrepareOptionsPanel(view, menu) | this.mFragments.dispatchPrepareOptionsMenu(menu);
        }
        return super.onPreparePanel(n, view, menu);
    }

    void onReallyStop() {
        this.mFragments.dispatchReallyStop();
    }

    @Override
    public void onRequestPermissionsResult(int n, @NonNull String[] object, @NonNull int[] arrn) {
        this.mFragments.noteStateNotSaved();
        int n2 = n >> 16 & 65535;
        if (n2 != 0) {
            String string = this.mPendingFragmentActivityResults.get(--n2);
            this.mPendingFragmentActivityResults.remove(n2);
            if (string == null) {
                Log.w((String)TAG, (String)"Activity result delivered for unknown Fragment.");
                return;
            }
            Fragment fragment = this.mFragments.findFragmentByWho(string);
            if (fragment == null) {
                object = new StringBuilder();
                object.append("Activity result no fragment exists for who: ");
                object.append(string);
                Log.w((String)TAG, (String)object.toString());
                return;
            }
            fragment.onRequestPermissionsResult(n & 65535, (String[])object, arrn);
        }
    }

    protected void onResume() {
        super.onResume();
        this.mHandler.sendEmptyMessage(2);
        this.mResumed = true;
        this.mFragments.execPendingActions();
    }

    protected void onResumeFragments() {
        this.mFragments.dispatchResume();
    }

    public Object onRetainCustomNonConfigurationInstance() {
        return null;
    }

    public final Object onRetainNonConfigurationInstance() {
        if (this.mStopped) {
            this.doReallyStop(true);
        }
        Object object = this.onRetainCustomNonConfigurationInstance();
        FragmentManagerNonConfig fragmentManagerNonConfig = this.mFragments.retainNestedNonConfig();
        if (fragmentManagerNonConfig == null && this.mViewModelStore == null && object == null) {
            return null;
        }
        NonConfigurationInstances nonConfigurationInstances = new NonConfigurationInstances();
        nonConfigurationInstances.custom = object;
        nonConfigurationInstances.viewModelStore = this.mViewModelStore;
        nonConfigurationInstances.fragments = fragmentManagerNonConfig;
        return nonConfigurationInstances;
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.markFragmentsCreated();
        int[] arrn = this.mFragments.saveAllState();
        if (arrn != null) {
            bundle.putParcelable(FRAGMENTS_TAG, (Parcelable)arrn);
        }
        if (this.mPendingFragmentActivityResults.size() > 0) {
            bundle.putInt(NEXT_CANDIDATE_REQUEST_INDEX_TAG, this.mNextCandidateRequestIndex);
            arrn = new int[this.mPendingFragmentActivityResults.size()];
            String[] arrstring = new String[this.mPendingFragmentActivityResults.size()];
            for (int i = 0; i < this.mPendingFragmentActivityResults.size(); ++i) {
                arrn[i] = this.mPendingFragmentActivityResults.keyAt(i);
                arrstring[i] = this.mPendingFragmentActivityResults.valueAt(i);
            }
            bundle.putIntArray(ALLOCATED_REQUEST_INDICIES_TAG, arrn);
            bundle.putStringArray(REQUEST_FRAGMENT_WHO_TAG, arrstring);
        }
    }

    protected void onStart() {
        super.onStart();
        this.mStopped = false;
        this.mReallyStopped = false;
        this.mHandler.removeMessages(1);
        if (!this.mCreated) {
            this.mCreated = true;
            this.mFragments.dispatchActivityCreated();
        }
        this.mFragments.noteStateNotSaved();
        this.mFragments.execPendingActions();
        this.mFragments.dispatchStart();
    }

    public void onStateNotSaved() {
        this.mFragments.noteStateNotSaved();
    }

    protected void onStop() {
        super.onStop();
        this.mStopped = true;
        this.markFragmentsCreated();
        this.mHandler.sendEmptyMessage(1);
        this.mFragments.dispatchStop();
    }

    void requestPermissionsFromFragment(Fragment fragment, String[] arrstring, int n) {
        if (n == -1) {
            ActivityCompat.requestPermissions(this, arrstring, n);
            return;
        }
        FragmentActivity.checkForValidRequestCode(n);
        try {
            this.mRequestedPermissionsFromFragment = true;
            ActivityCompat.requestPermissions(this, arrstring, (this.allocateRequestIndex(fragment) + 1 << 16) + (n & 65535));
            return;
        }
        finally {
            this.mRequestedPermissionsFromFragment = false;
        }
    }

    public void setEnterSharedElementCallback(SharedElementCallback sharedElementCallback) {
        ActivityCompat.setEnterSharedElementCallback(this, sharedElementCallback);
    }

    public void setExitSharedElementCallback(SharedElementCallback sharedElementCallback) {
        ActivityCompat.setExitSharedElementCallback(this, sharedElementCallback);
    }

    public void startActivityForResult(Intent intent, int n) {
        if (!this.mStartedActivityFromFragment && n != -1) {
            FragmentActivity.checkForValidRequestCode(n);
        }
        super.startActivityForResult(intent, n);
    }

    public void startActivityFromFragment(Fragment fragment, Intent intent, int n) {
        this.startActivityFromFragment(fragment, intent, n, null);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void startActivityFromFragment(Fragment var1_1, Intent var2_3, int var3_4, @Nullable Bundle var4_5) {
        this.mStartedActivityFromFragment = true;
        if (var3_4 != -1) ** GOTO lbl7
        try {
            ActivityCompat.startActivityForResult(this, var2_3, -1, var4_5);
            this.mStartedActivityFromFragment = false;
            return;
lbl7: // 1 sources:
            FragmentActivity.checkForValidRequestCode(var3_4);
            ActivityCompat.startActivityForResult(this, var2_3, (this.allocateRequestIndex(var1_1) + 1 << 16) + (var3_4 & 65535), var4_5);
            this.mStartedActivityFromFragment = false;
            return;
        }
        catch (Throwable var1_2) {}
        this.mStartedActivityFromFragment = false;
        throw var1_2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void startIntentSenderFromFragment(Fragment var1_1, IntentSender var2_3, int var3_4, @Nullable Intent var4_5, int var5_6, int var6_7, int var7_8, Bundle var8_9) throws IntentSender.SendIntentException {
        this.mStartedIntentSenderFromFragment = true;
        if (var3_4 != -1) ** GOTO lbl7
        try {
            ActivityCompat.startIntentSenderForResult(this, var2_3, var3_4, var4_5, var5_6, var6_7, var7_8, var8_9);
            this.mStartedIntentSenderFromFragment = false;
            return;
lbl7: // 1 sources:
            FragmentActivity.checkForValidRequestCode(var3_4);
            ActivityCompat.startIntentSenderForResult(this, var2_3, (this.allocateRequestIndex(var1_1) + 1 << 16) + (65535 & var3_4), var4_5, var5_6, var6_7, var7_8, var8_9);
            this.mStartedIntentSenderFromFragment = false;
            return;
        }
        catch (Throwable var1_2) {}
        this.mStartedIntentSenderFromFragment = false;
        throw var1_2;
    }

    public void supportFinishAfterTransition() {
        ActivityCompat.finishAfterTransition(this);
    }

    @Deprecated
    public void supportInvalidateOptionsMenu() {
        this.invalidateOptionsMenu();
    }

    public void supportPostponeEnterTransition() {
        ActivityCompat.postponeEnterTransition(this);
    }

    public void supportStartPostponedEnterTransition() {
        ActivityCompat.startPostponedEnterTransition(this);
    }

    @Override
    public final void validateRequestPermissionsRequestCode(int n) {
        if (!this.mRequestedPermissionsFromFragment && n != -1) {
            FragmentActivity.checkForValidRequestCode(n);
        }
    }

    class HostCallbacks
    extends FragmentHostCallback<FragmentActivity> {
        public HostCallbacks() {
            super(FragmentActivity.this);
        }

        @Override
        public void onAttachFragment(Fragment fragment) {
            FragmentActivity.this.onAttachFragment(fragment);
        }

        @Override
        public void onDump(String string, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
            FragmentActivity.this.dump(string, fileDescriptor, printWriter, arrstring);
        }

        @Nullable
        @Override
        public View onFindViewById(int n) {
            return FragmentActivity.this.findViewById(n);
        }

        @Override
        public FragmentActivity onGetHost() {
            return FragmentActivity.this;
        }

        @Override
        public LayoutInflater onGetLayoutInflater() {
            return FragmentActivity.this.getLayoutInflater().cloneInContext((Context)FragmentActivity.this);
        }

        @Override
        public int onGetWindowAnimations() {
            Window window = FragmentActivity.this.getWindow();
            if (window == null) {
                return 0;
            }
            return window.getAttributes().windowAnimations;
        }

        @Override
        public boolean onHasView() {
            Window window = FragmentActivity.this.getWindow();
            if (window != null && window.peekDecorView() != null) {
                return true;
            }
            return false;
        }

        @Override
        public boolean onHasWindowAnimations() {
            if (FragmentActivity.this.getWindow() != null) {
                return true;
            }
            return false;
        }

        @Override
        public void onRequestPermissionsFromFragment(@NonNull Fragment fragment, @NonNull String[] arrstring, int n) {
            FragmentActivity.this.requestPermissionsFromFragment(fragment, arrstring, n);
        }

        @Override
        public boolean onShouldSaveFragmentState(Fragment fragment) {
            return FragmentActivity.this.isFinishing() ^ true;
        }

        @Override
        public boolean onShouldShowRequestPermissionRationale(@NonNull String string) {
            return ActivityCompat.shouldShowRequestPermissionRationale(FragmentActivity.this, string);
        }

        @Override
        public void onStartActivityFromFragment(Fragment fragment, Intent intent, int n) {
            FragmentActivity.this.startActivityFromFragment(fragment, intent, n);
        }

        @Override
        public void onStartActivityFromFragment(Fragment fragment, Intent intent, int n, @Nullable Bundle bundle) {
            FragmentActivity.this.startActivityFromFragment(fragment, intent, n, bundle);
        }

        @Override
        public void onStartIntentSenderFromFragment(Fragment fragment, IntentSender intentSender, int n, @Nullable Intent intent, int n2, int n3, int n4, Bundle bundle) throws IntentSender.SendIntentException {
            FragmentActivity.this.startIntentSenderFromFragment(fragment, intentSender, n, intent, n2, n3, n4, bundle);
        }

        @Override
        public void onSupportInvalidateOptionsMenu() {
            FragmentActivity.this.supportInvalidateOptionsMenu();
        }
    }

    static final class NonConfigurationInstances {
        Object custom;
        FragmentManagerNonConfig fragments;
        ViewModelStore viewModelStore;

        NonConfigurationInstances() {
        }
    }

}
