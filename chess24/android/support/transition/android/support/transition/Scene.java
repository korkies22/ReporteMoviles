/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.SparseArray
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.transition;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.R;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Scene {
    private Context mContext;
    private Runnable mEnterAction;
    private Runnable mExitAction;
    private View mLayout;
    private int mLayoutId = -1;
    private ViewGroup mSceneRoot;

    public Scene(@NonNull ViewGroup viewGroup) {
        this.mSceneRoot = viewGroup;
    }

    private Scene(ViewGroup viewGroup, int n, Context context) {
        this.mContext = context;
        this.mSceneRoot = viewGroup;
        this.mLayoutId = n;
    }

    public Scene(@NonNull ViewGroup viewGroup, @NonNull View view) {
        this.mSceneRoot = viewGroup;
        this.mLayout = view;
    }

    static Scene getCurrentScene(View view) {
        return (Scene)view.getTag(R.id.transition_current_scene);
    }

    @NonNull
    public static Scene getSceneForLayout(@NonNull ViewGroup object, @LayoutRes int n, @NonNull Context context) {
        Object object2;
        SparseArray sparseArray = object2 = (SparseArray)object.getTag(R.id.transition_scene_layoutid_cache);
        if (object2 == null) {
            sparseArray = new SparseArray();
            object.setTag(R.id.transition_scene_layoutid_cache, (Object)sparseArray);
        }
        if ((object2 = (Scene)sparseArray.get(n)) != null) {
            return object2;
        }
        object = new Scene((ViewGroup)object, n, context);
        sparseArray.put(n, object);
        return object;
    }

    static void setCurrentScene(View view, Scene scene) {
        view.setTag(R.id.transition_current_scene, (Object)scene);
    }

    public void enter() {
        if (this.mLayoutId > 0 || this.mLayout != null) {
            this.getSceneRoot().removeAllViews();
            if (this.mLayoutId > 0) {
                LayoutInflater.from((Context)this.mContext).inflate(this.mLayoutId, this.mSceneRoot);
            } else {
                this.mSceneRoot.addView(this.mLayout);
            }
        }
        if (this.mEnterAction != null) {
            this.mEnterAction.run();
        }
        Scene.setCurrentScene((View)this.mSceneRoot, this);
    }

    public void exit() {
        if (Scene.getCurrentScene((View)this.mSceneRoot) == this && this.mExitAction != null) {
            this.mExitAction.run();
        }
    }

    @NonNull
    public ViewGroup getSceneRoot() {
        return this.mSceneRoot;
    }

    boolean isCreatedFromLayoutResource() {
        if (this.mLayoutId > 0) {
            return true;
        }
        return false;
    }

    public void setEnterAction(@Nullable Runnable runnable) {
        this.mEnterAction = runnable;
    }

    public void setExitAction(@Nullable Runnable runnable) {
        this.mExitAction = runnable;
    }
}
