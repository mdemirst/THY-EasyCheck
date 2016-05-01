package com.thy.easycheck;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.thy.easycheck.dbHelper.DBHelper;
import com.thy.easycheck.lopaManager.LopaEditView;
import com.thy.easycheck.model.LopaHandler;

public class CreateLopaActivity extends Activity{

    private LopaEditView lopaEditView;
    public LopaHandler lopaCreated;
    private Bitmap lopaTemplateImage;
    
    private EditText etSeatCount;
    
    private EditText etLopaName;
    private Spinner  spModelNameList;
    private EditText etVersionName;
    private EditText etNewAircraftModel;
    
    private Button btnChooseLopaTemplate;
    private Button btnSaveLopa;
    private Button btnResetLopa;
    private Button btnAddNewAircraftModel;
    
    private String lopaNameHint;
    
    private String choosenLopaTemplate;
    
    private ArrayList<String> aircraftModels;
    private ArrayAdapter<String> aircraftModelsAdapter;
    
    public String[] allLopaFiles;
    private String lopaScanPath ;
    private static final String FILE_TYPE = "*/*";
    private MediaScannerConnection conn;
    
    private final static int CHOOSE_TEMPLATE = 0;
    
    private DBHelper db;
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        setContentView(R.layout.create_new_lopa_screen);
        // TODO Auto-generated method stub
        
        db = new DBHelper(getApplicationContext());
        
        Typeface fontAvesome = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
        Typeface fontSansRegular = Typeface.createFromAsset(getAssets(), "opensans_regular.ttf");
        Typeface fontSansLight = Typeface.createFromAsset(getAssets(), "opensans_light.ttf");
        
        TextView leftHeaderIcon = (TextView)findViewById( R.id.leftHeaderIcon );
        TextView leftHeaderText = (TextView)findViewById( R.id.leftHeaderText );
        
        TextView middleEditLopaIcon = (TextView)findViewById( R.id.tv_middle_header_icon);
        TextView middleEditLopaText = (TextView)findViewById( R.id.tv_lopa_edit_text );
        
        TextView rightSettingsIcon = (TextView)findViewById( R.id.rightSettingsIcon );
        
        leftHeaderIcon.setTypeface(fontAvesome);
        rightSettingsIcon.setTypeface(fontAvesome);
        middleEditLopaIcon.setTypeface(fontAvesome);
        leftHeaderText.setTypeface(fontSansRegular);
        leftHeaderText.setTypeface(fontSansRegular);
        middleEditLopaText.setTypeface(fontSansRegular);
        
        
        etSeatCount = (EditText) findViewById(R.id.et_seat_count);
        etLopaName = (EditText) findViewById(R.id.et_lopa_name);
        spModelNameList = (Spinner) findViewById(R.id.sp_airplane_model_spinner);
        etVersionName = (EditText) findViewById(R.id.et_airplane_version_name);
        etNewAircraftModel = (EditText) findViewById(R.id.et_new_aircraft_model);
        
        btnChooseLopaTemplate = (Button) findViewById(R.id.bBtnChooseTemplate);
        btnSaveLopa = (Button) findViewById(R.id.bBtnSaveLopa);
        btnResetLopa = (Button) findViewById(R.id.bBtnResetLopa);
        btnAddNewAircraftModel = (Button) findViewById(R.id.bBtnAddNewAircraftModel);
        
        
        aircraftModelsAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item, db.getAircraftModels());
        spModelNameList.setAdapter(aircraftModelsAdapter);
          
        
        lopaEditView = (LopaEditView)findViewById(R.id.lopaTemplate);
        lopaTemplateImage = BitmapFactory.decodeResource(getResources(), R.drawable.a340dika).copy(Bitmap.Config.ARGB_8888, true);
        lopaCreated = new LopaHandler();
        lopaEditView.setLopaTemplate(lopaCreated, lopaTemplateImage);
        
        //Set lopa view dimensions
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) lopaEditView.getLayoutParams();
        params.height = lopaTemplateImage.getHeight();
        params.width = lopaTemplateImage.getWidth();
        lopaEditView.setLayoutParams(params);
        
        
        InventoryRunActivity.lopaDatabase = new ArrayList<LopaHandler>();
        
        
        
        
        File folder = new File(CreateLopaActivity.this.getExternalFilesDir(null)+"/lopas/");
        allLopaFiles = folder.list();

        
        lopaScanPath=CreateLopaActivity.this.getExternalFilesDir(null)+"/lopas/"+allLopaFiles[0];

        
        spModelNameList.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, 
                    int pos, long id) {
                updateLopaName();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        
        btnAddNewAircraftModel.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                db.addAircraftModel(etNewAircraftModel.getText().toString());
                aircraftModelsAdapter.clear();
                aircraftModelsAdapter.addAll(db.getAircraftModels());
                aircraftModelsAdapter.notifyDataSetChanged();
            }
        });
        
        
        etVersionName.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                updateLopaName();
            } 

        });
        
        btnChooseLopaTemplate.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                
                
                Intent chooseLopaTemplateIntent = new Intent(getApplicationContext(), LopasPopupActivity.class);
                startActivityForResult(chooseLopaTemplateIntent,CHOOSE_TEMPLATE);
            }
        });
        
        btnSaveLopa.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                

                lopaCreated.setDefn(etLopaName.getText().toString());
                lopaCreated.setModel(spModelNameList.getSelectedItem().toString());
                InventoryRunActivity.lopaDatabase.add(lopaCreated);
                
                db.addLopa(lopaCreated);
                
                
                Toast.makeText(getApplicationContext(),
                        "New lopa saved with name " + lopaCreated.getDefn(), Toast.LENGTH_LONG).show();
                
            }
        });
        
        btnResetLopa.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                
                lopaCreated = new LopaHandler();
                lopaEditView.setFilledSeatCount(0);
                setSeatCountLabel(0);
                if(!choosenLopaTemplate.equals(""))
                {
                    setLopaTemplate(choosenLopaTemplate);
                }
            }
        });
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
        case CHOOSE_TEMPLATE:
            if (resultCode == RESULT_OK) {
                choosenLopaTemplate = data.getStringExtra("FILE_PATH");
                setLopaTemplate(choosenLopaTemplate);
            }
            break;
        default:
            break;
        }
    }

    
    public void updateLopaName()
    {
        etLopaName.setText(spModelNameList.getSelectedItem().toString() + "_" +
                           etVersionName.getText().toString());
        
        
    }
    
    public void setLopaTemplate(String lopaTemplateImagePath)
    {
        lopaCreated = new LopaHandler();
        lopaTemplateImage = BitmapFactory.decodeFile(lopaTemplateImagePath).copy(Bitmap.Config.ARGB_8888, true);
        
        lopaEditView.setLopaTemplate(lopaCreated, lopaTemplateImage);
        
        //Set lopa view dimensions
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) lopaEditView.getLayoutParams();
        params.height = lopaTemplateImage.getHeight();
        params.width = lopaTemplateImage.getWidth();
        lopaEditView.setLayoutParams(params);
        
        lopaEditView.requestLayout();
    }
    
    public void setSeatCountLabel(int seatCount)
    {
        this.etSeatCount.setText(String.valueOf(seatCount));
    }
    

    public LopaHandler getLopaCreated() {
        return lopaCreated;
    }

    public void setLopaCreated(LopaHandler lopaCreated) {
        this.lopaCreated = lopaCreated;
    }

    public Bitmap getLopaTemplateImage() {
        return lopaTemplateImage;
    }

    public void setLopaTemplateImage(Bitmap lopaTemplateImage) {
        this.lopaTemplateImage = lopaTemplateImage;
    }

}
