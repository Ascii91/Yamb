package com.etf.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.etf.simulation.Igra;

public class YambDb
{
    private static String       DATABASE_NAME                 = "yamb";
    private static int          DATABASE_VERSION              = 1;
    private Context             context;
    private DatabaseHelper      myDbHelper;
    private SQLiteDatabase      myDatabase;

    private static final String DATABASE_CREATE_IGRA_TABLE    =

                                                              "create table igra (rb integer primary key autoincrement, " + "pl1 text not null,"
                                                                      + "pl2 text not null," + "pl3 text not null," + "pl4 text not null,"
                                                                      + "vreme long not null," + "trajanje long  null," + "broj_igraca integer not null)";

    private static final String DATABASE_CREATE_BACANJE_TABLE = "create table igra (id integer primary key autoincrement, " + "rb integer not null,"
                                                                      + "igrac text not null," + "broj_bacanja integer not null," + "bacanje1 text not null,"
                                                                      + "bacanje2 text not null," + "bacanje3 text not null," + "x integer not null,"
                                                                      + "y integer not null," + "value integer not null," + "selected1 text not null,"
                                                                      + "selected2 text not null,)";

    public YambDb(Context context)
    {
        this.setContext(context);
    }

    /**
     * Opens a connection with the database.
     * @return the database.
     * @throws SQLException
     */
    public YambDb open()
    {
        myDbHelper = new DatabaseHelper(context);
        myDatabase = myDbHelper.getWritableDatabase();
        return this;
    }

    public void insertIgra()
    {

    }

    public int getLastRb()
    {
        int lastId = 0;
        String query = "SELECT ROWID from MYTABLE order by ROWID DESC limit 1";
        Cursor c = myDatabase.rawQuery(query, null);
        if (c != null && c.moveToFirst())
        {
            lastId = c.getInt(0); // The 0 is the column index, we only have 1 column, so the index is 0
        }

        return lastId;
    }

    /**
     * Closes the connection with the database.
     */
    public void close()
    {
        myDbHelper.close();
    }

    public void insertIgra(Igra igra)
    {

    }

    public Context getContext()
    {
        return context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }

    public DatabaseHelper getMyDbHelper()
    {
        return myDbHelper;
    }

    public void setMyDbHelper(DatabaseHelper myDbHelper)
    {
        this.myDbHelper = myDbHelper;
    }

    public SQLiteDatabase getMyDatabase()
    {
        return myDatabase;
    }

    public void setMyDatabase(SQLiteDatabase myDatabase)
    {
        this.myDatabase = myDatabase;
    }

    /**
     * Private class which is used for creation and update of the database.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper
    {

        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(DATABASE_CREATE_IGRA_TABLE);
            db.execSQL(DATABASE_CREATE_BACANJE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS igra");
            db.execSQL("DROP TABLE IF EXISTS bacanje");
            onCreate(db);
        }
    }

}
