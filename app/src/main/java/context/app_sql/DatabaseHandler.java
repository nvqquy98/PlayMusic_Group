package context.app_sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import context.app_default.DbDefault;

public class DatabaseHandler extends SQLiteOpenHelper{

    public DatabaseHandler(Context context) {
        super(context, DbDefault.DATABASE_NAME, null, DbDefault.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}

