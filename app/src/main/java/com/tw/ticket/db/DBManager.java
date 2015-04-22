package com.tw.ticket.db;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.tw.ticket.app.R;
import com.tw.ticket.migration.Patch;
import com.tw.ticket.models.Reminder;
import com.tw.ticket.models.Vacation;

import javax.inject.Singleton;

@Singleton
public class DBManager {
    private Context context;
    private Patch[] patches;
    private MigrationHelper migrationHelper;
    private SQLiteDatabase db;
    private RuntimeExceptionDao<Vacation, ?> vacationDAO;
    private RuntimeExceptionDao<Reminder, ?> reminderDAO;

    static {
        OpenHelperManager.setOpenHelperClass(MigrationHelper.class);
    }

    public DBManager(Context context) {
        this.context = context;
        getClasses();
        migrationHelper = new MigrationHelper(context, patches);
        db = migrationHelper.getWritableDatabase();
        vacationDAO = migrationHelper.getRuntimeExceptionDao(Vacation.class);
        reminderDAO = migrationHelper.getRuntimeExceptionDao(Reminder.class);
    }

    public RuntimeExceptionDao getDao(Class model){
        return migrationHelper.getRuntimeExceptionDao(model);
    }

    public void addOrUpdateVacation(Vacation vacation) {
        vacationDAO.createOrUpdate(vacation);
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
                patches[i] = (Patch) instance.newInstance();
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
