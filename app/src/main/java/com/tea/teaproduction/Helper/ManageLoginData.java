package com.tea.teaproduction.Helper;

import android.util.Log;

import com.tea.teaproduction.ManageSharedPreferenceData.YoDB;
import com.tea.teaproduction.utils.Constants;

public class ManageLoginData {
    public static void addLoginData(String id, String name,String role) {
        YoDB.getPref().write(Constants.ID, "", id);
        YoDB.getPref().write(Constants.NAME, "", name);
        YoDB.getPref().write(Constants.ROLE, "", role);
    }

    public static void clearLoginData() {
        //Login DB
        YoDB.getPref().clear(Constants.ID);
        YoDB.getPref().clear(Constants.NAME);
        YoDB.getPref().clear(Constants.ROLE);

    }
}
