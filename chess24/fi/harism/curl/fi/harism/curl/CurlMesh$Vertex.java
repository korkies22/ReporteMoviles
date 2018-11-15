/*
 * Decompiled with CFR 0_134.
 */
package fi.harism.curl;

import fi.harism.curl.CurlMesh;

private class CurlMesh.Vertex {
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

    public void set(CurlMesh.Vertex vertex) {
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
