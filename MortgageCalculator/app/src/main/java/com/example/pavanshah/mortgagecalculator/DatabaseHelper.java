package com.example.pavanshah.mortgagecalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.Console;
import java.util.ArrayList;
import java.util.zip.ZipEntry;


public class DatabaseHelper extends SQLiteOpenHelper
{
    static DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    private static final String databaseName = "MortgageCalculatorDatabase.db";

    private static final int databaseVersion = 27;

    private static final String tableName = "PropertyTable";

    //private DatabaseHelper db;
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, databaseName, factory, databaseVersion);
        Log.d("PAVAN", "Inside Database helper constructor");
        sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        Log.d("PAVAN", "Inside create table");
        String query = "CREATE TABLE IF NOT EXISTS " + tableName + "(ID TEXT, PropertyType TEXT, Address TEXT, City TEXT, LoanAmount DOUBLE, APR DOUBLE, MonthlyPayment DOUBLE, Latitude DOUBLE, Longitude DOUBLE, Zip TEXT, Price TEXT, DownPayment TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        Log.d("PAVAN", "Inside upgrade");
        String query = "DROP TABLE IF EXISTS " + tableName;
        sqLiteDatabase.execSQL(query);

        onCreate(sqLiteDatabase);
    }

    public boolean editPropertyInfo(String ID, String PropertyType, String Address, String City, Double LoanAmount, Double APR, Double MonthlyPayment, Double Latitude, Double Longitude, String zip, String price, String downPayment)
    {
        sqLiteDatabase = getWritableDatabase();

        Log.d("Vansh", "inside Edit property info");
        try
        {

            ContentValues contentValues = new ContentValues();
            contentValues.put("ID", ID);
            contentValues.put("PropertyType", PropertyType);
            contentValues.put("Address", Address);
            contentValues.put("City", City);
            contentValues.put("LoanAmount", LoanAmount);
            contentValues.put("APR", APR);
            contentValues.put("MonthlyPayment", MonthlyPayment);
            contentValues.put("Latitude", Latitude);
            contentValues.put("Longitude", Longitude);
            contentValues.put("Zip", zip);
            contentValues.put("Price", price);
            contentValues.put("DownPayment", downPayment);
            Log.d("NEW", contentValues.get("Address").toString());
            String whereClause= "ID=\""+ID+"\"";
            //String query = "UPDATE "+tableName+" SET PropertyType = \""+PropertyType+"\",  Address = \""+Address+" City", LoanAmount DOUBLE, APR DOUBLE, MonthlyPayment DOUBLE, Latitude DOUBLE, Longitude DOUBLE, Zip TEXT, Price TEXT, DownPayment TEXT
            //String query = "UPDATE "+tableName+" SET PropertyType ='"+PropertyType+"', Address ='"+Address+"', City='"+City+"', LoanAmount='"+LoanAmount+"', APR='"+APR+"', MonthlyPayment='"+MonthlyPayment+"', Latitude='"+Latitude+"', Longitude='"+Longitude+"', Zip='"+ zip+"', Price='"+price +"', DownPayment='"+downPayment+"' WHERE ID='"+ID+"'";
            //Log.d("NEW", "query:   "+query);
            //sqLiteDatabase.execSQL(query);
            sqLiteDatabase.update(tableName, contentValues, whereClause,null );
            //sqLiteDatabase.insert(tableName, null, contentValues);
            MainActivity.getInstance().updateMap();
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addPropertyInfo(String ID, String PropertyType, String Address, String City, Double LoanAmount, Double APR, Double MonthlyPayment, Double Latitude, Double Longitude, String Zip, String price, String downPayment)
    {

        sqLiteDatabase = getWritableDatabase();

        Log.d("PAVAN", "inside Add property info");
        try
        {

            ContentValues contentValues = new ContentValues();
            contentValues.put("ID", ID);
            contentValues.put("Zip", Zip);
            contentValues.put("PropertyType", PropertyType);
            contentValues.put("Address", Address);
            contentValues.put("City", City);
            contentValues.put("LoanAmount", LoanAmount);
            contentValues.put("APR", APR);
            contentValues.put("MonthlyPayment", MonthlyPayment);
            contentValues.put("Latitude", Latitude);
            contentValues.put("Longitude", Longitude);
            contentValues.put("Price", price);
            contentValues.put("DownPayment", downPayment);

            sqLiteDatabase.insert(tableName, null, contentValues);
            MainActivity.getInstance().updateMap();
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public PropertyDetails getPropertyInfo(String ID)
    {

        PropertyDetails propertyDetails = new PropertyDetails();
        sqLiteDatabase = getWritableDatabase();

        Log.d("PAVAN", "inside Add property info");
        try
        {

            Log.d("PAVAN", "Cursoryo 1");
            ContentValues contentValues = new ContentValues();
            contentValues.put("ID", ID);

            Log.d("PAVAN", "Cursoryo 2");

            //String query = "SELECT * FROM " + tableName + " WHERE ID="+ID;
            //sqLiteDatabase.execSQL(query);

            Log.d("PAVAN", "Cursoryo 3");

            String query = "SELECT * FROM " + tableName +" WHERE ID = \""+ID+"\"";
            Log.d("PAVAN", "Cursoryo 4");
            Log.d("PAVAN", "Cursoryo 1"+query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);

            Log.d("PAVAN", "Cursoryo 5");
            ArrayList<PropertyDetails> adapter = new ArrayList<>();
            Log.d("PAVAN", "Cursoryo 6");

            Log.d("PAVAN", "Cursor count "+cursor.getCount());

            if (cursor != null && cursor.getCount() !=0)
            {
                cursor.moveToFirst();
                Log.d("PAVAN", "curcorniander");
                do
                {
                    //String ID = cursor.getString(cursor.getColumnIndex("ID"));
                    String PropertyType = cursor.getString(cursor.getColumnIndex("PropertyType"));
                    String Address = cursor.getString(cursor.getColumnIndex("Address"));
                    String City = cursor.getString(cursor.getColumnIndex("City"));
                    Double LoanAmount = cursor.getDouble(cursor.getColumnIndex("LoanAmount"));
                    Double APR = cursor.getDouble(cursor.getColumnIndex("APR"));
                    Double MonthlyPayment = cursor.getDouble(cursor.getColumnIndex("MonthlyPayment"));
                    Double Latitude = cursor.getDouble(cursor.getColumnIndex("Latitude"));
                    Double Longitude = cursor.getDouble(cursor.getColumnIndex("Longitude"));
                    String Zip = cursor.getString(cursor.getColumnIndex("Zip"));
                    String Price = cursor.getString(cursor.getColumnIndex("Price"));
                    String DownPayment = cursor.getString(cursor.getColumnIndex("DownPayment"));



                    propertyDetails.setID(ID);
                    propertyDetails.setPropertyType(PropertyType);
                    propertyDetails.setAddress(Address);
                    propertyDetails.setCity(City);
                    propertyDetails.setLoanAmount(LoanAmount);
                    propertyDetails.setAPR(APR);
                    propertyDetails.setMonthlyPayment(MonthlyPayment);
                    propertyDetails.setLatitude(Latitude);
                    propertyDetails.setLongitude(Longitude);
                    propertyDetails.setZip(Zip);
                    propertyDetails.setPrice(Price);
                    propertyDetails.setDownPayment(DownPayment);


                    //adapter.add(propertyDetails);

                }while (cursor.moveToNext());

                cursor.close();

                //return adapter;
            }

            //sqLiteDatabase.insert(tableName, null, contentValues);
            MainActivity.getInstance().updateMap();
            Log.d("PAVAN", "Cursor count jldsbfkhsdbbd"+ propertyDetails.getCity());
            return propertyDetails;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Log.d("PAVAN", "Cursordnfjslgsljdf count "+ propertyDetails.getCity());
            return propertyDetails;
        }
    }



    public ArrayList<PropertyDetails> readData()
    {
        Log.d("PAVAN", "Inside read data");

        Log.d("PAVAN", "Current object"+this.getClass().getName());
        sqLiteDatabase = getReadableDatabase();

        String query = "SELECT * FROM " + tableName;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        ArrayList<PropertyDetails> adapter = new ArrayList<>();

        Log.d("PAVAN", "Cursor count "+cursor.getCount());

        if (cursor != null && cursor.getCount() !=0)
        {
            cursor.moveToFirst();

            do
            {
                String ID = cursor.getString(cursor.getColumnIndex("ID"));
                String PropertyType = cursor.getString(cursor.getColumnIndex("PropertyType"));
                String Address = cursor.getString(cursor.getColumnIndex("Address"));
                String City = cursor.getString(cursor.getColumnIndex("City"));
                Double LoanAmount = cursor.getDouble(cursor.getColumnIndex("LoanAmount"));
                Double APR = cursor.getDouble(cursor.getColumnIndex("APR"));
                Double MonthlyPayment = cursor.getDouble(cursor.getColumnIndex("MonthlyPayment"));
                Double Latitude = cursor.getDouble(cursor.getColumnIndex("Latitude"));
                Double Longitude = cursor.getDouble(cursor.getColumnIndex("Longitude"));
                String Zip = cursor.getString(cursor.getColumnIndex("Zip"));

                String Price = cursor.getString(cursor.getColumnIndex("Price"));
                String DownPayment = cursor.getString(cursor.getColumnIndex("DownPayment"));

                PropertyDetails propertyDetails = new PropertyDetails();
                propertyDetails.setID(ID);
                propertyDetails.setPropertyType(PropertyType);
                propertyDetails.setAddress(Address);
                propertyDetails.setCity(City);
                propertyDetails.setLoanAmount(LoanAmount);
                propertyDetails.setAPR(APR);
                propertyDetails.setMonthlyPayment(MonthlyPayment);
                propertyDetails.setLatitude(Latitude);
                propertyDetails.setLongitude(Longitude);
                propertyDetails.setZip(Zip);
                propertyDetails.setPrice(Price);
                propertyDetails.setDownPayment(DownPayment);



                adapter.add(propertyDetails);

            }while (cursor.moveToNext());

            cursor.close();

            return adapter;
        }

        return adapter;
    }
}