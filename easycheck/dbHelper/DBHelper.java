package com.thy.easycheck.dbHelper;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Point;
import android.graphics.Rect;

import com.thy.easycheck.model.Aircraft;
import com.thy.easycheck.model.Employee;
import com.thy.easycheck.model.Inventory;
import com.thy.easycheck.model.InventoryResult;
import com.thy.easycheck.model.LopaHandler;
import com.thy.easycheck.model.PlaneTag;
import com.thy.easycheck.model.Seat;
import com.thy.easycheck.model.Seat.SeatStatus;
import com.thy.easycheck.model.ThyAircraft;

public class DBHelper extends SQLiteOpenHelper{
	
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 7;
	
	// Database Name
	private static final String DATABASE_NAME = "database";
	
	// Table Names
	private static final String TABLE_EMPLOYEES = "employees";
	private static final String TABLE_PLANE_TAGS = "plane_tags";
	private static final String TABLE_INVENTORY_RESULTS = "inventory_results";
	private static final String TABLE_INVENTORIES = "inventories";
	private static final String TABLE_LOPAS = "lopas";
	private static final String TABLE_SEATS = "seats";
	private static final String TABLE_THY_FLEET = "thy_fleet";
	private static final String TABLE_AIRCRAFTS = "aircrafts";
	private static final String TABLE_AIRCRAFT_MODELS = "aircrafts_models";
	
	
	// EMPLOYEES Table Columns names
	private static final String KEY_EMPLOYEES_TAG_ID = "employee_tag_id";
	private static final String KEY_EMPLOYEES_LOCATION = "location";
	private static final String KEY_EMPLOYEES_LOGIN_DATE = "login_date";
	private static final String KEY_EMPLOYEES_USERNAME = "username";
	private static final String KEY_EMPLOYEES_PASSWORD = "password";
	
	// PLANE TAGS Table Columns names
	private static final String KEY_TAGS_TAG_ID = "tag_id";
	private static final String KEY_TAGS_PN_NUMBER = "pn_number";
	private static final String KEY_TAGS_SN_NUMBER = "sn_number";
	private static final String KEY_TAGS_EXP_DATE = "expiration_date";
	private static final String KEY_TAGS_AIRCRAFT_CODE = "aircraft_code";
	private static final String KEY_TAGS_TYPE = "type";
	private static final String KEY_TAGS_EMPLOYEE_TAG_ID = "employee_tag_id";
	
	// INVENTORY RESULTS Table Columns names
	private static final String KEY_RESULTS_AIRCRAFT_CODE = "aircraft_code";
	private static final String KEY_RESULTS_SCAN_DATE = "scan_date";
	private static final String KEY_RESULTS_EMPLOYEE_TAG_ID = "employee_tag_id";
	private static final String KEY_RESULTS_TOTAL_SEAT_COUNT = "total_seat_count";
	private static final String KEY_RESULTS_EXPIRED_COUNT = "expired_count";
	private static final String KEY_RESULTS_EXPIRE_SOON_COUNT = "expire_soon_coun";
	private static final String KEY_RESULTS_TAG_FOUND_COUNT = "tag_found_count";
	
	// INVENTORIES Table Columns names
	private static final String KEY_INVENTORIES_AIRCRAFT_CODE = "aircraft_code";
	private static final String KEY_INVENTORIES_SCAN_DATE = "scan_date";
	private static final String KEY_INVENTORIES_TAG_ID = "tag_id";
	
	// LOPAS Table Columns names
	private static final String KEY_LOPAS_ID = "id";
	private static final String KEY_LOPAS_DEFN= "defn";
	private static final String KEY_LOPAS_MODEL= "model";
	
	// SEATS Table Columns names
    private static final String KEY_SEATS_ID = "id";
    private static final String KEY_SEATS_POS_X = "pos_x";
    private static final String KEY_SEATS_POS_Y = "pos_y";
    private static final String KEY_SEATS_RECT_L = "rect_l";
    private static final String KEY_SEATS_RECT_R = "rect_r";
    private static final String KEY_SEATS_RECT_T = "rect_t";
    private static final String KEY_SEATS_RECT_B = "rect_b";
    private static final String KEY_SEATS_STATUS = "status";
    private static final String KEY_SEATS_LOPA_ID = "lopa_id";
	
	// THY FLEET Table Columns names
	private static final String KEY_FLEET_AIRCRAFT_CODE= "aircraft_code";
	private static final String KEY_FLEET_AIRCRAFT_TYPE = "aircraft_type";		

	// AIRCRAFTS Table Columns names
	private static final String KEY_AIRCRAFTS_AIRCRAFT_ID = "aircraft_id";
	private static final String KEY_AIRCRAFTS_AIRCRAFT_DEFN = "aircraft_definition";
	private static final String KEY_AIRCRAFTS_IMG_LOCATION = "image_location";
	
	// AIRCRAFT MODELS Table Columns names
    private static final String KEY_AIRCRAFT_MODELS_ID = "id";
    private static final String KEY_AIRCRAFT_MODELS_DEFN = "defn";
	
	
	
    // Table Create Statements
    // EMPLOYEES table create statement
    private static final String CREATE_TABLE_EMPLOYEES = "CREATE TABLE "
            + TABLE_EMPLOYEES + "(" 
    		+ KEY_EMPLOYEES_TAG_ID      + " TEXT PRIMARY KEY," 
            + KEY_EMPLOYEES_LOCATION    + " TEXT NOT NULL," 
            + KEY_EMPLOYEES_LOGIN_DATE  + " DATETIME,"
            + KEY_EMPLOYEES_USERNAME    + " TEXT," 
            + KEY_EMPLOYEES_PASSWORD    + " TEXT " 
            + ")";
    
    // PLANE TAGS table create statement
    private static final String CREATE_TABLE_PLANE_TAGS = "CREATE TABLE "
            + TABLE_PLANE_TAGS         + "(" 
    		+ KEY_TAGS_TAG_ID          + " TEXT PRIMARY KEY," 
            + KEY_TAGS_PN_NUMBER       + " TEXT," 
            + KEY_TAGS_SN_NUMBER       + " TEXT,"
            + KEY_TAGS_EXP_DATE        + " DATETIME NOT NULL," 
            + KEY_TAGS_AIRCRAFT_CODE   + " TEXT," 
            + KEY_TAGS_TYPE            + " TEXT NOT NULL," 
            + KEY_TAGS_EMPLOYEE_TAG_ID + " TEXT NOT NULL "
            + ")";
    
    // INVENTORY RESULTS table create statement
    private static final String CREATE_TABLE_INVENTORY_RESULTS = "CREATE TABLE "
            + TABLE_INVENTORY_RESULTS + "(" 
    		+ KEY_RESULTS_AIRCRAFT_CODE      + " TEXT NOT NULL," 
            + KEY_RESULTS_SCAN_DATE          + " DATETIME NOT NULL," 
            + KEY_RESULTS_EMPLOYEE_TAG_ID    + " TEXT NOT NULL,"
            + KEY_RESULTS_TOTAL_SEAT_COUNT   + " INTEGER," 
            + KEY_RESULTS_EXPIRED_COUNT      + " INTEGER," 
            + KEY_RESULTS_EXPIRE_SOON_COUNT  + " INTEGER,"
            + KEY_RESULTS_TAG_FOUND_COUNT    + " INTEGER NOT NULL, "
            + "PRIMARY KEY ( " + KEY_RESULTS_AIRCRAFT_CODE + ", " 
            				   + KEY_RESULTS_SCAN_DATE + ") "
            + ")";
    
    // INVENTORIES table create statement
    private static final String CREATE_TABLE_INVENTORIES = "CREATE TABLE "
            + TABLE_INVENTORIES + "(" 
    		+ KEY_INVENTORIES_AIRCRAFT_CODE   + " TEXT NOT NULL," 
            + KEY_INVENTORIES_SCAN_DATE       + " DATETIME NOT NULL," 
            + KEY_INVENTORIES_TAG_ID          + " TEXT NOT NULL, "
            + "PRIMARY KEY ( " + KEY_INVENTORIES_AIRCRAFT_CODE + ", " 
            				   + KEY_INVENTORIES_SCAN_DATE + ", " 
            				   + KEY_INVENTORIES_TAG_ID + ") "
            + ")";
    
    // LOPAS table create statement
    private static final String CREATE_TABLE_LOPAS = "CREATE TABLE "
            + TABLE_LOPAS + "(" 
    		+ KEY_LOPAS_ID      + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," 
            + KEY_LOPAS_DEFN    + " TEXT NOT NULL," 
            + KEY_LOPAS_MODEL   + " TEXT NOT NULL" 
            + ")";
    
    // SEATS table create statement
    private static final String CREATE_TABLE_SEATS = "CREATE TABLE "
            + TABLE_SEATS + "(" 
            + KEY_SEATS_ID      + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_SEATS_POS_X   + " INTEGER NOT NULL,"
            + KEY_SEATS_POS_Y   + " INTEGER NOT NULL,"
            + KEY_SEATS_RECT_L  + " INTEGER NOT NULL,"
            + KEY_SEATS_RECT_R  + " INTEGER NOT NULL,"
            + KEY_SEATS_RECT_T  + " INTEGER NOT NULL,"
            + KEY_SEATS_RECT_B  + " INTEGER NOT NULL,"
            + KEY_SEATS_STATUS  + " INTEGER NOT NULL,"
            + KEY_SEATS_LOPA_ID + " INTEGER NOT NULL" 
            + ")";
    
    // FLEET table create statement
    private static final String CREATE_TABLE_THY_FLEET= "CREATE TABLE "
            + TABLE_THY_FLEET + "(" 
    		+ KEY_FLEET_AIRCRAFT_CODE + " TEXT PRIMARY KEY," 
            + KEY_FLEET_AIRCRAFT_TYPE + " INTEGER NOT NULL " 
            + ")";
    
    // AIRCRAFTS table create statement
    private static final String CREATE_TABLE_AIRCRAFTS= "CREATE TABLE "
            + TABLE_AIRCRAFTS + "(" 
    		+ KEY_AIRCRAFTS_AIRCRAFT_ID   + " INTEGER PRIMARY KEY," 
            + KEY_AIRCRAFTS_AIRCRAFT_DEFN + " TEXT NOT NULL," 
            + KEY_AIRCRAFTS_IMG_LOCATION  + " TEXT NO NULL "
            + ")";
    
    // AIRCRAFT MODELS table create statement
    private static final String CREATE_TABLE_AIRCRAFT_MODELS= "CREATE TABLE "
            + TABLE_AIRCRAFT_MODELS + "(" 
            + KEY_AIRCRAFT_MODELS_ID      + " INTEGER PRIMARY KEY," 
            + KEY_AIRCRAFT_MODELS_DEFN    + " TEXT NOT NULL" 
            + ")";
 
 
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		// creating required tables
        db.execSQL(CREATE_TABLE_EMPLOYEES );
        db.execSQL(CREATE_TABLE_PLANE_TAGS );
        db.execSQL(CREATE_TABLE_INVENTORY_RESULTS );
        db.execSQL(CREATE_TABLE_INVENTORIES );
        db.execSQL(CREATE_TABLE_LOPAS );
        db.execSQL(CREATE_TABLE_SEATS );
        db.execSQL(CREATE_TABLE_THY_FLEET );
        db.execSQL(CREATE_TABLE_AIRCRAFTS  );
        db.execSQL(CREATE_TABLE_AIRCRAFT_MODELS  );

	}
	
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES );        
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANE_TAGS );       
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVENTORY_RESULTS );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVENTORIES );      
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOPAS );   
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEATS );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THY_FLEET );        
    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_AIRCRAFTS  ); 
    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_AIRCRAFT_MODELS  );
 
        // create new tables
        onCreate(db);
    }
    
    
    // CRUD Operations for EMPLOYEES table
    
    /*
     * Creating a employee
     */
    public void addEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        
        values.put( KEY_EMPLOYEES_TAG_ID     , employee.getEmployeeTagId());
        values.put( KEY_EMPLOYEES_LOCATION   , employee.getLocation());
        values.put( KEY_EMPLOYEES_LOGIN_DATE , dateTime2Str(employee.getDatetime()));
        values.put( KEY_EMPLOYEES_USERNAME   , employee.getUsername());
        values.put( KEY_EMPLOYEES_PASSWORD   , employee.getPassword());
 
        // insert row
        db.insert(TABLE_EMPLOYEES, null, values);
    }
    
    /*
     * Getting a single employee
     */
    public Employee getEmployee(String employeeId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        
        // Select query
        String selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEES + " WHERE "
                + KEY_EMPLOYEES_TAG_ID + " = " + employeeId;
        
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if(cursor != null)
            cursor.moveToFirst();
        
        Employee employee = new Employee( cursor.getString(cursor.getColumnIndex(KEY_EMPLOYEES_TAG_ID)), 
                                          cursor.getString(cursor.getColumnIndex(KEY_EMPLOYEES_LOCATION)),
                                          str2DateTime(cursor.getString(cursor.getColumnIndex(KEY_EMPLOYEES_LOGIN_DATE))), 
                                          cursor.getString(cursor.getColumnIndex(KEY_EMPLOYEES_USERNAME)), 
                                          cursor.getString(cursor.getColumnIndex(KEY_EMPLOYEES_PASSWORD)));
        
        return employee;
    }
    
    
    /*
     * Getting all employees
     */
    public List<Employee> getAllEmployees()
    {
        List<Employee> employeeList = new ArrayList<Employee>();
        
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_AIRCRAFTS;
        
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Employee employee = new Employee( cursor.getString(cursor.getColumnIndex(KEY_EMPLOYEES_TAG_ID)), 
                                                  cursor.getString(cursor.getColumnIndex(KEY_EMPLOYEES_LOCATION)),
                                                  str2DateTime(cursor.getString(cursor.getColumnIndex(KEY_EMPLOYEES_LOGIN_DATE))), 
                                                  cursor.getString(cursor.getColumnIndex(KEY_EMPLOYEES_USERNAME)), 
                                                  cursor.getString(cursor.getColumnIndex(KEY_EMPLOYEES_PASSWORD)));

                // Adding aircraft to list
                employeeList.add(employee);
                
            } while (cursor.moveToNext());
        }
        
        return employeeList;
    }
    
    /*
     * Updating an employee
     */
    public void updateEmployee(Employee employee)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        
        values.put( KEY_EMPLOYEES_TAG_ID     , employee.getEmployeeTagId());
        values.put( KEY_EMPLOYEES_LOCATION   , employee.getLocation());
        values.put( KEY_EMPLOYEES_LOGIN_DATE , dateTime2Str(employee.getDatetime()));
        values.put( KEY_EMPLOYEES_USERNAME   , employee.getUsername());
        values.put( KEY_EMPLOYEES_PASSWORD   , employee.getPassword());
 
        db.update( TABLE_EMPLOYEES, values, KEY_EMPLOYEES_TAG_ID + " = ?",
                new String[] { String.valueOf(employee.getEmployeeTagId()) });    
    }
    
    /*
     * Deleting an employee
     */
    public void deleteEmployee(Employee employee)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEES, KEY_EMPLOYEES_TAG_ID + " = ?",
                new String[] { String.valueOf(employee.getEmployeeTagId()) });
    }
    
    /*
     * Deleting all employees
     */
    public void deleteAllEmployees()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEES, null, null);
    }
    
    
    /*
     * Getting employees count
     */
    public int getEmployeesCount() {
        String countQuery = "SELECT * FROM " + TABLE_EMPLOYEES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
    
        // return count
        return cursor.getCount();
    }
    
    
    // CRUD Operations for PLANE TAGS table

    /*
     * Creating a plane tags
     */
    public void addPlaneTag(PlaneTag planeTag) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        
        values.put( KEY_TAGS_TAG_ID          , planeTag.getTagId());
        values.put( KEY_TAGS_PN_NUMBER       , planeTag.getPnNumber());
        values.put( KEY_TAGS_SN_NUMBER       , planeTag.getSnNumber());
        values.put( KEY_TAGS_EXP_DATE        , planeTag.getExpirationDate());
        values.put( KEY_TAGS_AIRCRAFT_CODE   , planeTag.getAircraftCode());
        values.put( KEY_TAGS_TYPE            , planeTag.getType());
        values.put( KEY_TAGS_EMPLOYEE_TAG_ID , planeTag.getEmployeeTagId());
 
        // insert row
        db.insert(TABLE_PLANE_TAGS, null, values);
    }
    
    /*
     * Getting a single plane tags
     */
    public PlaneTag getPlaneTag(String planeTagId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        
        // Select query
        String selectQuery = "SELECT  * FROM " + TABLE_PLANE_TAGS + " WHERE "
                + KEY_TAGS_TAG_ID + " = " + planeTagId;
        
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if(cursor != null)
            cursor.moveToFirst();
        
        PlaneTag planeTag = new PlaneTag( cursor.getString(cursor.getColumnIndex(KEY_TAGS_TAG_ID         )),
                                          cursor.getString(cursor.getColumnIndex(KEY_TAGS_PN_NUMBER      )),
                                          cursor.getString(cursor.getColumnIndex(KEY_TAGS_SN_NUMBER      )),
                                          cursor.getString(cursor.getColumnIndex(KEY_TAGS_EXP_DATE       )),
                                          cursor.getString(cursor.getColumnIndex(KEY_TAGS_AIRCRAFT_CODE  )),
                                          cursor.getString(cursor.getColumnIndex(KEY_TAGS_TYPE           )),
                                          cursor.getString(cursor.getColumnIndex(KEY_TAGS_EMPLOYEE_TAG_ID)));
        
        return planeTag;
    }
    
    
    /*
     * Getting all plane tags
     */
    public List<PlaneTag> getAllPlaneTags()
    {
        List<PlaneTag> planeTagsList = new ArrayList<PlaneTag>();
        
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_PLANE_TAGS;
        
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PlaneTag planeTag = new PlaneTag( cursor.getString(cursor.getColumnIndex(KEY_TAGS_TAG_ID         )),
                                                  cursor.getString(cursor.getColumnIndex(KEY_TAGS_PN_NUMBER      )),
                                                  cursor.getString(cursor.getColumnIndex(KEY_TAGS_SN_NUMBER      )),
                                                  cursor.getString(cursor.getColumnIndex(KEY_TAGS_EXP_DATE       )),
                                                  cursor.getString(cursor.getColumnIndex(KEY_TAGS_AIRCRAFT_CODE  )),
                                                  cursor.getString(cursor.getColumnIndex(KEY_TAGS_TYPE           )),
                                                  cursor.getString(cursor.getColumnIndex(KEY_TAGS_EMPLOYEE_TAG_ID)));

                // Adding plane tag to list
                planeTagsList.add(planeTag);
                
            } while (cursor.moveToNext());
        }
        
        return planeTagsList;
    }
    
    /*
     * Updating a plane tag
     */
    public void updatePlaneTag(PlaneTag planeTag)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        
        values.put( KEY_TAGS_TAG_ID          , planeTag.getEmployeeTagId());
        values.put( KEY_TAGS_PN_NUMBER       , planeTag.getPnNumber());
        values.put( KEY_TAGS_SN_NUMBER       , planeTag.getSnNumber());
        values.put( KEY_TAGS_EXP_DATE        , planeTag.getExpirationDate());
        values.put( KEY_TAGS_AIRCRAFT_CODE   , planeTag.getAircraftCode());
        values.put( KEY_TAGS_TYPE            , planeTag.getType());
        values.put( KEY_TAGS_EMPLOYEE_TAG_ID , planeTag.getEmployeeTagId());
 
        db.update( TABLE_PLANE_TAGS, values, KEY_TAGS_TAG_ID + " = ?",
                new String[] { String.valueOf(planeTag.getTagId()) });    
    }
    
    /*
     * Deleting a plane tag
     */
    public void deleteePlaneTag(PlaneTag planeTag)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLANE_TAGS, KEY_TAGS_TAG_ID + " = ?",
                new String[] { String.valueOf(planeTag.getTagId()) });
    }
    
    /*
     * Deleting all plane tags
     */
    public void deleteAllPlaneTags()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLANE_TAGS, null, null);
    }
    
    
    /*
     * Getting plane tags count
     */
    public int getPlaneTagsCount() {
        String countQuery = "SELECT * FROM " + TABLE_PLANE_TAGS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
    
        // return count
        return cursor.getCount();
    }
    
    
    
    // CRUD Operations for INVENTORY RESULTS table
    /*
     * Creating an inventory result
     */
    public void addInventoryResult(InventoryResult inventoryResult) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        
        values.put( KEY_RESULTS_AIRCRAFT_CODE     , inventoryResult.getAircraftCode());
        values.put( KEY_RESULTS_SCAN_DATE         , dateTime2Str(inventoryResult.getDateTime()));
        values.put( KEY_RESULTS_EMPLOYEE_TAG_ID   , inventoryResult.getEmployeeTagId());
        values.put( KEY_RESULTS_TOTAL_SEAT_COUNT  , inventoryResult.getTotalSeatCount());
        values.put( KEY_RESULTS_EXPIRED_COUNT     , inventoryResult.getExpiredCount());
        values.put( KEY_RESULTS_EXPIRE_SOON_COUNT , inventoryResult.getExpireSoonCount());
        values.put( KEY_RESULTS_TAG_FOUND_COUNT   , inventoryResult.getTagFoundCount());
 
        // insert row
        db.insert(TABLE_INVENTORY_RESULTS, null, values);
    }
    
    /*
     * Getting a single inventory result
     */
    public InventoryResult getInventoryResult(String aircraftCode, String scanDate)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        
        // Select query
        String selectQuery = "SELECT  * FROM " + TABLE_INVENTORY_RESULTS + " WHERE "
                + KEY_RESULTS_AIRCRAFT_CODE + " = " + aircraftCode + " AND"
                + KEY_RESULTS_SCAN_DATE + " =" + scanDate;
        
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if(cursor != null)
            cursor.moveToFirst();
        
        InventoryResult inventoryResult = new InventoryResult( cursor.getString(cursor.getColumnIndex(KEY_RESULTS_AIRCRAFT_CODE    )),
                                                               str2DateTime(cursor.getString(cursor.getColumnIndex(KEY_RESULTS_SCAN_DATE ))),
                                                               cursor.getString(cursor.getColumnIndex(KEY_RESULTS_EMPLOYEE_TAG_ID  )),
                                                               cursor.getInt(cursor.getColumnIndex(KEY_RESULTS_TOTAL_SEAT_COUNT )),
                                                               cursor.getInt(cursor.getColumnIndex(KEY_RESULTS_EXPIRED_COUNT    )),
                                                               cursor.getInt(cursor.getColumnIndex(KEY_RESULTS_EXPIRE_SOON_COUNT)),
                                                               cursor.getInt(cursor.getColumnIndex(KEY_RESULTS_TAG_FOUND_COUNT  )));
        
        return inventoryResult;
    }
    
    
    /*
     * Getting all inventory results
     */
    public List<InventoryResult> getInventoryResults()
    {
        List<InventoryResult> inventoryResultsList = new ArrayList<InventoryResult>();
        
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_INVENTORY_RESULTS;
        
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                InventoryResult inventoryResult = new InventoryResult( cursor.getString(cursor.getColumnIndex(KEY_RESULTS_AIRCRAFT_CODE    )),
                                                                       str2DateTime(cursor.getString(cursor.getColumnIndex(KEY_RESULTS_SCAN_DATE ))),
                                                                       cursor.getString(cursor.getColumnIndex(KEY_RESULTS_EMPLOYEE_TAG_ID  )),
                                                                       cursor.getInt(cursor.getColumnIndex(KEY_RESULTS_TOTAL_SEAT_COUNT )),
                                                                       cursor.getInt(cursor.getColumnIndex(KEY_RESULTS_EXPIRED_COUNT    )),
                                                                       cursor.getInt(cursor.getColumnIndex(KEY_RESULTS_EXPIRE_SOON_COUNT)),
                                                                       cursor.getInt(cursor.getColumnIndex(KEY_RESULTS_TAG_FOUND_COUNT  )));

                // Adding plane tag to list
                inventoryResultsList.add(inventoryResult);
                
            } while (cursor.moveToNext());
        }
        
        return inventoryResultsList;
    }
    
    /*
     * Updating an inventory result
     */
    public void update(InventoryResult inventoryResult)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        
        values.put( KEY_RESULTS_AIRCRAFT_CODE     , inventoryResult.getAircraftCode());
        values.put( KEY_RESULTS_SCAN_DATE         , dateTime2Str(inventoryResult.getDateTime()));
        values.put( KEY_RESULTS_EMPLOYEE_TAG_ID   , inventoryResult.getEmployeeTagId());
        values.put( KEY_RESULTS_TOTAL_SEAT_COUNT  , inventoryResult.getTotalSeatCount());
        values.put( KEY_RESULTS_EXPIRED_COUNT     , inventoryResult.getExpiredCount());
        values.put( KEY_RESULTS_EXPIRE_SOON_COUNT , inventoryResult.getExpireSoonCount());
        values.put( KEY_RESULTS_TAG_FOUND_COUNT   , inventoryResult.getTagFoundCount());
 
        db.update( TABLE_INVENTORY_RESULTS, values, 
                KEY_RESULTS_AIRCRAFT_CODE + " = ?" + " AND" + 
                KEY_RESULTS_SCAN_DATE + " = ?",
                new String[] { inventoryResult.getAircraftCode(), 
                               dateTime2Str(inventoryResult.getDateTime()) });   

    }
    
    /*
     * Deleting an inventory result
     */
    public void deleteInventoryResult(InventoryResult inventoryResult)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INVENTORY_RESULTS, 
                KEY_RESULTS_AIRCRAFT_CODE + " = ?" + " AND" + 
                KEY_RESULTS_SCAN_DATE + " = ?",
                new String[] { inventoryResult.getAircraftCode(), 
                               dateTime2Str(inventoryResult.getDateTime()) }); 
    }
    
    /*
     * Deleting all inventory results
     */
    public void deleteAllInventoryResults()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INVENTORY_RESULTS, null, null);
    }
    
    
    /*
     * Getting inventory results count
     */
    public int getInventoryResultsCount() {
        String countQuery = "SELECT * FROM " + TABLE_INVENTORY_RESULTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
    
        // return count
        return cursor.getCount();
    }
    
    // CRUD Operations for INVENTORIES table
    /*
     * Creating an inventory
     */
    public void addInventory(Inventory inventory) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        
        values.put( KEY_INVENTORIES_AIRCRAFT_CODE , inventory.getAircraftCode());
        values.put( KEY_INVENTORIES_SCAN_DATE     , dateTime2Str(inventory.getScanDate()));
        values.put( KEY_INVENTORIES_TAG_ID        , inventory.getTagId());

 
        // insert row
        db.insert(TABLE_INVENTORIES, null, values);
    }
    
    /*
     * Getting a single inventory 
     */
    public Inventory getInventory(String aircraftCode, String scanDate)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        
        // Select query
        String selectQuery = "SELECT  * FROM " + TABLE_INVENTORIES + " WHERE "
                + KEY_INVENTORIES_AIRCRAFT_CODE + " = " + aircraftCode + " AND"
                + KEY_INVENTORIES_SCAN_DATE + " =" + scanDate;
        
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if(cursor != null)
            cursor.moveToFirst();
        
        Inventory inventory = new Inventory( cursor.getString(cursor.getColumnIndex(KEY_INVENTORIES_AIRCRAFT_CODE    )),
                                             str2DateTime(cursor.getString(cursor.getColumnIndex(KEY_INVENTORIES_SCAN_DATE ))),
                                             cursor.getString(cursor.getColumnIndex(KEY_INVENTORIES_TAG_ID)));
        
        return inventory;
    }
    
    
    /*
     * Getting all inventories
     */
    public List<Inventory> getInventories()
    {
        List<Inventory> inventoryList = new ArrayList<Inventory>();
        
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_INVENTORIES;
        
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Inventory inventory = new Inventory( cursor.getString(cursor.getColumnIndex(KEY_INVENTORIES_AIRCRAFT_CODE    )),
                        str2DateTime(cursor.getString(cursor.getColumnIndex(KEY_INVENTORIES_SCAN_DATE ))),
                        cursor.getString(cursor.getColumnIndex(KEY_INVENTORIES_TAG_ID)));

                // Adding plane tag to list
                inventoryList.add(inventory);
                
            } while (cursor.moveToNext());
        }
        
        return inventoryList;
    }
    
    /*
     * Updating an inventory
     */
    public void update(Inventory inventory)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        
        values.put( KEY_INVENTORIES_AIRCRAFT_CODE , inventory.getAircraftCode());
        values.put( KEY_INVENTORIES_SCAN_DATE     , dateTime2Str(inventory.getScanDate()));
        values.put( KEY_INVENTORIES_TAG_ID        , inventory.getTagId());
 
        db.update( TABLE_INVENTORIES, values, 
                KEY_INVENTORIES_AIRCRAFT_CODE + " = ?" + " AND" + 
                KEY_INVENTORIES_SCAN_DATE + " = ?" + " AND" +
                KEY_INVENTORIES_TAG_ID + " = ?",
                new String[] { inventory.getAircraftCode(), 
                               dateTime2Str(inventory.getScanDate()),
                               inventory.getTagId()});   

    }
    
    /*
     * Deleting an inventory
     */
    public void deleteInventory(Inventory inventory)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INVENTORY_RESULTS, 
                KEY_INVENTORIES_AIRCRAFT_CODE + " = ?" + " AND" + 
                KEY_INVENTORIES_SCAN_DATE + " = ?" + " AND" +
                KEY_INVENTORIES_TAG_ID + " = ?",
                new String[] { inventory.getAircraftCode(), 
                               dateTime2Str(inventory.getScanDate()),
                               inventory.getTagId()});  
    }
    
    /*
     * Deleting all inventories
     */
    public void deleteAllInventories()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INVENTORIES, null, null);
    }
    
    
    /*
     * Getting inventory results count
     */
    public int getInventoriesCount() {
        String countQuery = "SELECT * FROM " + TABLE_INVENTORIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
    
        // return count
        return cursor.getCount();
    }
    

    // CRUD Operations for LOPAS table
    /*
     * Creating a lopa
     */
    public void addLopa(LopaHandler lopa) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        
        values.put( KEY_LOPAS_DEFN    , lopa.getDefn());
        values.put( KEY_LOPAS_MODEL   , lopa.getModel());

 
        // insert row
        long lopa_id = db.insert(TABLE_LOPAS, null, values);
        
        addSeats(lopa.getSeats(), lopa_id);
    }
    
    /*
     * Getting a single lopa
     */
    public LopaHandler getLopa(String defn)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        
        // Select query
        String selectQuery = "SELECT  * FROM " + TABLE_LOPAS + " WHERE "
                + KEY_LOPAS_DEFN + " = " + "'" + defn + "'";
        
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if(cursor != null)
            cursor.moveToFirst();
        
        LopaHandler lopa = new LopaHandler();
        lopa.setId( cursor.getLong(cursor.getColumnIndex(KEY_LOPAS_ID)));
        lopa.setDefn(cursor.getString(cursor.getColumnIndex(KEY_LOPAS_DEFN)));
        lopa.setModel(cursor.getString(cursor.getColumnIndex(KEY_LOPAS_MODEL)));
        lopa.setSeats((ArrayList<Seat>)getSeats(lopa.getId()));
                
        
        return lopa;
    }
    
    /*
     * Getting all lopas of particular aircraft model
     */
    public List<LopaHandler> getLopasFromModel(String model)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        
        // Select query
        String selectQuery = "SELECT  * FROM " + TABLE_LOPAS + " WHERE "
                + KEY_LOPAS_MODEL + " = " + "'" + model + "'";
        
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        List<LopaHandler> lopaList = new ArrayList<LopaHandler>();
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LopaHandler lopa = new LopaHandler();
                lopa.setId( cursor.getLong(cursor.getColumnIndex(KEY_LOPAS_ID)));
                lopa.setDefn(cursor.getString(cursor.getColumnIndex(KEY_LOPAS_DEFN)));
                lopa.setModel(cursor.getString(cursor.getColumnIndex(KEY_LOPAS_MODEL)));
                lopa.setSeats((ArrayList<Seat>)getSeats(lopa.getId()));
                // Adding plane tag to list
                lopaList.add(lopa);
                
            } while (cursor.moveToNext());
        }
        
        return lopaList;
    }
    
    
    /*
     * Getting all lopas
     */
    public List<LopaHandler> getLopas()
    {
        List<LopaHandler> lopaList = new ArrayList<LopaHandler>();
        
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_LOPAS;
        
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LopaHandler lopa = new LopaHandler();
                lopa.setId( cursor.getLong(cursor.getColumnIndex(KEY_LOPAS_ID)));
                lopa.setDefn(cursor.getString(cursor.getColumnIndex(KEY_LOPAS_DEFN)));
                lopa.setModel(cursor.getString(cursor.getColumnIndex(KEY_LOPAS_MODEL)));
                lopa.setSeats((ArrayList<Seat>)getSeats(lopa.getId()));
                // Adding plane tag to list
                lopaList.add(lopa);
                
            } while (cursor.moveToNext());
        }
        
        return lopaList;
    }
    
    
    /*
     * Updating a lopa
     */
    public void update(LopaHandler lopa)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        
        values.put( KEY_LOPAS_ID      , lopa.getId());
        values.put( KEY_LOPAS_DEFN    , lopa.getDefn());
        values.put( KEY_LOPAS_MODEL   , lopa.getModel());
        
        db.update( TABLE_LOPAS, values, 
                KEY_LOPAS_ID + " = ?",
                new String[] { String.valueOf(lopa.getId()) });   

    }
    
    /*
     * Deleting a lopa
     */
    public void deleteLopa(LopaHandler lopa)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOPAS, 
                KEY_LOPAS_ID + " = ?",
                new String[] { String.valueOf(lopa.getId()) }); 
        deleteSeats(lopa.getId());
    }
    
    /*
     * Deleting all lopas
     */
    public void deleteAllLopas()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOPAS, null, null);
        db.delete(TABLE_SEATS, null, null);
    }
    
    
    /*
     * Getting lopas count
     */
    public int getLopasCount() {
        String countQuery = "SELECT * FROM " + TABLE_LOPAS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
    
        // return count
        return cursor.getCount();
    }
    
    
 // CRUD Operations for SEATS table
    /*
     * Adding seats
     */
    public void addSeats(ArrayList<Seat> seats, long lopa_id) {
        SQLiteDatabase db = this.getWritableDatabase();
     

        for(int i=0; i < seats.size(); i++)
        {
            ContentValues values = new ContentValues();
            
            values.put( KEY_SEATS_POS_X, seats.get(i).getPosition().x);
            values.put( KEY_SEATS_POS_Y, seats.get(i).getPosition().y);
            values.put( KEY_SEATS_RECT_L, seats.get(i).getSeatRect().left);
            values.put( KEY_SEATS_RECT_R, seats.get(i).getSeatRect().right);
            values.put( KEY_SEATS_RECT_T, seats.get(i).getSeatRect().top);
            values.put( KEY_SEATS_RECT_B, seats.get(i).getSeatRect().bottom);
            values.put( KEY_SEATS_STATUS, seats.get(i).getSeatStatus().ordinal());
            values.put( KEY_SEATS_LOPA_ID, lopa_id);
            
            // insert row
            db.insert(TABLE_SEATS, null, values);

        }

    }
    
    /*
     * Getting all seats
     */
    public List<Seat> getSeats(long lopa_id)
    {
        List<Seat> seats = new ArrayList<Seat>();
        
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_SEATS  + " WHERE "
                + KEY_SEATS_LOPA_ID + " = " + String.valueOf(lopa_id);
        
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                
                Point pos = new Point(cursor.getInt(cursor.getColumnIndex(KEY_SEATS_POS_X)), cursor.getInt(cursor.getColumnIndex(KEY_SEATS_POS_Y)));
                Rect seatRect = new Rect(cursor.getInt(cursor.getColumnIndex(KEY_SEATS_RECT_L)),
                                         cursor.getInt(cursor.getColumnIndex(KEY_SEATS_RECT_T)),
                                         cursor.getInt(cursor.getColumnIndex(KEY_SEATS_RECT_R)),
                                         cursor.getInt(cursor.getColumnIndex(KEY_SEATS_RECT_B)));
                SeatStatus status = SeatStatus.values()[cursor.getInt(cursor.getColumnIndex(KEY_SEATS_STATUS))];
                
                Seat seat = new Seat( pos, seatRect, status );
                
                seats.add(seat);

            } while (cursor.moveToNext());
        }
        
        return seats;
    }
    
    /*
     * Deleting seats
     */
    public void deleteSeats(long lopa_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SEATS, 
                KEY_SEATS_LOPA_ID + " = ?",
                new String[] { String.valueOf(lopa_id) }); 
    }
    
    // CRUD Operations for THY FLEET table
    /*
     * Creating a thy aircraft
     */
    public void addThyAircraft(ThyAircraft aircraft) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        
        values.put( KEY_FLEET_AIRCRAFT_CODE, aircraft.getAircraftCode());
        values.put( KEY_FLEET_AIRCRAFT_TYPE, aircraft.getAircraftType());

 
        // insert row
        db.insert(TABLE_THY_FLEET, null, values);
    }
    
    /*
     * Getting a single thy aircraft
     */
    public ThyAircraft getThyAircraft(String aircraftCode)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        
        // Select query
        String selectQuery = "SELECT  * FROM " + TABLE_THY_FLEET + " WHERE "
                + KEY_FLEET_AIRCRAFT_CODE + " = " + aircraftCode;
        
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if(cursor != null)
            cursor.moveToFirst();
        
        ThyAircraft aircraft = new ThyAircraft( cursor.getString(cursor.getColumnIndex(KEY_FLEET_AIRCRAFT_CODE    )),
                                                cursor.getString(cursor.getColumnIndex(KEY_FLEET_AIRCRAFT_TYPE)));
        
        return aircraft;
    }
    
    
    /*
     * Getting all thy fleet
     */
    public List<ThyAircraft> getThyFleet()
    {
        List<ThyAircraft> thyAircraftList = new ArrayList<ThyAircraft>();
        
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_THY_FLEET;
        
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ThyAircraft aircraft = new ThyAircraft( cursor.getString(cursor.getColumnIndex(KEY_FLEET_AIRCRAFT_CODE    )),
                        cursor.getString(cursor.getColumnIndex(KEY_FLEET_AIRCRAFT_TYPE)));

                // Adding plane tag to list
                thyAircraftList.add(aircraft);
                
            } while (cursor.moveToNext());
        }
        
        return thyAircraftList;
    }
    
    /*
     * Updating a thy aircraft
     */
    public void update(ThyAircraft aircraft)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        
        values.put( KEY_FLEET_AIRCRAFT_CODE, aircraft.getAircraftCode());
        values.put( KEY_FLEET_AIRCRAFT_TYPE, aircraft.getAircraftType());
        
        db.update( TABLE_THY_FLEET, values, 
                KEY_FLEET_AIRCRAFT_CODE + " = ?",
                new String[] { aircraft.getAircraftCode() });   

    }
    
    /*
     * Deleting a thy aircraft
     */
    public void deleteThyAircraft(String aircraftCode)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_THY_FLEET, 
                KEY_FLEET_AIRCRAFT_CODE + " = ?",
                new String[] { aircraftCode }); 
    }
    
    /*
     * Deleting all fleet
     */
    public void deleteAllFleet()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_THY_FLEET, null, null);
    }
    
    
    /*
     * Getting fleet count
     */
    public int getFleetCount() {
        String countQuery = "SELECT * FROM " + TABLE_THY_FLEET;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
    
        // return count
        return cursor.getCount();
    }
    
    
    // CRUD Operations for AIRCRAFTS table
    /*
     * Creating an aircraft
     */
    public void addAircraft(Aircraft aircraft) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        
        values.put( KEY_AIRCRAFTS_AIRCRAFT_ID   , aircraft.getAircraftId());
        values.put( KEY_AIRCRAFTS_AIRCRAFT_DEFN , aircraft.getAircraftDefinition());
        values.put( KEY_AIRCRAFTS_IMG_LOCATION  , aircraft.getImageLocation());

 
        // insert row
        db.insert(TABLE_AIRCRAFTS, null, values);
    }
    
    /*
     * Getting a single aircraft
     */
    public Aircraft getAircraft(String aircraftId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        
        // Select query
        String selectQuery = "SELECT  * FROM " + TABLE_AIRCRAFTS + " WHERE "
                + KEY_AIRCRAFTS_AIRCRAFT_ID + " = " + aircraftId;
        
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if(cursor != null)
            cursor.moveToFirst();
        
        Aircraft aircraft = new Aircraft( cursor.getString(cursor.getColumnIndex(KEY_AIRCRAFTS_AIRCRAFT_ID    )),
                                          cursor.getString(cursor.getColumnIndex(KEY_AIRCRAFTS_AIRCRAFT_DEFN  )),
                                          cursor.getString(cursor.getColumnIndex(KEY_AIRCRAFTS_IMG_LOCATION   )) );
        
        return aircraft;
    }
    
    
    /*
     * Getting all aircrafts
     */
    public List<Aircraft> getAircrafts()
    {
        List<Aircraft> aircraftList = new ArrayList<Aircraft>();
        
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_AIRCRAFTS;
        
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Aircraft aircraft = new Aircraft( cursor.getString(cursor.getColumnIndex(KEY_AIRCRAFTS_AIRCRAFT_ID    )),
                                                  cursor.getString(cursor.getColumnIndex(KEY_AIRCRAFTS_AIRCRAFT_DEFN  )),
                                                  cursor.getString(cursor.getColumnIndex(KEY_AIRCRAFTS_IMG_LOCATION   )) );

                // Adding plane tag to list
                aircraftList.add(aircraft);
                
            } while (cursor.moveToNext());
        }
        
        return aircraftList;
    }
    
    /*
     * Updating an aircraaft
     */
    public void update(Aircraft aircraft)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        
        values.put( KEY_AIRCRAFTS_AIRCRAFT_ID   , aircraft.getAircraftId());
        values.put( KEY_AIRCRAFTS_AIRCRAFT_DEFN , aircraft.getAircraftDefinition()); 
        values.put( KEY_AIRCRAFTS_IMG_LOCATION  , aircraft.getImageLocation());
        
        db.update( TABLE_AIRCRAFTS, values, 
                KEY_AIRCRAFTS_AIRCRAFT_ID + " = ?",
                new String[] { aircraft.getAircraftId() });   

    }
    
    /*
     * Deleting an aircraft
     */
    public void deleteAircraft(String aircraftId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_AIRCRAFTS, 
                KEY_AIRCRAFTS_AIRCRAFT_ID + " = ?",
                new String[] { aircraftId }); 
    }
    
    /*
     * Deleting all aircrafts
     */
    public void deleteAllAircrafts()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_AIRCRAFTS, null, null);
    }
    
    
    /*
     * Getting aircrafts count
     */
    public int getAircraftsCount() {
        String countQuery = "SELECT * FROM " + TABLE_AIRCRAFTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
    
        // return count
        return cursor.getCount();
    }    
    
    
 // CRUD Operations for AIRCRAFT MODELS table
    /*
     * Creating an aircraft model
     */
    public void addAircraftModel(String defn) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        
        values.put( KEY_AIRCRAFT_MODELS_DEFN   , defn);
 
        // insert row
        db.insert(TABLE_AIRCRAFT_MODELS, null, values);
    }
    
    /*
     * Getting all aircraft models
     */
    public List<String> getAircraftModels()
    {
        List<String> models = new ArrayList<String>();
        
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_AIRCRAFT_MODELS;
        
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // Adding models to list
                models.add(cursor.getString(cursor.getColumnIndex(KEY_AIRCRAFT_MODELS_DEFN   )));
                
            } while (cursor.moveToNext());
        }
        
        return models;
    }
    
    
    
    

    // Converting from String to DateTime or vice a versa
    private String dateTime2Str(DateTime dateTime) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");        
        return dtf.print(dateTime);
    }
    
    private DateTime str2DateTime(String dateTimeStr) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
        return dtf.parseDateTime(dateTimeStr);
    }
    
    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
