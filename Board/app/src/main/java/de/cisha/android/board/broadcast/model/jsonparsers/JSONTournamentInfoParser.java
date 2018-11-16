// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model.jsonparsers;

import org.json.JSONException;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import org.json.JSONObject;
import de.cisha.android.board.broadcast.model.TournamentInfo;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;

public class JSONTournamentInfoParser extends JSONAPIResultParser<TournamentInfo>
{
    private String _language;
    private long _serverTimeDiff;
    
    public JSONTournamentInfoParser(final long serverTimeDiff, final String language) {
        this._language = "en";
        if (language != null) {
            this._language = language;
        }
        this._serverTimeDiff = serverTimeDiff;
    }
    
    @Override
    public TournamentInfo parseResult(final JSONObject p0) throws InvalidJsonForObjectException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     0: new             Lde/cisha/android/board/broadcast/model/TournamentID;
        //     3: dup            
        //     4: aload_1        
        //     5: ldc             "rkeyOfDetailedRecord"
        //     7: invokevirtual   org/json/JSONObject.getString:(Ljava/lang/String;)Ljava/lang/String;
        //    10: invokespecial   de/cisha/android/board/broadcast/model/TournamentID.<init>:(Ljava/lang/String;)V
        //    13: astore          14
        //    15: aload_1        
        //    16: ldc             "titles"
        //    18: invokevirtual   org/json/JSONObject.optJSONObject:(Ljava/lang/String;)Lorg/json/JSONObject;
        //    21: astore          10
        //    23: aconst_null    
        //    24: astore          12
        //    26: aload           10
        //    28: ifnull          471
        //    31: aload           10
        //    33: aload_0        
        //    34: getfield        de/cisha/android/board/broadcast/model/jsonparsers/JSONTournamentInfoParser._language:Ljava/lang/String;
        //    37: aconst_null    
        //    38: invokevirtual   org/json/JSONObject.optString:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //    41: astore          10
        //    43: goto            46
        //    46: aload           10
        //    48: astore          11
        //    50: aload           10
        //    52: ifnonnull       63
        //    55: aload_1        
        //    56: ldc             "title"
        //    58: invokevirtual   org/json/JSONObject.optString:(Ljava/lang/String;)Ljava/lang/String;
        //    61: astore          11
        //    63: aload_1        
        //    64: ldc             "logo"
        //    66: ldc             "false"
        //    68: invokevirtual   org/json/JSONObject.optString:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //    71: astore          13
        //    73: ldc             "false"
        //    75: aload           13
        //    77: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    80: istore          7
        //    82: aload           12
        //    84: astore          10
        //    86: iload           7
        //    88: ifne            183
        //    91: new             Ljava/net/URL;
        //    94: dup            
        //    95: aload           13
        //    97: invokespecial   java/net/URL.<init>:(Ljava/lang/String;)V
        //   100: astore          10
        //   102: aload           10
        //   104: astore          12
        //   106: goto            187
        //   109: aload           12
        //   111: astore          10
        //   113: aload           13
        //   115: ifnull          183
        //   118: aload           12
        //   120: astore          10
        //   122: aload           13
        //   124: invokevirtual   java/lang/String.isEmpty:()Z
        //   127: ifne            183
        //   130: aload           12
        //   132: astore          10
        //   134: aload           13
        //   136: ldc             "http"
        //   138: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //   141: ifne            183
        //   144: new             Ljava/lang/StringBuilder;
        //   147: dup            
        //   148: invokespecial   java/lang/StringBuilder.<init>:()V
        //   151: astore          10
        //   153: aload           10
        //   155: ldc             "https://chess24.com/"
        //   157: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   160: pop            
        //   161: aload           10
        //   163: aload           13
        //   165: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   168: pop            
        //   169: new             Ljava/net/URL;
        //   172: dup            
        //   173: aload           10
        //   175: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   178: invokespecial   java/net/URL.<init>:(Ljava/lang/String;)V
        //   181: astore          10
        //   183: aload           10
        //   185: astore          12
        //   187: aload_1        
        //   188: ldc             "numberOfRounds"
        //   190: invokevirtual   org/json/JSONObject.optInt:(Ljava/lang/String;)I
        //   193: istore_2       
        //   194: aload_1        
        //   195: ldc             "videoAvailable"
        //   197: iconst_0       
        //   198: invokevirtual   org/json/JSONObject.optBoolean:(Ljava/lang/String;Z)Z
        //   201: istore          7
        //   203: aload_1        
        //   204: ldc             "runningGames"
        //   206: invokevirtual   org/json/JSONObject.optInt:(Ljava/lang/String;)I
        //   209: istore_3       
        //   210: aload_1        
        //   211: ldc             "finishedGames"
        //   213: invokevirtual   org/json/JSONObject.optInt:(Ljava/lang/String;)I
        //   216: istore          4
        //   218: aload_1        
        //   219: ldc             "status"
        //   221: invokevirtual   org/json/JSONObject.optString:(Ljava/lang/String;)Ljava/lang/String;
        //   224: astore          13
        //   226: aload_1        
        //   227: ldc             "startOfNextGames"
        //   229: invokevirtual   org/json/JSONObject.optLong:(Ljava/lang/String;)J
        //   232: lstore          8
        //   234: lload           8
        //   236: lconst_0       
        //   237: lcmp           
        //   238: ifne            253
        //   241: invokestatic    java/lang/System.currentTimeMillis:()J
        //   244: ldc2_w          3600000
        //   247: ladd           
        //   248: lstore          8
        //   250: goto            477
        //   253: lload           8
        //   255: aload_0        
        //   256: getfield        de/cisha/android/board/broadcast/model/jsonparsers/JSONTournamentInfoParser._serverTimeDiff:J
        //   259: lsub           
        //   260: lstore          8
        //   262: goto            477
        //   265: getstatic       de/cisha/android/board/broadcast/model/TournamentState.ONGOING:Lde/cisha/android/board/broadcast/model/TournamentState;
        //   268: astore          10
        //   270: ldc             "upcoming"
        //   272: aload           13
        //   274: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   277: ifeq            288
        //   280: getstatic       de/cisha/android/board/broadcast/model/TournamentState.UPCOMING:Lde/cisha/android/board/broadcast/model/TournamentState;
        //   283: astore          10
        //   285: goto            480
        //   288: ldc             "finished"
        //   290: aload           13
        //   292: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   295: ifeq            306
        //   298: getstatic       de/cisha/android/board/broadcast/model/TournamentState.FINISHED:Lde/cisha/android/board/broadcast/model/TournamentState;
        //   301: astore          10
        //   303: goto            480
        //   306: ldc             "paused"
        //   308: aload           13
        //   310: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   313: ifeq            483
        //   316: getstatic       de/cisha/android/board/broadcast/model/TournamentState.PAUSED:Lde/cisha/android/board/broadcast/model/TournamentState;
        //   319: astore          10
        //   321: goto            480
        //   324: aload_1        
        //   325: ldc             "currentRound"
        //   327: invokevirtual   org/json/JSONObject.optInt:(Ljava/lang/String;)I
        //   330: istore          5
        //   332: new             Lde/cisha/android/board/broadcast/model/SimpleTournamentRoundInfo;
        //   335: dup            
        //   336: iload           5
        //   338: invokespecial   de/cisha/android/board/broadcast/model/SimpleTournamentRoundInfo.<init>:(I)V
        //   341: astore          13
        //   343: aload_1        
        //   344: ldc             "eventType"
        //   346: ldc             ""
        //   348: invokevirtual   org/json/JSONObject.optString:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   351: invokestatic    de/cisha/android/board/broadcast/model/jsonparsers/BroadcastTournamentType.fromKey:(Ljava/lang/String;)Lde/cisha/android/board/broadcast/model/jsonparsers/BroadcastTournamentType;
        //   354: astore          15
        //   356: getstatic       de/cisha/android/board/broadcast/model/jsonparsers/BroadcastTournamentType.MULTIKNOCKOUT:Lde/cisha/android/board/broadcast/model/jsonparsers/BroadcastTournamentType;
        //   359: aload           15
        //   361: if_acmpne       486
        //   364: aload_1        
        //   365: ldc             "currentGame"
        //   367: invokevirtual   org/json/JSONObject.optInt:(Ljava/lang/String;)I
        //   370: istore          6
        //   372: new             Lde/cisha/android/board/broadcast/model/MultiGameKnockOutTournamentRoundInfo;
        //   375: dup            
        //   376: new             Lde/cisha/android/board/broadcast/model/MainKnockoutRoundInfo;
        //   379: dup            
        //   380: iload           5
        //   382: invokespecial   de/cisha/android/board/broadcast/model/MainKnockoutRoundInfo.<init>:(I)V
        //   385: iload           6
        //   387: invokespecial   de/cisha/android/board/broadcast/model/MultiGameKnockOutTournamentRoundInfo.<init>:(Lde/cisha/android/board/broadcast/model/MainKnockoutRoundInfo;I)V
        //   390: astore_1       
        //   391: goto            394
        //   394: new             Lde/cisha/android/board/broadcast/model/TournamentInfo;
        //   397: dup            
        //   398: aload           14
        //   400: aload           11
        //   402: iload_2        
        //   403: aload_1        
        //   404: iload_3        
        //   405: iload           4
        //   407: aload           12
        //   409: iload           7
        //   411: aload           10
        //   413: new             Ljava/util/Date;
        //   416: dup            
        //   417: lload           8
        //   419: invokespecial   java/util/Date.<init>:(J)V
        //   422: invokespecial   de/cisha/android/board/broadcast/model/TournamentInfo.<init>:(Lde/cisha/android/board/broadcast/model/TournamentID;Ljava/lang/String;ILde/cisha/android/board/broadcast/model/TournamentRoundInfo;IILjava/net/URL;ZLde/cisha/android/board/broadcast/model/TournamentState;Ljava/util/Date;)V
        //   425: astore_1       
        //   426: aload_1        
        //   427: areturn        
        //   428: astore_1       
        //   429: invokestatic    de/cisha/chess/util/Logger.getInstance:()Lde/cisha/chess/util/Logger;
        //   432: ldc             Lde/cisha/android/board/broadcast/model/jsonparsers/JSONTournamentInfoParser;.class
        //   434: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //   437: ldc             Lorg/json/JSONException;.class
        //   439: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //   442: aload_1        
        //   443: invokevirtual   de/cisha/chess/util/Logger.debug:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
        //   446: new             Lde/cisha/android/board/service/jsonparser/InvalidJsonForObjectException;
        //   449: dup            
        //   450: ldc             "Error parsing Tournament Info JSON:"
        //   452: aload_1        
        //   453: invokespecial   de/cisha/android/board/service/jsonparser/InvalidJsonForObjectException.<init>:(Ljava/lang/String;Ljava/lang/Throwable;)V
        //   456: athrow         
        //   457: astore          10
        //   459: goto            109
        //   462: astore          10
        //   464: aload           12
        //   466: astore          10
        //   468: goto            183
        //   471: aconst_null    
        //   472: astore          10
        //   474: goto            46
        //   477: goto            265
        //   480: goto            324
        //   483: goto            324
        //   486: aload           13
        //   488: astore_1       
        //   489: goto            394
        //    Exceptions:
        //  throws de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                            
        //  -----  -----  -----  -----  --------------------------------
        //  0      23     428    457    Lorg/json/JSONException;
        //  31     43     428    457    Lorg/json/JSONException;
        //  55     63     428    457    Lorg/json/JSONException;
        //  63     82     428    457    Lorg/json/JSONException;
        //  91     102    457    471    Ljava/net/MalformedURLException;
        //  91     102    428    457    Lorg/json/JSONException;
        //  122    130    462    471    Ljava/net/MalformedURLException;
        //  122    130    428    457    Lorg/json/JSONException;
        //  134    183    462    471    Ljava/net/MalformedURLException;
        //  134    183    428    457    Lorg/json/JSONException;
        //  187    234    428    457    Lorg/json/JSONException;
        //  241    250    428    457    Lorg/json/JSONException;
        //  253    262    428    457    Lorg/json/JSONException;
        //  265    285    428    457    Lorg/json/JSONException;
        //  288    303    428    457    Lorg/json/JSONException;
        //  306    321    428    457    Lorg/json/JSONException;
        //  324    391    428    457    Lorg/json/JSONException;
        //  394    426    428    457    Lorg/json/JSONException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IndexOutOfBoundsException: Index: 229, Size: 229
        //     at java.util.ArrayList.rangeCheck(Unknown Source)
        //     at java.util.ArrayList.get(Unknown Source)
        //     at com.strobel.decompiler.ast.AstBuilder.convertToAst(AstBuilder.java:3303)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:113)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
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
}
