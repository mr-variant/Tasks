package variantproject.tasks.DB;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import variantproject.tasks.R;

/**
 * Created by ixvar on 1/17/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_day.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_COLORS = "colors";
    public static final String TABLE_ICONS = "icons";
    public static final String TABLE_TAGS = "tags";


    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_RES_ID = "resID";
    public static final String COLUMN_URL = "url";


    public static final String COLUMN_COLOR_FK = "color_fk";
    public static final String COLUMN_ICON_FK = "icon_fk";

    private Resources res;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        res = context.getResources();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_COLORS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_RES_ID + " TEXT NOT NULL);");

        insertColor(db,1, R.color.red);
        insertColor(db,2, R.color.yellow);
        insertColor(db,3, R.color.orange);
        insertColor(db,4, R.color.green);
        insertColor(db,5, R.color.darkBlue);
        insertColor(db,6, R.color.lightBlue);

        db.execSQL("CREATE TABLE " + TABLE_ICONS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_URL + " TEXT NOT NULL);");

        insertIcon(db,"Icons/cardiogram.png");
        insertIcon(db,"Icons/diamond.png");
        insertIcon(db,"Icons/wifi-signal.png");

        db.execSQL("CREATE TABLE " + TABLE_TAGS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_COLOR_FK + " INTEGER NOT NULL, "
                + COLUMN_ICON_FK  + " INTEGER NOT NULL, "
                + "FOREIGN KEY(" + COLUMN_COLOR_FK + ") REFERENCES " + TABLE_COLORS + "("+ COLUMN_ID +"), "
                + "FOREIGN KEY(" + COLUMN_ICON_FK + ") REFERENCES " + TABLE_ICONS + "("+ COLUMN_ID +"));");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ICONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAGS);

        onCreate(db);
    }


    private static void insertColor(SQLiteDatabase db,int id, int resId) {
        ContentValues colorValues = new ContentValues();
        colorValues.put(COLUMN_ID, id);
        colorValues.put(COLUMN_RES_ID, resId);

        db.insert(TABLE_COLORS, null, colorValues);
    }

    private static void insertIcon(SQLiteDatabase db, String url) {
        ContentValues iconValues = new ContentValues();
        iconValues.put(COLUMN_RES_ID, url);

        db.insert(TABLE_ICONS, null, iconValues);
    }

}
