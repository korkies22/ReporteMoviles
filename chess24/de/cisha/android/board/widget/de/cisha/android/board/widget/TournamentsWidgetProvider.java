/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.PendingIntent
 *  android.appwidget.AppWidgetManager
 *  android.appwidget.AppWidgetProvider
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.widget.RemoteViews
 */
package de.cisha.android.board.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import de.cisha.android.board.MainActivity;
import de.cisha.android.board.widget.TournamentWidgetService;
import de.cisha.chess.util.Logger;

@TargetApi(value=14)
public class TournamentsWidgetProvider
extends AppWidgetProvider {
    private static final int REQUEST_CODE_START_MAIN_ACTIVITY = 44;
    private static final int REQUEST_CODE_START_TOURNAMENT_ACTIVITY = 3456;

    public PendingIntent getImagePendingIntent(Context context) {
        return PendingIntent.getActivity((Context)context, (int)44, (Intent)new Intent(context, MainActivity.class), (int)134217728);
    }

    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] arrn) {
        super.onUpdate(context, appWidgetManager, arrn);
        for (int n : arrn) {
            Logger logger = Logger.getInstance();
            String string = this.getClass().getName();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Updateing widget ");
            stringBuilder.append(n);
            logger.debug(string, stringBuilder.toString());
            logger = new RemoteViews(context.getPackageName(), 2131427570);
            logger.setOnClickPendingIntent(2131297181, this.getImagePendingIntent(context));
            string = new Intent(context, TournamentWidgetService.class);
            string.putExtra("appWidgetId", n);
            string.setData(Uri.parse((String)string.toUri(1)));
            logger.setRemoteAdapter(2131297182, (Intent)string);
            logger.setPendingIntentTemplate(2131297182, PendingIntent.getActivity((Context)context, (int)3456, (Intent)new Intent(context.getApplicationContext(), MainActivity.class), (int)134217728));
            appWidgetManager.updateAppWidget(n, (RemoteViews)logger);
            appWidgetManager.notifyAppWidgetViewDataChanged(n, 2131297182);
        }
    }
}
