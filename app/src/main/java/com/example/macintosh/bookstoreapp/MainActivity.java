package com.example.macintosh.bookstoreapp;

import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.macintosh.bookstoreapp.data.ProductContract.ProductEntry;
import com.example.macintosh.bookstoreapp.data.ProductContract.SupplierEntry;
import com.example.macintosh.bookstoreapp.data.ProductDbHelper;

public class MainActivity extends AppCompatActivity {

    private ProductDbHelper mDbHelper;
    private Cursor cursor;
    private TextView displayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button queryBtn = findViewById(R.id.Query_Prod);
        final Button querySupplierButton = findViewById(R.id.query_supplier_table);

        displayView =  findViewById(R.id.textView);


        mDbHelper = new ProductDbHelper(this);
        String dbName = mDbHelper.getDatabaseName();

        displayView.setText("Database: "+ dbName);

        insertSupplierData();


        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryProductData();
            }
        });

        querySupplierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                querySupplierData();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_insert_dummy_data:
                        insertProductData();break;
            case R.id.action_delete_all:
                        mDbHelper.deleteAllProducts();
        }

        return true;
    }


    /** inserts data in to the products table by fetching supplier Id from the supplier Table*/
    private void insertProductData(){

        cursor = mDbHelper.getAllSuppliers();
        int suppIDIndex = cursor.getColumnIndex(SupplierEntry.SUP_ID);
        cursor.moveToFirst();
        int supplierIDColvalue = cursor.getInt(suppIDIndex);

        long newRowProdId = mDbHelper.insertProduct(supplierIDColvalue);
//        Log.v("MainActivity: ","New Supplier Row ID: " + newRowSuppID);

         cursor = mDbHelper.getAllProducts();

        displayView.setText("Number of rows in bookstore database table: " + cursor.getCount());
        Log.v("MainActivity: ","New Product Row ID: " + newRowProdId);
    }

    /**
     * Inserts Supplier Data before Product Data can be inserted due to FK constraints.
     * is called when activity starts
     * */
    private void insertSupplierData(){
        long newRowSuppID = mDbHelper.insertSupplier();
        Log.v("Mainactivity:","Row_id "+ newRowSuppID);

        if(newRowSuppID!=-1) {
            Snackbar.make(findViewById(R.id.relative_layout), "Supplier added to " + SupplierEntry.TABLE_NAME + " Table.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

        else
            Snackbar.make(findViewById(R.id.relative_layout), "Error adding " + SupplierEntry.TABLE_NAME + " information.", Snackbar.LENGTH_LONG).setAction("Action", null).show();

    }

    /**Query the products table. */
    private void queryProductData(){

        cursor = mDbHelper.getAllProducts();


        try{

            displayView.setText("Number of rows in bookstore database table: " + cursor.getCount());
            //display header
            displayView.append("\n"+ProductEntry.PRODUCT_ID+ "\t\t| "+
                    ProductEntry.NAME + "\t\t| "+
                    ProductEntry.SUPPLIER_ID);


//            int priceColIndex = cursor.getColumnIndexOrThrow(ProductEntry.PRICE);

            //Figure out the index of each column
            int idColIndex = cursor.getColumnIndex(ProductEntry.PRODUCT_ID); //why is the col id 6???
            int nameColIndex = cursor.getColumnIndex(ProductEntry.NAME);
            int suppColIndex = cursor.getColumnIndex(ProductEntry.SUPPLIER_ID);
            //iterate through all the returnd rows in the cursor
            while(cursor.moveToNext()){


                long currentID = cursor.getLong(idColIndex);
                String currentName = cursor.getString(nameColIndex);
                String currentSuppId = cursor.getString(suppColIndex);



                displayView.append("\n" +currentID +  "\t|" + currentName + " | \t"+ currentSuppId);
            }
        }
        finally {
            cursor.close();
        }


    }

    private void querySupplierData(){
        cursor = mDbHelper.getAllSuppliers();

        try{

            displayView.setText("Number of rows in bookstore database supplier table: " + cursor.getCount());
            //display header
            displayView.append("\n"+SupplierEntry.SUP_ID+ "\t\t| "+
                    SupplierEntry.NAME + "\t\t| "+
                    SupplierEntry.PHONE);

            //Figure out the index of each column
            int idColIndex = cursor.getColumnIndex(SupplierEntry.SUP_ID); //why is the col id 6???
            int nameColIndex = cursor.getColumnIndex(SupplierEntry.NAME);
            int suppColIndex = cursor.getColumnIndex(SupplierEntry.PHONE);

            while(cursor.moveToNext()){


                long currentID = cursor.getLong(idColIndex);
                String currentName = cursor.getString(nameColIndex);
                String currentPhone = cursor.getString(suppColIndex);



                displayView.append("\n" +currentID +  "\t|" + currentName + " | \t"+ currentPhone);
            }
        }
        finally {
            cursor.close();
        }

    }
}
