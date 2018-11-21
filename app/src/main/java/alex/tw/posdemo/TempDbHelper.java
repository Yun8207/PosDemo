
package alex.tw.posdemo;

        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

public class TempDbHelper extends SQLiteOpenHelper {
    private final String createTable =
            "CREATE TABLE product (ind INTEGER PRIMARY KEY AUTOINCREMENT, pname TEXT, price INTEGER, discount INTEGER, cid TEXT)";
    private final String createTable2 =
            "CREATE TABLE purchase (PuID INTEGER PRIMARY KEY AUTOINCREMENT, pname TEXT, date DATE, price INTEGER, discount INTEGER, quantity INTEGER, tprice INTEGER, sugar INTEGER)";


    public TempDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
        db.execSQL(createTable2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(createTable);
        db.execSQL(createTable2);

    }
}