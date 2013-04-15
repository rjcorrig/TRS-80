/*
 * Copyright 2012-2013, Arno Puder
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.puder.trs80;

import org.puder.trs80.browser.FileBrowserActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SettingsActivity extends PreferenceActivity implements OnPreferenceChangeListener {

    public static final String SHARED_PREF_NAME = "Settings";

    public static final String CONF_ROM_MODEL1  = "conf_rom_model1";
    public static final String CONF_ROM_MODEL3  = "conf_rom_model3";
    public static final String CONF_ROM_MODEL4  = "conf_rom_model4";
    public static final String CONF_ROM_MODEL4P = "conf_rom_model4p";

    private SharedPreferences  sharedPrefs;
    private Preference         romModel1;
    private Preference         romModel3;
    private Preference         romModel4;
    private Preference         romModel4p;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        PreferenceManager mgr = this.getPreferenceManager();
        mgr.setSharedPreferencesName(SHARED_PREF_NAME);
        sharedPrefs = mgr.getSharedPreferences();
        Preference.OnPreferenceClickListener listener = new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference pref) {
                Intent intent = new Intent(SettingsActivity.this, FileBrowserActivity.class);
                String key = pref.getKey();
                startActivityForResult(intent, key.hashCode());
                return true;
            }
        };
        romModel1 = (Preference) findPreference(CONF_ROM_MODEL1);
        romModel1.setOnPreferenceChangeListener(this);
        romModel1.setOnPreferenceClickListener(listener);

        romModel3 = (Preference) findPreference(CONF_ROM_MODEL3);
        romModel3.setOnPreferenceChangeListener(this);
        romModel3.setOnPreferenceClickListener(listener);

        romModel4 = (Preference) findPreference(CONF_ROM_MODEL4);
        romModel4.setOnPreferenceChangeListener(this);
        romModel4.setOnPreferenceClickListener(listener);

        romModel4p = (Preference) findPreference(CONF_ROM_MODEL4P);
        romModel4p.setOnPreferenceChangeListener(this);
        romModel4p.setOnPreferenceClickListener(listener);
        
        updateSummaries();
    }

    private void updateSummaries() {
        String val = sharedPrefs.getString(CONF_ROM_MODEL1, null);
        if (val != null) {
            romModel1.setSummary(val);
        }
        val = sharedPrefs.getString(CONF_ROM_MODEL3, null);
        if (val != null) {
            romModel3.setSummary(val);
        }
        val = sharedPrefs.getString(CONF_ROM_MODEL4, null);
        if (val != null) {
            romModel4.setSummary(val);
        }
        val = sharedPrefs.getString(CONF_ROM_MODEL4P, null);
        if (val != null) {
            romModel4p.setSummary(val);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String newValue = data.getStringExtra("PATH");
            SharedPreferences.Editor editor = sharedPrefs.edit();
            String key;
            if (CONF_ROM_MODEL1.hashCode() == requestCode) {
                editor.putString(CONF_ROM_MODEL1, newValue);
            }
            if (CONF_ROM_MODEL3.hashCode() == requestCode) {
                editor.putString(CONF_ROM_MODEL3, newValue);
            }
            if (CONF_ROM_MODEL4.hashCode() == requestCode) {
                editor.putString(CONF_ROM_MODEL4, newValue);
            }
            if (CONF_ROM_MODEL4P.hashCode() == requestCode) {
                editor.putString(CONF_ROM_MODEL4P, newValue);
            }
            editor.commit();
            updateSummaries();
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        preference.setSummary(newValue.toString());
        return true;
    }

}
