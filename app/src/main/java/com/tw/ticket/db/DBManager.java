package com.tw.ticket.db;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.tw.ticket.app.R;
import com.tw.ticket.migration.Patch;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class DBManager {
    Context context;
    Patch[] patches;
    MigrationHelper migrationHelper;
    SQLiteDatabase db;

    public DBManager(Context context) {
        this.context = context;
        getClasses();
        migrationHelper = new MigrationHelper(context, patches);
        db = migrationHelper.getWritableDatabase();
    }

    public void insertAValue(){
        db.execSQL("insert into Vacation(name,date,status) values (?,?,?)", new String[] { "Vacation1", "some_date", "status" });
    }

    public List<String> readAllValues(){
        Cursor data = db.rawQuery("select * from Vacation", null);
        List<String> actualData = new ArrayList<String>();
        while (data.moveToNext()) {
            actualData.add(data.getString(1));
        }
        data.close();
        return actualData;
    }
    private void getClasses() {
        TypedArray typedArray = context.getResources().obtainTypedArray(R.array.patch);
        int numberOfPatches = typedArray.length();
        patches = new Patch[numberOfPatches];
        for (int i = 0; i < numberOfPatches; i++) {
            String className = typedArray.getText(i).toString();
            Class instance = null;
            try {
                instance = Class.forName(className);
                patches[i] = (Patch)instance.newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        migrationHelper.close();
        db.close();
    }
}
