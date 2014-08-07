package kusix.myflowers.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	private static final String TAG = "DBHelper";
	private static final String DB_NAME = "myflowers";
	private static final int DB_VERSION = 1;

	private SQLiteDatabase db;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		try {
			db = getWritableDatabase();
		} catch (SQLiteException e) {
			Log.e(TAG, "create db failed", e);
		}
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		if (db != null && db.isOpen()) {
			createFlowers();
			createFlowerData();
		}

	}
	
	private void createFlowers() {	
			db.execSQL("CREATE table IF NOT EXISTS " + "FLOWERS"
					+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name TEXT, " + "type TEXT, "
					+ "actived BOOLEAN, " + "created DTAE, "
					+ "modified DTAE)");
	}

	private void createFlowerData() {
			db.execSQL("CREATE table IF NOT EXISTS " + "FLOWER_DATA"
					+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "flower_id INTEGER, " + "air_Temperature INTEGER, " + "air_Humidity INTEGER, "
					+ "soil_Moisture INTEGER, " + "light INTEGER, "
					+ "created DTAE)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
