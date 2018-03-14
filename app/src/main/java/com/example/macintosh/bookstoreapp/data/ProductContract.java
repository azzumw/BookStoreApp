package com.example.macintosh.bookstoreapp.data;

import android.provider.BaseColumns;

/**
 * API Contract for the BookStore app.
 */

public final class ProductContract {

    //private constructor
    private ProductContract(){}

    //inner class that defines the schema of bookstore database
    public static final class ProductEntry implements BaseColumns{
        /**TABLE NAME*/
        public final static String TABLE_NAME = "products";

        /**UNIQUE ID NUMBER*/
        public final static String PRODUCT_ID = BaseColumns._ID;

        /**COLUMNS*/
        public final static String NAME = "name";
        public final static String PRICE = "price";
        public final static String QUANTITY = "quantity";
//        public final static String SUPPLIER_NAME = "supplier_name";
//        public final static String SUPPLIER_PHONE_NUMBER = "supplier_phone";
        public final static String STOCK_STATUS ="stock_status";
        public final static String SUPPLIER_ID ="supplier_id";

        /**POSSIBLE VALUES FOR IN_STOCK*/
        public final static int IN_STOCK = 1;
        public final static int OUT_OF_STOCK = 0;

    }

    public static final class SupplierEntry implements BaseColumns{
        /**TABLE NAME*/
        public final static String TABLE_NAME = "supplier";

        /**UNIQUE ID NUMBER*/
        public final static String SUP_ID = BaseColumns._ID;
        public final static String NAME = "supp_name";
        public final static String PHONE = "PHONE";
    }
}
