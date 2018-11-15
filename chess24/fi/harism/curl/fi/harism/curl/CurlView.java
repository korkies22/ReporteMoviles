/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.PointF
 *  android.graphics.RectF
 *  android.opengl.GLSurfaceView
 *  android.opengl.GLSurfaceView$Renderer
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 */
package fi.harism.curl;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.RectF;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import fi.harism.curl.CurlMesh;
import fi.harism.curl.CurlPage;
import fi.harism.curl.CurlRenderer;

public class CurlView
extends GLSurfaceView
implements View.OnTouchListener,
CurlRenderer.Observer {
    public static final int CURL_LEFT = 1;
    public static final int CURL_NONE = 0;
    public static final int CURL_RIGHT = 2;
    private static final int SET_CURL_TO_LEFT = 1;
    private static final int SET_CURL_TO_RIGHT = 2;
    public static final int SHOW_ONE_PAGE = 1;
    public static final int SHOW_TWO_PAGES = 2;
    private PageChangeObserver _pageChangeObserver;
    private boolean _requestPagesUpdate;
    private boolean mAllowLastPageCurl = true;
    private boolean mAnimate = false;
    private long mAnimationDurationTime = 300L;
    private PointF mAnimationSource = new PointF();
    private long mAnimationStartTime;
    private PointF mAnimationTarget = new PointF();
    private int mAnimationTargetEvent;
    private PointF mCurlDir = new PointF();
    private PointF mCurlPos = new PointF();
    private int mCurlState = 0;
    private PointF mDragStartPos = new PointF();
    private boolean mEnableTouchPressure = false;
    private int mPageBitmapHeight = -1;
    private int mPageBitmapWidth = -1;
    private CurlMesh mPageCurl;
    private CurlMesh mPageLeft;
    private PageProvider mPageProvider;
    private CurlMesh mPageRight;
    private PointerPosition mPointerPos = new PointerPosition();
    private boolean mRenderLeftPage = false;
    private CurlRenderer mRenderer;
    private SizeChangedObserver mSizeChangedObserver;
    private int mViewMode = 1;

    public CurlView(Context context) {
        super(context);
        this.init(context);
    }

    public CurlView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(context);
    }

    public CurlView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet);
    }

    private void init(Context context) {
        this.mRenderer = new CurlRenderer(this);
        this.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        this.setRenderer((GLSurfaceView.Renderer)this.mRenderer);
        this.setRenderMode(0);
        this.setOnTouchListener((View.OnTouchListener)this);
        this.mPageLeft = new CurlMesh(10);
        this.mPageRight = new CurlMesh(10);
        this.mPageCurl = new CurlMesh(10);
        this.mPageLeft.setFlipTexture(true);
        this.mPageRight.setFlipTexture(false);
    }

    private void setCurlPos(PointF pointF, PointF pointF2, double d) {
        float f;
        if (this.mCurlState != 2 && (this.mCurlState != 1 || this.mViewMode != 1)) {
            if (this.mCurlState == 1) {
                RectF rectF = this.mRenderer.getPageRect(1);
                if (pointF.x <= rectF.left) {
                    this.mPageCurl.reset();
                    this.requestRender();
                    return;
                }
                if (pointF.x > rectF.right) {
                    pointF.x = rectF.right;
                }
                if (pointF2.y != 0.0f) {
                    f = pointF.x;
                    float f2 = rectF.right;
                    f = pointF.y + (f - f2) * pointF2.x / pointF2.y;
                    if (pointF2.y < 0.0f && f < rectF.top) {
                        pointF2.x = rectF.top - pointF.y;
                        pointF2.y = pointF.x - rectF.right;
                    } else if (pointF2.y > 0.0f && f > rectF.bottom) {
                        pointF2.x = pointF.y - rectF.bottom;
                        pointF2.y = rectF.right - pointF.x;
                    }
                }
            }
        } else {
            RectF rectF = this.mRenderer.getPageRect(2);
            if (pointF.x >= rectF.right) {
                this.mPageCurl.reset();
                this.requestRender();
                return;
            }
            if (pointF.x < rectF.left) {
                pointF.x = rectF.left;
            }
            if (pointF2.y != 0.0f) {
                f = pointF.x;
                float f3 = rectF.left;
                f = pointF.y + (f - f3) * pointF2.x / pointF2.y;
                if (pointF2.y < 0.0f && f < rectF.top) {
                    pointF2.x = pointF.y - rectF.top;
                    pointF2.y = rectF.left - pointF.x;
                } else if (pointF2.y > 0.0f && f > rectF.bottom) {
                    pointF2.x = rectF.bottom - pointF.y;
                    pointF2.y = pointF.x - rectF.left;
                }
            }
        }
        if ((f = (float)Math.sqrt(pointF2.x * pointF2.x + pointF2.y * pointF2.y)) != 0.0f) {
            pointF2.x /= f;
            pointF2.y /= f;
            this.mPageCurl.curl(pointF, pointF2, d);
        } else {
            this.mPageCurl.reset();
        }
        this.requestRender();
    }

    private void startCurl(int n) {
        switch (n) {
            default: {
                return;
            }
            case 2: {
                this.mRenderer.removeCurlMesh(this.mPageLeft);
                this.mRenderer.removeCurlMesh(this.mPageRight);
                this.mRenderer.removeCurlMesh(this.mPageCurl);
                CurlMesh curlMesh = this.mPageRight;
                this.mPageRight = this.mPageCurl;
                this.mPageCurl = curlMesh;
                if (!this.mPageProvider.isFirstPage()) {
                    this.mPageLeft.setFlipTexture(true);
                    this.mPageLeft.setRect(this.mRenderer.getPageRect(1));
                    this.mPageLeft.reset();
                    if (this.mRenderLeftPage) {
                        this.mRenderer.addCurlMesh(this.mPageLeft);
                    }
                }
                if (!this.mPageProvider.isLastPage()) {
                    this.updateNextPage(this.mPageRight.getTexturePage());
                    this.mPageRight.setRect(this.mRenderer.getPageRect(2));
                    this.mPageRight.setFlipTexture(false);
                    this.mPageRight.reset();
                    this.mRenderer.addCurlMesh(this.mPageRight);
                }
                this.mPageCurl.setRect(this.mRenderer.getPageRect(2));
                this.mPageCurl.setFlipTexture(false);
                this.mPageCurl.reset();
                this.mRenderer.addCurlMesh(this.mPageCurl);
                this.mCurlState = 2;
                return;
            }
            case 1: 
        }
        this.mRenderer.removeCurlMesh(this.mPageLeft);
        this.mRenderer.removeCurlMesh(this.mPageRight);
        this.mRenderer.removeCurlMesh(this.mPageCurl);
        CurlMesh curlMesh = this.mPageLeft;
        this.mPageLeft = this.mPageCurl;
        this.mPageCurl = curlMesh;
        if (!this.mPageProvider.isFirstPage() && !this.mPageProvider.isSecondPage()) {
            this.updateSecondPreviousPage(this.mPageLeft.getTexturePage());
            this.mPageLeft.setFlipTexture(false);
            this.mPageLeft.setRect(this.mRenderer.getPageRect(1));
            this.mPageLeft.reset();
            if (this.mRenderLeftPage) {
                this.mRenderer.addCurlMesh(this.mPageLeft);
            }
        }
        this.mPageRight.setFlipTexture(false);
        this.mPageRight.setRect(this.mRenderer.getPageRect(2));
        this.mPageRight.reset();
        this.mRenderer.addCurlMesh(this.mPageRight);
        if (this.mViewMode != 1 && (this.mCurlState != 1 || this.mViewMode != 2)) {
            this.mPageCurl.setRect(this.mRenderer.getPageRect(1));
            this.mPageCurl.setFlipTexture(true);
        } else {
            this.mPageCurl.setRect(this.mRenderer.getPageRect(2));
            this.mPageCurl.setFlipTexture(false);
        }
        this.mPageCurl.reset();
        this.mRenderer.addCurlMesh(this.mPageCurl);
        this.mCurlState = 1;
    }

    private void updateAfterAnimation() {
        if (this.mAnimationTargetEvent == 2) {
            CurlMesh curlMesh = this.mPageCurl;
            CurlMesh curlMesh2 = this.mPageRight;
            curlMesh.setRect(this.mRenderer.getPageRect(2));
            curlMesh.setFlipTexture(false);
            curlMesh.reset();
            this.mRenderer.removeCurlMesh(curlMesh2);
            this.mPageCurl = curlMesh2;
            this.mPageRight = curlMesh;
            if (this.mCurlState == 1 && !this.mPageProvider.isFirstPage()) {
                this.updatePreviousPage(this.mPageLeft.getTexturePage());
            }
        } else if (this.mAnimationTargetEvent == 1) {
            CurlMesh curlMesh = this.mPageCurl;
            CurlMesh curlMesh3 = this.mPageLeft;
            curlMesh.setRect(this.mRenderer.getPageRect(1));
            curlMesh.setFlipTexture(true);
            curlMesh.reset();
            this.mRenderer.removeCurlMesh(curlMesh3);
            if (!this.mRenderLeftPage) {
                this.mRenderer.removeCurlMesh(curlMesh);
            }
            this.mPageCurl = curlMesh3;
            this.mPageLeft = curlMesh;
            int n = this.mCurlState;
        }
        this.mCurlState = 0;
        this.mAnimate = false;
        if (this._pageChangeObserver != null) {
            this._pageChangeObserver.curlAnimationFinished();
        }
        if (this._requestPagesUpdate) {
            this._requestPagesUpdate = false;
            this.updatePages();
        }
    }

    private void updateCurlPos(PointerPosition pointerPosition) {
        double d;
        double d2 = (double)(this.mRenderer.getPageRect(2).width() / 3.0f) * (double)Math.max(1.0f - pointerPosition.mPressure, 0.0f);
        this.mCurlPos.set(pointerPosition.mPos);
        if (this.mCurlState != 2 && (this.mCurlState != 1 || this.mViewMode != 2)) {
            d = d2;
            if (this.mCurlState == 1) {
                float f = this.mRenderer.getPageRect((int)2).left;
                d = Math.max(Math.min((double)(this.mCurlPos.x - f), d2), 0.0);
                f = this.mRenderer.getPageRect((int)2).right;
                pointerPosition = this.mCurlPos;
                pointerPosition.x = (float)((double)pointerPosition.x - Math.min((double)(f - this.mCurlPos.x), d));
                this.mCurlDir.x = this.mCurlPos.x + this.mDragStartPos.x;
                this.mCurlDir.y = this.mCurlPos.y - this.mDragStartPos.y;
            }
        } else {
            this.mCurlDir.x = this.mCurlPos.x - this.mDragStartPos.x;
            this.mCurlDir.y = this.mCurlPos.y - this.mDragStartPos.y;
            float f = (float)Math.sqrt(this.mCurlDir.x * this.mCurlDir.x + this.mCurlDir.y * this.mCurlDir.y);
            float f2 = this.mRenderer.getPageRect(2).width();
            double d3 = d2 * 3.141592653589793;
            double d4 = f;
            d = d2;
            d2 = d3;
            if (d4 > (double)(f2 *= 2.0f) - d3) {
                d2 = Math.max(f2 - f, 0.0f);
                d = d2 / 3.141592653589793;
            }
            if (d4 >= d2) {
                d2 = (d4 - d2) / 2.0;
                if (this.mViewMode == 2) {
                    pointerPosition = this.mCurlPos;
                    pointerPosition.x = (float)((double)pointerPosition.x - (double)this.mCurlDir.x * d2 / d4);
                } else {
                    f = this.mRenderer.getPageRect((int)2).left;
                    d = Math.max(Math.min((double)(this.mCurlPos.x - f), d), 0.0);
                }
                pointerPosition = this.mCurlPos;
                pointerPosition.y = (float)((double)pointerPosition.y - (double)this.mCurlDir.y * d2 / d4);
            } else {
                d2 = Math.sin(3.141592653589793 * Math.sqrt(d4 / d2)) * d;
                pointerPosition = this.mCurlPos;
                pointerPosition.x = (float)((double)pointerPosition.x + (double)this.mCurlDir.x * d2 / d4);
                pointerPosition = this.mCurlPos;
                pointerPosition.y = (float)((double)pointerPosition.y + (double)this.mCurlDir.y * d2 / d4);
            }
        }
        this.setCurlPos(this.mCurlPos, this.mCurlDir, d);
    }

    private void updateNextPage(CurlPage curlPage) {
        curlPage.reset();
        this.mPageProvider.getNextPage(curlPage, this.mPageBitmapWidth, this.mPageBitmapHeight);
    }

    private void updatePage(CurlPage curlPage) {
        curlPage.reset();
        this.mPageProvider.getCurrentPage(curlPage, this.mPageBitmapWidth, this.mPageBitmapHeight);
    }

    private void updatePreviousPage(CurlPage curlPage) {
        curlPage.reset();
        this.mPageProvider.getPreviousPage(curlPage, this.mPageBitmapWidth, this.mPageBitmapHeight);
    }

    private void updateSecondPreviousPage(CurlPage curlPage) {
        curlPage.reset();
        this.mPageProvider.getSecondPreviousPage(curlPage, this.mPageBitmapWidth, this.mPageBitmapHeight);
    }

    public void doCurl(boolean bl, boolean bl2) {
        int n = bl ? 1 : 2;
        this.startCurl(n);
        if (this.mCurlState == 1 || this.mCurlState == 2) {
            PointF pointF;
            this.mAnimationStartTime = System.currentTimeMillis();
            RectF rectF = this.mRenderer.getPageRect(2);
            if (bl) {
                pointF = new PointF(rectF.left, (rectF.top + rectF.bottom) / 2.0f);
                rectF = new PointF(rectF.right, (rectF.top + rectF.bottom) / 2.0f);
                this.mAnimationTargetEvent = 2;
            } else {
                pointF = new PointF(rectF.right, (rectF.top + rectF.bottom) / 2.0f);
                rectF = new PointF(rectF.left, (rectF.top + rectF.bottom) / 2.0f);
                this.mAnimationTargetEvent = 1;
            }
            this.mPointerPos.mPos.set(pointF);
            this.mDragStartPos.set(this.mPointerPos.mPos);
            this.mAnimationSource.set(pointF);
            this.mAnimationTarget.set((PointF)rectF);
            this.mAnimate = true;
            this.requestRender();
        }
    }

    public int getCurrentIndex() {
        return 0;
    }

    @Override
    public void onDrawFrame() {
        if (!this.mAnimate) {
            return;
        }
        long l = System.currentTimeMillis();
        if (l >= this.mAnimationStartTime + this.mAnimationDurationTime) {
            this.updateAfterAnimation();
            this.requestRender();
            return;
        }
        this.mPointerPos.mPos.set(this.mAnimationSource);
        float f = 1.0f - (float)(l - this.mAnimationStartTime) / (float)this.mAnimationDurationTime;
        f = 1.0f - f * f * f * (3.0f - 2.0f * f);
        PointF pointF = this.mPointerPos.mPos;
        pointF.x += (this.mAnimationTarget.x - this.mAnimationSource.x) * f;
        pointF = this.mPointerPos.mPos;
        pointF.y += (this.mAnimationTarget.y - this.mAnimationSource.y) * f;
        this.updateCurlPos(this.mPointerPos);
    }

    @Override
    public void onPageSizeChanged(int n, int n2) {
        this.mPageBitmapWidth = n;
        this.mPageBitmapHeight = n2;
        this.updatePages();
        this.requestRender();
    }

    public void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        this.requestRender();
        if (this.mSizeChangedObserver != null) {
            this.mSizeChangedObserver.onSizeChanged(n, n2);
        }
    }

    @Override
    public void onSurfaceCreated() {
        this.mPageLeft.resetTexture();
        this.mPageRight.resetTexture();
        this.mPageCurl.resetTexture();
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean bl = this.mAnimate;
        int n = 0;
        if (!bl) {
            if (this.mPageProvider == null) {
                return false;
            }
            view = this.mRenderer.getPageRect(2);
            RectF rectF = this.mRenderer.getPageRect(1);
            this.mPointerPos.mPos.set(motionEvent.getX(), motionEvent.getY());
            this.mRenderer.translate(this.mPointerPos.mPos);
            this.mPointerPos.mPressure = this.mEnableTouchPressure ? motionEvent.getPressure() : 0.8f;
            switch (motionEvent.getAction()) {
                default: {
                    return true;
                }
                case 2: {
                    this.updateCurlPos(this.mPointerPos);
                    return true;
                }
                case 1: 
                case 3: {
                    if (this.mCurlState != 1 && this.mCurlState != 2) break;
                    this.mAnimationSource.set(this.mPointerPos.mPos);
                    this.mAnimationStartTime = System.currentTimeMillis();
                    if (this.mViewMode == 1 && this.mPointerPos.mPos.x > (view.left + view.right) / 2.0f || this.mViewMode == 2 && this.mPointerPos.mPos.x > view.left) {
                        this.mAnimationTarget.set(this.mDragStartPos);
                        this.mAnimationTarget.x = this.mRenderer.getPageRect((int)2).right;
                        this.mAnimationTargetEvent = 2;
                    } else {
                        this.mAnimationTarget.set(this.mDragStartPos);
                        this.mAnimationTarget.x = this.mCurlState != 2 && this.mViewMode != 2 ? view.left : view.left;
                        this.mAnimationTargetEvent = 1;
                    }
                    this.mAnimate = true;
                    if (this._pageChangeObserver != null) {
                        int n2 = n;
                        if (this.mAnimationTargetEvent == 1) {
                            n2 = n;
                            if (this.mCurlState == 2) {
                                n2 = 1;
                            }
                        }
                        n = n2;
                        if (this.mAnimationTargetEvent == 2) {
                            n = n2;
                            if (this.mCurlState == 1) {
                                n = -1;
                            }
                        }
                        this._pageChangeObserver.curlAnimationStarted(n);
                    }
                    this.requestRender();
                    return true;
                }
                case 0: {
                    this.mDragStartPos.set(this.mPointerPos.mPos);
                    if (this.mDragStartPos.y > view.top) {
                        this.mDragStartPos.y = view.top;
                    } else if (this.mDragStartPos.y < view.bottom) {
                        this.mDragStartPos.y = view.bottom;
                    }
                    if (this.mViewMode == 2) {
                        if (this.mDragStartPos.x < view.left && !this.mPageProvider.isFirstPage()) {
                            this.mDragStartPos.x = rectF.left;
                            this.startCurl(1);
                        } else if (this.mDragStartPos.x >= view.left && !this.mPageProvider.isLastPage()) {
                            this.mDragStartPos.x = view.right;
                            if (!this.mAllowLastPageCurl && this.mPageProvider.isLastPage()) {
                                return false;
                            }
                            this.startCurl(2);
                        }
                    } else if (this.mViewMode == 1) {
                        float f = (view.right + view.left) / 2.0f;
                        if (this.mDragStartPos.x < f && !this.mPageProvider.isFirstPage()) {
                            this.mDragStartPos.x = view.left;
                            this.startCurl(1);
                        } else if (this.mDragStartPos.x >= f && !this.mPageProvider.isLastPage()) {
                            this.mDragStartPos.x = view.right;
                            if (!this.mAllowLastPageCurl && this.mPageProvider.isLastPage()) {
                                return false;
                            }
                            this.startCurl(2);
                        }
                    }
                    if (this.mCurlState != 0) break;
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void setAllowLastPageCurl(boolean bl) {
        this.mAllowLastPageCurl = bl;
    }

    public void setBackgroundColor(int n) {
        this.mRenderer.setBackgroundColor(n);
        this.requestRender();
    }

    public void setCurrentIndex(int n) {
        this.updatePages();
        this.requestRender();
    }

    public void setEnableTouchPressure(boolean bl) {
        this.mEnableTouchPressure = bl;
    }

    public void setMargins(float f, float f2, float f3, float f4) {
        this.mRenderer.setMargins(f, f2, f3, f4);
    }

    public void setPageChangeObserver(PageChangeObserver pageChangeObserver) {
        this._pageChangeObserver = pageChangeObserver;
    }

    public void setPageProvider(PageProvider pageProvider) {
        this.mPageProvider = pageProvider;
        this.updatePages();
        this.requestRender();
    }

    public void setRenderLeftPage(boolean bl) {
        this.mRenderLeftPage = bl;
    }

    public void setSizeChangedObserver(SizeChangedObserver sizeChangedObserver) {
        this.mSizeChangedObserver = sizeChangedObserver;
    }

    public void setViewMode(int n) {
        switch (n) {
            default: {
                return;
            }
            case 2: {
                this.mViewMode = n;
                this.mPageLeft.setFlipTexture(false);
                this.mRenderer.setViewMode(2);
                return;
            }
            case 1: 
        }
        this.mViewMode = n;
        this.mPageLeft.setFlipTexture(true);
        this.mRenderer.setViewMode(1);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void updatePages() {
        if (!this.mAnimate) {
            if (this.mPageProvider == null || this.mPageBitmapWidth <= 0) return;
            if (this.mPageBitmapHeight <= 0) {
                return;
            }
            this.mRenderer.removeCurlMesh(this.mPageLeft);
            this.mRenderer.removeCurlMesh(this.mPageRight);
            this.mRenderer.removeCurlMesh(this.mPageCurl);
            if (!this.mPageProvider.isLastPage()) {
                this.updatePage(this.mPageRight.getTexturePage());
                this.mPageRight.setFlipTexture(false);
                this.mPageRight.setRect(this.mRenderer.getPageRect(2));
                this.mPageRight.reset();
                this.mRenderer.addCurlMesh(this.mPageRight);
            }
            if (this.mPageProvider.isFirstPage()) return;
            this.updatePreviousPage(this.mPageLeft.getTexturePage());
            this.mPageLeft.setFlipTexture(true);
            this.mPageLeft.setRect(this.mRenderer.getPageRect(1));
            this.mPageLeft.reset();
            if (!this.mRenderLeftPage) return;
            this.mRenderer.addCurlMesh(this.mPageLeft);
            return;
        }
        this._requestPagesUpdate = true;
    }

    public static interface PageChangeObserver {
        public static final int PAGE_CHANGE_NEXT_PAGE = 1;
        public static final int PAGE_CHANGE_NO_CHANGE = 0;
        public static final int PAGE_CHANGE_PREVIOUS_PAGE = -1;

        public void curlAnimationFinished();

        public void curlAnimationStarted(int var1);
    }

    public static interface PageProvider {
        public void getCurrentPage(CurlPage var1, int var2, int var3);

        public void getNextPage(CurlPage var1, int var2, int var3);

        public void getPreviousPage(CurlPage var1, int var2, int var3);

        public void getSecondPreviousPage(CurlPage var1, int var2, int var3);

        public boolean isFirstPage();

        public boolean isLastPage();

        public boolean isSecondLastPage();

        public boolean isSecondPage();
    }

    private class PointerPosition {
        PointF mPos = new PointF();
        float mPressure;

        private PointerPosition() {
        }
    }

    public static interface SizeChangedObserver {
        public void onSizeChanged(int var1, int var2);
    }

}
