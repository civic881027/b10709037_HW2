package com.summer.b10709037_hw2;

import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
public class SettingFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    ListPreference lp;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_waitlist);
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getActivity());
        lp=(ListPreference)findPreference("color");
        lp.setSummary(pref.getString("color","red"));
        pref.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        lp.setSummary(sharedPreferences.getString(key,"red"));
    }
}
