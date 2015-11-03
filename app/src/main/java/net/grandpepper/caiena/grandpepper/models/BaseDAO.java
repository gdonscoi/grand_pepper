package net.grandpepper.caiena.grandpepper.models;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.table.TableUtils;

import net.grandpepper.caiena.grandpepper.database.DatabaseHelper;
import net.grandpepper.caiena.grandpepper.database.DatabaseManager;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class BaseDAO<T extends IEntidade> {
    protected Context ctx;

    protected DatabaseHelper getHelper() {
        return DatabaseManager.getInstance(ctx).getHelper();
    }

    protected Dao<T, Object> getConnection() throws Exception {
        return getHelper().getDAO(getEntityClass());
    }

    private Class getEntityClass() throws Exception {
        ParameterizedType t = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class) t.getActualTypeArguments()[0];
    }

    public List<T> findAll() throws Exception {
        return (List<T>) getHelper().getDAO(getEntityClass()).queryForAll();
    }

    public T findByPK(Object id) throws Exception {
        return (T) getHelper().getDAO(getEntityClass()).queryForId(id);
    }

    public int createOrUpdate(T obj) throws Exception {
        return getHelper().getDAO(getEntityClass()).createOrUpdate(obj).getNumLinesChanged();
    }

    public int createOrUpdate(List<T> obj) throws Exception {
        int numLinesChanged = 0;
        int objSize = obj.size();
        for (int i = 0; i < objSize; i++) {
            numLinesChanged += getHelper().getDAO(getEntityClass()).createOrUpdate(obj.get(i)).getNumLinesChanged();
        }
        return numLinesChanged;
    }

    public int delete(T obj) throws Exception {
        return getHelper().getDAO(getEntityClass()).delete(obj);
    }

    public int deleteByParam(String colum, Long idOtherObject) throws Exception {
        DeleteBuilder<T, Object> deleteBuilder = getHelper().getDAO(getEntityClass()).deleteBuilder();
        deleteBuilder.where().eq(colum, idOtherObject);
        PreparedDelete preparedDelete = deleteBuilder.prepare();
        return getHelper().getDAO(getEntityClass()).delete(preparedDelete);
    }

    public int deleteByParam(String colum, String idOtherObject) throws Exception {
        DeleteBuilder<T, Object> deleteBuilder = getHelper().getDAO(getEntityClass()).deleteBuilder();
        deleteBuilder.where().eq(colum, idOtherObject);
        PreparedDelete preparedDelete = deleteBuilder.prepare();
        return getHelper().getDAO(getEntityClass()).delete(preparedDelete);
    }

    public void destroyAll() throws Exception {
        TableUtils.clearTable(getHelper().getConnectionSource(), getEntityClass());
    }

    public List<T> findByParam(String fieldName, Object value) throws Exception {
        return getHelper().getDAO(getEntityClass()).queryForEq(fieldName, value);
    }
}
