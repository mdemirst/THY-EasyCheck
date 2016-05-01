package com.thy.easycheck;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.thy.easycheck.model.LopaHandler;



public class InventoryRunActivity extends ActionBarActivity {
    
    private PieChart inventoryChart;
    
    public static ArrayList<LopaHandler> lopaDatabase;
    
    

    protected String[] mStates = new String[] {
            "Not expired", "Expire soon", "Expired", "Uncounted"
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_run_screen);
        
        Typeface fontAvesome = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
        Typeface fontSansRegular = Typeface.createFromAsset(getAssets(), "opensans_regular.ttf");
        Typeface fontSansLight = Typeface.createFromAsset(getAssets(), "opensans_light.ttf");
        
        TextView leftHeaderIcon = (TextView)findViewById( R.id.leftHeaderIcon );
        TextView leftHeaderText = (TextView)findViewById( R.id.leftHeaderText );
        TextView rightSettingsIcon = (TextView)findViewById( R.id.rightSettingsIcon );
        
        leftHeaderIcon.setTypeface(fontAvesome);
        rightSettingsIcon.setTypeface(fontAvesome);
        leftHeaderText.setTypeface(fontSansRegular);
        
        inventoryChart = (PieChart) findViewById(R.id.chart);
        inventoryChart.setDescription("Inventory Results");
        inventoryChart.setHoleColor(Color.rgb(235, 235, 235));
        inventoryChart.setValueTypeface(fontSansRegular);
        inventoryChart.setCenterTextTypeface(fontSansLight);
        inventoryChart.setHoleRadius(30f);
        inventoryChart.setDescription("");
        inventoryChart.setDrawYValues(true);
        inventoryChart.setDrawCenterText(true);
        inventoryChart.setDrawHoleEnabled(true);
        inventoryChart.setRotationAngle(0);
        inventoryChart.setDrawXValues(true);
        inventoryChart.setRotationEnabled(true);
        inventoryChart.setUsePercentValues(true);
        inventoryChart.setCenterText("THY RF-ID");
        
        setChartData(3,3);

    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.inventoryRunScreen: {
                Intent i = new Intent(getApplicationContext(),InventoryRunActivity.class);
                startActivity(i);
                break;
            }
            case R.id.inventoryEntryScreen: {
                Intent i = new Intent(getApplicationContext(),InventoryMainActivity.class);
                startActivity(i);
                break;
            }
            case R.id.settingsScreen: {
                Intent i = new Intent(getApplicationContext(),SettingsActivity.class);
                startActivity(i);
                break;
            }
            case R.id.lopaEntryScreen: {
                Intent i = new Intent(getApplicationContext(),LopaManagerMainActivity.class);
                startActivity(i);
                break;
            }
            case R.id.lopaAddPlaneScreen: {
                Intent i = new Intent(getApplicationContext(),AddAircraftActivity.class);
                startActivity(i);
                break;
            }
            case R.id.lopaCreateScreen: {
                Intent i = new Intent(getApplicationContext(),CreateLopaActivity.class);
                startActivity(i);
                break;
            }
            case R.id.lopaShowLopasScreen: {
                Intent i = new Intent(getApplicationContext(),ViewLopaActivity.class);
                startActivity(i);
                break;
            }
            case R.id.showFlatUI: {
                Intent i = new Intent(getApplicationContext(),FlatUIExamples.class);
                startActivity(i);
            }
        }
        return true;
    }
    
    private void setChartData(int count, float range) {

        float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        for (int i = 0; i < count + 1; i++) {
            yVals1.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
        }

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < count + 1; i++)
            xVals.add(mStates[i % mStates.length]);

        PieDataSet set1 = new PieDataSet(yVals1, "Inventory Results");
        set1.setSliceSpace(10f);
        

        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.RED);
        colors.add(Color.GRAY);
        set1.setColors(colors);

        PieData data = new PieData(xVals, set1);
        inventoryChart.setData(data);

        // undo all highlights
        inventoryChart.highlightValues(null);

        inventoryChart.invalidate();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
