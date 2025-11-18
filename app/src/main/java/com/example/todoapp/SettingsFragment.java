package com.example.todoapp;


import android.content.Intent;
import android.os.Bundle;


import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;


public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference_settings, rootKey);


        Preference statsPref = findPreference("pref_view_statistics");
        if (statsPref != null) {
            statsPref.setOnPreferenceClickListener(preference -> {
                Intent intent = new Intent(getActivity(), com.example.todoapp.StatisticsActivity.class);
                startActivity(intent);
                return true;
            });
        }
    }
}