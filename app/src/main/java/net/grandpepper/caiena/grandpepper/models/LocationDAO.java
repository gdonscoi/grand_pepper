package net.grandpepper.caiena.grandpepper.models;

import android.content.Context;

import net.grandpepper.caiena.grandpepper.beans.Location;

public class LocationDAO extends BaseDAO<Location> {

    private static LocationDAO dao;

    public static LocationDAO getInstance(Context ctx) {
        if (dao == null) {
            dao = new LocationDAO(ctx);
        }
        return dao;
    }

    private LocationDAO(Context ctx) {
        super();
        super.ctx = ctx;
    }

}
