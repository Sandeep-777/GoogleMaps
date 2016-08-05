package com.sandeep.sthapit.maps;

import android.provider.BaseColumns;

/**
 * Created by sandeep on 8/5/16.
 */
public class DatabaseContract {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "transport.db";
    public static final String TEXT_TYPE = " TEXT";
    public static final String COMMA_SEP = ", ";
    public static final String INTEGER_TYPE = " INTEGER";

    public DatabaseContract() {
    }

    public static abstract class RouteTable {
        public static final String TABLE_NAME = "route";
        public static final String COLUMN_NAME_ID = "r_id";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_NAME_ID + " TEXT PRIMARY KEY" + " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class PlaceTable {
        public static final String TABLE_NAME = "place";
        public static final String COLUMN_NAME_ID = "p_id";
        public static final String COLUMN_NAME_NAME = "name";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_NAME_ID + " TEXT PRIMARY KEY," +
                COLUMN_NAME_NAME + TEXT_TYPE + " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class VehicleTable {
        public static final String TABLE_NAME = "vehicle";
        public static final String COLUMN_NAME_NUMBER = "number";
        public static final String COLUMN_NAME_TYPE = "type";


        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_NAME_NUMBER + " TEXT PRIMARY KEY," +
                COLUMN_NAME_TYPE + TEXT_TYPE + " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
