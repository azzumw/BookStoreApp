package com.example.macintosh.bookstoreapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.macintosh.bookstoreapp.data.ProductContract.ProductEntry;
import com.example.macintosh.bookstoreapp.data.ProductContract.SupplierEntry;
import com.example.macintosh.bookstoreapp.data.ProductDbHelper;

public class MainActivity extends AppCompatActivity {

    private ProductDbHelper mDbHelper;
    private SQLiteDatabase db;
//    private long newRowSuppID;

    TextView displayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button insertBtn = findViewById(R.id.insertButton);
        final Button queryBtn = findViewById(R.id.Query_db);
        displayView =  findViewById(R.id.textView);


        mDbHelper = new ProductDbHelper(this);
        // Create and/or open a database to read from it


//        displayDatabaseInfo();
            insertSupplierData();
//            queryData();


        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertProductData();
//                queryData();
//                displayDatabaseInfo();
            }
        });

        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryData();
            }
        });

    }

    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new ProductDbHelper(this);

        // Create and/or open a database to read from it
        db = mDbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        Cursor cursor = db.rawQuery("SELECT * FROM " +ProductEntry.TABLE_NAME, null);
        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).

            displayView.setText("Number of rows in bookstore database table: " + cursor.getCount());
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    private void insertProductData(){
        // Gets the data repository in write mode
        db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put(ProductEntry.NAME,"Quiet - The Power of Introverts");
        values.put(ProductEntry.PRICE,6);
        values.put(ProductEntry.QUANTITY,5);
        values.put(ProductEntry.STOCK_STATUS,ProductEntry.IN_STOCK);
        values.put(ProductEntry.SUPPLIER_ID,SupplierEntry.SUP_ID);

        // Insert the new row, returning the primary key value of the new row,e lse -1
        //null here means it does not create any row if you didnt provide values otherwise
        //it will insert row with null values.
        long newRowProdId = db.insert(ProductEntry.TABLE_NAME, null, values);
//        Log.v("MainActivity: ","New Supplier Row ID: " + newRowSuppID);

        Cursor cursor = db.rawQuery("SELECT * FROM "+ProductEntry.TABLE_NAME,null);

        displayView.setText("Number of rows in bookstore database table: " + cursor.getCount());
        Log.v("MainActivity: ","New Product Row ID: " + newRowProdId);
    }

    private void insertSupplierData(){

        // Gets the data repository in write mode
        db = mDbHelper.getWritableDatabase();


        ContentValues values = new ContentValues();

        values.put(SupplierEntry.NAME,"Amazon");
        values.put(SupplierEntry.PHONE,"02056897464");
        long newRowSuppID = db.insert(SupplierEntry.TABLE_NAME,null,values);


        Log.v("MainActivity: ","New Supplier Row ID: " + newRowSuppID);
    }

    private void queryData(){
        db = mDbHelper.getReadableDatabase();

//        String [] projections = {ProductEntry.PRODUCT_ID,ProductEntry.NAME,ProductEntry.PRICE,SupplierEntry.SUP_ID,SupplierEntry.TABLE_NAME+"."+SupplierEntry.NAME};

        final String QUERY = "SELECT *"
                + " FROM " + ProductEntry.TABLE_NAME + " P"
                + " INNER JOIN "+ SupplierEntry.TABLE_NAME + " S"
                + " ON S."+ SupplierEntry.SUP_ID + " = " + "P." +ProductEntry.SUPPLIER_ID;



         Cursor cursor = db.rawQuery(QUERY,null);

        try{

            displayView.setText("Number of rows in bookstore database table: " + cursor.getCount());
            //display header
            displayView.append("\n"+ProductEntry.PRODUCT_ID+ "\t\t| "+
                    ProductEntry.NAME + "\t\t| "+
                    SupplierEntry.NAME);

            //Figure out the index of each column
            int idColIndex = cursor.getColumnIndexOrThrow(ProductEntry.PRODUCT_ID);
            int nameColIndex = cursor.getColumnIndexOrThrow(ProductEntry.NAME);
//            int priceColIndex = cursor.getColumnIndexOrThrow(ProductEntry.PRICE);
            int suppNameIndex = cursor.getColumnIndexOrThrow(SupplierEntry.NAME);

            //iterate through all the returnd rows in the cursor
            while(cursor.moveToNext()){
                int currentID = cursor.getInt(idColIndex);
                String currentName = cursor.getString(nameColIndex);
//                int currentprice = cursor.getInt(priceColIndex);
                String supplName = cursor.getString(suppNameIndex);



                displayView.append("\n"+currentID + "\t|" +
                                        currentName + "\t\t|" +
                                        supplName);
            }
        }
        finally {
            cursor.close();
        }


    }
}
