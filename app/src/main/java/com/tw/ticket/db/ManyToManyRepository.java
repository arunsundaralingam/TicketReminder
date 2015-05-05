package com.tw.ticket.db;

import android.content.Context;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;

import java.sql.SQLException;
import java.util.List;

public class ManyToManyRepository<S, T, U> {
    private final DBManager dbManager;
    private final RuntimeExceptionDao sourceDao;
    private final RuntimeExceptionDao targetDao;
    private final RuntimeExceptionDao manyToManyDao;
    private PreparedQuery<T> targetForSourceQuery = null;

    public ManyToManyRepository(Context context, Class<S> sourceModel, Class<T> targetModel, Class<U> manyToManyModel) {
        this.dbManager = new DBManager(context);
        this.sourceDao = dbManager.getDao(sourceModel);
        this.targetDao = dbManager.getDao(targetModel);
        this.manyToManyDao = dbManager.getDao(manyToManyModel);
    }

    public List<T> lookupTargetForSource(S source, String sourceFieldName, String targetFieldName) throws SQLException {
        if (targetForSourceQuery == null) {
            targetForSourceQuery = makeQueryForTarget(sourceFieldName, targetFieldName);
        }
        targetForSourceQuery.setArgumentHolderValue(0, source);
        return targetDao.query(targetForSourceQuery);
    }

    private PreparedQuery<T> makeQueryForTarget(String sourceFieldName, String targetFieldName) throws SQLException {
        QueryBuilder<U, Integer> manyToManyQB = manyToManyDao.queryBuilder();
        manyToManyQB.selectColumns(targetFieldName);
        SelectArg userSelectArg = new SelectArg();
        manyToManyQB.where().eq(sourceFieldName, userSelectArg);
        QueryBuilder<T, Integer> targetQB = targetDao.queryBuilder();
        // TODO: This assumes all plain models have id field, need to pass id fields
        String id = "id";
        targetQB.where().in(id, manyToManyQB);
        return targetQB.prepare();
    }

    public void close() {
        dbManager.close();
    }
}
