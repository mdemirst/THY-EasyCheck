package com.thy.easycheck.lopaManager;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.ViewGroup;

import com.thy.easycheck.R;
import com.thy.easycheck.model.LopaHandler;


public class LopaManager extends ActionBarActivity {

    
    
    private LopaEditView lopaEditView;
    public static LopaHandler lastLopa;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_lopa_screen);
        
        lopaEditView = (LopaEditView)findViewById(R.id.lopaTemplate);
        
        //Set lopa view dimensions
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams)
        lopaEditView.getLayoutParams();
        params.height = lopaEditView.lopaTemplateImage.getHeight();
        params.width = lopaEditView.lopaTemplateImage.getWidth();
        lopaEditView.setLayoutParams(params);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
