/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.graphics.Path
 *  android.util.Log
 */
package android.support.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Path;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.graphics.PathParser;
import android.util.Log;

private static class VectorDrawableCompat.VPath {
    int mChangingConfigurations;
    protected PathParser.PathDataNode[] mNodes = null;
    String mPathName;

    public VectorDrawableCompat.VPath() {
    }

    public VectorDrawableCompat.VPath(VectorDrawableCompat.VPath vPath) {
        this.mPathName = vPath.mPathName;
        this.mChangingConfigurations = vPath.mChangingConfigurations;
        this.mNodes = PathParser.deepCopyNodes(vPath.mNodes);
    }

    public void applyTheme(Resources.Theme theme) {
    }

    public boolean canApplyTheme() {
        return false;
    }

    public PathParser.PathDataNode[] getPathData() {
        return this.mNodes;
    }

    public String getPathName() {
        return this.mPathName;
    }

    public boolean isClipPath() {
        return false;
    }

    public String nodesToString(PathParser.PathDataNode[] arrpathDataNode) {
        String string = " ";
        for (int i = 0; i < arrpathDataNode.length; ++i) {
            float[] arrf = new float[]();
            arrf.append(string);
            arrf.append(arrpathDataNode[i].mType);
            arrf.append(":");
            string = arrf.toString();
            arrf = arrpathDataNode[i].mParams;
            for (int j = 0; j < arrf.length; ++j) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string);
                stringBuilder.append(arrf[j]);
                stringBuilder.append(",");
                string = stringBuilder.toString();
            }
        }
        return string;
    }

    public void printVPath(int n) {
        StringBuilder stringBuilder;
        String string = "";
        for (int i = 0; i < n; ++i) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append("    ");
            string = stringBuilder.toString();
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append("current path is :");
        stringBuilder.append(this.mPathName);
        stringBuilder.append(" pathData is ");
        stringBuilder.append(this.nodesToString(this.mNodes));
        Log.v((String)VectorDrawableCompat.LOGTAG, (String)stringBuilder.toString());
    }

    public void setPathData(PathParser.PathDataNode[] arrpathDataNode) {
        if (!PathParser.canMorph(this.mNodes, arrpathDataNode)) {
            this.mNodes = PathParser.deepCopyNodes(arrpathDataNode);
            return;
        }
        PathParser.updateNodes(this.mNodes, arrpathDataNode);
    }

    public void toPath(Path path) {
        path.reset();
        if (this.mNodes != null) {
            PathParser.PathDataNode.nodesToPath(this.mNodes, path);
        }
    }
}
