package com.tooflya.bubblefun.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tooflya.bubblefun.Game;
import com.tooflya.bubblefun.Options;
import com.tooflya.bubblefun.screens.LevelScreen;

/**
 * @author Tooflya.com
 * @since
 */
public class DataStorage extends SQLiteOpenHelper {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "storage";

	private static final String LEVEL_TABLE = "levels";
	private static final String BOX_TABLE = "boxes";

	private static final String LEVEL_ID = "id";
	private static final String LEVEL_STATE = "state";
	private static final String LEVEL_STARS = "stars";
	private static final String LEVEL_SCORE = "score";

	private static final String BOX_ID = "id";
	private static final String BOX_STATE = "state";

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * 
	 */
	public DataStorage() {
		super(Game.context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * @param db
	 */
	public void addBox(final SQLiteDatabase db) {
		ContentValues values = new ContentValues();
		values.put(BOX_STATE, 0);

		db.insert(BOX_TABLE, null, values);
	}

	/**
	 * @param db
	 */
	public void addBox(final SQLiteDatabase db, final boolean pIsOpen) {
		ContentValues values = new ContentValues();
		values.put(BOX_STATE, 1);

		db.insert(BOX_TABLE, null, values);
	}

	/**
	 * @param id
	 * @return
	 */
	public Box getBox(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(BOX_TABLE, new String[] { BOX_STATE }, LEVEL_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();
		}

		final Box box = new Box(id, cursor.getInt(0) > 0);

		cursor.close();

		return box;
	}

	public int updateBox(final int id, final int pOpen) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(BOX_STATE, pOpen);

		return db.update(BOX_TABLE, values, LEVEL_ID + " = ?", new String[] { String.valueOf(id) });
	}

	/**
	 * @param db
	 */
	public void addLevel(final SQLiteDatabase db) {
		ContentValues values = new ContentValues();
		values.put(LEVEL_STATE, 0);
		values.put(LEVEL_STARS, 0);

		db.insert(LEVEL_TABLE, null, values);
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
		values.put(LEVEL_STATE, pOpen);
		values.put(LEVEL_STARS, pStars);
		values.put(LEVEL_SCORE, LevelScreen.Score);

		return db.update(LEVEL_TABLE, values, LEVEL_ID + " = ?", new String[] { String.valueOf(id + 25 * Options.boxNumber) });
	}

	/**
	 * @param id
	 * @param pOpen
	 * @return
	 */
	public int updateLevel(final int id, final int pOpen) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(LEVEL_STATE, pOpen);

		return db.update(LEVEL_TABLE, values, LEVEL_ID + " = ?", new String[] { String.valueOf(id + 25 * Options.boxNumber) });
	}

	public int updateLevel(final int id, final int pOpen, final int pStars, final int pScore) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(LEVEL_STATE, pOpen);
		values.put(LEVEL_STARS, pStars);
		values.put(LEVEL_SCORE, pScore);

		return db.update(LEVEL_TABLE, values, LEVEL_ID + " = ?", new String[] { String.valueOf(id + 25 * Options.boxNumber) });
	}

	/**
	 * @param id
	 * @return
	 */
	public Level getLevel(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(LEVEL_TABLE, new String[] { LEVEL_STATE, LEVEL_STARS }, LEVEL_ID + "=?", new String[] { String.valueOf(id + 25 * Options.boxNumber) }, null, null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();
		}

		final Level level = new Level(id, cursor.getInt(0) > 0, cursor.getInt(1));

		cursor.close();

		return level;
	}

	public int getTotalCore() {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery("SELECT SUM(score) FROM levels", null);

		cursor.moveToFirst();
		final int value = cursor.getInt(0);

		cursor.close();

		return value;
	}

	public int getTotalStars() {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery("SELECT SUM(stars) FROM levels", null);

		cursor.moveToFirst();
		final int value = cursor.getInt(0);

		cursor.close();

		return value;
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
		db.execSQL("CREATE TABLE " + LEVEL_TABLE + "(" + LEVEL_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT," + LEVEL_STARS + " INTEGER DEFAULT 0,  " + LEVEL_SCORE + " INTEGER DEFAULT 0,  " + LEVEL_STATE + " INTEGER DEFAULT 0" + ")");
		db.execSQL("CREATE TABLE " + BOX_TABLE + "(" + BOX_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT," + BOX_STATE + " INTEGER DEFAULT 0" + ")");

		for (int i = 1; i <= 25 * 3; i++) {
			this.addLevel(db);
		}

		this.addBox(db, true);
		for (int i = 1; i <= 3; i++) {
			this.addBox(db);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + LEVEL_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + BOX_TABLE);

		onCreate(db);
	}
}
