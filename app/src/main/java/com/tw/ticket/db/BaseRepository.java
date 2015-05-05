package com.tw.ticket.db;

import android.content.Context;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.util.List;

public class BaseRepository<T> {
    protected RuntimeExceptionDao dao;
    private DBManager dbManager;

    public BaseRepository(Context context, Class<T> type) {
        this.dbManager = new DBManager(context);
        this.dao = dbManager.getDao(type);
    }

    public void addOrUpdate(T obj) {
        dao.createOrUpdate(obj);
    }

    public List<T> readAll() {
        return dao.queryForAll();
    }

    public void close() {
        dbManager.close();
    }

    public List<T> executeQuery(PreparedQuery<T> query) {
        return dao.query(query);
    }

    public QueryBuilder<T, String> getQueryBuilder() {
        return dao.queryBuilder();
    }
}
