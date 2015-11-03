package net.grandpepper.caiena.grandpepper.models;

import android.content.Context;

import net.grandpepper.caiena.grandpepper.beans.Event;

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

}
