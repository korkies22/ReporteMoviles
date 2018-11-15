/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.NotFoundException;

final class FieldParser {
    private static final Object[][] FOUR_DIGIT_DATA_LENGTH;
    private static final Object[][] THREE_DIGIT_DATA_LENGTH;
    private static final Object[][] THREE_DIGIT_PLUS_DIGIT_DATA_LENGTH;
    private static final Object[][] TWO_DIGIT_DATA_LENGTH;
    private static final Object VARIABLE_LENGTH;

    static {
        VARIABLE_LENGTH = new Object();
        Object object = new Object[]{"00", 18};
        Object object2 = new Object[]{"01", 14};
        Object object3 = new Object[]{"02", 14};
        Object object4 = new Object[]{"10", VARIABLE_LENGTH, 20};
        Object object5 = new Object[]{"11", 6};
        Object object6 = new Object[]{"12", 6};
        Object object7 = new Object[]{"13", 6};
        Object object8 = new Object[]{"15", 6};
        Object object9 = new Object[]{"17", 6};
        Object[] arrobject = new Object[]{"21", VARIABLE_LENGTH, 20};
        Object[] arrobject2 = new Object[]{"22", VARIABLE_LENGTH, 29};
        Object[] arrobject3 = new Object[]{"30", VARIABLE_LENGTH, 8};
        Object[] arrobject4 = new Object[]{"37", VARIABLE_LENGTH, 8};
        Object[] arrobject5 = new Object[]{"90", VARIABLE_LENGTH, 30};
        Object[] arrobject6 = new Object[]{"91", VARIABLE_LENGTH, 30};
        Object[] arrobject7 = new Object[]{"92", VARIABLE_LENGTH, 30};
        Object[] arrobject8 = new Object[]{"93", VARIABLE_LENGTH, 30};
        Object[] arrobject9 = new Object[]{"94", VARIABLE_LENGTH, 30};
        Object[] arrobject10 = new Object[]{"95", VARIABLE_LENGTH, 30};
        Object[] arrobject11 = new Object[]{"96", VARIABLE_LENGTH, 30};
        Object[] arrobject12 = new Object[]{"97", VARIABLE_LENGTH, 30};
        Object[] arrobject13 = new Object[]{"98", VARIABLE_LENGTH, 30};
        Object[] arrobject14 = new Object[]{"99", VARIABLE_LENGTH, 30};
        TWO_DIGIT_DATA_LENGTH = new Object[][]{object, object2, object3, object4, object5, object6, object7, object8, object9, {"20", 2}, arrobject, arrobject2, arrobject3, arrobject4, arrobject5, arrobject6, arrobject7, arrobject8, arrobject9, arrobject10, arrobject11, arrobject12, arrobject13, arrobject14};
        object5 = new Object[]{"240", VARIABLE_LENGTH, 30};
        object6 = new Object[]{"241", VARIABLE_LENGTH, 30};
        object = VARIABLE_LENGTH;
        object7 = new Object[]{"250", VARIABLE_LENGTH, 30};
        object8 = new Object[]{"251", VARIABLE_LENGTH, 30};
        object2 = VARIABLE_LENGTH;
        object9 = new Object[]{"254", VARIABLE_LENGTH, 20};
        arrobject = new Object[]{"400", VARIABLE_LENGTH, 30};
        arrobject2 = new Object[]{"401", VARIABLE_LENGTH, 30};
        arrobject3 = new Object[]{"402", 17};
        arrobject4 = new Object[]{"403", VARIABLE_LENGTH, 30};
        arrobject5 = new Object[]{"412", 13};
        arrobject6 = new Object[]{"420", VARIABLE_LENGTH, 20};
        object3 = VARIABLE_LENGTH;
        object4 = VARIABLE_LENGTH;
        THREE_DIGIT_DATA_LENGTH = new Object[][]{object5, object6, {"242", object, 6}, object7, object8, {"253", object2, 17}, object9, arrobject, arrobject2, arrobject3, arrobject4, {"410", 13}, {"411", 13}, arrobject5, {"413", 13}, {"414", 13}, arrobject6, {"421", object3, 15}, {"422", 3}, {"423", object4, 15}, {"424", 3}, {"425", 3}, {"426", 3}};
        object3 = new Object[]{"310", 6};
        object4 = new Object[]{"313", 6};
        object5 = new Object[]{"315", 6};
        object6 = new Object[]{"327", 6};
        object7 = new Object[]{"329", 6};
        object8 = new Object[]{"332", 6};
        object9 = new Object[]{"340", 6};
        arrobject = new Object[]{"341", 6};
        arrobject2 = new Object[]{"342", 6};
        arrobject3 = new Object[]{"345", 6};
        arrobject4 = new Object[]{"354", 6};
        arrobject5 = new Object[]{"356", 6};
        arrobject6 = new Object[]{"365", 6};
        arrobject7 = new Object[]{"366", 6};
        arrobject8 = new Object[]{"390", VARIABLE_LENGTH, 15};
        object = VARIABLE_LENGTH;
        object2 = VARIABLE_LENGTH;
        arrobject9 = new Object[]{"393", VARIABLE_LENGTH, 18};
        arrobject10 = new Object[]{"703", VARIABLE_LENGTH, 30};
        THREE_DIGIT_PLUS_DIGIT_DATA_LENGTH = new Object[][]{object3, {"311", 6}, {"312", 6}, object4, {"314", 6}, object5, {"316", 6}, {"320", 6}, {"321", 6}, {"322", 6}, {"323", 6}, {"324", 6}, {"325", 6}, {"326", 6}, object6, {"328", 6}, object7, {"330", 6}, {"331", 6}, object8, {"333", 6}, {"334", 6}, {"335", 6}, {"336", 6}, object9, arrobject, arrobject2, {"343", 6}, {"344", 6}, arrobject3, {"346", 6}, {"347", 6}, {"348", 6}, {"349", 6}, {"350", 6}, {"351", 6}, {"352", 6}, {"353", 6}, arrobject4, {"355", 6}, arrobject5, {"357", 6}, {"360", 6}, {"361", 6}, {"362", 6}, {"363", 6}, {"364", 6}, arrobject6, arrobject7, {"367", 6}, {"368", 6}, {"369", 6}, arrobject8, {"391", object, 18}, {"392", object2, 15}, arrobject9, arrobject10};
        object = VARIABLE_LENGTH;
        object2 = VARIABLE_LENGTH;
        object3 = VARIABLE_LENGTH;
        object4 = VARIABLE_LENGTH;
        object5 = VARIABLE_LENGTH;
        object6 = VARIABLE_LENGTH;
        object7 = VARIABLE_LENGTH;
        arrobject = new Object[]{"8100", 6};
        object8 = VARIABLE_LENGTH;
        object9 = VARIABLE_LENGTH;
        FOUR_DIGIT_DATA_LENGTH = new Object[][]{{"7001", 13}, {"7002", object, 30}, {"7003", 10}, {"8001", 14}, {"8002", object2, 20}, {"8003", object3, 30}, {"8004", object4, 30}, {"8005", 6}, {"8006", 18}, {"8007", object5, 30}, {"8008", object6, 12}, {"8018", 18}, {"8020", object7, 25}, arrobject, {"8101", 10}, {"8102", 2}, {"8110", object8, 70}, {"8200", object9, 70}};
    }

    private FieldParser() {
    }

    static String parseFieldsInGeneralPurpose(String string) throws NotFoundException {
        if (string.isEmpty()) {
            return null;
        }
        if (string.length() < 2) {
            throw NotFoundException.getNotFoundInstance();
        }
        String string2 = string.substring(0, 2);
        for (Object[] arrobject : TWO_DIGIT_DATA_LENGTH) {
            if (!arrobject[0].equals(string2)) continue;
            if (arrobject[1] == VARIABLE_LENGTH) {
                return FieldParser.processVariableAI(2, (Integer)arrobject[2], string);
            }
            return FieldParser.processFixedAI(2, (Integer)arrobject[1], string);
        }
        if (string.length() < 3) {
            throw NotFoundException.getNotFoundInstance();
        }
        string2 = string.substring(0, 3);
        for (Object[] arrobject : THREE_DIGIT_DATA_LENGTH) {
            if (!arrobject[0].equals(string2)) continue;
            if (arrobject[1] == VARIABLE_LENGTH) {
                return FieldParser.processVariableAI(3, (Integer)arrobject[2], string);
            }
            return FieldParser.processFixedAI(3, (Integer)arrobject[1], string);
        }
        for (Object[] arrobject : THREE_DIGIT_PLUS_DIGIT_DATA_LENGTH) {
            if (!arrobject[0].equals(string2)) continue;
            if (arrobject[1] == VARIABLE_LENGTH) {
                return FieldParser.processVariableAI(4, (Integer)arrobject[2], string);
            }
            return FieldParser.processFixedAI(4, (Integer)arrobject[1], string);
        }
        if (string.length() < 4) {
            throw NotFoundException.getNotFoundInstance();
        }
        string2 = string.substring(0, 4);
        for (Object[] arrobject : FOUR_DIGIT_DATA_LENGTH) {
            if (!arrobject[0].equals(string2)) continue;
            if (arrobject[1] == VARIABLE_LENGTH) {
                return FieldParser.processVariableAI(4, (Integer)arrobject[2], string);
            }
            return FieldParser.processFixedAI(4, (Integer)arrobject[1], string);
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static String processFixedAI(int n, int n2, String string) throws NotFoundException {
        if (string.length() < n) {
            throw NotFoundException.getNotFoundInstance();
        }
        String string2 = string.substring(0, n);
        int n3 = string.length();
        if (n3 < (n2 += n)) {
            throw NotFoundException.getNotFoundInstance();
        }
        CharSequence charSequence = string.substring(n, n2);
        string = string.substring(n2);
        StringBuilder stringBuilder = new StringBuilder("(");
        stringBuilder.append(string2);
        stringBuilder.append(')');
        stringBuilder.append((String)charSequence);
        string2 = stringBuilder.toString();
        string = FieldParser.parseFieldsInGeneralPurpose(string);
        if (string == null) {
            return string2;
        }
        charSequence = new StringBuilder();
        charSequence.append(string2);
        charSequence.append(string);
        return charSequence.toString();
    }

    private static String processVariableAI(int n, int n2, String string) throws NotFoundException {
        int n3;
        String string2 = string.substring(0, n);
        int n4 = string.length();
        n2 = n3 = n2 + n;
        if (n4 < n3) {
            n2 = string.length();
        }
        CharSequence charSequence = string.substring(n, n2);
        string = string.substring(n2);
        StringBuilder stringBuilder = new StringBuilder("(");
        stringBuilder.append(string2);
        stringBuilder.append(')');
        stringBuilder.append((String)charSequence);
        string2 = stringBuilder.toString();
        string = FieldParser.parseFieldsInGeneralPurpose(string);
        if (string == null) {
            return string2;
        }
        charSequence = new StringBuilder();
        charSequence.append(string2);
        charSequence.append(string);
        return charSequence.toString();
    }
}
