package com.tw.ticket.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.tw.ticket.app.R;
import com.tw.ticket.migration.Patch;

public class MigrationHelper extends OrmLiteSqliteOpenHelper {
    private static final String DB_NAME = "TicketReminder";
    private Patch[] patches;

    public MigrationHelper(Context context, Patch[] patches) {
        super(context, DB_NAME, null, patches.length + 1, R.raw.ormlite_config);
        this.patches = patches;
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //TODO : currently the migrations are done with sql scripts. need to find a way to avoid this
        for (Patch patch : patches) {
            database.execSQL(patch.onUpgrade());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        for(int i = oldVersion; i < newVersion; i++) {
            database.execSQL(patches[i].onUpgrade());
        }
    }

}
