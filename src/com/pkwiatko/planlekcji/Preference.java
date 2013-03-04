package com.pkwiatko.planlekcji;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.Menu;


import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.widget.Toast;



public class Preference extends SherlockPreferenceActivity implements OnSharedPreferenceChangeListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {    	
    	setTheme(MainActivity.THEME);
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {        
        return true;
    }
    
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
            String key) {
    	
    	if ("theme_preference".equals(key))	{
    		int themeId = R.style.Theme_Sherlock;
    		String theme = sharedPreferences.getString("theme_preference", "default");
    		if ("default".equals(theme))	{
    			themeId=R.style.Theme_Sherlock;
    		} else if ("light".equals(theme))	{
    			themeId = R.style.Theme_Sherlock_Light;
    		} else if ("light_dark".equals(theme))	{
    			themeId = R.style.Theme_Sherlock_Light_DarkActionBar;
    		}
    		MainActivity.THEME =  themeId;
    		setTheme(MainActivity.THEME);
    	}
    	 

    }
}
