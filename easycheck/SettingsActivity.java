package com.thy.easycheck;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class SettingsActivity extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        setContentView(R.layout.settings_screen);
        
        Typeface fontAvesome = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
        Typeface fontSansRegular = Typeface.createFromAsset(getAssets(), "opensans_regular.ttf");
        Typeface fontSansLight = Typeface.createFromAsset(getAssets(), "opensans_light.ttf");
        
        TextView leftHeaderIcon = (TextView)findViewById( R.id.leftHeaderIcon );
        TextView leftHeaderText = (TextView)findViewById( R.id.leftHeaderText );
        TextView rightSettingsIcon = (TextView)findViewById( R.id.rightSettingsIcon );
        
        leftHeaderIcon.setTypeface(fontAvesome);
        rightSettingsIcon.setTypeface(fontAvesome);
        leftHeaderText.setTypeface(fontSansRegular);
    }

}
