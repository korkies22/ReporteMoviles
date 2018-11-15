/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.expanded.decoders.BlockParsedResult;
import com.google.zxing.oned.rss.expanded.decoders.CurrentParsingState;
import com.google.zxing.oned.rss.expanded.decoders.DecodedChar;
import com.google.zxing.oned.rss.expanded.decoders.DecodedInformation;
import com.google.zxing.oned.rss.expanded.decoders.DecodedNumeric;
import com.google.zxing.oned.rss.expanded.decoders.DecodedObject;
import com.google.zxing.oned.rss.expanded.decoders.FieldParser;

final class GeneralAppIdDecoder {
    private final StringBuilder buffer = new StringBuilder();
    private final CurrentParsingState current = new CurrentParsingState();
    private final BitArray information;

    GeneralAppIdDecoder(BitArray bitArray) {
        this.information = bitArray;
    }

    private DecodedChar decodeAlphanumeric(int n) {
        char c;
        int n2 = this.extractNumericValueFromBitArray(n, 5);
        if (n2 == 15) {
            return new DecodedChar(n + 5, '$');
        }
        if (n2 >= 5 && n2 < 15) {
            return new DecodedChar(n + 5, (char)(n2 + 48 - 5));
        }
        n2 = this.extractNumericValueFromBitArray(n, 6);
        if (n2 >= 32 && n2 < 58) {
            return new DecodedChar(n + 6, (char)(n2 + 33));
        }
        switch (n2) {
            default: {
                StringBuilder stringBuilder = new StringBuilder("Decoding invalid alphanumeric value: ");
                stringBuilder.append(n2);
                throw new IllegalStateException(stringBuilder.toString());
            }
            case 62: {
                c = '/';
                break;
            }
            case 61: {
                c = '.';
                break;
            }
            case 60: {
                c = '-';
                break;
            }
            case 59: {
                c = ',';
                break;
            }
            case 58: {
                c = '*';
            }
        }
        return new DecodedChar(n + 6, c);
    }

    private DecodedChar decodeIsoIec646(int n) throws FormatException {
        char c;
        int n2 = this.extractNumericValueFromBitArray(n, 5);
        if (n2 == 15) {
            return new DecodedChar(n + 5, '$');
        }
        if (n2 >= 5 && n2 < 15) {
            return new DecodedChar(n + 5, (char)(n2 + 48 - 5));
        }
        n2 = this.extractNumericValueFromBitArray(n, 7);
        if (n2 >= 64 && n2 < 90) {
            return new DecodedChar(n + 7, (char)(n2 + 1));
        }
        if (n2 >= 90 && n2 < 116) {
            return new DecodedChar(n + 7, (char)(n2 + 7));
        }
        switch (this.extractNumericValueFromBitArray(n, 8)) {
            default: {
                throw FormatException.getFormatInstance();
            }
            case 252: {
                c = ' ';
                break;
            }
            case 251: {
                c = '_';
                break;
            }
            case 250: {
                c = '?';
                break;
            }
            case 249: {
                c = '>';
                break;
            }
            case 248: {
                c = '=';
                break;
            }
            case 247: {
                c = '<';
                break;
            }
            case 246: {
                c = ';';
                break;
            }
            case 245: {
                c = ':';
                break;
            }
            case 244: {
                c = '/';
                break;
            }
            case 243: {
                c = '.';
                break;
            }
            case 242: {
                c = '-';
                break;
            }
            case 241: {
                c = ',';
                break;
            }
            case 240: {
                c = '+';
                break;
            }
            case 239: {
                c = '*';
                break;
            }
            case 238: {
                c = ')';
                break;
            }
            case 237: {
                c = '(';
                break;
            }
            case 236: {
                c = '\'';
                break;
            }
            case 235: {
                c = '&';
                break;
            }
            case 234: {
                c = '%';
                break;
            }
            case 233: {
                c = '\"';
                break;
            }
            case 232: {
                c = '!';
            }
        }
        return new DecodedChar(n + 8, c);
    }

    private DecodedNumeric decodeNumeric(int n) throws FormatException {
        int n2 = n + 7;
        if (n2 > this.information.getSize()) {
            if ((n = this.extractNumericValueFromBitArray(n, 4)) == 0) {
                return new DecodedNumeric(this.information.getSize(), 10, 10);
            }
            return new DecodedNumeric(this.information.getSize(), n - 1, 10);
        }
        n = this.extractNumericValueFromBitArray(n, 7) - 8;
        return new DecodedNumeric(n2, n / 11, n % 11);
    }

    static int extractNumericValueFromBitArray(BitArray bitArray, int n, int n2) {
        int n3 = 0;
        for (int i = 0; i < n2; ++i) {
            int n4 = n3;
            if (bitArray.get(n + i)) {
                n4 = n3 | 1 << n2 - i - 1;
            }
            n3 = n4;
        }
        return n3;
    }

    private boolean isAlphaOr646ToNumericLatch(int n) {
        int n2 = n + 3;
        if (n2 > this.information.getSize()) {
            return false;
        }
        while (n < n2) {
            if (this.information.get(n)) {
                return false;
            }
            ++n;
        }
        return true;
    }

    private boolean isAlphaTo646ToAlphaLatch(int n) {
        int n2;
        if (n + 1 > this.information.getSize()) {
            return false;
        }
        for (int i = 0; i < 5 && (n2 = i + n) < this.information.getSize(); ++i) {
            if (!(i == 2 ? !this.information.get(n + 2) : this.information.get(n2))) continue;
            return false;
        }
        return true;
    }

    private boolean isNumericToAlphaNumericLatch(int n) {
        int n2;
        if (n + 1 > this.information.getSize()) {
            return false;
        }
        for (int i = 0; i < 4 && (n2 = i + n) < this.information.getSize(); ++i) {
            if (!this.information.get(n2)) continue;
            return false;
        }
        return true;
    }

    private boolean isStillAlpha(int n) {
        if (n + 5 > this.information.getSize()) {
            return false;
        }
        int n2 = this.extractNumericValueFromBitArray(n, 5);
        if (n2 >= 5 && n2 < 16) {
            return true;
        }
        if (n + 6 > this.information.getSize()) {
            return false;
        }
        if ((n = this.extractNumericValueFromBitArray(n, 6)) >= 16 && n < 63) {
            return true;
        }
        return false;
    }

    private boolean isStillIsoIec646(int n) {
        if (n + 5 > this.information.getSize()) {
            return false;
        }
        int n2 = this.extractNumericValueFromBitArray(n, 5);
        if (n2 >= 5 && n2 < 16) {
            return true;
        }
        if (n + 7 > this.information.getSize()) {
            return false;
        }
        n2 = this.extractNumericValueFromBitArray(n, 7);
        if (n2 >= 64 && n2 < 116) {
            return true;
        }
        if (n + 8 > this.information.getSize()) {
            return false;
        }
        if ((n = this.extractNumericValueFromBitArray(n, 8)) >= 232 && n < 253) {
            return true;
        }
        return false;
    }

    private boolean isStillNumeric(int n) {
        int n2;
        if (n + 7 > this.information.getSize()) {
            if (n + 4 <= this.information.getSize()) {
                return true;
            }
            return false;
        }
        for (int i = n; i < (n2 = n + 3); ++i) {
            if (!this.information.get(i)) continue;
            return true;
        }
        return this.information.get(n2);
    }

    private BlockParsedResult parseAlphaBlock() {
        while (this.isStillAlpha(this.current.getPosition())) {
            DecodedChar decodedChar = this.decodeAlphanumeric(this.current.getPosition());
            this.current.setPosition(decodedChar.getNewPosition());
            if (decodedChar.isFNC1()) {
                return new BlockParsedResult(new DecodedInformation(this.current.getPosition(), this.buffer.toString()), true);
            }
            this.buffer.append(decodedChar.getValue());
        }
        if (this.isAlphaOr646ToNumericLatch(this.current.getPosition())) {
            this.current.incrementPosition(3);
            this.current.setNumeric();
        } else if (this.isAlphaTo646ToAlphaLatch(this.current.getPosition())) {
            if (this.current.getPosition() + 5 < this.information.getSize()) {
                this.current.incrementPosition(5);
            } else {
                this.current.setPosition(this.information.getSize());
            }
            this.current.setIsoIec646();
        }
        return new BlockParsedResult(false);
    }

    private DecodedInformation parseBlocks() throws FormatException {
        boolean bl;
        BlockParsedResult blockParsedResult;
        int n;
        do {
            n = this.current.getPosition();
            if (this.current.isAlpha()) {
                blockParsedResult = this.parseAlphaBlock();
                bl = blockParsedResult.isFinished();
            } else if (this.current.isIsoIec646()) {
                blockParsedResult = this.parseIsoIec646Block();
                bl = blockParsedResult.isFinished();
            } else {
                blockParsedResult = this.parseNumericBlock();
                bl = blockParsedResult.isFinished();
            }
            n = n != this.current.getPosition() ? 1 : 0;
        } while ((n != 0 || bl) && !bl);
        return blockParsedResult.getDecodedInformation();
    }

    private BlockParsedResult parseIsoIec646Block() throws FormatException {
        while (this.isStillIsoIec646(this.current.getPosition())) {
            DecodedChar decodedChar = this.decodeIsoIec646(this.current.getPosition());
            this.current.setPosition(decodedChar.getNewPosition());
            if (decodedChar.isFNC1()) {
                return new BlockParsedResult(new DecodedInformation(this.current.getPosition(), this.buffer.toString()), true);
            }
            this.buffer.append(decodedChar.getValue());
        }
        if (this.isAlphaOr646ToNumericLatch(this.current.getPosition())) {
            this.current.incrementPosition(3);
            this.current.setNumeric();
        } else if (this.isAlphaTo646ToAlphaLatch(this.current.getPosition())) {
            if (this.current.getPosition() + 5 < this.information.getSize()) {
                this.current.incrementPosition(5);
            } else {
                this.current.setPosition(this.information.getSize());
            }
            this.current.setAlpha();
        }
        return new BlockParsedResult(false);
    }

    private BlockParsedResult parseNumericBlock() throws FormatException {
        while (this.isStillNumeric(this.current.getPosition())) {
            DecodedObject decodedObject = this.decodeNumeric(this.current.getPosition());
            this.current.setPosition(decodedObject.getNewPosition());
            if (decodedObject.isFirstDigitFNC1()) {
                decodedObject = decodedObject.isSecondDigitFNC1() ? new DecodedInformation(this.current.getPosition(), this.buffer.toString()) : new DecodedInformation(this.current.getPosition(), this.buffer.toString(), decodedObject.getSecondDigit());
                return new BlockParsedResult((DecodedInformation)decodedObject, true);
            }
            this.buffer.append(decodedObject.getFirstDigit());
            if (decodedObject.isSecondDigitFNC1()) {
                return new BlockParsedResult(new DecodedInformation(this.current.getPosition(), this.buffer.toString()), true);
            }
            this.buffer.append(decodedObject.getSecondDigit());
        }
        if (this.isNumericToAlphaNumericLatch(this.current.getPosition())) {
            this.current.setAlpha();
            this.current.incrementPosition(4);
        }
        return new BlockParsedResult(false);
    }

    String decodeAllCodes(StringBuilder stringBuilder, int n) throws NotFoundException, FormatException {
        String string = null;
        do {
            DecodedInformation decodedInformation = this.decodeGeneralPurposeField(n, string);
            string = FieldParser.parseFieldsInGeneralPurpose(decodedInformation.getNewString());
            if (string != null) {
                stringBuilder.append(string);
            }
            string = decodedInformation.isRemaining() ? String.valueOf(decodedInformation.getRemainingValue()) : null;
            if (n == decodedInformation.getNewPosition()) break;
            n = decodedInformation.getNewPosition();
        } while (true);
        return stringBuilder.toString();
    }

    DecodedInformation decodeGeneralPurposeField(int n, String object) throws FormatException {
        this.buffer.setLength(0);
        if (object != null) {
            this.buffer.append((String)object);
        }
        this.current.setPosition(n);
        object = this.parseBlocks();
        if (object != null && object.isRemaining()) {
            return new DecodedInformation(this.current.getPosition(), this.buffer.toString(), object.getRemainingValue());
        }
        return new DecodedInformation(this.current.getPosition(), this.buffer.toString());
    }

    int extractNumericValueFromBitArray(int n, int n2) {
        return GeneralAppIdDecoder.extractNumericValueFromBitArray(this.information, n, n2);
    }
}
