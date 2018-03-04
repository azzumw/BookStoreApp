package com.example.macintosh.bookstoreapp.data;

import android.content.Context;
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
                ProductEntry.SUPPLIER_NAME + " TEXT NOT NULL, " +
                ProductEntry.SUPPLIER_PHONE_NUMBER + " TEXT);";

        final String CREATE_SUPPLIER_TABLE = "CREATE TABLE "+ SupplierEntry.TABLE_NAME + " ("
                + SupplierEntry.SUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SupplierEntry.NAME + " TEXT NOT NULL, "
                + SupplierEntry.phone + " TEXT);";

        sqLiteDatabase.execSQL(CREATE_PRODUCT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
