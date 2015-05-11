package com.etf.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.etf.controller.Controler;
import com.etf.simulation.Bacanje;
import com.etf.simulation.Igra;
import com.etf.utils.ListObject;

public class YambDb
{
	private static String DATABASE_NAME = "yamb";
	private static int DATABASE_VERSION = 1;
	private Context context;
	private DatabaseHelper myDbHelper;
	private SQLiteDatabase myDatabase;

	private static final String IGRA_TABLE = "igra";
	private static final String BACANJE_TABLE = "bacanje";
	private static final String DATABASE_CREATE_IGRA_TABLE =

	"create table igra (rb integer primary key autoincrement, " + "pl1 text null," + "pl2 text null," + "pl3 text  null," + "pl4 text  null," + "vreme long  null,"
			+ "trajanje long  null," + "broj_igraca integer null)";

	private static final String DATABASE_CREATE_BACANJE_TABLE = "create table bacanje (id integer primary key autoincrement, " + "rb integer not null," + "igrac text not null,"
			+ "bacanje1 text  null," + "bacanje2 text  null," + "bacanje3 text  null," + "x integer  null," + "y integer  null," + "value integer  null," + "selected1 text  null,"
			+ "selected2 text  null)";

	public YambDb(Context context)
	{
		this.setContext(context);
	}

	/**
	 * Opens a connection with the database.
	 * 
	 * @return the database.
	 * @throws SQLException
	 */
	public YambDb open()
	{
		myDbHelper = new DatabaseHelper(context);
		myDatabase = myDbHelper.getWritableDatabase();
		return this;
	}

	public List<Igra> getIgre()
	{
		List<Igra> igre = new ArrayList<Igra>();

		String[] COLUMNS = { "pl1", "pl2", "pl3", "pl4", "vreme", "rb", "broj_igraca" };
		open();

		Cursor cursor = myDatabase.query(IGRA_TABLE, // a. table
				COLUMNS, // b. column names
				null, null, // e. group by
				null, // f. having
				null, // g. order by
				null); // h. limit

		if (cursor.moveToFirst())
		{
			do
			{
				Igra igra = new Igra();
				igra.setPl1(cursor.getString(0));
				igra.setPl2(cursor.getString(1));
				igra.setPl3(cursor.getString(2));
				igra.setPl4(cursor.getString(3));
				igra.setVreme(cursor.getLong(4));
				igra.setRb(cursor.getInt(5));
				igra.setBrojIgraca(cursor.getInt(6));

				List<Bacanje> bacanja = new ArrayList<Bacanje>();
				String[] COLUMNS2 = { "rb", "igrac", "bacanje1", "bacanje2", "bacanje3", "x", "y", "value", "selected1", "selected2" };
				open();
				Cursor cursor2 = myDatabase.query(BACANJE_TABLE, // a. table
						COLUMNS2, // b. column names
						"rb = ?", // c. selections
						new String[] { "" + igra.getRb() }, // d. selections
															// args
						null, // e. group by
						null, // f. having
						null, // g. order by
						null); // h. limit

				if (cursor2.moveToFirst())
				{
					do
					{
						Bacanje b = new Bacanje();
						b.setBrojPartije(cursor2.getInt(0));
						b.setIgrac(cursor2.getString(1));
						b.setBacanje1(cursor2.getString(2));
						b.setBacanje2(cursor2.getString(3));
						b.setBacanje3(cursor2.getString(4));
						b.setX(cursor2.getInt(5));
						b.setY(cursor2.getInt(6));
						b.setValue(cursor2.getInt(7));
						b.setSelected1(cursor2.getString(8));
						b.setSelected2(cursor2.getString(9));

						igra.getLista().add(b);

					} while (cursor2.moveToNext());

				}

				igre.add(igra);

			} while (cursor.moveToNext());

		}

		close();

		return igre;
	}

	public void insertIgra()
	{
		open();
		ContentValues cv = new ContentValues();
		Igra igra = Controler.getControler().getIgra();

		cv.put("pl1", igra.getPl1());
		cv.put("pl2", igra.getPl2());
		cv.put("pl3", igra.getPl3());
		cv.put("pl4", igra.getPl4());
		cv.put("vreme", igra.getVreme());
		cv.put("trajanje", igra.getTrajanje());
		cv.put("broj_igraca", igra.getBrojIgraca());

		long id = myDatabase.insert(IGRA_TABLE, null, cv);
		for (Bacanje b : Controler.getControler().getIgra().getLista())
		{
			b.setBrojPartije((int) id);
			cv = new ContentValues();
			cv.put("rb", b.getBrojPartije());
			cv.put("igrac", b.getIgrac());
			cv.put("bacanje1", b.getBacanje1());
			cv.put("bacanje2", b.getBacanje2());
			cv.put("bacanje3", b.getBacanje3());
			cv.put("x", b.getX());
			cv.put("y", b.getY());
			cv.put("value", b.getValue());
			cv.put("selected1", b.getSelected1());
			cv.put("selected2", b.getSelected2());

			myDatabase.insert(BACANJE_TABLE, null, cv);

		}

		close();

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
			db.execSQL("DROP TABLE IF EXISTS " + IGRA_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + BACANJE_TABLE);
			onCreate(db);
		}
	}

}
