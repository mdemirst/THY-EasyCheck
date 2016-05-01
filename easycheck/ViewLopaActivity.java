package com.thy.easycheck;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.thy.easycheck.dbHelper.DBHelper;
import com.thy.easycheck.lopaManager.LopaDrawer;
import com.thy.easycheck.model.LopaHandler;
import com.thy.easycheck.utils.HorizontialListView;

public class ViewLopaActivity extends Activity {

    
    
    public LopaDrawer lopaDraw;
    
    HorizontialListView lvExistingLopaList;
    ListAdapter lvAdapter;
    
    Button btnDeleteLopa;
    TextView tvLopaDefn;
    
    private DBHelper db;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        setContentView(R.layout.view_lopas_screen);
        
        db = new DBHelper(getApplicationContext());
        
        lvExistingLopaList=(HorizontialListView)findViewById(R.id.lv_existing_lopa_list);
        //lvAdapter=new ListAdapter(this, R.layout.existing_lopa_item, InventoryRunActivity.lopaDatabase);
        lvAdapter=new ListAdapter(this, R.layout.existing_lopa_item, db.getLopas());
        lvExistingLopaList.setAdapter(lvAdapter);
        
        lvExistingLopaList.setOnItemClickListener(new OnItemClickListener() {
            @SuppressWarnings("deprecation")
            @Override 
            public void onItemClick(AdapterView<?> arg0, final View arg1,final int position, long arg3)
            { 
                AlertDialog.Builder confirmDeleteLopa = new AlertDialog.Builder(arg1.getContext());
                confirmDeleteLopa.setMessage("Delete lopa?");
                confirmDeleteLopa.setCancelable(true);
                confirmDeleteLopa.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ((Activity)arg1.getContext()).runOnUiThread(new Runnable()
                                {
                                   @Override
                                   public void run()
                                   {
                                       db.deleteLopa((LopaHandler)lvExistingLopaList.getItemAtPosition(position));
                                       lvAdapter.clear();
                                       lvAdapter.addAll(db.getLopas());
                                       lvAdapter.notifyDataSetChanged();
                                   }
                                });
                        }
                });
                confirmDeleteLopa.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                        }
                });

                AlertDialog alert = confirmDeleteLopa.create();
                alert.show();
            }
        });
        
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
