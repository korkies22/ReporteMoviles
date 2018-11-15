/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.TypeEvaluator
 */
package android.support.graphics.drawable;

import android.animation.TypeEvaluator;
import android.support.graphics.drawable.AnimatorInflaterCompat;
import android.support.v4.graphics.PathParser;

private static class AnimatorInflaterCompat.PathDataEvaluator
implements TypeEvaluator<PathParser.PathDataNode[]> {
    private PathParser.PathDataNode[] mNodeArray;

    private AnimatorInflaterCompat.PathDataEvaluator() {
    }

    AnimatorInflaterCompat.PathDataEvaluator(PathParser.PathDataNode[] arrpathDataNode) {
        this.mNodeArray = arrpathDataNode;
    }

    public PathParser.PathDataNode[] evaluate(float f, PathParser.PathDataNode[] arrpathDataNode, PathParser.PathDataNode[] arrpathDataNode2) {
        if (!PathParser.canMorph(arrpathDataNode, arrpathDataNode2)) {
            throw new IllegalArgumentException("Can't interpolate between two incompatible pathData");
        }
        if (this.mNodeArray == null || !PathParser.canMorph(this.mNodeArray, arrpathDataNode)) {
            this.mNodeArray = PathParser.deepCopyNodes(arrpathDataNode);
        }
        for (int i = 0; i < arrpathDataNode.length; ++i) {
            this.mNodeArray[i].interpolatePathDataNode(arrpathDataNode[i], arrpathDataNode2[i], f);
        }
        return this.mNodeArray;
    }
}
