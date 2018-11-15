/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.util;

import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import org.java_websocket.exceptions.InvalidDataException;

public class Charsetfunctions {
    public static CodingErrorAction codingErrorAction = CodingErrorAction.REPORT;

    public static byte[] asciiBytes(String arrby) {
        try {
            arrby = arrby.getBytes("ASCII");
            return arrby;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException(unsupportedEncodingException);
        }
    }

    public static void main(String[] arrstring) throws InvalidDataException {
        Charsetfunctions.stringUtf8(Charsetfunctions.utf8Bytes("\u0000"));
        Charsetfunctions.stringAscii(Charsetfunctions.asciiBytes("\u0000"));
    }

    public static String stringAscii(byte[] arrby) {
        return Charsetfunctions.stringAscii(arrby, 0, arrby.length);
    }

    public static String stringAscii(byte[] object, int n, int n2) {
        try {
            object = new String((byte[])object, n, n2, "ASCII");
            return object;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException(unsupportedEncodingException);
        }
    }

    public static String stringUtf8(ByteBuffer byteBuffer) throws InvalidDataException {
        Object object = Charset.forName("UTF8").newDecoder();
        object.onMalformedInput(codingErrorAction);
        object.onUnmappableCharacter(codingErrorAction);
        try {
            byteBuffer.mark();
            object = object.decode(byteBuffer).toString();
            byteBuffer.reset();
            return object;
        }
        catch (CharacterCodingException characterCodingException) {
            throw new InvalidDataException(1007, characterCodingException);
        }
    }

    public static String stringUtf8(byte[] arrby) throws InvalidDataException {
        return Charsetfunctions.stringUtf8(ByteBuffer.wrap(arrby));
    }

    public static byte[] utf8Bytes(String arrby) {
        try {
            arrby = arrby.getBytes("UTF8");
            return arrby;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException(unsupportedEncodingException);
        }
    }
}
