/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.internal.Validate;
import com.facebook.share.model.GameRequestContent;
import java.util.List;

public class GameRequestValidation {
    public static void validate(GameRequestContent gameRequestContent) {
        Validate.notNull(gameRequestContent.getMessage(), "message");
        String string = gameRequestContent.getObjectId();
        int n = 0;
        int n2 = string != null ? 1 : 0;
        int n3 = gameRequestContent.getActionType() != GameRequestContent.ActionType.ASKFOR && gameRequestContent.getActionType() != GameRequestContent.ActionType.SEND ? 0 : 1;
        if (n2 ^ n3) {
            throw new IllegalArgumentException("Object id should be provided if and only if action type is send or askfor");
        }
        n3 = n;
        if (gameRequestContent.getRecipients() != null) {
            n3 = 1;
        }
        n2 = n3;
        if (gameRequestContent.getSuggestions() != null) {
            n2 = n3 + 1;
        }
        n3 = n2;
        if (gameRequestContent.getFilters() != null) {
            n3 = n2 + 1;
        }
        if (n3 > 1) {
            throw new IllegalArgumentException("Parameters to, filters and suggestions are mutually exclusive");
        }
    }
}
