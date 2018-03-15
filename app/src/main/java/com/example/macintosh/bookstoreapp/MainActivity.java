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

    private TextView displayView;

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
        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        Cursor cursor = mDbHelper.getAllProducts();
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
        long newRowProdId = mDbHelper.insertProduct(1L);
//        Log.v("MainActivity: ","New Supplier Row ID: " + newRowSuppID);

        Cursor cursor = mDbHelper.getAllProducts();

        displayView.setText("Number of rows in bookstore database table: " + cursor.getCount());
        Log.v("MainActivity: ","New Product Row ID: " + newRowProdId);
    }

    private void insertSupplierData(){
        long newRowSuppID = mDbHelper.insertSupplier();
        Log.v("MainActivity: ","New Supplier Row ID: " + newRowSuppID);
    }

    private void queryData(){
//        String [] projections = {ProductEntry.PRODUCT_ID,ProductEntry.NAME,ProductEntry.PRICE,SupplierEntry.SUP_ID,SupplierEntry.TABLE_NAME+"."+SupplierEntry.NAME};
         Cursor cursor = mDbHelper.getJoinedRows();
         Log.v("MainActivity" , cursor.toString());

        try{

            displayView.setText("Number of rows in bookstore database table: " + cursor.getCount());
            //display header
            displayView.append("\n"+ProductEntry.PRODUCT_ID+ "\t\t| "+
                    ProductEntry.NAME + "\t\t| "+
                    SupplierEntry.NAME);

            //Figure out the index of each column
//            int idColIndex = cursor.getColumnIndex(ProductEntry.PRODUCT_ID); //why is the col id 6???
            int nameColIndex = cursor.getColumnIndex(ProductEntry.NAME);
//            int priceColIndex = cursor.getColumnIndexOrThrow(ProductEntry.PRICE);
             Log.v("MainActivity",cursor.moveToFirst()+"");
            //iterate through all the returnd rows in the cursor
            while(cursor.moveToNext()){
//                int currentID = cursor.getInt(idColIndex);
                String currentName = cursor.getString(1);
//                int currentprice = cursor.getInt(priceColIndex);
                String supplName = cursor.getString(2);



                displayView.append("\n" + "\t|" +
                                        currentName + "\t\t|" +
                                        supplName);
            }
        }
        finally {
            cursor.close();
        }


    }
}
