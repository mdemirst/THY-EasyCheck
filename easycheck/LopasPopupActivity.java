package com.thy.easycheck;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.thy.easycheck.lazylist.LazyAdapter;
import com.thy.easycheck.utils.HorizontialListView;

public class LopasPopupActivity extends Activity {

    
    File file;
    HorizontialListView lvLopaTemplateList;
    LazyAdapter lvAdapter;
    
    private String[] mFileStrings;
    private File[] listFile;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.lopa_template_list);
        
        Typeface fontAvesome = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
        Typeface fontSansRegular = Typeface.createFromAsset(getAssets(), "opensans_regular.ttf");
        
        TextView existingLopaTemplateSelectIcon = (TextView)findViewById( R.id.tvExistingLopaTemplateSelectIcon );
        TextView existingLopaTemplateSelectText = (TextView)findViewById( R.id.tvExistingLopaTemplateSelectText );
        
        existingLopaTemplateSelectIcon.setTypeface(fontAvesome);
        existingLopaTemplateSelectText.setTypeface(fontSansRegular);
    
        
        // Check for SD Card
        if (!Environment.getExternalStorageState().equals(
                     Environment.MEDIA_MOUNTED)) {
               Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG)
                            .show();
        } else {
               file = new File(Environment.getExternalStorageDirectory()
                            + File.separator + "/lopas/");
               file.mkdirs();
        }

        if (file.isDirectory())
        {
            listFile = file.listFiles();
            mFileStrings = new String[listFile.length];

            for (int i = 0; i < listFile.length; i++)
            {
                mFileStrings[i] = listFile[i].getAbsolutePath();
            }
        }
        
        lvLopaTemplateList=(HorizontialListView)findViewById(R.id.lv_lopa_template_list);
        lvAdapter=new LazyAdapter(this, mFileStrings);
        lvLopaTemplateList.setAdapter(lvAdapter);
        
        
        lvLopaTemplateList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                
                String lopaTemplateAddress = (String) lvLopaTemplateList.getItemAtPosition(position);
                
                Intent intent = new Intent();
                intent.putExtra("FILE_PATH", lopaTemplateAddress);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        
        
        
    }
    
    
 
}
