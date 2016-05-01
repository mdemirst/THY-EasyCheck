package com.thy.easycheck;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.thy.easycheck.dbHelper.DBHelper;
import com.thy.easycheck.lopaManager.LopaDrawer;
import com.thy.easycheck.model.LopaHandler;
import com.thy.easycheck.utils.HorizontialListView;

public class AddAircraftActivity extends Activity {

    public LopaDrawer lopaDraw;
    
    HorizontialListView lvSuitableLopaList;
    ListAdapter lvAdapter;
    
    private ArrayList<String> aircraftModels;
    private ArrayAdapter<String> aircraftModelsAdapter;
    
    ArrayList<LopaHandler> suitableLopas;
    EditText etAircraftName;
    Spinner  spModelNameList;
    
    EditText etAircraftLopaSelect;
    
    LopaHandler selectedLopa;
    
    private DBHelper db;
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        setContentView(R.layout.add_aircraft_screen);
        
        db = new DBHelper(getApplicationContext());
        
        Typeface fontAvesome = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
        Typeface fontSansRegular = Typeface.createFromAsset(getAssets(), "opensans_regular.ttf");
        Typeface fontSansLight = Typeface.createFromAsset(getAssets(), "opensans_light.ttf");
        
        TextView leftHeaderIcon = (TextView)findViewById( R.id.leftHeaderIcon );
        TextView leftHeaderText = (TextView)findViewById( R.id.leftHeaderText );
        TextView rightSettingsIcon = (TextView)findViewById( R.id.rightSettingsIcon );
        
        etAircraftName = (EditText) findViewById(R.id.et_new_aircraft_name);
        spModelNameList = (Spinner) findViewById(R.id.sp_airplane_model_spinner);
        etAircraftLopaSelect = (EditText) findViewById(R.id.et_new_aircraft_lopa_select);
        
        leftHeaderIcon.setTypeface(fontAvesome);
        rightSettingsIcon.setTypeface(fontAvesome);
        leftHeaderText.setTypeface(fontSansRegular);
        
        final Context ctx = this.getApplicationContext();
        
        selectedLopa = new LopaHandler();
        
        
        aircraftModelsAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item, db.getAircraftModels());
        spModelNameList.setAdapter(aircraftModelsAdapter);
        
        spModelNameList.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, 
                    int pos, long id) {
                
                
                ((Activity)view.getContext()).runOnUiThread(new Runnable()
                {
                   @Override
                   public void run()
                   {
                       aircraftModelsAdapter.clear();
                       
                       aircraftModelsAdapter.addAll( db.getAircraftModels());
                       
                       lvAdapter.notifyDataSetChanged();
                   }
                });
                
                
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        
        lvSuitableLopaList=(HorizontialListView)findViewById(R.id.lv_suitable_lopa_list);
        lvAdapter=new ListAdapter(this, R.layout.existing_lopa_item, suitableLopas);
        lvSuitableLopaList.setAdapter(lvAdapter);
        
        lvSuitableLopaList.setOnItemClickListener(new OnItemClickListener() {
            @SuppressWarnings("deprecation")
            @Override 
            public void onItemClick(AdapterView<?> arg0, final View arg1,final int position, long arg3)
            { 
                AlertDialog.Builder confirmSelectThisLopa = new AlertDialog.Builder(arg1.getContext());
                confirmSelectThisLopa.setMessage("Use this lopa?");
                confirmSelectThisLopa.setCancelable(true);
                confirmSelectThisLopa.setPositiveButton("Select",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ((Activity)arg1.getContext()).runOnUiThread(new Runnable()
                                {
                                   @Override
                                   public void run()
                                   {
                                       selectedLopa = (LopaHandler) lvSuitableLopaList.getItemAtPosition(position);
                                       etAircraftLopaSelect.setText(selectedLopa.getDefn().toString());
                                   }
                                });
                        }
                });
                confirmSelectThisLopa.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                        }
                });

                AlertDialog alert = confirmSelectThisLopa.create();
                alert.show();
            }
        });
        
    }
    

    
    private ArrayList<LopaHandler> getSuitableLopas(ArrayList<LopaHandler> allLopas, ArrayList<LopaHandler> suitableLopas, String keyword)
    {
        suitableLopas.clear();
        
        for(int i=0; i<allLopas.size(); i++)
        {
            if(allLopas.get(i).getDefn().contains(keyword))
                suitableLopas.add(allLopas.get(i));
        }
        
        return suitableLopas;
        
    }
    
    public class ListAdapter extends ArrayAdapter<LopaHandler> {

        public ListAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        public ListAdapter(Context context, int resource, List<LopaHandler> items) {
            super(context, resource, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {

                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.existing_lopa_item, null);

            }

            
            LopaDrawer lopaDraw = (LopaDrawer)v.findViewById(R.id.lopaDrawItem);
            
            lopaDraw.initializeLopa(getItem(position));
            lopaDraw.invalidate();
            
            TextView tvLopaDefn = (TextView) v.findViewById(R.id.tv_existingLopaItemDefn);

            
            tvLopaDefn.setText(getItem(position).getDefn().toString());


            return v;

        }
        
    }

}
