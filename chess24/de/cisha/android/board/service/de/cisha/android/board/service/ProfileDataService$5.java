/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import de.cisha.android.board.profile.model.DashboardData;
import de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader;
import org.json.JSONObject;

class ProfileDataService
implements GeneralJSONAPICommandLoader.JSONOutputListener<DashboardData> {
    ProfileDataService() {
    }

    @Override
    public void output(DashboardData dashboardData, JSONObject jSONObject) {
        ProfileDataService.this.storeDashboardData(jSONObject);
    }
}
