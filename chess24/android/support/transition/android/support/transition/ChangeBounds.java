/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.AnimatorSet
 *  android.animation.ObjectAnimator
 *  android.animation.PropertyValuesHolder
 *  android.animation.TypeEvaluator
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.content.res.XmlResourceParser
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Path
 *  android.graphics.PointF
 *  android.graphics.Rect
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.util.Property
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  org.xmlpull.v1.XmlPullParser
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.ObjectAnimatorUtils;
import android.support.transition.PathMotion;
import android.support.transition.PropertyValuesHolderUtils;
import android.support.transition.RectEvaluator;
import android.support.transition.Styleable;
import android.support.transition.Transition;
import android.support.transition.TransitionListenerAdapter;
import android.support.transition.TransitionUtils;
import android.support.transition.TransitionValues;
import android.support.transition.ViewGroupUtils;
import android.support.transition.ViewUtils;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;

public class ChangeBounds
extends Transition {
    private static final Property<View, PointF> BOTTOM_RIGHT_ONLY_PROPERTY;
    private static final Property<ViewBounds, PointF> BOTTOM_RIGHT_PROPERTY;
    private static final Property<Drawable, PointF> DRAWABLE_ORIGIN_PROPERTY;
    private static final Property<View, PointF> POSITION_PROPERTY;
    private static final String PROPNAME_BOUNDS = "android:changeBounds:bounds";
    private static final String PROPNAME_CLIP = "android:changeBounds:clip";
    private static final String PROPNAME_PARENT = "android:changeBounds:parent";
    private static final String PROPNAME_WINDOW_X = "android:changeBounds:windowX";
    private static final String PROPNAME_WINDOW_Y = "android:changeBounds:windowY";
    private static final Property<View, PointF> TOP_LEFT_ONLY_PROPERTY;
    private static final Property<ViewBounds, PointF> TOP_LEFT_PROPERTY;
    private static RectEvaluator sRectEvaluator;
    private static final String[] sTransitionProperties;
    private boolean mReparent = false;
    private boolean mResizeClip = false;
    private int[] mTempLocation = new int[2];

    static {
        sTransitionProperties = new String[]{PROPNAME_BOUNDS, PROPNAME_CLIP, PROPNAME_PARENT, PROPNAME_WINDOW_X, PROPNAME_WINDOW_Y};
        DRAWABLE_ORIGIN_PROPERTY = new Property<Drawable, PointF>(PointF.class, "boundsOrigin"){
            private Rect mBounds = new Rect();

            public PointF get(Drawable drawable) {
                drawable.copyBounds(this.mBounds);
                return new PointF((float)this.mBounds.left, (float)this.mBounds.top);
            }

            public void set(Drawable drawable, PointF pointF) {
                drawable.copyBounds(this.mBounds);
                this.mBounds.offsetTo(Math.round(pointF.x), Math.round(pointF.y));
                drawable.setBounds(this.mBounds);
            }
        };
        TOP_LEFT_PROPERTY = new Property<ViewBounds, PointF>(PointF.class, "topLeft"){

            public PointF get(ViewBounds viewBounds) {
                return null;
            }

            public void set(ViewBounds viewBounds, PointF pointF) {
                viewBounds.setTopLeft(pointF);
            }
        };
        BOTTOM_RIGHT_PROPERTY = new Property<ViewBounds, PointF>(PointF.class, "bottomRight"){

            public PointF get(ViewBounds viewBounds) {
                return null;
            }

            public void set(ViewBounds viewBounds, PointF pointF) {
                viewBounds.setBottomRight(pointF);
            }
        };
        BOTTOM_RIGHT_ONLY_PROPERTY = new Property<View, PointF>(PointF.class, "bottomRight"){

            public PointF get(View view) {
                return null;
            }

            public void set(View view, PointF pointF) {
                ViewUtils.setLeftTopRightBottom(view, view.getLeft(), view.getTop(), Math.round(pointF.x), Math.round(pointF.y));
            }
        };
        TOP_LEFT_ONLY_PROPERTY = new Property<View, PointF>(PointF.class, "topLeft"){

            public PointF get(View view) {
                return null;
            }

            public void set(View view, PointF pointF) {
                ViewUtils.setLeftTopRightBottom(view, Math.round(pointF.x), Math.round(pointF.y), view.getRight(), view.getBottom());
            }
        };
        POSITION_PROPERTY = new Property<View, PointF>(PointF.class, "position"){

            public PointF get(View view) {
                return null;
            }

            public void set(View view, PointF pointF) {
                int n = Math.round(pointF.x);
                int n2 = Math.round(pointF.y);
                ViewUtils.setLeftTopRightBottom(view, n, n2, view.getWidth() + n, view.getHeight() + n2);
            }
        };
        sRectEvaluator = new RectEvaluator();
    }

    public ChangeBounds() {
    }

    public ChangeBounds(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, Styleable.CHANGE_BOUNDS);
        boolean bl = TypedArrayUtils.getNamedBoolean((TypedArray)context, (XmlPullParser)((XmlResourceParser)attributeSet), "resizeClip", 0, false);
        context.recycle();
        this.setResizeClip(bl);
    }

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (ViewCompat.isLaidOut(view) || view.getWidth() != 0 || view.getHeight() != 0) {
            transitionValues.values.put(PROPNAME_BOUNDS, (Object)new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
            transitionValues.values.put(PROPNAME_PARENT, (Object)transitionValues.view.getParent());
            if (this.mReparent) {
                transitionValues.view.getLocationInWindow(this.mTempLocation);
                transitionValues.values.put(PROPNAME_WINDOW_X, this.mTempLocation[0]);
                transitionValues.values.put(PROPNAME_WINDOW_Y, this.mTempLocation[1]);
            }
            if (this.mResizeClip) {
                transitionValues.values.put(PROPNAME_CLIP, (Object)ViewCompat.getClipBounds(view));
            }
        }
    }

    private boolean parentMatches(View view, View view2) {
        if (this.mReparent) {
            TransitionValues transitionValues = this.getMatchedTransitionValues(view, true);
            if (transitionValues == null ? view == view2 : view2 == transitionValues.view) {
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Nullable
    @Override
    public Animator createAnimator(final @NonNull ViewGroup viewGroup, @Nullable TransitionValues object, @Nullable TransitionValues transitionValues) {
        block27 : {
            block28 : {
                int n;
                int n2;
                int n3;
                View view;
                int n4;
                block36 : {
                    block35 : {
                        block29 : {
                            int n5;
                            int n6;
                            int n7;
                            int n8;
                            int n9;
                            int n10;
                            int n11;
                            int n12;
                            int n13;
                            int n14;
                            int n15;
                            ViewGroup viewGroup2;
                            int n16;
                            ViewGroup viewGroup3;
                            int n17;
                            block34 : {
                                int n18;
                                block33 : {
                                    block32 : {
                                        block30 : {
                                            block31 : {
                                                if (object == null || transitionValues == null) break block27;
                                                viewGroup2 = object.values;
                                                view = transitionValues.values;
                                                viewGroup2 = (ViewGroup)viewGroup2.get(PROPNAME_PARENT);
                                                viewGroup3 = (ViewGroup)view.get(PROPNAME_PARENT);
                                                if (viewGroup2 == null || viewGroup3 == null) break block28;
                                                view = transitionValues.view;
                                                if (!this.parentMatches((View)viewGroup2, (View)viewGroup3)) break block29;
                                                viewGroup = (Rect)object.values.get(PROPNAME_BOUNDS);
                                                viewGroup2 = (Rect)transitionValues.values.get(PROPNAME_BOUNDS);
                                                n15 = viewGroup.left;
                                                n14 = viewGroup2.left;
                                                n13 = viewGroup.top;
                                                n17 = viewGroup2.top;
                                                n6 = viewGroup.right;
                                                n8 = viewGroup2.right;
                                                n10 = viewGroup.bottom;
                                                n12 = viewGroup2.bottom;
                                                n7 = n6 - n15;
                                                n16 = n10 - n13;
                                                n11 = n8 - n14;
                                                n9 = n12 - n17;
                                                object = (Rect)object.values.get(PROPNAME_CLIP);
                                                viewGroup2 = (Rect)transitionValues.values.get(PROPNAME_CLIP);
                                                if ((n7 == 0 || n16 == 0) && (n11 == 0 || n9 == 0)) break block30;
                                                n5 = n15 == n14 && n13 == n17 ? 0 : 1;
                                                if (n6 != n8) break block31;
                                                n18 = n5;
                                                if (n10 == n12) break block32;
                                            }
                                            n18 = n5 + 1;
                                            break block32;
                                        }
                                        n18 = 0;
                                    }
                                    if (object != null && !object.equals((Object)viewGroup2)) break block33;
                                    n5 = n18;
                                    if (object != null) break block34;
                                    n5 = n18;
                                    if (viewGroup2 == null) break block34;
                                }
                                n5 = n18 + 1;
                            }
                            if (n5 > 0) {
                                if (!this.mResizeClip) {
                                    viewGroup = view;
                                    ViewUtils.setLeftTopRightBottom((View)viewGroup, n15, n13, n6, n10);
                                    if (n5 == 2) {
                                        if (n7 == n11 && n16 == n9) {
                                            object = this.getPathMotion().getPath(n15, n13, n14, n17);
                                            viewGroup = ObjectAnimatorUtils.ofPointF(viewGroup, POSITION_PROPERTY, (Path)object);
                                        } else {
                                            object = new ViewBounds((View)viewGroup);
                                            viewGroup = this.getPathMotion().getPath(n15, n13, n14, n17);
                                            transitionValues = ObjectAnimatorUtils.ofPointF(object, TOP_LEFT_PROPERTY, (Path)viewGroup);
                                            viewGroup = this.getPathMotion().getPath(n6, n10, n8, n12);
                                            viewGroup2 = ObjectAnimatorUtils.ofPointF(object, BOTTOM_RIGHT_PROPERTY, (Path)viewGroup);
                                            viewGroup = new AnimatorSet();
                                            viewGroup.playTogether(new Animator[]{transitionValues, viewGroup2});
                                            viewGroup.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter((ViewBounds)object){
                                                private ViewBounds mViewBounds;
                                                final /* synthetic */ ViewBounds val$viewBounds;
                                                {
                                                    this.val$viewBounds = viewBounds;
                                                    this.mViewBounds = this.val$viewBounds;
                                                }
                                            });
                                        }
                                    } else if (n15 == n14 && n13 == n17) {
                                        object = this.getPathMotion().getPath(n6, n10, n8, n12);
                                        viewGroup = ObjectAnimatorUtils.ofPointF(viewGroup, BOTTOM_RIGHT_ONLY_PROPERTY, (Path)object);
                                    } else {
                                        object = this.getPathMotion().getPath(n15, n13, n14, n17);
                                        viewGroup = ObjectAnimatorUtils.ofPointF(viewGroup, TOP_LEFT_ONLY_PROPERTY, (Path)object);
                                    }
                                } else {
                                    viewGroup3 = view;
                                    ViewUtils.setLeftTopRightBottom((View)viewGroup3, n15, n13, Math.max(n7, n11) + n15, Math.max(n16, n9) + n13);
                                    if (n15 == n14 && n13 == n17) {
                                        viewGroup = null;
                                    } else {
                                        viewGroup = this.getPathMotion().getPath(n15, n13, n14, n17);
                                        viewGroup = ObjectAnimatorUtils.ofPointF(viewGroup3, POSITION_PROPERTY, (Path)viewGroup);
                                    }
                                    if (object == null) {
                                        object = new Rect(0, 0, n7, n16);
                                    }
                                    transitionValues = viewGroup2 == null ? new Rect(0, 0, n11, n9) : viewGroup2;
                                    if (!object.equals((Object)transitionValues)) {
                                        ViewCompat.setClipBounds((View)viewGroup3, (Rect)object);
                                        object = ObjectAnimator.ofObject((Object)viewGroup3, (String)"clipBounds", (TypeEvaluator)sRectEvaluator, (Object[])new Object[]{object, transitionValues});
                                        object.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter((View)viewGroup3, (Rect)viewGroup2, n14, n17, n8, n12){
                                            private boolean mIsCanceled;
                                            final /* synthetic */ int val$endBottom;
                                            final /* synthetic */ int val$endLeft;
                                            final /* synthetic */ int val$endRight;
                                            final /* synthetic */ int val$endTop;
                                            final /* synthetic */ Rect val$finalClip;
                                            final /* synthetic */ View val$view;
                                            {
                                                this.val$view = view;
                                                this.val$finalClip = rect;
                                                this.val$endLeft = n;
                                                this.val$endTop = n2;
                                                this.val$endRight = n3;
                                                this.val$endBottom = n4;
                                            }

                                            public void onAnimationCancel(Animator animator) {
                                                this.mIsCanceled = true;
                                            }

                                            public void onAnimationEnd(Animator animator) {
                                                if (!this.mIsCanceled) {
                                                    ViewCompat.setClipBounds(this.val$view, this.val$finalClip);
                                                    ViewUtils.setLeftTopRightBottom(this.val$view, this.val$endLeft, this.val$endTop, this.val$endRight, this.val$endBottom);
                                                }
                                            }
                                        });
                                    } else {
                                        object = null;
                                    }
                                    viewGroup = TransitionUtils.mergeAnimators((Animator)viewGroup, (Animator)object);
                                }
                                if (view.getParent() instanceof ViewGroup) {
                                    object = (ViewGroup)view.getParent();
                                    ViewGroupUtils.suppressLayout((ViewGroup)object, true);
                                    this.addListener(new TransitionListenerAdapter((ViewGroup)object){
                                        boolean mCanceled = false;
                                        final /* synthetic */ ViewGroup val$parent;
                                        {
                                            this.val$parent = viewGroup;
                                        }

                                        @Override
                                        public void onTransitionCancel(@NonNull Transition transition) {
                                            ViewGroupUtils.suppressLayout(this.val$parent, false);
                                            this.mCanceled = true;
                                        }

                                        @Override
                                        public void onTransitionEnd(@NonNull Transition transition) {
                                            if (!this.mCanceled) {
                                                ViewGroupUtils.suppressLayout(this.val$parent, false);
                                            }
                                            transition.removeListener(this);
                                        }

                                        @Override
                                        public void onTransitionPause(@NonNull Transition transition) {
                                            ViewGroupUtils.suppressLayout(this.val$parent, false);
                                        }

                                        @Override
                                        public void onTransitionResume(@NonNull Transition transition) {
                                            ViewGroupUtils.suppressLayout(this.val$parent, true);
                                        }
                                    });
                                }
                                return viewGroup;
                            }
                            break block35;
                        }
                        n3 = (Integer)object.values.get(PROPNAME_WINDOW_X);
                        n = (Integer)object.values.get(PROPNAME_WINDOW_Y);
                        n4 = (Integer)transitionValues.values.get(PROPNAME_WINDOW_X);
                        n2 = (Integer)transitionValues.values.get(PROPNAME_WINDOW_Y);
                        if (n3 != n4 || n != n2) break block36;
                    }
                    return null;
                }
                viewGroup.getLocationInWindow(this.mTempLocation);
                object = Bitmap.createBitmap((int)view.getWidth(), (int)view.getHeight(), (Bitmap.Config)Bitmap.Config.ARGB_8888);
                view.draw(new Canvas((Bitmap)object));
                object = new BitmapDrawable((Bitmap)object);
                float f = ViewUtils.getTransitionAlpha(view);
                ViewUtils.setTransitionAlpha(view, 0.0f);
                ViewUtils.getOverlay((View)viewGroup).add((Drawable)object);
                transitionValues = this.getPathMotion().getPath(n3 - this.mTempLocation[0], n - this.mTempLocation[1], n4 - this.mTempLocation[0], n2 - this.mTempLocation[1]);
                transitionValues = ObjectAnimator.ofPropertyValuesHolder((Object)object, (PropertyValuesHolder[])new PropertyValuesHolder[]{PropertyValuesHolderUtils.ofPointF(DRAWABLE_ORIGIN_PROPERTY, (Path)transitionValues)});
                transitionValues.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter((BitmapDrawable)object, view, f){
                    final /* synthetic */ BitmapDrawable val$drawable;
                    final /* synthetic */ float val$transitionAlpha;
                    final /* synthetic */ View val$view;
                    {
                        this.val$drawable = bitmapDrawable;
                        this.val$view = view;
                        this.val$transitionAlpha = f;
                    }

                    public void onAnimationEnd(Animator animator) {
                        ViewUtils.getOverlay((View)viewGroup).remove((Drawable)this.val$drawable);
                        ViewUtils.setTransitionAlpha(this.val$view, this.val$transitionAlpha);
                    }
                });
                return transitionValues;
            }
            return null;
        }
        return null;
    }

    public boolean getResizeClip() {
        return this.mResizeClip;
    }

    @Nullable
    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public void setResizeClip(boolean bl) {
        this.mResizeClip = bl;
    }

    private static class ViewBounds {
        private int mBottom;
        private int mBottomRightCalls;
        private int mLeft;
        private int mRight;
        private int mTop;
        private int mTopLeftCalls;
        private View mView;

        ViewBounds(View view) {
            this.mView = view;
        }

        private void setLeftTopRightBottom() {
            ViewUtils.setLeftTopRightBottom(this.mView, this.mLeft, this.mTop, this.mRight, this.mBottom);
            this.mTopLeftCalls = 0;
            this.mBottomRightCalls = 0;
        }

        void setBottomRight(PointF pointF) {
            this.mRight = Math.round(pointF.x);
            this.mBottom = Math.round(pointF.y);
            ++this.mBottomRightCalls;
            if (this.mTopLeftCalls == this.mBottomRightCalls) {
                this.setLeftTopRightBottom();
            }
        }

        void setTopLeft(PointF pointF) {
            this.mLeft = Math.round(pointF.x);
            this.mTop = Math.round(pointF.y);
            ++this.mTopLeftCalls;
            if (this.mTopLeftCalls == this.mBottomRightCalls) {
                this.setLeftTopRightBottom();
            }
        }
    }

}
