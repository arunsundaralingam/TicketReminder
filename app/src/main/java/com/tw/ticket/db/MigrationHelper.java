package com.tw.ticket.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.tw.ticket.migration.Patch;

public class MigrationHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "TicketReminder";
    private Patch[] patches;

    public MigrationHelper(Context context, Patch[] patches) {
        super(context, DB_NAME, null, patches.length + 1);
        this.patches = patches;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (Patch patch : patches) {
            db.execSQL(patch.onUpgrade());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int existingVersion, int newVersion) {
        for(int i = existingVersion; i < newVersion; i++) {
            db.execSQL(patches[i].onUpgrade());
        }
    }
}
