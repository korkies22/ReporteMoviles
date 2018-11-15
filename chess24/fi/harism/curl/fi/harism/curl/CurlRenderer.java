/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 *  android.graphics.PointF
 *  android.graphics.RectF
 *  android.opengl.GLSurfaceView
 *  android.opengl.GLSurfaceView$Renderer
 *  android.opengl.GLU
 *  javax.microedition.khronos.egl.EGLConfig
 *  javax.microedition.khronos.opengles.GL10
 */
package fi.harism.curl;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import fi.harism.curl.CurlMesh;
import java.util.Vector;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CurlRenderer
implements GLSurfaceView.Renderer {
    public static final int PAGE_LEFT = 1;
    public static final int PAGE_RIGHT = 2;
    public static final int SHOW_ONE_PAGE = 1;
    public static final int SHOW_TWO_PAGES = 2;
    private static final boolean USE_PERSPECTIVE_PROJECTION = false;
    private int mBackgroundColor;
    private Vector<CurlMesh> mCurlMeshes;
    private RectF mMargins = new RectF();
    private Observer mObserver;
    private RectF mPageRectLeft;
    private RectF mPageRectRight;
    private int mViewMode = 1;
    private RectF mViewRect = new RectF();
    private int mViewportHeight;
    private int mViewportWidth;

    public CurlRenderer(Observer observer) {
        this.mObserver = observer;
        this.mCurlMeshes = new Vector();
        this.mPageRectLeft = new RectF();
        this.mPageRectRight = new RectF();
    }

    private void updatePageRects() {
        if (this.mViewRect.width() != 0.0f) {
            if (this.mViewRect.height() == 0.0f) {
                return;
            }
            if (this.mViewMode == 1) {
                this.mPageRectRight.set(this.mViewRect);
                RectF rectF = this.mPageRectRight;
                rectF.left += this.mViewRect.width() * this.mMargins.left;
                rectF = this.mPageRectRight;
                rectF.right -= this.mViewRect.width() * this.mMargins.right;
                rectF = this.mPageRectRight;
                rectF.top += this.mViewRect.height() * this.mMargins.top;
                rectF = this.mPageRectRight;
                rectF.bottom -= this.mViewRect.height() * this.mMargins.bottom;
                this.mPageRectLeft.set(this.mPageRectRight);
                this.mPageRectLeft.offset(- this.mPageRectRight.width(), 0.0f);
                int n = (int)(this.mPageRectRight.width() * (float)this.mViewportWidth / this.mViewRect.width());
                int n2 = (int)(this.mPageRectRight.height() * (float)this.mViewportHeight / this.mViewRect.height());
                this.mObserver.onPageSizeChanged(n, n2);
                return;
            }
            if (this.mViewMode == 2) {
                this.mPageRectRight.set(this.mViewRect);
                RectF rectF = this.mPageRectRight;
                rectF.left += this.mViewRect.width() * this.mMargins.left;
                rectF = this.mPageRectRight;
                rectF.right -= this.mViewRect.width() * this.mMargins.right;
                rectF = this.mPageRectRight;
                rectF.top += this.mViewRect.height() * this.mMargins.top;
                rectF = this.mPageRectRight;
                rectF.bottom -= this.mViewRect.height() * this.mMargins.bottom;
                this.mPageRectLeft.set(this.mPageRectRight);
                this.mPageRectRight.left = this.mPageRectLeft.right = (this.mPageRectLeft.right + this.mPageRectLeft.left) / 2.0f;
                int n = (int)(this.mPageRectRight.width() * (float)this.mViewportWidth / this.mViewRect.width());
                int n3 = (int)(this.mPageRectRight.height() * (float)this.mViewportHeight / this.mViewRect.height());
                this.mObserver.onPageSizeChanged(n, n3);
            }
            return;
        }
    }

    public void addCurlMesh(CurlMesh curlMesh) {
        synchronized (this) {
            this.removeCurlMesh(curlMesh);
            this.mCurlMeshes.add(curlMesh);
            return;
        }
    }

    public RectF getPageRect(int n) {
        if (n == 1) {
            return this.mPageRectLeft;
        }
        if (n == 2) {
            return this.mPageRectRight;
        }
        return null;
    }

    public void onDrawFrame(GL10 gL10) {
        synchronized (this) {
            gL10.glHint(3152, 4354);
            gL10.glDisable(2929);
            gL10.glDisable(2884);
            this.mObserver.onDrawFrame();
            gL10.glClearColor((float)Color.red((int)this.mBackgroundColor) / 255.0f, (float)Color.green((int)this.mBackgroundColor) / 255.0f, (float)Color.blue((int)this.mBackgroundColor) / 255.0f, (float)Color.alpha((int)this.mBackgroundColor) / 255.0f);
            gL10.glClear(16384);
            gL10.glLoadIdentity();
            int n = 0;
            do {
                if (n >= this.mCurlMeshes.size()) break;
                this.mCurlMeshes.get(n).onDrawFrame(gL10);
                ++n;
            } while (true);
            return;
        }
    }

    public void onSurfaceChanged(GL10 gL10, int n, int n2) {
        gL10.glViewport(0, 0, n, n2);
        this.mViewportWidth = n;
        this.mViewportHeight = n2;
        float f = (float)n / (float)n2;
        this.mViewRect.top = 1.0f;
        this.mViewRect.bottom = -1.0f;
        this.mViewRect.left = - f;
        this.mViewRect.right = f;
        this.updatePageRects();
        gL10.glMatrixMode(5889);
        gL10.glLoadIdentity();
        GLU.gluOrtho2D((GL10)gL10, (float)this.mViewRect.left, (float)this.mViewRect.right, (float)this.mViewRect.bottom, (float)this.mViewRect.top);
        gL10.glMatrixMode(5888);
        gL10.glLoadIdentity();
    }

    public void onSurfaceCreated(GL10 gL10, EGLConfig eGLConfig) {
        gL10.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gL10.glShadeModel(7425);
        gL10.glHint(3152, 4354);
        gL10.glHint(3154, 4354);
        gL10.glHint(3155, 4354);
        gL10.glEnable(2848);
        gL10.glDisable(2929);
        gL10.glDisable(2884);
        this.mObserver.onSurfaceCreated();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeCurlMesh(CurlMesh curlMesh) {
        synchronized (this) {
            boolean bl;
            while (bl = this.mCurlMeshes.remove(curlMesh)) {
            }
            return;
        }
    }

    public void setBackgroundColor(int n) {
        this.mBackgroundColor = n;
    }

    public void setMargins(float f, float f2, float f3, float f4) {
        synchronized (this) {
            this.mMargins.left = f;
            this.mMargins.top = f2;
            this.mMargins.right = f3;
            this.mMargins.bottom = f4;
            this.updatePageRects();
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void setViewMode(int var1_1) {
        block3 : {
            // MONITORENTER : this
            if (var1_1 != 1) ** GOTO lbl7
            this.mViewMode = var1_1;
            this.updatePageRects();
            return;
lbl7: // 1 sources:
            if (var1_1 != 2) break block3;
            this.mViewMode = var1_1;
            this.updatePageRects();
            return;
        }
        // MONITOREXIT : this
        return;
    }

    public void translate(PointF pointF) {
        pointF.x = this.mViewRect.left + this.mViewRect.width() * pointF.x / (float)this.mViewportWidth;
        pointF.y = this.mViewRect.top - (- this.mViewRect.height()) * pointF.y / (float)this.mViewportHeight;
    }

    public static interface Observer {
        public void onDrawFrame();

        public void onPageSizeChanged(int var1, int var2);

        public void onSurfaceCreated();
    }

}
