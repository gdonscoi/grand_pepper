package net.grandpepper.caiena.grandpepper.models;

import android.content.Context;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import net.grandpepper.caiena.grandpepper.beans.Event;

import java.util.List;

public class EventDAO extends BaseDAO<Event> {

    private static EventDAO dao;

    public static EventDAO getInstance(Context ctx) {
        if (dao == null) {
            dao = new EventDAO(ctx);
        }
        return dao;
    }

    private EventDAO(Context ctx) {
        super();
        super.ctx = ctx;
    }

    public List<Event> getTalks(int grandPepperVersion) throws Exception {

        QueryBuilder<Event, Object> queryBuilder = dao.getConnection().queryBuilder();
        Where<Event, Object> where = queryBuilder.where();
        //noinspection unchecked
        where.and(where.eq("grandPepper_version", grandPepperVersion),
                where.or(where.eq("eventType", "presentation"),
                        where.eq("eventType", "techtalk")));

        PreparedQuery<Event> preparedQuery = queryBuilder.prepare();

        return dao.getConnection().query(preparedQuery);

    }

}
