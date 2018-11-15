/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Color
 *  android.graphics.PointF
 *  android.graphics.RectF
 *  android.opengl.GLUtils
 *  javax.microedition.khronos.opengles.GL10
 */
package fi.harism.curl;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.opengl.GLUtils;
import fi.harism.curl.CurlPage;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

public class CurlMesh {
    private static final boolean DRAW_CURL_POSITION = false;
    private static final boolean DRAW_POLYGON_OUTLINES = false;
    private static final boolean DRAW_SHADOW = true;
    private static final boolean DRAW_TEXTURE = true;
    private static final float[] SHADOW_INNER_COLOR = new float[]{0.0f, 0.0f, 0.0f, 0.5f};
    private static final float[] SHADOW_OUTER_COLOR = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
    private Array<ShadowVertex> mArrDropShadowVertices;
    private Array<Vertex> mArrIntersections;
    private Array<Vertex> mArrOutputVertices;
    private Array<Vertex> mArrRotatedVertices;
    private Array<Double> mArrScanLines;
    private Array<ShadowVertex> mArrSelfShadowVertices;
    private Array<ShadowVertex> mArrTempShadowVertices;
    private Array<Vertex> mArrTempVertices;
    private FloatBuffer mBufColors;
    private FloatBuffer mBufCurlPositionLines;
    private FloatBuffer mBufShadowColors;
    private FloatBuffer mBufShadowVertices;
    private FloatBuffer mBufTexCoords;
    private FloatBuffer mBufVertices;
    private int mCurlPositionLinesCount;
    private int mDropShadowCount;
    private boolean mFlipTexture = false;
    private int mMaxCurlSplits;
    private final Vertex[] mRectangle = new Vertex[4];
    private int mSelfShadowCount;
    private boolean mTextureBack = false;
    private int[] mTextureIds = null;
    private final CurlPage mTexturePage = new CurlPage();
    private final RectF mTextureRectBack = new RectF();
    private final RectF mTextureRectFront = new RectF();
    private int mVerticesCountBack;
    private int mVerticesCountFront;

    public CurlMesh(int n) {
        int n2 = n < 1 ? 1 : n;
        this.mMaxCurlSplits = n2;
        this.mArrScanLines = new Array(n + 2);
        this.mArrOutputVertices = new Array(7);
        this.mArrRotatedVertices = new Array(4);
        this.mArrIntersections = new Array(2);
        this.mArrTempVertices = new Array(11);
        for (n = 0; n < 11; ++n) {
            this.mArrTempVertices.add(new Vertex());
        }
        this.mArrSelfShadowVertices = new Array((this.mMaxCurlSplits + 2) * 2);
        this.mArrDropShadowVertices = new Array((this.mMaxCurlSplits + 2) * 2);
        this.mArrTempShadowVertices = new Array((this.mMaxCurlSplits + 2) * 2);
        for (n = 0; n < (this.mMaxCurlSplits + 2) * 2; ++n) {
            this.mArrTempShadowVertices.add(new ShadowVertex());
        }
        for (n = 0; n < 4; ++n) {
            this.mRectangle[n] = new Vertex();
        }
        Object object = this.mRectangle[0];
        Vertex vertex = this.mRectangle[1];
        Vertex vertex2 = this.mRectangle[1];
        this.mRectangle[3].mPenumbraY = -1.0;
        vertex2.mPenumbraY = -1.0;
        vertex.mPenumbraX = -1.0;
        object.mPenumbraX = -1.0;
        object = this.mRectangle[0];
        vertex = this.mRectangle[2];
        vertex2 = this.mRectangle[2];
        this.mRectangle[3].mPenumbraX = 1.0;
        vertex2.mPenumbraY = 1.0;
        vertex.mPenumbraX = 1.0;
        object.mPenumbraY = 1.0;
        n = 6 + this.mMaxCurlSplits * 2;
        object = ByteBuffer.allocateDirect(n * 3 * 4);
        object.order(ByteOrder.nativeOrder());
        this.mBufVertices = object.asFloatBuffer();
        this.mBufVertices.position(0);
        object = ByteBuffer.allocateDirect(n * 2 * 4);
        object.order(ByteOrder.nativeOrder());
        this.mBufTexCoords = object.asFloatBuffer();
        this.mBufTexCoords.position(0);
        object = ByteBuffer.allocateDirect(n * 4 * 4);
        object.order(ByteOrder.nativeOrder());
        this.mBufColors = object.asFloatBuffer();
        this.mBufColors.position(0);
        n = (this.mMaxCurlSplits + 2) * 2 * 2;
        object = ByteBuffer.allocateDirect(n * 4 * 4);
        object.order(ByteOrder.nativeOrder());
        this.mBufShadowColors = object.asFloatBuffer();
        this.mBufShadowColors.position(0);
        object = ByteBuffer.allocateDirect(n * 3 * 4);
        object.order(ByteOrder.nativeOrder());
        this.mBufShadowVertices = object.asFloatBuffer();
        this.mBufShadowVertices.position(0);
        this.mSelfShadowCount = 0;
        this.mDropShadowCount = 0;
    }

    private void addVertex(Vertex vertex) {
        this.mBufVertices.put((float)vertex.mPosX);
        this.mBufVertices.put((float)vertex.mPosY);
        this.mBufVertices.put((float)vertex.mPosZ);
        this.mBufColors.put(vertex.mColorFactor * (float)Color.red((int)vertex.mColor) / 255.0f);
        this.mBufColors.put(vertex.mColorFactor * (float)Color.green((int)vertex.mColor) / 255.0f);
        this.mBufColors.put(vertex.mColorFactor * (float)Color.blue((int)vertex.mColor) / 255.0f);
        this.mBufColors.put((float)Color.alpha((int)vertex.mColor) / 255.0f);
        this.mBufTexCoords.put((float)vertex.mTexX);
        this.mBufTexCoords.put((float)vertex.mTexY);
    }

    private Array<Vertex> getIntersections(Array<Vertex> array, int[][] arrn, double d) {
        this.mArrIntersections.clear();
        for (int i = 0; i < arrn.length; ++i) {
            Vertex vertex = array.get(arrn[i][0]);
            Vertex vertex2 = array.get(arrn[i][1]);
            if (vertex.mPosX <= d || vertex2.mPosX >= d) continue;
            double d2 = (d - vertex2.mPosX) / (vertex.mPosX - vertex2.mPosX);
            Vertex vertex3 = this.mArrTempVertices.remove(0);
            vertex3.set(vertex2);
            vertex3.mPosX = d;
            vertex3.mPosY += (vertex.mPosY - vertex2.mPosY) * d2;
            vertex3.mTexX += (vertex.mTexX - vertex2.mTexX) * d2;
            vertex3.mTexY += (vertex.mTexY - vertex2.mTexY) * d2;
            vertex3.mPenumbraX += (vertex.mPenumbraX - vertex2.mPenumbraX) * d2;
            vertex3.mPenumbraY += (vertex.mPenumbraY - vertex2.mPenumbraY) * d2;
            this.mArrIntersections.add(vertex3);
        }
        return this.mArrIntersections;
    }

    private void setTexCoords(float f, float f2, float f3, float f4) {
        synchronized (this) {
            Vertex vertex = this.mRectangle[0];
            double d = f;
            vertex.mTexX = d;
            vertex = this.mRectangle[0];
            double d2 = f2;
            vertex.mTexY = d2;
            this.mRectangle[1].mTexX = d;
            vertex = this.mRectangle[1];
            d = f4;
            vertex.mTexY = d;
            vertex = this.mRectangle[2];
            double d3 = f3;
            vertex.mTexX = d3;
            this.mRectangle[2].mTexY = d2;
            this.mRectangle[3].mTexX = d3;
            this.mRectangle[3].mTexY = d;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void curl(PointF object, PointF pointF, double d) {
        synchronized (this) {
            double d2;
            int n;
            double d3;
            void var2_2;
            double d4;
            Object object2;
            Object object3;
            Object object4;
            Array<Vertex> array;
            this.mBufVertices.position(0);
            this.mBufColors.position(0);
            this.mBufTexCoords.position(0);
            double d5 = d2 = Math.acos(var2_2.x);
            if (var2_2.y > 0.0f) {
                d5 = - d2;
            }
            this.mArrTempVertices.addAll(this.mArrRotatedVertices);
            this.mArrRotatedVertices.clear();
            int n2 = 0;
            do {
                if (n2 < 4) {
                    object3 = this.mArrTempVertices.remove(0);
                    object3.set(this.mRectangle[n2]);
                    object3.translate(- object.x, - object.y);
                    object3.rotateZ(- d5);
                } else {
                    object3 = new int[][]{{0, 1}, {0, 2}, {1, 3}, {2, 3}};
                    object4 = this.mArrRotatedVertices.get(0);
                    array = this.mArrRotatedVertices.get(2);
                    object2 = this.mArrRotatedVertices.get(3);
                    if (Math.sqrt((object4.mPosX - array.mPosX) * (object4.mPosX - array.mPosX) + (object4.mPosY - array.mPosY) * (object4.mPosY - array.mPosY)) > Math.sqrt((object4.mPosX - object2.mPosX) * (object4.mPosX - object2.mPosX) + (object4.mPosY - object2.mPosY) * (object4.mPosY - object2.mPosY))) {
                        object3[1][1] = 3;
                        object3[2][1] = 2;
                    }
                    this.mVerticesCountBack = 0;
                    this.mVerticesCountFront = 0;
                    this.mArrTempShadowVertices.addAll(this.mArrDropShadowVertices);
                    this.mArrTempShadowVertices.addAll(this.mArrSelfShadowVertices);
                    this.mArrDropShadowVertices.clear();
                    this.mArrSelfShadowVertices.clear();
                    d3 = 3.141592653589793 * d4;
                    this.mArrScanLines.clear();
                    if (this.mMaxCurlSplits > 0) {
                        this.mArrScanLines.add(0.0);
                    }
                    for (n2 = 1; n2 < this.mMaxCurlSplits; ++n2) {
                        this.mArrScanLines.add((- d3) * (double)n2 / (double)(this.mMaxCurlSplits - 1));
                    }
                    break;
                }
                for (n = 0; n < this.mArrRotatedVertices.size(); ++n) {
                    object4 = this.mArrRotatedVertices.get(n);
                    if (object3.mPosX > object4.mPosX || object3.mPosX == object4.mPosX && object3.mPosY > object4.mPosY) break;
                }
                this.mArrRotatedVertices.add(n, (Vertex)object3);
                ++n2;
            } while (true);
            this.mArrScanLines.add(this.mArrRotatedVertices.get((int)3).mPosX - 1.0);
            d2 = this.mArrRotatedVertices.get((int)0).mPosX + 1.0;
            n2 = 0;
            double d6 = d5;
            d5 = d3;
            do {
                if (n2 >= this.mArrScanLines.size()) {
                    this.mBufVertices.position(0);
                    this.mBufColors.position(0);
                    this.mBufTexCoords.position(0);
                    this.mBufShadowColors.position(0);
                    this.mBufShadowVertices.position(0);
                    this.mDropShadowCount = 0;
                    break;
                }
                double d7 = this.mArrScanLines.get(n2);
                n = 0;
                do {
                    if (n < this.mArrRotatedVertices.size()) {
                        object4 = this.mArrRotatedVertices.get(n);
                        if (object4.mPosX >= d7 && object4.mPosX <= d2) {
                            array = this.mArrTempVertices.remove(0);
                            array.set((Vertex)object4);
                            object2 = this.getIntersections(this.mArrRotatedVertices, (int[][])object3, array.mPosX);
                            if (object2.size() == 1 && ((Vertex)object2.get((int)0)).mPosY > object4.mPosY) {
                                this.mArrOutputVertices.addAll((Array<Vertex>)object2);
                                this.mArrOutputVertices.add((Vertex)((Object)array));
                            } else if (object2.size() <= 1) {
                                this.mArrOutputVertices.add((Vertex)((Object)array));
                                this.mArrOutputVertices.addAll((Array<Vertex>)object2);
                            } else {
                                this.mArrTempVertices.add((Vertex)((Object)array));
                                this.mArrTempVertices.addAll((Array<Vertex>)object2);
                            }
                        }
                    } else {
                        array = this.getIntersections(this.mArrRotatedVertices, (int[][])object3, d7);
                        if (array.size() == 2) {
                            object4 = (Vertex)array.get(0);
                            object2 = (Vertex)array.get(1);
                            if (object4.mPosY < object2.mPosY) {
                                this.mArrOutputVertices.add((Vertex)object2);
                                this.mArrOutputVertices.add((Vertex)object4);
                                object4 = object3;
                                d3 = d6;
                                break;
                            }
                            this.mArrOutputVertices.addAll(array);
                            object4 = object3;
                            d3 = d6;
                            break;
                        }
                        object4 = object3;
                        d3 = d6;
                        if (array.size() == 0) break;
                        this.mArrTempVertices.addAll(array);
                        d3 = d6;
                        object4 = object3;
                        break;
                    }
                    ++n;
                } while (true);
                while (this.mArrOutputVertices.size() > 0) {
                    boolean bl;
                    block46 : {
                        block45 : {
                            block42 : {
                                block43 : {
                                    block44 : {
                                        block41 : {
                                            object3 = this.mArrOutputVertices.remove(0);
                                            this.mArrTempVertices.add((Vertex)object3);
                                            if (n2 != 0) break block41;
                                            ++this.mVerticesCountFront;
                                            break block42;
                                        }
                                        if (n2 == this.mArrScanLines.size() - 1 || d5 == 0.0) break block43;
                                        d2 = object3.mPosX / d5 * 3.141592653589793;
                                        object3.mPosX = d4 * Math.sin(d2);
                                        object3.mPosZ = d4 - Math.cos(d2) * d4;
                                        object3.mPenumbraX *= Math.cos(d2);
                                        object3.mColorFactor = (float)(0.10000000149011612 + 0.8999999761581421 * Math.sqrt(Math.sin(d2) + 1.0));
                                        if (object3.mPosZ < d4) break block44;
                                        ++this.mVerticesCountBack;
                                        break block45;
                                    }
                                    ++this.mVerticesCountFront;
                                    break block42;
                                }
                                object3.mPosX = - d5 + object3.mPosX;
                                object3.mPosZ = 2.0 * d4;
                                object3.mPenumbraX = - object3.mPenumbraX;
                                ++this.mVerticesCountBack;
                                break block45;
                            }
                            bl = true;
                            break block46;
                        }
                        bl = false;
                    }
                    if (bl != this.mFlipTexture) {
                        object3.mTexX *= (double)this.mTextureRectFront.right;
                        object3.mTexY *= (double)this.mTextureRectFront.bottom;
                        object3.mColor = this.mTexturePage.getColor(1);
                    } else {
                        object3.mTexX *= (double)this.mTextureRectBack.right;
                        object3.mTexY *= (double)this.mTextureRectBack.bottom;
                        object3.mColor = this.mTexturePage.getColor(2);
                    }
                    object3.rotateZ(d3);
                    object3.translate(object.x, object.y);
                    this.addVertex((Vertex)object3);
                    if (object3.mPosZ > 0.0 && object3.mPosZ <= d4) {
                        array = this.mArrTempShadowVertices.remove(0);
                        array.mPosX = object3.mPosX;
                        array.mPosY = object3.mPosY;
                        array.mPosZ = object3.mPosZ;
                        array.mPenumbraX = object3.mPosZ / 2.0 * (double)(- var2_2.x);
                        array.mPenumbraY = object3.mPosZ / 2.0 * (double)(- var2_2.y);
                        array.mPenumbraColor = object3.mPosZ / d4;
                        n = (this.mArrDropShadowVertices.size() + 1) / 2;
                        this.mArrDropShadowVertices.add(n, (ShadowVertex)((Object)array));
                    }
                    if (object3.mPosZ <= d4) continue;
                    array = this.mArrTempShadowVertices.remove(0);
                    array.mPosX = object3.mPosX;
                    array.mPosY = object3.mPosY;
                    array.mPosZ = object3.mPosZ;
                    array.mPenumbraX = (object3.mPosZ - d4) / 3.0 * object3.mPenumbraX;
                    array.mPenumbraY = (object3.mPosZ - d4) / 3.0 * object3.mPenumbraY;
                    array.mPenumbraColor = (object3.mPosZ - d4) / (2.0 * d4);
                    n = (this.mArrSelfShadowVertices.size() + 1) / 2;
                    this.mArrSelfShadowVertices.add(n, (ShadowVertex)((Object)array));
                }
                ++n2;
                d2 = d7;
                object3 = object4;
                d6 = d3;
            } while (true);
            for (n2 = 0; n2 < this.mArrDropShadowVertices.size(); this.mDropShadowCount += 2, ++n2) {
                object = this.mArrDropShadowVertices.get(n2);
                this.mBufShadowVertices.put((float)object.mPosX);
                this.mBufShadowVertices.put((float)object.mPosY);
                this.mBufShadowVertices.put((float)object.mPosZ);
                this.mBufShadowVertices.put((float)(object.mPosX + object.mPenumbraX));
                this.mBufShadowVertices.put((float)(object.mPosY + object.mPenumbraY));
                this.mBufShadowVertices.put((float)object.mPosZ);
                for (n = 0; n < 4; ++n) {
                    d4 = SHADOW_OUTER_COLOR[n];
                    d5 = SHADOW_INNER_COLOR[n] - SHADOW_OUTER_COLOR[n];
                    d2 = object.mPenumbraColor;
                    this.mBufShadowColors.put((float)(d4 + d5 * d2));
                }
                this.mBufShadowColors.put(SHADOW_OUTER_COLOR);
            }
            this.mSelfShadowCount = 0;
            n2 = 0;
            do {
                if (n2 >= this.mArrSelfShadowVertices.size()) {
                    this.mBufShadowColors.position(0);
                    this.mBufShadowVertices.position(0);
                    return;
                }
                object = this.mArrSelfShadowVertices.get(n2);
                this.mBufShadowVertices.put((float)object.mPosX);
                this.mBufShadowVertices.put((float)object.mPosY);
                this.mBufShadowVertices.put((float)object.mPosZ);
                this.mBufShadowVertices.put((float)(object.mPosX + object.mPenumbraX));
                this.mBufShadowVertices.put((float)(object.mPosY + object.mPenumbraY));
                this.mBufShadowVertices.put((float)object.mPosZ);
                for (n = 0; n < 4; ++n) {
                    d4 = SHADOW_OUTER_COLOR[n];
                    d5 = SHADOW_INNER_COLOR[n] - SHADOW_OUTER_COLOR[n];
                    d2 = object.mPenumbraColor;
                    this.mBufShadowColors.put((float)(d4 + d5 * d2));
                }
                this.mBufShadowColors.put(SHADOW_OUTER_COLOR);
                this.mSelfShadowCount += 2;
                ++n2;
            } while (true);
        }
    }

    public CurlPage getTexturePage() {
        synchronized (this) {
            CurlPage curlPage = this.mTexturePage;
            return curlPage;
        }
    }

    public void onDrawFrame(GL10 gL10) {
        synchronized (this) {
            int n;
            int n2;
            Bitmap bitmap;
            block13 : {
                if (this.mTextureIds != null) break block13;
                this.mTextureIds = new int[2];
                gL10.glGenTextures(2, this.mTextureIds, 0);
                bitmap = this.mTextureIds;
                n = ((int[])bitmap).length;
                for (n2 = 0; n2 < n; ++n2) {
                    gL10.glBindTexture(3553, (int)bitmap[n2]);
                    gL10.glTexParameterf(3553, 10241, 9728.0f);
                    gL10.glTexParameterf(3553, 10240, 9728.0f);
                    gL10.glTexParameterf(3553, 10242, 33071.0f);
                    gL10.glTexParameterf(3553, 10243, 33071.0f);
                }
            }
            if (this.mTexturePage.getTexturesChanged()) {
                gL10.glBindTexture(3553, this.mTextureIds[0]);
                bitmap = this.mTexturePage.getTexture(this.mTextureRectFront, 1);
                GLUtils.texImage2D((int)3553, (int)0, (Bitmap)bitmap, (int)0);
                bitmap.recycle();
                this.mTextureBack = this.mTexturePage.hasBackTexture();
                if (this.mTextureBack) {
                    gL10.glBindTexture(3553, this.mTextureIds[1]);
                    bitmap = this.mTexturePage.getTexture(this.mTextureRectBack, 2);
                    GLUtils.texImage2D((int)3553, (int)0, (Bitmap)bitmap, (int)0);
                    bitmap.recycle();
                } else {
                    this.mTextureRectBack.set(this.mTextureRectFront);
                }
                this.mTexturePage.recycle();
                this.reset();
            }
            gL10.glEnableClientState(32884);
            gL10.glDisable(3553);
            gL10.glEnable(3042);
            gL10.glBlendFunc(770, 771);
            gL10.glEnableClientState(32886);
            gL10.glColorPointer(4, 5126, 0, (Buffer)this.mBufShadowColors);
            gL10.glVertexPointer(3, 5126, 0, (Buffer)this.mBufShadowVertices);
            gL10.glDrawArrays(5, 0, this.mDropShadowCount);
            gL10.glDisableClientState(32886);
            gL10.glDisable(3042);
            gL10.glEnableClientState(32888);
            gL10.glTexCoordPointer(2, 5126, 0, (Buffer)this.mBufTexCoords);
            gL10.glVertexPointer(3, 5126, 0, (Buffer)this.mBufVertices);
            gL10.glEnableClientState(32886);
            gL10.glColorPointer(4, 5126, 0, (Buffer)this.mBufColors);
            gL10.glDisable(3553);
            gL10.glDrawArrays(5, 0, this.mVerticesCountFront);
            gL10.glEnable(3042);
            gL10.glEnable(3553);
            if (this.mFlipTexture && this.mTextureBack) {
                gL10.glBindTexture(3553, this.mTextureIds[1]);
            } else {
                gL10.glBindTexture(3553, this.mTextureIds[0]);
            }
            gL10.glBlendFunc(770, 771);
            gL10.glDrawArrays(5, 0, this.mVerticesCountFront);
            gL10.glDisable(3042);
            gL10.glDisable(3553);
            n2 = Math.max(0, this.mVerticesCountFront - 2);
            n = this.mVerticesCountFront + this.mVerticesCountBack - n2;
            gL10.glDrawArrays(5, n2, n);
            gL10.glEnable(3042);
            gL10.glEnable(3553);
            if (!this.mFlipTexture && this.mTextureBack) {
                gL10.glBindTexture(3553, this.mTextureIds[1]);
            } else {
                gL10.glBindTexture(3553, this.mTextureIds[0]);
            }
            gL10.glBlendFunc(770, 771);
            gL10.glDrawArrays(5, n2, n);
            gL10.glDisable(3042);
            gL10.glDisable(3553);
            gL10.glDisableClientState(32888);
            gL10.glDisableClientState(32886);
            gL10.glEnable(3042);
            gL10.glBlendFunc(770, 771);
            gL10.glEnableClientState(32886);
            gL10.glColorPointer(4, 5126, 0, (Buffer)this.mBufShadowColors);
            gL10.glVertexPointer(3, 5126, 0, (Buffer)this.mBufShadowVertices);
            gL10.glDrawArrays(5, this.mDropShadowCount, this.mSelfShadowCount);
            gL10.glDisableClientState(32886);
            gL10.glDisable(3042);
            gL10.glDisableClientState(32884);
            return;
        }
    }

    public void reset() {
        synchronized (this) {
            this.mBufVertices.position(0);
            this.mBufColors.position(0);
            this.mBufTexCoords.position(0);
            for (int i = 0; i < 4; ++i) {
                Vertex vertex = this.mArrTempVertices.get(0);
                vertex.set(this.mRectangle[i]);
                if (this.mFlipTexture) {
                    vertex.mTexX *= (double)this.mTextureRectBack.right;
                    vertex.mTexY *= (double)this.mTextureRectBack.bottom;
                    vertex.mColor = this.mTexturePage.getColor(2);
                } else {
                    vertex.mTexX *= (double)this.mTextureRectFront.right;
                    vertex.mTexY *= (double)this.mTextureRectFront.bottom;
                    vertex.mColor = this.mTexturePage.getColor(1);
                }
                this.addVertex(vertex);
            }
            this.mVerticesCountFront = 4;
            this.mVerticesCountBack = 0;
            this.mBufVertices.position(0);
            this.mBufColors.position(0);
            this.mBufTexCoords.position(0);
            this.mSelfShadowCount = 0;
            this.mDropShadowCount = 0;
            return;
        }
    }

    public void resetTexture() {
        synchronized (this) {
            this.mTextureIds = null;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setFlipTexture(boolean bl) {
        synchronized (this) {
            this.mFlipTexture = bl;
            if (bl) {
                this.setTexCoords(1.0f, 0.0f, 0.0f, 1.0f);
            } else {
                this.setTexCoords(0.0f, 0.0f, 1.0f, 1.0f);
            }
            return;
        }
    }

    public void setRect(RectF rectF) {
        this.mRectangle[0].mPosX = rectF.left;
        this.mRectangle[0].mPosY = rectF.top;
        this.mRectangle[1].mPosX = rectF.left;
        this.mRectangle[1].mPosY = rectF.bottom;
        this.mRectangle[2].mPosX = rectF.right;
        this.mRectangle[2].mPosY = rectF.top;
        this.mRectangle[3].mPosX = rectF.right;
        this.mRectangle[3].mPosY = rectF.bottom;
    }

    private class Array<T> {
        private Object[] mArray;
        private int mCapacity;
        private int mSize;

        public Array(int n) {
            this.mCapacity = n;
            this.mArray = new Object[n];
        }

        public void add(int n, T t) {
            if (n >= 0 && n <= this.mSize && this.mSize < this.mCapacity) {
                for (int i = this.mSize; i > n; --i) {
                    this.mArray[i] = this.mArray[i - 1];
                }
                this.mArray[n] = t;
                ++this.mSize;
                return;
            }
            throw new IndexOutOfBoundsException();
        }

        public void add(T t) {
            if (this.mSize >= this.mCapacity) {
                throw new IndexOutOfBoundsException();
            }
            Object[] arrobject = this.mArray;
            int n = this.mSize;
            this.mSize = n + 1;
            arrobject[n] = t;
        }

        public void addAll(Array<T> array) {
            if (this.mSize + array.size() > this.mCapacity) {
                throw new IndexOutOfBoundsException();
            }
            for (int i = 0; i < array.size(); ++i) {
                Object[] arrobject = this.mArray;
                int n = this.mSize;
                this.mSize = n + 1;
                arrobject[n] = array.get(i);
            }
        }

        public void clear() {
            this.mSize = 0;
        }

        public T get(int n) {
            if (n >= 0 && n < this.mSize) {
                return (T)this.mArray[n];
            }
            throw new IndexOutOfBoundsException();
        }

        public T remove(int n) {
            if (n >= 0 && n < this.mSize) {
                Object object = this.mArray[n];
                while (n < this.mSize - 1) {
                    Object[] arrobject = this.mArray;
                    Object[] arrobject2 = this.mArray;
                    int n2 = n + 1;
                    arrobject[n] = arrobject2[n2];
                    n = n2;
                }
                --this.mSize;
                return (T)object;
            }
            throw new IndexOutOfBoundsException();
        }

        public int size() {
            return this.mSize;
        }
    }

    private class ShadowVertex {
        public double mPenumbraColor;
        public double mPenumbraX;
        public double mPenumbraY;
        public double mPosX;
        public double mPosY;
        public double mPosZ;

        private ShadowVertex() {
        }
    }

    private class Vertex {
        public int mColor;
        public float mColorFactor = 1.0f;
        public double mPenumbraX;
        public double mPenumbraY;
        public double mPosX = 0.0;
        public double mPosY = 0.0;
        public double mPosZ = 0.0;
        public double mTexX = 0.0;
        public double mTexY = 0.0;

        public void rotateZ(double d) {
            double d2 = Math.cos(d);
            d = Math.sin(d);
            double d3 = this.mPosX;
            double d4 = this.mPosY;
            double d5 = this.mPosX;
            double d6 = - d;
            double d7 = this.mPosY;
            this.mPosX = d3 * d2 + d4 * d;
            this.mPosY = d5 * d6 + d7 * d2;
            d3 = this.mPenumbraX;
            d4 = this.mPenumbraY;
            d5 = this.mPenumbraX;
            d7 = this.mPenumbraY;
            this.mPenumbraX = d3 * d2 + d4 * d;
            this.mPenumbraY = d5 * d6 + d7 * d2;
        }

        public void set(Vertex vertex) {
            this.mPosX = vertex.mPosX;
            this.mPosY = vertex.mPosY;
            this.mPosZ = vertex.mPosZ;
            this.mTexX = vertex.mTexX;
            this.mTexY = vertex.mTexY;
            this.mPenumbraX = vertex.mPenumbraX;
            this.mPenumbraY = vertex.mPenumbraY;
            this.mColor = vertex.mColor;
            this.mColorFactor = vertex.mColorFactor;
        }

        public void translate(double d, double d2) {
            this.mPosX += d;
            this.mPosY += d2;
        }
    }

}
