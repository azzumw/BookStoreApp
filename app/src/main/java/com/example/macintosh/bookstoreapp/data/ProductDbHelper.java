package com.example.macintosh.bookstoreapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.macintosh.bookstoreapp.data.ProductContract.ProductEntry;
import com.example.macintosh.bookstoreapp.data.ProductContract.SupplierEntry;

/**
 * Created by macintosh on 04/03/2018.
 */

public class ProductDbHelper extends SQLiteOpenHelper {

    /** Name of the database file */
    private static final String DATABASE_NAME = "bookstore.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    public ProductDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_PRODUCT_TABLE = "CREATE TABLE " + ProductEntry.TABLE_NAME + " ("+
                ProductEntry.PRODUCT_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                ProductEntry.NAME+ " TEXT NOT NULL, " +
                ProductEntry.PRICE + " INTEGER NOT NULL, " +
                ProductEntry.QUANTITY+ " INTEGER, " +
                ProductEntry.STOCK_STATUS + " INTEGER NOT NULL, "+
                ProductEntry.SUPPLIER_ID + " INTEGER NOT NULL,"+
                "FOREIGN KEY("+ProductEntry.SUPPLIER_ID+") REFERENCES "+ SupplierEntry.NAME+"("+SupplierEntry.SUP_ID+"));";
//                ProductEntry.SUPPLIER_ID + " INTEGER REFERENCES "+ SupplierEntry.TABLE_NAME +");";
//                ProductEntry.SUPPLIER_NAME + " TEXT NOT NULL, " +
//                ProductEntry.SUPPLIER_PHONE_NUMBER + " TEXT);";
//
//              "FOREIGN KEY("+SUPPLIER_ID) REFERENCES ProductEntry.TABLE_NAME(ProductEntry.PRODUCT_ID));

        final String CREATE_SUPPLIER_TABLE = "CREATE TABLE "+ SupplierEntry.TABLE_NAME + " ("
                + SupplierEntry.SUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SupplierEntry.NAME + " TEXT NOT NULL, "
                + SupplierEntry.PHONE + " TEXT);";

        sqLiteDatabase.execSQL(CREATE_PRODUCT_TABLE);
        sqLiteDatabase.execSQL(CREATE_SUPPLIER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // do something
    }

    public Cursor getAllProducts() {
        return getReadableDatabase().rawQuery("SELECT * FROM " +ProductEntry.TABLE_NAME, null);
    }

    public Cursor getAllSuppliers() {
        return getReadableDatabase().rawQuery("SELECT * FROM " +SupplierEntry.TABLE_NAME, null);
    }

    public long insertProduct(long supplierId) {
        ContentValues values = new ContentValues();

        values.put(ProductEntry.NAME,"Quiet - The Power of Introverts");
        values.put(ProductEntry.PRICE,6);
        values.put(ProductEntry.QUANTITY,5);
        values.put(ProductEntry.STOCK_STATUS,ProductEntry.IN_STOCK);
        values.put(ProductEntry.SUPPLIER_ID,supplierId);

        // Insert the new row, returning the primary key value of the new row,e lse -1
        //null here means it does not create any row if you didnt provide values otherwise
        //it will insert row with null values.
        return getWritableDatabase().insert(ProductEntry.TABLE_NAME, null, values);
    }

    public long insertSupplier() {
        ContentValues values = new ContentValues();

        values.put(SupplierEntry.NAME,"Amazon");
        values.put(SupplierEntry.PHONE,"02056897464");

        return getWritableDatabase().insert(SupplierEntry.TABLE_NAME,null,values);
    }

    public Cursor getJoinedRows() {
        final String QUERY = "SELECT *"
                + " FROM " + ProductEntry.TABLE_NAME + " P"
                + " INNER JOIN "+ SupplierEntry.TABLE_NAME + " S"
                + " ON S."+ SupplierEntry.SUP_ID + " = " + "P." +ProductEntry.SUPPLIER_ID;

        return getReadableDatabase().rawQuery(QUERY,null);
    }

    public int deleteAllProducts() {
        return getWritableDatabase().delete(ProductEntry.TABLE_NAME, null, null);
    }

    public int deleteAllSuppliers() {
        return getWritableDatabase().delete(SupplierEntry.TABLE_NAME, null, null);
    }
}
