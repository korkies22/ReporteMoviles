// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import org.json.JSONException;
import de.cisha.chess.model.FEN;
import org.json.JSONObject;

public abstract class JSONAPIResultParser<E>
{
    protected boolean optBoolean(final JSONObject p0, final String p1, final boolean p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     0: aload_1        
        //     1: aload_2        
        //     2: invokevirtual   org/json/JSONObject.getBoolean:(Ljava/lang/String;)Z
        //     5: istore          5
        //     7: iload           5
        //     9: ireturn        
        //    10: aload_1        
        //    11: aload_2        
        //    12: invokevirtual   org/json/JSONObject.opt:(Ljava/lang/String;)Ljava/lang/Object;
        //    15: astore          6
        //    17: aload_1        
        //    18: aload_2        
        //    19: iconst_m1      
        //    20: invokevirtual   org/json/JSONObject.optInt:(Ljava/lang/String;I)I
        //    23: istore          4
        //    25: aload           6
        //    27: getstatic       org/json/JSONObject.NULL:Ljava/lang/Object;
        //    30: if_acmpeq       83
        //    33: iload           4
        //    35: ifne            41
        //    38: goto            83
        //    41: aload_1        
        //    42: aload_2        
        //    43: invokevirtual   org/json/JSONObject.getString:(Ljava/lang/String;)Ljava/lang/String;
        //    46: astore_1       
        //    47: aload_1        
        //    48: ldc             "false"
        //    50: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //    53: ifne            83
        //    56: aload_1        
        //    57: ldc             "0"
        //    59: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    62: ifne            83
        //    65: aload_1        
        //    66: ldc             ""
        //    68: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    71: istore          5
        //    73: iload           5
        //    75: ifeq            81
        //    78: goto            83
        //    81: iconst_1       
        //    82: ireturn        
        //    83: iconst_0       
        //    84: ireturn        
        //    85: astore          6
        //    87: goto            10
        //    90: astore_1       
        //    91: iload_3        
        //    92: ireturn        
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                    
        //  -----  -----  -----  -----  ------------------------
        //  0      7      85     93     Lorg/json/JSONException;
        //  41     73     90     93     Lorg/json/JSONException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0041:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2592)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:123)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    protected FEN optFEN(final JSONObject jsonObject, String optStringNotNull, final FEN fen) {
        optStringNotNull = this.optStringNotNull(jsonObject, optStringNotNull, null);
        FEN fen2 = fen;
        if (optStringNotNull != null) {
            fen2 = fen;
            if (!"".equals(optStringNotNull.trim())) {
                fen2 = new FEN(optStringNotNull);
            }
        }
        return fen2;
    }
    
    protected String optStringNotNull(final JSONObject jsonObject, final String s, final String s2) {
        final Object opt = jsonObject.opt(s);
        String optString = jsonObject.optString(s, s2);
        if (opt == null || opt == JSONObject.NULL) {
            optString = s2;
        }
        return optString;
    }
    
    public abstract E parseResult(final JSONObject p0) throws InvalidJsonForObjectException, JSONException;
}
