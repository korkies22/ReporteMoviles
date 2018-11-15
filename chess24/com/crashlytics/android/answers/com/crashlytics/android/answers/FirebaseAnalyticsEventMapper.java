/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.crashlytics.android.answers;

import android.os.Bundle;
import com.crashlytics.android.answers.AddToCartEvent;
import com.crashlytics.android.answers.FirebaseAnalyticsEvent;
import com.crashlytics.android.answers.SessionEvent;
import io.fabric.sdk.android.Fabric;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class FirebaseAnalyticsEventMapper {
    private static final Set<String> EVENT_NAMES = new HashSet<String>(Arrays.asList("app_clear_data", "app_exception", "app_remove", "app_upgrade", "app_install", "app_update", "firebase_campaign", "error", "first_open", "first_visit", "in_app_purchase", "notification_dismiss", "notification_foreground", "notification_open", "notification_receive", "os_update", "session_start", "user_engagement", "ad_exposure", "adunit_exposure", "ad_query", "ad_activeview", "ad_impression", "ad_click", "screen_view", "firebase_extra_parameter"));
    private static final String FIREBASE_LEVEL_NAME = "level_name";
    private static final String FIREBASE_METHOD = "method";
    private static final String FIREBASE_RATING = "rating";
    private static final String FIREBASE_SUCCESS = "success";

    private String mapAttribute(String charSequence) {
        block4 : {
            block6 : {
                String string;
                block5 : {
                    if (charSequence == null || charSequence.length() == 0) break block4;
                    string = charSequence.replaceAll("[^\\p{Alnum}_]+", "_");
                    if (string.startsWith("ga_") || string.startsWith("google_") || string.startsWith("firebase_")) break block5;
                    charSequence = string;
                    if (Character.isLetter(string.charAt(0))) break block6;
                }
                charSequence = new StringBuilder();
                charSequence.append("fabric_");
                charSequence.append(string);
                charSequence = charSequence.toString();
            }
            if (charSequence.length() > 40) {
                return charSequence.substring(0, 40);
            }
            return charSequence;
        }
        return "fabric_unnamed_parameter";
    }

    private Integer mapBooleanValue(String string) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:659)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    private void mapCustomEventAttributes(Bundle bundle, Map<String, Object> object) {
        for (Map.Entry entry : object.entrySet()) {
            Object v = entry.getValue();
            String string = this.mapAttribute((String)entry.getKey());
            if (v instanceof String) {
                bundle.putString(string, entry.getValue().toString());
                continue;
            }
            if (v instanceof Double) {
                bundle.putDouble(string, ((Double)entry.getValue()).doubleValue());
                continue;
            }
            if (v instanceof Long) {
                bundle.putLong(string, ((Long)entry.getValue()).longValue());
                continue;
            }
            if (!(v instanceof Integer)) continue;
            bundle.putInt(string, ((Integer)entry.getValue()).intValue());
        }
    }

    private String mapCustomEventName(String charSequence) {
        block5 : {
            CharSequence charSequence2;
            block7 : {
                block6 : {
                    if (charSequence == null || charSequence.length() == 0) break block5;
                    if (EVENT_NAMES.contains(charSequence)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("fabric_");
                        stringBuilder.append((String)charSequence);
                        return stringBuilder.toString();
                    }
                    charSequence2 = charSequence.replaceAll("[^\\p{Alnum}_]+", "_");
                    if (charSequence2.startsWith("ga_") || charSequence2.startsWith("google_") || charSequence2.startsWith("firebase_")) break block6;
                    charSequence = charSequence2;
                    if (Character.isLetter(charSequence2.charAt(0))) break block7;
                }
                charSequence = new StringBuilder();
                charSequence.append("fabric_");
                charSequence.append((String)charSequence2);
                charSequence = charSequence.toString();
            }
            charSequence2 = charSequence;
            if (charSequence.length() > 40) {
                charSequence2 = charSequence.substring(0, 40);
            }
            return charSequence2;
        }
        return "fabric_unnamed_event";
    }

    private Double mapDouble(Object object) {
        if ((object = String.valueOf(object)) == null) {
            return null;
        }
        return Double.valueOf((String)object);
    }

    private Bundle mapPredefinedEvent(SessionEvent sessionEvent) {
        Bundle bundle = new Bundle();
        if ("purchase".equals(sessionEvent.predefinedType)) {
            this.putString(bundle, "item_id", (String)sessionEvent.predefinedAttributes.get("itemId"));
            this.putString(bundle, "item_name", (String)sessionEvent.predefinedAttributes.get("itemName"));
            this.putString(bundle, "item_category", (String)sessionEvent.predefinedAttributes.get("itemType"));
            this.putDouble(bundle, "value", this.mapPriceValue(sessionEvent.predefinedAttributes.get("itemPrice")));
            this.putString(bundle, "currency", (String)sessionEvent.predefinedAttributes.get("currency"));
        } else if ("addToCart".equals(sessionEvent.predefinedType)) {
            this.putString(bundle, "item_id", (String)sessionEvent.predefinedAttributes.get("itemId"));
            this.putString(bundle, "item_name", (String)sessionEvent.predefinedAttributes.get("itemName"));
            this.putString(bundle, "item_category", (String)sessionEvent.predefinedAttributes.get("itemType"));
            this.putDouble(bundle, "price", this.mapPriceValue(sessionEvent.predefinedAttributes.get("itemPrice")));
            this.putDouble(bundle, "value", this.mapPriceValue(sessionEvent.predefinedAttributes.get("itemPrice")));
            this.putString(bundle, "currency", (String)sessionEvent.predefinedAttributes.get("currency"));
            bundle.putLong("quantity", 1L);
        } else if ("startCheckout".equals(sessionEvent.predefinedType)) {
            this.putLong(bundle, "quantity", (long)((Integer)sessionEvent.predefinedAttributes.get("itemCount")));
            this.putDouble(bundle, "value", this.mapPriceValue(sessionEvent.predefinedAttributes.get("totalPrice")));
            this.putString(bundle, "currency", (String)sessionEvent.predefinedAttributes.get("currency"));
        } else if ("contentView".equals(sessionEvent.predefinedType)) {
            this.putString(bundle, "content_type", (String)sessionEvent.predefinedAttributes.get("contentType"));
            this.putString(bundle, "item_id", (String)sessionEvent.predefinedAttributes.get("contentId"));
            this.putString(bundle, "item_name", (String)sessionEvent.predefinedAttributes.get("contentName"));
        } else if ("search".equals(sessionEvent.predefinedType)) {
            this.putString(bundle, "search_term", (String)sessionEvent.predefinedAttributes.get("query"));
        } else if ("share".equals(sessionEvent.predefinedType)) {
            this.putString(bundle, FIREBASE_METHOD, (String)sessionEvent.predefinedAttributes.get(FIREBASE_METHOD));
            this.putString(bundle, "content_type", (String)sessionEvent.predefinedAttributes.get("contentType"));
            this.putString(bundle, "item_id", (String)sessionEvent.predefinedAttributes.get("contentId"));
            this.putString(bundle, "item_name", (String)sessionEvent.predefinedAttributes.get("contentName"));
        } else if (FIREBASE_RATING.equals(sessionEvent.predefinedType)) {
            this.putString(bundle, FIREBASE_RATING, String.valueOf(sessionEvent.predefinedAttributes.get(FIREBASE_RATING)));
            this.putString(bundle, "content_type", (String)sessionEvent.predefinedAttributes.get("contentType"));
            this.putString(bundle, "item_id", (String)sessionEvent.predefinedAttributes.get("contentId"));
            this.putString(bundle, "item_name", (String)sessionEvent.predefinedAttributes.get("contentName"));
        } else if ("signUp".equals(sessionEvent.predefinedType)) {
            this.putString(bundle, FIREBASE_METHOD, (String)sessionEvent.predefinedAttributes.get(FIREBASE_METHOD));
        } else if ("login".equals(sessionEvent.predefinedType)) {
            this.putString(bundle, FIREBASE_METHOD, (String)sessionEvent.predefinedAttributes.get(FIREBASE_METHOD));
        } else if ("invite".equals(sessionEvent.predefinedType)) {
            this.putString(bundle, FIREBASE_METHOD, (String)sessionEvent.predefinedAttributes.get(FIREBASE_METHOD));
        } else if ("levelStart".equals(sessionEvent.predefinedType)) {
            this.putString(bundle, FIREBASE_LEVEL_NAME, (String)sessionEvent.predefinedAttributes.get("levelName"));
        } else if ("levelEnd".equals(sessionEvent.predefinedType)) {
            this.putDouble(bundle, "score", this.mapDouble(sessionEvent.predefinedAttributes.get("score")));
            this.putString(bundle, FIREBASE_LEVEL_NAME, (String)sessionEvent.predefinedAttributes.get("levelName"));
            this.putInt(bundle, FIREBASE_SUCCESS, this.mapBooleanValue((String)sessionEvent.predefinedAttributes.get(FIREBASE_SUCCESS)));
        }
        this.mapCustomEventAttributes(bundle, sessionEvent.customAttributes);
        return bundle;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private String mapPredefinedEventName(String var1_1, boolean var2_2) {
        block39 : {
            block35 : {
                block38 : {
                    block36 : {
                        block37 : {
                            var4_3 = 1;
                            if (!var2_2) break block35;
                            var3_4 = var1_1.hashCode();
                            if (var3_4 == -902468296) break block36;
                            if (var3_4 == 103149417) break block37;
                            if (var3_4 != 1743324417 || !var1_1.equals("purchase")) ** GOTO lbl-1000
                            var3_4 = 0;
                            break block38;
                        }
                        if (!var1_1.equals("login")) ** GOTO lbl-1000
                        var3_4 = 2;
                        break block38;
                    }
                    if (var1_1.equals("signUp")) {
                        var3_4 = 1;
                    } else lbl-1000: // 3 sources:
                    {
                        var3_4 = -1;
                    }
                }
                switch (var3_4) {
                    default: {
                        ** break;
                    }
                    case 2: {
                        return "failed_login";
                    }
                    case 1: {
                        return "failed_sign_up";
                    }
                    case 0: 
                }
                return "failed_ecommerce_purchase";
            }
            switch (var1_1.hashCode()) {
                default: {
                    break;
                }
                case 1743324417: {
                    if (!var1_1.equals("purchase")) break;
                    var3_4 = 0;
                    break block39;
                }
                case 1664021448: {
                    if (!var1_1.equals("startCheckout")) break;
                    var3_4 = 2;
                    break block39;
                }
                case 196004670: {
                    if (!var1_1.equals("levelStart")) break;
                    var3_4 = 10;
                    break block39;
                }
                case 109400031: {
                    if (!var1_1.equals("share")) break;
                    var3_4 = 5;
                    break block39;
                }
                case 103149417: {
                    if (!var1_1.equals("login")) break;
                    var3_4 = 8;
                    break block39;
                }
                case 23457852: {
                    if (!var1_1.equals("addToCart")) break;
                    var3_4 = var4_3;
                    break block39;
                }
                case -389087554: {
                    if (!var1_1.equals("contentView")) break;
                    var3_4 = 3;
                    break block39;
                }
                case -902468296: {
                    if (!var1_1.equals("signUp")) break;
                    var3_4 = 7;
                    break block39;
                }
                case -906336856: {
                    if (!var1_1.equals("search")) break;
                    var3_4 = 4;
                    break block39;
                }
                case -938102371: {
                    if (!var1_1.equals("rating")) break;
                    var3_4 = 6;
                    break block39;
                }
                case -1183699191: {
                    if (!var1_1.equals("invite")) break;
                    var3_4 = 9;
                    break block39;
                }
                case -2131650889: {
                    if (!var1_1.equals("levelEnd")) break;
                    var3_4 = 11;
                    break block39;
                }
            }
            var3_4 = -1;
        }
        switch (var3_4) {
            default: {
                return this.mapCustomEventName(var1_1);
            }
            case 11: {
                return "level_end";
            }
            case 10: {
                return "level_start";
            }
            case 9: {
                return "invite";
            }
            case 8: {
                return "login";
            }
            case 7: {
                return "sign_up";
            }
            case 6: {
                return "rate_content";
            }
            case 5: {
                return "share";
            }
            case 4: {
                return "search";
            }
            case 3: {
                return "select_content";
            }
            case 2: {
                return "begin_checkout";
            }
            case 1: {
                return "add_to_cart";
            }
            case 0: 
        }
        return "ecommerce_purchase";
    }

    private Double mapPriceValue(Object object) {
        if ((object = (Long)object) == null) {
            return null;
        }
        return new BigDecimal(object.longValue()).divide(AddToCartEvent.MICRO_CONSTANT).doubleValue();
    }

    private void putDouble(Bundle bundle, String string, Double d) {
        if ((d = this.mapDouble(d)) == null) {
            return;
        }
        bundle.putDouble(string, d.doubleValue());
    }

    private void putInt(Bundle bundle, String string, Integer n) {
        if (n == null) {
            return;
        }
        bundle.putInt(string, n.intValue());
    }

    private void putLong(Bundle bundle, String string, Long l) {
        if (l == null) {
            return;
        }
        bundle.putLong(string, l.longValue());
    }

    private void putString(Bundle bundle, String string, String string2) {
        if (string2 == null) {
            return;
        }
        bundle.putString(string, string2);
    }

    public FirebaseAnalyticsEvent mapEvent(SessionEvent object) {
        Object object2;
        Bundle bundle;
        boolean bl = SessionEvent.Type.CUSTOM.equals((Object)object.type);
        boolean bl2 = false;
        boolean bl3 = bl && object.customType != null;
        boolean bl4 = SessionEvent.Type.PREDEFINED.equals((Object)object.type) && object.predefinedType != null;
        if (!bl3 && !bl4) {
            return null;
        }
        if (bl4) {
            bundle = this.mapPredefinedEvent((SessionEvent)object);
        } else {
            bundle = object2 = new Bundle();
            if (object.customAttributes != null) {
                this.mapCustomEventAttributes((Bundle)object2, object.customAttributes);
                bundle = object2;
            }
        }
        if (bl4) {
            object2 = (String)object.predefinedAttributes.get(FIREBASE_SUCCESS);
            bl = bl2;
            if (object2 != null) {
                bl = bl2;
                if (!Boolean.parseBoolean((String)object2)) {
                    bl = true;
                }
            }
            object = this.mapPredefinedEventName(object.predefinedType, bl);
        } else {
            object = this.mapCustomEventName(object.customType);
        }
        Fabric.getLogger().d("Answers", "Logging event into firebase...");
        return new FirebaseAnalyticsEvent((String)object, bundle);
    }
}
