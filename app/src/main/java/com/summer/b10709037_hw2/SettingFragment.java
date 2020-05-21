package com.summer.b10709037_hw2;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;
import android.os.Bundle;
public class SettingFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_waitlist);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

    }
}
