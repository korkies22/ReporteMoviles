/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.multi;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import java.util.Map;

public final class ByQuadrantReader
implements Reader {
    private final Reader delegate;

    public ByQuadrantReader(Reader reader) {
        this.delegate = reader;
    }

    private static void makeAbsolute(ResultPoint[] arrresultPoint, int n, int n2) {
        if (arrresultPoint != null) {
            for (int i = 0; i < arrresultPoint.length; ++i) {
                ResultPoint resultPoint = arrresultPoint[i];
                arrresultPoint[i] = new ResultPoint(resultPoint.getX() + (float)n, resultPoint.getY() + (float)n2);
            }
        }
    }

    @Override
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, ChecksumException, FormatException {
        return this.decode(binaryBitmap, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Result decode(BinaryBitmap object, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        int n = object.getWidth();
        int n2 = object.getHeight();
        n /= 2;
        n2 /= 2;
        try {
            return this.delegate.decode(object.crop(0, 0, n, n2), map);
        }
        catch (NotFoundException notFoundException) {}
        try {
            Result result = this.delegate.decode(object.crop(n, 0, n, n2), map);
            ByQuadrantReader.makeAbsolute(result.getResultPoints(), n, 0);
            return result;
        }
        catch (NotFoundException notFoundException) {}
        try {
            Result result = this.delegate.decode(object.crop(0, n2, n, n2), map);
            ByQuadrantReader.makeAbsolute(result.getResultPoints(), 0, n2);
            return result;
        }
        catch (NotFoundException notFoundException) {}
        try {
            Result result = this.delegate.decode(object.crop(n, n2, n, n2), map);
            ByQuadrantReader.makeAbsolute(result.getResultPoints(), n, n2);
            return result;
        }
        catch (NotFoundException notFoundException) {}
        int n3 = n / 2;
        int n4 = n2 / 2;
        object = object.crop(n3, n4, n, n2);
        object = this.delegate.decode((BinaryBitmap)object, map);
        ByQuadrantReader.makeAbsolute(object.getResultPoints(), n3, n4);
        return object;
    }

    @Override
    public void reset() {
        this.delegate.reset();
    }
}
