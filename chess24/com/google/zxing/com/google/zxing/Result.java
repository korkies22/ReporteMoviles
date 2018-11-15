/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import java.util.EnumMap;
import java.util.Map;

public final class Result {
    private final BarcodeFormat format;
    private final int numBits;
    private final byte[] rawBytes;
    private Map<ResultMetadataType, Object> resultMetadata;
    private ResultPoint[] resultPoints;
    private final String text;
    private final long timestamp;

    public Result(String string, byte[] arrby, int n, ResultPoint[] arrresultPoint, BarcodeFormat barcodeFormat, long l) {
        this.text = string;
        this.rawBytes = arrby;
        this.numBits = n;
        this.resultPoints = arrresultPoint;
        this.format = barcodeFormat;
        this.resultMetadata = null;
        this.timestamp = l;
    }

    public Result(String string, byte[] arrby, ResultPoint[] arrresultPoint, BarcodeFormat barcodeFormat) {
        this(string, arrby, arrresultPoint, barcodeFormat, System.currentTimeMillis());
    }

    public Result(String string, byte[] arrby, ResultPoint[] arrresultPoint, BarcodeFormat barcodeFormat, long l) {
        int n = arrby == null ? 0 : 8 * arrby.length;
        this(string, arrby, n, arrresultPoint, barcodeFormat, l);
    }

    public void addResultPoints(ResultPoint[] arrresultPoint) {
        ResultPoint[] arrresultPoint2 = this.resultPoints;
        if (arrresultPoint2 == null) {
            this.resultPoints = arrresultPoint;
            return;
        }
        if (arrresultPoint != null && arrresultPoint.length > 0) {
            ResultPoint[] arrresultPoint3 = new ResultPoint[arrresultPoint2.length + arrresultPoint.length];
            System.arraycopy(arrresultPoint2, 0, arrresultPoint3, 0, arrresultPoint2.length);
            System.arraycopy(arrresultPoint, 0, arrresultPoint3, arrresultPoint2.length, arrresultPoint.length);
            this.resultPoints = arrresultPoint3;
        }
    }

    public BarcodeFormat getBarcodeFormat() {
        return this.format;
    }

    public int getNumBits() {
        return this.numBits;
    }

    public byte[] getRawBytes() {
        return this.rawBytes;
    }

    public Map<ResultMetadataType, Object> getResultMetadata() {
        return this.resultMetadata;
    }

    public ResultPoint[] getResultPoints() {
        return this.resultPoints;
    }

    public String getText() {
        return this.text;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void putAllMetadata(Map<ResultMetadataType, Object> map) {
        if (map != null) {
            if (this.resultMetadata == null) {
                this.resultMetadata = map;
                return;
            }
            this.resultMetadata.putAll(map);
        }
    }

    public void putMetadata(ResultMetadataType resultMetadataType, Object object) {
        if (this.resultMetadata == null) {
            this.resultMetadata = new EnumMap<ResultMetadataType, Object>(ResultMetadataType.class);
        }
        this.resultMetadata.put(resultMetadataType, object);
    }

    public String toString() {
        return this.text;
    }
}
