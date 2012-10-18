package com.tooflya.bubblefun.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tooflya.bubblefun.Game;

/**
 * @author Tooflya.com
 * @since
 */
public class LevelsStorage extends SQLiteOpenHelper {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "storage";
	private static final String TABLE = "levels";

	private static final String ID = "id";
	private static final String STATE = "state";
	private static final String STARS = "stars";

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * 
	 */
	public LevelsStorage() {
		super(Game.context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * @param db
	 */
	public void addLevel(final SQLiteDatabase db) {
		ContentValues values = new ContentValues();
		values.put(STATE, 0);
		values.put(STARS, 0);

		db.insert(TABLE, null, values);
	}

	/**
	 * @param id
	 * @param pOpen
	 * @param pStars
	 * @return
	 */
	public int updateLevel(final int id, final int pOpen, final int pStars) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(STATE, pOpen);
		values.put(STARS, pStars);

		return db.update(TABLE, values, ID + " = ?", new String[] { String.valueOf(id) });
	}

	/**
	 * @param id
	 * @param pOpen
	 * @return
	 */
	public int updateLevel(final int id, final int pOpen) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(STATE, pOpen);

		return db.update(TABLE, values, ID + " = ?", new String[] { String.valueOf(id) });
	}

	/**
	 * @param id
	 * @return
	 */
	public Level getLevel(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE, new String[] { STATE, STARS }, ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();
		}

		return new Level(cursor.getInt(0) > 0, cursor.getInt(1));
	}

	// ===========================================================
	// Virtual methods
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE + "(" + ID + "  INTEGER PRIMARY KEY AUTOINCREMENT," + STARS + " INTEGER DEFAULT 0,  " + STATE + " INTEGER DEFAULT 0" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);

		for (int i = 1; i <= 20; i++) {
			this.addLevel(db);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE);

		onCreate(db);
	}
}